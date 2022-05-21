package ds.assignment.exceptions.handlers;

import ds.assignment.exceptions.models.CustomException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        List<ObjectError> errors = ex.getBindingResult().getAllErrors();
        List<String> details = new ArrayList<>();

        for(ObjectError error : errors) {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            details.add(fieldName + ":" + errorMessage);
        }

        ExceptionHandlerResponseDTO errorInfos =
                ExceptionHandlerResponseDTO.builder()
                        .timestamp(new Date())
                        .status(status.value())
                        .error(status.getReasonPhrase())
                        .resource(ex.getParameter().getParameterName())
                        .message(MethodArgumentNotValidException.class.getSimpleName())
                        .details(details)
                        .path(request.getDescription(false))
                        .build();

        return handleExceptionInternal(ex, errorInfos, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(value = {ConstraintViolationException.class})
    public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex, WebRequest request) {

        HttpStatus status = HttpStatus.CONFLICT;
        Set<ConstraintViolation<?>> details = ex.getConstraintViolations();

        ExceptionHandlerResponseDTO errorInfos =
                                ExceptionHandlerResponseDTO.builder()
                                                .timestamp(new Date())
                                                .status(status.value())
                                                .error(status.getReasonPhrase())
                                                .message(ex.getMessage())
                                                .details(details)
                                                .path(request.getDescription(false))
                                                .build();

        return handleExceptionInternal(ex, errorInfos, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(value = {CustomException.class})
    protected ResponseEntity<Object> handleCustomExceptions(CustomException ex, WebRequest request) {

        ExceptionHandlerResponseDTO errorInfos =
                ExceptionHandlerResponseDTO.builder()
                        .timestamp(new Date())
                        .status(ex.getHttpStatus().value())
                        .error(ex.getHttpStatus().getReasonPhrase())
                        .resource(ex.getResource())
                        .message(ex.getMessage())
                        .details(ex.getValidationErrors())
                        .path(request.getDescription(false))
                        .build();

        return handleExceptionInternal(ex, errorInfos, new HttpHeaders(), ex.getHttpStatus(), request);
    }

}
