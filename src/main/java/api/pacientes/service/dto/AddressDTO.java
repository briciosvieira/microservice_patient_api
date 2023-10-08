package api.pacientes.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTO {

    @NotEmpty
    private String number;
    @NotEmpty
    private String  neigborhood;
    @NotEmpty
    private String county;
    @NotEmpty
    private String zipCode;
    @NotEmpty
    private String state;
    @NotEmpty
    private String street;
}
