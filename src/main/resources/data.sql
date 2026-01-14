-- Données de base pour le projet Pharmacie
-- Dispensaire (Etablissements de santé qui passent commande de médicaments)
-- Le fichier est chargé au démarrage de l''application

-- Insertion des catégories de médicaments
INSERT INTO CATEGORIE (CODE, LIBELLE, DESCRIPTION) VALUES
(DEFAULT, 'Antalgiques et Antipyrétiques', 'Médicaments contre la douleur et la fièvre'), -- code : 1
(DEFAULT, 'Anti-inflammatoires', 'Médicaments réduisant l''inflammation'), -- code : 2
(DEFAULT, 'Antibiotiques', 'Médicaments pour traiter les infections bactériennes'),
(DEFAULT, 'Antihypertenseurs', 'Médicaments pour traiter l''hypertension artérielle'),
(DEFAULT, 'Antidiabétiques', 'Médicaments pour traiter le diabète'),
(DEFAULT, 'Antihistaminiques', 'Médicaments pour traiter les allergies'),
(DEFAULT, 'Vitamines et Compléments', 'Suppléments nutritionnels'),
(DEFAULT, 'Médicaments Cardiovasculaires', 'Médicaments pour le cœur et la circulation'),
(DEFAULT, 'Médicaments Gastro-intestinaux', 'Médicaments pour les troubles digestifs'),
(DEFAULT, 'Médicaments Respiratoires', 'Médicaments pour les troubles respiratoires');


-- Catégorie 1: Antalgiques et Antipyrétiques
INSERT INTO MEDICAMENT (NOM, CATEGORIE_CODE, QUANTITE_PAR_UNITE, PRIX_UNITAIRE, UNITES_EN_STOCK, UNITES_COMMANDEES, NIVEAU_DE_REAPPRO, INDISPONIBLE, imageURL) VALUES
('Morphine 10mg', 1, 'Boîte de 14 comprimés', 25.80, 80, 0, 15, false, 'https://images.unsplash.com/photo-1550572017-edd951aa8f72?w=400'),
('Doliprane Effervescent 1g', 1, 'Boîte de 8 comprimés', 3.50, 280, 0, 30, false, 'https://images.unsplash.com/photo-1587854692152-cbe660dbde88?w=400'),
('Efferalgan Vitamine C', 1, 'Boîte de 16 comprimés', 4.20, 220, 0, 25, false, 'https://images.unsplash.com/photo-1576091160550-2173dba999ef?w=400');

-- Catégorie 2: Anti-inflammatoires
INSERT INTO MEDICAMENT (NOM, CATEGORIE_CODE, QUANTITE_PAR_UNITE, PRIX_UNITAIRE, UNITES_EN_STOCK, UNITES_COMMANDEES, NIVEAU_DE_REAPPRO, INDISPONIBLE, imageURL) VALUES
('Étodolac 400mg', 2, 'Boîte de 14 comprimés', 12.50, 110, 0, 15, false, 'https://images.unsplash.com/photo-1471864190281-a93a3070b6de?w=400'),
('Flurbiprofène 100mg', 2, 'Boîte de 30 comprimés', 10.80, 130, 0, 16, false, 'https://images.unsplash.com/photo-1550572017-edd951aa8f72?w=400');

-- Catégorie 3: Antibiotiques (2 médicaments indisponbibles)
INSERT INTO MEDICAMENT (NOM, CATEGORIE_CODE, QUANTITE_PAR_UNITE, PRIX_UNITAIRE, UNITES_EN_STOCK, UNITES_COMMANDEES, NIVEAU_DE_REAPPRO, INDISPONIBLE, imageURL) VALUES
('Lévofloxacine 500mg', 3, 'Boîte de 7 comprimés', 15.80, 160, 0, 18, true, 'https://images.unsplash.com/photo-1628771065518-0d82f1938462?w=400'),
('Clindamycine 300mg', 3, 'Boîte de 16 gélules', 13.20, 140, 0, 16, true, 'https://images.unsplash.com/photo-1584308666744-24d5c474f2ae?w=400');


-- Dispensaires (établissements)
INSERT INTO DISPENSAIRE (CODE, NOM, CONTACT, TELEPHONE, FONCTION, FAX, ADRESSE, VILLE, REGION, PAYS, CODE_POSTAL) VALUES
(DEFAULT, 'Dispensaire Central', 'Dr Pierre Martin', '0102030405', 'Directeur', '0102030406', '1 Rue Principale', 'Paris', 'Ile-de-France', 'France', '75001'),
(DEFAULT, 'Clinique Sainte-Marie', 'Dr Anne Durand', '0605040302', 'Responsable', '0605040303', '12 Avenue de la Santé', 'Lyon', 'Auvergne-Rhône-Alpes', 'France', '69001');

-- Commandes
INSERT INTO COMMANDE (DISPENSAIRE_CODE, ENVOYEE_LE, SAISIE_LE, PORT, REMISE, DESTINATAIRE, ADRESSE, VILLE, REGION, PAYS, CODE_POSTAL) VALUES
(1, DATE '2024-01-05', DATE '2024-01-03', 10.00, 0.00, 'Hôpital Saint-Luc', '10 Rue de la Santé', 'Paris', 'Ile-de-France', 'France', '75002'),
(2, DATE '2024-02-10', DATE '2024-02-08', 12.50, 1.50, 'Clinique Sainte-Marie', '12 Avenue de la Santé', 'Lyon', 'Auvergne-Rhône-Alpes', 'France', '69002');

-- Lignes de commande (références aux médicaments et commandes)
INSERT INTO LIGNE (ID, QUANTITE, MEDICAMENT_REFERENCE, COMMANDE_NUMERO) VALUES
(DEFAULT, 50, 1, 1),
(DEFAULT, 30, 2, 1),
(DEFAULT, 20, 3, 2);
