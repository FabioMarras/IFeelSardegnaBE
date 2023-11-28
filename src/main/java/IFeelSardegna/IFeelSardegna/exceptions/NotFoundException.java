package IFeelSardegna.IFeelSardegna.exceptions;


public class NotFoundException extends RuntimeException{
    public NotFoundException(long id) {
        super("L'elemento con id: " + id + " non è stato trovato! Provane un altro!");
    }
    public NotFoundException(String message) {
        super(String.valueOf(message));
    }
}
