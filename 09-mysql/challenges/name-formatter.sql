SELECT 
  CONCAT(UPPER(last_name), first_name) AS formatted_name,
  CONCAT(first_name, '.', last_name, '@company.com') AS email,
  CONCAT(UPPER(LEFT(first_name, 1)), UPPER(LEFT(last_name, 1))) AS initials
FROM users;