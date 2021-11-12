-- initialization DB
DROP TABLE IF EXISTS task;

-- Table
CREATE TABLE task (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  title VARCHAR(55) NOT NULL,
  description VARCHAR(250),
  created_at TIMESTAMP(7) DEFAULT CURRENT_TIMESTAMP(7)
);

-- state allows to know the state of your task. true, it is in progress. false, it is finished. 
ALTER TABLE task ADD state BOOLEAN NOT NULL;

INSERT INTO task (title, description, state) VALUES
('Titre 1','',true),('Titre 2','Description 2', false),('Titre 3','Description 3',true);
