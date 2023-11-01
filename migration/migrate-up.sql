CREATE TABLE `api_keys` (
    `key` VARCHAR(255) NOT NULL,
    `client` VARCHAR(255) NOT NULL,
    PRIMARY KEY (`key`)
);

CREATE TABLE `logging` (
    `id` int NOT NULL AUTO_INCREMENT,
    `description` varchar(255) NOT NULL,
    `IP` char(255) NOT NULL,
    `endpoint` varchar(255) NOT NULL,
    `requested_at` timestamp NOT NULL,
    PRIMARY KEY (`id`)
);

INSERT INTO api_keys VALUES
('1a5b0c4d8e1f8f6a9b0e0b0a6c9f4e4d3c9i8b3a7f6f0d5g4h9i6j8b3c9o0p8b', 'rest service'),
('7a3f6f0d5g4h9i6j8b3c9o0p8b5e9f3f1d6a81a5b0c4d8e1f8f6a9b0e0b0a6c9', 'php app service');