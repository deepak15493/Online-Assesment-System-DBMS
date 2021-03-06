CREATE TABLE ANSWERSNEW 
(
  A_ID NUMBER(38, 0) NOT NULL 
, Q_ID NUMBER(38, 0) NOT NULL 
, ANSWER VARCHAR2(20 BYTE) 
, IS_CORRECT NUMBER(38, 0) NOT NULL 
, CONSTRAINT SYS_C00425869 PRIMARY KEY 
  (
    A_ID 
  , Q_ID 
  )
  USING INDEX 
  (
      CREATE UNIQUE INDEX SYS_C00425869 ON ANSWERSNEW (A_ID ASC, Q_ID ASC) 
      LOGGING 
      TABLESPACE USERS 
      PCTFREE 10 
      INITRANS 2 
      STORAGE 
      ( 
        INITIAL 65536 
        NEXT 1048576 
        MINEXTENTS 1 
        MAXEXTENTS UNLIMITED 
        BUFFER_POOL DEFAULT 
      ) 
      NOPARALLEL 
  )
  ENABLE 
) 
LOGGING 
TABLESPACE USERS 
PCTFREE 10 
INITRANS 1 
STORAGE 
( 
  INITIAL 65536 
  NEXT 1048576 
  MINEXTENTS 1 
  MAXEXTENTS UNLIMITED 
  BUFFER_POOL DEFAULT 
) 
NOCOMPRESS 
NOPARALLEL;

ALTER TABLE ANSWERSNEW
ADD CONSTRAINT SYS_C00425870 FOREIGN KEY
(
  Q_ID 
)
REFERENCES QUESTIONSNEW
(
  Q_ID 
)
ENABLE;

ALTER TABLE ANSWERSNEW
ADD CONSTRAINT SYS_C00425868 CHECK 
(IS_CORRECT IN (1,0))
ENABLE;


GO;


CREATE TABLE ATTEMPTSNEW 
(
  AT_ID NUMBER(38, 0) NOT NULL 
, INSTANCE_ID NUMBER(38, 0) NOT NULL 
, USER_ID VARCHAR2(20 BYTE) 
, TIME_OF_ATTEMPT TIMESTAMP(6) 
, OBTAINED_SCORE NUMBER(38, 0) 
, CONSTRAINT SYS_C00425871 PRIMARY KEY 
  (
    AT_ID 
  , INSTANCE_ID 
  )
  USING INDEX 
  (
      CREATE UNIQUE INDEX SYS_C00425871 ON ATTEMPTSNEW (AT_ID ASC, INSTANCE_ID ASC) 
      LOGGING 
      TABLESPACE USERS 
      PCTFREE 10 
      INITRANS 2 
      STORAGE 
      ( 
        INITIAL 65536 
        NEXT 1048576 
        MINEXTENTS 1 
        MAXEXTENTS UNLIMITED 
        BUFFER_POOL DEFAULT 
      ) 
      NOPARALLEL 
  )
  ENABLE 
) 
LOGGING 
TABLESPACE USERS 
PCTFREE 10 
INITRANS 1 
STORAGE 
( 
  INITIAL 65536 
  NEXT 1048576 
  MINEXTENTS 1 
  MAXEXTENTS UNLIMITED 
  BUFFER_POOL DEFAULT 
) 
NOCOMPRESS 
NOPARALLEL;

ALTER TABLE ATTEMPTSNEW
ADD CONSTRAINT SYS_C00425872 FOREIGN KEY
(
  INSTANCE_ID 
)
REFERENCES EXERCISE_INSTANCENEW
(
  INSTANCE_ID 
)
ENABLE;

ALTER TABLE ATTEMPTSNEW
ADD CONSTRAINT SYS_C00425873 FOREIGN KEY
(
  USER_ID 
)
REFERENCES USERS
(
  USER_ID 
)
ENABLE;


GO;

