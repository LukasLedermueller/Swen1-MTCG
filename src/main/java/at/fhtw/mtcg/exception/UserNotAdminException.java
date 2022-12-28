package at.fhtw.mtcg.exception;

public class UserNotAdminException extends Exception {

    public UserNotAdminException() {
        super();
    }
    public UserNotAdminException(String message) {
        super(message);
    }
}
