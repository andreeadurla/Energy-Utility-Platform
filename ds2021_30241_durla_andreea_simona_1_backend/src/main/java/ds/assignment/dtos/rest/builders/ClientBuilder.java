package ds.assignment.dtos.rest.builders;

import ds.assignment.dtos.rest.ClientDTO;
import ds.assignment.dtos.rest.ClientDetailsDTO;
import ds.assignment.entities.Client;
import ds.assignment.entities.User;

public class ClientBuilder {

    private ClientBuilder() {}

    public static ClientDetailsDTO toClientDetailsDTO(Client client) {
        return ClientDetailsDTO.builder()
                .id(client.getId())
                .username((client.getUser() != null)? client.getUsername() : null)
                .name(client.getName())
                .birthDate(client.getBirthDate())
                .address(client.getAddress())
                .build();
    }

    public static Client toEntity(ClientDTO clientDTO) {
        return Client.builder()
                .name(clientDTO.getName())
                .birthDate(clientDTO.getBirthDate())
                .address(clientDTO.getAddress())
                .user((clientDTO.getUserId() != null) ?
                        User.builder().id(clientDTO.getUserId()).build():
                        null)
                .build();
    }
}
