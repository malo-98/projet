let currentSelection = {};

let changeTab = function (continent) {
    currentSelection.continent = continent;
    currentSelection.country = undefined;
    currentSelection.city = undefined;

    listCountries(continent);
    let tabElements = document.querySelectorAll("#main-nav li.nav-item");
    for (const tabElement of tabElements) {
        if (tabElement.id === 'tab-' + continent.toLowerCase()) {
            tabElement.getElementsByTagName("a")[0].classList.add("active");
        } else {
            tabElement.getElementsByTagName("a")[0].classList.remove("active");
        }
    }
    document.getElementById("no-country-selected").hidden = false;
    document.getElementById("no-city-selected").hidden = true;
    document.getElementById("no-cities-in-country").hidden = true;
    document.getElementById("city-add-button").hidden = true;
    document.getElementById("cities-list").hidden = true;
    document.getElementById("city-details").hidden = true;
    document.getElementById("city-form").hidden = true;
};

let listCountries = function (continent) {
    // TODO
    let countriesRequest = new XMLHttpRequest();
    countriesRequest.open("GET", "api/continents/"+continent+"/countries",true);
    countriesRequest.responseType = "json";

    countriesRequest.onload = function(){
        let countries = this.response;
        refreshCountriesList(countries);
    };

    countriesRequest.send();
};

let selectCountry = function (country) {
    currentSelection.country = country.code;
    listCities(country.code);

    selectLink("countries-list", "country" + country.code);

    document.getElementById("no-country-selected").hidden = true;
    document.getElementById("no-city-selected").hidden = false;
    document.getElementById("no-cities-in-country").hidden = true;
    document.getElementById("city-add-button").hidden = false;
    document.getElementById("cities-list").hidden = true;
    document.getElementById("city-details").hidden = true;
    document.getElementById("city-form").hidden = true;
};

let listCities = function (countryCode) {
    // TODO
    let listCitiesRequest = new XMLHttpRequest();
    listCitiesRequest.open("GET", "api/" +
        "continents/"+currentSelection.continent+"/countries/"+countryCode+"/cities", true);
    listCitiesRequest.responseType = "json";

    listCitiesRequest.onload =function(){
        let citiesList = this.response;
        let element = document.getElementById("cities-list");
        let noElement = document.getElementById("no-cities-in-country");
        if (citiesList.length!=0){
            element.hidden =false;
            noElement.hidden=true;
            refreshCitiesList(citiesList);
        }else {
            element.hidden=true;
            noElement.hidden=false;
        }
    };
    listCitiesRequest.send();
};

let getCityDetail = function (cityId) {
    currentSelection.city = cityId;
    selectLink("cities-list", "city" + cityId);

    // TODO
    let detailRequest = new XMLHttpRequest();
    detailRequest.open("GET", "api/continents/"+currentSelection.continent+"/countries/"+currentSelection.country+"/cities/"+cityId, true);
    detailRequest.responseType="json";

    detailRequest.onload = function(){
        let city = this.response;
        document.getElementById("city-name").innerText = city.name;
        document.getElementById("city-population").innerText = city.population.toLocaleString();
        document.getElementById("city-image").src = city.imageData;
        document.getElementById("no-city-selected").hidden=true;
        document.getElementById("city-form").hidden=true;
        document.getElementById("city-details").hidden=false;
        document.getElementById("city-activities").innerText =city.activities;
    };
    detailRequest.send();
};

let addCountry = function (){
    let countryRequest = new XMLHttpRequest();
    countryRequest.open("POST", "api/continents/"+currentSelection.continent+"/countries", true );
    countryRequest.responseType = "json";

    let countryCode = document.getElementById("country-code-input").value;
    let countryName = document.getElementById("country-name-input").value;

    countryRequest.onload = function () {
        let newCountry = this.response;
        document.getElementById("countries-list").appendChild(createCountryLink(newCountry));
    };

    countryRequest.setRequestHeader("content-type", "application/x-www-form-urlencoded");
    countryRequest.send("code=" + countryCode+ "&name=" + countryName);
};

let addActivity = function(newActivity){
    let activityRequest = new XMLHttpRequest();
    activityRequest.open("POST", "api/continents/"+currentSelection.continent+"/countries/"+currentSelection.country+"/cities/"+currentSelection.city, true);
    activityRequest.responseType = "json";

    activityRequest.onload = function(){
        let add = document.createElement("li");
        add.innerText = newActivity;
        document.getElementById("cities-list").appendChild(add);
    }

    activityRequest.send();
};


window.onload = function () {
    changeTab("EUROPE");

    let addCountryButton = document.getElementById("country-add-button");
    addCountryButton.onclick = function (){
        console.log("Adding country button");
        addCountry();
    };

    let addActivityButton = document.getElementById("add-activity");
    addActivityButton.onclick = function () {
        console.log("Adding country activity");
        addActivity(document.getElementById("new-activity").value);
    }
};