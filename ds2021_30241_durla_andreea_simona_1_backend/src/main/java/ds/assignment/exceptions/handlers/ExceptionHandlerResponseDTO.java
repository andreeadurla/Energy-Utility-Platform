package ds.assignment.exceptions.handlers;

import lombok.Builder;
import lombok.Data;

import java.util.Collection;
import java.util.Date;

@Data
@Builder
public class ExceptionHandlerResponseDTO {

    private Date timestamp;
    private int status;
    private String error;
    private String message;
    private String path;
    private String resource;
    private Collection<?> details;
}
