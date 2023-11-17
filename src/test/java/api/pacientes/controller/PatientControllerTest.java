package api.pacientes.controller;

import api.pacientes.entity.Address;
import api.pacientes.entity.Contact;
import api.pacientes.entity.Patient;
import api.pacientes.exception.ResourceNotFoundException;
import api.pacientes.service.PatientService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import util.JsonHelper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

@SpringBootTest
@AutoConfigureMockMvc
class PatientControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    PatientService patientService;

    @Test
    @DisplayName("Deve retornar uma lista de pacientes cadastrados.")
    void should_findAllPatient_ExpectedOkAndCorrectData() throws Exception {
        Patient patient1 = new Patient();
        patient1.setFirstName("Natã");
        patient1.setLastName("Ferreira");
        patient1.setBirthDate(LocalDate.of(2001, 2, 25));
        patient1.setGender("M");

        Patient patient2 = new Patient();
        patient2.setFirstName("Taysa");
        patient2.setLastName("Barbosa");
        patient2.setBirthDate(LocalDate.of(2001, 2, 25));
        patient2.setGender("F");

        List<Patient> patientList = Arrays.asList(patient1, patient2);

        Mockito.when(patientService.getAll()).thenReturn(patientList);

        mockMvc.perform(MockMvcRequestBuilders.get("/patient"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].firstName").value(patient1.getFirstName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].lastName").value(patient1.getLastName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].gender").value(patient1.getGender()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].cpf").value(patient1.getCpf()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].birthDate").value(patient1.getBirthDate().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].contact").value(patient1.getContact()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].address").value(patient1.getAddress()));

        verify(patientService, times(1)).getAll();
    }

    @Test
    @DisplayName("Deve retornar um Array vazio quando busca não retornar pacientes")
    void should_findAllPatient_ExpectedOkAndEmptyList() throws Exception {
        List<Patient> patients = new ArrayList<>();

        Mockito.when(patientService.getAll()).thenReturn(patients);

        mockMvc.perform(MockMvcRequestBuilders.get("/patient"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(0));

        verify(patientService, times(1)).getAll();
    }

    @Test
    @DisplayName("Deve retornar um Array vazio quando busca não retornar pacientes")
    void should_findByIdPatient_ExpectedOkAndCorrectData() throws Exception, ResourceNotFoundException {
        Patient patient = new Patient();
        patient.setId("teste");
        patient.setFirstName("Natã");
        patient.setLastName("Ferreira");
        patient.setBirthDate(LocalDate.of(2001, 2, 25));
        patient.setGender("M");


        Mockito.when(patientService.findById(patient.getId())).thenReturn(patient);

        mockMvc.perform(MockMvcRequestBuilders.get("/patient/" + patient.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value(patient.getFirstName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value(patient.getLastName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.gender").value(patient.getGender()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cpf").value(patient.getCpf()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.birthDate").value(patient.getBirthDate().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.contact").value(patient.getContact()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.address").value(patient.getAddress()));

        verify(patientService, times(1)).findById(patient.getId());
    }

    @Test
    @DisplayName("Deve retornar uma ResourceNotFoundException ao buscar um id inexistente")
    void should_findByIdPatientInvalid_ExpectedNotFound() throws Exception, ResourceNotFoundException {
        String id = "teste";
        Mockito.when(patientService.findById(id)).thenThrow(ResourceNotFoundException.class);
        mockMvc.perform(MockMvcRequestBuilders.get("/patient/" + id))
                .andExpect(MockMvcResultMatchers.status().isNotFound());

        verify(patientService, times(1)).findById(id);
    }

    @Test
    @DisplayName("Deve retornar valor vazio ao excluir um paciente")
    void should_deleteByIdPatient_ExpectedNoContent() throws Exception, ResourceNotFoundException {
        Patient patient = new Patient();
        patient.setId("teste");
        patient.setFirstName("Gabriel");
        patient.setLastName("Moreira");
        patient.setBirthDate(LocalDate.of(1999, 8, 17));
        patient.setGender("M");

        Mockito.doNothing().when(patientService).delete(patient.getId());

        mockMvc.perform(delete("/patient/{id}", patient.getId()))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        verify(patientService, times(1)).delete(patient.getId());
    }

    @Test
    @DisplayName("Deve registrar um novo paciente")
    void should_createPatient_ExpectedCreatedAndCorrectData() throws Exception {
        Patient patient = new Patient();
        patient.setFirstName("Ivan");
        patient.setLastName("Romão");
        patient.setBirthDate(LocalDate.of(2023, 3, 10));
        patient.setGender("M");
        patient.setCpf("76824616672");

        Address address = new Address();
        address.setNumber("123");
        address.setNeighborhood("Sample Neighborhood");
        address.setCounty("Sample County");
        address.setZipCode("12345");
        address.setState("Sample State");
        address.setStreet("Sample Street");

        Contact contact = new Contact();
        contact.setTelephone("(71) 9456-7890");
        contact.setWhatsapp("(71) 9456-7890");
        contact.setEmail("test@example.com");

        patient.setAddress(address);
        patient.setContact(contact);
        Mockito.when(patientService.registerPatient(Mockito.any(Patient.class))).thenReturn(patient);

        mockMvc.perform(MockMvcRequestBuilders.post("/patient")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonHelper.toJson(patient)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value(patient.getFirstName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value(patient.getLastName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cpf").value(patient.getCpf()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.gender").value(patient.getGender()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.birthDate").value(patient.getBirthDate().toString()))

                .andExpect(MockMvcResultMatchers.jsonPath("$.address.street").value(address.getStreet()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.address.county").value(address.getCounty()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.address.state").value(address.getState()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.address.number").value(address.getNumber()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.address.neighborhood").value(address.getNeighborhood()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.address.zipCode").value(address.getZipCode()))

                .andExpect(MockMvcResultMatchers.jsonPath("$.contact.whatsapp").value(contact.getWhatsapp()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.contact.telephone").value(contact.getTelephone()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.contact.email").value(contact.getEmail()));

        verify(patientService, times(1)).registerPatient(Mockito.any(Patient.class));
    }

    @Test
    @DisplayName("Deve atualizar um paciente")
    void should_updatePatient_ExpectedOkAndCorrectData() throws Exception, ResourceNotFoundException {
        Patient patient = new Patient();
        patient.setId("testeId");
        patient.setFirstName("Ivan");
        patient.setLastName("Romão");
        patient.setBirthDate(LocalDate.of(2023, 3, 10));
        patient.setGender("M");
        patient.setCpf("76824616672");

        Address address = new Address();
        address.setNumber("123");
        address.setNeighborhood("Sample Neighborhood");
        address.setCounty("Sample County");
        address.setZipCode("12345");
        address.setState("Sample State");
        address.setStreet("Sample Street");

        Contact contact = new Contact();
        contact.setTelephone("(71) 9456-7890");
        contact.setWhatsapp("(71) 9456-7890");
        contact.setEmail("test@example.com");

        patient.setAddress(address);
        patient.setContact(contact);
        Mockito.when(patientService.update(Mockito.anyString(),Mockito.any(Patient.class))).thenReturn(patient);

        mockMvc.perform(MockMvcRequestBuilders.put("/patient/{id}", "testId")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonHelper.toJson(patient)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value(patient.getFirstName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value(patient.getLastName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cpf").value(patient.getCpf()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.gender").value(patient.getGender()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.birthDate").value(patient.getBirthDate().toString()))

                .andExpect(MockMvcResultMatchers.jsonPath("$.address.street").value(address.getStreet()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.address.county").value(address.getCounty()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.address.state").value(address.getState()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.address.number").value(address.getNumber()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.address.neighborhood").value(address.getNeighborhood()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.address.zipCode").value(address.getZipCode()))

                .andExpect(MockMvcResultMatchers.jsonPath("$.contact.whatsapp").value(contact.getWhatsapp()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.contact.telephone").value(contact.getTelephone()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.contact.email").value(contact.getEmail()));

        verify(patientService, times(1)).update(Mockito.eq("testId"), Mockito.any(Patient.class));


    }
}