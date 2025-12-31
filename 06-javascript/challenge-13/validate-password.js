const validatePassword = password => {
  const commonPasswords = [
    "password",
    "123456",
    "qwerty",
    "admin",
    "letmein"
  ];

  const rules = [
    {
      test: pwd => pwd.length >= 8,
      error: "Too short",
      suggestion: "Use at least 8 characters",
      score: 20
    },
    {
      test: pwd => /[A-Z]/.test(pwd),
      error: "No uppercase letter",
      suggestion: "Add uppercase letters",
      score: 20
    },
    {
      test: pwd => /[a-z]/.test(pwd),
      error: "No lowercase letter",
      suggestion: "Add lowercase letters",
      score: 20
    },
    {
      test: pwd => /[0-9]/.test(pwd),
      error: "No number",
      suggestion: "Add numbers",
      score: 20
    },
    {
      test: pwd => /[!@#$%^&*()_+\-=]/.test(pwd),
      error: "No special character",
      suggestion: "Add special characters",
      score: 20
    }
  ];

  // Apply rules using array methods
  const results = rules.map(rule => ({
    passed: rule.test(password),
    ...rule
  }));

  const errors = results
    .filter(result => result.passed === false)
    .map(result => result.error);

  const suggestions = results
    .filter(result => result.passed === false)
    .map(result => result.suggestion);

  let score = results.reduce(
    (total, result) => total + (result.passed ? result.score : 0),
    0
  );

  // Common password check
  if (commonPasswords.includes(password.toLowerCase())) {
    errors.push("Common password");
    suggestions.push("Avoid common passwords");
    score = Math.min(score, 20);
  }

  return {
    isValid: errors.length === 0,
    score: Math.min(score, 100),
    errors,
    suggestions,
    message: `Password strength score: ${score}/100`
  };
};
