package hei.devweb.cities.pojos;

public class City {
    private String cityCode;
    private String name;
    private String postalCode;
    private Department department;
    private boolean livedIn;

    public City() {
    }

    public City(CityDto cityDto) {
        this.cityCode = cityDto.getCityCode();
        this.name = cityDto.getName();
        this.postalCode = cityDto.getPostalCode();
        this.department = Department.fromNumber(cityDto.getDepartmentNumber());
    }

    public City(String cityCode, String name, String postalCode, Department department) {
        this.cityCode = cityCode;
        this.name = name;
        this.postalCode = postalCode;
        this.department = department;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public boolean isLivedIn() {
        return livedIn;
    }

    public void setLivedIn(boolean livedIn) {
        this.livedIn = livedIn;
    }
}
