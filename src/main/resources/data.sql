insert into public.plans (id, course_count, month_price, name, year_price)
select 1, 2, 10, 'Test Plan', 100       where 1 not in (select id from plans);
-- select 2, 5, 20, 'Test Plan Gold', 200  where 2 not in (select id from plans);

-- values
-- (1, 2, 10, 'Test Plan', 100),
-- (2, 5, 20, 'Test Plan Gold', 200)
-- where id not in (1, 2);


insert into courses (id, course_type, description, name, price) values (1, 'dick_type', 'course about my dick', 'Applied dick', 14.88);
insert into steps (id, content, course_id, next_id, prev_id) values (1, 'dick1', 1, null,null);
insert into steps (id, content, course_id, next_id, prev_id) values (2, 'dick2', 1, null,null);
insert into steps (id, content, course_id, next_id, prev_id) values (3, 'dick3', 1, null,null);
insert into steps (id, content, course_id, next_id, prev_id) values (4, 'dick4', 1, null,null);
insert into steps (id, content, course_id, next_id, prev_id) values (5, 'dick5', 1, null,null);
insert into steps (id, content, course_id, next_id, prev_id) values (6, 'dick6', 1, null,null);
insert into steps (id, content, course_id, next_id, prev_id) values (7, 'dick7', 1, null,null);
insert into steps (id, content, course_id, next_id, prev_id) values (8, 'dick8', 1, null,null);

update steps set next_id = 2 where id = 1;
update steps set next_id = 3, prev_id = 1 where id = 2;
update steps set next_id = 4, prev_id = 2 where id = 3;
update steps set next_id = 5, prev_id = 3 where id = 4;
update steps set next_id = 6, prev_id = 4 where id = 5;
update steps set next_id = 7, prev_id = 5 where id = 6;
update steps set next_id = 8, prev_id = 6 where id = 7;
update steps set prev_id = 7 where id = 8;

commit;