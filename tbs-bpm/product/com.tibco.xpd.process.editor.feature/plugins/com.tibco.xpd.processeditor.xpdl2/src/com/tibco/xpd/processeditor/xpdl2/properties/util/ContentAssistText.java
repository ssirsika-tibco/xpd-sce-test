/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */
package com.tibco.xpd.processeditor.xpdl2.properties.util;

import org.eclipse.jface.fieldassist.ControlDecoration;
import org.eclipse.jface.fieldassist.IContentProposal;
import org.eclipse.jface.fieldassist.IContentProposalProvider;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.services.IDisposable;

import com.tibco.xpd.processeditor.xpdl2.properties.util.FixedValueFieldAssistHelper.FixedValueFieldChangedListener;
import com.tibco.xpd.processeditor.xpdl2.properties.util.FixedValueFieldAssistHelper.PositionalFieldProposalProvider;
import com.tibco.xpd.ui.properties.XpdFormToolkit;

/**
 * Wrapper class around a {@link ControlDecoration} for the content assist text
 * control.
 * 
 * @author njpatel
 */
public class ContentAssistText implements IDisposable {

    private FixedValueFieldAssistHelper helper;

    /**
     * Wrapper class around a {@link ControlDecoration} for the content assist
     * text control.
     * 
     * @param parent
     *            parent control
     * @param toolkit
     *            form toolkit
     * @param proposalProvider
     *            proposal provider for this content assist control
     * 
     */
    public ContentAssistText(Composite parent, XpdFormToolkit toolkit,
            final IContentProposalProvider proposalProvider) {

        PositionalFieldProposalProvider fieldProposalProvider =
                new PositionalFieldProposalProvider() {
                    @Override
                    public Object[] getProposals(String contents, int position) {
                        Object[] proposals = null;
                        if (position >= 0) {
                            IContentProposal[] contentProposals =
                                    proposalProvider.getProposals(contents,
                                            position);

                            if (contentProposals != null
                                    && contentProposals.length > 0) {
                                proposals = new Object[contentProposals.length];
                                for (int i = 0; i < contentProposals.length; i++) {
                                    proposals[i] =
                                            contentProposals[i].getContent();
                                }
                            } else if (contents.length() > 0) {
                                // If last character change produced no
                                // proposals,
                                // go back to the previous character.
                                proposals =
                                        getProposals(contents.substring(0,
                                                contents.length() - 1),
                                                position - 1);

                            }
                        }
                        return proposals;
                    }
                };
        helper =
                new FixedValueFieldAssistHelper(toolkit, parent,
                        fieldProposalProvider, true);
        helper.getDecoratedField().getLayoutControl()
                .setBackground(parent.getBackground());
    }

    /**
     * Set the layout data of this control.
     * 
     * @param layoutData
     */
    public void setLayoutData(Object layoutData) {
        Control txt = helper.getDecoratedField().getLayoutControl();
        if (txt != null && !txt.isDisposed()) {
            txt.setLayoutData(layoutData);
        }
    }

    /**
     * Get the text control.
     * 
     * @return text control
     */
    public Text getText() {
        return (Text) helper.getDecoratedField().getControl();
    }

    /**
     * Gets the decorator fields layout control
     * 
     * @return the decorator fields layout control
     */
    public Control getLayoutControl() {
        return helper.getDecoratedField().getLayoutControl();
    }

    /**
     * @see org.eclipse.ui.services.IDisposable#dispose()
     * 
     */
    @Override
    public void dispose() {
        /*
         * XPD-7648: Initially we just disposed the Text control, however the
         * text control has a 'FieldDecoration' (light bulb) on its left and
         * hence we need to dispose that as well(else on dispose the ligh bulb
         * will still keep glowing ;) ).
         */
        Control layoutControl = getLayoutControl();

        if (layoutControl != null) {
            layoutControl.dispose();
        }
    }

    public void addValueChangeListener(FixedValueFieldChangedListener listener) {
        helper.addValueChangedListener(listener);
    }
}