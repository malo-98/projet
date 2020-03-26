const CITIES_PER_PAGE = 20;
const CREATION = 1;
const UPDATE = 2;

let listParams = {
    page: 1,
    department: undefined,
    sort: undefined
};

let formMode;

let listCities = function () {
    let citiesRequest = new XMLHttpRequest();
    let url = "ws/cities?page=" + listParams.page;
    if (listParams.department) {
        url += "&department=" + listParams.department;
    }
    if (listParams.sort) {
        url += "&sort=" + listParams.sort;
    }
    citiesRequest.open("GET", url, true);
    citiesRequest.responseType = "json";

    citiesRequest.onload = function () {
        let cities = this.response;
        refreshTable(cities);
    };

    citiesRequest.send();
};

let listDepartments = function () {
    let departmentsRequest = new XMLHttpRequest();
    departmentsRequest.open("GET", "ws/departments", true);
    departmentsRequest.responseType = "json";

    departmentsRequest.onload = function () {
        let selectElement = document.getElementById("department");
        let selectFormElement = document.getElementById("department-input");
        let departments = this.response;
        for (const department of departments) {
            let optionElement = document.createElement("option");
            optionElement.innerText = department.number + " - " + department.name;
            optionElement.setAttribute("value", department.number);
            selectElement.appendChild(optionElement);
            selectFormElement.appendChild(optionElement.cloneNode(true));
        }

    };

    departmentsRequest.send();
};

let countCities = function () {
    let countRequest = new XMLHttpRequest();
    let url = "ws/cities/count";
    if (listParams.department) {
        url += "?department=" + listParams.department;
    }
    countRequest.open("GET", url, true);
    countRequest.onload = function () {
        listParams.total = parseInt(this.response);
        listParams.lastPage = Math.ceil(listParams.total / CITIES_PER_PAGE);
        refreshPagination();
    };
    countRequest.send();
};

let goToNextPage = function () {
    if (listParams.page < listParams.lastPage) {
        listParams.page++;
        refreshPagination();
        listCities();
    }
};

let goToPreviousPage = function () {
    if (listParams.page > 1) {
        listParams.page--;
        refreshPagination();
        listCities();
    }
};

let applyParameters = function () {
    let department = document.getElementById("department").value;
    listParams.department = department ? department : undefined;

    let sort = document.getElementById("sort").value;
    listParams.sort = sort ? sort : undefined;

    listParams.page = 1;

    countCities();
    listCities();
    return false;
};

let deleteCity = function (city) {
    if (confirm("Are you sure you want to delete " + city.name + " (" + city.cityCode + ") ?")) {
        let deleteRequest = new XMLHttpRequest();
        deleteRequest.open("DELETE", "ws/cities/" + city.cityCode, true);

        deleteRequest.onload = function () {
            countCities();
            listCities();
        };

        deleteRequest.send();
    }
};

let showCity = function (city) {
    formMode = city.cityCode ? UPDATE : CREATION;

    document.getElementById("code-input").value = city.cityCode;
    document.getElementById("code-input").disabled = !!city.cityCode;
    document.getElementById("department-input").value = city.department.number;
    document.getElementById("name-input").value = city.name;
    document.getElementById("postal-input").value = city.postalCode;

    document.getElementById("list").hidden = true;
    document.getElementById("form").hidden = false;
};

let saveForm = function () {
    let city = {
        cityCode: document.getElementById("code-input").value,
        departmentNumber: document.getElementById("department-input").value,
        name: document.getElementById("name-input").value,
        postalCode: document.getElementById("postal-input").value
    };

    if (formMode === CREATION) {
        createCity(city);
    } else {
        updateCity(city);
    }


    return false;
};

let createCity = function (city) {
    let saveRequest = new XMLHttpRequest();
    saveRequest.open("POST", "ws/cities", true);

    saveRequest.onload = function () {
        if (this.status === 201) {
            countCities();
            listCities();
            document.getElementById("form").hidden = true;
            document.getElementById("list").hidden = false;
        } else if (this.status === 204) {
            alert("The city creation should return a CREATED response status!");
        } else if (this.status === 409) {
            alert("The city code already exists!");
        }
    };
    saveRequest.setRequestHeader("content-type", "application/json");
    saveRequest.send(JSON.stringify(city));
};

let updateCity = function (city) {
    let saveRequest = new XMLHttpRequest();
    saveRequest.open("PATCH", "ws/cities/" + city.cityCode, true);

    saveRequest.onload = function () {
        if (this.status === 204) {
            countCities();
            listCities();
            document.getElementById("form").hidden = true;
            document.getElementById("list").hidden = false;
        } else if (this.status === 404) {
            alert("The updated city does not exist!");
        }
    };
    saveRequest.setRequestHeader("content-type", "application/x-www-form-urlencoded");
    saveRequest.send("department=" + city.departmentNumber + "&name=" + city.name + "&postalCode=" + city.postalCode);
};

