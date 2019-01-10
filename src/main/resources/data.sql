insert into public.plans (id, course_count, month_price, name, year_price)
select 1, 2, 10, 'Test Plan', 100       where 1 not in (select id from plans);
-- select 2, 5, 20, 'Test Plan Gold', 200  where 2 not in (select id from plans);

-- values
-- (1, 2, 10, 'Test Plan', 100),
-- (2, 5, 20, 'Test Plan Gold', 200)
-- where id not in (1, 2);
commit;