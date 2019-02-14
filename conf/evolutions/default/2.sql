# --- !Ups
create table OPERATION
(
  ID         int          not null auto_increment primary key,
  DATE       TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  NATURE     varchar(255) not null,
  AMOUNT     DOUBLE       not null,
  TOTAL      DOUBLE       not null,
  ACCOUNT_ID int,
  FOREIGN KEY (ACCOUNT_ID) REFERENCES ACCOUNT (id)
);
# --- !Downs

drop table OPERATION;

