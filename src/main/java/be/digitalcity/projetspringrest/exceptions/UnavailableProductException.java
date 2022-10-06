package be.digitalcity.projetspringrest.exceptions;

public class UnavailableProductException extends RuntimeException {
    public UnavailableProductException(String msg) {
        super(msg);
    }
}
