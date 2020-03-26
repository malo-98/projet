package hei.devweb.cityexplorer.pojos;

import java.util.List;

public class City {
    private Integer id;
    private String name;
    private Integer population;
    private Country country;
    private List<String> activities;
    private String imageData;

    public City() {
    }

    public City(CityCreationDto creationDto) {
        this.name = creationDto.getName();
        this.population = creationDto.getPopulation();
        this.activities = creationDto.getActivities();
        this.imageData = creationDto.getImage();
    }

    public City(Integer id, String name, Integer population, Country country, List<String> activities, String imageData) {
        this.id = id;
        this.name = name;
        this.population = population;
        this.country = country;
        this.activities = activities;
        this.imageData = imageData;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPopulation() {
        return population;
    }

    public void setPopulation(Integer population) {
        this.population = population;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public List<String> getActivities() {
        return activities;
    }

    public void setActivities(List<String> activities) {
        this.activities = activities;
    }

    public String getImageData() {
        return imageData;
    }

    public void setImageData(String imageData) {
        this.imageData = imageData;
    }
}