CREATE TABLE COURSE 
(
  C_ID VARCHAR2(20 BYTE) NOT NULL 
, CNAME VARCHAR2(40 BYTE) NOT NULL 
, ST_DATE TIMESTAMP(6) 
, END_DATE TIMESTAMP(6) 
, NO_OF_ENROLLED_STUDENT NUMBER(*, 0) 
, MAX_ALLOWED_STUDENT NUMBER(*, 0) 
, C_LEVEL VARCHAR2(20 BYTE) NOT NULL 
, TAUGHT_BY VARCHAR2(20 BYTE) 
, CONSTRAINT SYS_C00412548 PRIMARY KEY 
  (
    C_ID 
  )
  USING INDEX 
  (
      CREATE UNIQUE INDEX SYS_C00412548 ON COURSE (C_ID ASC) 
      LOGGING 
      TABLESPACE USERS 
      PCTFREE 10 
      INITRANS 2 
      STORAGE 
      ( 
        INITIAL 65536 
        NEXT 1048576 
        MINEXTENTS 1 
        MAXEXTENTS UNLIMITED 
        BUFFER_POOL DEFAULT 
      ) 
      NOPARALLEL 
  )
  ENABLE 
) 
LOGGING 
TABLESPACE USERS 
PCTFREE 10 
INITRANS 1 
STORAGE 
( 
  INITIAL 65536 
  NEXT 1048576 
  MINEXTENTS 1 
  MAXEXTENTS UNLIMITED 
  BUFFER_POOL DEFAULT 
) 
NOCOMPRESS 
NOPARALLEL;

ALTER TABLE COURSE
ADD CONSTRAINT FK_TAUGHT FOREIGN KEY
(
  TAUGHT_BY 
)
REFERENCES USERS
(
  USER_ID 
)
ENABLE;


GO;

CREATE TABLE COURSE_CONTAINS_TOPICNEW 
(
  T_ID NUMBER(38, 0) NOT NULL 
, C_ID VARCHAR2(20 BYTE) NOT NULL 
, CONSTRAINT SYS_C00425846 PRIMARY KEY 
  (
    T_ID 
  , C_ID 
  )
  USING INDEX 
  (
      CREATE UNIQUE INDEX SYS_C00425846 ON COURSE_CONTAINS_TOPICNEW (T_ID ASC, C_ID ASC) 
      LOGGING 
      TABLESPACE USERS 
      PCTFREE 10 
      INITRANS 2 
      STORAGE 
      ( 
        INITIAL 65536 
        NEXT 1048576 
        MINEXTENTS 1 
        MAXEXTENTS UNLIMITED 
        BUFFER_POOL DEFAULT 
      ) 
      NOPARALLEL 
  )
  ENABLE 
) 
LOGGING 
TABLESPACE USERS 
PCTFREE 10 
INITRANS 1 
STORAGE 
( 
  INITIAL 65536 
  NEXT 1048576 
  MINEXTENTS 1 
  MAXEXTENTS UNLIMITED 
  BUFFER_POOL DEFAULT 
) 
NOCOMPRESS 
NOPARALLEL;

ALTER TABLE COURSE_CONTAINS_TOPICNEW
ADD CONSTRAINT SYS_C00425847 FOREIGN KEY
(
  C_ID 
)
REFERENCES COURSE
(
  C_ID 
)
ENABLE;

ALTER TABLE COURSE_CONTAINS_TOPICNEW
ADD CONSTRAINT SYS_C00425848 FOREIGN KEY
(
  T_ID 
)
REFERENCES TOPICNEW
(
  T_ID 
)
ENABLE;


GO;


