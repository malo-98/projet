package hei.devweb.cityexplorer.daos;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import hei.devweb.cityexplorer.pojos.City;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CityDao {

    private List<City> cities;

    public CityDao() {
        try {
            this.init();
        } catch (Exception e) {
            throw new RuntimeException("Problem when initializing the list of cities.", e);
        }
    }

    public List<City> listAll() {
        return cities;
    }

    public List<City> listByCountry(String countryCode) {
        return cities.stream()
                .filter(c -> c.getCountry().getCode().equals(countryCode.toUpperCase()))
                .collect(Collectors.toList());
    }

    public City getById(Integer cityId) {
        return cities.stream()
                .filter(c -> c.getId().equals(cityId))
                .findFirst()
                .orElse(null);
    }

    public City createCity(City city) {
        Integer id = cities.stream().mapToInt(City::getId).max().orElse(0) + 1;
        city.setId(id);

        cities.add(city);

        return city;
    }

    public void addActivity(Integer cityId, String activity) {
        cities.stream()
                .filter(c -> c.getId().equals(cityId))
                .findFirst()
                .ifPresent(c -> c.getActivities().add(activity));
    }

    private void init() throws Exception {
        cities = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        CountryDao countryDao = new CountryDao();
        try (InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("cities.json")) {
            JsonNode citiesJson = objectMapper.readTree(inputStream);
            Iterator<Map.Entry<String, JsonNode>> countryIterator = citiesJson.fields();
            while (countryIterator.hasNext()) {
                Map.Entry<String, JsonNode> countryCities = countryIterator.next();
                if (countryCities.getValue().isArray()) {
                    for (JsonNode cityJson : countryCities.getValue()) {
                        City city = objectMapper.treeToValue(cityJson, City.class);
                        city.setCountry(countryDao.getByCode(countryCities.getKey()));
                        cities.add(city);
                    }
                }
            }
        }
    }
}
