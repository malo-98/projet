package hei.devweb.cityexplorer.services;

import hei.devweb.cityexplorer.daos.CityDao;
import hei.devweb.cityexplorer.exceptions.CityNotFoundException;
import hei.devweb.cityexplorer.exceptions.TooManyActivitiesException;
import hei.devweb.cityexplorer.pojos.City;
import hei.devweb.cityexplorer.pojos.CityCreationDto;
import hei.devweb.cityexplorer.pojos.CityListDto;

import java.util.List;
import java.util.stream.Collectors;

public class CityService {
    private static class CityServiceHolder {
        private final static CityService instance = new CityService();
    }

    public static CityService getInstance() {
        return CityServiceHolder.instance;
    }

    private CityService() {
    }

    public CityDao cityDao = new CityDao();

    public List<CityListDto> listByCountryCode(String countryCode) {
        return cityDao.listByCountry(countryCode).stream()
                .map(CityListDto::new)
                .collect(Collectors.toList());
    }

    public City getById(Integer cityId) {
        return cityDao.getById(cityId);
    }

    public City createCity(CityCreationDto cityDto, String countryCode) {
        if (cityDto == null) {
            throw new IllegalArgumentException("No city is provided.");
        }
        if (cityDto.getName() == null || "".equals(cityDto.getName())) {
            throw new IllegalArgumentException("The name is mandatory.");
        }
        if (cityDto.getPopulation() == null) {
            throw new IllegalArgumentException("The population is mandatory.");
        }
        if (cityDto.getImage() == null || "".equals(cityDto.getImage())) {
            throw new IllegalArgumentException("The image is mandatory.");
        }
        City city = new City(cityDto);
        city.setCountry(CountryService.getInstance().getByCode(countryCode));

        return cityDao.createCity(city);
    }

    public void addActivity(Integer cityId, String activity) {
        City city = getById(cityId);
        if (city == null) {
            throw new CityNotFoundException("City not found for id " + cityId);
        }
        if (city.getActivities().size() >= 5) {
            throw new TooManyActivitiesException("A city can't have more than 5 activities.");
        }
        cityDao.addActivity(cityId, activity);
    }
}
