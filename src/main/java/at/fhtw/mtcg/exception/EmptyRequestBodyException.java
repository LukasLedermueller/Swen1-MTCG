package at.fhtw.mtcg.exception;

public class EmptyRequestBodyException extends Exception {

    public EmptyRequestBodyException() {
        super();
    }
    public EmptyRequestBodyException(String message) {
        super(message);
    }
}
