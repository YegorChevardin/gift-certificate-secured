CREATE TABLE `gift_certificates`
(
    `id`               INT NOT NULL AUTO_INCREMENT,
    `name`             VARCHAR(45)  NOT NULL,
    `description`      TEXT(500)    NULL,
    `price`            FLOAT        NOT NULL,
    `duration`         INT          NOT NULL,
    `create_date`      TIMESTAMP    NOT NULL,
    `last_update_date` TIMESTAMP    NOT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE `tags`
(
    `id` INT  NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(45)  NOT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE `gift_certificates_tags`
(
    `gift_certificate_id` INT  NOT NULL,
    `tag_id`              INT  NOT NULL
);

CREATE TABLE `users`
(
    `id`       INT  NOT NULL AUTO_INCREMENT,
    `username` VARCHAR(50)  NOT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE `orders`
(
    `id`           INT    NOT NULL AUTO_INCREMENT,
    `user_id`      INT    NOT NULL,
    `cost`         FLOAT  NOT NULL,
    `purchased_at` TIMESTAMP      NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
);

CREATE TABLE `orders_gift_certificates`
(
    `order_id`            INT  NOT NULL,
    `gift_certificate_id` INT  NOT NULL
);
