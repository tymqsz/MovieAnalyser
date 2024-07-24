use MovieDB;

/* top movies by genre */
DECLARE @genre nvarchar(20) = 'Romance';

SELECT TOP 10 title, avgRating 
FROM MOVIES
WHERE genre LIKE CONCAT('%', @genre, '%')
ORDER BY avgRating DESC;

/* best old movies */
SELECT TOP 10 title, year, avgRating 
FROM MOVIES
ORDER BY year;

/* best new movies */
SELECT TOP 10 title, year, avgRating
FROM MOVIES
ORDER BY year DESC;

/* average rating in 10 year brackets */

SELECT
    CASE
        WHEN [year] >= 1900 AND [year] < 1910 THEN '1900-1909'
        WHEN [year] >= 1910 AND [year] < 1920 THEN '1910-1919'
        WHEN [year] >= 1920 AND [year] < 1930 THEN '1920-1929'
        WHEN [year] >= 1930 AND [year] < 1940 THEN '1930-1939'
        WHEN [year] >= 1940 AND [year] < 1950 THEN '1940-1949'
        WHEN [year] >= 1950 AND [year] < 1960 THEN '1950-1959'
        WHEN [year] >= 1960 AND [year] < 1970 THEN '1960-1969'
        WHEN [year] >= 1970 AND [year] < 1980 THEN '1970-1979'
        WHEN [year] >= 1980 AND [year] < 1990 THEN '1980-1989'
        WHEN [year] >= 1990 AND [year] < 2000 THEN '1990-1999'
        WHEN [year] >= 2000 AND [year] < 2010 THEN '2000-2009'
        WHEN [year] >= 2010 AND [year] < 2020 THEN '2010-2019'
        WHEN [year] >= 2020 AND [year] < 2025 THEN '2020-2024'
        ELSE 'wrong'
    END AS decade,
    AVG(avgRating) AS rating,
	COUNT(*) AS movieCnt
FROM
    MOVIES
GROUP BY
    CASE
        WHEN [year] >= 1900 AND [year] < 1910 THEN '1900-1909'
        WHEN [year] >= 1910 AND [year] < 1920 THEN '1910-1919'
        WHEN [year] >= 1920 AND [year] < 1930 THEN '1920-1929'
        WHEN [year] >= 1930 AND [year] < 1940 THEN '1930-1939'
        WHEN [year] >= 1940 AND [year] < 1950 THEN '1940-1949'
        WHEN [year] >= 1950 AND [year] < 1960 THEN '1950-1959'
        WHEN [year] >= 1960 AND [year] < 1970 THEN '1960-1969'
        WHEN [year] >= 1970 AND [year] < 1980 THEN '1970-1979'
        WHEN [year] >= 1980 AND [year] < 1990 THEN '1980-1989'
        WHEN [year] >= 1990 AND [year] < 2000 THEN '1990-1999'
        WHEN [year] >= 2000 AND [year] < 2010 THEN '2000-2009'
        WHEN [year] >= 2010 AND [year] < 2020 THEN '2010-2019'
        WHEN [year] >= 2020 AND [year] < 2025 THEN '2020-2024'
        ELSE 'wrong'
    END
ORDER BY rating DESC;

/* best directors */
SELECT TOP 5 director, COUNT(*) as movies_count
FROM MOVIES
GROUP BY director
ORDER BY movies_count DESC; -- by movie count

SELECT TOP 5 director, AVG(avgRating) rating
FROM MOVIES
GROUP BY director
HAVING COUNT(*) > 1 -- more than one movie in top 250
ORDER BY rating DESC; -- by average rating

/* movies with given actors */
DECLARE @actor nvarchar(30) = 'Hopkins';

SELECT title, avgRating, cast
FROM MOVIES
WHERE cast LIKE CONCAT('%', @actor, '%');

SELECT * FROM MOVIES;