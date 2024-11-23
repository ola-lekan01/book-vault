CREATE TABLE member
(
    id              UUID PRIMARY KEY,
    name            VARCHAR(150) NOT NULL UNIQUE,
    email           VARCHAR(150) NOT NULL,
    membership_date DATE         NOT NULL,
    del_flag        BOOLEAN      NOT NULL DEFAULT FALSE,
    date_created    TIMESTAMP    NOT NULL DEFAULT NOW(),
    date_updated    TIMESTAMP    NOT NULL DEFAULT NOW()
);

CREATE TRIGGER update_date_updated_trigger
    BEFORE UPDATE
    ON member
    FOR EACH ROW
EXECUTE FUNCTION update_date_updated_column();