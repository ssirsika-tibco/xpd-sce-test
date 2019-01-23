
-- =============================================
-- Author:		Ravi
-- Create date: 
-- Description:	
-- =============================================
CREATE OR REPLACE PROCEDURE swpro.QueryCustomerName(
	customerId IN NUMBER,
	customerName OUT varchar2
) 
IS
BEGIN
 SELECT custName into customerName from swpro.MyCustomer where custId=customerId
END ;
