package intra.poleemploi.guidanceservice.dao;

import intra.poleemploi.guidanceservice.entities.RoleApp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin("*")
@RepositoryRestResource
public interface RoleAppRepository extends JpaRepository<RoleApp, Long> {
    RoleApp findRoleByRoleName(String roleName);
}
