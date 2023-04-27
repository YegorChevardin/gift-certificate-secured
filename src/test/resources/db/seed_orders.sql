INSERT INTO orders(`id`, `user_id`, `cost`, `purchased_at`) VALUES
(1, 1, 10.10, '2020-10-20T07:20:15.156'),
(2, 1, 30.30, '2019-10-20T07:20:15.156');
INSERT INTO orders_gift_certificates(`order_id`, `gift_certificate_id`) VALUES
(1, 3),
(2, 2);