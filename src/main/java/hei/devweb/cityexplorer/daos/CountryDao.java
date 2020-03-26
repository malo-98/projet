package hei.devweb.cityexplorer.daos;

import hei.devweb.cityexplorer.exceptions.CountryAlreadyExistsException;
import hei.devweb.cityexplorer.pojos.Continent;
import hei.devweb.cityexplorer.pojos.Country;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CountryDao {

    private List<Country> countries;

    public CountryDao() {
        this.initCountries();
    }

    public List<Country> listAll() {
        return countries;
    }

    public List<Country> listByContinent(Continent continent) {
        return countries.stream()
                .filter(c -> c.getContinent().equals(continent))
                .collect(Collectors.toList());
    }

    public Country getByCode(String code) {
        if (code == null) {
            return null;
        }
        return countries.stream().filter(c -> c.getCode().equals(code.toUpperCase())).findFirst().orElse(null);
    }

    public Country addCountry(Country country) {
        if(getByCode(country.getCode()) != null) {
            throw new CountryAlreadyExistsException(String.format("Country wit code %s already exists.", country.getCode()));
        }

        countries.add(country);

        return country;
    }

    private void initCountries() {
        countries = new ArrayList<>();
        // AFRICA
        countries.add(new Country("EG", "Egypt", Continent.AFRICA));
        countries.add(new Country("NG", "Nigeria", Continent.AFRICA));

        // AMERICA
        countries.add(new Country("US", "United States of America", Continent.AMERICA));

        // ASIA
        countries.add(new Country("IN", "India", Continent.ASIA));
        countries.add(new Country("JP", "Japan", Continent.ASIA));
        countries.add(new Country("CN", "China", Continent.ASIA));

        // EUROPE
        countries.add(new Country("FR", "France", Continent.EUROPE));
        countries.add(new Country("UK", "United Kingdom", Continent.EUROPE));
        countries.add(new Country("DE", "Germany", Continent.EUROPE));
        countries.add(new Country("IT", "Italy", Continent.EUROPE));

        // OCEANIA
        countries.add(new Country("AU", "Australia", Continent.OCEANIA));
    }
}
