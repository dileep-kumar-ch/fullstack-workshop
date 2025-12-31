let score = 0;

const questions = [
  {
    question: "What is 5 + 3?",
    answer: "8"
  },
  {
    question: "Capital of Andhra Pradesh?",
    answer: "amaravati"
  },
  {
    question: "Color of sky?",
    answer: "blue"
  }
];

questions.forEach(({ question, answer }) => {
  const userAnswer = prompt(question);

  if (
    userAnswer !== null &&
    userAnswer.trim().toLowerCase() === answer
  ) {
    score++;
  }
});

alert(`The score is ${score}/${questions.length}`);
console.log(`The score is ${score}/${questions.length}`);
