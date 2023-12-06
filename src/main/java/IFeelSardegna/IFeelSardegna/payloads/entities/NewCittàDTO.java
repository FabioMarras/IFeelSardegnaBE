package IFeelSardegna.IFeelSardegna.payloads.entities;

import jakarta.validation.constraints.NotNull;

public record NewCittàDTO(@NotNull(message = "Inserisci obbligatoriamente il nome della città")
                          String name,
                          String cover,
                          String text) {
}
