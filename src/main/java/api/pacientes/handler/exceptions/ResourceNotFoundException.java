package api.pacientes.handler.exceptions;

public class ResourceNotFoundException extends Exception {

    public ResourceNotFoundException(){
        super("Paciente não encontrado");
    }

}
