const choice = Number(
  prompt(
    "Choose an option:\n1. Celsius to Fahrenheit\n2. Fahrenheit to Celsius"
  )
);

const temperature = Number(prompt("Enter the temperature:"));

const converters = [
  {
    id: 1,
    label: "Celsius to Fahrenheit",
    convert: temp => (temp * 9) / 5 + 32,
    unit: "Fahrenheit"
  },
  {
    id: 2,
    label: "Fahrenheit to Celsius",
    convert: temp => ((temp - 32) * 5) / 9,
    unit: "Celsius"
  }
];

// Use array method to find the selected conversion
const selected = converters.find(c => c.id === choice);

if (!selected || Number.isNaN(temperature)) {
  const errorMessage = "Please enter valid option (1 or 2) and temperature.";
  alert(errorMessage);
  console.log(errorMessage);
} else {
  const result = selected.convert(temperature).toFixed(2);
  const message = `Converted Temperature: ${result} ${selected.unit}`;

  alert(message);
  console.log(message);
}
