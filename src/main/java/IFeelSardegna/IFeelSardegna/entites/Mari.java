package IFeelSardegna.IFeelSardegna.entites;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class Mari {
    @Id
    @GeneratedValue
    private int id;
    private String name;
    private String cover;
    private String text;

    @ElementCollection
    private List<String> spiaggiaDetails;

    @ElementCollection
    private List<String> mariDetails;
}
