package api.pacientes.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Address {

    private String street;
    private String number;
    private String  neigborhood;
    private String county;
    private String zipCode;
    private String state;


}
