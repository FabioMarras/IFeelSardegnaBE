package IFeelSardegna.IFeelSardegna.exceptions;


public class NotFoundException extends RuntimeException{
    public NotFoundException(long message) {
        super(String.valueOf(message));
    }
}
