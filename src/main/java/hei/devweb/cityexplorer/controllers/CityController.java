package hei.devweb.cityexplorer.controllers;


import hei.devweb.cityexplorer.pojos.City;
import hei.devweb.cityexplorer.pojos.CityListDto;
import hei.devweb.cityexplorer.pojos.Continent;
import hei.devweb.cityexplorer.services.CityService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/continents/{continent}/countries/{code}/cities")
public class CityController {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<CityListDto> listByCountry(
            @PathParam("continent") Continent continent,
            @PathParam("code") String code){

        return CityService.getInstance().listByCountryCode(code);
    }



    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{cityId}")
    public City getCity(
            @PathParam("cityId") Integer cityId){
        return CityService.getInstance().getById(cityId);
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Path("{cityId}")
    public void addActivity(@PathParam("cityId")Integer cityId,
                            @FormParam("activity") String activity) {
        CityService.getInstance().addActivity(cityId,activity);

    }


}
