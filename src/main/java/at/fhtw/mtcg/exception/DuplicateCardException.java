package at.fhtw.mtcg.exception;

public class DuplicateCardException extends Exception {

    public DuplicateCardException() {
        super();
    }
    public DuplicateCardException(String message) {
        super(message);
    }
}
