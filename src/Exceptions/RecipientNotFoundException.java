package Exceptions;

public class RecipientNotFoundException extends Exception{
    public RecipientNotFoundException(String errorMessage){
        super(errorMessage);
    }
}
