-- Rooms
INSERT INTO
  rooms (name)
VALUES
  ('Aharon Test Room');

INSERT INTO
  rooms (name)
VALUES
  ('Room 2');

-- Users
INSERT INTO
  users (name, nickname, country_code, phone_number)
VALUES
  ('Aharon Maghen', 'Magoo', 'IL', '0505533685');

INSERT INTO
  users (name, nickname, country_code, phone_number)
VALUES
  ('John Doe', 'Doey', 'US', '3471231234');

INSERT INTO
  users (name, nickname, country_code, phone_number)
VALUES
  ('Jane Doe', 'Janey', 'IL', '0501231234');

INSERT INTO
  users (name, nickname, country_code, phone_number)
VALUES
  ('Super Man', 'SM', 'US', '3475555555');

-- Room Users
INSERT INTO
  room_users (room_id, user_id)
VALUES
  (1, 1);

INSERT INTO
  room_users (room_id, user_id)
VALUES
  (2, 2);

INSERT INTO
  room_users (room_id, user_id)
VALUES
  (2, 3);