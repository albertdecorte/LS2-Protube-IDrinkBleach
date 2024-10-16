CREATE TABLE IF NOT EXISTS app_user (
    userName VARCHAR(255),
    gmail VARCHAR(255) PRIMARY KEY,
    password VARCHAR(255)
    );

INSERT INTO User (userName, gmail, password) VALUES ('John Doe', 'john.doe@gmail.com', 'password123');
INSERT INTO User (userName, gmail, password) VALUES ('Jane Smith', 'jane.smith@gmail.com', 'password456');
INSERT INTO User (userName, gmail, password) VALUES ('Alice Johnson', 'alice.johnson@gmail.com', 'password789');
INSERT INTO User (userName, gmail, password) VALUES ('Bob Brown', 'bob.brown@gmail.com', 'password101');
INSERT INTO User (userName, gmail, password) VALUES ('Charlie Davis', 'charlie.davis@gmail.com', 'password102');

