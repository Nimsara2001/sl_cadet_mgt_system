DROP DATABASE IF EXISTS cadetMgtSysDB;
CREATE DATABASE IF NOT EXISTS cadetMgtSysDB;
SHOW DATABASES;
USE cadetMgtSysDB;
#----------------------------------------------------

DROP TABLE IF EXISTS AppUser;
CREATE TABLE IF NOT EXISTS AppUser(
    userName VARCHAR(255),
    password VARCHAR(255),
    CONSTRAINT PRIMARY KEY (userName)
);
#----------------------------------------------------

DROP TABLE IF EXISTS Battalion;
CREATE TABLE IF NOT EXISTS Battalion(
    btCode VARCHAR(25),
    name VARCHAR(50),
    address VARCHAR(255),
    contactNo VARCHAR(12),
    eMail VARCHAR(255),
    description MEDIUMTEXT,
    specialNote MEDIUMTEXT,
    CONSTRAINT PRIMARY KEY (btCode)
);

INSERT INTO Battalion VALUES("15Ncc","","","","","","");
SHOW TABLES;
DESCRIBE Battalion;



DROP TABLE IF EXISTS Zone;
CREATE TABLE IF NOT EXISTS Zone(
    zoneCode VARCHAR(50),
    zoneName VARCHAR(200),
    totalNumOfScl INT(5) DEFAULT '0',
    btCode VARCHAR(25),
    CONSTRAINT PRIMARY KEY (zoneCode),
    CONSTRAINT FOREIGN KEY (btCode) REFERENCES Battalion(btCode) ON DELETE CASCADE ON UPDATE CASCADE

);

INSERT INTO Zone VALUES("notAssigned","notAssigned",0,"15Ncc");
SHOW TABLES;
DESCRIBE Zone;


DROP TABLE IF EXISTS Company;
CREATE TABLE IF NOT EXISTS Company(
    companyCode VARCHAR(50),
    CONSTRAINT PRIMARY KEY(companyCode)
);
SHOW TABLES;
DESCRIBE Company;



DROP TABLE IF EXISTS School;
CREATE TABLE IF NOT EXISTS School(
    sclCode VARCHAR(50),
    name VARCHAR(255),
    address VARCHAR(255),
    contactNo VARCHAR(15),
    eMail VARCHAR(255),
    noOfPlatoon INT(5),
    zoneCode VARCHAR(50)DEFAULT "NoZone",
    CONSTRAINT PRIMARY KEY (sclCode),
    CONSTRAINT FOREIGN KEY (zoneCode) REFERENCES Zone(zoneCode) ON DELETE CASCADE ON UPDATE CASCADE
);

SHOW TABLES;
DESCRIBE School;


DROP TABLE IF EXISTS Officer;
CREATE TABLE IF NOT EXISTS Officer(
    offRegNo VARCHAR(50),
    role VARCHAR(30),
    offRank VARCHAR(20),
    serviceType VARCHAR(100),
    nameInFull VARCHAR(255),
    nameWithInit VARCHAR(255),
    btCode VARCHAR(50),
    sclCode VARCHAR(50),
    dateOfBirth DATE,
    nicNo VARCHAR(30),
    eMail VARCHAR(100),
    address VARCHAR(255),
    contactNo VARCHAR(15),
    image MEDIUMBLOB,
    CONSTRAINT PRIMARY KEY (offRegNo),
    CONSTRAINT FOREIGN KEY (btCode) REFERENCES Battalion(btCode) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT FOREIGN KEY (sclCode) REFERENCES School(sclCode) ON DELETE CASCADE ON UPDATE CASCADE
);

SHOW TABLES;
DESCRIBE Officer;



DROP TABLE IF EXISTS Platoon;
CREATE TABLE IF NOT EXISTS Platoon(
    platoonCode VARCHAR(50),
    gender VARCHAR(10),/*Boys or Girls*/
    type VARCHAR(100),/* defence, police, band*/
    category VARCHAR(100),/*jonior or senior*/
    numOfCadets INT(10),
    sclCode VARCHAR(50),
    companyCode VARCHAR(50),
    CONSTRAINT PRIMARY KEY(platoonCode),
    CONSTRAINT FOREIGN KEY (sclCode) REFERENCES School(sclCode) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT FOREIGN KEY (companyCode) REFERENCES Company(companyCode) ON DELETE CASCADE ON UPDATE CASCADE

);

SHOW TABLES;
DESCRIBE Platoon;



