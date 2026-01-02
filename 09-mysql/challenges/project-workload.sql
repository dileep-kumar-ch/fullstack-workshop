-- Find projects with total allocated hours and number of team members. Only include projects with budget > 50000. Order by total hours descending.
select p.name as project_name, p.budget as budget,count(a.employee_id)as team_size,sum(a.hours_allocated) as total_hours
from projects as p
 join assignments as a
on p.id = a.project_id
where budget > 50000
group by p.id
order by total_hours desc;