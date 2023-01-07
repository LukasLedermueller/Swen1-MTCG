package at.fhtw.mtcg.exception;

public class NoPlayerFoundException extends Exception {

    public NoPlayerFoundException() {
        super();
    }
    public NoPlayerFoundException(String message) {
        super(message);
    }
}
