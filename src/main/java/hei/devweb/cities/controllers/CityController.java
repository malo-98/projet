package hei.devweb.cities.controllers;

import hei.devweb.cities.exceptions.CityAlreadyExistsException;
import hei.devweb.cities.pojos.City;
import hei.devweb.cities.pojos.CityDto;
import hei.devweb.cities.pojos.CitySortable;
import hei.devweb.cities.pojos.Department;
import hei.devweb.cities.services.CityService;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/cities")
public class CityController {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<City> listCities(
            @QueryParam("page") Integer pageNumber,
            @QueryParam("department") String departmentNumber,
            @QueryParam("sort") CitySortable sort) {
        return CityService.getInstance().list(pageNumber, departmentNumber, sort);
    }

    @GET
    @Path("/count")
    public Long countCities(@QueryParam("department") String departmentNumber) {
        return CityService.getInstance().count(departmentNumber);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addCity(CityDto cityDto) {
        try {
            City city = new City(cityDto);
            CityService.getInstance().addCity(city);
            return Response.status(Response.Status.CREATED).build();
        } catch (CityAlreadyExistsException e) {
            System.err.println(e.getMessage());
            return Response.status(Response.Status.CONFLICT).build();
        }
    }

    @PATCH
    @Path("/{cityCode}")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public void updateCity(
            @PathParam("cityCode") String cityCode,
            @FormParam("name") String name,
            @FormParam("postalCode") String postalCode,
            @FormParam("department") String departmentNumber) {
        City city = new City(
                cityCode, name, postalCode,
                Department.fromNumber(departmentNumber));
        CityService.getInstance().updateCity(city);
    }

    @PATCH
    @Path("/{cityCode}/livein")
    public void markCityAsLivedIn(
            @PathParam("cityCode") String cityCode) {
        CityService.getInstance().markCityAsLivedIn(cityCode);
    }

    @DELETE
    @Path("/{cityCode}")
    public void deleteCity(
            @PathParam("cityCode") String cityCode) {
        CityService.getInstance().deleteCity(cityCode);
    }
}
