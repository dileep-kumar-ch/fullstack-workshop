let score = 0;

let q1 = prompt('What is 5 + 3?') // 8

if(q1 === '8') score++;

let q2 = prompt('Capital of Andhra Pradesh?'); // Amaravati


if(q2.toLocaleLowerCase() === "amaravathi") score++;

let q3 = prompt('color of sky?'); // Blue

if(q3.toLocaleLowerCase() === "blue") score++;

alert(`the score is ${score}/3`);
console.log('the score is ',score,'/3');
