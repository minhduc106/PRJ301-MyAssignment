-- Create Department table
CREATE TABLE Department (
    did INT PRIMARY KEY,
    dname NVARCHAR(100),
    dtype NVARCHAR(50)
);

-- Create Employee table with foreign key to Department
CREATE TABLE Employee (
    eid INT PRIMARY KEY,
    ename NVARCHAR(100),
    salaryLevel NVARCHAR(10),
    did INT,
    FOREIGN KEY (did) REFERENCES Department(did) ON DELETE CASCADE ON UPDATE CASCADE
);

-- Create User table with foreign key to Employee
CREATE TABLE User (
    username NVARCHAR(50) PRIMARY KEY,
    password NVARCHAR(50) NOT NULL,
    displayName NVARCHAR(100),
    eid INT,
    FOREIGN KEY (eid) REFERENCES Employee(eid) ON DELETE CASCADE ON UPDATE CASCADE
);

-- Create Product table
CREATE TABLE Product (
    pid INT PRIMARY KEY,
    pname NVARCHAR(100)
);

-- Create Plan table with foreign key to Department
CREATE TABLE Plan (
    plid INT PRIMARY KEY,
    startd DATE,
    endd DATE,
    did INT,
    FOREIGN KEY (did) REFERENCES Department(did) ON DELETE CASCADE ON UPDATE CASCADE
);

-- Create PlanCampaign table with foreign keys to Plan and Product
CREATE TABLE PlanCampaign (
    canid INT PRIMARY KEY,
    plid INT,
    pid INT,
    quantity INT,
    FOREIGN KEY (plid) REFERENCES Plan(plid) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (pid) REFERENCES Product(pid) ON DELETE CASCADE ON UPDATE CASCADE
);

-- Create ScheduleCampaign table with foreign key to PlanCampaign
CREATE TABLE ScheduleCampaign (
    scid INT PRIMARY KEY,
    canid INT,
    date DATE,
    shift NVARCHAR(10),
    quantity INT,
    FOREIGN KEY (canid) REFERENCES PlanCampaign(canid) ON DELETE CASCADE ON UPDATE CASCADE
);

-- Create WorkerSchedule table with foreign keys to ScheduleCampaign and Employee
CREATE TABLE WorkerSchedule (
    wsid INT PRIMARY KEY,
    scid INT,
    eid INT,
    quantity INT,
    FOREIGN KEY (scid) REFERENCES ScheduleCampaign(scid) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (eid) REFERENCES Employee(eid) ON DELETE CASCADE ON UPDATE CASCADE
);

-- Create Attendance table with foreign key to WorkerSchedule
CREATE TABLE Attendance (
    aid INT PRIMARY KEY,
    wsid INT,
    quantity INT,
    alpha FLOAT,
    FOREIGN KEY (wsid) REFERENCES WorkerSchedule(wsid) ON DELETE CASCADE ON UPDATE CASCADE
);