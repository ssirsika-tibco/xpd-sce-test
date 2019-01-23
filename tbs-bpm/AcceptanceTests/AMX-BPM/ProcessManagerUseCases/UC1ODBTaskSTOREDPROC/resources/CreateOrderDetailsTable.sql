USE [bpm]
GO

/****** Object:  Table [dbo].[Order_Details]    Script Date: 08/12/2010 11:07:54 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING ON
GO

CREATE TABLE [dbo].[Order_Details](
	[orderId] [varchar](50) NOT NULL,
	[productId] [varchar](50) NOT NULL,
	[custId] [varchar](50) NOT NULL,
	[qty] [int] NOT NULL,
	[orderDate] [datetime] NOT NULL
) ON [PRIMARY]

GO

SET ANSI_PADDING OFF
GO

ALTER TABLE [dbo].[Order_Details]  WITH CHECK ADD  CONSTRAINT [FK_Order_Details_Customer] FOREIGN KEY([custId])
REFERENCES [dbo].[Customer] ([custId])
GO

ALTER TABLE [dbo].[Order_Details] CHECK CONSTRAINT [FK_Order_Details_Customer]
GO

