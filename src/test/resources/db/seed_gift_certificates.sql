INSERT INTO gift_certificates(`id`, `name`, `description`, `price`, `duration`, `create_date`, `last_update_date`) VALUES
                              (1, 'giftCertificate1', 'description1', 99.90, 1, '2020-10-20T07:20:15.156', '2020-10-20T07:20:15.156'),
                              (2, 'giftCertificate3', 'description3', 100.99, 3, '2019-10-20T07:20:15.156', '2019-10-20T07:20:15.156'),
                              (3, 'giftCertificate2', 'description2', 999.99, 2, '2018-10-20T07:20:15.156', '2018-10-20T07:20:15.156');
INSERT INTO gift_certificates_tags(`gift_certificate_id`, `tag_id`) VALUES (1, 2), (2, 4), (3, 4), (3, 2);