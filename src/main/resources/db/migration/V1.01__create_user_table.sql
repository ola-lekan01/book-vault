CREATE TABLE users
(
    id           UUID PRIMARY KEY,
    username     VARCHAR(150) NOT NULL UNIQUE,
    password     VARCHAR(150) NOT NULL,
    del_flag     BOOLEAN      NOT NULL DEFAULT FALSE,
    date_created TIMESTAMP    NOT NULL DEFAULT NOW(),
    date_updated TIMESTAMP    NOT NULL DEFAULT NOW()
);

CREATE TRIGGER update_date_updated_trigger
    BEFORE UPDATE
    ON users
    FOR EACH ROW
EXECUTE FUNCTION update_date_updated_column();