package pharmacie.entity;

import java.util.LinkedList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter @Setter @NoArgsConstructor @RequiredArgsConstructor @ToString
public class Dispensaire {
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Setter(AccessLevel.NONE) // la clé est autogénérée par la BD, On ne veut pas de "setter"
	private Integer Code = null;

    @NonNull
    private String nom;

    @NonNull
    private String contact;

    @NonNull
    private String telephone;

    @NonNull
    private String fonction;

    private String fax;

    @Embedded
    private AdressePostale adressePostale;

    @ToString.Exclude
	@OneToMany(cascade = {CascadeType.ALL}, mappedBy = "dispensaire")
	private List<Commande> commandes = new LinkedList<>();
}
