-- Flyway migration for the initial schema
CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    phone VARCHAR(20) NOT NULL,
    email VARCHAR(100),
    password VARCHAR(100) NOT NULL,
    role VARCHAR(50),
    trust_score INT DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS products (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    price DECIMAL(10,2) NOT NULL,
    stock INT NOT NULL,
    category_id BIGINT,
    warehouse_id BIGINT,
    image_url VARCHAR(255),
    FOREIGN KEY (category_id) REFERENCES categories(id),
    FOREIGN KEY (warehouse_id) REFERENCES warehouses(id)
);

-- Add other table schemas here...

-- Add indexes for optimization
CREATE INDEX idx_user_phone ON users(phone);
CREATE INDEX idx_product_name ON products(name);
