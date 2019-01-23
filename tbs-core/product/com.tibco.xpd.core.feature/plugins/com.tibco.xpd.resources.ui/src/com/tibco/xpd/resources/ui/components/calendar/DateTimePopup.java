/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.resources.ui.components.calendar;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.fieldassist.ContentProposalAdapter;
import org.eclipse.jface.fieldassist.IContentProposal;
import org.eclipse.jface.fieldassist.IContentProposalProvider;
import org.eclipse.jface.fieldassist.TextContentAdapter;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.TraverseEvent;
import org.eclipse.swt.events.TraverseListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Text;

import com.tibco.xpd.resources.ui.XpdResourcesUIActivator;
import com.tibco.xpd.resources.ui.internal.Messages;

/**
 * A pop-up window to show a calendar control that can be set to select a date,
 * date and time, time (also supports time zone selection).
 * <p>
 * To convert from {@link Date} to {@link XMLGregorianCalendar} input see
 * methods {@link #createCalendar(Date)} and
 * {@link #getDate(XMLGregorianCalendar)}.
 * </p>
 * 
 * @author njpatel
 * @since 3.3
 */
public class DateTimePopup extends Window {

    private static final String GMT = "GMT"; //$NON-NLS-1$

    private static final String TIME_ZONE_PATTERN =
            GMT + "[+-]\\d{1,2}\\.?\\d?"; //$NON-NLS-1$

    private DateTime calendar;

    private DateTime time;

    private Text timeZoneTxt;

    private Button okBtn;

    private final DateType style;

    private XMLGregorianCalendar selection;

    private final Control control;

    /**
     * A pop-up window to show calendar, date or time selection control. The
     * style will determine which control to show. The style of DATE will show
     * the calendar, DATETIME will show a calendar and time input and TIME just
     * the time input.
     * 
     * @param control
     *            parent control
     * @param style
     *            date type
     */
    public DateTimePopup(Control control, DateType style) {
        super(control.getShell());
        this.control = control;
        this.style = style;
        setShellStyle(SWT.ON_TOP | SWT.TOOL | SWT.APPLICATION_MODAL | SWT.CLOSE);
        setBlockOnOpen(true);
    }

    /**
     * Set the current date.
     * 
     * @param calendar
     */
    public void setCalendar(XMLGregorianCalendar calendar) {
        if (calendar != null) {
            selection = (XMLGregorianCalendar) calendar.clone();
        } else {
            selection = null;
        }
    }

    /**
     * Get the {@link XMLGregorianCalendar} for the given date.
     * 
     * @param date
     * @return
     */
    public static XMLGregorianCalendar createCalendar(Date date) {
        try {
            GregorianCalendar cal =
                    (GregorianCalendar) GregorianCalendar.getInstance();
            if (date != null) {
                cal.setTime(date);
            }
            return DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
        } catch (DatatypeConfigurationException e) {
            XpdResourcesUIActivator
                    .getDefault()
                    .getLogger()
                    .error(e,
                            Messages.DateTimePopup_failedToCreateCalendar_error_message);
        }
        return null;
    }

    /**
     * Get the {@link Date} from the given {@link XMLGregorianCalendar}.
     * 
     * @param calendar
     * @return
     */
    public static Date getDate(XMLGregorianCalendar calendar) {
        if (calendar != null) {
            GregorianCalendar gCal = calendar.toGregorianCalendar();
            if (gCal != null) {
                return gCal.getTime();
            }
        }
        return null;
    }

    @Override
    protected Point getInitialLocation(Point initialSize) {

        Point location = null;
        if (control != null) {
            Rectangle r = control.getBounds();
            if (control.getParent() != null) {
                location = control.getParent().toDisplay(r.x, r.y);
                location = new Point(location.x + r.width, location.y);
            }
        }
        return location != null ? location : super
                .getInitialLocation(initialSize);
    }

    @Override
    protected Layout getLayout() {
        FillLayout layout = new FillLayout();
        layout.marginHeight = 0;
        layout.marginHeight = 0;
        return layout;
    }