CREATE TABLE COURSE_ENROLLMENT 
(
  C_ID VARCHAR2(20 BYTE) NOT NULL 
, USER_ID VARCHAR2(20 BYTE) NOT NULL 
, CONSTRAINT PK_CR_ENR PRIMARY KEY 
  (
    C_ID 
  , USER_ID 
  )
  USING INDEX 
  (
      CREATE UNIQUE INDEX PK_CR_ENR ON COURSE_ENROLLMENT (C_ID ASC, USER_ID ASC) 
      LOGGING 
      TABLESPACE USERS 
      PCTFREE 10 
      INITRANS 2 
      STORAGE 
      ( 
        INITIAL 65536 
        NEXT 1048576 
        MINEXTENTS 1 
        MAXEXTENTS UNLIMITED 
        BUFFER_POOL DEFAULT 
      ) 
      NOPARALLEL 
  )
  ENABLE 
) 
LOGGING 
TABLESPACE USERS 
PCTFREE 10 
INITRANS 1 
STORAGE 
( 
  INITIAL 65536 
  NEXT 1048576 
  MINEXTENTS 1 
  MAXEXTENTS UNLIMITED 
  BUFFER_POOL DEFAULT 
) 
NOCOMPRESS 
NOPARALLEL;

ALTER TABLE COURSE_ENROLLMENT
ADD CONSTRAINT FK_CR_ENR FOREIGN KEY
(
  C_ID 
)
REFERENCES COURSE
(
  C_ID 
)
ENABLE;

ALTER TABLE COURSE_ENROLLMENT
ADD CONSTRAINT FK_USR_CR_ENR FOREIGN KEY
(
  USER_ID 
)
REFERENCES USERS
(
  USER_ID 
)
ENABLE;


GO;


CREATE TABLE COURSE_TA 
(
  C_ID VARCHAR2(20 BYTE) NOT NULL 
, USER_ID VARCHAR2(20 BYTE) NOT NULL 
, CONSTRAINT PK_CR_TA PRIMARY KEY 
  (
    C_ID 
  , USER_ID 
  )
  USING INDEX 
  (
      CREATE UNIQUE INDEX PK_CR_TA ON COURSE_TA (C_ID ASC, USER_ID ASC) 
      LOGGING 
      TABLESPACE USERS 
      PCTFREE 10 
      INITRANS 2 
      STORAGE 
      ( 
        INITIAL 65536 
        NEXT 1048576 
        MINEXTENTS 1 
        MAXEXTENTS UNLIMITED 
        BUFFER_POOL DEFAULT 
      ) 
      NOPARALLEL 
  )
  ENABLE 
) 
LOGGING 
TABLESPACE USERS 
PCTFREE 10 
INITRANS 1 
STORAGE 
( 
  INITIAL 65536 
  NEXT 1048576 
  MINEXTENTS 1 
  MAXEXTENTS UNLIMITED 
  BUFFER_POOL DEFAULT 
) 
NOCOMPRESS 
NOPARALLEL;

ALTER TABLE COURSE_TA
ADD CONSTRAINT FK_CR_TA FOREIGN KEY
(
  C_ID 
)
REFERENCES COURSE
(
  C_ID 
)
ENABLE;

ALTER TABLE COURSE_TA
ADD CONSTRAINT FK_USR_CR_TA FOREIGN KEY
(
  USER_ID 
)
REFERENCES USERS
(
  USER_ID 
)
ENABLE;


GO;

CREATE TABLE EXERCISE_CONTAINS_QNSNEW 
(
  EX_ID NUMBER(38, 0) NOT NULL 
, C_ID VARCHAR2(20 BYTE) NOT NULL 
, Q_ID NUMBER(38, 0) NOT NULL 
, CONSTRAINT SYS_C00425862 PRIMARY KEY 
  (
    EX_ID 
  , C_ID 
  , Q_ID 
  )
  USING INDEX 
  (
      CREATE UNIQUE INDEX SYS_C00425862 ON EXERCISE_CONTAINS_QNSNEW (EX_ID ASC, C_ID ASC, Q_ID ASC) 
      LOGGING 
      TABLESPACE USERS 
      PCTFREE 10 
      INITRANS 2 
      STORAGE 
      ( 
        INITIAL 65536 
        NEXT 1048576 
        MINEXTENTS 1 
        MAXEXTENTS UNLIMITED 
        BUFFER_POOL DEFAULT 
      ) 
      NOPARALLEL 
  )
  ENABLE 
) 
LOGGING 
TABLESPACE USERS 
PCTFREE 10 
INITRANS 1 
STORAGE 
( 
  INITIAL 65536 
  NEXT 1048576 
  MINEXTENTS 1 
  MAXEXTENTS UNLIMITED 
  BUFFER_POOL DEFAULT 
) 
NOCOMPRESS 
NOPARALLEL;

