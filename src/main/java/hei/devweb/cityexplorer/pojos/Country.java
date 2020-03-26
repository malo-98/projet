package hei.devweb.cityexplorer.pojos;

public class Country {

    private String code;
    private String name;
    private Continent continent;

    public Country() {
    }

    public Country(String code, String name, Continent continent) {
        this.code = code;
        this.name = name;
        this.continent = continent;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Continent getContinent() {
        return continent;
    }

    public void setContinent(Continent continent) {
        this.continent = continent;
    }
}
