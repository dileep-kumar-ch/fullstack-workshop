const weight = Number(prompt("Enter your weight in kg?"));
const height = Number(prompt("Enter your height in meters?"));

const bmi = weight / (height * height);

const categories = [
  { label: "Underweight", min: 0, max: 18.5 },
  { label: "Normal weight", min: 18.5, max: 24.9 },
  { label: "Overweight", min: 25, max: 29.9 },
  { label: "Obese", min: 30, max: Infinity }
];

// Use array method to determine category
const category = categories.find(
  c => bmi >= c.min && bmi <= c.max
);

const message = `Your BMI is ${bmi.toFixed(2)}. You are ${category.label}.`;

alert(message);
console.log(message);
