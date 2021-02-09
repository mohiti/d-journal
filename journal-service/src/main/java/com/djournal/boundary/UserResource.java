package com.djournal.boundary;

import java.net.URI;
import java.util.Objects;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.container.ResourceContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import com.djournal.domain.User;
import com.djournal.domain.UserRepo;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * The User REST resource implementation.
 */
@Path("users")
@RequestScoped
public class UserResource {
	
		@Inject
	    private UserRepo userRepo;
	    @Context
	    private ResourceContext context;
	   
	    @GET
	    @Produces(MediaType.APPLICATION_JSON)
	    @ApiOperation(value = "Find all users")
	    @ApiResponses({
	      @ApiResponse(code=200, message="success"),
	      @ApiResponse(code=404, message="Not found")
	    })
	    public Response users() {
	        return Response.ok(userRepo.findAll()).build();
	    }
	    
	    @POST
	    @Consumes(MediaType.APPLICATION_JSON)
	    @ApiOperation(value = "Create user")
	    @ApiResponses({
	      @ApiResponse(code=200, message="success"),
	      @ApiResponse(code=409, message="conflict")
	    })
	    public Response create(@ApiParam(required=true) User user) {
	        if (userRepo.exists(user.getName())) {
	            return Response.status(Response.Status.CONFLICT).build();
	        }

	        userRepo.create(user);
	        URI location = UriBuilder.fromResource(UserResource.class)
	                .path("/{id}")
	                .resolveTemplate("id", user.getId())
	                .build();
	        return Response.created(location).build();
	    }
	    
	    @GET
	    @Path("/{id}")
	    @ApiOperation(value = "Get user")
	    @ApiResponses({
	      @ApiResponse(code=200, message="success"),
	      @ApiResponse(code=404, message="not found")
	    })
	    public Response get(@PathParam("id") String id) {
	        User user = userRepo.findByID(id);
	        return Response.ok(user).build();
	    }
	    
	    @PUT
	    @Path("/{id}")
	    @ApiOperation(value = "Update user")
	    @ApiResponses({
	      @ApiResponse(code=200, message="success"),
	      @ApiResponse(code=404, message="not found")
	    })
	    public Response update(@ApiParam(required=true) @PathParam("id") String id, @ApiParam(required=true) User user) {
	        if (!Objects.equals(id, user.getId())) {
	            // return Response.status(Response.Status.BAD_REQUEST).build();
	            // throw new WebApplicationException("ISBN must match path parameter.", Response.Status.BAD_REQUEST);
	            throw new BadRequestException("ID must match path parameter.");
	        }
	        userRepo.update(id, user);
	        return Response.ok().build();
	    }
	    
	    @DELETE
	    @Path("/{id}")
	    @ApiOperation(value = "Delete user")
	    @ApiResponses({
	      @ApiResponse(code=200, message="success"),
	      @ApiResponse(code=404, message="not found")
	    })
	    public Response delete(@ApiParam(required=true) @PathParam("id") String id) {
	    	userRepo.delete(id);
	        return Response.ok().build();
	    }
	    
	    @Path("/{id}/journals")
	    @ApiOperation(value = "Get user journals")
	    @ApiResponses({
	      @ApiResponse(code=200, message="success"),
	      @ApiResponse(code=404, message="not found")
	    })
	    public JournalResource journals(@ApiParam(required=true) @PathParam("id") String id) {
	    	JournalResource journalResource = context.getResource(JournalResource.class);
	    	journalResource.setUserId(id);

	        return journalResource;
	    }


}
