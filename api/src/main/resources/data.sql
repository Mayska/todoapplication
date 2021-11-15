-- initialization DB
DROP TABLE IF EXISTS task;

-- Table
CREATE TABLE task (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  title VARCHAR(55) NOT NULL,
  description VARCHAR(250),
  created_at VARCHAR(55)
);

-- state allows to know the state of your task. true, it is in progress. false, it is finished. 
ALTER TABLE task ADD state BOOLEAN NOT NULL;