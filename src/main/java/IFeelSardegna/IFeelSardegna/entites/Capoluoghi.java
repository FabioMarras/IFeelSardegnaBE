package IFeelSardegna.IFeelSardegna.entites;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@JsonIgnoreProperties("province")
public class Capoluoghi {
    @Id
    @GeneratedValue
    private int id;
    private String name;
    private String cover;
    private String text;
    private String introText;

    @ManyToOne
    private Province province;
}
