package africa.bookvault.exception;

import africa.bookvault.dto.ResponseWrapper;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class GlobalExceptionHandler {

    private final Gson gson;

    private static Map<String, Object> getErrorMap(FieldError primaryError, String errorMessage, List<FieldError> fieldErrors) {
        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put(primaryError.getField(), errorMessage);
        if (fieldErrors.size() > 1) {
            List<String> additionalErrors = new ArrayList<>();
            for (int i = 1; i < fieldErrors.size(); i++) {
                FieldError error = fieldErrors.get(i);
                String additionalErrorMessage = String.format("%s: %s", error.getField(), error.getDefaultMessage());
                additionalErrors.add(additionalErrorMessage);
            }
            errorDetails.put("additionalErrors", additionalErrors);
        }
        return errorDetails;
    }

    private static ResponseWrapper<Object> errorResponseBuilder(String message, Map<String, Object> validation) {
        ResponseWrapper<Object> response = new ResponseWrapper<>();
        response.setCode("01");
        response.setMessage(message);
        response.setErrors(validation);
        return response;
    }

    private static ResponseWrapper<Object> errorResponseBuilder(String message) {
        ResponseWrapper<Object> response = new ResponseWrapper<>();
        response.setCode("01");
        response.setMessage(message);
        return response;
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ResponseWrapper<Object>> handleBadRequestException(BadRequestException e) {
        ResponseEntity<ResponseWrapper<Object>> error = new ResponseEntity<>(errorResponseBuilder(e.getMessage()),
                HttpStatus.BAD_REQUEST);
        log.error("BadRequestException - {}", gson.toJson(error));
        return error;
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ResponseWrapper<Object>> handleResourceNotFoundException(ResourceNotFoundException e) {
        ResponseEntity<ResponseWrapper<Object>> error = new ResponseEntity<>(errorResponseBuilder(e.getMessage()),
                HttpStatus.NOT_FOUND);
        log.error("ResourceNotFoundException - {}", gson.toJson(error));
        return error;
    }

    @ExceptionHandler(ServerException.class)
    public ResponseEntity<ResponseWrapper<Object>> handleServerException(ServerException e) {
        ResponseEntity<ResponseWrapper<Object>> error = new ResponseEntity<>(errorResponseBuilder(e.getMessage()),
                HttpStatus.INTERNAL_SERVER_ERROR);
        log.error("ServerException - {}", gson.toJson(error));
        return error;
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseWrapper<Object>> handleException(Exception e) {
        ResponseEntity<ResponseWrapper<Object>> error = new ResponseEntity<>(errorResponseBuilder(e.getMessage()),
                HttpStatus.INTERNAL_SERVER_ERROR);
        log.error("ServerException - {}", gson.toJson(error));
        return error;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseWrapper<Object>> handleArgumentException(MethodArgumentNotValidException e) {
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        if (fieldErrors.isEmpty()) {
            log.error("No errors found when expected.");
            return new ResponseEntity<>(
                    errorResponseBuilder("Unexpected error occurred"), HttpStatus.BAD_REQUEST);
        }
        FieldError primaryError = fieldErrors.get(0);
        String errorMessage = primaryError.getDefaultMessage();
        Map<String, Object> errorDetails = getErrorMap(primaryError, errorMessage, fieldErrors);
        ResponseEntity<ResponseWrapper<Object>> response = new ResponseEntity<>(
                errorResponseBuilder(errorMessage, errorDetails), HttpStatus.BAD_REQUEST);
        log.error("MethodArgumentNotValidException - {}", gson.toJson(response));
        return response;
    }
}