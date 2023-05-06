INSERT INTO users(`id`, `username`, `password`) VALUES
(1, 'name1', '$2a$10$GPmoN4behXcM.BSDekHJ1eYhYSQdBE9UeNTxMmqDwO0usKdRyiCyy'),
(2, 'name2', '$2a$10$GPmoN4behXcM.BSDekHJ1eYhYSQdBE9UeNTxMmqDwO0usKdRyiCyy');

INSERT INTO users_roles(`user_id`, `role_id`) VALUES (1, 0), (1, 1), (2, 0), (2, 1);