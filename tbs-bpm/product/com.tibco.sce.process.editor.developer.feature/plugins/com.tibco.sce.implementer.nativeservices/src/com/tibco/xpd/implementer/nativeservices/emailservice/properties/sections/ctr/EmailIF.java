/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.implementer.nativeservices.emailservice.properties.sections.ctr;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.views.properties.tabbed.AbstractPropertySection;

import com.tibco.xpd.analyst.resources.xpdl2.pickers.DataFilterPicker;
import com.tibco.xpd.analyst.resources.xpdl2.pickers.DataPicker;
import com.tibco.xpd.implementer.nativeservices.NativeServicesConsts;
import com.tibco.xpd.implementer.nativeservices.internal.Messages;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.xpdl2.ProcessRelevantData;

/**
 * Abstract class that is used by controls that will be used in the Email
 * property sheet. The subclass controls will define a label, a text control and
 * a browse button to pick a data field up.
 * 
 * @author njpatel
 * 
 */
public abstract class EmailIF {

    /**
     * Interface that will be implemented by the provider of the Data Picker
     */
    public interface IDataPP {
        public DataFilterPicker getDataPicker(boolean multi);
    }

    protected static final String BROWSE_LABEL = "..."; //$NON-NLS-1$
    
    protected static final String CLEAR_LABEL = "Clear"; //$NON-NLS-1$

    // This can be updated by subclasses
    protected String browseToolTipText = Messages.EmailInputField_BrowseDataFieldToolTip;

    protected String clearToolTipText = Messages.EmailInputField_ClearDataFieldToolTip;
    /*
     * Field delimeter used for process fields - this character will be used at
     * the beginning and end of a field, ie. %FIELD%
     */
    protected static final String PROCESS_FIELD_DELIM = NativeServicesConsts.PROCESS_FIELD_DELIM;

    private final XpdFormToolkit toolkit;

    private static Shell shell;

    // As the subclass controls are used in a property sheet use the property
    // sheet standard label width
    private static int labelWidthHint = AbstractPropertySection.STANDARD_LABEL_WIDTH;

    private static int textWidthHint = 150;

    private final IDataPP pickerProvider;

    private final EStructuralFeature attribute;

    /**
     * Create the Controls - text/combo and button in the parent composite. It
     * will be assumed that the parent uses <code>GridLayout</code>.
     * 
     * @param shell
     * @param toolkit
     */
    protected EmailIF(XpdFormToolkit toolkit, Composite parent,
            String label, EStructuralFeature eFeature,
            IDataPP pickerProvider) {
        this.toolkit = toolkit;
        attribute = eFeature;
        this.pickerProvider = pickerProvider;
        EmailIF.shell = shell;

        doInit();

        createControls(parent, label);
    }

    /**
     * Get the standard label width hint
     * 
     * @return
     */
    public int getLabelWidthHint() {
        return labelWidthHint;
    }

    /**
     * Get the standard text width hint
     * 
     * @return
     */
    public int getTextWidthHint() {
        return textWidthHint;
    }

    /**
     * Get the feature in the model this control is responsible for
     * 
     * @return
     */
    public EStructuralFeature getEFeature() {
        return attribute;
    }

    /**
     * Create the Controls - text/combo and button in the parent composite. It
     * will be assumed that the parent uses <code>GridLayout</code>.
     * 
     * @param parent
     */
    public abstract void createControls(Composite parent, String label);

    /**
     * Get the text from the control
     * 
     * @return Text set in the control
     */
    public abstract String getText();

    /**
     * Set the text in this control
     * 
     * @param text
     */
    public abstract void setText(String text);

    /**
     * Enable/Disable the controls
     * 
     * @param enabled
     */
    public abstract void setEnabled(boolean enabled);

    /**
     * Subclass can extend this to run any initialisation code before the
     * controls are created
     */
    protected void doInit() {
        // Do nothing. Subclasses can extend this to run any initialisation code
        // before the controls are created.
    }

    /**
     * Get the toolkit
     * 
     * @return
     */
    protected XpdFormToolkit getToolkit() {
        return toolkit;
    }

    /**
     * Get data fields from the data picker (multiple selection)
     * 
     * @return Array of the selected <code>ProcessRelevantData</code> objects.
     *         If Cancel was pressed in the picker then <b>null</b> will be
     *         returned.
     */
    protected ProcessRelevantData[] getDataFields() {
        List<ProcessRelevantData> fieldsList = null;

        if (pickerProvider != null) {
            DataFilterPicker picker = pickerProvider.getDataPicker(true);
            
            if (picker != null && picker.open() == DataPicker.OK) {
                Object[] fields = picker.getResult();

                if (fields != null && fields.length > 0) {
                    fieldsList = new ArrayList<ProcessRelevantData>();

                    for (Object field : fields) {
                        if (field instanceof ProcessRelevantData) {
                            fieldsList.add((ProcessRelevantData) field);
                        }
                    }
                }
            }
        }

        return fieldsList != null ? fieldsList
                .toArray(new ProcessRelevantData[fieldsList.size()]) : null;
    }

    /**
     * Get a data field from the data picker (single selection)
     * 
     * @return the selected <code>ProcessRelevantData </code>object. If Cancel
     *         was pressed in the picker then <b>null</b> will be returned.
     */
    protected ProcessRelevantData getDataField() {
        ProcessRelevantData field = null;

        if (pickerProvider != null) {
            DataFilterPicker picker = pickerProvider.getDataPicker(false);

            if (picker != null) {
                if (picker.open() == DataPicker.OK)
                    field = (ProcessRelevantData) picker.getFirstResult();
            }
        }

        return field;
    }
}
