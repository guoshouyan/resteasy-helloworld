package com.martijndashorst.quickstarts.resteasy;

/**
 * Created by guo on 12/06/2017.
 */
import io.swagger.annotations.*;
import io.swagger.jaxrs.config.BeanConfig;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/hello")
@Produces("application/json")
@Api(tags = "Hello")
public class HelloWorld {
    @GET
    @Produces("application/json")
    @ApiOperation(value = "return hello world")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success",
                    response = String.class),
            @ApiResponse(code = 404, message = "query param 'username' is missing") })
    public Response hello(@ApiParam(value = "username", required = true) @QueryParam("username") String username) {
        if(username == null){
            return Response.status(400).build();
        }
        String result ="{response:Hello World " + username + "}";
        return Response.status(201).entity(result).build();
    }
}
