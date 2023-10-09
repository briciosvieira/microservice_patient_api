package api.pacientes.service;

import api.pacientes.entity.Address;
import api.pacientes.entity.Contact;
import api.pacientes.entity.Patient;
import api.pacientes.repository.PatientRepository;
import api.pacientes.service.dto.AddressDTO;
import api.pacientes.service.dto.ContactDTO;
import api.pacientes.service.dto.PatientDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
@Transactional
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    public Patient registerPatient(PatientDTO patientDTO) {
        return patientRepository.insert(
                Patient.builder()
                        .firstName(patientDTO.getFirstName())
                        .lastName(patientDTO.getLastName())
                        .birthDate(patientDTO.getBirthDate())
                        .gender(patientDTO.getGender())
                        .cpf(patientDTO.getCpf())
                        .address(Address.builder()
                                .state(patientDTO.getAddress().getState())
                                .county(patientDTO.getAddress().getCounty())
                                .zipCode(patientDTO.getAddress().getZipCode())
                                .street(patientDTO.getAddress().getStreet())
                                .number(patientDTO.getAddress().getNumber())
                                .neighborhood(patientDTO.getAddress().getNeighborhood())
                                .build())
                        .contact(Contact.builder()
                                .telephone(patientDTO.getContact().getTelephone())
                                .whatsapp(patientDTO.getContact().getWhatsapp())
                                .email(patientDTO.getContact().getEmail())
                                .build())
                        .build()
        );
    }

    public void mockPatients() {
        List<PatientDTO> mockPatientList = Arrays.asList(
                createMockPatient("João", "Silva", "1990-01-01", "M", "617.442.440-57", "SP", "São Paulo", "01000-000", "Rua A", "1", "Centro", "111111111", "911111111", "joao.silva@email.com"),
                createMockPatient("Maria", "Oliveira", "1992-02-02", "F", "628.367.830-01", "RJ", "Rio de Janeiro", "02000-000", "Rua B", "2", "Leste", "222222222", "922222222", "maria.oliveira@email.com"),
                createMockPatient("Pedro", "Fernandes", "1994-03-03", "M", "991.688.770-56", "MG", "Belo Horizonte", "03000-000", "Rua C", "3", "Norte", "333333333", "933333333", "pedro.fernandes@email.com"),
                createMockPatient("Ana", "Costa", "1996-04-04", "F", "810.805.460-57", "BA", "Salvador", "04000-000", "Rua D", "4", "Oeste", "444444444", "944444444", "ana.costa@email.com"),
                createMockPatient("Lucas", "Moraes", "1998-05-05", "M", "372.354.680-33", "PE", "Recife", "05000-000", "Rua E", "5", "Sul", "555555555", "955555555", "lucas.moraes@email.com")
        );
        mockPatientList.forEach(this::registerPatient);
    }

    private PatientDTO createMockPatient(String firstName, String lastName, String birthDate, String gender, String cpf,
                                         String state, String county, String zipCode, String street, String number,
                                         String neighborhood, String telephone, String whatsapp, String email) {
        return PatientDTO.builder()
                .firstName(firstName)
                .lastName(lastName)
                .birthDate(birthDate)
                .gender(gender)
                .cpf(cpf)
                .address(
                    AddressDTO.builder()
                        .state(state)
                        .county(county)
                        .zipCode(zipCode)
                        .street(street)
                        .number(number)
                        .neighborhood(neighborhood)
                        .build()
                ).contact(
                    ContactDTO.builder()
                        .telephone(telephone)
                        .whatsapp(whatsapp)
                        .email(email)
                        .build()
                ).build();
    }
}
