package IFeelSardegna.IFeelSardegna.entites;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@JsonIgnoreProperties({"citta","mari"})

public class Recensioni {
    @Id
    @GeneratedValue
    private int id;
    private String testo;

    @ManyToOne
    private Città citta;
    @ManyToOne
    private Mari mari;
}
