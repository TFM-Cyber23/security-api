package es.franvallano.service;

import es.franvallano.domain.EmployeeDTO;
import es.franvallano.domain.NewEmployeeDTO;
import es.franvallano.infraestructure.repositories.EmployeeRepository;
import io.quarkus.hibernate.reactive.panache.common.WithSession;
import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
import org.jboss.logging.Logger;
import org.jboss.logging.MDC;

import java.util.List;
import java.util.NoSuchElementException;

@Singleton
public class EmployeeService {

    private static final Logger log = Logger.getLogger(EmployeeService.class.getName());

    @Inject
    EmployeeRepository employeeRepository;

    @CircuitBreaker(requestVolumeThreshold = 4)
    @WithSession
    public Uni<List<EmployeeDTO>> findAll(Integer page, Integer size) {
        log.infof("UserService.findAll(%d, %d)", page, size);
        return this.employeeRepository.findAll().page(page, size).list()
                .onItem().ifNotNull().transform(i -> i.stream().map(e -> EmployeeDTO.fromEmployeeEntity(e)).toList())
                .onItem().ifNull().failWith(new NoSuchElementException("No such element"));
    }

    @CircuitBreaker(requestVolumeThreshold = 4)
    @WithTransaction
    public Uni<Long> save(final NewEmployeeDTO userDTO) {
        log.infof("UserService.save(%s)", userDTO);
        // Flush usually a good thing as it is more efficient according to the
        // documentation
        return this.employeeRepository.persistAndFlush(userDTO.toUserEntity())
                .onItem().ifNotNull().transform(i -> {
                    MDC.put("employeeId", i.id);
                    return i.id;
                })
                .onItem().ifNull().failWith(new NoSuchElementException("No such element"))
                .onFailure().transform(f -> {
                        throw new RuntimeException(f.getMessage());
                });
    }

}
