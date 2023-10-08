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
public class Patient {

    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String cpf;
    private String gender;
    private String birthDate;

    private Contact contact;
    private Address address;


}
