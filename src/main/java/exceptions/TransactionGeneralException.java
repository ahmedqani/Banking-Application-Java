package exceptions;

public class TransactionGeneralException extends Exception{

    private static final long serialVersionUID = 1L;

    public TransactionGeneralException(String message) {
        super(message);
    }
}
