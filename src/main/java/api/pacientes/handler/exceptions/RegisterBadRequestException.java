package api.pacientes.handler.exceptions;

public class RegisterBadRequestException extends Exception {

    public RegisterBadRequestException(String mesage) {
        super(mesage);
    }
}