ALTER TABLE EXERCISE_CONTAINS_QNSNEW
ADD CONSTRAINT SYS_C00425863 FOREIGN KEY
(
  Q_ID 
)
REFERENCES QUESTIONSNEW
(
  Q_ID 
)
ENABLE;

ALTER TABLE EXERCISE_CONTAINS_QNSNEW
ADD CONSTRAINT SYS_C00425864 FOREIGN KEY
(
  EX_ID 
, C_ID 
)
REFERENCES EXERCISE_OUTLINENEW
(
  EX_ID 
, C_ID 
)
ENABLE;



GO;

CREATE TABLE EXERCISE_INSTANCENEW 
(
  INSTANCE_ID NUMBER(38, 0) NOT NULL 
, EX_ID NUMBER(38, 0) 
, USER_ID VARCHAR2(20 BYTE) 
, C_ID VARCHAR2(20 BYTE) 
, CONSTRAINT SYS_C00425855 PRIMARY KEY 
  (
    INSTANCE_ID 
  )
  USING INDEX 
  (
      CREATE UNIQUE INDEX SYS_C00425855 ON EXERCISE_INSTANCENEW (INSTANCE_ID ASC) 
      LOGGING 
      TABLESPACE USERS 
      PCTFREE 10 
      INITRANS 2 
      STORAGE 
      ( 
        INITIAL 65536 
        NEXT 1048576 
        MINEXTENTS 1 
        MAXEXTENTS UNLIMITED 
        BUFFER_POOL DEFAULT 
      ) 
      NOPARALLEL 
  )
  ENABLE 
) 
LOGGING 
TABLESPACE USERS 
PCTFREE 10 
INITRANS 1 
STORAGE 
( 
  INITIAL 65536 
  NEXT 1048576 
  MINEXTENTS 1 
  MAXEXTENTS UNLIMITED 
  BUFFER_POOL DEFAULT 
) 
NOCOMPRESS 
NOPARALLEL;

ALTER TABLE EXERCISE_INSTANCENEW
ADD CONSTRAINT SYS_C00425856 FOREIGN KEY
(
  USER_ID 
)
REFERENCES USERS
(
  USER_ID 
)
ENABLE;

ALTER TABLE EXERCISE_INSTANCENEW
ADD CONSTRAINT SYS_C00425857 FOREIGN KEY
(
  EX_ID 
, C_ID 
)
REFERENCES EXERCISE_OUTLINENEW
(
  EX_ID 
, C_ID 
)
ENABLE;


GO;

CREATE TABLE EXERCISE_OUTLINENEW 
(
  EX_ID NUMBER(38, 0) NOT NULL 
, ENAME VARCHAR2(50 BYTE) 
, ST_DATE TIMESTAMP(6) 
, END_DATE TIMESTAMP(6) 
, TOTAL_QUES NUMBER(38, 0) 
, RETRIES NUMBER(38, 0) 
, CORRECT_POINTS NUMBER(38, 0) 
, PENALTY NUMBER(38, 0) 
, SCORING_POLICY VARCHAR2(2 BYTE) 
, EX_TYPE VARCHAR2(2 BYTE) 
, QBANK_ID NUMBER(38, 0) 
, C_ID VARCHAR2(20 BYTE) NOT NULL 
, CREATED_BY_USER VARCHAR2(20 BYTE) 
, T_ID NUMBER DEFAULT 1 NOT NULL 
, CONSTRAINT SYS_C00425851 PRIMARY KEY 
  (
    EX_ID 
  , C_ID 
  )
  USING INDEX 
  (
      CREATE UNIQUE INDEX SYS_C00425851 ON EXERCISE_OUTLINENEW (EX_ID ASC, C_ID ASC) 
      LOGGING 
      TABLESPACE USERS 
      PCTFREE 10 
      INITRANS 2 
      STORAGE 
      ( 
        INITIAL 65536 
        NEXT 1048576 
        MINEXTENTS 1 
        MAXEXTENTS UNLIMITED 
        BUFFER_POOL DEFAULT 
      ) 
      NOPARALLEL 
  )
  ENABLE 
) 
LOGGING 
TABLESPACE USERS 
PCTFREE 10 
INITRANS 1 
STORAGE 
( 
  INITIAL 65536 
  NEXT 1048576 
  MINEXTENTS 1 
  MAXEXTENTS UNLIMITED 
  BUFFER_POOL DEFAULT 
) 
NOCOMPRESS 
NOPARALLEL;

