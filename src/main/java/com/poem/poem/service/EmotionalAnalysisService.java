package com.poem.poem.service;

import com.poem.poem.domain.Tag;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EmotionalAnalysisService {
    // Claude에 글을 보내고, 응답에서 태그를 뽑아오는 서비스
    // 서비스로 분리 이유: 컨트롤러에 API 호출 코드 넣으면 역할 과다, 비즈니스 로직 service에
    private final RestClient restClient;
    // API 호출할 HTTP 클라이언트, 생성자에서 만들어두고 재사용
    private final String apiKey;
    // application.properties에서 가져온 키, 요청의 헤더에 주입

    private static final Set<String> VALID_TAGS = Arrays.stream(Tag.values())
            .map(Tag::getLabel)
            .collect(Collectors.toSet());
    // Tag enum label들 set으로 생성(O(1), List는 O(n))
    // Claude가 이상한 태그 넘겨주면 걸러내기 위함

    public EmotionalAnalysisService(@Value("${claude.api.key}") String apiKey) {
        this.apiKey = apiKey;
        this.restClient = RestClient.builder()
                .baseUrl("https://api.anthropic.com")
                // 요청의 기본 URL
                .build();
    }

    public String analyzeTags(String content) {
        String prompt = """
                다음 글을 읽고, 이 글에서 느껴지는 감정을 아래 10개 태그 중에서 2~3개 골라줘.
                반드시 아래 태그만 사용하고, 쉼표로 구분해서 태그만 출력해. 다른 말은 아무것도 하지마.
                
                [태그 목록]
                사랑, 외로움, 슬픔, 그리움, 불안, 희망, 절망, 설렘, 허무, 후회
                
                [글]
                %s
                """.formatted(content);


        String escapedPrompt = prompt
                .replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");

        String requestBody = "{\"model\":\"claude-haiku-4-5-20251001\",\"max_tokens\":100,\"messages\":[{\"role\":\"user\",\"content\":\"" + escapedPrompt + "\"}]}";

        try {
            String response = restClient.post()
                    // POST 요청 생성
                    .uri("/v1/messages")
                    // baseUrl + 이것 = "https://api.anthropic.com/v1/messages"
                    .header("x-api-key", apiKey)
                    // Anthropic API 인증 방식. 이 헤더에 API 키를 넣어야 함.
                    .header("anthropic-version", "2023-06-01")
                    // API 버전, Anthropic이 요구하는 필수 헤더.
                    .contentType(MediaType.APPLICATION_JSON)
                    // 요청 본문이 JSON이라고 알려줌.
                    .body(requestBody)
                    // 위에서 만든 JSON 본문을 요청에 담음.
                    .retrieve()
                    // 요청 실행.
                    .body(String.class);
            // 응답 본문을 String으로 받음.

            return parseTagsFromResponse(response);
            // 응답 JSON에서 태그만 뽑아내는 메서드 호출.

        } catch (Exception e) {
            e.printStackTrace();
            return null;
            // API 호출 실패(네트워크 오류, 타임아웃, 키 오류 등) → 에러 출력
            // 컨트롤러에서 null이면 수동 태그를 사용하도록 처리함.
        }
    }
    private String parseTagsFromResponse(String response) {
        // Claude API 응답 JSON에서 실제 텍스트를 꺼내고, 유효한 태그만 필터링.
        // Claude 응답 형태:
        // {"content":[{"type":"text","text":"사랑,그리움,슬픔"}], ...}
        // 여기서 "text":"사랑,그리움,슬픔" 부분만 꺼내야 함.

        int textStart = response.indexOf("\"text\":\"") + 8;
        int textEnd = response.indexOf("\"", textStart);
        // "text":"  ← 이 뒤부터 다음 큰따옴표까지가 실제 텍스트.
        // indexOf로 위치를 찾아서 substring으로 잘라냄.

        if (textStart < 8 || textEnd < 0)
            return null;
        // "text":" 패턴을 못 찾았으면 파싱 실패 → null.

        String rawText = response.substring(textStart, textEnd);
        // 예: "사랑,그리움,슬픔" 또는 "사랑, 쓸쓸함, 희망"

        String result = Arrays.stream(rawText.split(","))
                // 쉼표로 쪼갬 → ["사랑", " 그리움", " 슬픔"]
                .map(String::trim)
                // 앞뒤 공백 제거 → ["사랑", "그리움", "슬픔"]
                .filter(VALID_TAGS::contains)
                // 우리 Tag enum에 있는 것만 남김.
                // Claude가 "쓸쓸함"이라고 했으면 여기서 걸러짐.
                .collect(Collectors.joining(","));
        // 다시 쉼표로 합침 → "사랑,그리움,슬픔"

        return result.isEmpty() ? null : result;
        // 유효한 태그가 하나도 없으면 null → 폴백.
    }
}
