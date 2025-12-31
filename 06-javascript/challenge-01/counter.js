let count = 0;

const output = document.getElementById("output");

const updateDisplay = () => {
  output.textContent = `${count}`;
};

const inc = () => {
  count += 1;
  updateDisplay();
};

const dec = () => {
  count -= 1;
  updateDisplay();
};

const reset = () => {
  count = 0;
  updateDisplay();
};

// Step values stored in an array
const steps = [1, 5, 10];

// Using array methods
const one = () => {
  count += steps.find(step => step === 1);
  updateDisplay();
};

const five = () => {
  count += steps.find(step => step === 5);
  updateDisplay();
};

const ten = () => {
  count += steps.find(step => step === 10);
  updateDisplay();
};
