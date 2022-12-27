package at.fhtw.mtcg.exception;

public class NoCardsException extends Exception {

    public NoCardsException() {
        super();
    }
    public NoCardsException(String message) {
        super(message);
    }
}
