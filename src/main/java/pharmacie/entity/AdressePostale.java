package pharmacie.entity;

import jakarta.persistence.Embeddable;

@Embeddable
public class AdressePostale {
    private String adresse;
    private String ville;
    private String region;
    private String pays;
    private String codePostal;
}
