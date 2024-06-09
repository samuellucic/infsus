CREATE TABLE  Notification (
    id          BIGINT          IDENTITY PRIMARY KEY,
    userId      NVARCHAR(100)   NOT NULL,
    message     NVARCHAR(1000),
    topic       NVARCHAR(100),
    delivered   INT
);