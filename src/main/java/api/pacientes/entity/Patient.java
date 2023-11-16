package api.pacientes.entity;

import api.pacientes.entity.util.DatabaseObject;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document
public class Patient extends DatabaseObject {

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
//    @UniqueElements
    private String cpf;

    @NotNull(message = "A data de nascimento do paciente não foi informada")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "UTC")
    private LocalDate birthDate;

    @NotNull(message = "Insira ao menos dos 3 campos de contato. Telefone, whatsapp ou e-mail")
    private Contact contact;

    @NotNull(message = "O endereço do cliente não foi informado")
    private Address address;

    public Patient(String firstName, String lastName, String gender, String cpf, LocalDate birthDate, Contact contact, Address address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.cpf = cpf;
        this.birthDate = birthDate;
        this.contact = contact;
        this.address = address;
    }
}
