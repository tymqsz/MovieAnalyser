USE MovieDB

/* if tables already exist - delete them */
IF OBJECT_ID('dbo.IMDB', 'U') is not NULL
	BEGIN
		DROP TABLE dbo.IMDB
	END
IF OBJECT_ID('dbo.RT', 'U') is not NULL
	BEGIN
		DROP TABLE dbo.RT
	END
IF OBJECT_ID('dbo.MC', 'U') is not NULL
	BEGIN
		DROP TABLE dbo.MC
	END
IF OBJECT_ID('dbo.MOVIES', 'U') is not NULL
	BEGIN
		DROP TABLE dbo.MOVIES
	END

/* create all 4 needed tables */
CREATE TABLE dbo.IMDB(
    title nvarchar(70) PRIMARY KEY,
    [year] nvarchar(4),
    genre nvarchar(30),
    [length] nvarchar(10),
    imdbRating nvarchar(5),
    imdbMetaRating nvarchar(5),
    rtRating nvarchar(5),
    rtMetaRating nvarchar(5),
    mcRating nvarchar(5),
    mcMetaRating nvarchar(5),
    director nvarchar(50),
    [cast] nvarchar(80)
);

CREATE TABLE dbo.RT(
    title nvarchar(70) PRIMARY KEY,
    [year] nvarchar(4),
    genre nvarchar(30),
    [length] nvarchar(10),
    imdbRating nvarchar(5),
    imdbMetaRating nvarchar(5),
    rtRating nvarchar(5),
    rtMetaRating nvarchar(5),
    mcRating nvarchar(5),
    mcMetaRating nvarchar(5),
    director nvarchar(50),
    [cast] nvarchar(60)
);

CREATE TABLE dbo.MC(
    title nvarchar(70) PRIMARY KEY,
    [year] nvarchar(4),
    genre nvarchar(30),
    [length] nvarchar(10),
    imdbRating nvarchar(5),
    imdbMetaRating nvarchar(5),
    rtRating nvarchar(5),
    rtMetaRating nvarchar(5),
    mcRating nvarchar(5),
    mcMetaRating nvarchar(5),
    director nvarchar(50),
    [cast] nvarchar(60)
);

CREATE TABLE dbo.MOVIES(
    title nvarchar(70) PRIMARY KEY,
    [year] nvarchar(4),
    genre nvarchar(30),
    [length] nvarchar(10),
    imdbRating nvarchar(5),
    imdbMetaRating nvarchar(5),
    rtRating nvarchar(5),
    rtMetaRating nvarchar(5),
    mcRating nvarchar(5),
    mcMetaRating nvarchar(5),
    director nvarchar(80),
    [cast] nvarchar(80)
);

/* insert data into tables */
BULK INSERT dbo.IMDB
FROM 'C:\Users\topiw\OneDrive\Desktop\data\imdb.txt'
WITH(
	FIELDTERMINATOR = ';',
	ROWTERMINATOR = '0x0A',
	CODEPAGE =  '65001'  
);

BULK INSERT dbo.RT
FROM 'C:\Users\topiw\OneDrive\Desktop\data\rt.txt'
WITH(
	FIELDTERMINATOR = ';',
	ROWTERMINATOR = '0x0A',
	CODEPAGE =  '65001'  
);

BULK INSERT dbo.MC
FROM 'C:\Users\topiw\OneDrive\Desktop\data\mc.txt'
WITH(
	FIELDTERMINATOR = ';',
	ROWTERMINATOR = '0x0A',
	CODEPAGE =  '65001'  
);