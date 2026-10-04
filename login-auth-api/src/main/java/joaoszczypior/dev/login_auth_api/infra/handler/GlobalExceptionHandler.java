package joaoszczypior.dev.login_auth_api.infra.handler;


import jakarta.servlet.http.HttpServletRequest;
import joaoszczypior.dev.login_auth_api.infra.exceptions.InvalidCredentialsException;
import joaoszczypior.dev.login_auth_api.infra.exceptions.StandardError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<StandardError> invalidCredentials(InvalidCredentialsException e, HttpServletRequest request) {

        StandardError error = new StandardError(HttpStatus.FORBIDDEN.value(), e.getMessage(), System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
    }
}
