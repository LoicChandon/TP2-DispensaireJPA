package pharmacie.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import pharmacie.entity.Dispensaire;

public interface DispensaireRepository  extends JpaRepository<Dispensaire, Integer> {
    
    /**
     * Trouve tous les dispensaires situés dans une région donnée
     * @param region la région à rechercher
     * @return liste des dispensaires de la région spécifiée
     */
    List<Dispensaire> findAllByAdressePostaleRegion(String region);
}
