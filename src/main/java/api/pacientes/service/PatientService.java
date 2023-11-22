package api.pacientes.service;

import api.pacientes.entity.Address;
import api.pacientes.entity.Contact;
import api.pacientes.entity.Patient;
import api.pacientes.handler.exceptions.DuplicatedCPFException;
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

    public Patient registerPatient(Patient patient) throws DuplicatedCPFException {
        patient.setCpf(cleanedCPF(patient.getCpf()));
        if (patientRepository.existsPatientByCpf(patient.getCpf())) {
            throw new DuplicatedCPFException();
        }
        return patientRepository.insert(patient);
    }

    public void mockPatients() {
        List<Patient> mockPatientList = Arrays.asList(
                new Patient("João", "Silva", "M", "61744244057", LocalDate.of(1990, 01, 01),  new Contact( "111111111", "911111111", "joao.silva@email.com"), new Address("1", "Centro", "São Paulo", "01001-001", "SP", "Rua A")),
                new Patient("Maria", "Oliveira", "F", "93473522090", LocalDate.of(1992, 02, 12), new Contact("111111112", "911111112", "maria.oliveira@email.com"), new Address("2", "Centro", "São Paulo", "01002-001", "SP", "Rua B")),
                new Patient("Carlos", "Fernandes", "M", "08062413578", LocalDate.of(1989, 03, 23), new Contact("111111113", "911111113", "carlos.fernandes@email.com"), new Address("3", "Centro", "São Paulo", "01003-001", "SP", "Rua C")),
                new Patient("Ana", "Martins", "F", "85904895070", LocalDate.of(1991, 04, 14), new Contact("111111114", "911111114", "ana.martins@email.com"), new Address("4", "Centro", "São Paulo", "01001-004", "SP", "Rua D")),
                new Patient("Roberto", "Alves", "M", "42838946056", LocalDate.of(1993, 05, 15), new Contact("111111115", "911111115", "roberto.alves@email.com"), new Address("5", "Centro", "São Paulo", "01005-001", "SP", "Rua E"))
        );
        patientRepository.insert(mockPatientList);
    }

    @Transactional(readOnly = true)
    public List<Patient> getAll() {
        return patientRepository.findAllByOrderByCreatedAtDesc();
    }

    @Transactional(readOnly = true)
    public Patient findById(String id) throws ResourceNotFoundException {
        return getPatientOptionalById(id);
    }

    public Patient update(String id, Patient newPatient) throws ResourceNotFoundException, DuplicatedCPFException {
        Patient patient = getPatientOptionalById(id);
        if (patientRepository.existsPatientByCpfAndIdNot(cleanedCPF(newPatient.getCpf()), id)) {
            throw new DuplicatedCPFException();
        }
        patient.setGender(newPatient.getGender());
        patient.setAddress(newPatient.getAddress());
        patient.setContact(newPatient.getContact());
        patient.setCpf(cleanedCPF(newPatient.getCpf()));
        patient.setBirthDate(newPatient.getBirthDate());
        patient.setFirstName(newPatient.getFirstName());
        patient.setLastName(newPatient.getLastName());
        return patientRepository.save(patient);
    }

    public void delete(String id) throws ResourceNotFoundException {
        patientRepository.delete(getPatientOptionalById(id));
    }

    public void deleteAll() {
        patientRepository.deleteAll();
    }

    private Patient getPatientOptionalById(String id) throws ResourceNotFoundException {
        Optional<Patient> patientOptional = patientRepository.findById(id);

        if (patientOptional.isEmpty()){
            throw new ResourceNotFoundException();
        }

        return patientOptional.get();
    }

    private String cleanedCPF(String cpf) {
        String cleanCpf = cpf.replaceAll("\\D", "");
        return cleanCpf;
    }
}
