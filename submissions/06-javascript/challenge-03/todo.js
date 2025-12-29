let tasks = JSON.parse(localStorage.getItem("tasks")) || [];
let currentFilter = "All";

function addTask() {
  const input = document.getElementById("taskInput");
  const category = document.getElementById("category").value;

  if (input.value.trim() === "") return;

  tasks.push({
    text: input.value,
    category: category,
    completed: false
  });

  input.value = "";
  saveAndRender();
}

function toggleTask(index) {
  tasks[index].completed = !tasks[index].completed;
  saveAndRender();
}

function deleteTask(index) {
  tasks.splice(index, 1);
  saveAndRender();
}

function filterTasks(type) {
  currentFilter = type;
  renderTasks();
}

function saveAndRender() {
  localStorage.setItem("tasks", JSON.stringify(tasks));
  renderTasks();
}

function renderTasks() {
  const list = document.getElementById("taskList");
  list.innerHTML = "";

  let workCount = 0;
  let personalCount = 0;

  tasks.forEach((task, index) => {
    if (currentFilter !== "All" && task.category !== currentFilter) return;

    if (task.category === "Work") workCount++;
    if (task.category === "Personal") personalCount++;

    const li = document.createElement("li");
    li.className = "list-group-item d-flex justify-content-between align-items-center";

    li.innerHTML = `
      <div>
        <input type="checkbox" ${task.completed ? "checked" : ""} 
               onclick="toggleTask(${index})" class="me-2">
        <span style="${task.completed ? "text-decoration:line-through" : ""}">
          ${task.text}
        </span>
        <span class="badge bg-secondary ms-2">${task.category}</span>
      </div>
      <button class="btn btn-sm btn-danger" onclick="deleteTask(${index})">X</button>
    `;

    list.appendChild(li);
  });

  document.getElementById("workCount").innerText = workCount;
  document.getElementById("personalCount").innerText = personalCount;
}

renderTasks();