ALTER TABLE EXERCISE_OUTLINENEW
ADD CONSTRAINT FK_TOPICID FOREIGN KEY
(
  T_ID 
)
REFERENCES TOPICNEW
(
  T_ID 
)
ENABLE;

ALTER TABLE EXERCISE_OUTLINENEW
ADD CONSTRAINT SYS_C00425852 FOREIGN KEY
(
  C_ID 
)
REFERENCES COURSE
(
  C_ID 
)
ENABLE;

ALTER TABLE EXERCISE_OUTLINENEW
ADD CONSTRAINT SYS_C00425853 FOREIGN KEY
(
  QBANK_ID 
)
REFERENCES QUESTION_BANKNEW
(
  QBANK_ID 
)
ENABLE;

ALTER TABLE EXERCISE_OUTLINENEW
ADD CONSTRAINT SYS_C00425854 FOREIGN KEY
(
  CREATED_BY_USER 
)
REFERENCES USERS
(
  USER_ID 
)
ENABLE;

ALTER TABLE EXERCISE_OUTLINENEW
ADD CONSTRAINT SYS_C00425849 CHECK 
(SCORING_POLICY IN ('L','M','A'))
ENABLE;

ALTER TABLE EXERCISE_OUTLINENEW
ADD CONSTRAINT SYS_C00425850 CHECK 
(EX_TYPE IN ('A','S'))
ENABLE;


GO;


CREATE TABLE EXID_QUEID 
(
  EX_ID NUMBER NOT NULL 
, Q_ID NUMBER NOT NULL 
, CONSTRAINT EXIDQUEID_PK PRIMARY KEY 
  (
    EX_ID 
  , Q_ID 
  )
  USING INDEX 
  (
      CREATE UNIQUE INDEX EXIDQUEID_PK ON EXID_QUEID (EX_ID ASC, Q_ID ASC) 
      LOGGING 
      TABLESPACE USERS 
      PCTFREE 10 
      INITRANS 2 
      STORAGE 
      ( 
        INITIAL 65536 
        NEXT 1048576 
        MINEXTENTS 1 
        MAXEXTENTS UNLIMITED 
        BUFFER_POOL DEFAULT 
      ) 
      NOPARALLEL 
  )
  ENABLE 
) 
LOGGING 
TABLESPACE USERS 
PCTFREE 10 
INITRANS 1 
STORAGE 
( 
  INITIAL 65536 
  NEXT 1048576 
  MINEXTENTS 1 
  MAXEXTENTS UNLIMITED 
  BUFFER_POOL DEFAULT 
) 
NOCOMPRESS 
NOPARALLEL;


GO;


CREATE TABLE FINAL_SCORES 
(
  EX_ID NUMBER NOT NULL 
, C_ID VARCHAR2(20 BYTE) NOT NULL 
, USER_ID VARCHAR2(20 BYTE) NOT NULL 
, SCORE NUMBER NOT NULL 
, CONSTRAINT FINALSCORES_PK PRIMARY KEY 
  (
    USER_ID 
  , C_ID 
  , EX_ID 
  )
  USING INDEX 
  (
      CREATE UNIQUE INDEX FINALSCORES_PK ON FINAL_SCORES (USER_ID ASC, C_ID ASC, EX_ID ASC) 
      LOGGING 
      TABLESPACE USERS 
      PCTFREE 10 
      INITRANS 2 
      STORAGE 
      ( 
        INITIAL 65536 
        NEXT 1048576 
        MINEXTENTS 1 
        MAXEXTENTS UNLIMITED 
        BUFFER_POOL DEFAULT 
      ) 
      NOPARALLEL 
  )
  ENABLE 
) 
LOGGING 
TABLESPACE USERS 
PCTFREE 10 
INITRANS 1 
STORAGE 
( 
  INITIAL 65536 
  NEXT 1048576 
  MINEXTENTS 1 
  MAXEXTENTS UNLIMITED 
  BUFFER_POOL DEFAULT 
) 
NOCOMPRESS 
NOPARALLEL;

