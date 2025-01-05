-- Rooms
INSERT INTO rooms (name, uuid)
VALUES ('Aharon Test Room', 'dfc98e14-8bb9-4f76-af51-70afaaaaae4e');

INSERT INTO rooms (name, uuid)
VALUES ('Room 2', '7db07b43-ce20-4c6a-bcad-0ef47b66e2bc');

-- Users
INSERT INTO users (name, uuid, nickname, country_code, phone_number)
VALUES ('Aharon Maghen', '73066cc7-d23b-4330-aeae-db224e02bfa4', 'Magoo', 'IL', '0505533685');

INSERT INTO users (name, uuid, nickname, country_code, phone_number)
VALUES ('John Doe', '3e649738-d999-4eda-9124-08a0ae2f6ec4', 'Doey', 'US', '3471231234');

INSERT INTO users (name, uuid, nickname, country_code, phone_number)
VALUES ('Jane Doe', '9bef0d8f-cd75-4429-9642-d7a2a1140855', 'Janey', 'IL', '0501231234');

INSERT INTO users (name, uuid, nickname, country_code, phone_number)
VALUES ('Super Man', 'ec786f98-b8a4-4ed8-89fb-9e1e6d4dc25d', 'SM', 'US', '3475555555');

-- Room Users
INSERT INTO room_users (room_id, user_id, uuid)
VALUES (1, 1, '061dfe46-86c8-40b8-8087-0b7cdc222cd9');

INSERT INTO room_users (room_id, user_id, uuid)
VALUES (2, 2, 'fd5baca6-c3d8-4ef6-8c02-f7270c4bf179');

INSERT INTO room_users (room_id, user_id, uuid)
VALUES (2, 3, '6e4841c5-79ac-4279-bad7-1b79c7fb2b8d');