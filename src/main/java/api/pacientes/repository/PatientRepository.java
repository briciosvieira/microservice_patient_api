package api.pacientes.repository;

import api.pacientes.entity.Patient;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientRepository extends MongoRepository<Patient, String> {

    List<Patient> findAllByOrderByCreatedAtDesc();
}
