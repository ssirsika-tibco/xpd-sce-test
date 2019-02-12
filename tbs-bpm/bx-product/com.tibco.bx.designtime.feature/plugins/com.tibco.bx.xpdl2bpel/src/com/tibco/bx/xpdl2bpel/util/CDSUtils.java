/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.bx.xpdl2bpel.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.bpel.model.BPELFactory;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Property;
import org.eclipse.wst.wsdl.Definition;
import org.eclipse.wst.wsdl.Part;

import com.tibco.bx.xpdl2bpel.N2PEConstants;
import com.tibco.bx.xpdl2bpel.converter.internal.ConverterContext;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ActivityInterfaceData;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ActivityInterfaceDataUtil;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.bom.resources.BOMResourcesPlugin;
import com.tibco.xpd.bom.types.PrimitivesUtil;
import com.tibco.xpd.bom.types.api.BomTypesUtil;
import com.tibco.xpd.implementer.resources.xpdl2.properties.JavaScriptConceptUtil;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.indexer.IndexerItem;
import com.tibco.xpd.resources.indexer.IndexerItemImpl;
import com.tibco.xpd.resources.indexer.IndexerService;
import com.tibco.xpd.resources.internal.indexer.IndexerServiceImpl;
import com.tibco.xpd.resources.util.SpecialFolderUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.ui.util.NameUtil;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.DataType;
import com.tibco.xpd.xpdl2.DirectionType;
import com.tibco.xpd.xpdl2.ExternalReference;
import com.tibco.xpd.xpdl2.Message;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TaskService;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * @author kupadhya
 * 
 */
public class CDSUtils {

    private static final String INDEXER_ID =
            "com.tibco.xpd.n2.bom.gen.cdsIndexer"; //$NON-NLS-1$

    /**
     * Returns fully qualified CDS package name which contains the CDS class
     * referred from the process fieldName
     * 
     * @param fieldName
     * @param xpdlProcess
     * @return null if the fieldName does not refer to BOM class
     */
    public static String getCDSPackageName(ProcessRelevantData prd) {
        IndexerItem indexerItem = getIndexerItem(prd);
        if (indexerItem == null) {
            return null;
        }
        String packageName = indexerItem.get("packageName"); //$NON-NLS-1$
        return packageName;
    }

    /**
     * Returns CDS class name <b>(not qualified by the package name)</b>
     * generated for the BOM class referred from the process fieldName
     * 
     * @param fieldName
     * @param xpdlProcess
     * @return null if the fieldName does not refer to BOM class
     */
    public static String getCDSClassName(ProcessRelevantData prd) {
        IndexerItem indexerItem = getIndexerItem(prd);
        if (indexerItem == null) {
            return null;
        }
        String packageName = indexerItem.get("packageName"); //$NON-NLS-1$
        String className = indexerItem.getName();
        String fullyQualifiedClassName = packageName + "." + className; //$NON-NLS-1$
        return fullyQualifiedClassName;
    }

    /**
     * Returns CDS factory class name for the BOM package that contains the BOM
     * class referred from the process fieldName
     * 
     * @param fieldName
     * @param xpdlProcess
     * @return null if the fieldName does not refer to BOM class
     */
    public static String getCDSFactoryClassName(ProcessRelevantData prd) {
        IndexerItem indexerItem = getIndexerItem(prd);
        if (indexerItem == null) {
            return null;
        }
        String packageName = indexerItem.get("packageName"); //$NON-NLS-1$
        String className = indexerItem.get("factoryName"); //$NON-NLS-1$
        String fullyQualifiedClassName = packageName + "." + className; //$NON-NLS-1$
        return fullyQualifiedClassName;
    }

    /**
     * Returns CDS factory method name for the BOM class referred from the
     * process fieldName
     * 
     * @param fieldName
     * @param xpdlProcess
     * @return null if the fieldName does not refer to BOM class
     */
    public static String getCDSFactoryMethodName(ProcessRelevantData prd) {
        IndexerItem indexerItem = getIndexerItem(prd);
        if (indexerItem == null) {
            return null;
        }
        String className = indexerItem.getName();
        String createMethodName = "create" + className; //$NON-NLS-1$ //$NON-NLS-2$
        return createMethodName;
    }

