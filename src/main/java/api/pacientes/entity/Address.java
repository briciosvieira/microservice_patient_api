package api.pacientes.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

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
    @Pattern(
            regexp = "^(AC|AL|AP|AM|BA|CE|DF|ES|GO|MA|MT|MS|MG|PA|PB|PR|PE|PI|RJ|RN|RS|RO|RR|SC|SE|SP|TO)$",
            message = "O estado deve conter apenas letras maiúsculas e ser uma sigla válida de estado brasileiro. Exemplo: BA"
    )
    private String state;

    @NotEmpty(message = "A rua do endereço do paciente não foi informado")
    private String street;

}
