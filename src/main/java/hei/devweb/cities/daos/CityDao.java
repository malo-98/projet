package hei.devweb.cities.daos;

import hei.devweb.cities.exceptions.CityNotFoundException;
import hei.devweb.cities.pojos.City;
import hei.devweb.cities.pojos.Department;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class CityDao {

    private List<City> cities;

    public CityDao() {
        try {
            this.initCities();
        } catch (Exception e) {
            throw new RuntimeException("Problem when initializing the list of cities.", e);
        }
    }

    public List<City> listAll() {
        return cities;
    }

    public List<City> list(int fromIndex, int number, Comparator<City> comparator) {
        return cities.stream()
                .sorted(comparator)
                .skip(fromIndex)
                .limit(number)
                .collect(Collectors.toList());
    }

    public List<City> listByDepartment(Department department, int fromIndex, int number, Comparator<City> comparator) {
        return cities.stream()
                .filter(c -> c.getDepartment().equals(department))
                .sorted(comparator)
                .skip(fromIndex)
                .limit(number)
                .collect(Collectors.toList());
    }

    public Long count() {
        return (long) cities.size();
    }

    public Long count(Department department) {
        return cities.stream()
                .filter(c -> c.getDepartment().equals(department)).count();
    }

    public void addCity(City newCity) {
        cities.add(newCity);
    }

    public boolean existsCity(String cityCode) {
        return cities.stream().anyMatch(c -> c.getCityCode().equals(cityCode));
    }

    public void updateCity(City city) {
        cities.stream()
                .filter(c -> c.getCityCode().equals(city.getCityCode()))
                .findFirst()
                .ifPresent(c-> {
                    c.setDepartment(city.getDepartment());
                    c.setName(city.getName());
                    c.setPostalCode(city.getPostalCode());
                });
    }

    public void markedAsLivedIn(String cityCode) {
        cities.stream()
                .filter(c -> c.getCityCode().equals(cityCode))
                .findFirst()
                .ifPresent(c -> c.setLivedIn(true));
    }

    public void deleteCity(String cityCode) {
        Iterator<City> iterator = cities.iterator();
        while (iterator.hasNext()) {
            City city = iterator.next();
            if (city.getCityCode().equals(cityCode)) {
                iterator.remove();
                return;
            }
        }
    }

    private void initCities() throws Exception {
        this.cities = new ArrayList<>();
        Path citiesFile = Paths.get(this.getClass().getClassLoader().getResource("liste_villes_francaises.csv").toURI());
        try (BufferedReader reader = Files.newBufferedReader(citiesFile)) {
            String line;
            while ((line = reader.readLine()) != null) {
                cities.add(parseCity(line));
            }
        }
    }

    private City parseCity(String line) {
        String[] cityInfos = line.split(";");
        String cityCode = cityInfos[0];
        String postalCode = String.format("%05d", Integer.parseInt(cityInfos[1]));
        String name = cityInfos[2];
        if (!"".equals(cityInfos[3])) {
            String article = cityInfos[3];
            if (!article.endsWith("'")) {
                article += " ";
            }
            name = article + name;
        }
        String departmentNumber = cityInfos[4];
        if (departmentNumber.length() == 1) {
            departmentNumber = "0" + departmentNumber;
        } else if ("97".equals(departmentNumber)) {
            departmentNumber = postalCode.substring(0, 3);
        }
        Department department = Department.fromNumber(departmentNumber);
        return new City(cityCode, name, postalCode, department);
    }

    public static void main(String[] args) {
        CityDao cityDao = new CityDao();
        List<City> cities = cityDao.listAll();
        for (City city : cities) {
            System.out.println(String.format("%s %s (%s)", city.getName(), city.getPostalCode(), city.getDepartment().getName()));
        }
    }

}
