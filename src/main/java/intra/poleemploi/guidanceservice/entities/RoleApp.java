package intra.poleemploi.guidanceservice.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor @ToString
@Table(name = "role")
public class RoleApp implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String roleName;
    /*@ManyToMany(mappedBy = "roles")
    private Set<UserApp> userApps;*/
}
