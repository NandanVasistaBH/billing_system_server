CREATE DATABASE IF NOT EXISTS billing_system;

-- Switch to the new database
USE billing_system;

-- Create the Supplier table
CREATE TABLE Supplier (
    branch_id INT AUTO_INCREMENT PRIMARY KEY,
    branch_loc VARCHAR(255) NOT NULL,
    branch_manager VARCHAR(100) NOT NULL,
    branch_email VARCHAR(100) UNIQUE NOT NULL,
    branch_phone_no VARCHAR(20),
    branch_password VARCHAR(255) NOT NULL
);

-- Create the Customer table
CREATE TABLE Customer (
    cust_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    cust_email VARCHAR(100) UNIQUE NOT NULL,
    cust_phone_no VARCHAR(20),
    branch_id INT,
    cust_password VARCHAR(255) NOT NULL,
    FOREIGN KEY (branch_id) REFERENCES Supplier(branch_id)
);

-- Create the Subscription table
CREATE TABLE Subscription (
    subscription_id INT AUTO_INCREMENT PRIMARY KEY,
    type VARCHAR(50) NOT NULL,
    service_days INT NOT NULL,
    no_of_days INT NOT NULL
);

-- Create the Payment table
CREATE TABLE Payment (
    pay_id INT AUTO_INCREMENT PRIMARY KEY,
    payment_gateway ENUM('Credit Card', 'Debit Card', 'PayPal', 'Bank Transfer') NOT NULL,
    type VARCHAR(50) NOT NULL,
    amount_paid DECIMAL(10, 2) NOT NULL,
    status VARCHAR(20) NOT NULL,
    last_update TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    transaction_date DATE NOT NULL
);

-- Create the Invoice table
CREATE TABLE Invoice (
    Invoice_id INT AUTO_INCREMENT PRIMARY KEY,
    cust_id INT,
    branch_id INT,
    subscription_id INT,
    payment_id INT,
    amount_paid DECIMAL(10, 2) NOT NULL,
    amount_due DECIMAL(10, 2) NOT NULL,
    tax DECIMAL(10, 2) NOT NULL,
    invoice_issue_date DATE NOT NULL,
    due_date DATE NOT NULL,
    FOREIGN KEY (cust_id) REFERENCES Customer(cust_id),
    FOREIGN KEY (branch_id) REFERENCES Supplier(branch_id),
    FOREIGN KEY (subscription_id) REFERENCES Subscription(subscription_id),
    FOREIGN KEY (payment_id) REFERENCES Payment(pay_id)
);