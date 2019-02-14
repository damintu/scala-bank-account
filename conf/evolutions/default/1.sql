# --- !Ups

create table ACCOUNT (
  ID int NOT NULL AUTO_INCREMENT PRIMARY KEY,
  FIRSTNAME varchar(255) NOT NULL,
  LASTNAME varchar(255) NOT NULL
);
# --- !Downs

drop table IF EXISTS ACCOUNT ;
