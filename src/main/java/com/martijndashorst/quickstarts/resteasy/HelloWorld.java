package com.martijndashorst.quickstarts.resteasy;

/**
 * Created by guo on 12/06/2017.
 */
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.jaxrs.config.BeanConfig;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/hello")
//@Produces(MediaType.TEXT_PLAIN)
@Produces("application/json")
@Api(value = "hello world1")
public class HelloWorld {
    @GET
    @Produces("application/json")
    @ApiOperation(value = "return hello world")
    public Response hello(@QueryParam("username") String username) {
        if(username == null){
            return Response.status(400).build();
        }
        String result ="{response:Hello World " + username + "}";
        return Response.status(201).entity(result).build();
    }
}
