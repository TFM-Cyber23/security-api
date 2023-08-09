package es.franvallano.infraestructure.repositories;

import es.franvallano.infraestructure.entities.Employee;
import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import jakarta.inject.Singleton;

@Singleton
public class EmployeeRepository implements PanacheRepository<Employee> {

}