let markAsLivedIn = function (city) {
    console.log("I have lived in this city : " + city.cityCode);

    let livedInRequest = new XMLHttpRequest();
    livedInRequest.open("PATCH", "ws/cities/" + city.cityCode + "/livein");

    livedInRequest.onload = function () {
        document.getElementById("city-" + city.cityCode).classList.add("bg-warning");
        document.querySelector("#city-" + city.cityCode + " .lived-in-button").disabled = true;
    };

    livedInRequest.send();
};



let refreshPagination = function () {
    let currentPage = listParams.page;
    let lastPage = listParams.lastPage;

    if (currentPage === 1) {
        document.getElementById("pagination-goto-previous").classList.add("disabled");
    } else {
        document.getElementById("pagination-goto-previous").classList.remove("disabled");
    }
    document.querySelector("#pagination-current a").innerText = currentPage + " of " + lastPage;
    if (currentPage >= lastPage) {
        document.getElementById("pagination-goto-next").classList.add("disabled");
    } else {
        document.getElementById("pagination-goto-next").classList.remove("disabled");
    }

};

let refreshTable = function (cities) {
    let tableElement = document.querySelector("#cities-list tbody");
    var newTableElement = tableElement.cloneNode(false);
    let compteur = 0;
    for (const city of cities) {
        newTableElement.appendChild(buildCityTableLine(city));
        compteur++;
        if (compteur > 1000) {
            alert("Too many cities are returned! (more than 1000) For performance reason, only 1000 cities are displayed.");
            break;
        }
    }
    tableElement.parentNode.replaceChild(newTableElement, tableElement);
};


let buildCityTableLine = function (city) {
    let lineElement = document.createElement("tr");
    lineElement.id = "city-" + city.cityCode;
    if (city.livedIn) {
        lineElement.classList.add("bg-warning");
    }

    lineElement.appendChild(createTableCell(city.cityCode));
    lineElement.appendChild(createTableCell(city.department.name));
    lineElement.appendChild(createTableCell(city.name, true));
    lineElement.appendChild(createTableCell(city.postalCode));

    let actionCell = document.createElement("td");
    let buttonGroupElement = document.createElement("div");
    buttonGroupElement.classList.add("btn-group");
    actionCell.appendChild(buttonGroupElement);

    let showButton = document.createElement("button");
    showButton.classList.add("btn", "btn-primary", "btn-sm");
    showButton.innerHTML = "<i class=\"fas fa-eye\"></i>";
    showButton.title = "Modify this city";
    showButton.onclick = function () {
        showCity(city);
    };
    buttonGroupElement.appendChild(showButton);

    let deleteButton = document.createElement("button");
    deleteButton.classList.add("btn", "btn-danger", "btn-sm");
    deleteButton.innerHTML = "<i class=\"fas fa-trash-alt\"></i>";
    deleteButton.title = "Delete this city";
    deleteButton.onclick = function () {
        deleteCity(city);
    };
    buttonGroupElement.appendChild(deleteButton);

    let livedInButton = document.createElement("button");
    livedInButton.classList.add("btn", "btn-success", "btn-sm", "lived-in-button");
    livedInButton.innerHTML = "<i class=\"fas fa-home\"></i>";
    livedInButton.title = "I have lived in this city";
    livedInButton.disabled = city.livedIn;
    livedInButton.onclick = function () {
        markAsLivedIn(city);
    };
    buttonGroupElement.appendChild(livedInButton);


    lineElement.appendChild(actionCell);

    return lineElement;
};

let createTableCell = function (text, header = false) {
    let cellElement;
    if (header) {
        cellElement = document.createElement("th");
        cellElement.setAttribute("scope", "row");
    } else {
        cellElement = document.createElement("td");
    }
    cellElement.innerText = text;
    return cellElement;
};

window.onload = function () {
    document.getElementById("form").hidden = true;

    listDepartments();
    countCities();
    listCities();

    document.getElementById("pagination-goto-previous").onclick = goToPreviousPage;
    document.getElementById("pagination-goto-next").onclick = goToNextPage;
    document.getElementById("list-parameters").onsubmit = applyParameters;

    document.getElementById("add-button").onclick = function () {
        showCity({cityCode: "", department: {}, name: "", postalCode: ""});
    };

    document.getElementById("go-back").onclick = function () {
        document.getElementById("form").hidden = true;
        document.getElementById("list").hidden = false;
    };

    document.getElementById("save-form").onclick = saveForm;
};