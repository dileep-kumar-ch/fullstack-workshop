const validation = () => {
  const username = document.getElementById("username").value.trim();
  const email = document.getElementById("exampleInputEmail1").value.trim();
  const password = document.getElementById("exampleInputPassword1").value;
  const confirmPassword = document.getElementById("exampleInputPassword2").value;

  const rules = [
    {
      condition: !/^[a-zA-Z0-9]{3,15}$/.test(username),
      message: "Username must be 3–15 characters and alphanumeric only"
    },
    {
      condition: !/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email),
      message: "Please enter a valid email address"
    },
    {
      condition: !/^(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/.test(password),
      message:
        "Password must be at least 8 characters, include 1 uppercase letter, 1 number, and 1 special character"
    },
    {
      condition: password !== confirmPassword,
      message: "Passwords do not match"
    }
  ];

  // Using array method (find)
  const error = rules.find(rule => rule.condition === true);

  if (error) {
    alert(error.message);
    return false;
  }

  alert(`Form submitted successfully!\nUsername: ${username}\nEmail: ${email}`);
  console.log({ username, email });

  return true;
};
