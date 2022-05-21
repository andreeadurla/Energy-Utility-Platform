package ds.assignment.services;

import ds.assignment.dtos.rest.ClientDTO;
import ds.assignment.dtos.rest.ClientDetailsDTO;
import ds.assignment.dtos.rest.builders.ClientBuilder;
import ds.assignment.entities.Client;
import ds.assignment.exceptions.models.ResourceNotFoundException;
import ds.assignment.repositories.ClientRepository;
import ds.assignment.services.interfaces.IClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ClientService implements IClientService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientService.class);

    private final ClientRepository clientRepository;
    private final DeviceService deviceService;

    @Autowired
    public ClientService(ClientRepository clientRepository,
                         DeviceService deviceService) {
        this.clientRepository = clientRepository;
        this.deviceService = deviceService;
    }

    public List<ClientDetailsDTO> findClients() {

        List<Client> clients = clientRepository.findAll();

        return clients.stream()
                .map(ClientBuilder::toClientDetailsDTO)
                .collect(Collectors.toList());
    }

    public Page<ClientDetailsDTO> findClients(Pageable pageable) {

        Page<Client> clients = clientRepository.findAll(pageable);

        return clients.map(ClientBuilder::toClientDetailsDTO);
    }

    public ClientDetailsDTO findClientById(UUID id) {

        Optional<Client> clientOptional = clientRepository.findById(id);

        if(!clientOptional.isPresent()) {
            throw new ResourceNotFoundException(Client.class.getSimpleName() + " with id: " + id);
        }

        return ClientBuilder.toClientDetailsDTO(clientOptional.get());
    }

    public UUID insertClient(ClientDTO clientDetailsDTO) {

        Client client = ClientBuilder.toEntity(clientDetailsDTO);
        client = clientRepository.save(client);

        return client.getId();
    }

    public void updateClient(UUID id, ClientDTO clientDTO) {

        clientRepository.findById(id)
                .map(client -> {
                    client.setName(clientDTO.getName());
                    client.setBirthDate(clientDTO.getBirthDate());
                    client.setAddress(clientDTO.getAddress());
                    return clientRepository.save(client);
                })
                .orElseGet(() -> {
                    throw new ResourceNotFoundException(Client.class.getSimpleName() + " with id: " + id);
                });
    }

    public void deleteClientById(UUID id) {

        try {
            clientRepository.deleteById(id);
            deviceService.unassignClient(id);
        } catch (EmptyResultDataAccessException ex) {
            throw new ResourceNotFoundException(Client.class.getSimpleName() + " with id: " + id);
        }
    }

}
