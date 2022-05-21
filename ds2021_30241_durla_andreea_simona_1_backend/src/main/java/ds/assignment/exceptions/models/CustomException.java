package ds.assignment.exceptions.models;

import org.springframework.http.HttpStatus;

import java.util.List;

public class CustomException extends RuntimeException{

    private final String resource;
    private final HttpStatus httpStatus;
    private final List<String> validationErrors;

    public CustomException(String message, HttpStatus httpStatus, String resource, List<String> errors) {
        super(message);
        this.httpStatus = httpStatus;
        this.resource = resource;
        this.validationErrors = errors;
    }

    public String getResource() {
        return resource;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public List<String> getValidationErrors() {
        return validationErrors;
    }
}
