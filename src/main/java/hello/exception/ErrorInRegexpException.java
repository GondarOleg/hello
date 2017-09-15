package hello.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ErrorInRegexpException extends RuntimeException {
    public ErrorInRegexpException(String message) {
        super(message);
    }
}
