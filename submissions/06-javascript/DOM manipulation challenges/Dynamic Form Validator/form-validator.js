

function validation() {
const username = document.getElementById("username").value.trim();
const email = document.getElementById("exampleInputEmail1").value.trim();
const password = document.getElementById("exampleInputPassword1").value;
const confirmpassword = document.getElementById("exampleInputPassword2").value;

  const usernameRegex = /^[a-zA-Z0-9]{3,15}$/;
   if (!usernameRegex.test(username)) {
    alert("Username must be 3–15 characters and alphanumeric only");
    return false;
  }

    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
      if (!emailRegex.test(email)) {
    alert("Please enter a valid email address");
    return false;
  }

  const passwordRegex =
    /^(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;
      if (!passwordRegex.test(password)) {
    alert(
      "Password must be at least 8 characters, include 1 uppercase letter, 1 number, and 1 special character"
    );
    return false;
  }

   if (password !== confirmpassword) {
    alert("Passwords do not match");
    return false;
  }
    alert("Form submitted successfully!");
  console.log(username, email, password);
  
}


