CREATE TABLE radius (
       id INT NOT NULL,
       description VARCHAR(20) NOT NULL,
       size DOUBLE NOT NULL,
       PRIMARY KEY(id)
);

INSERT INTO radius VALUES (1, 'MILES', 3959), (2, 'KILOMETERS', 6371), (3, 'METERS', 6371000);


DROP FUNCTION IF EXISTS distance;

DELIMITER $
CREATE FUNCTION distance(lat1 DOUBLE, long1 DOUBLE, lat2 DOUBLE, long2 DOUBLE, type_radius INT) RETURNS DOUBLE
BEGIN
       DECLARE r DOUBLE;
       DECLARE distance DOUBLE;

       SET r = (SELECT size FROM radius WHERE id = type_radius);
       SET distance = ( r * acos( 
                cos( radians( lat1 ) ) 
              * cos( radians( lat2 ) ) 
              * cos( radians( long2 ) - radians(long1) ) 
              + sin( radians(lat1 ) ) 
              * sin( radians( lat2 ) ) ) );

       RETURN distance;
END
$
DELIMITER ;

DROP FUNCTION IF EXISTS is_in_radius;

DELIMITER $
CREATE FUNCTION is_in_radius(lat1 DOUBLE, long1 DOUBLE, lat2 DOUBLE, long2 DOUBLE, type_radius INT, radius DOUBLE) RETURNS TINYINT(1)
BEGIN
       DECLARE distance DOUBLE;

       SET distance = distance(lat1, long1, lat2, long2, type_radius);

       RETURN distance <= radius;
END
$
DELIMITER ;

SELECT * FROM stores WHERE is_in_radius(-3.759080, -38.538164, latitude, longitude, 2, radius);

SELECT *, distance(-3.759080, -38.538164, latitude, longitude, 2) FROM stores;