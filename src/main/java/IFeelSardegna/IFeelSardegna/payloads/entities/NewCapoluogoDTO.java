package IFeelSardegna.IFeelSardegna.payloads.entities;

import jakarta.validation.constraints.NotNull;

public record NewCapoluogoDTO(
        @NotNull(message = "Inserisci obbligatoriamente il nome del capoluogo")
        String name,
        String cover,
        String text,
        String introText
) {
}
