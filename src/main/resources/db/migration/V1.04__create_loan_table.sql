CREATE TABLE loan
(
    id           UUID PRIMARY KEY,
    book_id      UUID NOT NULL,
    member_id    UUID NOT NULL,
    loan_date    DATE         NOT NULL,
    return_date  DATE,
    del_flag     BOOLEAN      NOT NULL DEFAULT FALSE,
    date_created TIMESTAMP    NOT NULL DEFAULT NOW(),
    date_updated TIMESTAMP    NOT NULL DEFAULT NOW()
);

ALTER TABLE loan
    ADD CONSTRAINT fk_loan_book_id
    FOREIGN KEY (book_id)
    REFERENCES book (id);

ALTER TABLE loan
    ADD CONSTRAINT fk_loan_member_id
    FOREIGN KEY (member_id)
    REFERENCES member (id);


CREATE TRIGGER update_loans_date_updated_trigger
    BEFORE UPDATE
    ON loan
    FOR EACH ROW
EXECUTE FUNCTION update_date_updated_column();
