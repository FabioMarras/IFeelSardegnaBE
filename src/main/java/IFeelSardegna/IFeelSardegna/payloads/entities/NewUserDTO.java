package IFeelSardegna.IFeelSardegna.payloads.entities;

import IFeelSardegna.IFeelSardegna.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record NewUserDTO(
        @NotNull(message = "Username obbligatorio")
        @Size(min = 4, message = "Scrivi un username con più di 4 caratteri")
        String username,
        @NotNull(message = "Inserisci obbligatoriamente il tuo nome")
        @Size(min = 2, message = "Il nome deve avere più di 2 caratteri")
        String name,
        @NotNull(message = "Inserisci obbligatoriamente il tuo lastName")
        @Size(min = 2, message = "LastName deve avere più di 2 caratteri")
        String lastName,
        @NotBlank(message = "Inserisci obbligatoriamente la tua email")
        @Email(message = "Inserisci un indirizzo email valido")
        String email,
        String avatar,
        String password,
        Role role,
        List<String> errorsList

) {

}
