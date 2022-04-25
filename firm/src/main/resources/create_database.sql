CREATE TABLE DEPARTMENTS
(
    ID   INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
    NAME VARCHAR2(20)
);
CREATE TABLE EMPLOYEES(
    ID          INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
    FIRST_NAME  VARCHAR2(20),
    LAST_NAME   VARCHAR2(20),
    FATHER_NAME VARCHAR2(20),
    POSITION    VARCHAR2(20),
    SALARY      NUMBER(18, 2)
);
CREATE TABLE PROJECTS
(
    ID            INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
    NAME          VARCHAR2(200),
    COST          NUMBER(18, 2),
    DEPARTMENT_ID INTEGER,
    DATE_BEG      DATE,
    DATE_END      DATE,
    DATE_END_REAL DATE,
    FOREIGN KEY (DEPARTMENT_ID) REFERENCES DEPARTMENTS (ID)
);
CREATE TABLE DEPARTMENTS_EMPLOYEES
(
    ID            INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
    EMPLOYEE_ID   INTEGER,
    DEPARTMENT_ID INTEGER,
    FOREIGN KEY (EMPLOYEE_ID) REFERENCES EMPLOYEES (ID),
    FOREIGN KEY (DEPARTMENT_ID) REFERENCES DEPARTMENTS (ID)
);