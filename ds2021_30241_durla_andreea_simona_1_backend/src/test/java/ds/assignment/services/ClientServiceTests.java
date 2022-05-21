package ds.assignment.services;

import ds.assignment.DsAssigTestConfig;
import ds.assignment.dtos.rest.ClientDetailsDTO;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.springframework.test.util.AssertionErrors.assertEquals;

@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:/test-sql/create.sql")
@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:/test-sql/delete.sql")
public class ClientServiceTests extends DsAssigTestConfig {

    @Autowired
    ClientService clientService;

    @Test
    public void testGetCorrect() {
        List<ClientDetailsDTO> clientDTOS = clientService.findClients();
        assertEquals("Test Get Clients", 1, clientDTOS.size());
    }
}
