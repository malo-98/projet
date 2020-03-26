

/*
    Empties the list of countries and fill it with the countries sent as parameter
 */
let refreshCountriesList = function (countries) {
    let oldCountriesListElement = document.getElementById("countries-list");
    let newCountriesListElement = oldCountriesListElement.cloneNode(false);
    for (const country of countries) {
        newCountriesListElement.appendChild(createCountryLink(country));
    }
    oldCountriesListElement.parentNode.replaceChild(newCountriesListElement, oldCountriesListElement);
};

/*
    Empties the list of cities and fill it with the cities sent as parameter
 */
let refreshCitiesList = function (cities) {
    let oldCitiesListElement = document.getElementById("cities-list");
    let newCitiesListElement = oldCitiesListElement.cloneNode(false);
    for (const city of cities) {
        newCitiesListElement.appendChild(createCityLink(city));
    }
    oldCitiesListElement.parentNode.replaceChild(newCitiesListElement, oldCitiesListElement);
};

/*
    Extracts the image added to the file input of the city form
    Then it calls the callback fucntion with the image as Base64 string as a parameter

    getImageFileAsBase64(function(base64Image) {
        console.log(bas64Image);
    });
 */
let getImageFileAsBase64 = function (callback) {
    let uploadedFileList = document.getElementById("city-image-input").files;
    if (uploadedFileList.length === 0) {
        callback();
        return;
    }
    let uploadedImage = uploadedFileList[0];

    let reader = new FileReader();
    reader.onload = function () {
        callback(reader.result);
    };
    reader.readAsDataURL(uploadedImage);
};

/*
    Create a country link element for the list
 */
let createCountryLink = function (country) {
    return createListLink("country" + country.code, country.name, function () {
        selectCountry(country);
    });
};

/*
    Create an city link element for the list
 */
let createCityLink = function (city) {
    return createListLink("city" + city.id, city.name, function () {
        getCityDetail(city.id);
    });
};

/*
    Create a generic link element for the lists
 */
let createListLink = function (id, text, action) {
    let linkElement = document.createElement("a");
    linkElement.id = id;
    linkElement.classList.add("list-group-item", "list-group-item-action");
    linkElement.innerHTML = text;
    linkElement.href = "#";
    linkElement.onclick = action;
    return linkElement;
};

/*
    Selects a link with "itemId" in a list with "listId"
 */
let selectLink = function (listId, itemId) {
    let listItems = document.querySelectorAll("#" + listId + " a");
    for (const groupItem of listItems) {
        if (groupItem.id === itemId) {
            groupItem.classList.add("active");
        } else {
            groupItem.classList.remove("active");
        }
    }
};