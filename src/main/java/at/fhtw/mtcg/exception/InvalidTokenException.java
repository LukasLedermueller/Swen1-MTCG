package at.fhtw.mtcg.exception;

public class InvalidTokenException extends Exception {

    public InvalidTokenException() {
        super();
    }
    public InvalidTokenException(String message) {
        super(message);
    }
}
