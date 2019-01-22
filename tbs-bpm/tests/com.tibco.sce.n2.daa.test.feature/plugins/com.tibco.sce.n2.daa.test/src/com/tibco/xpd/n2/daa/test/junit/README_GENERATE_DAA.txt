
** WHEN REGENERATING DAA's FOR DAA JUNIT TESTS **YOU MUST** set the Java property

	in Run Configuration => Arguments => VM Arguments as follows

    	 -Dbx_flownames_number_suffixed=TRUE
     
** THIS forces _BX_flow_xx names to be generated with NUMBER SUFFIX instead of UUID SUFFIX 

	as introduced by XPD-8210 (See ConverterContext.genUniqueActivityNameWithUUID()   
	
	If you do not do this then the DAA comparison will fail because the compare code
	sets this flag to ensure consistent comparison of bpel files.
	
** Sid 09/06/2017

	
  

