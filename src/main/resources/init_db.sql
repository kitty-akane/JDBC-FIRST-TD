CREATE DATABASE product_management_db;

CREATE USER product_manager_user WITH PASSWORD '123456';

CREATE DATABASE product_management_db;

GRANT CONNECT ON DATABASE product_management_db TO product_manager_user;

GRANT USAGE, CREATE ON SCHEMA public TO product_manager_user;

GRANT SELECT, INSERT, UPDATE, DELETE, REFERENCES
ON ALL TABLES IN SCHEMA public TO product_manager_user;

ALTER DEFAULT PRIVILEGES IN SCHEMA public
GRANT SELECT, INSERT, UPDATE, DELETE, REFERENCES ON TABLES TO product_manager_user;