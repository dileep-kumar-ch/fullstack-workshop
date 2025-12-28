let input = prompt('Choose an option:\n1 .Celsius to Fahrenheit\n2. Fahrenheit to Celsius')

let temperature = prompt('enter the temperature?')

input = Number(input);
temperature = Number(temperature);

if (input === 1) {
  let Fahernheit = (temperature * 9/5) + 32;
  console.log(Fahernheit ,'fahrenheit');
  alert(Fahernheit +'fahrenheit');
  
} else if (input === 2){
  let  celsius = (temperature - 32 ) * 5/9;
  console.log(celsius ,'celsius');
  alert(celsius +'celsius');

} else {
  console.log('enter valid numbers that is 1 or 2 ');
  alert('enter valid numbers that is 1 or 2 ');
}