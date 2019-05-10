INSERT INTO userentity (id, fullname) VALUES (nextval('hibernate_sequence'), 'Viktor Tsoy');

INSERT INTO balanceentity (id, total, reserved) VALUES (nextval('hibernate_sequence'), 100, 90);
INSERT INTO balanceentity (id, total, reserved) VALUES (nextval('hibernate_sequence'), 600, 11);

INSERT INTO accountentity (id, user_id, status, balance_id, iban, currency) VALUES (nextval('hibernate_sequence'), 1, 'ACTIVE', 2, 'LT4645464564', 'EUR');
INSERT INTO accountentity (id, user_id, status, balance_id, iban, currency) VALUES (nextval('hibernate_sequence'), 1, 'ACTIVE', 3, 'RB3151564887', 'USD');

INSERT INTO userentity (id, fullname) Values (nextval('hibernate_sequence'), 'John Smith');

INSERT INTO balanceentity (id, total, reserved) VALUES (nextval('hibernate_sequence'), 500, 0);

INSERT INTO accountentity (id, user_id, status, balance_id, iban, currency) VALUES (nextval('hibernate_sequence'), 6, 'ACTIVE', 7, 'LT3212131544', 'EUR');