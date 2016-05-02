CREATE TABLE Abonnement (
    Utilisateur_id int NOT NULL,
    Album_id int NOT NULL,
    status int NOT NULL,
    CONSTRAINT Abonnement_pk PRIMARY KEY (Utilisateur_id,Album_id) );  
CREATE TABLE Album (
    id int NOT NULL AUTO_INCREMENT,
    nom varchar(255) NOT NULL,
    last_update_time timestamp NOT NULL,
    Produit_id int NOT NULL,
    statut int NOT NULL,
    CONSTRAINT Album_pk PRIMARY KEY (id) );
CREATE TABLE Album_photo (
    Album_id int NOT NULL,
    Image_id int NOT NULL,
    `order` int NOT NULL,
    statut int NOT NULL,
    CONSTRAINT Album_photo_pk PRIMARY KEY (Album_id) ); 
CREATE TABLE Client_application (
    id int NOT NULL,
    nom varchar(255) NOT NULL,
    uid_application varchar(255) NOT NULL,
    CONSTRAINT Client_application_pk PRIMARY KEY (id) ); 
CREATE TABLE History (
    id int NOT NULL,
    Utilisateur_id int NOT NULL,
    Album_id int NOT NULL,
    date date NOT NULL,
    statut int NOT NULL,
    action_type int NOT NULL,
    isUserDestination bool NOT NULL,
    CONSTRAINT History_pk PRIMARY KEY (id) );
CREATE TABLE Image (
    id int NOT NULL AUTO_INCREMENT,
    nom varchar(255) NOT NULL,
    description int NULL,
    original_path varchar(255) NOT NULL,
    path varchar(255) NOT NULL,
    statut int NOT NULL,
    CONSTRAINT Image_pk PRIMARY KEY (id) );
CREATE TABLE Produit (
    id int NOT NULL AUTO_INCREMENT,
    nom varchar(255) NOT NULL,
    Description varchar(255) NOT NULL,
    statut int NOT NULL,
    CONSTRAINT Produit_pk PRIMARY KEY (id) ); 
CREATE TABLE Utilisateur (
    id int NOT NULL AUTO_INCREMENT,
    nom varchar(255) NOT NULL,
    prenom varchar(255) NOT NULL,
    username varchar(255) NOT NULL,
    photo varchar(255) NOT NULL,
    email varchar(255) NOT NULL,
    password varchar(255) NOT NULL,
    role_type int NOT NULL,
    telephone varchar(255) NOT NULL,
    statut int NOT NULL,
    CONSTRAINT Utilisateur_pk PRIMARY KEY (id) );
ALTER TABLE Album ADD CONSTRAINT 
Album_Produit FOREIGN KEY Album_Produit (Produit_id)
    REFERENCES Produit (id);
ALTER TABLE History ADD CONSTRAINT Notifications_Albums FOREIGN 
KEY Notifications_Albums (Album_id)
    REFERENCES Album (id); 
ALTER TABLE History ADD CONSTRAINT 
Notifications_Utilisateurs FOREIGN KEY Notifications_Utilisateurs 
(Utilisateur_id)
    REFERENCES Utilisateur (id); 
ALTER TABLE Abonnement ADD CONSTRAINT 
abonnements_Albums FOREIGN KEY abonnements_Albums (Album_id)
    REFERENCES Album (id); 
ALTER TABLE Abonnement ADD CONSTRAINT 
abonnements_Utilisateurs FOREIGN KEY abonnements_Utilisateurs 
(Utilisateur_id)
    REFERENCES Utilisateur (id);
ALTER TABLE Album_photo ADD CONSTRAINT album_photo_Album 
FOREIGN KEY album_photo_Album (Album_id)
    REFERENCES Album (id); 
ALTER TABLE Album_photo ADD CONSTRAINT album_photo_Image 
FOREIGN KEY album_photo_Image (Image_id)
    REFERENCES Image (id); -- End of file.
