-- Write a query that returns each department with:

-- Total employees
select department ,count(name)  as employee_count
from  employees
group by department;

-- Average salary (rounded to 2 decimals)-- 
select department, round(avg(salary),2) as avg_salary
from employees
group by department;

-- Highest salary
select department, max(salary) as max_salary
from employees
group by department;

-- Only departments with more than 2 employees
select department
from employees
group by department
having count(name)  > 2;