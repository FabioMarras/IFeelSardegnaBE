package IFeelSardegna.IFeelSardegna.entites;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class Province {
    @Id
    @GeneratedValue
    private int id;
    private String name;
    private String cover;
    private String text;
    private String introText;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "province_id")
    private List<Capoluoghi> capoluoghi;
}
