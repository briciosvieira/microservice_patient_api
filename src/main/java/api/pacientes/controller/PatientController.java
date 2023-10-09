package api.pacientes.controller;

import api.pacientes.entity.Patient;
import api.pacientes.service.PatientService;
import api.pacientes.service.dto.PatientDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/patient")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @PostMapping
    public ResponseEntity<Patient> registerPatient(@RequestBody @Valid PatientDTO patientDTO){
        return ResponseEntity.created(null).body(patientService.registerPatient(patientDTO));
    }

    @PostMapping(value = "/mock-patients")
    public ResponseEntity<Void> mockPatients() {
        patientService.mockPatients();
        return ResponseEntity.created(null).build();
    }
}
