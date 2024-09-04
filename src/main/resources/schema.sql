CREATE TABLE IF NOT EXISTS products (
                                       id VARCHAR(255) PRIMARY KEY,
                                       name VARCHAR(255) NOT NULL,
                                       brand VARCHAR(255) NOT NULL,
                                       category VARCHAR(255) NOT NULL,
                                       image_name VARCHAR(255) NOT NULL,
                                       description TEXT NOT NULL,
                                       price DOUBLE NOT NULL,
                                       created_date DATE NOT NULL
);
