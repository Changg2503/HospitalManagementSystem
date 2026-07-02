-- ==========================================
-- DROP & CREATE DATABASE
-- ==========================================

USE master;
GO

IF DB_ID('IS2004_HOSPITALMANAGEMENT') IS NOT NULL
BEGIN
    ALTER DATABASE IS2004_HOSPITALMANAGEMENT
    SET SINGLE_USER
    WITH ROLLBACK IMMEDIATE;

    DROP DATABASE IS2004_HOSPITALMANAGEMENT;
END
GO

CREATE DATABASE IS2004_HOSPITALMANAGEMENT;
GO

USE IS2004_HOSPITALMANAGEMENT;
GO

-- ==========================================
-- ACCOUNT
-- ==========================================

CREATE TABLE Account (
    AccountID INT IDENTITY(1,1) PRIMARY KEY,
    Username VARCHAR(50) NOT NULL UNIQUE,
    Password VARCHAR(255) NOT NULL,
    Role VARCHAR(20) NOT NULL,
    Status BIT NOT NULL DEFAULT 1
);

-- ==========================================
-- DEPARTMENT
-- ==========================================

CREATE TABLE Department (
    DepartmentID INT IDENTITY(1,1) PRIMARY KEY,
    DepartmentName VARCHAR(100) NOT NULL UNIQUE
);

-- ==========================================
-- PATIENT
-- ==========================================

CREATE TABLE Patient (
    PatientID INT IDENTITY(1,1) PRIMARY KEY,

    AccountID INT UNIQUE,

    FullName VARCHAR(100) NOT NULL,
    Gender VARCHAR(10),

    DOB DATE,

    Phone VARCHAR(15) UNIQUE,
    Email VARCHAR(100) UNIQUE,

    Address VARCHAR(255),

    CONSTRAINT FK_Patient_Account
        FOREIGN KEY (AccountID)
        REFERENCES Account(AccountID)
);

-- ==========================================
-- DOCTOR
-- ==========================================

CREATE TABLE Doctor (
    DoctorID INT IDENTITY(1,1) PRIMARY KEY,

    FullName VARCHAR(100) NOT NULL,

    Specialization VARCHAR(100),

    Phone VARCHAR(15) UNIQUE,

    DepartmentID INT NOT NULL,

    CONSTRAINT FK_Doctor_Department
        FOREIGN KEY (DepartmentID)
        REFERENCES Department(DepartmentID)
);

-- ==========================================
-- APPOINTMENT
-- ==========================================

CREATE TABLE Appointment (

    AppointmentID INT IDENTITY(1,1) PRIMARY KEY,

    PatientID INT NOT NULL,

    DoctorID INT NOT NULL,

    AppointmentDate DATETIME NOT NULL,

    Status VARCHAR(30) NOT NULL DEFAULT 'Pending',

    CONSTRAINT FK_Appointment_Patient
        FOREIGN KEY (PatientID)
        REFERENCES Patient(PatientID),

    CONSTRAINT FK_Appointment_Doctor
        FOREIGN KEY (DoctorID)
        REFERENCES Doctor(DoctorID)
);

-- ==========================================
-- MEDICAL RECORD
-- ==========================================

CREATE TABLE MedicalRecord (

    RecordID INT IDENTITY(1,1) PRIMARY KEY,

    PatientID INT NOT NULL,

    DoctorID INT NOT NULL,

    Diagnosis NVARCHAR(MAX),

    Treatment NVARCHAR(MAX),

    CreatedDate DATETIME NOT NULL DEFAULT GETDATE(),

    CONSTRAINT FK_Record_Patient
        FOREIGN KEY (PatientID)
        REFERENCES Patient(PatientID),

    CONSTRAINT FK_Record_Doctor
        FOREIGN KEY (DoctorID)
        REFERENCES Doctor(DoctorID)
);

-- ==========================================
-- PRESCRIPTION
-- ==========================================

CREATE TABLE Prescription (

    PrescriptionID INT IDENTITY(1,1) PRIMARY KEY,

    RecordID INT NOT NULL,

    Medicine VARCHAR(200) NOT NULL,

    Quantity INT NOT NULL CHECK(Quantity > 0),

    CONSTRAINT FK_Prescription_Record
        FOREIGN KEY (RecordID)
        REFERENCES MedicalRecord(RecordID)
);

-- ==========================================
-- INVOICE
-- ==========================================

