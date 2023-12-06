package IFeelSardegna.IFeelSardegna.entites;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Terme {
    @Id
    @GeneratedValue
    private int id;
    private String name;
    private String cover;
    private String text;
}
