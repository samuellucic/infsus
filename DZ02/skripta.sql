CREATE TABLE Korisnik
(
  ime VARCHAR(100) NOT NULL,
  prezime VARCHAR(100) NOT NULL,
  datumRod DATE NOT NULL,
  adresa VARCHAR(100) NOT NULL,
  email VARCHAR(100) NOT NULL,
  idKorisnik INT NOT NULL,
  lozinka VARCHAR(100) NOT NULL,
  PRIMARY KEY (idKorisnik)
);

CREATE TABLE Zaposlenik
(
  OIB INT NOT NULL,
  datumPocRad DATE NOT NULL,
  datumKrajRad DATE NOT NULL,
  idZaposlenik INT NOT NULL,
  PRIMARY KEY (idZaposlenik),
  FOREIGN KEY (idZaposlenik) REFERENCES Korisnik(idKorisnik),
  UNIQUE (OIB)
);

CREATE TABLE Kupac
(
  idKupac INT NOT NULL,
  PRIMARY KEY (idKupac),
  FOREIGN KEY (idKupac) REFERENCES Korisnik(idKorisnik)
);

CREATE TABLE Vlasnik
(
  idVlasnik INT NOT NULL,
  PRIMARY KEY (idVlasnik),
  FOREIGN KEY (idVlasnik) REFERENCES Korisnik(idKorisnik)
);

CREATE TABLE Izvještaj
(
  vrstaIzvještaj VARCHAR(100) NOT NULL,
  idIzvještaj INT NOT NULL,
  opis VARCHAR(500) NOT NULL,
  datumPoc DATE NOT NULL,
  datumKraj DATE NOT NULL,
  dobit FLOAT NOT NULL,
  prihod FLOAT NOT NULL,
  idZaposlenik INT,
  PRIMARY KEY (idIzvještaj),
  FOREIGN KEY (idZaposlenik) REFERENCES Zaposlenik(idZaposlenik)
);

CREATE TABLE Obilazak
(
  idNekretnina INT NOT NULL,
  datumObilazak DATE NOT NULL,
  idZaposlenik INT NOT NULL,
  idKupac INT NOT NULL,
  PRIMARY KEY (idNekretnina, idZaposlenik, idKupac),
  FOREIGN KEY (idZaposlenik) REFERENCES Zaposlenik(idZaposlenik),
  FOREIGN KEY (idKupac) REFERENCES Kupac(idKupac)
);

CREATE TABLE Nekretnina
(
  idNekretnina INT NOT NULL,
  cijena FLOAT NOT NULL,
  adresa VARCHAR(100) NOT NULL,
  kvadratura FLOAT NOT NULL,
  opis VARCHAR(1000) NOT NULL,
  idVlasnik INT NOT NULL,
  PRIMARY KEY (idNekretnina),
  FOREIGN KEY (idVlasnik) REFERENCES Vlasnik(idVlasnik)
);

CREATE TABLE Prodaja
(
  idNekretnina INT NOT NULL,
  idZaposlenik INT NOT NULL,
  idKupac INT NOT NULL,
  PRIMARY KEY (idNekretnina, idZaposlenik, idKupac),
  FOREIGN KEY (idZaposlenik) REFERENCES Zaposlenik(idZaposlenik),
  FOREIGN KEY (idKupac) REFERENCES Kupac(idKupac)
);

CREATE TABLE Slika
(
  idSlika INT NOT NULL,
  idNekretnina INT NOT NULL,
  slika VARCHAR(1000) NOT NULL,
  opis VARCHAR(500) NOT NULL,
  PRIMARY KEY (idSlika),
  FOREIGN KEY (idNekretnina) REFERENCES Nekretnina(idNekretnina)
);

INSERT INTO Korisnik (ime, prezime, datumRod, adresa, email, idKorisnik, lozinka)
VALUES ('John', 'Doe', '1990-05-15', '123 Main Street', 'john.doe@example.com', 1, 'password');

INSERT INTO Korisnik (ime, prezime, datumRod, adresa, email, idKorisnik, lozinka)
VALUES ('Jane', 'Doe', '1990-05-15', '123 Main Street', 'john.doe@example.com', 2, 'password2');

INSERT INTO Korisnik (ime, prezime, datumRod, adresa, email, idKorisnik, lozinka)
VALUES ('Samuel', 'Doe', '1990-05-15', '123 Main Street', 'john.doe@example.com', 3, 'password3');

INSERT INTO Zaposlenik (OIB, datumPocRad, datumKrajRad, idZaposlenik)
VALUES (123456789, '2023-01-01', '2024-12-31', 1);

INSERT INTO Kupac (idKupac)
VALUES (2);

INSERT INTO Vlasnik (idVlasnik)
VALUES (3);

INSERT INTO Izvještaj (vrstaIzvještaj, idIzvještaj, opis, datumPoc, datumKraj, dobit, prihod, idZaposlenik)
VALUES ('Prodaja', 1, 'Opis izvještaja', '2024-01-01', '2024-01-31', 1000.00, 5000.00, 1);

INSERT INTO Obilazak (idNekretnina, datumObilazak, idZaposlenik, idKupac)
VALUES (1, '2024-01-01', 1, 2);

INSERT INTO Nekretnina (idNekretnina, cijena, adresa, kvadratura, opis, idVlasnik)
VALUES (1, 200000.00, '456 Elm Street', 150.00, 'Opis nekretnine', 3);

INSERT INTO Prodaja (idNekretnina, idZaposlenik, idKupac)
VALUES (1, 1, 2);

INSERT INTO Slika (idSlika, idNekretnina, slika, opis)
VALUES (1, 1, 'slika.jpg', 'Opis slike');


