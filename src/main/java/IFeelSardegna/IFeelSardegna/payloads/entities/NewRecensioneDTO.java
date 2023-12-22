package IFeelSardegna.IFeelSardegna.payloads.entities;

import IFeelSardegna.IFeelSardegna.entites.Città;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record NewRecensioneDTO(@NotNull(message = "Inserisci obbligatoriamente il testo del commento")
                          String testo
) {
}
