package IFeelSardegna.IFeelSardegna.payloads.entities;

import jakarta.validation.constraints.NotNull;

public record NewTermaDTO(@NotNull(message = "Inserisci obbligatoriamente il nome della terma")
                          String name,
                          String cover,
                          String text) {
}
