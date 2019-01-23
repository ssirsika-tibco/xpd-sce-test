package com.tibco.openspace.basegadget.client;
/** 
The Sample Gadget example is supplied "as is" with no warranties. The code in TestGadget is intended
as a simple illustration of the concepts and techniques needed to develop a custom gadget application.
It is not intended as a basis for production-ready code and should not be used as such. 
Any references to any third party software in the code is not under our control and we can offer no warranties
 */

import com.google.gwt.user.client.Window;

public class Logger
{
	static boolean showAlert=false;
	//use your favourite logging framework
	public static void log(String log)
	{
		if(showAlert){
			Window.alert(log);
		}
	}
	
	public static void log(String log,Throwable e)
	{
		if(showAlert){
			Window.alert(log);
		}
	}

	public static void trace(String trace)
	{
		if(showAlert){
			Window.alert(trace);
		}
	}

	public static void error(String error, Throwable exception)
	{
		if(showAlert){
			Window.alert(error);
		}
		
	}
	
	public static void error(String error)
	{
		if(showAlert){
			Window.alert(error);
		}
		
	}

	public static void info(String msg)
	{
		if(showAlert){
			Window.alert(msg);
		}
		
	}
}
