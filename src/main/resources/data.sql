-- Clear existing data
DELETE FROM flights;
DELETE FROM planes;
DELETE FROM airports;

-- Reset and initialize sequences to start at 1
ALTER SEQUENCE IF EXISTS airport_sequence RESTART WITH 1;
ALTER SEQUENCE IF EXISTS plane_sequence RESTART WITH 1;
ALTER SEQUENCE IF EXISTS flight_sequence RESTART WITH 1;