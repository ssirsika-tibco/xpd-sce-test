USE bpm
GO
/****** Object:  Table [dbo].[MyCustomer]    Script Date: 07/15/2009 01:23:05 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE amxbpm.MyCustomer(
	custId int NULL,
	custName varchar(50) NULL) 
GO
SET ANSI_PADDING OFF

select * from amxbpm.MyCustomer;