    private static IndexerItem getIndexerItem(ProcessRelevantData prd) {
        IndexerItem indexerRow = null;
        URI bomEntityURI = getBOMClassURI(prd);
        if (bomEntityURI == null) {
            return null;
        }
        IndexerItemImpl criteria = new IndexerItemImpl();
        criteria.set(IndexerServiceImpl.ATTRIBUTE_URI, bomEntityURI.toString());

        IndexerService service =
                XpdResourcesPlugin.getDefault().getIndexerService();
        Collection<IndexerItem> query = service.query(INDEXER_ID, criteria);
        for (IndexerItem indexerItem : query) {
            indexerRow = indexerItem;
            break;
        }
        return indexerRow;

    }

    public static Map<String, ProcessRelevantData> getActivityOutDataList(
            Activity activity) {
        Map<String, ProcessRelevantData> toReturn =
                new HashMap<String, ProcessRelevantData>();
        if (!(activity.getImplementation() instanceof Task)) {
            return toReturn;
        }
        TaskService taskService =
                ((Task) activity.getImplementation()).getTaskService();
        if (taskService == null) {
            return toReturn;
        }
        String type =
                (String) Xpdl2ModelUtil.getOtherAttribute(taskService,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_ImplementationType());
        if (!"Database".equals(type)) { //$NON-NLS-1$
            return toReturn;
        }
        List<String> outProcessDataList = new ArrayList<String>();
        Message messageOut = taskService.getMessageOut();
        EList<DataMapping> dataMappings = messageOut.getDataMappings();
        if (dataMappings != null) {
            for (DataMapping dataMapping : dataMappings) {
                if (DirectionType.OUT_LITERAL == dataMapping.getDirection()) {
                    String processDataName = dataMapping.getActual().getText();
                    outProcessDataList.add(processDataName);
                    continue;
                }
                if (DirectionType.INOUT_LITERAL == dataMapping.getDirection()) {
                    String processDataName = dataMapping.getActual().getText();
                    outProcessDataList.add(processDataName);
                    continue;
                }
            }
        }
        Map<String, ProcessRelevantData> prdUsed =
                new HashMap<String, ProcessRelevantData>();
        List<ProcessRelevantData> allAvailableRelevantDataForActivity =
                ProcessInterfaceUtil
                        .getAllAvailableRelevantDataForActivity(activity);
        for (String dataName : outProcessDataList) {
            for (ProcessRelevantData processRelevantData : allAvailableRelevantDataForActivity) {
                if (processRelevantData.getName().equals(dataName)) {
                    prdUsed.put(dataName, processRelevantData);
                }
            }
        }
        return prdUsed;
    }

    /**
     * Returns reference to process data whose name matches the passed name
     * 
     * @param xpdlActivity
     * @param fieldName
     * @return
     */
    public static ProcessRelevantData getActivityInterfaceData(Activity xpdlActivity, String fieldName) {
    	Collection<ActivityInterfaceData> allData = ActivityInterfaceDataUtil.getActivityInterfaceData(xpdlActivity);
        for (ActivityInterfaceData aid : allData) {
            if (fieldName.equals(aid.getName())) {
                return aid.getData();
            }
        }
        return null;
    }

    /**
     * Returns the BOM class URI which is being referred from the passed process
     * data
     * 
     * @param prd
     * @return
     */
    private static URI getBOMClassURI(ProcessRelevantData prd) {
        DataType dataType = prd.getDataType();
        if (!(dataType instanceof ExternalReference)) {
            return null;
        }
        WorkingCopy workingCopyFor = WorkingCopyUtil.getWorkingCopyFor(prd);
        IProject xpdlProject =
                workingCopyFor.getEclipseResources().get(0).getProject();
        ExternalReference ref = (ExternalReference) dataType;
        String bomFileName = ref.getLocation();
        String bomClassId = ref.getXref();
        IFile bomResource =
                SpecialFolderUtil.resolveSpecialFolderRelativePath(xpdlProject,
                        BOMResourcesPlugin.BOM_SPECIAL_FOLDER_KIND,
                        bomFileName,
                        true);
        URI bomEntityURI =
                URI.createPlatformResourceURI(bomResource.getFullPath()
                        .toString(),
                        true).appendFragment(bomClassId);
        return bomEntityURI;
        
    }
    
