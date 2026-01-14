package pharmacie.dao;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import pharmacie.entity.Commande;

public interface CommandeRepository  extends JpaRepository<Commande, Integer>{
    
    /**
     * Trouve toutes les commandes saisies après une date donnée
     * @param date la date de référence
     * @return liste des commandes saisies après la date donnée
     */
    List<Commande> findAllBySaisieLeAfter(Date date);
}
