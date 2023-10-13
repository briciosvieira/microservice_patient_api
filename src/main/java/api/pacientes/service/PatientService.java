package api.pacientes.service;

import api.pacientes.entity.Address;
import api.pacientes.entity.Contact;
import api.pacientes.entity.Patient;
import api.pacientes.exception.ResourceNotFoundException;
import api.pacientes.repository.PatientRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    public Patient registerPatient(Patient patient) {
        return patientRepository.insert(
                Patient.builder()
                        .firstName(patient.getFirstName())
                        .lastName(patient.getLastName())
                        .birthDate(patient.getBirthDate())
                        .gender(patient.getGender())
                        .cpf(patient.getCpf())
                        .address(Address.builder()
                                .state(patient.getAddress().getState())
                                .county(patient.getAddress().getCounty())
                                .zipCode(patient.getAddress().getZipCode())
                                .street(patient.getAddress().getStreet())
                                .number(patient.getAddress().getNumber())
                                .neighborhood(patient.getAddress().getNeighborhood())
                                .build())
                        .contact(Contact.builder()
                                .telephone(patient.getContact().getTelephone())
                                .whatsapp(patient.getContact().getWhatsapp())
                                .email(patient.getContact().getEmail())
                                .build())
                        .build()
        );
    }

    public void mockPatients() {
        List<Patient> mockPatientList = Arrays.asList(
                createMockPatient("João", "Silva", "1990-01-01", "M", "617.442.440-57", "SP", "São Paulo", "01000-000", "Rua A", "1", "Centro", "111111111", "911111111", "joao.silva@email.com"),
                createMockPatient("Maria", "Oliveira", "1992-02-02", "F", "628.367.830-01", "RJ", "Rio de Janeiro", "02000-000", "Rua B", "2", "Leste", "222222222", "922222222", "maria.oliveira@email.com"),
                createMockPatient("Pedro", "Fernandes", "1994-03-03", "M", "991.688.770-56", "MG", "Belo Horizonte", "03000-000", "Rua C", "3", "Norte", "333333333", "933333333", "pedro.fernandes@email.com"),
                createMockPatient("Ana", "Costa", "1996-04-04", "F", "810.805.460-57", "BA", "Salvador", "04000-000", "Rua D", "4", "Oeste", "444444444", "944444444", "ana.costa@email.com"),
                createMockPatient("Lucas", "Moraes", "1998-05-05", "M", "372.354.680-33", "PE", "Recife", "05000-000", "Rua E", "5", "Sul", "555555555", "955555555", "lucas.moraes@email.com")
        );
        mockPatientList.forEach(this::registerPatient);
    }

    private Patient createMockPatient(String firstName, String lastName, String birthDate, String gender, String cpf,
                                         String state, String county, String zipCode, String street, String number,
                                         String neighborhood, String telephone, String whatsapp, String email) {
        return Patient.builder()
                .firstName(firstName)
                .lastName(lastName)
                .birthDate(birthDate)
                .gender(gender)
                .cpf(cpf)
                .address(
                    Address.builder()
                        .state(state)
                        .county(county)
                        .zipCode(zipCode)
                        .street(street)
                        .number(number)
                        .neighborhood(neighborhood)
                        .build()
                ).contact(
                    Contact.builder()
                        .telephone(telephone)
                        .whatsapp(whatsapp)
                        .email(email)
                        .build()
                ).build();
    }

    @Transactional(readOnly = true)
    public List<Patient> getAll() {
        return patientRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Patient findById(String id) throws ResourceNotFoundException {
        Optional<Patient> patientOptional = getPatientOptionalById(id);
        verifyIfPatientIsEmpty(patientOptional);
        return patientOptional.get();
    }

    public Patient update(String id, Patient newPatient) throws ResourceNotFoundException {
        Optional<Patient> patientOptional = getPatientOptionalById(id);
        verifyIfPatientIsEmpty(patientOptional);
        BeanUtils.copyProperties(newPatient, patientOptional.get());
        return patientRepository.save(patientOptional.get());
    }

    public void delete(String id) throws ResourceNotFoundException {
        Optional<Patient> patientOptional = getPatientOptionalById(id);
        verifyIfPatientIsEmpty(patientOptional);
        patientRepository.delete(patientOptional.get());
    }

    private Optional<Patient> getPatientOptionalById(String id) {
        return patientRepository.findById(id);
    }

    private void verifyIfPatientIsEmpty(Optional<Patient> patientOptional) throws ResourceNotFoundException {
        if (patientOptional.isEmpty()){
            throw new ResourceNotFoundException();
        }
    }
}
