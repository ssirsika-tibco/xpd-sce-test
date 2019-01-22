/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.deploy.ui.components;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;

import com.tibco.xpd.deploy.Server;
import com.tibco.xpd.deploy.ui.DeployUIActivator;
import com.tibco.xpd.resources.logger.Logger;

/**
 * Control for displaying/capturing deployment parameter of 'string' type.
 * 
 * @author Jan Arciuchiewicz
 */
public class StringControl extends Composite {

    private static final Logger LOG = DeployUIActivator.getDefault()
            .getLogger();

    protected final StringControlAction action;

    protected final Control textControl;

    protected Button actionButton;

    private Image buttonImage;

    private TestableServerProvider testableServerProvider;

    protected Map<String, String> options;

    public StringControl(Composite parent, String defaultText,
            final Map<String, String> options) {
        this(parent, defaultText, options, null);
    }

    /**
     * Creates new string control.
     * 
     * @param parent
     *            parent composite.
     * @param options
     *            contains style and rendering options and other hints.
     */
    public StringControl(Composite parent, String defaultText,
            final Map<String, String> options,
            TestableServerProvider serverProvider) {

        super(parent, getCompositeStyleOption(options));
        this.options = new HashMap<String, String>(options);
        this.testableServerProvider = serverProvider;
        action = createStringControlAction();

        GridLayout gridLayout = new GridLayout();
        gridLayout.numColumns = (action != null) ? 2 : 1;
        gridLayout.marginHeight = 0;
        gridLayout.marginWidth = 0;
        gridLayout.makeColumnsEqualWidth = false;
        this.setLayout(gridLayout);

        textControl = createTextControl(this, defaultText);
        GridDataFactory.swtDefaults().align(SWT.FILL, SWT.CENTER)
                .grab(true, false).applyTo(textControl);

        if (action != null) {
            actionButton = new Button(this, SWT.PUSH);
            if (action.getText() != null
                    && action.getText().trim().length() > 0) {
                actionButton.setText(action.getText());
            }
            if (action.getToolTipText() != null
                    && action.getToolTipText().trim().length() > 0) {
                actionButton.setToolTipText(action.getToolTipText());
            }
            ImageDescriptor imageDescriptor = action.getImageDescriptor();
            if (imageDescriptor != null) {
                buttonImage = imageDescriptor.createImage();
                actionButton.setImage(buttonImage);
            }
            GridDataFactory.swtDefaults().applyTo(actionButton);
            actionButton.addSelectionListener(new SelectionListener() {
                @Override
                public void widgetDefaultSelected(SelectionEvent e) {
                    // do nothing.
                }

                @Override
                public void widgetSelected(SelectionEvent e) {
                    Server testableServer = null;
                    if (testableServerProvider != null) {
                        testableServer =
                                testableServerProvider.getTestableServer();
                    }
                    action.runWithServer(testableServer);
                }
            });
        }
    }

    /**
     * @param options
     * @return
     */
    private StringControlAction createStringControlAction() {
        String actionString = options.get("StringAction"); //$NON-NLS-1$
        if (actionString != null && actionString.trim().length() > 0) {
            String[] actionStringArray = actionString.trim().split("/"); //$NON-NLS-1$
            if (actionStringArray.length == 2) {
                String pluginId = actionStringArray[0];
                String aClass = actionStringArray[1];
                StringControlAction stringControlAction;
                try {
                    stringControlAction =
                            (StringControlAction) Platform.getBundle(pluginId)
                                    .loadClass(aClass).newInstance();
                } catch (Exception e) {
                    throw new IllegalArgumentException(
                            String.format("Incorrect value of 'StringAction' parameter's facet: '%s'", //$NON-NLS-1$
                                    actionString), e);
                }
                stringControlAction.setStringControl(this);
                return stringControlAction;
            } else {
                throw new IllegalArgumentException(
                        String.format("Incorrect value of 'StringAction' parameter's facet: '%s'", //$NON-NLS-1$
                                actionString));
            }
        }
        return null;
    }

    private static int getCompositeStyleOption(Map<String, String> options) {
        String compositeStyle = options.get("StringCompositeStyle"); //$NON-NLS-1$
        if (compositeStyle != null) {
            try {
                int compositeStyleInt = Integer.parseInt(compositeStyle.trim());
                return compositeStyleInt;
            } catch (NumberFormatException e) {
                // ignore, default will be used
            }
        }
        return SWT.NONE; // default
    }

