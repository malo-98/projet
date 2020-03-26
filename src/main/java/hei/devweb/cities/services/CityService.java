package hei.devweb.cities.services;

import hei.devweb.cities.daos.CityDao;
import hei.devweb.cities.exceptions.CityAlreadyExistsException;
import hei.devweb.cities.exceptions.CityNotFoundException;
import hei.devweb.cities.pojos.City;
import hei.devweb.cities.pojos.CitySortable;
import hei.devweb.cities.pojos.Department;

import java.util.Comparator;
import java.util.List;

public class CityService {

    private static final int CITIES_PER_PAGE = 20;


    private static class CityServiceHolder {
        private final static CityService instance = new CityService();
    }

    public static CityService getInstance() {
        return CityServiceHolder.instance;
    }

    private CityService() {
    }

    private CityDao cityDao = new CityDao();

    public List<City> listAll() {
        return cityDao.listAll();
    }

    public List<City> list(int pageNumber) {
        int startIndex = (pageNumber - 1) * CITIES_PER_PAGE;
        return cityDao.list(startIndex, CITIES_PER_PAGE, CitySortable.CITY_CODE.getComparator());
    }

    public List<City> list(int pageNumber, String departmentNumber, CitySortable sort) {
        int startIndex = (pageNumber - 1) * CITIES_PER_PAGE;
        Department department = null;
        if(departmentNumber != null) {
            department = Department.fromNumber(departmentNumber);
        }
        if (sort == null) {
            sort = CitySortable.CITY_CODE;
        }
        List<City> cities;
        if(department == null) {
            cities = cityDao.list(startIndex, CITIES_PER_PAGE, sort.getComparator());
        } else {
            cities = cityDao.listByDepartment(department, startIndex, CITIES_PER_PAGE, sort.getComparator());
        }
        return cities;
    }

    public Long count() {
        return cityDao.count();
    }

    public Long count(String departmentNumber) {
        Department department = null;
        if(departmentNumber != null) {
            department = Department.fromNumber(departmentNumber);
        }
        if(department == null) {
            return cityDao.count();
        } else {
            return cityDao.count(department);
        }
    }

    public void addCity(City city) {
        checkCity(city);
        if (cityDao.existsCity(city.getCityCode())) {
            throw new CityAlreadyExistsException(String.format("A city already exist with the code %s.", city.getCityCode()));
        }
        cityDao.addCity(city);
    }

    public void updateCity(City city) {
        checkCity(city);
        if (!cityDao.existsCity(city.getCityCode())) {
            throw new CityNotFoundException(String.format("The city with code %s does not exist and can not be updated.", city.getCityCode()));
        }
        cityDao.updateCity(city);
    }

    public void markCityAsLivedIn(String cityCode) {
        if (!cityDao.existsCity(cityCode)) {
            throw new CityNotFoundException(String.format("The city with code %s does not exist and can not be updated.", cityCode));
        }
        cityDao.markedAsLivedIn(cityCode);
    }

    private void checkCity(City city) {
        if (city == null) {
            throw new IllegalArgumentException("A city must be provided.");
        }
        if (city.getCityCode() == null || "".equals(city.getCityCode())
                || city.getDepartment() == null
                || city.getPostalCode() == null || "".equals(city.getPostalCode())
                || city.getName() == null || "".equals(city.getName())) {
            throw new IllegalArgumentException("All fields of a city must be provided.");
        }
    }

    public void deleteCity(String cityCode) {
        if (cityCode == null) {
            throw new IllegalArgumentException("A city code must be provided in order to delete a city.");
        }
        if (!cityDao.existsCity(cityCode)) {
            throw new CityNotFoundException(String.format("The city with code %s does not exist and can not be deleted.", cityCode));
        }
        cityDao.deleteCity(cityCode);
    }
}
