package api.pacientes.controller;

import api.pacientes.entity.Patient;
import api.pacientes.handler.exceptions.DuplicatedCPFException;
import api.pacientes.handler.exceptions.ResourceNotFoundException;
import api.pacientes.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/patient")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @GetMapping
    public ResponseEntity<List<Patient>> getAll(){
        return ResponseEntity.ok().body(patientService.getAll());
    }


    @GetMapping("/{id}")
    public ResponseEntity<Patient> getById(@PathVariable String id) throws ResourceNotFoundException {
        return ResponseEntity.ok().body(patientService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Patient> registerPatient(@RequestBody @Valid Patient patient) throws DuplicatedCPFException {
        return ResponseEntity.created(null).body(patientService.registerPatient(patient));
    }

    @PostMapping(value = "/mock-patients")
    public ResponseEntity<Void> mockPatients() {
        patientService.mockPatients();
        return ResponseEntity.created(null).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) throws ResourceNotFoundException {
        patientService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAll() throws ResourceNotFoundException {
        patientService.deleteAll();
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable String id, @RequestBody @Valid Patient patient) throws ResourceNotFoundException, DuplicatedCPFException {
        Patient patientUpdate = patientService.update(id, patient);
        return ResponseEntity.ok(patientUpdate);
    }
}