    /**
     * Returns null if the process data does not refer to BOM. Returns empty
     * list if there are no attributes in the referenced BOM class
     */
    public static Map<String,Property> getBOMClassAttributesName(
            ProcessRelevantData prd) {
    	Map<String,Property> labelMap = null;
        URI bomClassURI = getBOMClassURI(prd);
        if (bomClassURI == null) {
            return labelMap;
        }
        labelMap = new HashMap<String,Property>();
        EObject eo =
                XpdResourcesPlugin.getDefault().getEditingDomain()
                        .getResourceSet().getEObject(bomClassURI, true);
        if (eo instanceof org.eclipse.uml2.uml.Class) {
            EList<Property> attributes =
                    ((org.eclipse.uml2.uml.Class) eo).getAllAttributes();
            for (Property property : attributes) {
                if (property instanceof NamedElement) {
                    String displayLabel =
                            PrimitivesUtil.getDisplayLabel(property);
                    labelMap.put(displayLabel, property);
                }
            }
        }
        return labelMap;
    }

	private static URI getClassUriForSubSegments(URI classURI, String[] segments) {
		for (int idx = 1; idx < segments.length; idx++) {
        	String propertyName = segments[idx];
        	Property prop = null;
            EObject eo = XpdResourcesPlugin.getDefault().getEditingDomain().getResourceSet().getEObject(classURI, true);
            if (eo instanceof org.eclipse.uml2.uml.Class) {
                for (Property property : ((org.eclipse.uml2.uml.Class) eo).getAllAttributes()) {
                    if (propertyName.equals(property.getName())) {
                        prop = property;
                        break;
                    }
                }
            }
            if (prop == null) {
            	//no such property by the name of segments[idx]
            } else if (prop.getType() instanceof Class) {
                classURI = EcoreUtil.getURI(prop.getType());
            } else if (BomTypesUtil.isPropertyXsdAnyType(prop)) { 
                classURI = URI.createURI("http://tibco.com/anyType"); //$NON-NLS-1$
            } else {
            	//the property is not a BOM class
            	return null;
            }
		}
        
        return classURI;
	}

    /**
     * Returns the BOM class URI which is being referred from the expression, such as
     * 'myClaim.witness.address'
     */
    public static URI getBOMClassURI(Activity xpdlActivity, String expression) {
        String[] segments = expression.split("\\."); //$NON-NLS-1$
        if (segments == null || segments.length == 0) {
        	//invalid segments
        	return null;
        }
        
        // Get the class
        ProcessRelevantData prd = getActivityInterfaceData(xpdlActivity, segments[0]);
        if (prd == null) {
        	return null;
        }
        
        URI classURI = getBOMClassURI(prd);
        if (classURI == null) {
        	//no BOM class found
        	return null;
        }
        
        return getClassUriForSubSegments(classURI, segments);
    }

    /**
     * Returns the UML property referenced by the given expression e.g. returns the
     * UML property for firstLine in class Address for
     * 'myClaim.witness.address.firstLine'
     * 
     * @param xpdlActivity
     * @param expression
     * @return
     */
    public static Property getProperty(Activity xpdlActivity, String expression) {
        int pos = expression.lastIndexOf("."); //$NON-NLS-1$
        if (pos < 0) {
        	return null;
        }
        
        String parentExpression = expression.substring(0, pos);
        String propertyName = expression.substring(pos+1);

        URI classURI = getBOMClassURI(xpdlActivity, parentExpression);
        if (classURI == null) {
        	//no BOM class found
        	return null;
        }
        
        // Load the class
        EObject eo = XpdResourcesPlugin.getDefault().getEditingDomain()
        	.getResourceSet().getEObject(classURI, true);
        if (eo instanceof org.eclipse.uml2.uml.Class) {
            for (Property property : ((org.eclipse.uml2.uml.Class) eo).getAllAttributes()) {
                if (propertyName.equals(property.getName())) {
                    return property;
                }
            }
        }

        return null;
    }

	public static URI getBOMClassURI(ConverterContext context, javax.wsdl.Message wsdlMessage, String expression) {
	    /*
	     * Sid ACE-194 - we don't support message events in ACE
	     */
	     throw new RuntimeException("Unexpected unsupported message activity in source process.");

//	    
//        String[] segments = expression.split("\\."); //$NON-NLS-1$
//        if (segments == null || segments.length == 0) {
//        	//invalid segments
//        	return null;
//        }
//        
//		String partName = segments[0];
//
//		Definition sourceWsdl = context.getSourceWsdl(wsdlMessage.getQName().getNamespaceURI());
//		Part part = (Part) sourceWsdl.getMessage(wsdlMessage.getQName()).getPart(partName);
//		if (part == null) {
//        	return null;
//		}
//
//		Class conceptClass = JavaScriptConceptUtil.INSTANCE.getConceptClass(part);
//        if (conceptClass == null) {
//        	return null;
//        }
//
//        Resource resource = conceptClass.eResource();
//        URI classURI = resource.getURI().appendFragment(resource.getURIFragment(conceptClass));
//
//        return getClassUriForSubSegments(classURI, segments);
    }
	
