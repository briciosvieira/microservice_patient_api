package api.pacientes.exception;

public class ResourceNotFoundException extends Throwable {

    public ResourceNotFoundException(){
        super("Paciente não encontrado");
    }

}
