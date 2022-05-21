package ds.assignment.dtos.rest;

import ds.assignment.dtos.rest.validators.annotations.AddressConstraint;
import ds.assignment.dtos.rest.validators.annotations.BirthDateConstraint;
import ds.assignment.dtos.rest.validators.annotations.NameConstraint;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.UUID;

@Data
public class ClientDTO extends RepresentationModel<ClientDTO> {

    @NotNull
    @NameConstraint
    private String name;

    @NotNull
    @BirthDateConstraint
    private Date birthDate;

    @NotNull
    @AddressConstraint
    private String address;

    private UUID userId;

    public ClientDTO(String name, Date birthDate, String address) {
        this.name = name;
        this.birthDate = birthDate;
        this.address = address;
    }
}

