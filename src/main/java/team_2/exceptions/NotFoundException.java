package team_2.exceptions;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String id) {
        super("ID " + id + " non trovato");
    }

}
