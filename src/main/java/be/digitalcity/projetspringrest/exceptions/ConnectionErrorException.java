package be.digitalcity.projetspringrest.exceptions;

public class ConnectionErrorException extends RuntimeException {
    public ConnectionErrorException(String msg) {
        super(msg);
    }
}
