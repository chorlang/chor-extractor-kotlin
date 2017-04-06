package epp;

/**
 * Created by lara on 06/04/17.
 */
public class MergingException extends Exception{
    public MergingException(String m) {
        super(m);
    }

    public MergingException(String message, Throwable cause) {
        super(message, cause);
    }
}
