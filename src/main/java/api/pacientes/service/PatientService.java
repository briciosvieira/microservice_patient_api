package api.pacientes.service;

import api.pacientes.entity.Address;
import api.pacientes.entity.Contact;
import api.pacientes.entity.Patient;
import api.pacientes.repository.PatientRepository;
import api.pacientes.service.dto.PatientDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
                                .neigborhood(patientDTO.getAddress().getNeigborhood())
                                .build())
                        .contact(Contact.builder()
                                .telephone(patientDTO.getContact().getTelephone())
                                .whatsapp(patientDTO.getContact().getWhatsapp())
                                .email(patientDTO.getContact().getEmail())
                                .build())
                        .build()
        );
    }
}
