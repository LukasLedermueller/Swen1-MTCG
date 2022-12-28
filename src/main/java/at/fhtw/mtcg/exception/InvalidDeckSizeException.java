package at.fhtw.mtcg.exception;

public class InvalidDeckSizeException extends Exception {

    public InvalidDeckSizeException() {
        super();
    }
    public InvalidDeckSizeException(String message) {
        super(message);
    }
}
