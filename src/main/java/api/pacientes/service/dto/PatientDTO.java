package api.pacientes.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientDTO {

    @NotEmpty
    private String firstName;
    @NotEmpty
    private String lastName;
    @NotEmpty
    private String gender;
    @CPF
    private String cpf;
    @NotEmpty
    private String birthDate;
    @NotNull
    private ContactDTO contact;
    @NotNull
    private AddressDTO address;


}
