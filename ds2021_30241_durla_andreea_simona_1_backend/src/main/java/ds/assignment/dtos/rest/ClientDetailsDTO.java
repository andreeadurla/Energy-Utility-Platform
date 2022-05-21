package ds.assignment.dtos.rest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
public class ClientDetailsDTO extends RepresentationModel<ClientDetailsDTO> {

    private UUID id;
    private String username;
    private String name;
    private Date birthDate;
    private String address;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ClientDetailsDTO clientDTO = (ClientDetailsDTO) o;
        return id == clientDTO.id &&
                Objects.equals(username, clientDTO.username) &&
                Objects.equals(name, clientDTO.name) &&
                Objects.equals(birthDate, clientDTO.birthDate) &&
                Objects.equals(address, clientDTO.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, name, birthDate, address);
    }
}
