package es.franvallano.resource.model.request;

import es.franvallano.domain.NewEmployeeDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record NewEmployeeRequest(
        @NotBlank(message = "name may not be blank")
        String name,
        @NotBlank(message = "surname may not be blank")
        String surname,
        @NotBlank(message = "email may not be blank")
        String email,
        @NotNull(message = "age may not be blank")
        Integer age) {

    public NewEmployeeDTO toNewUserDTO() {
        NewEmployeeDTO dto = new NewEmployeeDTO(
                this.name,
                this.surname,
                this.email,
                this.age);

        return dto;
    }

}