DROP TABLE IF EXISTS PlatoonAchivement;
CREATE TABLE IF NOT EXISTS PlatoonAchivement(
    platoonCode VARCHAR(50),
    achivementDescription MEDIUMTEXT,
    CONSTRAINT FOREIGN KEY (platoonCode) REFERENCES Platoon(platoonCode) ON DELETE CASCADE ON UPDATE CASCADE
);

SHOW TABLES;
DESCRIBE PlatoonAchivement;



DROP TABLE IF EXISTS Cadet;
CREATE TABLE IF NOT EXISTS Cadet(
    cdtRegNo VARCHAR(50),
    cdtRank VARCHAR(20),
    status VARCHAR(50),
    platoonCode VARCHAR(50),
    nameInFull VARCHAR(255),
    nameWithInit VARCHAR(255),
    dateOfBirth DATE,
    nicNo VARCHAR(30),
    eMail VARCHAR(100),
    address VARCHAR(255),
    contactNo VARCHAR(15),
    image MEDIUMBLOB,
    CONSTRAINT PRIMARY KEY (cdtRegNo),
    CONSTRAINT FOREIGN KEY (platoonCode) REFERENCES Platoon(platoonCode) ON DELETE CASCADE ON UPDATE CASCADE
);

SHOW TABLES;
DESCRIBE Cadet;


DROP TABLE IF EXISTS CadetNCO;
CREATE TABLE IF NOT EXISTS CadetNCO(
    cdtRegNo VARCHAR(50),
    cdtRank VARCHAR(50),
    nameWithInit VARCHAR(255),
    companyCode VARCHAR(50),
    CONSTRAINT PRIMARY KEY (cdtRegNo,companyCode),
    CONSTRAINT FOREIGN KEY (cdtRegNo) REFERENCES Cadet(cdtRegNo) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT FOREIGN KEY (companyCode) REFERENCES Company(companyCode) ON DELETE CASCADE ON UPDATE CASCADE
);

SHOW TABLES;
DESCRIBE CadetNCO;


DROP TABLE IF EXISTS CadetAchivement;
CREATE TABLE IF NOT EXISTS CadetAchivement(
    cdtRegNo VARCHAR(50),
    achivementDescription MEDIUMTEXT,
    CONSTRAINT FOREIGN KEY (cdtRegNo) REFERENCES Cadet(cdtRegNo) ON DELETE CASCADE ON UPDATE CASCADE
);

SHOW TABLES;
DESCRIBE CadetAchivement;



DROP TABLE IF EXISTS Camping;
CREATE TABLE IF NOT EXISTS Camping(
    campCode VARCHAR(50),
    year VARCHAR(10),
    type VARCHAR(255),
    campSeason VARCHAR (50),
    startDate DATE,
    endDate DATE,
    description VARCHAR(255),
    trainingCentre VARCHAR(255),
    duration VARCHAR(20),
    CONSTRAINT PRIMARY KEY (campCode)
);

SHOW TABLES;
DESCRIBE Camping;



DROP TABLE IF EXISTS PlatoonCamping; 
CREATE TABLE IF NOT EXISTS PlatoonCamping(
    platoonCode VARCHAR(50),
    campCode VARCHAR(50),
    platoonCommander VARCHAR(50),
    CONSTRAINT PRIMARY KEY (platoonCode,campCode),
    CONSTRAINT FOREIGN KEY (platoonCode) REFERENCES Platoon(platoonCode) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT FOREIGN KEY (campCode) REFERENCES Camping(campCode) ON DELETE CASCADE ON UPDATE CASCADE
);

SHOW TABLES;
DESCRIBE PlatoonCamping;


DROP TABLE IF EXISTS CashReport;
CREATE TABLE IF NOT EXISTS CashReport(
    year VARCHAR(10),
    month VARCHAR(10),
    monthAllowance DOUBLE,
    monthCost DOUBLE,
    monthSaving DOUBLE,
    btCode VARCHAR(50),
    CONSTRAINT PRIMARY KEY (year,month),
    CONSTRAINT FOREIGN KEY (btCode) REFERENCES Battalion(btCode) ON DELETE CASCADE ON UPDATE CASCADE
);

SHOW TABLES;
DESCRIBE CashReport;



DROP TABLE IF EXISTS SpentDescription;
CREATE TABLE IF NOT EXISTS SpentDescription(
    year VARCHAR(10),
    month VARCHAR(10),
    sNo INT(5),
    description VARCHAR(255),
    cost DOUBLE,
    CONSTRAINT PRIMARY KEY (year,month,sNo),
    CONSTRAINT FOREIGN KEY (year,month) REFERENCES CashReport(year,month) ON DELETE CASCADE ON UPDATE CASCADE
    
);

SHOW TABLES;
DESCRIBE SpentDescription;

SHOW TABLES;