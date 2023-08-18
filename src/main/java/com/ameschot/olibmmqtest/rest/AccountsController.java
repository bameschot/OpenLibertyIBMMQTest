package com.ameschot.olibmmqtest.rest;

import com.ameschot.olibmmqtest.generated.model.TestInMessage;
import com.ameschot.olibmmqtest.generated.model.TestOutMessage;
import com.ameschot.olibmmqtest.service.AccountService;
import com.ameschot.olibmmqtest.service.ReceiveTotalService;
import com.ameschot.olibmmqtest.service.SendAmountService;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Slf4j
@Path("accounts")
public class AccountsController {

    @Inject
    SendAmountService sendAmountService;

    @Inject
    ReceiveTotalService receiveTotalService;

    @Inject
    AccountService accountService;

    @POST
    @Path("send-amount")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response sendMessage(TestInMessage testInMessage){
        log.debug("sending: {}",testInMessage);

        sendAmountService.send(testInMessage);

        return Response.accepted("{\"send\": \""+testInMessage.getAccount()+"\"}").build();
    }

    @GET
    @Path("receive-total")
    @Produces(MediaType.APPLICATION_JSON)
    public Response received() {
        TestOutMessage outMessage = receiveTotalService.receive();
        if (outMessage != null) {
            return Response.status(Response.Status.ACCEPTED).entity(outMessage).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }


    @GET
    @Path("accounts-overview")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAccountsOverview(){
        return Response.accepted(accountService.getAccounts()).build();
    }



}
