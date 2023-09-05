--liquibase formatted sql


-- changeset mbuyanov:1
create table faculty
(
    id bigSerial primary key ,
    color varchar(255) not null ,
    name varchar(255) not null
);

-- changeset mbuyanov:2
create table student
(
    id bigSerial primary key ,
    age integer not null ,
    name varchar(255) not null,
    faculty_id bigint unique references faculty(id) not null
);
-- changeset mbuyanov:3
create table avatar
(
    id bigSerial primary key ,
    data bytea not null ,
    file_path varchar(255) not null,
    long_size bigint not null ,
    media_type varchar(255) not null ,
    student_id bigint references student(id)
);

-- changeset mbuyanov:4
create index student_name_index on student (name);

-- changeset mbuyanov:5
create index faculty_name_color_index on faculty(name,color);
