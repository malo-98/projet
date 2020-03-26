package hei.devweb.cityexplorer.controllers;


import hei.devweb.cityexplorer.pojos.Continent;
import hei.devweb.cityexplorer.pojos.Country;
import hei.devweb.cityexplorer.services.CountryService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.awt.*;
import java.util.List;

@Path("/continents/{continent}/countries")
public class CountryController {

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public List<Country> listByContinent(@PathParam("continent")Continent continent){
        return CountryService.getInstance().listByContinent(continent);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Country addCountry(@PathParam("continent")Continent continent,
                              @FormParam("code")String code,
                              @FormParam("name")String name){
        return CountryService.getInstance().addCountry(code, name, continent);
    }
}
