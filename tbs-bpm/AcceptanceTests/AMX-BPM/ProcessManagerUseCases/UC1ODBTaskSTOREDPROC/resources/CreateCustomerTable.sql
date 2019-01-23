USE [bpm]
GO

/****** Object:  Table [dbo].[Customer]    Script Date: 08/12/2010 11:02:30 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING ON
GO

CREATE TABLE [dbo].[Customer](
	[custId] [varchar](50) NOT NULL,
	[custName] [varchar](50) NOT NULL,
	[gender] [varchar](50) NOT NULL,
	[maritalStatus] [varchar](50) NOT NULL,
	[emailId] [varchar](50) NOT NULL,
	[postCode] [varchar](50) NOT NULL,
 CONSTRAINT [PK_Customer] PRIMARY KEY CLUSTERED 
(
	[custId] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

SET ANSI_PADDING OFF
GO

