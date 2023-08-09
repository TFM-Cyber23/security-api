package es.franvallano.resource.model.response;

public record EmployeeResponse(
                Long id,
                String name,
                String surname,
                String email,
                Integer age) {
}
