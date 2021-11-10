-- initialization DB
DROP TABLE IF EXISTS task;

-- Table
CREATE TABLE task (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  title VARCHAR(55) NOT NULL,
  description VARCHAR(250),
  created_at TIMESTAMP(7) DEFAULT CURRENT_TIMESTAMP(7)
);


INSERT INTO task (title, description) VALUES
('Titre 1',''),('Titre 2','Description 2'),('Titre 3','Description 3');
