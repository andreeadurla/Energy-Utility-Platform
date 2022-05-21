package ds.assignment.services.interfaces;

import ds.assignment.dtos.rest.ClientDTO;
import ds.assignment.dtos.rest.ClientDetailsDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface IClientService {

    List<ClientDetailsDTO> findClients();
    Page<ClientDetailsDTO> findClients(Pageable pageable);
    ClientDetailsDTO findClientById(UUID id);
    UUID insertClient(ClientDTO clientDTO);
    void updateClient(UUID id, ClientDTO clientDTO);
    void deleteClientById(UUID id);
}
