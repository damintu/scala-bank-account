# --- !Ups

create table "ACCOUNT" (
  "ID" bigint generated by default as identity(start with 1) not null primary key,
  "FIRSTNAME" varchar not null,
  "LASTNAME" varchar not null
);
# --- !Downs

drop table "ACCOUNT" if exists;