    @Override
    protected Control createContents(Composite parent) {
        Composite root = (Composite) super.createContents(parent);

        if (style == DateType.TIME) {
            // Only time picker so show the time control only
            root.setLayout(new GridLayout(2, false));
            time = new DateTime(root, SWT.TIME | SWT.SHORT | SWT.BORDER);
            time.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));
            okBtn = new Button(root, SWT.NONE);
            okBtn
                    .setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true,
                            false));

        } else if (style == DateType.DATE) {
            root.setLayout(new GridLayout());
            calendar = new DateTime(root, SWT.CALENDAR | SWT.BORDER);
            calendar.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false,
                    false));
            okBtn = new Button(root, SWT.NONE);
            okBtn
                    .setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true,
                            false));
        } else {
            root.setLayout(new GridLayout(2, false));
            calendar = new DateTime(root, SWT.CALENDAR | SWT.BORDER);
            calendar.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false,
                    false));

            Composite rightSide = new Composite(root, SWT.NONE);
            rightSide.setBackground(root.getBackground());
            rightSide
                    .setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
            rightSide.setLayout(new GridLayout());
            if (style == DateType.DATETIME || style == DateType.DATETIMETZ) {
                Group timeGrp = new Group(rightSide, SWT.NONE);
                timeGrp.setBackground(rightSide.getBackground());
                timeGrp.setText(Messages.DateTimePopup_timeGroup_label);
                timeGrp.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true,
                        false));
                FillLayout layout = new FillLayout();
                layout.marginHeight = 3;
                layout.marginWidth = 3;
                timeGrp.setLayout(layout);
                time = new DateTime(timeGrp, SWT.TIME | SWT.SHORT | SWT.BORDER);
            }

            if (style == DateType.DATETIMETZ) {
                Group tzGrp = new Group(rightSide, SWT.NONE);
                tzGrp.setBackground(rightSide.getBackground());
                tzGrp.setText(Messages.DateTimePopup_timeZoneGroup_label);
                tzGrp
                        .setLayoutData(new GridData(SWT.FILL, SWT.TOP, true,
                                false));
                FillLayout layout = new FillLayout();
                layout.marginHeight = 3;
                layout.marginWidth = 3;
                tzGrp.setLayout(layout);

                timeZoneTxt = createTimeZoneText(tzGrp);
            }

            okBtn = new Button(rightSide, SWT.NONE);
            GridData data = new GridData(SWT.FILL, SWT.BOTTOM, true, true);
            okBtn.setLayoutData(data);
        }

        if (okBtn != null) {
            okBtn.setText(IDialogConstants.OK_LABEL);
            okBtn.addSelectionListener(new SelectionAdapter() {
                @Override
                public void widgetSelected(SelectionEvent e) {
                    okClicked();
                }
            });
        }

        // Update the current selection
        if (selection != null) {
            if (calendar != null) {
                // Show the currently selected date in the calendar
                calendar.setDate(selection.getYear(),
                        selection.getMonth() - 1,
                        selection.getDay());
            }

            if (time != null) {
                time.setTime(selection.getHour(),
                        selection.getMinute(),
                        selection.getSecond());
            }

            if (timeZoneTxt != null) {
                int offset = selection.getTimezone();
                if (offset != DatatypeConstants.FIELD_UNDEFINED) {
                    timeZoneTxt
                            .setText(getTimeZoneLabel((double) offset / 60.0));
                } else {
                    timeZoneTxt.setText(getTimeZoneLabel(0.0));
                }
            }
        }

        root.addTraverseListener(new TraverseListener() {
            public void keyTraversed(TraverseEvent e) {
                if (e.keyCode == SWT.CR) {
                    okClicked();
                }
            }
        });

        return root;
    }

    /**
     * Create a time zone label with the given GMT offset. So, if offset is 1
     * then the label will be "GMT+1".
     * 
     * @param offset
     * @return
     */
    private String getTimeZoneLabel(Double offset) {
        String str = offset > 0 ? GMT + "+" : GMT; //$NON-NLS-1$

        if (offset != 0) {
            if (offset == offset.intValue()) {
                return str + offset.intValue();
            } else {
                double rem = offset - offset.intValue();
                if (rem < 0.5 && rem > 0.0) {
                    return str + (offset.intValue() + 0.5);
                } else if (rem > 0.5) {
                    return str + (offset.intValue() + 1);
                }
                return str + offset;
            }
        }

        return GMT;
    }

    /**
     * Create the time zone text control.
     * 
     * @param parent
     * @return
     */
    protected Text createTimeZoneText(Composite parent) {
        final Text txt = new Text(parent, SWT.BORDER);
        ContentProposalAdapter adapter =
                new ContentProposalAdapter(txt, new TextContentAdapter(),
                        new TimeZoneProposalProvider(), null, null);

        GC gc = new GC(txt);
        gc.setFont(txt.getFont());
        int averageCharWidth = gc.getFontMetrics().getAverageCharWidth();
        int height = gc.getFontMetrics().getHeight();
        gc.dispose();

        adapter.setPopupSize(new Point(averageCharWidth * 20, height * 8));
        adapter.setAutoActivationDelay(0);
        adapter
                .setProposalAcceptanceStyle(ContentProposalAdapter.PROPOSAL_REPLACE);

        txt.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                txt.setSelection(0, txt.getText().length());
            }
        });

        return txt;
    }

    /**
     * Respose to ok click or enter key press.
     */
    private void okClicked() {
        if (selection != null) {
            if (style != DateType.TIME) {
                selection.setYear(calendar.getYear());
                selection.setMonth(calendar.getMonth() + 1);
                selection.setDay(calendar.getDay());
            }

            if (style != DateType.DATE) {
                selection.setHour(time.getHours());
                selection.setMinute(time.getMinutes());
                selection.setSecond(0);
                selection.setMillisecond(0);
            }

            if (style == DateType.DATETIMETZ) {
                double offset = getTimeZoneOffset();
                selection.setTimezone((int) (offset * 60.0));
            }
        }
        close();
    }

    /**
     * Get the time zone offset.
     * 
     * @return
     */
    private double getTimeZoneOffset() {
        double offset = 0;
        if (timeZoneTxt != null && !timeZoneTxt.isDisposed()) {
            String text = timeZoneTxt.getText();

            // See if this is already a number
            try {
                offset = Double.parseDouble(text);
            } catch (NumberFormatException e) {
                // Not a number so check if it's the time zone string
                if (text.matches(TIME_ZONE_PATTERN)) {
                    text = text.substring(3);
                    try {
                        offset = Double.parseDouble(text);
                    } catch (NumberFormatException e1) {
                        // Do nothing, will return 0 as the offset
                    }
                }
            }
        }
        if (offset < -12) {
            offset = -12;
        } else if (offset > 12) {
            offset = 12;
        }
        return offset;
    }

    /**
     * Get the date and/or time selected by the user in the pop-up.
     * 
     * @return {@link Date}, or <code>null</code> if the user cancelled.
     */
    public XMLGregorianCalendar getSelection() {
        return selection;
    }

    /**
     * Time zone's content-assisted control's proposal provider.
     * 
     * @author njpatel
     * 
     */
    private class TimeZoneProposalProvider implements IContentProposalProvider {

        public IContentProposal[] getProposals(String contents, int position) {
            if (contents == null || contents.length() == 0) {
                return getSampleProposals();
            } else if (contents.equals(GMT)) {
                return new IContentProposal[0];
            } else {
                String value = contents.replaceAll("[^\\d+-]", ""); //$NON-NLS-1$ //$NON-NLS-2$
                try {
                    List<IContentProposal> proposals =
                            new ArrayList<IContentProposal>();
                    Double parseDouble = Double.parseDouble(value);
                    if (parseDouble > 12.0) {
                        parseDouble = 12.0;
                    } else if (parseDouble < -12.0) {
                        parseDouble = -12.0;
                    }

                    if (parseDouble == parseDouble.intValue()) {
                        int intVal = parseDouble.intValue();
                        if (intVal > 0) {
                            int myVal = intVal;

                            while (myVal <= 12) {
                                proposals.add(new MyContentProposal(myVal));
                                if (intVal < 12) {
                                    proposals.add(new MyContentProposal(
                                            myVal + 0.5));
                                }
                                myVal *= 10;
                            }
                            intVal = -intVal;
                        }
                        int myVal = intVal;
                        while (myVal >= -12) {
                            proposals.add(new MyContentProposal(myVal));
                            if (intVal > -12) {
                                proposals
                                        .add(new MyContentProposal(myVal - 0.5));
                            }
                            myVal *= 10;
                        }
                    } else {
                        if (parseDouble > 0) {
                            proposals.add(new MyContentProposal(parseDouble));
                            parseDouble = -parseDouble;
                        }
                        proposals.add(new MyContentProposal(parseDouble));
                    }
                    if (!proposals.isEmpty()) {
                        return proposals.toArray(new IContentProposal[proposals
                                .size()]);
                    }
                } catch (NumberFormatException e) {
                    // Do nothing - show sample values
                }
            }
            return getSampleProposals();
        }

        private IContentProposal[] getSampleProposals() {
            List<IContentProposal> proposals =
                    new ArrayList<IContentProposal>();

            for (int idx = -3; idx < 4; idx++) {
                proposals.add(new MyContentProposal(idx));
            }

            return proposals.toArray(new IContentProposal[proposals.size()]);
        }

    }

    /**
     * Content proposal for the content-assist time zone control.
     * 
     * @author njpatel
     * 
     */
    private class MyContentProposal implements IContentProposal,
            Comparable<MyContentProposal> {

        private final double value;

        public MyContentProposal(double value) {
            this.value = value;
        }

        public String getContent() {
            return getTimeZoneLabel(value);
        }

        public int getCursorPosition() {
            return getContent().length();
        }

        public String getDescription() {
            return null;
        }

        public String getLabel() {
            return null;
        }

        public int compareTo(MyContentProposal o) {
            double diff = o.value - value;
            return diff == 0 ? 0 : diff < 0 ? -1 : 1;
        }
    }

}
