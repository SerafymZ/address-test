IF NOT EXISTS (SELECT * FROM sysobjects WHERE name='address' and xtype='U')
CREATE TABLE dbo.address(
	id bigint IDENTITY(1,1) NOT NULL PRIMARY KEY,
	address nvarchar(50) NULL
);