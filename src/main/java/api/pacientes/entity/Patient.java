package api.pacientes.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Patient {

    @Id
    private String id;

    @NotEmpty
    private String firstName;

    @NotEmpty
    private String lastName;

    @NotEmpty
    private String gender;

    @CPF
    @NotEmpty
    private String cpf;

    @NotEmpty
    private String birthDate;

    @NotNull
    private Contact contact;

    @NotNull
    private Address address;

}
