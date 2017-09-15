package hello.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ErrorInRegexpException extends RuntimeException {

    private final static String ERR_DESCRIPTION = "Regexp is not valid: ";

    public ErrorInRegexpException(String message) {
        super(ERR_DESCRIPTION + message);
    }
}
