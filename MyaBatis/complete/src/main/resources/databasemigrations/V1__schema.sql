DROP TABLE IF EXISTS GENRE;
DROP TABLE IF EXISTS BOOK;

CREATE TABLE GENRE (
  id    BIGINT SERIAL PRIMARY KEY NOT NULL,
  name VARCHAR(255)              NOT NULL UNIQUE
);

CREATE TABLE BOOK (
  id    BIGINT SERIAL PRIMARY KEY NOT NULL,
  name VARCHAR(255)              NOT NULL,
  isbn VARCHAR(255)              NOT NULL,
  genre_id BIGINT,
    constraint FKM1T3YVW5I7OLWDF32CWUUL7TA
    foreign key (GENRE_ID) references GENRE
);

