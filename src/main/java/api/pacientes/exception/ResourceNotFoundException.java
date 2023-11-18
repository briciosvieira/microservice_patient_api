package api.pacientes.exception;

public class ResourceNotFoundException extends Exception {

    public ResourceNotFoundException(){
        super("Paciente não encontrado");
    }

}
