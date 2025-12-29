// Ask for weight in kg
// Ask for height in meters
// Calculate BMI: weight / (height * height)
// Display BMI and category:
// - Underweight: < 18.5
// - Normal: 18.5 - 24.9
// - Overweight: 25 - 29.9
// - Obese: >= 30

let weight = prompt('Enter your weight in kg?');

let height = prompt('Enter your height in meters?');

weight = Number(weight);
height = Number(height);

let bmi = weight / (height * height);
if (bmi < 18.5) {
  alert('You are Underweight' + bmi)
  console.log( 'You are Underweight', bmi);
} else if(bmi > 18.5 && bmi < 24.9){
   alert('You are in normal weight' + bmi)
  console.log( 'You in normal weight', bmi);
  
} else if(bmi > 25 && bmi < 29.9){
   alert('You are in Over weight' + bmi)
  console.log( 'You in Over weight', bmi);
  
} else {
  alert('You are Obese' + bmi)
  console.log( 'You are Obese', bmi);
}

