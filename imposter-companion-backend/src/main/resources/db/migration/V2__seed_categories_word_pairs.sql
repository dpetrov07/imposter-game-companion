-- Enables UUID generation
CREATE EXTENSION IF NOT EXISTS "pgcrypto";

-- Inserts categories
INSERT INTO category (id, name) VALUES
  (gen_random_uuid(), 'Movies'),
  (gen_random_uuid(), 'Countries'),
  (gen_random_uuid(), 'Jobs'),
  (gen_random_uuid(), 'Sports'),
  (gen_random_uuid(), 'Food'),
  (gen_random_uuid(), 'Places');

-- Insert word pairs
INSERT INTO word_pair (id, category_id, word, imposter_hint)
SELECT
  gen_random_uuid(),
  c.id,
  wp.word,
  wp.imposter_hint
FROM category c
JOIN (
  VALUES
    -- Movies
    ('Movies', 'Titanic', 'Ship'),
    ('Movies', 'Star Wars', 'Battle'),
    ('Movies', 'Avatar', 'Alien'),
    ('Movies', 'Jaws', 'Ocean'),
    ('Movies', 'Rocky', 'Gloves'),
    ('Movies', 'Frozen', 'Ice'),
    ('Movies', 'Gladiator', 'Fight'),
    ('Movies', 'Interstellar', 'Space'),
    ('Movies', 'Spider-Man', 'Hero'),
    ('Movies', 'Harry Potter', 'Magic'),

    -- Countries
    ('Countries', 'Italy', 'Local'),
    ('Countries', 'Japan', 'Order'),
    ('Countries', 'Brazil', 'Lively'),
    ('Countries', 'Canada', 'Wide'),
    ('Countries', 'France', 'Stylish'),
    ('Countries', 'Germany', 'Organized'),
    ('Countries', 'Australia', 'Far'),
    ('Countries', 'Mexico', 'Colorful'),
    ('Countries', 'Egypt', 'Old'),
    ('Countries', 'India', 'Varied'),

    -- Jobs
    ('Jobs', 'Doctor', 'Serious'),
    ('Jobs', 'Teacher', 'Helpful'),
    ('Jobs', 'Engineer', 'Careful'),
    ('Jobs', 'Chef', 'Fast'),
    ('Jobs', 'Pilot', 'Focused'),
    ('Jobs', 'Firefighter', 'Brave'),
    ('Jobs', 'Lawyer', 'Careful'),
    ('Jobs', 'Nurse', 'Busy'),
    ('Jobs', 'Electrician', 'Risky'),
    ('Jobs', 'Mechanic', 'Hands-on'),

    -- Sports
    ('Sports', 'Soccer', 'Flowing'),
    ('Sports', 'Basketball', 'Quick'),
    ('Sports', 'Football', 'Planned'),
    ('Sports', 'Baseball', 'Slow'),
    ('Sports', 'Tennis', 'Tense'),
    ('Sports', 'Hockey', 'Tough'),
    ('Sports', 'Golf', 'Calm'),
    ('Sports', 'Swimming', 'Steady'),
    ('Sports', 'Boxing', 'Rough'),
    ('Sports', 'Running', 'Long'),

    -- Food
    ('Food', 'Pizza', 'Shared'),
    ('Food', 'Burger', 'Messy'),
    ('Food', 'Pasta', 'Heavy'),
    ('Food', 'Sushi', 'Clean'),
    ('Food', 'Tacos', 'Spicy'),
    ('Food', 'Steak', 'Juicy'),
    ('Food', 'Ice Cream', 'Sweet'),
    ('Food', 'Salad', 'Fresh'),
    ('Food', 'Sandwich', 'Easy'),
    ('Food', 'Soup', 'Warm'),

    -- Places
    ('Places', 'Airport', 'Busy'),
    ('Places', 'Hospital', 'Quiet'),
    ('Places', 'School', 'Loud'),
    ('Places', 'Beach', 'Relaxed'),
    ('Places', 'Mall', 'Crowded'),
    ('Places', 'Library', 'Silent'),
    ('Places', 'Stadium', 'Loud'),
    ('Places', 'Restaurant', 'Social'),
    ('Places', 'Gym', 'Active'),
    ('Places', 'Park', 'Open')
) AS wp(category_name, word, imposter_hint)
ON c.name = wp.category_name;