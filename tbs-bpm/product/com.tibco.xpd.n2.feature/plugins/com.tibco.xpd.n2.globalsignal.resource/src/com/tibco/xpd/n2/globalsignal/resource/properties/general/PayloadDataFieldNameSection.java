/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.xpd.n2.globalsignal.resource.properties.general;

import java.util.Iterator;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.layout.GridData;

import com.tibco.xpd.globalSignalDefinition.GlobalSignal;
import com.tibco.xpd.globalSignalDefinition.GlobalSignalDefinitions;
import com.tibco.xpd.globalSignalDefinition.PayloadDataField;
import com.tibco.xpd.globalSignalDefinition.impl.PayloadDataFieldImpl;
import com.tibco.xpd.n2.globalsignal.resource.internal.Messages;
import com.tibco.xpd.processeditor.xpdl2.properties.NamedElementSection;
import com.tibco.xpd.xpdl2.DataField;
import com.tibco.xpd.xpdl2.NamedElement;
import com.tibco.xpd.xpdl2.edit.ui.Xpdl2UiPlugin;

/**
 * {@link NamedElementSection} for the {@link PayloadDataField}, as it is not a
 * direct type of {@link DataField} or {@link NamedElement}, the implementation
 * is adapted to use the {@link PayloadDataField} for that purpose.
 * 
 * @author aprasad
 * 
 */
public class PayloadDataFieldNameSection extends NamedElementSection {

    @Override
    protected String getDuplicateNameMessage(EObject duplicate) {

        String errorStr = null;

        if (duplicate instanceof PayloadDataField) {

            errorStr =
                    Messages.PayloadDataFieldNameSection_PayloadDataWithSameName_ErrorMsg;
        }
        return errorStr;
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.NamedElementSection#requiresTokenName(org.eclipse.emf.ecore.EObject)
     * 
     * @param input
     * @return
     */
    @Override
    protected boolean requiresTokenName(EObject input) {

        if (input instanceof PayloadDataField) {

            return true;
        }

        return super.requiresTokenName(input);
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.processeditor.xpdl2.properties.NamedElementSection#
     * getDuplicateDisplayNameMesage(org.eclipse.emf.ecore.EObject)
     */
    @Override
    protected String getDuplicateDisplayNameMesage(EObject duplicate) {

        String errorStr = null;

        if (duplicate instanceof PayloadDataField) {

            errorStr =
                    Messages.PayloadDataFieldNameSection_PayloadDataWithSameLabel_ErrorMsg;
        }

        return errorStr;
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.NamedElementSection#verifyName(java.lang.String)
     * 
     * @param nameText
     */
    @Override
    protected void verifyName(String nameText) {

        super.verifyName(nameText);

        EObject duplicate = null;

        if (getInputContainer() instanceof GlobalSignal) {

            /*
             * Input container is global signal.
             */

            duplicate =
                    getDuplicateNameObject((GlobalSignal) getInputContainer(),
                            getInput(),
                            nameText);

            if (duplicate != null) {

                nameValid = false;
                err = getDuplicateNameMessage(duplicate);
            }

        } else if (getInputContainer() instanceof GlobalSignalDefinitions) {

            /*
             * Input container is global signal definitions.
             */

            if (getSelection() instanceof GlobalSignal)

                duplicate =
                        getDuplicateNameObject((GlobalSignal) getSelection(),
                                getInput(),
                                nameText);

            if (duplicate != null) {

                nameValid = false;
                err = getDuplicateNameMessage(duplicate);
            }
        }

        /*
         * Perform necessary actions if name is invalid.
         */
        if (!nameValid) {

            if (nameLabel.getImage() == null) {

                /*
                 * Need to reset the layout data.
                 */
                nameLabel.setLayoutData(new GridData(
                        GridData.VERTICAL_ALIGN_CENTER));

            }

            nameLabel.setImage(Xpdl2UiPlugin.getDefault().getImageRegistry()
                    .get(Xpdl2UiPlugin.IMG_ERROR));

            nameLabel.setToolTipText(err);

        } else {

            if (nameLabel.getImage() != null) {

                /*
                 * Need to reset the layout data.
                 */

                nameLabel.setLayoutData(new GridData(
                        GridData.VERTICAL_ALIGN_CENTER));
            }

            nameLabel.setImage(null);
            nameLabel.setToolTipText(""); //$NON-NLS-1$
        }
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.NamedElementSection#allowLeadingNumerics()
     * 
     * @return
     */
    @Override
    protected boolean allowLeadingNumerics() {

        /*
         * Don't allow leading numerics in PDF name.
         */
        return false;

    }

    /**
     * @param globalSignal
     * @param input
     * @param nameText
     * @return
     */
    private EObject getDuplicateNameObject(GlobalSignal globalSignal,
            EObject input, String nameText) {

        if (globalSignal != null) {

            /*
             * Search for duplicate.
             */

            for (Iterator iter = globalSignal.getPayloadDataFields().iterator(); iter
                    .hasNext();) {

                PayloadDataField eachPdf = (PayloadDataField) iter.next();
                DataField df = (PayloadDataField) input;

                if (eachPdf != df) {

                    if (nameText.equals(eachPdf.getName())) {

                        return eachPdf;
                    }
                }
            }
        }

        return null;

    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractFilteredTransactionalSection#select(java.lang.Object)
     * 
     * @param toTest
     * @return
     */
    @Override
    public boolean select(Object toTest) {

        if (toTest instanceof PayloadDataField) {

            if (toTest.getClass() == PayloadDataFieldImpl.class) {

                return true;
            }
        }

        return false;
    }
}
