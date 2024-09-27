CREATE FUNCTION GetNextEmployeeID()
RETURNS VARCHAR(10)
AS
BEGIN
    DECLARE @NextID VARCHAR(10);
    DECLARE @CurrentMaxID VARCHAR(10);

    SELECT @CurrentMaxID = MAX(e_ID) FROM Employee WHERE e_ID LIKE 'CN%';

    IF @CurrentMaxID IS NULL
        SET @NextID = 'CN001';
    ELSE
        SET @NextID = 'CN' + RIGHT('000' + CAST(CAST(SUBSTRING(@CurrentMaxID, 3, LEN(@CurrentMaxID) - 2) AS INT) + 1 AS VARCHAR), 3);

    RETURN @NextID;
END;


CREATE TRIGGER trg_SetHourlyRate
ON Employee
INSTEAD OF INSERT
AS
BEGIN
    INSERT INTO Employee (e_ID, e_Name, e_Level, e_HourlyRate)
    SELECT 
        dbo.GetNextEmployeeID(), 
        e_Name, 
        e_Level, 
        CASE e_Level
            WHEN 'F1' THEN 30000
            WHEN 'F2' THEN 40000
            WHEN 'F3' THEN 50000
        END
    FROM inserted;
END;
