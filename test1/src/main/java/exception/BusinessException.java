package exception;

import javax.ws.rs.core.Response;

/**
 * Created by sw on 2016/9/13.
 */
public class BusinessException extends SelfDefinedException {

    public BusinessException(String message) {
        this(message, null);
    }

    public BusinessException(Throwable cause) {
        this(cause.getMessage(), cause);
    }

    public BusinessException(String message, Throwable cause) {
        super(cause, Response.Status.SERVICE_UNAVAILABLE, Errors.errors(message));
    }
}
