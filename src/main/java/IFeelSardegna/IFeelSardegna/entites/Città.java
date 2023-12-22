package IFeelSardegna.IFeelSardegna.entites;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class Città {
    @Id
    @GeneratedValue
    private int id;
    private String name;
    private String cover;
    private String text;

    @OneToMany(mappedBy = "citta")
    private List<Recensioni> commenti;
}
