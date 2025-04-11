-- Clear existing data
DELETE FROM flights;
DELETE FROM planes;
DELETE FROM airports;

-- First get the max values
SET @max_airport = (SELECT COALESCE(MAX(id)+1, 1) FROM airport);
SET @max_plane = (SELECT COALESCE(MAX(id)+1, 1) FROM plane);
SET @max_flight = (SELECT COALESCE(MAX(id)+1, 1) FROM flight);

-- Then reset sequences
ALTER SEQUENCE airport_sequence RESTART WITH @max_airport;
ALTER SEQUENCE plane_sequence RESTART WITH @max_plane;
ALTER SEQUENCE flight_sequence RESTART WITH @max_flight;