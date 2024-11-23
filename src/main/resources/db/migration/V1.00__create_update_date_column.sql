DROP FUNCTION IF EXISTS update_date_updated_column();

CREATE OR REPLACE FUNCTION update_date_updated_column()
    RETURNS TRIGGER AS
$$
BEGIN
    NEW.date_updated = NOW();
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;