CREATE TABLE Product (
    p_ID INT PRIMARY KEY IDENTITY(1,1),
    p_Name NVARCHAR(255) NOT NULL,
    p_Effort FLOAT NOT NULL
);

CREATE TABLE Employee (
    e_ID VARCHAR(10) PRIMARY KEY,
    e_Name NVARCHAR(255) NOT NULL,
    e_Level VARCHAR(2) CHECK (e_Level IN ('F1', 'F2', 'F3')),
    e_HourlyRate INT NOT NULL
);

INSERT INTO Employee (e_Name, e_Level)
VALUES (N'Nguyễn Văn X ', 'F1');

INSERT INTO Employee (e_Name, e_Level)
VALUES (N'Trần Thế Mỹ', 'F2');

INSERT INTO Employee (e_Name, e_Level)
VALUES (N'Bao Chửng', 'F3');



