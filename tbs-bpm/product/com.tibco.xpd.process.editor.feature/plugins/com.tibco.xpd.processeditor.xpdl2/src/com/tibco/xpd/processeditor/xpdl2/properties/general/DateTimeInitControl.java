/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.properties.general;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.XMLGregorianCalendar;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

import com.tibco.xpd.resources.ui.components.calendar.DateTimePopup;
import com.tibco.xpd.resources.ui.components.calendar.DateType;
import com.tibco.xpd.ui.properties.XpdFormToolkit;

/**
 * DateTimeInitControl
 * 
 * 
 * @author bharge
 * @since 3.3 (25 Nov 2009)
 */
public class DateTimeInitControl implements SelectionListener {

    /**
     * @return the type
     */
    public DateType getType() {
        return type;
    }

    /**
     * @param type
     *            the type to set
     */
    public void setType(DateType type) {
        this.type = type;
    }

    private Button browseBtn;

    private Text textControl;

    private Button clearBtn;

    private final XpdFormToolkit toolkit;

    private static final String BROWSE_LABEL = "..."; //$NON-NLS-1$

    private static final String CLEAR_LABEL = "Clear"; //$NON-NLS-1$

    private DateType type;

    public DateTimeInitControl(XpdFormToolkit toolkit, Composite parent) {
        this.toolkit = toolkit;
        createControls(parent);
    }

    public void createControls(Composite parent) {
        if (parent != null) {
            Composite rootComposite = toolkit.createComposite(parent);
            rootComposite.setLayout(new GridLayout(3, false));
            rootComposite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

            textControl = toolkit.createText(rootComposite, ""); //$NON-NLS-1$
            textControl.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
            textControl.setEditable(false);

            browseBtn =
                    toolkit.createButton(rootComposite, BROWSE_LABEL, SWT.PUSH);
            browseBtn.addSelectionListener(this);
            browseBtn.setLayoutData(new GridData());

            clearBtn =
                    toolkit.createButton(rootComposite, CLEAR_LABEL, SWT.PUSH);
            clearBtn.addSelectionListener(this);
            clearBtn.setLayoutData(new GridData());
        }
    }

    /**
     * Get the <code>Text</code> control
     * 
     * @return <code>Text</code>
     */
    public Text getTextControl() {
        return textControl;
    }

    public String getText() {
        return textControl != null ? textControl.getText() : null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.swt.events.SelectionListener#widgetDefaultSelected(org.eclipse
     * .swt.events.SelectionEvent)
     */
    @Override
    public void widgetDefaultSelected(SelectionEvent e) {
        // do nothing
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.swt.events.SelectionListener#widgetSelected(org.eclipse.swt
     * .events.SelectionEvent)
     */
    @Override
    public void widgetSelected(SelectionEvent e) {
        Object source = e.getSource();
        if (source instanceof Button) {
            Button button = (Button) source;
            String text = button.getText();
            if (text.equals(CLEAR_LABEL)) {
                handleClear();
            } else if (text.equals(BROWSE_LABEL)) {
                handleBrowse();
            }
        }
    }

    /**
     * Handle the browse button click
     */
    protected void handleBrowse() {
        Date date = null;
        DateType dateType = getType();
        DateTimePopup popup = new DateTimePopup(browseBtn, dateType);

        XMLGregorianCalendar calendar =
                DateTimePopup.createCalendar(new Date());
        popup.setCalendar(calendar);

        if (popup.open() == DateTimePopup.OK) {
            calendar = popup.getSelection();
            calendar.setTimezone(DatatypeConstants.FIELD_UNDEFINED);
            date = DateTimePopup.getDate(calendar);
        }
        String dateStr = null;
        if (date != null) {
            switch (dateType) {
            case DATE:
                dateStr =
                        SimpleDateFormat.getDateInstance(DateFormat.SHORT)
                                .format(date);
                break;
            case DATETIME:
                dateStr =
                        SimpleDateFormat.getDateTimeInstance(DateFormat.SHORT,
                                DateFormat.SHORT).format(date);
                break;
            case TIME:
                dateStr =
                        SimpleDateFormat.getTimeInstance(DateFormat.SHORT)
                                .format(date);
                break;
            }
        }
        if (dateStr != null) {
            textControl.setText(dateStr);
        }
    }

    protected void handleClear() {
        if (textControl.getText() != null || textControl.getText().length() > 0) {
            textControl.setText(""); //$NON-NLS-1$
        }
    }

    /**
     * Get the toolkit
     * 
     * @return
     */
    protected XpdFormToolkit getToolkit() {
        return toolkit;
    }
}
