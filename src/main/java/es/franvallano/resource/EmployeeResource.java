package es.franvallano.resource;

import es.franvallano.resource.model.request.NewEmployeeRequest;
import es.franvallano.resource.model.response.CreatedResponse;
import es.franvallano.service.EmployeeService;
import io.quarkus.security.Authenticated;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.faulttolerance.Timeout;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.jboss.logging.Logger;
import org.jboss.resteasy.reactive.RestQuery;

import java.util.Optional;

@Path("/api/v1")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Authenticated
public class EmployeeResource {

    private static final Logger log = Logger.getLogger(EmployeeResource.class.getName());

    @Inject
    EmployeeService employeeService;

    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json", schema = @Schema(type = SchemaType.OBJECT), example = "[]")),
    })
    @Path("/user/employee")
    @GET
    @Timeout(40000)
    public Uni<Response> findAll(@RestQuery Integer page, @RestQuery Integer size) {
        log.infof("EmployeeResource.users(%d, %d)", page, size);
        Integer pageParam = Optional.ofNullable(page).orElse(0);
        Integer sizeParam = Optional.ofNullable(size).orElse(10);
        if (pageParam < 0)
            throw new IllegalArgumentException("Page cannot be less than 0");
        if (sizeParam < 10)
            throw new IllegalArgumentException("Size cannot be less than 10");

        return this.employeeService.findAll(pageParam, sizeParam).onItem().ifNotNull()
                .transform(i ->
                        Response.ok(i.stream().map(e -> e.toEmployeeResponse()).toList())
                                .status(Response.Status.OK)
                                .build()
                );
    }

    @Path("/admin/employee")
    @POST
    @Timeout(40000)
    public Uni<Response> create(@Valid NewEmployeeRequest payload) {
        log.infof("employeeResource.create(%s)", payload);
        return this.employeeService.save(payload.toNewUserDTO())
                .onItem().ifNotNull().transform(i ->
                        Response.ok(new CreatedResponse(i))
                                .status(Response.Status.CREATED)
                                .build()
                );
    }


}