CREATE TABLE Invoice (

    InvoiceID INT IDENTITY(1,1) PRIMARY KEY,

    PatientID INT NOT NULL,

    Amount DECIMAL(12,2) NOT NULL CHECK(Amount >= 0),

    PaymentDate DATETIME NOT NULL DEFAULT GETDATE(),

    CONSTRAINT FK_Invoice_Patient
        FOREIGN KEY (PatientID)
        REFERENCES Patient(PatientID)
);

GO


-- ==========================================
-- INSERT ACCOUNT
-- ==========================================

INSERT INTO Account (Username, Password, Role, Status)
VALUES

-- ======================
-- ADMIN
-- ======================

('admin01','admin123','Admin',1),

-- ======================
-- DOCTOR
-- ======================

('doctor01','doctor123','Doctor',1),
('doctor02','doctor123','Doctor',1),
('doctor03','doctor123','Doctor',1),
('doctor04','doctor123','Doctor',1),
('doctor05','doctor123','Doctor',1),
('doctor06','doctor123','Doctor',1),
('doctor07','doctor123','Doctor',1),
('doctor08','doctor123','Doctor',1),
('doctor09','doctor123','Doctor',1),
('doctor10','doctor123','Doctor',1),

-- ======================
-- PATIENT
-- ======================

('patient01','patient123','Patient',1),
('patient02','patient123','Patient',1),
('patient03','patient123','Patient',1),
('patient04','patient123','Patient',1),
('patient05','patient123','Patient',1),
('patient06','patient123','Patient',1),
('patient07','patient123','Patient',1),
('patient08','patient123','Patient',1),
('patient09','patient123','Patient',1),
('patient10','patient123','Patient',1),
('patient11','patient123','Patient',1),
('patient12','patient123','Patient',1),
('patient13','patient123','Patient',1),
('patient14','patient123','Patient',1),
('patient15','patient123','Patient',1),
('patient16','patient123','Patient',1),
('patient17','patient123','Patient',1),
('patient18','patient123','Patient',1),
('patient19','patient123','Patient',1),
('patient20','patient123','Patient',1);


-- ==========================================
-- INSERT DEPARTMENT
-- ==========================================

INSERT INTO Department (DepartmentName)
VALUES
('Cardiology'),
('Neurology'),
('Orthopedics'),
('Pediatrics'),
('Dermatology'),
('ENT'),
('Emergency'),
('General Surgery'),
('Internal Medicine'),
('Ophthalmology');


-- ==========================================
-- INSERT PATIENT
-- ==========================================

INSERT INTO Patient
(AccountID, FullName, Gender, DOB, Phone, Email, Address)
VALUES

(12,'Nguyen Van An','Male','1998-03-15','0911111101','nguyenvanan01@gmail.com','Ha Noi'),

(13,'Tran Thi Mai','Female','1999-07-20','0911111102','tranthimai02@gmail.com','Hai Phong'),

(14,'Le Quoc Bao','Male','1997-11-10','0911111103','lequocbao03@gmail.com','Da Nang'),

(15,'Pham Thu Ha','Female','2001-01-25','0911111104','phamthuha04@gmail.com','Nam Dinh'),

(16,'Hoang Minh Duc','Male','1995-05-12','0911111105','hoangminhduc05@gmail.com','Ho Chi Minh'),

(17,'Nguyen Thi Lan','Female','1996-09-17','0911111106','nguyenthilan06@gmail.com','Can Tho'),

(18,'Tran Quoc Huy','Male','1994-12-30','0911111107','tranquochuy07@gmail.com','Quang Ninh'),

(19,'Le Thi Hoa','Female','2000-06-09','0911111108','lethihoa08@gmail.com','Hai Duong'),

(20,'Pham Van Long','Male','1998-08-18','0911111109','phamvanlong09@gmail.com','Bac Ninh'),

(21,'Do Thi Huong','Female','1999-02-14','0911111110','dothihuong10@gmail.com','Thai Binh'),

(22,'Vo Minh Quan','Male','1993-10-11','0911111111','vominhquan11@gmail.com','Hue'),

(23,'Bui Thi Yen','Female','1997-04-07','0911111112','buithiyen12@gmail.com','Nghe An'),

(24,'Dang Hoang Nam','Male','1996-01-19','0911111113','danghoangnam13@gmail.com','Thanh Hoa'),

