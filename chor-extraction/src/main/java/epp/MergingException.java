package epp;

public class MergingException extends Exception{
    public MergingException(String m) {
        super(m);
    }

    public MergingException(String message, Throwable cause) {
        super(message, cause);
    }
}
