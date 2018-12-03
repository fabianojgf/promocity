--Distancia em m

select name, latitude, longitude,
       ( 6371000 * acos( cos( radians(-3.759080) ) 
              * cos( radians( latitude ) ) 
              * cos( radians( longitude ) - radians(-38.538164) ) 
              + sin( radians(-3.759080) ) 
              * sin( radians( latitude ) ) ) ) AS distance 
from stores;

--Distancia em Km

select name, latitude, longitude,
       ( 6371 * acos( cos( radians(-3.759080) ) 
              * cos( radians( latitude ) ) 
              * cos( radians( longitude ) - radians(-38.538164) ) 
              + sin( radians(-3.759080) ) 
              * sin( radians( latitude ) ) ) ) AS distance 
from stores;

--Distancia em Ml

select name, latitude, longitude,
       ( 3959 * acos( cos( radians(-3.759080) ) 
              * cos( radians( latitude ) ) 
              * cos( radians( longitude ) - radians(-38.538164) ) 
              + sin( radians(-3.759080) ) 
              * sin( radians( latitude ) ) ) ) AS distance 
from stores;

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

SELECT *, distance(-3.759080, -38.538164, latitude, longitude, 2) FROM stores where distance(-3.759080, -38.538164, latitude, longitude, 2) > 4.5;

SELECT *, distance(-3.759080, -38.538164, latitude, longitude, 2) FROM stores where distance(-3.759080, -38.538164, latitude, longitude, 2) > (4.5 as double);



select store0_.id as id1_2_, store0_.active as active2_2_, 
store0_.address as address3_2_, store0_.city as city4_2_, 
store0_.latitude as latitude5_2_, store0_.longitude as longitud6_2_, 
store0_.name as name7_2_, store0_.radius as radius8_2_, 
store0_.state as state9_2_, store0_.user_id as user_id10_2_ 
from stores store0_ 
where is_in_radius(-3.759080, -38.538164, store0_.latitude, store0_.longitude, 2, store0_.radius)=1

