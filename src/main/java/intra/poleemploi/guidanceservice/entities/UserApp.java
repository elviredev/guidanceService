package intra.poleemploi.guidanceservice.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor @ToString
@Table(name="userapp")
public class UserApp implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String username;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private boolean activated;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="userapp_roles", joinColumns=@JoinColumn(name="user_app_id"), inverseJoinColumns=@JoinColumn(name="roles_id"))
    private Collection<RoleApp> roles = new ArrayList<>();
}
