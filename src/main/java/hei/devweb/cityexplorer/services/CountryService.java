package hei.devweb.cityexplorer.services;

import hei.devweb.cityexplorer.daos.CountryDao;
import hei.devweb.cityexplorer.pojos.Continent;
import hei.devweb.cityexplorer.pojos.Country;

import java.util.List;

public class CountryService {

    private static class CountryServiceHolder {
        private final static CountryService instance = new CountryService();
    }

    public static CountryService getInstance() {
        return CountryServiceHolder.instance;
    }

    private CountryService() {

    }

    private CountryDao countryDao = new CountryDao();

    public List<Country> listAll() {
        return countryDao.listAll();
    }

    public List<Country> listByContinent(Continent continent) {
        return countryDao.listByContinent(continent);
    }

    public Country getByCode(String code) {
        return countryDao.getByCode(code);
    }

    public Country addCountry(String code, String name, Continent continent) {
        if (code == null || "".equals(code)) {
            throw new IllegalArgumentException("Code is mandatory");
        }
        if (name == null || "".equals(name)) {
            throw new IllegalArgumentException("Name is mandatory");
        }
        Country country = new Country(code, name, continent);
        return countryDao.addCountry(country);
    }
}
