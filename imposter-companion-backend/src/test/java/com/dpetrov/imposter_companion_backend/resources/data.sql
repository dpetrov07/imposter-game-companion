INSERT INTO category (id, name)
VALUES
  ('11111111-1111-1111-1111-111111111111', 'Sports'),
  ('22222222-2222-2222-2222-222222222222', 'School');

INSERT INTO word_pair (id, word, imposter_hint, category_id)
VALUES
  (
    'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa',
    'Soccer',
    'Feet',
    '11111111-1111-1111-1111-111111111111'
  ),
  (
    'bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb',
    'Football',
    'Throw',
    '11111111-1111-1111-1111-111111111111'
  ),
  (
    'cccccccc-cccc-cccc-cccc-cccccccccccc',
    'Baseball',
    'Swing',
    '11111111-1111-1111-1111-111111111111'
  ),
  (
    'dddddddd-dddd-dddd-dddd-dddddddddddd',
    'Pencil',
    'Write',
    '22222222-2222-2222-2222-222222222222'
  ),
  (
    'eeeeeeee-eeee-eeee-eeee-eeeeeeeeeeee',
    'Teacher',
    'Person',
    '22222222-2222-2222-2222-222222222222'
  ),
  (
    'ffffffff-ffff-ffff-ffff-ffffffffffff',
    'Books',
    'Paper',
    '22222222-2222-2222-2222-222222222222'
  );