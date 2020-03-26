package hei.devweb.cities.pojos;

import java.util.Comparator;

public enum CitySortable {
    CITY_CODE(Comparator.comparing(City::getCityCode)),
    DEPARTMENT(Comparator.comparing(City::getDepartment)),
    NAME(Comparator.comparing(City::getName)),
    POSTAL_CODE(Comparator.comparing(City::getPostalCode));

    private Comparator<City> comparator;

    CitySortable(Comparator<City> comparator) {
        this.comparator = comparator;
    }

    public Comparator<City> getComparator() {
        return comparator;
    }
}
