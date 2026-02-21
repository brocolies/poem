INSERT INTO book (id, title, author, published_year, purchase_url, cover_image_url)
VALUES (1, '하늘과 바람과 별과 시', '윤동주', 1948, 'https://www.aladin.co.kr/shop/wproduct.aspx?ItemId=120024', null);

INSERT INTO book (id, title, author, published_year, purchase_url, cover_image_url)
VALUES (2, '못 위의 잠', '정현종', 2003, 'https://www.aladin.co.kr/shop/wproduct.aspx?ItemId=299889', null);

INSERT INTO poem (id, title, author, content, tags, book_id)
VALUES (1, '서시', '윤동주', '죽는 날까지 하늘을 우러러
한 점 부끄럼이 없기를,
잎새에 이는 바람에도
나는 괴로워했다.', '성찰,다짐,별', 1);

INSERT INTO poem (id, title, author, content, tags, book_id)
VALUES (2, '별 헤는 밤', '윤동주', '계절이 지나가는 하늘에는
가을로 가득 차 있습니다.', '별,그리움,가을', 1);

INSERT INTO poem (id, title, author, content, tags, book_id)
VALUES (3, '방문객', '정현종', '사람이 온다는 건
실은 어마어마한 일이다.', '만남,사람,관계', 2);