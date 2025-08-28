--
-- board
--

desc board;

-- select * from board order by g_no desc, o_no asc limit (page-1)*5,5);



update board set o_no = o_no+1 where g_no = 1 and o_no >= 2;