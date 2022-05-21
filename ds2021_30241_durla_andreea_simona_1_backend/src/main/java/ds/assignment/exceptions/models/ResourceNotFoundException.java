package ds.assignment.exceptions.models;

import org.springframework.http.HttpStatus;

import java.util.ArrayList;

public class ResourceNotFoundException extends CustomException {

    private static final String MESSAGE = "Resource not found!";
    private static final HttpStatus HTTP_STATUS = HttpStatus.NOT_FOUND;

    public ResourceNotFoundException(String resource) {
        super(MESSAGE, HTTP_STATUS, resource, new ArrayList<>());
    }
}
