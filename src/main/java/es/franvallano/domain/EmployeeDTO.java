package es.franvallano.domain;

import es.franvallano.infraestructure.entities.Employee;
import es.franvallano.resource.model.response.EmployeeResponse;

public record EmployeeDTO(
        Long id,
        String name,
        String surname,
        String email,
        Integer age) {

    public EmployeeResponse toEmployeeResponse() {
        return new EmployeeResponse(
                this.id,
                this.name,
                this.surname,
                this.email,
                this.age);
    }

    public static EmployeeDTO fromEmployeeEntity(Employee employee) {
        return new EmployeeDTO(
                employee.id,
                employee.name,
                employee.surname,
                employee.email,
                employee.age);
    }

}