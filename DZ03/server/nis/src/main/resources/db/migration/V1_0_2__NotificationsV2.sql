ALTER TABLE Notification
    ADD pid NVARCHAR(1000);

ALTER TABLE Notification
    ADD CONSTRAINT UK_Notification UNIQUE (pid, userId, topic);