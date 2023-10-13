package api.pacientes.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.UniqueElements;
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

    @NotEmpty(message = "O nome do paciente não foi informado")
    private String firstName;

    @NotEmpty(message = "O sobrenome do paciente não foi informado")
    private String lastName;

    @NotEmpty(message = "O gênero do paciente não foi informado")
    private String gender;

    @CPF(message = "O CPF informado está inválido")
    @NotEmpty(message = "O CPF do paciente não foi informado")
    @UniqueElements
    private String cpf;

    @NotEmpty(message = "A data de nascimento do paciente não foi informada")
    private String birthDate;

    @NotNull(message = "Insira ao menos dos 3 campos de contato. Telefone, whatsapp ou e-mail")
    private Contact contact;

    @NotNull(message = "O endereço do cliente não foi informado")
    private Address address;

    public Patient(String firstName, String lastName, String gender, String cpf, String birthDate, Contact contact, Address address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.cpf = cpf;
        this.birthDate = birthDate;
        this.contact = contact;
        this.address = address;
    }
}