    /**
     * Return the BOM class that backs the given WSDL message part (identified
     * by name or first part of a part.x.x.x path expression
     * 
     * @param context
     * @param wsdlMessage
     * @param partOrPartDotExpression
     *            Part name or part.x.x.x path type expression
     * 
     * @return BOM class or <code>null</code> if part is not a complex type
     *         backed by a bom class.
     */
    public static Class getBOMClassForPartOrPath(ConverterContext context,
            javax.wsdl.Message wsdlMessage, String partOrPartDotExpression) {
        URI bomClassURI =
                getBOMClassURI(context, wsdlMessage, partOrPartDotExpression);

        if (bomClassURI != null) {
            EObject eo =
                    XpdResourcesPlugin.getDefault().getEditingDomain()
                            .getResourceSet().getEObject(bomClassURI, true);
            if (eo instanceof org.eclipse.uml2.uml.Class) {
                return (Class) eo;
            }
        }

        return null;
    }

	public static Property getProperty(ConverterContext context, javax.wsdl.Message wsdlMessage, String expression) {
        int pos = expression.lastIndexOf("."); //$NON-NLS-1$
        if (pos < 0) {
        	return null;
        }
        
        String parentExpression = expression.substring(0, pos);
        String propertyName = expression.substring(pos+1);

        URI classURI = getBOMClassURI(context, wsdlMessage, parentExpression);
        if (classURI == null) {
        	//no BOM class found
        	return null;
        }
        
        // Load the class
        EObject eo = XpdResourcesPlugin.getDefault().getEditingDomain()
        	.getResourceSet().getEObject(classURI, true);
        if (eo instanceof org.eclipse.uml2.uml.Class) {
            for (Property property : ((org.eclipse.uml2.uml.Class) eo).getAllAttributes()) {
                if (propertyName.equals(property.getName())) {
                    return property;
                }
            }
        }

        return null;
	}

    /**
     * Query the CDS indexer with the given criteria.
     * 
     * @param query
     * @return
     */
    private static IndexerItem query(IndexerItemImpl query) {
        Collection<IndexerItem> res =
                XpdResourcesPlugin.getDefault().getIndexerService().query(INDEXER_ID, query);
        return !res.isEmpty() ? res.iterator().next() : null;
    }

    /**
     * This returns the create statement for the given class URI. For example:
     * "com_example_uc1ipojo_Factory.createClaim()"
     * 
     * @param classURI
     * @return
     */
    private static String getCDSClassCreateStatement(URI classURI) {
        IndexerItem indexerItem = query(new IndexerItemImpl(null, "Class", classURI.toString(), null));
        if (indexerItem == null) {
            return null;
        }
        String className = indexerItem.getName();
        String createMethodName =
                indexerItem.get("qualifiedCDSFactoryName") + ".create" + className + "()"; //$NON-NLS-1$ //$NON-NLS-2$
        return createMethodName;
    }

    /**
     * Get the statements to create the instances of the classes
     * which are being referred from the passed expression.
     * For example, given the expression <code>myClaim.witness.address.firstLine</code>, the return string is:
     * if (myClaim == null) myClaim = com_example_uc1ipojo_Factory.createClaim();
     * if (myClaim.witness == null) myClaim.witness = com_example_uc1ipojo_witnesspackage_Factory.createWitness();
     * if (myClaim.witness.address == null) myClaim.witness.address = com_example_uc1ipojo_detailspackage_Factory.createAddress();
     * 
     * @param xpdlActivity
     * @param expr
     */
    public static String getCDSClassCreateStatementsForExpression(Activity xpdlActivity, String expr) {
    	String[] segments = expr.split("\\."); //$NON-NLS-1$
    	if (segments == null || segments.length < 2) {
    		return null;
    	}
    	
    	ProcessRelevantData prd = getActivityInterfaceData(xpdlActivity, segments[0]);
        URI classURI = getBOMClassURI(prd);
        return getCDSClassCreateStatementsForSegments(classURI, segments);
    }
    
