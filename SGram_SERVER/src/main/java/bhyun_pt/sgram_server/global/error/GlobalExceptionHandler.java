package bhyun_pt.sgram_server.global.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorReponse> customExceptionHandler(CustomException e) {
        final ErrorCode errorCode = e.getErrorCode();
        return new ResponseEntity<>(
                ErrorReponse.builder()
                        .status(errorCode.getStatus())
                        .message(errorCode.getMessage())
                        .build(),
                HttpStatus.valueOf(errorCode.getStatus())
        );
    }

}
