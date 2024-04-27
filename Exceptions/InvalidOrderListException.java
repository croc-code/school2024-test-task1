package Exceptions;

public class InvalidOrderListException extends Exception{

    private final Exception causeException;
    public Exception getCauseException(){
        return causeException;
    }
    public InvalidOrderListException (String message, Exception exception){
        super(message);
        causeException = exception;
    }
}
