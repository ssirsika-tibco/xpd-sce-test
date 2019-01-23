Sid 8 July 2013

Decided not to introduce this test (now) because this test case generates invalid BOMs in v3.6.0 V18. 
Looks like when WSDL message refs part defined in a different imported WSDL then the type for the operation 
parameters isn’t set correctly in BOM. 
