package api.pacientes.handler.exceptions;

public class DuplicatedCPFException extends Exception {

    public DuplicatedCPFException() {
        super("CPF já cadastrado");
    }

}
