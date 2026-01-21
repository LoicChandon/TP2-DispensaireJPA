package pharmacie.dao;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import pharmacie.entity.Categorie;
import pharmacie.entity.Commande;
import pharmacie.entity.Dispensaire;
import pharmacie.entity.Medicament;

@DataJpaTest
public class RepositoryCustomMethodsTest {

    @Autowired
    private CategorieRepository categorieRepository;
    @Autowired
    private MedicamentRepository medicamentRepository;
    @Autowired
    private CommandeRepository commandeRepository;
    @Autowired
    private DispensaireRepository dispensaireRepository;
    @Autowired
    private LigneRepository ligneRepository;


    @Test // Ce test se base uniquement sur les données définies dans data.sql
    public void testMedicamentCustomMethods() {    
        Medicament indisponible = medicamentRepository.findByNom("Lévofloxacine 500mg").orElseThrow();
        Medicament disponible   = medicamentRepository.findByNom("Doliprane Effervescent 1g").orElseThrow();
    
        // Trouve tous les médicaments disponibles
        List<Medicament> disponibles = medicamentRepository.findByIndisponibleFalse();

        assertTrue(disponibles.contains(disponible));
        assertFalse(disponibles.contains(indisponible));        
        assertFalse(disponibles.isEmpty());
    }

    @Test // Ce test crée les enregistrements nécessaires
    public void testCategorieCustomMethods() {
        Categorie c1 = new Categorie();
        c1.setLibelle("AnalgesiquesTest");
        categorieRepository.save(c1);

        Categorie c2 = new Categorie();
        c2.setLibelle("AntibiotiquesTest");
        categorieRepository.save(c2);

        // findByLibelle
        Categorie found = categorieRepository.findByLibelle("AnalgesiquesTest");
        assertNotNull(found);
        assertEquals("AnalgesiquesTest", found.getLibelle());

        // findByLibelleContaining
        List<Categorie> list = categorieRepository.findByLibelleContaining("iquesTest");
        assertEquals(2, list.size());
        assertTrue(list.stream().anyMatch(cat -> cat.getLibelle().equals("AntibiotiquesTest")));
        assertTrue(list.stream().anyMatch(cat -> cat.getLibelle().equals("AnalgesiquesTest")));
    }

    @Test
    public void testCommandeCustomMethods() {
        Date cutoff = Date.valueOf("2024-01-04");
        List<Commande> recent = commandeRepository.findAllBySaisieLeAfter(cutoff);
        assertEquals(1, recent.size());
        assertEquals("Clinique Sainte-Marie", recent.get(0).getDestinataire());
    }

    @Test
    public void testDispensaireCustomMethods() {
        List<Dispensaire> list = dispensaireRepository.findAllByAdressePostaleRegion("Ile-de-France");
        assertFalse(list.isEmpty());
        assertTrue(list.stream().anyMatch(d -> d.getNom().equals("Dispensaire Central")));
    }

    
    // ------------------ CONTRAINTES D'INTÉGRITÉ ------------------
    
    @Test
    public void testMedicamentMustHaveACategorie() {
        Medicament med = new Medicament();
        med.setNom("TestMedicamentSansCategorie");

        assertThrows(DataIntegrityViolationException.class, () -> {
            medicamentRepository.saveAndFlush(med);
        }, "Should not save Medicament without Categorie"); 
        
    }

    @Test
    public void deleteCategorieWithNoMedicamentShouldSucceed() {
        Categorie cat = new Categorie();
        cat.setLibelle("CategorieSansMedicament");
        categorieRepository.saveAndFlush(cat);

        // Mtn supprimer
        categorieRepository.delete(cat);
        categorieRepository.flush(); // ne devrait pas lancer d'exception
    }

    @Test
    public void testCannotDeleteCategoryWithMedicaments() {
        // Créer une catégorie avec un médicament
        Categorie categorie = new Categorie("CategorieWithMeds");
        Medicament med = new Medicament();
        med.setNom("Ibuprofène");
        med.setCategorie(categorie);
        categorie.getMedicaments().add(med);
        categorieRepository.save(categorie);
        Integer categoryId = categorie.getCode();

        // Vérifier qu'on ne peut pas la supprimer
        assertThrows(InvalidDataAccessApiUsageException.class, () -> {
            categorieRepository.deleteById(categoryId);
        });

        // Vérifier qu'elle existe toujours
        assertTrue(categorieRepository.findById(categoryId).isPresent());
    }

    @Test
    public void testDeletingCommandeRemovesLignes() {
        // Create required entities: Categorie, Medicament, Dispensaire, Commande, Ligne
        Categorie cat = new Categorie("CatForCmd");
        categorieRepository.save(cat);

        Medicament med = new Medicament();
        med.setNom("MedForCmd");
        med.setCategorie(cat);
        medicamentRepository.save(med);

        Dispensaire disp = new Dispensaire("D1","Contact","0123","Fonction");
        dispensaireRepository.save(disp);

        Commande cmd = new Commande();
        cmd.setDestinataire("DestTest");
        cmd.setDispensaire(disp);

        // create line
        pharmacie.entity.Ligne line = new pharmacie.entity.Ligne();
        line.setQuantite(2);
        line.setMedicament(med);
        line.setCommande(cmd);
        cmd.getLignes().add(line);

        commandeRepository.save(cmd);
        Integer cmdId = cmd.getNumero();
        Integer lineId = cmd.getLignes().get(0).getId();

        // delete command
        commandeRepository.deleteById(cmdId);

        // ligne should be deleted
        assertFalse(ligneRepository.findById(lineId).isPresent());
    }

    @Test
    public void testDeletingDispensaireRemovesCommandes() {
        Dispensaire disp = new Dispensaire("D2","Contact","0456","Fonction");
        dispensaireRepository.save(disp);

        Commande cmd = new Commande();
        cmd.setDestinataire("DestForDisp");
        cmd.setDispensaire(disp);
        disp.getCommandes().add(cmd);

        dispensaireRepository.save(disp);
        Integer dispId = disp.getCode();
        Integer cmdId = disp.getCommandes().get(0).getNumero();

        dispensaireRepository.deleteById(dispId);

        assertFalse(commandeRepository.findById(cmdId).isPresent());
    }
}