    public static String getCDSClassCreateStatementsForExpression(ConverterContext context, javax.wsdl.Message wsdlMessage, String expr) {
    	String[] segments = expr.split("\\."); //$NON-NLS-1$
    	if (segments == null || segments.length < 2) {
    		return null;
    	}
    	
		URI classURI = getBOMClassURI(context, wsdlMessage, segments[0]);
		/*
         * XPD-8327: Sanitize message part name to ensure that the generated JavaScript is valid.
         */
		segments[0] = N2PEConstants.MESSAGE_PREFIX + NameUtil.sanitizeMessagePartVariableName(segments[0]);
        return getCDSClassCreateStatementsForSegments(classURI, segments);
    }
    
    private static String getCDSClassCreateStatementsForSegments(URI classURI, String[] segments) {
        String segment = segments[0];
        String varName = segment;

    	StringBuffer script = new StringBuffer();

        int idx = 1;
        while (classURI != null) {
    		script.append("if (").append(varName).append(" == null) "); //$NON-NLS-1$ //$NON-NLS-2$
            script.append(varName).append(" = ").append(getCDSClassCreateStatement(classURI)).append(";\n"); //$NON-NLS-1$ //$NON-NLS-2$

            if (idx == segments.length-1) {
            	break;
            }
            
            segment = segments[idx++];
            varName += "." + segment; //$NON-NLS-1$

            /**
             * BX-2868:
             * Look for property in actual bom class rather than looking for it in the index using the parent class URI.
             * 
             * The parent class may not actually OWN the property if the property is contained in a super class that the parent class is
             * generalizing (and hence won't be indexed under the parent class's URI.
             * 
             * Also, looking in the BOM class model itself is much more efficient than indexer query per segment per mapping.
             */
            Class bomClass = (Class) XpdResourcesPlugin.getDefault().getEditingDomain().getResourceSet().getEObject(classURI, true);
            Property prop = null;
            for (Property iterProp : bomClass.getAllAttributes()) {
                if (iterProp.getName().equals(segment)) {
                    prop = iterProp;
                    break;
                }
            }

            if (prop != null && prop.getType() instanceof Class) {
                bomClass = (Class) prop.getType();
                classURI = EcoreUtil.getURI(bomClass);
            } else {
                break;
            }
        }
        
        return script.toString();
    }

    public static String getCDSClassCreateStatementsForPart(ConverterContext context, javax.wsdl.Message wsdlMessage, String partName) {
		URI classURI = getBOMClassURI(context, wsdlMessage, partName);
    	StringBuffer script = new StringBuffer();
        script.append(getCDSClassCreateStatement(classURI)).append(";"); //$NON-NLS-1$
        return script.toString();
    }

    public static org.eclipse.bpel.model.To createToCDSScript(Activity xpdlActivity, String expression) {
        /*
         * BX-3762: Saket: OutputFromSerfvice was not recognising that target
         * data is multi-instance and therefore it was not treating them as
         * lists. (e.g. Manager.address = fromReturn;). It should've been
         * generating the following instead... Manager.address.clear();
         * Manager.address.addAll(fromReturn);
         */
        String enumTypeName = null;
        boolean isArray = false;

        StringBuffer script = new StringBuffer();

        int pos = expression.indexOf("."); //$NON-NLS-1$

        if (pos > 0) {
            Property property = CDSUtils.getProperty(xpdlActivity, expression);
            boolean mapToEnumField =
                    property != null
                            && property.getType() instanceof Enumeration;
            if (mapToEnumField) {
                enumTypeName = property.getType().getQualifiedName();
                enumTypeName =
                        enumTypeName.replace(".", "_").replace("::", "_"); //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
            }
            isArray =
                    property != null
                            && (property.getUpper() > 1 || property.getUpper() == -1);
            
            /*
             * Sid XPD-194 - we don't support XSD based BOMs in ACE
             */

            String createStmts =
                    CDSUtils.getCDSClassCreateStatementsForExpression(xpdlActivity,
                            expression);
            if (createStmts != null) {
                script.append(createStmts);
            }
        }

        if (isArray) {
            script.append(expression).append(".clear();\n"); //$NON-NLS-1$
            if (enumTypeName == null) {
                script.append(expression).append(".addAll(fromReturn);"); //$NON-NLS-1$
            } else {
                script.append("for (var i = 0; i < fromReturn.size(); i++) {\n"); //$NON-NLS-1$
                script.append("var val = fromReturn.get(i);\n"); //$NON-NLS-1$
                script.append(expression)
                        .append(".add(").append(enumTypeName).append(".get(val));\n"); //$NON-NLS-1$ //$NON-NLS-2$
                script.append("}\n"); //$NON-NLS-1$
            }
        }  else if (enumTypeName != null) {
            /*
             * script.append("if (fromReturn != null && ").append(enumTypeName).
             * append(".get(fromReturn) == null) {\n"); script.append(
             * "throw new java.lang.IllegalArgumentException(\"Invalid " +
             * enumTypeName + " value \" + fromReturn);\n");
             * script.append("}\n");
             */
            script.append(expression)
                    .append(" = ").append(enumTypeName).append(".get(fromReturn);"); //$NON-NLS-1$ //$NON-NLS-2$
        } else {
            script.append(expression).append(" = fromReturn;"); //$NON-NLS-1$
        }

        org.eclipse.bpel.model.Expression expr =
                BPELFactory.eINSTANCE.createExpression();
        expr.setExpressionLanguage(N2PEConstants.JSCRIPT_LANGUAGE);
        expr.setBody(script.toString());

        org.eclipse.bpel.model.To to = BPELFactory.eINSTANCE.createTo();
        to.setExpression(expr);
        return to;
	}

