/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.implementer.script;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.wst.wsdl.Part;
import org.eclipse.wst.wsdl.WSDLFactory;
import org.eclipse.xsd.XSDElementDeclaration;
import org.eclipse.xsd.XSDFactory;
import org.eclipse.xsd.XSDSchemaContent;
import org.eclipse.xsd.XSDTypeDefinition;
import org.eclipse.xsd.ecore.MapBuilder.Mapper;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.bom.xsdtransform.xsdindexing.XsdUIUtil;
import com.tibco.xpd.resources.indexer.IndexerItem;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.ui.util.NameUtil;
import com.tibco.xpd.wsdlgen.transform.template.BasicTypesHelper;
import com.tibco.xpd.xpdExtension.InitialValues;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdExtension.SoapBindingStyle;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.BasicType;
import com.tibco.xpd.xpdl2.DataType;
import com.tibco.xpd.xpdl2.ExternalReference;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.NamedElement;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Utility class the provides the {@link Part} reference to the {@link Mapper}.
 * 
 * @author rsomayaj
 * 
 */
public class PartProxy {

    private static Map<FormalParameter, Part> partsMap =
            new HashMap<FormalParameter, Part>();

    /**
     * Caches the {@link Part} reference with the same name as the
     * {@link FormalParameter} name, so that the {@link Mapper} resolves the
     * figures it needs to draw.
     * 
     * @param partName
     * @return
     */
    public static Part getPart(FormalParameter formalParam) {
        String partName =
                NameUtil.getInternalName(formalParam.getName(), false);
        Part part = null;
        Process process = Xpdl2ModelUtil.getProcess(formalParam);
        boolean isDocLiteralBinding = true;

        if (process != null) {
            isDocLiteralBinding =
                    SoapBindingStyle.DOCUMENT_LITERAL.equals(Xpdl2ModelUtil
                            .getWsdlBindingStyle(process));
        } else {
            ProcessInterface processInterface =
                    ProcessInterfaceUtil.getProcessInterface(formalParam);
            if (processInterface != null) {
                isDocLiteralBinding =
                        SoapBindingStyle.DOCUMENT_LITERAL.equals(Xpdl2ModelUtil
                                .getWsdlBindingStyle(processInterface));
            }
        }
        if (partsMap.containsKey(formalParam)) {
            part = partsMap.get(formalParam);
            part.setName(formalParam.getName());
            XSDSchemaContent schemaContent =
                    getReferencedSchemaContentForPart(formalParam,
                            isDocLiteralBinding);
            if (isDocLiteralBinding
                    && schemaContent instanceof XSDElementDeclaration) {
                part.setElementDeclaration((XSDElementDeclaration) schemaContent);
            } else if (schemaContent instanceof XSDTypeDefinition) {
                part.setTypeDefinition((XSDTypeDefinition) schemaContent);
            }
        } else {
            part = WSDLFactory.eINSTANCE.createPart();
            part.setName(partName);
            XSDSchemaContent typeDefinitionForPart =
                    getReferencedSchemaContentForPart(formalParam,
                            isDocLiteralBinding);
            if (isDocLiteralBinding
                    && typeDefinitionForPart instanceof XSDElementDeclaration) {
                part.setElementDeclaration((XSDElementDeclaration) typeDefinitionForPart);
            } else if (typeDefinitionForPart instanceof XSDTypeDefinition) {
                part.setTypeDefinition((XSDTypeDefinition) typeDefinitionForPart);
            }
            partsMap.put(formalParam, part);
        }
        return part;
    }

    /**
     * @param formalParam
     * @param isDocLiteralBinding
     */
    private static XSDSchemaContent getReferencedSchemaContentForPart(
            FormalParameter formalParam, boolean isDocLiteralBinding) {
        DataType dataType = formalParam.getDataType();
        XSDTypeDefinition typeDefinition = null;
        XSDElementDeclaration elementDeclaration = null;

        String paramContainerName = ""; //$NON-NLS-1$

        if (formalParam.eContainer() instanceof NamedElement) {
            paramContainerName =
                    ((NamedElement) formalParam.eContainer()).getName();
        }

        if (dataType instanceof BasicType) {

            InitialValues initialValues = null;
            Object otherElement =
                    Xpdl2ModelUtil.getOtherElement(formalParam,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_InitialValues());
            if (otherElement instanceof InitialValues) {
                initialValues = (InitialValues) otherElement;
            }
            if (isDocLiteralBinding) {
                // TODO:XSDElementDeclaration
            } else {
                typeDefinition =
                        BasicTypesHelper.createNewTypeDefinition(null,
                                paramContainerName,
                                formalParam.getName(),
                                (BasicType) dataType,
                                null,
                                formalParam.isIsArray(),
                                initialValues);
            }
        } else if (dataType instanceof ExternalReference) {
            ExternalReference externalReference = (ExternalReference) dataType;
            String xref = externalReference.getXref();
            Package xpdlPackage = Xpdl2ModelUtil.getPackage(formalParam);
            IFile xpdlFile = null;
            IProject project = null;
            if (xpdlPackage != null) {
                xpdlFile = WorkingCopyUtil.getFile(xpdlPackage);
                if (xpdlFile != null) {
                    project = xpdlFile.getProject();
                }
            }

            IndexerItem indexedItem =
                    XsdUIUtil.queryXsdElement(xref,
                            project,
                            isDocLiteralBinding);
            if (indexedItem != null) {
                if (isDocLiteralBinding) {
                    // TODO:XSDElementDeclaration
                } else {
                    typeDefinition =
                            XSDFactory.eINSTANCE
                                    .createXSDComplexTypeDefinition();
                    typeDefinition.setName(indexedItem.getName());
                }
            }
        }

        XSDSchemaContent schemaContent = null;
        if (typeDefinition != null) {
            schemaContent = typeDefinition;
        } else {
            schemaContent = typeDefinition;
        }

        return schemaContent;
    }

    public static void clearParts() {
        partsMap.clear();
    }
}