(25,'Nguyen Bao Chau','Female','2002-07-28','0911111114','nguyenbaochau14@gmail.com','Vinh Phuc'),

(26,'Tran Minh Khoa','Male','1995-03-06','0911111115','tranminhkhoa15@gmail.com','Ha Nam'),

(27,'Le Thanh Tung','Male','1998-09-21','0911111116','lethanhtung16@gmail.com','Ninh Binh'),

(28,'Pham Ngoc Anh','Female','2001-05-13','0911111117','phamngocanh17@gmail.com','Bac Giang'),

(29,'Hoang Gia Bao','Male','1999-11-29','0911111118','hoanggiabao18@gmail.com','Lao Cai'),

(30,'Nguyen Thu Trang','Female','1997-08-08','0911111119','nguyenthutrang19@gmail.com','Ha Noi'),

(31,'Tran Duc Manh','Male','1994-02-26','0911111120','tranducmanh20@gmail.com','Hai Phong');


-- ==========================================
-- INSERT DOCTOR
-- ==========================================

INSERT INTO Doctor
(FullName, Specialization, Phone, DepartmentID)
VALUES

-- Cardiology
('Nguyen Van Hung','Cardiologist','0901000001',1),
('Tran Minh Duc','Cardiologist','0901000002',1),

-- Neurology
('Le Quang Huy','Neurologist','0901000003',2),
('Pham Thanh Hai','Neurologist','0901000004',2),

-- Orthopedics
('Hoang Anh Tuan','Orthopedic Surgeon','0901000005',3),
('Do Thi Lan','Orthopedic Surgeon','0901000006',3),

-- Pediatrics
('Nguyen Thi Hoa','Pediatrician','0901000007',4),
('Tran Van Nam','Pediatrician','0901000008',4),

-- Dermatology
('Le Minh Chau','Dermatologist','0901000009',5),
('Pham Thi Nga','Dermatologist','0901000010',5),

-- ENT
('Vo Quoc Bao','ENT Specialist','0901000011',6),
('Bui Thanh Son','ENT Specialist','0901000012',6),

-- Emergency
('Dang Hoang Long','Emergency Physician','0901000013',7),
('Nguyen Thanh Binh','Emergency Physician','0901000014',7),

-- General Surgery
('Tran Quoc Viet','General Surgeon','0901000015',8),
('Le Thanh Tung','General Surgeon','0901000016',8),

-- Internal Medicine
('Pham Minh Anh','Internal Medicine Specialist','0901000017',9),
('Hoang Van Phuc','Internal Medicine Specialist','0901000018',9),

-- Ophthalmology
('Do Minh Tuan','Ophthalmologist','0901000019',10),
('Nguyen Bao Chau','Ophthalmologist','0901000020',10);


-- ==========================================
-- INSERT APPOINTMENT
-- ==========================================

INSERT INTO Appointment
(PatientID, DoctorID, AppointmentDate, Status)
VALUES

(1,1,'2026-07-01 08:00:00','Completed'),
(2,2,'2026-07-01 09:00:00','Completed'),
(3,3,'2026-07-01 10:00:00','Pending'),
(4,4,'2026-07-01 11:00:00','Cancelled'),
(5,5,'2026-07-02 08:00:00','Completed'),

(6,6,'2026-07-02 09:00:00','Pending'),
(7,7,'2026-07-02 10:00:00','Completed'),
(8,8,'2026-07-02 11:00:00','Completed'),
(9,9,'2026-07-03 08:00:00','Pending'),
(10,10,'2026-07-03 09:00:00','Completed'),

(11,11,'2026-07-03 10:00:00','Cancelled'),
(12,12,'2026-07-03 11:00:00','Completed'),
(13,13,'2026-07-04 08:00:00','Completed'),
(14,14,'2026-07-04 09:00:00','Pending'),
(15,15,'2026-07-04 10:00:00','Completed'),

(16,16,'2026-07-04 11:00:00','Completed'),
(17,17,'2026-07-05 08:00:00','Pending'),
(18,18,'2026-07-05 09:00:00','Completed'),
(19,19,'2026-07-05 10:00:00','Completed'),
(20,20,'2026-07-05 11:00:00','Cancelled'),

(1,6,'2026-07-06 08:30:00','Pending'),
(2,8,'2026-07-06 09:30:00','Completed'),
(3,10,'2026-07-06 10:30:00','Completed'),
(4,12,'2026-07-06 11:30:00','Pending'),
(5,14,'2026-07-07 08:00:00','Completed'),

