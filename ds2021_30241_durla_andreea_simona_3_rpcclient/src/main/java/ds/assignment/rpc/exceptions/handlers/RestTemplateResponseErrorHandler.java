package ds.assignment.rpc.exceptions.handlers;

import ds.assignment.rpc.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;

import static org.springframework.http.HttpStatus.Series.CLIENT_ERROR;
import static org.springframework.http.HttpStatus.Series.SERVER_ERROR;

@Component
public class RestTemplateResponseErrorHandler implements ResponseErrorHandler {

    @Override
    public boolean hasError(ClientHttpResponse clientHttpResponse) throws IOException {
        return (clientHttpResponse.getStatusCode().series() == CLIENT_ERROR
                || clientHttpResponse.getStatusCode().series() == SERVER_ERROR);
    }

    @Override
    public void handleError(ClientHttpResponse clientHttpResponse) throws IOException {
        if (clientHttpResponse.getStatusCode() == HttpStatus.NOT_FOUND) {
            throw new ResourceNotFoundException("Wrong username or password");
        }

        if (clientHttpResponse.getStatusCode() == HttpStatus.UNAUTHORIZED) {
            throw new ResourceNotFoundException("Wrong username or password");
        }
    }
}
