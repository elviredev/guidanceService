package intra.poleemploi.guidanceservice.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
@Table(name="appli")
public class Appli implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true)
    private String appliName;
    private String appliUrl;
    private String idAppliKM;
    @OneToMany(mappedBy = "appli")
    private Collection<Content> contents = new ArrayList<>();
}
