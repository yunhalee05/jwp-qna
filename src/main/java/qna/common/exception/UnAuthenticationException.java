package qna.common.exception;

public class UnAuthenticationException extends BaseException {

    private static final long serialVersionUID = 1L;

    public UnAuthenticationException(ErrorMessage errorMessage) {
        super(errorMessage);
    }

    public UnAuthenticationException() {
        super();
    }

    public UnAuthenticationException(String message, Throwable cause, boolean enableSuppression,
        boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public UnAuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnAuthenticationException(String message) {
        super(message);
    }

    public UnAuthenticationException(Throwable cause) {
        super(cause);
    }
}