let tasks = JSON.parse(localStorage.getItem("tasks")) || [];
let currentFilter = "All";

const taskInput = document.getElementById("taskInput");
const categorySelect = document.getElementById("category");
const taskList = document.getElementById("taskList");
const workCountEl = document.getElementById("workCount");
const personalCountEl = document.getElementById("personalCount");

const addTask = () => {
  const text = taskInput.value.trim();
  const category = categorySelect.value;

  if (text === "") return;

  tasks.push({
    text,
    category,
    completed: false
  });

  taskInput.value = "";
  saveAndRender();
};

const toggleTask = index => {
  tasks[index].completed = !tasks[index].completed;
  saveAndRender();
};

const deleteTask = index => {
  tasks.splice(index, 1);
  saveAndRender();
};

const filterTasks = type => {
  currentFilter = type;
  renderTasks();
};

const saveAndRender = () => {
  localStorage.setItem("tasks", JSON.stringify(tasks));
  renderTasks();
};

const renderTasks = () => {
  taskList.innerHTML = "";

  // Filter tasks using modern array method
  const filteredTasks =
    currentFilter === "All"
      ? tasks
      : tasks.filter(task => task.category === currentFilter);

  // Count categories using reduce
  const counts = tasks.reduce(
    (acc, task) => {
      if (task.category === "Work") acc.work++;
      if (task.category === "Personal") acc.personal++;
      return acc;
    },
    { work: 0, personal: 0 }
  );

  workCountEl.textContent = counts.work;
  personalCountEl.textContent = counts.personal;

  // Render using map
  filteredTasks.map((task, index) => {
    const li = document.createElement("li");
    li.className =
      "list-group-item d-flex justify-content-between align-items-center";

    li.innerHTML = `
      <div>
        <input 
          type="checkbox" 
          class="me-2"
          ${task.completed ? "checked" : ""}
          onclick="toggleTask(${index})"
        />
        <span style="text-decoration:${task.completed ? "line-through" : "none"}">
          ${task.text}
        </span>
        <span class="badge bg-secondary ms-2">${task.category}</span>
      </div>
      <button 
        class="btn btn-sm btn-danger"
        onclick="deleteTask(${index})"
      >
        X
      </button>
    `;

    taskList.appendChild(li);
  });
};

renderTasks();
