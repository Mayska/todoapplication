TRUNCATE TABLE task;
-- Insert data test 'TestSubmitTask_newTaskTopList()'
INSERT into task (title, description, created_at, state) values
('a','description1', '2001-01-02 11:22:33', true),
('aa','description2', '2012-11-12 04:22:00', true),
('1','description1', '2015-12-29 12:22:32', true),
('ab','description2', '2021-01-02 00:22:00', false),
('b','description1', '1990-01-02 00:22:00', true),
('rr','description2', '2021-01-02 00:22:00', false),
('bac','description1', '1916-01-02 00:22:00', true),
('a12','description2', '2021-01-02 12:22:00', true),
('1234','description1', '1902-01-02 00:22:00', true),
('yu78','description2', '2025-01-02 00:22:00', false),
('62','description1', '1925-01-02 00:22:00', true),
('azerty','description2', '2021-01-02 00:22:00', false);