ALTER TABLE FINAL_SCORES
ADD CONSTRAINT FK1_USERID FOREIGN KEY
(
  USER_ID 
)
REFERENCES USERS
(
  USER_ID 
)
ENABLE;

ALTER TABLE FINAL_SCORES
ADD CONSTRAINT SYS_C00430489 FOREIGN KEY
(
  EX_ID 
, C_ID 
)
REFERENCES EXERCISE_OUTLINENEW
(
  EX_ID 
, C_ID 
)
ENABLE;


GO;


CREATE TABLE INSTANCE_CONTAINS_QUESTIONSNEW 
(
  INSTANCE_ID NUMBER(38, 0) NOT NULL 
, Q_ID NUMBER(38, 0) NOT NULL 
, CONSTRAINT SYS_C00425859 PRIMARY KEY 
  (
    INSTANCE_ID 
  , Q_ID 
  )
  USING INDEX 
  (
      CREATE UNIQUE INDEX SYS_C00425859 ON INSTANCE_CONTAINS_QUESTIONSNEW (INSTANCE_ID ASC, Q_ID ASC) 
      LOGGING 
      TABLESPACE USERS 
      PCTFREE 10 
      INITRANS 2 
      STORAGE 
      ( 
        INITIAL 65536 
        NEXT 1048576 
        MINEXTENTS 1 
        MAXEXTENTS UNLIMITED 
        BUFFER_POOL DEFAULT 
      ) 
      NOPARALLEL 
  )
  ENABLE 
) 
LOGGING 
TABLESPACE USERS 
PCTFREE 10 
INITRANS 1 
STORAGE 
( 
  INITIAL 65536 
  NEXT 1048576 
  MINEXTENTS 1 
  MAXEXTENTS UNLIMITED 
  BUFFER_POOL DEFAULT 
) 
NOCOMPRESS 
NOPARALLEL;

ALTER TABLE INSTANCE_CONTAINS_QUESTIONSNEW
ADD CONSTRAINT SYS_C00425860 FOREIGN KEY
(
  INSTANCE_ID 
)
REFERENCES EXERCISE_INSTANCENEW
(
  INSTANCE_ID 
)
ENABLE;

ALTER TABLE INSTANCE_CONTAINS_QUESTIONSNEW
ADD CONSTRAINT SYS_C00425861 FOREIGN KEY
(
  Q_ID 
)
REFERENCES QUESTIONSNEW
(
  Q_ID 
)
ENABLE;


GO;

CREATE TABLE QUESTION_BANKNEW 
(
  QBANK_ID NUMBER(38, 0) NOT NULL 
, NO_OF_QUESTIONS NUMBER(38, 0) 
, C_ID VARCHAR2(20 BYTE) 
, CONSTRAINT SYS_C00425836 PRIMARY KEY 
  (
    QBANK_ID 
  )
  USING INDEX 
  (
      CREATE UNIQUE INDEX SYS_C00425836 ON QUESTION_BANKNEW (QBANK_ID ASC) 
      LOGGING 
      TABLESPACE USERS 
      PCTFREE 10 
      INITRANS 2 
      STORAGE 
      ( 
        INITIAL 65536 
        NEXT 1048576 
        MINEXTENTS 1 
        MAXEXTENTS UNLIMITED 
        BUFFER_POOL DEFAULT 
      ) 
      NOPARALLEL 
  )
  ENABLE 
) 
LOGGING 
TABLESPACE USERS 
PCTFREE 10 
INITRANS 1 
STORAGE 
( 
  INITIAL 65536 
  NEXT 1048576 
  MINEXTENTS 1 
  MAXEXTENTS UNLIMITED 
  BUFFER_POOL DEFAULT 
) 
NOCOMPRESS 
NOPARALLEL;

