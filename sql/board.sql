--
-- board
--

desc board;

-- select
select a.id, a.title, a.contents, a.hit, date_format(reg_date, '%Y-%m-%d %h:%i:%s'), g_no, o_no, depth, user_id, b.name from board a, user b where a.user_id = b.id order by id desc limit 0, 10;

-- insert
insert into board values (null, "test", "test", 0, now(), 
 (select max_gno + 1 from (select ifnull(max(g_no), 0) as max_gno from board) t), 1, 1, 1);

insert into board values (null, "test", "test", 0, now(), ifnull((select max(a.g_no) from board a), 0) + 1, 1, 1, 5);


-- select * from board order by g_no desc, o_no asc limit (page-1)*5,5);

update board set o_no = o_no+1 where g_no = 1 and o_no >= 2;

select title, contents from board where id = 2;

select a.id, title, contents, b.name from board a, user b where a.user_id = b.id and id = ?;