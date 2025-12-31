let x = 5;
let y = 10;

// Swap using array destructuring (modern & clean)
[x, y] = [y, x];

const logValues = () => {
  console.log(`Value of x = ${x}`); // 10
  console.log(`Value of y = ${y}`); // 5
};

logValues();
