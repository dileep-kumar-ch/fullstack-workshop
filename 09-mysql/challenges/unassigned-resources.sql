-- Find all employees who are NOT assigned to any project.
select e.id,e.name,e.department
from employees e
left join projects p
on e.id = p.id
WHERE p.id IS NULL;
