package pharmacie.entity;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.LinkedList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter @Setter @NoArgsConstructor @RequiredArgsConstructor @ToString
public class Commande {
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Setter(AccessLevel.NONE) // la clé est autogénérée par la BD, On ne veut pas de "setter"
	private Integer Numero = null;

    @PastOrPresent 
    private Date envoyeeLe;

    @PastOrPresent
    private Date saisieLe;

    @PositiveOrZero
	private BigDecimal port = BigDecimal.TEN;

    @PositiveOrZero
	private BigDecimal remise = BigDecimal.TEN;

    @NonNull
	@Column(unique=true, length = 255)
	private String destinataire;

    @Embedded
    private AdressePostale adressePostale;

    @ManyToOne(optional = false)
	@NonNull
	@ToString.Exclude
	private Dispensaire dispensaire;

    @ToString.Exclude
	@OneToMany(cascade = {CascadeType.ALL}, mappedBy = "commande")
	private List<Ligne> lignes = new LinkedList<>();
}
