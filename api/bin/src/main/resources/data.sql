DROP TABLE IF EXISTS individu;

CREATE TABLE individu (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  prenom VARCHAR(250) NOT NULL,
  sex BOOLEAN NOT null,
  chapeaux BOOLEAN NOT null,
  yeux BOOLEAN NOT null,
  couleur_cheveux VARCHAR(250) NOT NULL
);

-- Si c'est une fille c'est TRUE
-- Si il/elle a un chapeau c'est TRUE
-- Si il/elle a les yeux ouvert c'est TRUE
INSERT INTO individu (prenom, sex, chapeaux, yeux, couleur_cheveux) VALUES
  ('Jeanne',TRUE,TRUE,TRUE,'roux'),
  ('Hugo',FALSE,FALSE,TRUE,'blond'),
  ('Agathe',TRUE,TRUE,FALSE,'marron'),
  ('Arthur',FALSE,FALSE,TRUE,'marron'),
  ('Leo',FALSE,TRUE,TRUE,'noir'),
  ('Sophie',TRUE,FALSE,TRUE,'blond'),
  ('Tom',FALSE,FALSE,TRUE,'roux'),
  ('Madi',TRUE,FALSE,TRUE,'noir'),
  ('Lucile',TRUE,FALSE,FALSE,'roux'),
  ('Suzie',TRUE,FALSE,TRUE,'noir'),
  ('Imane',TRUE,FALSE,TRUE,'noir'),
  ('Melissa',TRUE,TRUE,FALSE,'noir'),
  ('Paul',FALSE,FALSE,TRUE,'roux'),
  ('Gaspard',FALSE,TRUE,FALSE,'blond'),
  ('Lou',TRUE,FALSE,TRUE,'noir'),
  ('Ines',TRUE,TRUE,TRUE,'marron');