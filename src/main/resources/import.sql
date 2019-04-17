INSERT INTO userentity (id, fullname) VALUES (1, 'Viktor Tsoy');

INSERT INTO balanceentity (id, total, reserved) VALUES (1, 100, 90);
INSERT INTO balanceentity (id, total, reserved) VALUES (2, 600, 11);

INSERT INTO accountentity (id, user_id, status, balance_id, iban, currency) VALUES (1, 1, 'ACTIVE', 1, 'LT4645464564', 'EUR');
INSERT INTO accountentity (id, user_id, status, balance_id, iban, currency) VALUES (2, 1, 'ACTIVE', 2, 'RB3151564887', 'USD');

INSERT INTO userentity (id, fullname) Values (2, 'John Smith');

INSERT INTO balanceentity (id, total, reserved) VALUES (3, 500, 0);

INSERT INTO accountentity (id, user_id, status, balance_id, iban, currency) VALUES (3, 2, 'ACTIVE', 3, 'LT3212131544', 'EUR');