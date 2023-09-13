/**
  Возраст студента не может быть меньше 16 лет.
 */
alter table student
    ADD CONSTRAINT age
     check (age>=16);

/**
  Имена студентов должны быть уникальными и не равны нулю..
 */

alter table student
    alter column name
        set not null;

alter table student
    ADD CONSTRAINT name UNIQUE(name);

/**
  Пара “значение названия” - “цвет факультета” должна быть уникальной.
 */

alter table faculty
    add constraint name_color unique (name, color);

/**
  При создании студента без возраста ему автоматически должно присваиваться 20 лет.
 */

ALTER TABLE student
    ALTER COLUMN age SET DEFAULT 20;
