package hei.devweb.cityexplorer.pojos;

import java.util.ArrayList;
import java.util.List;

public class CityCreationDto {
    private String name;
    private Integer population;
    private List<String> activities = new ArrayList<>();
    private String image;

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

    public List<String> getActivities() {
        return activities;
    }

    public void setActivities(List<String> activities) {
        this.activities = activities;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
