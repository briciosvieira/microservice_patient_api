package api.pacientes.controller;

import api.pacientes.entity.Patient;
import api.pacientes.exception.ResourceNotFoundException;
import api.pacientes.service.PatientService;
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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
@AutoConfigureMockMvc
class PatientControllerTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    PatientService patientService;

    @Test
    @DisplayName("Deve retornar uma lista de pacientes cadastrados.")
    void testObterTodos() throws Exception {
        // Arrange
        Patient patient1 = new Patient();
        patient1.setFirstName("Natã");
        patient1.setLastName("Ferreira");
        patient1.setBirthDate(String.valueOf(LocalDate.of(2001, 2, 25)));
        patient1.setGender("M");

        Patient patient2 = new Patient();
        patient2.setFirstName("Taysa");
        patient2.setLastName("Barbosa");
        patient2.setBirthDate(String.valueOf(LocalDate.of(2001, 2, 25)));
        patient2.setGender("F");

        List<Patient> patientList = Arrays.asList(patient1, patient2);
        // Mock
        Mockito.when(patientService.getAll()).thenReturn(patientList);
        // Action Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/patient"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].firstName").value(patient1.getFirstName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].lastName").value(patient1.getLastName()));
        // Verify

        verify(patientService, times(1)).getAll();
    }

    @Test
    @DisplayName("Deve retornar um Array vazio quando busca não retornar pacientes")
    void testObterListaEmBranco() throws Exception {
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
    void testObterPeloId() throws Exception, ResourceNotFoundException {
        Patient patient1 = new Patient();
        patient1.setId("teste");
        patient1.setFirstName("Natã");
        patient1.setLastName("Ferreira");
        patient1.setBirthDate(String.valueOf(LocalDate.of(2001, 2, 25)));
        patient1.setGender("M");


        Mockito.when(patientService.findById(patient1.getId())).thenReturn(patient1);

        mockMvc.perform(MockMvcRequestBuilders.get("/patient/" + patient1.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value(patient1.getFirstName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value(patient1.getLastName()));

        verify(patientService, times(1)).findById(patient1.getId());
    }

    @Test
    @DisplayName("Deve retornar uma ResourceNotFoundException ao buscar um id inexistente")
    void testObterPorUmIdInvalido() throws Exception, ResourceNotFoundException {
        String id = "teste";
        Mockito.when(patientService.findById(id)).thenThrow(ResourceNotFoundException.class);
        mockMvc.perform(MockMvcRequestBuilders.get("/patient/" + id))
                .andExpect(MockMvcResultMatchers.status().isNotFound());

        verify(patientService, times(1)).findById(id);
    }
}
