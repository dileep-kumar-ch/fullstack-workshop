let count  = 0;
// let decs = document.getElementById("output").innerText;



function dec() {
  if (count <= 0) {
  alert('Score should not less than "0"');
  return; 
} 
count--;
document.getElementById("output").innerText = count;
  
}

function reset() {
  
document.getElementById("output").innerText = 0;
  
}
function inc() {
  count++;
document.getElementById("output").innerText = count;
  
}

function one() {
    count++;
document.getElementById("output").innerText = count;
  
}

function five() {
count += 5;
document.getElementById("output").innerText = count;
  
}


function ten() {
    count += 10;
document.getElementById("output").innerText = count;
  
}