CREATE TABLE book
(
    id             UUID PRIMARY KEY,
    title          VARCHAR(100) NOT NULL,
    author         VARCHAR(100) NOT NULL,
    isbn           VARCHAR(100) NOT NULL,
    category       VARCHAR(100) NOT NULL,
    published_date DATE         NOT NULL,
    del_flag       BOOLEAN      NOT NULL DEFAULT FALSE,
    date_created   TIMESTAMP    NOT NULL DEFAULT NOW(),
    date_updated   TIMESTAMP    NOT NULL DEFAULT NOW()
);

CREATE TRIGGER update_book_date_updated_trigger
    BEFORE UPDATE
    ON book
    FOR EACH ROW
EXECUTE FUNCTION update_date_updated_column();
