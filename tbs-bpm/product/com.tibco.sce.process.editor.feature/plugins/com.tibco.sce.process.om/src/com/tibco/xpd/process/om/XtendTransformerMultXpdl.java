/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.process.om;

import java.net.URL;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.swt.widgets.Display;
import org.openarchitectureware.workflow.Workflow;
import org.openarchitectureware.workflow.WorkflowContextDefaultImpl;
import org.openarchitectureware.workflow.issues.IssuesImpl;
import org.openarchitectureware.workflow.monitor.NullProgressMonitor;
import org.openarchitectureware.xsd.XMLReader;
import org.openarchitectureware.xsd.XMLWriter;
import org.openarchitectureware.xsd.XSDMetaModel;
import org.openarchitectureware.xsd.XMLWriter.ExpressionWithVarname;
import org.openarchitectureware.xtend.XtendComponent;

/**
 * Transform XPDL Model to multiple XPDL models with each process in turn populated.
 * <p>
 * <i>Created: 10 September 2008</i>
 * </p>
 * 
 * @author Gary Lewis
 */
public class XtendTransformerMultXpdl {   
    
    /** */
    private static final String EXTENSION_FILE = "com::tibco::xpd::process::om::template::Xpdl2MultXpdl"; //$NON-NLS-1$
    /** */
    private static final String TRANSFORM_EXPRESSION = "transform"; //$NON-NLS-1$
    
    private IssuesImpl issues = null;

    public IssuesImpl getIssues() {
        return issues;
    }
    
    private final String xsdMetaModelURL = "model/TC-1025_bpmnxpdl_24.xsd";   //$NON-NLS-1$
    
    private final String xsdMetaModelURL2 = "model/bpmnxpdl_31.xsd";   //$NON-NLS-1$
    
    private final String xsdMetaModelID = "xsdmm1";   //$NON-NLS-1$
    
    private final String xsdMetaModelID2 = "xsdmm2";   //$NON-NLS-1$
    
    private static XSDMetaModel xsdMetaModel = null;
    
    private static XSDMetaModel xsdMetaModel2 = null;
    
    private final String xpdlModelSlot =  "xpdlmodel"; //$NON-NLS-1$    
    
    private final String xpdlModelOutputSlot = "xpdlModelOutput"; //$NON-NLS-1$


    /**
     * Performs the actual transformation
     * @param source
     * @param omFilePath
     * @return
     */
    public boolean transform(IFile source) throws Exception{
        issues = new IssuesImpl();
        final Workflow workflow = new Workflow();        
        final WorkflowContextDefaultImpl wfContext = new WorkflowContextDefaultImpl();
        URL url = null;
        
        // register ecore package
        EPackage.Registry.INSTANCE.put(EcorePackage.eNS_URI, EcorePackage.eINSTANCE);
                
        // add xsd meta model for TC-1025_schema_10_xpdl.xsd
        if (xsdMetaModel == null){
            url = this.getClass().getResource(xsdMetaModelURL);            
            xsdMetaModel = new XSDMetaModel();        
            xsdMetaModel.addSchemaFile(url.getPath());
            xsdMetaModel.setId(xsdMetaModelID);
        }
                
        // add xsd meta model for bpmnxpdl_31.xsd
        if (xsdMetaModel2 == null){
            url = this.getClass().getResource(xsdMetaModelURL2);         
            xsdMetaModel2 = new XSDMetaModel();
            xsdMetaModel2.addSchemaFile(url.getPath());
            xsdMetaModel2.setId(xsdMetaModelID2); 
        }
        
        // add xml reader components for each xpdl file we encounter so we can add them all into the output model slots
        // ready to be passed into the xtend component later on
        
        String expression = EXTENSION_FILE + "::" + TRANSFORM_EXPRESSION + "("; //$NON-NLS-1$ //$NON-NLS-2$       
        
        XMLReader xmlReader = new XMLReader();
        xmlReader.addMetaModel(xsdMetaModel);
        xmlReader.addMetaModel(xsdMetaModel2);
        String modelSlot = xpdlModelSlot;                
        xmlReader.setModelSlot(modelSlot);
        String path = source.getFullPath().toPortableString();
        xmlReader.setUri(path);            
        workflow.addComponent(xmlReader);           
        
        expression += modelSlot;
        expression += ")"; //$NON-NLS-1$
        
        // create the xtendComponent adding the meta models (defined earlier) and set the invocation expression value.
        XtendComponent xtendComponent = new XtendComponent();
        xtendComponent.addMetaModel(xsdMetaModel);
        xtendComponent.addMetaModel(xsdMetaModel2);        
        xtendComponent.setInvoke(expression);
        xtendComponent.setOutputSlot(xpdlModelOutputSlot);
        workflow.addComponent(xtendComponent);   
        
        ExpressionWithVarname expressionWithVarname = new XMLWriter.ExpressionWithVarname();
        expressionWithVarname.setVarName("docroot");
        expression = "'"+source.getFullPath().removeLastSegments(1).toPortableString()+"/transformed/'+"+ EXTENSION_FILE +"::getFileName(docroot)";        
        //expression = "'file://C://dltk/'+"+ EXTENSION_FILE +"::getFileName(docroot)";
        expressionWithVarname.setExpression(expression);
        
        XMLWriter xmlWriter = new XMLWriter();
        xmlWriter.addMetaModel(xsdMetaModel);
        xmlWriter.addMetaModel(xsdMetaModel2);  
        xmlWriter.setModelSlot(xpdlModelOutputSlot);
        xmlWriter.setUriExpression(expressionWithVarname);
        workflow.addComponent(xmlWriter);
        
        Display.getDefault().syncExec(new Runnable() {
            public void run() {    
               workflow.invoke(wfContext,  new NullProgressMonitor(), issues);               
            }
        });
        
        //Resource resource = xmlWriter.getResourceSet().getResources().get(0);
        //resource.unload();
        
        return true;
    }
}
