use MovieDB

/* merge all 3 tables into one */
TRUNCATE TABLE MOVIES;

INSERT INTO MOVIES (title, year, genre, length, imdbRating, imdbMetaRating,
                    rtRating, rtMetaRating, mcRating, mcMetaRating, director, cast)
SELECT IMDB.title, IMDB.year, IMDB.genre, IMDB.length, IMDB.imdbRating, IMDB.imdbMetaRating,
       RT.rtRating, RT.rtMetaRating, MC.mcRating, MC.mcMetaRating, IMDB.director, IMDB.cast
FROM dbo.RT
JOIN dbo.IMDB ON dbo.RT.title = dbo.IMDB.title
JOIN MC ON MC.title = IMDB.title;


/* fix name splitting */
--SELECT * FROM MOVIES;
IF OBJECT_ID('split_names', 'FN') is not null DROP FUNCTION split_names;

GO
CREATE FUNCTION split_names(@string varchar(80))
RETURNS varchar(80)
BEGIN
	DECLARE @output varchar(80);
	DECLARE @i int = 2; /* starting from the second char */
	DECLARE @len int = len(@string);
	DECLARE @prev_char char(1) = substring(@string, 1, 1); /* taking the 1. char */
	DECLARE @crt_char char(1);

	WHILE @i <= @len		
		BEGIN
			SET @crt_char = SUBSTRING(@string, @i, 1);
			
			/* if capital char comes right after lower case char
				-> add ', ' and split names */ 
			IF		UNICODE(@prev_char) >= 97 AND UNICODE(@prev_char) <= 122
				AND UNICODE(@crt_char) >= 65 AND UNICODE(@crt_char) <= 90
				BEGIN
					SET @output = CONCAT(@output, @prev_char, ', ');
				END
			ELSE
				BEGIN
					SET @output = CONCAT(@output, @prev_char);
				END

			SET @prev_char = @crt_char;
			SET @i = @i + 1;
		END				
		
		/* add remaining letter */
		SET @output = CONCAT(@output, @prev_char);

	RETURN @output
END

GO

/* apply the function */
IF OBJECT_ID('tempdb..#temp') is not null DROP TABLE #temp;

SELECT title, year, genre, length, imdbRating, imdbMetaRating, rtRating, rtMetaRating, 
	   mcRating, mcMetaRating, dbo.split_names(director) AS director, dbo.split_names(cast) AS cast
INTO #temp
FROM MOVIES;

TRUNCATE TABLE MOVIES;

INSERT INTO MOVIES (title, year, genre, length, imdbRating, imdbMetaRating,
                    rtRating, rtMetaRating, mcRating, mcMetaRating, director, cast)
SELECT title, year, genre, length, imdbRating, imdbMetaRating,
       rtRating, rtMetaRating, mcRating, mcMetaRating, director, cast
FROM #temp;

/* manual cleaning */
UPDATE MOVIES
SET [cast] = 'Dean-Charles Chapman, George Mac, Kay, Daniel Mays'
WHERE [cast] = 'Dean-Charles Chapman, George MacKay, Daniel Mays';

UPDATE MOVIES
SET [director] = 'Steve Mc, Queen'
WHERE [director] = 'Steve McQueen';

UPDATE MOVIES
SET [cast] = 'Kevin Costner, Mary Mc, Donnell, Graham Greene'
WHERE [cast] = 'Kevin Costner, Mary McDonnell, Graham Greene';

UPDATE MOVIES
SET year = '1996',
	genre = 'Crime Thriller',
	length = '1h 38m',
	imdbRating = '8.1',
	imdbMetaRating = NULL,
	director = 'Joel Coen, Ethan Coen',
	cast = 'William H. Macy, Frances McDormand, Steve Buscemi'
WHERE title = 'Fargo';

UPDATE MOVIES
SET [cast] = REPLACE([cast], 'Leonardo Di, Caprio', 'Leonardo DiCaprio')
WHERE [cast] LIKE '%Leonardo Di%';

/* standardize rating */
IF OBJECT_ID('standardize', 'FN') is not null DROP FUNCTION standardize;

