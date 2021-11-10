DROP TABLE IF EXISTS ARTICLES;

CREATE TABLE ARTICLES(
                        id BIGINT AUTO-INCREMENT PRIMARY KEY,
                        news_head VARCHAR (250) NOT NULL,
			news_body VARCHAR (250) NOT NULL,
);


