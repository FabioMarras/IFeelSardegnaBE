package IFeelSardegna.IFeelSardegna.payloads.entities;

import jakarta.validation.constraints.NotNull;

public record NewMareDTO(@NotNull(message = "Inserisci obbligatoriamente il nome del mare")
                          String name,
                         String cover,
                         String text) {
}
