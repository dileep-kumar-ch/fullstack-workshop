let x = 5;
let y = 10;

x  = x + y;
y = x - y;
x = x - y;

console.log('value of x = ',x); // Should be 10

console.log('value of y = ',y); // Should be 5