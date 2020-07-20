
## PostgreSQLサーバーの起動

docker run --name postgres-spring -e POSTGRES_PASSWORD=password -d -p 5432:5432 postgres:alpine


## データベース作成

postgres=# create database demodb;
CREATE DATABASE
postgres=# \l
                                 List of databases
   Name    |  Owner   | Encoding |  Collate   |   Ctype    |   Access privileges   
-----------+----------+----------+------------+------------+-----------------------
 demodb    | postgres | UTF8     | en_US.utf8 | en_US.utf8 | 
 postgres  | postgres | UTF8     | en_US.utf8 | en_US.utf8 | 
 template0 | postgres | UTF8     | en_US.utf8 | en_US.utf8 | =c/postgres          +
           |          |          |            |            | postgres=CTc/postgres
 template1 | postgres | UTF8     | en_US.utf8 | en_US.utf8 | =c/postgres          +
           |          |          |            |            | postgres=CTc/postgres
(4 rows)

postgres=# \c demodb;
You are now connected to database "demodb" as user "postgres".

## テーブル自動生成

demodb=# \dt
 public | flyway_schema_history | table | postgres
 public | person                | table | postgres

demodb=# \d person
 id     | uuid                   |           | not null | 
 name   | character varying(100) |           | not null | 

## ID自動生成

demodb=# create extension "uuid-ossp";
CREATE EXTENSION
demodb=# select uuid_generate_v4();
 d54847bc-23e7-4945-bf5f-39062ea8895e

## 初期データ作成

demodb=# insert into person(id, name) values (uuid_generate_v4(), 'Maria Jones');
INSERT 0 1
demodb=# insert into person(id, name) values (uuid_generate_v4(), 'Nerson Mandera');
INSERT 0 1

demodb=# select * from person;
 3fe3921c-f1a1-4586-b335-959d4b2ef92a | Maria Jones
 a75bc8ee-4d9e-4891-8707-b352ba2a4aec | Nerson Mandera

 