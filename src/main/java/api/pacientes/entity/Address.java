package api.pacientes.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Address {

    @NotEmpty(message = "O número do endereço do paciente não foi informado")
    private String number;

    @NotEmpty(message = "O bairro do endereço do paciente não foi informado")
    private String neighborhood;

    @NotEmpty(message = "O bairro do endereço do paciente não foi informado")
    private String county;

    @NotEmpty(message = "O país do endereço do paciente não foi informado")
    private String zipCode;

    @NotEmpty(message = "O estado do endereço do paciente não foi informado")
    private String state;

    @NotEmpty(message = "O rua do endereço do paciente não foi informado")
    private String street;

}
