CREATE TABLE PUBLIC.FILE_INFO
(ID INTEGER NOT NULL,
FILE_NAME VARCHAR2(100),
SIZE NUMBER(10),
CONTENT_TYPE VARCHAR2(20),
IMAGE BLOB(7483647),
CREATED_TIME TIMESTAMP,
PRIMARY KEY (ID));