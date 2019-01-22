package com.tibco.bx.debug.core.invoke.util;

public class StringUtils {
	
    public static final String EMPTY_STRING = ""; //$NON-NLS-1$
    
	public static final boolean equal(String str1,String str2){
		
		return str1 == null ? str2 == null : str1.equals(str2); 
	}
	public static final boolean equals(final Object left, final Object right) {
		return left == null ? right == null : ((right != null) && left
				.equals(right));
	}
	 public static final boolean validateString(String input)
	  {
	    return ((input != null) && (input.trim().length() > 0));
	  }

	  public static final boolean validateURL(String input)
	  {
		  return (input != null && input.matches("[a-zA-Z\\+\\-\\.]++:.*")); //$NON-NLS-1$
	  }
	  
	  public static final boolean isNotBlank(String input){
		  return input != null && input.trim().length() > 0;
	  }
	  
	  public static final boolean validateInteger(String input)
	  {
	    try
	    {
	      Integer.parseInt(input);
	      return true;
	    }
	    catch (NumberFormatException e)
	    {
	      return false;
	    }
	  }
}
