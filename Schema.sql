create database modivapp;

create table person(id SERIAL primary key, name varchar(60) NOT NULL, email varchar(250) NOT NULL);

create table address(id serial, address varchar(120) NOT NULL, street varchar(120) NOT NULL, city varchar(20), state varchar(20), zip varchar(10), type varchar(10), person_id integer references person(id));

create table telephone(id serial, type varchar(10), number varchar(12) not null, person_id integer references person(id));