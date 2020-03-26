package hei.devweb.cities.controllers;

import hei.devweb.cities.pojos.Department;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Arrays;
import java.util.List;

@Path("/departments")
public class DepartmentController {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Department> listAll() {
        return Arrays.asList(Department.values());
    }
}
