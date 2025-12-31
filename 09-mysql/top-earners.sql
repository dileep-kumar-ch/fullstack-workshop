-- Write a query to find the top 3 highest-paid employees per department using window functions.
select * 
	from 	
		(select department, name, salary, dense_rank() over(partition by department order by salary desc) as rank_in_dept
			from employees) as r
where r.rank_in_dept <=2;