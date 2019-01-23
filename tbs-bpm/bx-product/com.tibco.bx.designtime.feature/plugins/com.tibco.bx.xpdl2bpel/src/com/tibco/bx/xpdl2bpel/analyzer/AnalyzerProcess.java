package com.tibco.bx.xpdl2bpel.analyzer;

import java.util.ArrayList;
import java.util.List;

import com.tibco.xpd.xpdl2.WebServiceOperation;

/**
 * Created by IntelliJ IDEA.
* User: goldberg
* Date: Sep 10, 2008
* Time: 4:58:17 PM
* To change this template use File | Settings | File Templates.
*/
public class AnalyzerProcess extends AnalyzerParentTask {
    private com.tibco.xpd.xpdl2.Process xpdlProcess;
    private List<AnalyzerParentTask> subFlows = new ArrayList<AnalyzerParentTask>();
    private int nextGenNumber = 1;
    private List<WebServiceOperation> webServiceOperations = new ArrayList<WebServiceOperation>();

    public AnalyzerProcess(com.tibco.xpd.xpdl2.Process xpdlProcess) {
        super(null);
        this.xpdlProcess = xpdlProcess;
        addSubFlow(this);
        super.setTaskType(Analyzer.TaskType.Process);
    }

    public com.tibco.xpd.xpdl2.Process getXpdlProcess() {
        return xpdlProcess;
    }

    public void addSubFlow(AnalyzerParentTask subFlow) {
        subFlows.add(subFlow);
    }

    public List<AnalyzerParentTask> getSubFlows() {
        return subFlows;
    }

    public String getName() {
        return xpdlProcess.getName();
    }

    public int getNextGenNumber() {
        return nextGenNumber++;
    }

    public List<WebServiceOperation> getWebServiceOperations() {
        return webServiceOperations;
    }

    public void addWebServiceOperations(WebServiceOperation webServiceOperation) {
        this.webServiceOperations.add(webServiceOperation);
    }
    
}
