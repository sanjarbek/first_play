# --- !Ups
CREATE TABLE clients (
  id bigserial primary key,
  username varchar,
  firstname varchar,
  surname varchar,
  birthday date
)
# --- !Downs
drop table if exists clients