package com.tibco.processframework.pojo;

public class ProcessFramework {
	
	/*public void echoTypes(String myText,  int myInteger, boolean myBoolean, XMLGregorianCalendar myDate, double myDecimal, XMLGregorianCalendar myTime, XMLGregorianCalendar myDateTime)
	{
		System.out.println(myText);
		System.out.println(myInteger);
		System.out.println(myBoolean);
		System.out.println(myDate);
		System.out.println(myDecimal);
		System.out.println(myDateTime);
		System.out.println(myTime);
		
	}*/
	
	public void echoTypes(String myText,  int myInteger, boolean myBoolean,  double myDecimal)
	{
		System.out.println(myText);
		System.out.println(myInteger);
		System.out.println(myBoolean);
		System.out.println(myDecimal);
		
	}
	public static void main(String[] args) {
		String s = "Hello";
		int i = 50;
		boolean b = true;
		double dou = 3.14;
		
		ProcessFramework p = new ProcessFramework();
		p.echoTypes (s,i,b,dou);
		
	}

}