GO
CREATE FUNCTION standardize(@old nvarchar(5))
RETURNS nvarchar(5)
BEGIN
	DECLARE @new nvarchar(5) = NULL;
	DECLARE @val float;
	DECLARE @pos int = CHARINDEX('%', @old);

	IF TRY_CAST(@old AS float) <= 10 SET @new = @old
	ELSE IF @old != 'null' AND @old != 'tbd' 
	BEGIN
		IF @pos != 0
		BEGIN
			SET @val = CAST(SUBSTRING(@old, 1, @pos-1) AS float) / 10;
			SET @new = CAST(@val AS nvarchar);
		END
		ELSE
		BEGIN
			SET @val = CAST(@old AS float) / 10;
			SET @new = CAST(@val AS nvarchar);
		END
	END

	RETURN @new;
END

GO

IF OBJECT_ID('tempdb..#temp') is not null DROP TABLE #temp;

SELECT title, year, genre, length, dbo.standardize(imdbRating) as imdbRating,
	   dbo.standardize(imdbMetaRating) as imdbMetaRating, dbo.standardize(rtRating) as rtRating,
	   dbo.standardize(rtMetaRating) as rtMetaRating, dbo.standardize(mcRating) as mcRating,
	   dbo.standardize(mcMetaRating) as mcMetaRating, director, cast
INTO #temp
FROM MOVIES;

TRUNCATE TABLE MOVIES;

INSERT INTO MOVIES (title, year, genre, length, imdbRating, imdbMetaRating,
                    rtRating, rtMetaRating, mcRating, mcMetaRating, director, cast)
SELECT title, year, genre, length, imdbRating, imdbMetaRating,
       rtRating, rtMetaRating, mcRating, mcMetaRating, director, cast
FROM #temp;


/* fix datatypes */
ALTER TABLE MOVIES
ALTER COLUMN imdbRating float;

ALTER TABLE MOVIES
ALTER COLUMN imdbMetaRating float;

ALTER TABLE MOVIES
ALTER COLUMN rtRating float;

ALTER TABLE MOVIES
ALTER COLUMN rtMetaRating float;

ALTER TABLE MOVIES
ALTER COLUMN mcRating float;

ALTER TABLE MOVIES
ALTER COLUMN mcMetaRating float;

/* add average rating */
GO
ALTER TABLE MOVIES
ADD avgRating float;

IF OBJECT_ID('average_rating', 'FN') is not null DROP FUNCTION average_rating;
GO
CREATE FUNCTION average_rating(@a float, @b float, @c float,
							   @d float, @e float, @f float)
RETURNS float
BEGIN
	DECLARE @sum float = 0;
	DECLARE @cnt int = 0;
	
	IF @a is not NULL
	BEGIN
		SET @sum = @sum + @a;
		SET @cnt = @cnt + 1;
	END
	IF @b is not NULL
	BEGIN
		SET @sum = @sum + @b;
		SET @cnt = @cnt + 1;
	END
	IF @c is not NULL
	BEGIN
		SET @sum = @sum + @c;
		SET @cnt = @cnt + 1;
	END
	IF @d is not NULL
	BEGIN
		SET @sum = @sum + @d;
		SET @cnt = @cnt + 1;
	END
	IF @e is not NULL
	BEGIN
		SET @sum = @sum + @e;
		SET @cnt = @cnt + 1;
	END
	IF @f is not NULL
	BEGIN
		SET @sum = @sum + @f;
		SET @cnt = @cnt + 1;
	END
	RETURN ROUND(@sum / @cnt, 2)  ;
END

GO
UPDATE MOVIES
SET avgRating = dbo.average_rating(imdbRating, imdbMetaRating,
								   rtRating, rtMetaRating,
								   mcRating, mcMetaRating);

/* calc length in minutes */
IF OBJECT_ID('minutes_len', 'FN') is not null DROP FUNCTION minutes_len;

GO
CREATE FUNCTION minutes_len(@std_len nvarchar(20))
RETURNS int
BEGIN
	DECLARE @result int = 0;
	DECLARE @h_pos int = CHARINDEX('h',@std_len);
	DECLARE @m_pos int = CHARINDEX('m',@std_len);

	IF @h_pos != 0 SET @result = 60*CAST(SUBSTRING(@std_len, 1, @h_pos-1) AS int);

	IF @m_pos != 0 SET @result = @result + CAST(SUBSTRING(@std_len, @h_pos+2, @m_pos-2-@h_pos) AS int) 

	RETURN @result;
END
GO
ALTER TABLE MOVIES
ADD [length (minutes)] int;

GO
UPDATE MOVIES
SET [length (minutes)] = dbo.minutes_len(length);

SELECT * FROM MOVIES;