(6,16,'2026-07-07 09:00:00','Completed'),
(7,18,'2026-07-07 10:00:00','Cancelled'),
(8,20,'2026-07-07 11:00:00','Pending'),
(9,2,'2026-07-08 08:30:00','Completed'),
(10,4,'2026-07-08 09:30:00','Pending'),

(11,5,'2026-07-09 08:00:00','Completed'),
(12,7,'2026-07-09 09:00:00','Completed'),
(13,9,'2026-07-09 10:00:00','Pending'),
(14,11,'2026-07-09 11:00:00','Completed'),
(15,13,'2026-07-10 08:00:00','Cancelled'),

(16,15,'2026-07-10 09:00:00','Completed'),
(17,17,'2026-07-10 10:00:00','Completed'),
(18,19,'2026-07-10 11:00:00','Pending'),
(19,1,'2026-07-11 08:30:00','Completed'),
(20,3,'2026-07-11 09:30:00','Completed');


-- ==========================================
-- INSERT MEDICAL RECORD
-- ==========================================

INSERT INTO MedicalRecord
(PatientID, DoctorID, Diagnosis, Treatment)
VALUES

(1,1,'Hypertension','Prescribed antihypertensive medication and lifestyle modification'),
(2,2,'Coronary Artery Disease','Medication and ECG monitoring'),
(3,3,'Migraine','Pain relief medication'),
(4,4,'Epilepsy','Anticonvulsant therapy'),
(5,5,'Fractured Arm','Cast immobilization'),

(6,6,'Knee Ligament Injury','Physical therapy'),
(7,7,'Seasonal Influenza','Rest and antiviral medication'),
(8,8,'Bronchitis','Antibiotics and cough syrup'),
(9,9,'Eczema','Topical corticosteroid cream'),
(10,10,'Acne Vulgaris','Acne treatment medication'),

(11,11,'Sinusitis','Antibiotics'),
(12,12,'Otitis Media','Ear drops'),
(13,13,'Appendicitis','Appendectomy surgery'),
(14,14,'Gallstones','Laparoscopic surgery'),
(15,15,'Food Poisoning','IV fluids'),

(16,16,'Traffic Accident Injury','Emergency treatment'),
(17,17,'Type 2 Diabetes','Insulin therapy'),
(18,18,'Hypertension','Blood pressure medication'),
(19,19,'Cataract','Eye surgery'),
(20,20,'Conjunctivitis','Eye drops'),

(1,6,'Common Cold','Medication and rest'),
(2,8,'Pneumonia','Hospitalization'),
(3,10,'Skin Allergy','Antihistamine'),
(4,12,'Hearing Loss','Audiometry examination'),
(5,14,'Gastric Ulcer','Medication'),

(6,16,'Heat Stroke','Emergency care'),
(7,18,'High Cholesterol','Diet control'),
(8,20,'Dry Eye Syndrome','Artificial tears'),
(9,2,'Chest Pain','Cardiac examination'),
(10,4,'Chronic Headache','MRI scan'),

(11,5,'Shoulder Dislocation','Joint reduction'),
(12,7,'Asthma','Bronchodilator'),
(13,9,'Dermatitis','Skin cream'),
(14,11,'Tonsillitis','Antibiotics'),
(15,13,'Hernia','Surgery'),

(16,15,'Food Allergy','Medication'),
(17,17,'Diabetes Follow-up','Continue insulin'),
(18,19,'Eye Infection','Eye drops'),
(19,1,'Hypertension Follow-up','Medication adjustment'),
(20,3,'Migraine Follow-up','Pain management');


-- ==========================================
-- INSERT PRESCRIPTION
-- ==========================================

INSERT INTO Prescription (RecordID, Medicine, Quantity)
VALUES

(1,'Amlodipine 5mg',30),
(1,'Aspirin 81mg',30),

(2,'Atorvastatin 20mg',30),
(2,'Clopidogrel 75mg',30),

(3,'Paracetamol 500mg',20),
(3,'Ibuprofen 400mg',15),

(4,'Sodium Valproate 500mg',30),

(5,'Calcium Tablets',60),
(5,'Vitamin D3',30),

(6,'Diclofenac 50mg',20),

