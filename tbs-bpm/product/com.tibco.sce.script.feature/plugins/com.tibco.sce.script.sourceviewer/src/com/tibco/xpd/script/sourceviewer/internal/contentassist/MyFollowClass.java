package com.tibco.xpd.script.sourceviewer.internal.contentassist;

import java.util.ArrayList;
import java.util.List;

import com.tibco.xpd.script.model.client.IScriptRelevantData;

public class MyFollowClass {

	private String className;
	
	private IScriptRelevantData genericContext;

	private ArrayList<MyCompletionStringNode> list = new ArrayList<MyCompletionStringNode>();

	public MyFollowClass(String className) {
		this.className = className;
	}

	public List<MyCompletionStringNode> getCompletionNodes() {
		return this.list;
	}

	public String getClassName() {
		return className;
	}
	
	public void addMyCompletionStringNode(MyCompletionStringNode node){
		list.add(node);
	}

    public IScriptRelevantData getGenericContext() {
        return genericContext;
    }

    public void setGenericContext(IScriptRelevantData genericContext) {
        this.genericContext = genericContext;
    }

	
}
