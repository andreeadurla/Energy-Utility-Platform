INSERT INTO end_user (id, username, password, role, deleted) values
    ('e3c505a412b840e6ae774c022f71d8c7', 'john.doe', 'John123!', 1, false);

INSERT INTO client (id, name, birth_date, address, user_id, deleted) values
    ('00201D1E61F3401588BF4292B86E22E4', 'John Doe', '2001-10-09', 'Street street1', 'e3c505a412b840e6ae774c022f71d8c7', false);