ALTER TABLE QUESTION_BANKNEW
ADD CONSTRAINT UQ_CID UNIQUE 
(
  C_ID 
)
USING INDEX 
(
    CREATE UNIQUE INDEX UQ_CID ON QUESTION_BANKNEW (C_ID ASC) 
    LOGGING 
    TABLESPACE USERS 
    PCTFREE 10 
    INITRANS 2 
    STORAGE 
    ( 
      INITIAL 65536 
      NEXT 1048576 
      MINEXTENTS 1 
      MAXEXTENTS UNLIMITED 
      BUFFER_POOL DEFAULT 
    ) 
    NOPARALLEL 
)
 ENABLE;

ALTER TABLE QUESTION_BANKNEW
ADD CONSTRAINT SYS_C00425838 FOREIGN KEY
(
  C_ID 
)
REFERENCES COURSE
(
  C_ID 
)
ENABLE;


GO;

CREATE TABLE QUESTIONSNEW 
(
  Q_ID NUMBER(38, 0) NOT NULL 
, T_ID NUMBER(38, 0) 
, DIFF_LEVEL NUMBER(38, 0) 
, HINT VARCHAR2(500 BYTE) 
, QUESTION_TEXT VARCHAR2(500 BYTE) NOT NULL 
, ISPARAMETRIC NUMBER(38, 0) 
, QBANK_ID NUMBER(38, 0) 
, SOLUTION VARCHAR2(500 BYTE) 
, ROOT_TEXT VARCHAR2(20 BYTE) 
, CONSTRAINT SYS_C00425843 PRIMARY KEY 
  (
    Q_ID 
  )
  USING INDEX 
  (
      CREATE UNIQUE INDEX SYS_C00425843 ON QUESTIONSNEW (Q_ID ASC) 
      LOGGING 
      TABLESPACE USERS 
      PCTFREE 10 
      INITRANS 2 
      STORAGE 
      ( 
        INITIAL 65536 
        NEXT 1048576 
        MINEXTENTS 1 
        MAXEXTENTS UNLIMITED 
        BUFFER_POOL DEFAULT 
      ) 
      NOPARALLEL 
  )
  ENABLE 
) 
LOGGING 
TABLESPACE USERS 
PCTFREE 10 
INITRANS 1 
STORAGE 
( 
  INITIAL 65536 
  NEXT 1048576 
  MINEXTENTS 1 
  MAXEXTENTS UNLIMITED 
  BUFFER_POOL DEFAULT 
) 
NOCOMPRESS 
NOPARALLEL;

ALTER TABLE QUESTIONSNEW
ADD CONSTRAINT SYS_C00425844 FOREIGN KEY
(
  T_ID 
)
REFERENCES TOPICNEW
(
  T_ID 
)
ENABLE;

ALTER TABLE QUESTIONSNEW
ADD CONSTRAINT SYS_C00425845 FOREIGN KEY
(
  QBANK_ID 
)
REFERENCES QUESTION_BANKNEW
(
  QBANK_ID 
)
ENABLE;

ALTER TABLE QUESTIONSNEW
ADD CONSTRAINT SYS_C00425841 CHECK 
(DIFF_LEVEL>=1 AND DIFF_LEVEL<=6)
ENABLE;

ALTER TABLE QUESTIONSNEW
ADD CONSTRAINT SYS_C00425842 CHECK 
(ISPARAMETRIC IN (1,0))
ENABLE;


GO;