	public static org.eclipse.bpel.model.To createToExpressionWithCDS(Activity xpdlActivity, String expression, boolean mappingFromSingleToArray) {
		StringBuffer script = new StringBuffer();
    	
		String createStmts = getCDSClassCreateStatementsForExpression(xpdlActivity, expression);
		if (createStmts != null) {
			script.append(createStmts);
		}
		
    	if (mappingFromSingleToArray) {
    		script.append("var loopIdx = Process.getActivityLoopIndex();\n"); //$NON-NLS-1$
    		script.append("if (loopIdx < ").append(expression).append(".size()) {\n");
        	script.append(expression).append(".set(loopIdx, fromReturn);\n"); //$NON-NLS-1$
    		script.append("} else {\n");
    		script.append("for (i = ").append(expression).append(".size(); i < loopIdx; i++) {\n"); //$NON-NLS-1$ //$NON-NLS-2$
    		script.append(expression).append(".add(i, null);\n"); //$NON-NLS-1$
    		script.append("}\n"); //$NON-NLS-1$
            script.append(expression).append(".add(loopIdx, fromReturn);\n"); //$NON-NLS-1$
            script.append("}\n"); //$NON-NLS-1$
    	} else {
    		Property property = getProperty(xpdlActivity, expression);
    		boolean isArray = property != null && (property.getUpper() > 1 || property.getUpper() == -1);
            /*
             * Sid ACE-194 - we don't support XSD based BOMs in ACE
             */

    		boolean mapToEnumField = property != null && property.getType() instanceof Enumeration; 
    		String enumTypeName = mapToEnumField ? property.getType().getName() : null; 
    		if (isArray) {
            	script.append(expression).append(".clear();\n"); //$NON-NLS-1$
            	if (enumTypeName == null) {
            		script.append(expression).append(".addAll(fromReturn);"); //$NON-NLS-1$
            	} else {
            		script.append("for (var i = 0; i < fromReturn.size(); i++) {\n"); //$NON-NLS-1$
            		script.append("var val = fromReturn.get(i);\n"); //$NON-NLS-1$
            		script.append(expression).append(".add(").append(enumTypeName).append(".get(val));\n"); //$NON-NLS-1$
            		script.append("}\n"); //$NON-NLS-1$
            	}
    		} else {
	        	if (enumTypeName != null) {
	        		script.append(expression).append(" = ").append(enumTypeName).append(".get(fromReturn);"); //$NON-NLS-1$ //$NON-NLS-2$
	        	} else {
	        		script.append(expression).append(" = fromReturn;"); //$NON-NLS-1$
	        	}
	    	}
    	}
		
		org.eclipse.bpel.model.Expression expr = BPELFactory.eINSTANCE.createExpression();
    	expr.setExpressionLanguage(N2PEConstants.JSCRIPT_LANGUAGE);
    	expr.setBody(script.toString());
    	
    	org.eclipse.bpel.model.To to = BPELFactory.eINSTANCE.createTo();
		to.setExpression(expr);
    	return to;
	}

}
