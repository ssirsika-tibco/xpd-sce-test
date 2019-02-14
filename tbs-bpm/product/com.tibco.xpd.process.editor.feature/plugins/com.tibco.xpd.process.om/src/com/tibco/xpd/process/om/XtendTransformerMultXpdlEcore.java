/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.process.om;

import org.eclipse.core.resources.IFile;

/**
 * Transform XPDL Model to multiple XPDL models with each process in turn
 * populated. This transformation accounts for changes through Ecore.
 * 
 * @author rsomayaj
 */
/*
 * Sid ACE-131 - we're going to hide Fragments importer for now as it uses OAW
 * and we want to drop that.
 */
public class XtendTransformerMultXpdlEcore {

    public boolean transform(IFile source) throws Exception {
        return false;
    }

    // private final static Logger LOG =
    // XpdResourcesPlugin.getDefault().getLogger();
    // /** */
    // private static final String EXTENSION_FILE =
    // "com::tibco::xpd::process::om::template::Xpdl2MultXpdlEcore";
    // //$NON-NLS-1$
    //
    // /** */
    // private static final String TRANSFORM_EXPRESSION = "transform";
    // //$NON-NLS-1$
    //
    // private IssuesImpl issues = null;
    //
    // public IssuesImpl getIssues() {
    // return issues;
    // }
    //
    // private final String xpdl2MetaModelEcoreURL = "/model/xpdl2.ecore";
    //
    // private final String xpdExtensionEcoreURL = "/model/XpdExtension.ecore";
    //
    // private final String xpdlModelSlot = "xpdlmodel"; //$NON-NLS-1$
    //
    // private final String xpdlModelOutputSlot = "xpdlModelOutput";
    // //$NON-NLS-1$
    //
    // private static final String REFLECTION_XPDL_PLUGIN_ID =
    // "com.tibco.xpd.xpdl2"; //$NON-NLS-1$
    //
    // private Bundle xpdl2Bundle;
    //
    // /**
    // * Performs the actual transformation
    // *
    // * @param source
    // * @param omFilePath
    // * @return
    // */
    // public boolean transform(IFile source) throws Exception {
    //
    // xpdl2Bundle = Platform.getBundle(REFLECTION_XPDL_PLUGIN_ID);
    // if (xpdl2Bundle == null) {
    // return false;
    // }
    // issues = new IssuesImpl();
    // final Workflow workflow = new Workflow();
    // final WorkflowContextDefaultImpl wfContext =
    // new WorkflowContextDefaultImpl();
    // URL url = null;
    // // register ecore package
    // EPackage.Registry.INSTANCE.put(EcorePackage.eNS_URI,
    // EcorePackage.eINSTANCE);
    // // add ecore model for xpdl2
    // url = xpdl2Bundle.getEntry(xpdl2MetaModelEcoreURL);
    // EmfMetaModel xpdl2MetaModel = new EmfMetaModel();
    // xpdl2MetaModel.setMetaModelFile(url.toString());
    // // add xsd meta model for
    // url = xpdl2Bundle.getEntry(xpdExtensionEcoreURL);
    // EmfMetaModel xpdExtMetaModel = new EmfMetaModel();
    // xpdExtMetaModel.setMetaModelFile(url.toString());
    //
    // // add xml reader components for each xpdl file we encounter so we can
    // // add them all into the output model slots
    // // ready to be passed into the xtend component later on
    // String expression = EXTENSION_FILE + "::" + TRANSFORM_EXPRESSION + "(";
    // //$NON-NLS-1$ //$NON-NLS-2$
    // Reader reader = new Reader();
    // String modelSlot = xpdlModelSlot;
    // expression += modelSlot;
    // reader.setModelSlot(modelSlot);
    // reader.setUri(source.getFullPath().toPortableString());
    // workflow.addComponent(reader);
    // expression += ")"; //$NON-NLS-1$
    //
    // // create the xtendComponent adding the meta models (defined earlier)
    // // and set the invocation expression value.
    // XtendComponent xtendComponent = new XtendComponent();
    // xtendComponent.addMetaModel(xpdl2MetaModel);
    // xtendComponent.addMetaModel(xpdExtMetaModel);
    // xtendComponent.setInvoke(expression);
    // xtendComponent.setOutputSlot(xpdlModelOutputSlot);
    // workflow.addComponent(xtendComponent);
    // ExpressionWithVarname expressionWithVarname =
    // new XMLWriter.ExpressionWithVarname();
    // expressionWithVarname.setVarName("docroot");
    // expression =
    // "'"
    // + source.getFullPath().removeLastSegments(1)
    // .toPortableString() + "/transformed/'+"
    // + EXTENSION_FILE + "::getFileName(docroot)";
    // expressionWithVarname.setExpression(expression);
    // XMLWriter xmlWriter = new XMLWriter();
    // xmlWriter.addMetaModel(xpdl2MetaModel);
    // xmlWriter.addMetaModel(xpdExtMetaModel);
    // xmlWriter.setModelSlot(xpdlModelOutputSlot);
    // xmlWriter.setUriExpression(expressionWithVarname);
    // workflow.addComponent(xmlWriter);
    // Display.getDefault().syncExec(new Runnable() {
    // public void run() {
    // workflow.invoke(wfContext, new NullProgressMonitor(), issues);
    // }
    // });
    // return true;
    // }
}