CREATE TABLE SUBMISSION_DETAILSNEW 
(
  AT_ID NUMBER(38, 0) 
, INSTANCE_ID NUMBER(38, 0) 
, USER_ID VARCHAR2(20 BYTE) 
, GIVEN_ANSWER VARCHAR2(50 BYTE) 
, Q_ID NUMBER(38, 0) 
) 
LOGGING 
TABLESPACE USERS 
PCTFREE 10 
INITRANS 1 
STORAGE 
( 
  INITIAL 65536 
  NEXT 1048576 
  MINEXTENTS 1 
  MAXEXTENTS UNLIMITED 
  BUFFER_POOL DEFAULT 
) 
NOCOMPRESS 
NOPARALLEL;

ALTER TABLE SUBMISSION_DETAILSNEW
ADD CONSTRAINT SYS_C00425874 FOREIGN KEY
(
  INSTANCE_ID 
)
REFERENCES EXERCISE_INSTANCENEW
(
  INSTANCE_ID 
)
ENABLE;

ALTER TABLE SUBMISSION_DETAILSNEW
ADD CONSTRAINT SYS_C00425875 FOREIGN KEY
(
  USER_ID 
)
REFERENCES USERS
(
  USER_ID 
)
ENABLE;

ALTER TABLE SUBMISSION_DETAILSNEW
ADD CONSTRAINT SYS_C00425876 FOREIGN KEY
(
  Q_ID 
)
REFERENCES QUESTIONSNEW
(
  Q_ID 
)
ENABLE;


GO;


CREATE TABLE TOPICNEW 
(
  T_ID NUMBER(38, 0) NOT NULL 
, TNAME VARCHAR2(100 BYTE) 
, C_ID VARCHAR2(20 BYTE) 
, CONSTRAINT SYS_C00425835 PRIMARY KEY 
  (
    T_ID 
  )
  USING INDEX 
  (
      CREATE UNIQUE INDEX SYS_C00425835 ON TOPICNEW (T_ID ASC) 
      LOGGING 
      TABLESPACE USERS 
      PCTFREE 10 
      INITRANS 2 
      STORAGE 
      ( 
        INITIAL 65536 
        NEXT 1048576 
        MINEXTENTS 1 
        MAXEXTENTS UNLIMITED 
        BUFFER_POOL DEFAULT 
      ) 
      NOPARALLEL 
  )
  ENABLE 
) 
LOGGING 
TABLESPACE USERS 
PCTFREE 10 
INITRANS 1 
STORAGE 
( 
  INITIAL 65536 
  NEXT 1048576 
  MINEXTENTS 1 
  MAXEXTENTS UNLIMITED 
  BUFFER_POOL DEFAULT 
) 
NOCOMPRESS 
NOPARALLEL;

ALTER TABLE TOPICNEW
ADD CONSTRAINT TOPICNEW_FK1 FOREIGN KEY
(
  C_ID 
)
REFERENCES COURSE
(
  C_ID 
)
ON DELETE CASCADE ENABLE;


GO;


CREATE TABLE USERS 
(
  USER_ID VARCHAR2(20 BYTE) NOT NULL 
, FNAME VARCHAR2(20 BYTE) 
, LNAME VARCHAR2(20 BYTE) 
, TYPE VARCHAR2(20 BYTE) 
, PASSWORD VARCHAR2(20 BYTE) NOT NULL 
, CONSTRAINT USERS_PK PRIMARY KEY 
  (
    USER_ID 
  )
  USING INDEX 
  (
      CREATE UNIQUE INDEX USERS_PK ON USERS (USER_ID ASC) 
      LOGGING 
      TABLESPACE USERS 
      PCTFREE 10 
      INITRANS 2 
      STORAGE 
      ( 
        INITIAL 65536 
        NEXT 1048576 
        MINEXTENTS 1 
        MAXEXTENTS UNLIMITED 
        BUFFER_POOL DEFAULT 
      ) 
      NOPARALLEL 
  )
  ENABLE 
) 
LOGGING 
TABLESPACE USERS 
PCTFREE 10 
INITRANS 1 
STORAGE 
( 
  INITIAL 65536 
  NEXT 1048576 
  MINEXTENTS 1 
  MAXEXTENTS UNLIMITED 
  BUFFER_POOL DEFAULT 
) 
NOCOMPRESS 
NOPARALLEL;


GO;

