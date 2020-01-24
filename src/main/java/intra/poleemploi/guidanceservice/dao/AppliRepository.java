package intra.poleemploi.guidanceservice.dao;

import intra.poleemploi.guidanceservice.entities.Appli;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin("*")
@RepositoryRestResource
public interface AppliRepository extends JpaRepository<Appli, Integer> {
    Appli findAppliByAppliName(String appliName);
}