(7,'Oseltamivir',10),
(7,'Vitamin C 500mg',30),

(8,'Amoxicillin 500mg',21),
(8,'Ambroxol Syrup',1),

(9,'Hydrocortisone Cream',1),

(10,'Benzoyl Peroxide Gel',1),

(11,'Augmentin 625mg',14),

(12,'Ciprofloxacin Ear Drops',1),

(13,'Ceftriaxone Injection',5),
(13,'Paracetamol 500mg',20),

(14,'Omeprazole 20mg',30),

(15,'ORS',10),
(15,'Metronidazole 500mg',15),

(16,'Diclofenac Injection',5),

(17,'Insulin',5),
(17,'Metformin 500mg',60),

(18,'Losartan 50mg',30),

(19,'Artificial Tears',2),

(20,'Chloramphenicol Eye Drops',1),

(21,'Paracetamol 500mg',15),
(21,'Vitamin C',20),

(22,'Azithromycin 500mg',6),
(22,'Cefixime 200mg',10),

(23,'Cetirizine 10mg',20),

(24,'Vitamin B Complex',30),

(25,'Pantoprazole 40mg',30),

(26,'Normal Saline',3),

(27,'Rosuvastatin 10mg',30),

(28,'Artificial Tears',2),

(29,'Nitroglycerin',20),
(29,'Aspirin 81mg',30),

(30,'Sumatriptan',10),

(31,'Diclofenac Gel',2),

(32,'Salbutamol Inhaler',1),

(33,'Hydrocortisone Cream',2),

(34,'Amoxicillin 500mg',21),

(35,'Ceftriaxone Injection',5),

(36,'Loratadine 10mg',20),

(37,'Insulin',5),
(37,'Metformin 500mg',60),

(38,'Levofloxacin Eye Drops',1),

(39,'Amlodipine 5mg',30),
(39,'Atorvastatin 20mg',30),

(40,'Paracetamol 500mg',20),
(40,'Ibuprofen 400mg',15),
(40,'Vitamin B Complex',30);


-- ==========================================
-- INSERT INVOICE
-- ==========================================

INSERT INTO Invoice
(PatientID, Amount, PaymentDate)
VALUES

(1,350000,'2026-07-01 09:30:00'),
(2,450000,'2026-07-01 10:20:00'),
(3,2200000,'2026-07-01 14:00:00'),
(4,500000,'2026-07-01 15:30:00'),
(5,1800000,'2026-07-02 09:00:00'),

(6,650000,'2026-07-02 10:00:00'),
(7,300000,'2026-07-02 11:00:00'),
(8,420000,'2026-07-02 15:00:00'),
(9,550000,'2026-07-03 09:30:00'),
(10,480000,'2026-07-03 10:30:00'),

(11,350000,'2026-07-03 11:20:00'),
(12,390000,'2026-07-03 14:00:00'),
(13,4500000,'2026-07-04 10:30:00'),
(14,3800000,'2026-07-04 14:00:00'),
(15,850000,'2026-07-04 15:20:00'),

(16,1200000,'2026-07-05 09:00:00'),
(17,780000,'2026-07-05 10:00:00'),
(18,520000,'2026-07-05 11:00:00'),
(19,2800000,'2026-07-05 14:30:00'),
(20,450000,'2026-07-05 15:30:00'),

(1,250000,'2026-07-06 09:00:00'),
(2,1800000,'2026-07-06 14:20:00'),
(3,420000,'2026-07-06 15:30:00'),
(4,600000,'2026-07-06 16:20:00'),
(5,700000,'2026-07-07 09:10:00'),

(6,950000,'2026-07-07 10:00:00'),
(7,430000,'2026-07-07 11:00:00'),
(8,320000,'2026-07-07 14:30:00'),
(9,520000,'2026-07-08 09:00:00'),
(10,680000,'2026-07-08 10:20:00'),

(11,550000,'2026-07-08 11:40:00'),
(12,400000,'2026-07-08 14:10:00'),
(13,950000,'2026-07-09 09:30:00'),
(14,620000,'2026-07-09 10:30:00'),
(15,2700000,'2026-07-09 15:00:00'),

(16,380000,'2026-07-10 09:00:00'),
(17,820000,'2026-07-10 10:15:00'),
(18,480000,'2026-07-10 11:20:00'),
(19,550000,'2026-07-10 14:00:00'),
(20,350000,'2026-07-10 15:10:00');