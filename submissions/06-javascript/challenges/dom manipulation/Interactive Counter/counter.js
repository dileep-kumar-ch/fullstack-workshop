let count = 0;
const output = document.getElementById("output");

function update() {
  output.textContent = count;
}

function inc() {
  count++;
  update();
}
function dec() {
  if (count > 0) {
    count--;
    update();
  } else {
    alert("Counter cannot go below 0");
  }
}


function reset() {
  count = 0;
  update();
}

function one() {
  count += 1;
  update();
}

function five() {
  count += 5;
  update();
}

function ten() {
  count += 10;
  update();
}
