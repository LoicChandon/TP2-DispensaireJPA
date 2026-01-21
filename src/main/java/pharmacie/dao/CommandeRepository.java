package pharmacie.dao;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import pharmacie.entity.Commande;

public interface CommandeRepository  extends JpaRepository<Commande, Integer>{
    
    /**
     * Trouve toutes les commandes saisies après une date donnée
     * @param date la date de référence
     * @return liste des commandes saisies après la date donnée
     */
    List<Commande> findAllBySaisieLeAfter(Date date);

    /**
     * Trouve toutes les commandes en cours pour un dispensaire donné
     * Une commande est en cours si sa date d'envoi (envoyele) n'est pas renseignée
     * @param dispensaireId l'identifiant du dispensaire
     * @return la liste des commandes en cours
     */
    @Query("SELECT c FROM Commande c WHERE c.dispensaire.Code = :dispId AND c.envoyeeLe IS NULL")
    List<Commande> findCommandesEnCoursByDispensaire(@Param("dispId") Integer dispensaireId);

    /**
     * Trouve toutes les commandes déjà envoyées pour un dispensaire donné
     * @param dispensaireId l'identifiant du dispensaire
     * @return la liste des commandes envoyées
     */
    @Query("SELECT c FROM Commande c WHERE c.dispensaire.Code = :dispId AND c.envoyeeLe IS NOT NULL")
    List<Commande> findCommandesEnvoyeesByDispensaire(@Param("dispId") Integer dispensaireId);
}
