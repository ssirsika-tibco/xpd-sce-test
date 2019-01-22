/*
* ENVIRONMENT:    Java Generic
*
* COPYRIGHT:      (C) 2012 TIBCO Software Inc
*/
package com.tibco.openspace.basegadget.client;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.ScriptElement;

public class SimpleScriptInjector
{
 public static void injectScript(String url){
	 	Element head = Document.get().getElementsByTagName("head").getItem(0);
		ScriptElement script = Document.get().createScriptElement();
		script.setSrc(url);
		head.appendChild(script);
 }
}
