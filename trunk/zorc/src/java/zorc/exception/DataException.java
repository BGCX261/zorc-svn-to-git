package zorc.exception;

/**
 * @author Genocide
 *
 * Класс для представления ошибки .
 */
public class DataException extends Exception {

    private String msg;

    public DataException(Throwable e) {
        super(e);
    }

    public DataException() {
    }

    public DataException(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "Exception (" + msg + ")";
    }

    public String getMsg() {
        return msg;
    }
}
