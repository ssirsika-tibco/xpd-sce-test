package com.tibco.bx.xpdl2bpel.analyzer;

import org.eclipse.emf.ecore.EObject;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by IntelliJ IDEA.
* User: goldberg
* Date: Sep 19, 2008
* Time: 1:34:10 PM
* To change this template use File | Settings | File Templates.
*/
public class AnalyzerIssue {
    private EObject obj;
    private String issueId;
    private List<String> msgData;

    public AnalyzerIssue(String issueId, EObject obj) {
        this.obj = obj;
        this.issueId = issueId;
        this.msgData = null;
    }

    public AnalyzerIssue(String issueId, EObject obj, String msgData) {
        this.obj = obj;
        this.issueId = issueId;
        this.msgData = new ArrayList<String>(Arrays.asList(msgData));
    }

    public AnalyzerIssue(String issueId, EObject obj, String[] msgData) {
        this.obj = obj;
        this.issueId = issueId;
        this.msgData = new ArrayList<String>(Arrays.asList(msgData));
    }

    public EObject getObj() {
        return obj;
    }

    public String getIssueId() {
        return issueId;
    }

    public List<String> getMsgData() {
        return msgData;
    }
}
