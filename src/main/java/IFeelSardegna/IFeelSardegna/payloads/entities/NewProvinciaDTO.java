package IFeelSardegna.IFeelSardegna.payloads.entities;

import jakarta.validation.constraints.NotNull;

public record NewProvinciaDTO(
        @NotNull(message = "Inserisci obbligatoriamente il nome della Provincia")
        String name,
        String cover,
        String text,
        String introText
) {
}
