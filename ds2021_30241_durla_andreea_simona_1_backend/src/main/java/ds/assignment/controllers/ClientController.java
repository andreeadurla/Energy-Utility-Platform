package ds.assignment.controllers;

import ds.assignment.dtos.rest.ClientDTO;
import ds.assignment.dtos.rest.ClientDetailsDTO;
import ds.assignment.services.interfaces.IClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@CrossOrigin
@RequestMapping(value = "/clients")
public class ClientController {

    private final IClientService clientService;

    @Autowired
    public ClientController(IClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping()
    public ResponseEntity<Page<ClientDetailsDTO>> getClientsPage(Pageable pageable) {

        Page<ClientDetailsDTO> clientDTOS = clientService.findClients(pageable);

        for (ClientDetailsDTO clientDTO : clientDTOS) {
            Link clientLink = linkTo(methodOn(ClientController.class)
                    .getClient(clientDTO.getId())).withSelfRel();
            clientDTO.add(clientLink);
        }

        return new ResponseEntity<>(clientDTOS, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ClientDetailsDTO> getClient(@PathVariable("id") UUID id) {

        ClientDetailsDTO clientDTO = clientService.findClientById(id);
        return new ResponseEntity<>(clientDTO, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<UUID> insertClient(@Valid @RequestBody ClientDTO clientDTO) {

        UUID clientId = clientService.insertClient(clientDTO);
        Link clientLink = linkTo(methodOn(ClientController.class)
                                    .getClient(clientId)).withSelfRel();

        return ResponseEntity
                .created(clientLink.toUri())
                .body(clientId);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity updateClient(@PathVariable("id") UUID id,
                                       @Valid @RequestBody ClientDTO clientDTO) {

        clientService.updateClient(id, clientDTO);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity deleteClient(@PathVariable("id") UUID id) {

        clientService.deleteClientById(id);

        return ResponseEntity.noContent().build();
    }

}
