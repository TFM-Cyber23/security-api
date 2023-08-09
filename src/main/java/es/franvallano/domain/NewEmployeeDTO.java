package es.franvallano.domain;

import es.franvallano.infraestructure.entities.Employee;

public record NewEmployeeDTO(
        String name,
        String surname,
        String email,
        Integer age) {

    public Employee toUserEntity() {
        Employee entity = new Employee();
        entity.name = this.name;
        entity.surname = this.surname;
        entity.email = this.email;
        entity.age = this.age;

        return entity;
    }

}