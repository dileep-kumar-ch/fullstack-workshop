function validatePassword(password) {
  const errors = [];
  const suggestions = [];
  let score = 0;

  const commonPasswords = [
    "password",
    "123456",
    "qwerty",
    "admin",
    "letmein"
  ];

  // Length check
  if (password.length < 8) {
    errors.push("Too short");
    suggestions.push("Use at least 8 characters");
    score += 10;
  } else {
    score += 20;
  }

  // Uppercase check
  if (!/[A-Z]/.test(password)) {
    errors.push("No uppercase letter");
    suggestions.push("Add uppercase letters");
  } else {
    score += 20;
  }

  // Lowercase check
  if (!/[a-z]/.test(password)) {
    errors.push("No lowercase letter");
    suggestions.push("Add lowercase letters");
  } else {
    score += 20;
  }

  // Number check
  if (!/[0-9]/.test(password)) {
    errors.push("No number");
    suggestions.push("Add numbers");
  } else {
    score += 20;
  }

  // Special character check
  if (!/[!@#$%^&*()_+\-=]/.test(password)) {
    errors.push("No special character");
    suggestions.push("Add special characters");
  } else {
    score += 20;
  }

  // Common password check
  if (commonPasswords.includes(password.toLowerCase())) {
    errors.push("Common password");
    suggestions.push("Avoid common passwords");
    score = Math.min(score, 20);
  }

  // Cap score at 100
  score = Math.min(score, 100);

  return {
    isValid: errors.length === 0,
    score,
    errors,
    suggestions
  };
}