    protected Control createTextControl(Composite parent, String defaultText) {
        Map<String, String> opt = Collections.emptyMap();
        if (options != null) {
            opt = Collections.unmodifiableMap(options);
        }
        boolean isReadOnly = Boolean.parseBoolean(opt.get("StringReadOnly")); //$NON-NLS-1$
        String controlType = opt.get("StringControlType"); //$NON-NLS-1$
        if (controlType != null && controlType.equals("COMBO")) { //$NON-NLS-1$ 
            int style = SWT.SINGLE | SWT.BORDER;
            if (isReadOnly) {
                style |= SWT.READ_ONLY;
            }
            Combo combo = new Combo(parent, style);
            combo.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
            updateStringValues(combo);
            combo.setText(defaultText);
            return combo;
        } else {
            int style = SWT.SINGLE | SWT.BORDER;
            if (isReadOnly) {
                style |= SWT.READ_ONLY;
            }
            Text textFeld = new Text(parent, style);
            textFeld.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
            textFeld.setText(defaultText);
            return textFeld;
        }

    }

    /**
     * String values (usually represented as combo items) will be updated. The
     * updated values will depend of facets provided in extension.
     * <p>
     * First if "StringValuesProvided" was provided it will be queried and if it
     * returns collection of strings then they will be used as string values.
     * Otherwise (if "StringValuesProvided" was not provided or it's invocation
     * returned 'null') static list from "StringValues" facet will be used.
     * </p>
     * 
     */
    protected void updateStringValues(Control textControl) {
        if (textControl instanceof Combo) {
            Combo combo = (Combo) textControl;

            String[] newItems = null;
            String stringValuesProvider = options.get("StringValuesProvider"); //$NON-NLS-1$
            String stringValues = options.get("StringValues"); //$NON-NLS-1$

            /* Check dynamic values first. */
            if (stringValuesProvider != null
                    && !stringValuesProvider.trim().isEmpty()) {

                String[] actionStringArray =
                        stringValuesProvider.trim().split("/"); //$NON-NLS-1$
                if (actionStringArray.length == 2) {
                    String pluginId = actionStringArray[0];
                    String aClass = actionStringArray[1];
                    StringControlValuesProvider stringValueControl;
                    try {
                        stringValueControl =
                                (StringControlValuesProvider) Platform
                                        .getBundle(pluginId).loadClass(aClass)
                                        .newInstance();
                    } catch (Exception e) {
                        throw new IllegalArgumentException(
                                String.format("Incorrect value of 'StringValuesProvider' parameter's facet: '%s'", //$NON-NLS-1$
                                        stringValuesProvider), e);
                    }
                    Collection<String> dynamicStringValues = null;
                    try {
                        dynamicStringValues =
                                stringValueControl
                                        .getStringValues(testableServerProvider);
                    } catch (Exception e) {
                        DeployUIActivator
                                .getDefault()
                                .getLogger()
                                .error(e,
                                        "Retriving dynamic string values thrown exception."); //$NON-NLS-1$
                    }

                    if (dynamicStringValues != null) {
                        newItems =
                                dynamicStringValues
                                        .toArray(new String[dynamicStringValues
                                                .size()]);
                    }

                }
            }
            /* If dynamic values are not provided - try to get static values. */
            if (newItems == null && stringValues != null
                    && !stringValues.trim().isEmpty()) {
                String delimiter = options.get("StringValuesDelimiter"); //$NON-NLS-1$
                delimiter = (delimiter != null) ? delimiter : "\\|"; //$NON-NLS-1$
                newItems = stringValues.split(delimiter);
            }
            if (newItems != null) {
                combo.setItems(newItems);
            }
        }
    }

    /**
     * Returns the control containing text.
     * 
     * @return the control containing text at the moment it can be {@link Text}
     *         or {@link Combo}.
     */
    public Control getTextControl() {
        return textControl;
    }

    /**
     * Returns the button invoking associated action.
     * 
     * @return the button invoking associated action.
     */
    public Button getActionButton() {
        return actionButton;
    }

    /**
     * Gets text of the contained textual control.
     * 
     * @return text of the contained textual control
     */
    public String getText() {
        if (textControl instanceof Text) {
            return ((Text) textControl).getText();
        } else if (textControl instanceof Combo) {
            return ((Combo) textControl).getText();
        }
        return null;
    }

    /**
     * Sets text of the contained textual control.
     */
    public void setText(String text) {
        String theText = text != null ? text : ""; //$NON-NLS-1$
        if (textControl instanceof Text) {
            ((Text) textControl).setText(theText);
        } else if (textControl instanceof Combo) {
            ((Combo) textControl).setText(theText);
        }
    }

    public void setTestableServerProvider(
            TestableServerProvider testableServerProvider) {
        this.testableServerProvider = testableServerProvider;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void dispose() {
        if (buttonImage != null) {
            buttonImage.dispose();
            buttonImage = null;
        }
        super.dispose();
    }
}
