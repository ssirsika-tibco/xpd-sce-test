/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.properties.dataFields;

import java.util.Set;

import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.widgets.Composite;

import com.tibco.xpd.processeditor.xpdl2.properties.messages.Messages;
import com.tibco.xpd.resources.ui.components.XpdToolkit;
import com.tibco.xpd.resources.ui.components.actions.TableAddAction;
import com.tibco.xpd.resources.ui.components.actions.ViewerAddAction;
import com.tibco.xpd.ui.util.NameUtil;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.BasicType;
import com.tibco.xpd.xpdl2.BasicTypeType;
import com.tibco.xpd.xpdl2.DataField;
import com.tibco.xpd.xpdl2.Length;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * CorrelationDataTable
 *
 *
 * @author bharge
 * @since 3.3 (22 Oct 2009)
 */
public class CorrelationDataTable extends DataFieldTable {
    private final EditingDomain editingDomain;
    
    /**
     * @param parent
     * @param toolkit
     * @param editingDomain
     */
    public CorrelationDataTable(Composite parent, XpdToolkit toolkit,
            EditingDomain editingDomain) {
        super(parent, toolkit, editingDomain);
        this.editingDomain = editingDomain;
        ViewerFilter filter = new DataFieldFilter(true);
        ViewerFilter[] filters = new ViewerFilter[] { filter };
        getViewer().setFilters(filters);
    }

    /**
     * Get the input of this table.
     * 
     * @return
     */
    private EObject getInput() {
        return (EObject) (getViewer() != null ? getViewer().getInput() : null);
    }
    
    /* (non-Javadoc)
     * @see com.tibco.xpd.processeditor.xpdl2.properties.dataFields.DataFieldTable#getMovableFeatures()
     */
    @Override
    protected Set<EStructuralFeature> getMovableFeatures() {
        Set<EStructuralFeature> features = super.getMovableFeatures();
        features.add(Xpdl2Package.eINSTANCE.getDataFieldsContainer_DataFields());
        return features;
    }
    
    /* (non-Javadoc)
     * @see com.tibco.xpd.processeditor.xpdl2.properties.dataFields.DataFieldTable#createAddAction(org.eclipse.jface.viewers.ColumnViewer)
     */
    @Override
    protected ViewerAddAction createAddAction(ColumnViewer viewer) {
        return new TableAddAction(viewer, Messages.PropertiesSection_AddLabel,
                Messages.DataFieldsSection_AddDataFieldButton_tooltip) {  

            @Override
            protected Object addRow(StructuredViewer viewer) {
                Object input = getInput();
                if (null != input) {
                    String firstCellVal = getNewRowFirstCellVal();
                    DataField dataField = createFileTemplate(firstCellVal);
                    dataField.setName(NameUtil.getInternalName(firstCellVal, true));
                    Xpdl2ModelUtil
                            .setOtherAttribute(dataField, XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_DisplayName(), firstCellVal);
                    CompoundCommand cmd =
                            new CompoundCommand(
                                    Messages.DataFieldsSection_createDataField_menu);
                    cmd.append(AddCommand.create(editingDomain,
                            input,
                            Xpdl2Package.eINSTANCE.getDataField(),
                            dataField));
                    if (cmd.canExecute()) {
                        editingDomain.getCommandStack().execute(cmd);
                    }
                }
                return null;
            }
            
            protected String getNewRowFirstCellVal() {
                String propName =
                        Messages.CorrelationDataFolderSection_NewCorrelationFieldName;
                DataField dataField = createFileTemplate(propName);
                String uniqueDataFieldName =
                        getUniqueDataFieldName(propName, dataField);
                if (uniqueDataFieldName != null
                        && !uniqueDataFieldName.equals(propName)) {
                    propName = uniqueDataFieldName;
                }
                return propName;
            }
            
            private DataField createFileTemplate(String dataFieldName) {
                Xpdl2Factory fact = Xpdl2Factory.eINSTANCE;
                DataField input = fact.createDataField();
                input.setName(NameUtil.getInternalName(dataFieldName, true));
                Xpdl2ModelUtil
                        .setOtherAttribute(input, XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_DisplayName(), dataFieldName);
                // Set basic string type
                BasicType basicType = fact.createBasicType();
                basicType.setType(BasicTypeType.STRING_LITERAL);
                Length len = Xpdl2Factory.eINSTANCE.createLength();
                len.setValue("50"); //$NON-NLS-1$
                basicType.setLength(len);
                input.setReadOnly(true);

                input.setDataType(basicType);
                input.setCorrelation(true);

                return input;
            }

            private String getUniqueDataFieldName(String baseName,
                    DataField dataField) {
                String finalName = baseName;
                int idx = 1;
                while (Xpdl2ModelUtil.getDuplicateDisplayFieldOrParam(getInput(),
                        dataField,
                        finalName) != null
                        || Xpdl2ModelUtil.getDuplicateFieldOrParam(getInput(),
                                dataField,
                                NameUtil.getInternalName(finalName, true)) != null) {
                    idx++;
                    finalName = baseName + idx;
                }
                return finalName;
            }
            
        };
    }
}
