package hei.devweb.cityexplorer.pojos;

public class CityListDto {
    private Integer id;
    private String name;

    public CityListDto(City city) {
        this.id = city.getId();
        this.name = city.getName();
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
