CREATE TABLE notfication (
	id serial PRIMARY key,
	topic VARCHAR (15) not null,
	title VARCHAR (50) not null,
	area text not null,
	description text  not null,
	date_created TIMESTAMP,
	status varchar(10),
	user_create_id bigint not null
)

CREATE TABLE city (
	id serial PRIMARY key,
	lat real not null,
	lon real not null,
	city varchar(50) not null,
	state varchar(50)  not null,
	status varchar(15)
)


CREATE TABLE topic (
	id serial PRIMARY key,
	name varchar(15)  not null,
	status varchar(15)
)

CREATE TABLE public.user (
	id serial PRIMARY key,
	dni bigint  not null,
	email varchar(50),
	password varchar(15) not null,
	mobile_phone bigint,
	full_name varchar(100),
	status varchar(15) not null,
	rol varchar(15) not null
)