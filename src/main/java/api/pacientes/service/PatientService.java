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
                new Patient("João", "Silva", "M", "617.442.440-57", "1990-01-01",  new Contact( "111111111", "911111111", "joao.silva@email.com"), new Address("SP", "São Paulo", "01000-000", "Rua A", "1", "Centro")),
                new Patient("Maria", "Oliveira", "F", "934.735.220-90", "1992-02-12", new Contact("111111112", "911111112", "maria.oliveira@email.com"), new Address("SP", "São Paulo", "01001-001", "Rua B", "2", "Centro")),
                new Patient("Carlos", "Fernandes", "M", "080.624.135-78", "1989-03-23", new Contact("111111113", "911111113", "carlos.fernandes@email.com"), new Address("SP", "São Paulo", "01002-002", "Rua C", "3", "Centro")),
                new Patient("Ana", "Martins", "F", "859.048.950-70", "1991-04-14", new Contact("111111114", "911111114", "ana.martins@email.com"), new Address("SP", "São Paulo", "01003-003", "Rua D", "4", "Centro")),
                new Patient("Roberto", "Alves", "M", "428.389.460-56", "1993-05-15", new Contact("111111115", "911111115", "roberto.alves@email.com"), new Address("SP", "São Paulo", "01004-004", "Rua E", "5", "Centro"))
        );
        mockPatientList.forEach(this::registerPatient);
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
