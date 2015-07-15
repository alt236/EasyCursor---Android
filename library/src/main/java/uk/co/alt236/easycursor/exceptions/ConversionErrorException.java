package uk.co.alt236.easycursor.exceptions;

/**
 *
 */
public class ConversionErrorException extends RuntimeException{
    public ConversionErrorException(final String message){
        super(message);
    }

    public ConversionErrorException(final Exception exception){
        super(exception);
    }
}
