package ds.assignment.rpc.services;

import ds.assignment.rpc.dtos.LoginRequest;
import ds.assignment.rpc.dtos.UserDTO;
import ds.assignment.rpc.exceptions.handlers.RestTemplateResponseErrorHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AuthenticateService {

    @Value("${baseUrl}")
    private String baseUrl;

    private RestTemplate restTemplate;

    @Autowired
    public AuthenticateService(RestTemplateBuilder restTemplateBuilder) {
        restTemplate = restTemplateBuilder
                .errorHandler(new RestTemplateResponseErrorHandler())
                .build();
    }

    public UserDTO authenticate(String username, String password) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<LoginRequest> request = new HttpEntity<>(
                LoginRequest.builder()
                        .username(username)
                        .password(password)
                        .build(),
                headers
        );

        return restTemplate.postForObject(
                baseUrl + "/auth/login",
                request,
                UserDTO.class);
    }
}
