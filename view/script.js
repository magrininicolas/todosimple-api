const url = "http://localhost:8080/task/user/1";

function hideLoader() {
  document.getElementById("loading").style.display = "none";
}

function show(tasks) {
  let tab = `<thead>
          <th scope="col">#</th>
          <th scope="col">Username</th>
          <th scope="col">Description</th>
          </thead>`;

  for (let task of tasks) {
    tab += `
      <tr>
        <td scope="row">${task.id}</td>
        <td>${task.user.username}</td>
        <td>${task.description}</td>
      </tr>
    `;
  }

  document.getElementById("tasks").innerHTML = tab;
}

async function getAPI(url) {
  const response = await fetch(url, { method: "GET" });

  let data = await response.json();

  if (response) hideLoader();

  show(data);
}

getAPI(url);
