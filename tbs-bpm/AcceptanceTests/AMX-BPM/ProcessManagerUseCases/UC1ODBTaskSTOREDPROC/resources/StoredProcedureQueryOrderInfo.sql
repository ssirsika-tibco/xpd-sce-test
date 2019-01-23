-- ================================================
-- Template generated from Template Explorer using:
-- Create Procedure (New Menu).SQL
--
-- Use the Specify Values for Template Parameters 
-- command (Ctrl-Shift-M) to fill in the parameter 
-- values below.
--
-- This block of comments will not be included in
-- the definition of the procedure.
-- ================================================
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
--USE bpm;
--DROP PROCEDURE dbo.QueryOrderInfo
--GO

CREATE PROCEDURE dbo.QueryOrderInfo 
	-- Add the parameters for the stored procedure here
	 @customerId varchar(50)
	--@custName varchar(50) OUTPUT
	--@gender varchar(50) OUTPUT
	--@maritalStatus varchar(50) OUTPUT,
	--@emailId varchar(50) OUTPUT,
	--@postCode varchar(50) OUTPUT,
	--@productId varchar(50) OUTPUT,
	--@productName varchar(50) OUTPUT,
	--@price money OUTPUT,
	--@orderId varchar(50) OUTPUT,
	--@qty int OUTPUT,
	--@orderDate DateTime OUTPUT
	
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

    -- Insert statements for procedure here
	Select Customer.custName, Customer.gender, Customer.maritalStatus, Customer.emailId, Customer.postCode, Product.productId, Product.productName, Product.price, OrderDetails.orderId, OrderDetails.qty, OrderDetails.orderDate from [dbo].[Order_Details] as OrderDetails, [dbo].[Product] as Product, [dbo].[Customer] as Customer where Customer.custId=@customerId AND OrderDetails.custId=Customer.custId AND OrderDetails.productId=Product.productId;

END
GO
