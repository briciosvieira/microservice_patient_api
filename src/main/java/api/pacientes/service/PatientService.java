package api.pacientes.service;

import api.pacientes.entity.Address;
import api.pacientes.entity.Contact;
import api.pacientes.entity.Patient;
import api.pacientes.handler.exceptions.ResourceNotFoundException;
import api.pacientes.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
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
                new Patient("João", "Silva", "M", "617.442.440-57", LocalDate.of(1990, 01, 01),  new Contact( "111111111", "911111111", "joao.silva@email.com"), new Address("1", "Centro", "São Paulo", "01001-001", "SP", "Rua A")),
                new Patient("Maria", "Oliveira", "F", "934.735.220-90", LocalDate.of(1992, 02, 12), new Contact("111111112", "911111112", "maria.oliveira@email.com"), new Address("2", "Centro", "São Paulo", "01002-001", "SP", "Rua B")),
                new Patient("Carlos", "Fernandes", "M", "080.624.135-78", LocalDate.of(1989, 03, 23), new Contact("111111113", "911111113", "carlos.fernandes@email.com"), new Address("3", "Centro", "São Paulo", "01003-001", "SP", "Rua C")),
                new Patient("Ana", "Martins", "F", "859.048.950-70", LocalDate.of(1991, 04, 14), new Contact("111111114", "911111114", "ana.martins@email.com"), new Address("4", "Centro", "São Paulo", "01001-004", "SP", "Rua D")),
                new Patient("Roberto", "Alves", "M", "428.389.460-56", LocalDate.of(1993, 05, 15), new Contact("111111115", "911111115", "roberto.alves@email.com"), new Address("5", "Centro", "São Paulo", "01005-001", "SP", "Rua E"))
        );
        mockPatientList.forEach(this::registerPatient);
    }

    @Transactional(readOnly = true)
    public List<Patient> getAll() {
        return patientRepository.findAllByOrderByCreatedAtDesc();
    }

    @Transactional(readOnly = true)
    public Patient findById(String id) throws ResourceNotFoundException {
        return getPatientOptionalById(id);
    }

    public Patient update(String id, Patient newPatient) throws ResourceNotFoundException {
        Patient patient = getPatientOptionalById(id);
        patient.setGender(newPatient.getGender());
        patient.setAddress(newPatient.getAddress());
        patient.setContact(newPatient.getContact());
        patient.setCpf(newPatient.getCpf());
        patient.setBirthDate(newPatient.getBirthDate());
        patient.setFirstName(newPatient.getFirstName());
        patient.setLastName(newPatient.getLastName());

        return patientRepository.save(patient);
    }

    public void delete(String id) throws ResourceNotFoundException {
        patientRepository.delete(getPatientOptionalById(id));
    }

    private Patient getPatientOptionalById(String id) throws ResourceNotFoundException {
        Optional<Patient> patientOptional = patientRepository.findById(id);

        if (patientOptional.isEmpty()){
            throw new ResourceNotFoundException();
        }

        return patientOptional.get();
    }
}
