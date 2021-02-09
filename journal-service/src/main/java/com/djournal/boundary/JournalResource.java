package com.djournal.boundary;


import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import com.djournal.domain.Journal;
import com.djournal.domain.JournalRepo;
import com.djournal.domain.User;
import com.djournal.domain.UserRepo;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Sub REST resource implementation for journals.
 */

@RequestScoped
public class JournalResource {

    @Inject
    private JournalRepo journalRepo;
    
    @Inject
    private UserRepo userRepo;
    
    
    @Inject
    private Logger logger;
    
    
    private String userId;
    
    
    public void setUserId(String userId) {
		this.userId = userId;
	}


	@GET
    @Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Find all user 's journals")
    @ApiResponses({
      @ApiResponse(code=200, message="success"),
      @ApiResponse(code=404, message="Not found")
    })
    public Response journals() {
		 logger.log(Level.INFO, "Getting journals for user with ID {0}.", userId);
	        User user = userRepo.findByID(userId);
	        return Response.ok(user.getJournals()).build();
    }
	
	@POST
    @Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "create journal")
    @ApiResponses({
      @ApiResponse(code=200, message="success"),
      @ApiResponse(code=404, message="Not found")
    })
    public Response createJournal(@ApiParam(required=true) Journal journal) {
		journalRepo.createJournal(userId, journal);
        URI location = UriBuilder.fromResource(UserResource.class)
                .path("/{id}/journals/{journalId}")
                .resolveTemplate("id", userId)
                .resolveTemplate("journalId", journal.getId())
                .build();
        return Response.created(location).build();
    }
	
	
	@DELETE
    @Path("/{journalId}")
	@ApiOperation(value = "Delete journal")
    @ApiResponses({
      @ApiResponse(code=200, message="success"),
      @ApiResponse(code=404, message="Not found")
    })
    public Response removeJournal(@ApiParam(required=true) @PathParam("journalId") String journalId) {
		journalRepo.removeJournal(userId, journalId);
        return Response.ok().build();
    }
	
	@GET
    @Path("/{journalId}")
    @Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Get journal info")
    @ApiResponses({
    @ApiResponse(code=200, message="success"),
    @ApiResponse(code=404, message="Not found")
    })
    public Response journal(@ApiParam(required=true) @PathParam("journalId") String journalId) {
        Journal journal = journalRepo.journalInfo(journalId);
        return Response.ok(journal).build();
    }
}
