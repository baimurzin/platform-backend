insert into public.plans (id, course_count, month_price, name, year_price)
select 1, 2, 10, 'Test Plan', 100       where 1 not in (select id from plans);
-- select 2, 5, 20, 'Test Plan Gold', 200  where 2 not in (select id from plans);

-- values
-- (1, 2, 10, 'Test Plan', 100),
-- (2, 5, 20, 'Test Plan Gold', 200)
-- where id not in (1, 2);

-- INSERT INTO users (id, activation_key, balance, confirmed, created_date, currency, email, lang, password, role, timezone, plan_id) VALUES (1, '42020464482654822267', 0.00, false, '2019-02-02 20:48:34.162000', 'RUR', 'qwe', null, '$2a$10$5fBG6hsBVwXfWDVy53N.pu/fBOZ3BsadRyfEevzWn8S.tu5yonZeK', 'UNCONFIRMED', null, null);
insert into courses (id, course_type, description, name, price) values (1, 'REGULAR', 'course about my dick1', 'Applied dick1', 14.88);
-- insert into courses (id, course_type, description, name, price) values (2, 'REGULAR', 'course about my dick2', 'Applied dick2', 14.89);
-- insert into courses (id, course_type, description, name, price) values (3, 'REGULAR', 'course about my dick3', 'Applied dick3', 14.90);

insert into steps (id, title, type, content, course_id, next_id, prev_id) values
(1, 'Title Java Spring Project', 'THEORY', 'dick1', 1, null,null),
(2, 'Title Java Spring Project', 'THEORY', 'dick2', 1, null,null),
(3, 'Title Java Spring Project', 'THEORY', 'dick3', 1, null,null),
(4, 'Title Java Spring Project', 'THEORY', 'dick4', 1, null,null),
(5, 'Title Java Spring Project', 'THEORY', 'dick5', 1, null,null),
(6, 'Title Java Spring Project', 'THEORY', 'dick6', 1, null,null),
(7, 'Title Java Spring Project', 'THEORY', 'dick7', 1, null,null),
(8, 'Title Java Spring Project', 'THEORY', 'dick8', 1, null,null);

update steps set next_id = 2 where id = 1;
update steps set next_id = 3, prev_id = 1 where id = 2;
update steps set next_id = 4, prev_id = 2 where id = 3;
update steps set next_id = 5, prev_id = 3 where id = 4;
update steps set next_id = 6, prev_id = 4 where id = 5;
update steps set next_id = 7, prev_id = 5 where id = 6;
update steps set next_id = 8, prev_id = 6 where id = 7;
update steps set prev_id = 7 where id = 8;

--
--
-- insert into steps (id, content, course_id, next_id, prev_id) values (9, 'dick1', 2, null,null);
-- insert into steps (id, content, course_id, next_id, prev_id) values (10, 'dick2', 2, null,null);
-- insert into steps (id, content, course_id, next_id, prev_id) values (11, 'dick3', 2, null,null);
-- insert into steps (id, content, course_id, next_id, prev_id) values (12, 'dick4', 2, null,null);
-- insert into steps (id, content, course_id, next_id, prev_id) values (13, 'dick5', 2, null,null);
-- insert into steps (id, content, course_id, next_id, prev_id) values (14, 'dick6', 2, null,null);
-- insert into steps (id, content, course_id, next_id, prev_id) values (15, 'dick7', 2, null,null);
-- insert into steps (id, content, course_id, next_id, prev_id) values (16, 'dick8', 2, null,null);
--
-- update steps set next_id = 10 where id = 9;
-- update steps set next_id = 11, prev_id = 9 where id = 10;
-- update steps set next_id = 12, prev_id = 10 where id = 11;
-- update steps set next_id = 13, prev_id = 11 where id = 12;
-- update steps set next_id = 14, prev_id = 12 where id = 13;
-- update steps set next_id = 15, prev_id = 13 where id = 14;
-- update steps set next_id = 16, prev_id = 14 where id = 15;
-- update steps set prev_id = 15 where id = 16;
--
-- insert into steps (id, content, course_id, next_id, prev_id) values (17, 'dick1', 3, null,null);
-- insert into steps (id, content, course_id, next_id, prev_id) values (18, 'dick2', 3, null,null);
-- insert into steps (id, content, course_id, next_id, prev_id) values (19, 'dick3', 3, null,null);
-- insert into steps (id, content, course_id, next_id, prev_id) values (20, 'dick4', 3, null,null);
-- insert into steps (id, content, course_id, next_id, prev_id) values (21, 'dick5', 3, null,null);
-- insert into steps (id, content, course_id, next_id, prev_id) values (22, 'dick6', 3, null,null);
-- insert into steps (id, content, course_id, next_id, prev_id) values (23, 'dick7', 3, null,null);
-- insert into steps (id, content, course_id, next_id, prev_id) values (24, 'dick8', 3, null,null);
--
-- update steps set next_id = 18 where id = 17;
-- update steps set next_id = 19, prev_id = 17 where id = 18;
-- update steps set next_id = 20, prev_id = 18 where id = 19;
-- update steps set next_id = 21, prev_id = 19 where id = 20;
-- update steps set next_id = 22, prev_id = 20 where id = 21;
-- update steps set next_id = 23, prev_id = 21 where id = 22;
-- update steps set next_id = 24, prev_id = 22 where id = 23;
-- update steps set prev_id = 23 where id = 24;
--

commit;