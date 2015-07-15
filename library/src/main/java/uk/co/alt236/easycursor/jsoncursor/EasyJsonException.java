package uk.co.alt236.easycursor.jsoncursor;

public class EasyJsonException extends RuntimeException {
    private static final long serialVersionUID = 3684199008295295442L;

    public EasyJsonException(Exception e) {
        super(e);
    }
}