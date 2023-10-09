package api.pacientes.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressDTO {

    @NotEmpty
    private String number;

    @NotEmpty
    private String neighborhood;

    @NotEmpty
    private String county;

    @NotEmpty
    private String zipCode;

    @NotEmpty
    private String state;

    @NotEmpty
    private String street;

}
