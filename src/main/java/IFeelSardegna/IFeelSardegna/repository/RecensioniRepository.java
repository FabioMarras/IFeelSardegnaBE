package IFeelSardegna.IFeelSardegna.repository;

import IFeelSardegna.IFeelSardegna.entites.Città;
import IFeelSardegna.IFeelSardegna.entites.Recensioni;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecensioniRepository extends JpaRepository<Recensioni,Long> {
}
