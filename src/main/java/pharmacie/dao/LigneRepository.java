package pharmacie.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import pharmacie.entity.Commande;
import pharmacie.entity.Ligne;
import pharmacie.entity.Medicament;

public interface LigneRepository  extends JpaRepository<Ligne, Integer> {
    /**
	 * Recherche une ligne par son medicament (unique)
	 * @param medicament le medicament recherché
	 * @return Une ligne avec ce medicament
	 */
	Ligne findByMedicament(Medicament medicament);

     /**
	 * Recherche une ligne par son commande (unique)
	 * @param commande la commande recherchée
	 * @return Une ligne avec cette commande
	 */
	Ligne findByCommande(Commande commande);
}
