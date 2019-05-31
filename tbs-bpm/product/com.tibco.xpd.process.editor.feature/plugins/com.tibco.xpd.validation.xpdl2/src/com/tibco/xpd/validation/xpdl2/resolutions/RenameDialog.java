package com.tibco.xpd.validation.xpdl2.resolutions;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/**
 * Dialog to rename an item.
 * 
 * @author nwilson
 */
public class RenameDialog extends Dialog {
    /** Default text box width. */
    private static final int DEFAULT_WIDTH = 150;

    /** Dialog title id. */
    private static final String RENAME_DIALOG_TITLE = "renameDialogTitle"; //$NON-NLS-1$

    private static final String DEC_PLACES_DIALOG_TITLE =
            "decimalPlacesDialogTitle"; //$NON-NLS-1$

    /** Old name label id. */
    private static final String RENAME_DIALOG_OLD = "renameDialogNewNameLabel"; //$NON-NLS-1$

    /** New name label id. */
    private static final String RENAME_DIALOG_NEW = "renameDialogOldNameLabel"; //$NON-NLS-1$

    /** The old name. */
    private String oldName;

    private String oldNameDescription;

    /** The new name. */
    private String newName;

    private String defaultName;

    /** The name control. */
    private Text name;

    private String title = null;

    private Label oldNameDescriptionCtrl;

    /**
     * Ask the user what value to set as the decimal places.
     * 
     * @param oldValue
     *            the current decimal places setting.
     * @param defaultValue
     *            the default decimal places setting
     * @return the chosen decimal places setting - as a string. If the user
     *         choses "cancel", the return value will be an empty string.
     */
    public static String getDecimalPlaces(String oldValue,
            String defaultValue) {
        RenameDialog dialog = new RenameDialog(null,
                ResolutionMessages.getText(
                        RenameDialog.DEC_PLACES_DIALOG_TITLE),
                oldValue);
        dialog.setDefaultName(defaultValue);
        dialog.setBlockOnOpen(true);

        int button = dialog.open();
        return (button == Window.OK) ? dialog.getName().trim() : ""; //$NON-NLS-1$
    }

    /**
     * @param parentShell
     *            The parent shell.
     * @param oldName
     *            The old name.
     */
    public RenameDialog(Shell parentShell, String oldName) {
        this(parentShell, ResolutionMessages.getText(RENAME_DIALOG_TITLE),
                oldName);
    }

    public RenameDialog(Shell parentShell, String title, String oldName) {
        super(parentShell);
        this.title = title;
        this.oldName = oldName == null ? "" : oldName; //$NON-NLS-1$
        this.oldNameDescription = this.oldName;
        newName = this.oldName;
        defaultName = this.oldName;
    }

    /**
     * @param shell
     *            The shell.
     * @see org.eclipse.jface.window.Window#configureShell(org.eclipse.swt.widgets.Shell)
     */
    @Override
    protected void configureShell(Shell shell) {
        super.configureShell(shell);
        shell.setText(title);
    }

    /**
     * @return The new name.
     */
    public String getName() {
        return newName;
    }

    /**
     * @param defaultName
     *            the defaultName to set
     */
    public void setDefaultName(String defaultName) {
        this.defaultName = defaultName;
        if (name != null && !name.isDisposed()) {
            name.setText(defaultName != null ? defaultName : ""); //$NON-NLS-1$
        }
    }

    /**
     * @param oldNameDescription
     *            the oldNameDescription to set
     */
    public void setOldNameDescription(String oldNameDescription) {
        this.oldNameDescription = oldNameDescription;
        if (oldNameDescriptionCtrl != null
                && !oldNameDescriptionCtrl.isDisposed()) {
            oldNameDescriptionCtrl.setText(oldNameDescription);
        }
    }

    /**
     * @param parent
     *            The parent composite.
     * @return The root dialog control.
     * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets.Composite)
     */
    @Override
    protected Control createDialogArea(Composite parent) {
        Composite composite = (Composite) super.createDialogArea(parent);
        GridLayout layout = new GridLayout();
        layout.marginHeight = 20;
        layout.marginWidth = 20;
        layout.numColumns = 2;
        composite.setLayout(layout);
        composite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        Label oldNameLabel = new Label(composite, SWT.NONE);
        oldNameLabel.setText(ResolutionMessages.getText(RENAME_DIALOG_OLD));
        oldNameDescriptionCtrl = new Label(composite, SWT.NONE);
        oldNameDescriptionCtrl
                .setText(oldNameDescription != null ? oldNameDescription : ""); //$NON-NLS-1$

        GridData data = new GridData();
        data.widthHint = DEFAULT_WIDTH;
        data.grabExcessHorizontalSpace = true;
        data.horizontalAlignment = SWT.FILL;
        Label nameLabel = new Label(composite, SWT.NONE);
        nameLabel.setText(ResolutionMessages.getText(RENAME_DIALOG_NEW));
        name = new Text(composite, SWT.BORDER);
        name.setText(defaultName != null ? defaultName : ""); //$NON-NLS-1$
        name.setLayoutData(data);
        name.setSelection(0, oldName.length());
        return composite;
    }

    /**
     * @see org.eclipse.jface.dialogs.Dialog#okPressed()
     */
    @Override
    protected void okPressed() {
        newName = name.getText();
        super.okPressed();
    }
}