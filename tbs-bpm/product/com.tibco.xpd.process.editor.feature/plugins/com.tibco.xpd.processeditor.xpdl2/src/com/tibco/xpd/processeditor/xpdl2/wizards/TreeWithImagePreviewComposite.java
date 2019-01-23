/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.processeditor.xpdl2.wizards;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.CheckboxTreeViewer;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.widgets.FormText;
import org.eclipse.ui.part.Page;
import org.eclipse.ui.part.PageBook;

import com.tibco.xpd.fragments.IFragment;
import com.tibco.xpd.fragments.IFragmentCategory;
import com.tibco.xpd.processeditor.xpdl2.dialogs.XPDZoomImageControl;
import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.processeditor.xpdl2.pkgtemplates.PackageTemplate;
import com.tibco.xpd.processeditor.xpdl2.pkgtemplates.PackageTemplateChildElement;
import com.tibco.xpd.xpdExtension.ProcessInterface;

/**
 * Composite control containing a Tree viewer and another composite to display
 * images.
 * 
 * @author rsomayaj
 * 
 */
public class TreeWithImagePreviewComposite extends Composite implements
        ISelectionChangedListener {

    public interface ICheckTreeWithDescriptionInputProvider {

        /**
         * Get the content provider for the checkbox table viewer
         * 
         * @return <code>IStructuredContentProvider</code>
         */
        public ITreeContentProvider getContentProvider();

        /**
         * Get the label provider for the checkbox table viewer
         * 
         * @return <code>ILabelProvider</code>
         */
        public ILabelProvider getLabelProvider();

        /**
         * Get the input for the checkbox table viewer.
         * 
         * @return Input for the table viewer
         */
        public Object getInput();

        /**
         * Get the initial selection to be made in the table
         * 
         * @return the object to select in the table
         */
        public Object getInitialSelection();

        /**
         * Get the description associated with the given element from the
         * checkbox table
         * 
         * @param element
         *            Element selected in the checkbox table
         * @return Description to display in the description viewer
         */
        public String getDescription(Object element);
    }

    private class TemplateImageControl extends XPDZoomImageControl {
        private ImageData imageData = null;

        private Image img = null;

        private Image scaledImage = null;

        public TemplateImageControl(Composite parent, int style) {
            super(parent, style, true, null);
        }

        @Override
        protected Image createImage(Dimension sizeHint) {
            // Dispose the old scaled image if there is one.
            disposeScaledImage();
            disposeImg();
            if (imageData == null) {
                return null;
            }
            // If not asked to scale or no image then return the original image.
            if (img == null) {
                img =
                        new Image(PlatformUI.getWorkbench().getDisplay(),
                                imageData);
            }
            if (sizeHint == null || imageData == null) {
                return img;
            }

            // Maintain the original aspect ratio
            // base scale on the largest side.
            ImageData imgData = imageData;

            double imageScaleX =
                    (double) sizeHint.width / (double) imgData.width;
            double imageScaleY =
                    (double) sizeHint.height / (double) imgData.height;

            double imgScale;

            if (imageScaleY < imageScaleX) {
                imgScale = imageScaleY;
            } else {
                imgScale = imageScaleX;
            }

            if (imgScale > 1.0) {
                imgScale = 1.0;
            }

            int finalWidth = (int) (imgData.width * imgScale);
            int finalHeight = (int) (imgData.height * imgScale);

            //
            // Create a new image of the required size.
            scaledImage =
                    new Image(Display.getDefault(), sizeHint.width,
                            sizeHint.height);
            GC scaleGC = new GC(scaledImage);

            // Windows image looks rubbish without interpolation=high
            // Linux looks rubbish with interpolation = high.
            if (System.getProperty("os.name").toUpperCase().startsWith( //$NON-NLS-1$
            "WIN")) {//$NON-NLS-1$
                scaleGC.setInterpolation(SWT.HIGH);
            }

            scaleGC.drawImage(img,
                    0,
                    0,
                    img.getBounds().width,
                    img.getBounds().height,
                    (sizeHint.width / 2) - (finalWidth / 2),
                    (sizeHint.height / 2) - (finalHeight / 2),
                    finalWidth,
                    finalHeight);

            // That's it, dispose the GC and returned the scaled version of the
            // image.
            scaleGC.dispose();

            return scaledImage;
        }

        /**
         * dispose of the scaled version of image if there is one.
         */
        private void disposeScaledImage() {
            if (scaledImage != null && !scaledImage.isDisposed()) {
                scaledImage.dispose();
                scaledImage = null;
            }
        }

        /**
         * dispose of the scaled version of image if there is one.
         */
        private void disposeImg() {
            if (img != null && !img.isDisposed()) {
                img.dispose();
                img = null;
            }
        }

        public void setImageData(ImageData imageData) {
            this.imageData = imageData;
            disposeScaledImage();

            refresh(true);
        }

        @Override
        protected void finalize() throws Throwable {
            // System.out.println("Finalizing image preview."); //$NON-NLS-1$
            dispose();
            super.finalize();
        }

        /**
         * @see com.tibco.xpd.processeditor.xpdl2.dialogs.XPDZoomImageControl#dispose()
         * 
         */
        @Override
        public void dispose() {
            if (img != null && !img.isDisposed()) {
                img.dispose();
            }
            super.dispose();
        }

        @Override
        public void setToolTipText(String string) {
            // do nothing
        }
    }

    class ImagePage extends Page {

        @Override
        public void createControl(Composite parent) {
            templateImagePreview = createTemplatePreview(parent);
        }

        @Override
        public Control getControl() {
            return templateImagePreview;
        }

        @Override
        public void setFocus() {

        }

    }

    class DescriptionPage extends Page {

        @Override
        public void createControl(Composite parent) {
            formText = new FormText(parent, SWT.BORDER | SWT.MULTI | SWT.WRAP);
            formText.setBackground(ColorConstants.white);
        }

        @Override
        public Control getControl() {
            return formText;
        }

        @Override
        public void setFocus() {
            // Do nothing

        }

    }

    private ImageData templateImage;

    private TemplateImageControl templateImagePreview;

    private TreeViewer treeViewer;

    private final ICheckTreeWithDescriptionInputProvider provider;

    private boolean checkTreeViewer = false;

    private PageBook descPgBook;

    private ImagePage imgPreviewPage;

    private DescriptionPage descPage;

    private FormText formText;

    private final String descriptionHead =
            Messages.TreeWithImagePreviewComposite_DescribeMsgTemplateDescHead_shortmsg;

    private final String descriptionSection = "<p> <b></b></p>" //$NON-NLS-1$
            + descriptionHead + " <p> %s</p>"; //$NON-NLS-1$

    private final String formEnd = "</form>"; //$NON-NLS-1$

    /**
     * Constructor for the table with description composite.
     * 
     * @param parent
     *            The parent <code>Composite</code>
     * @param provider
     *            The input provider
     * @param style
     *            <code>{@link SWT#NONE}</code> for a table,
     *            <code>{@link SWT#CHECK}</code> for a checkbox table.
     */
    public TreeWithImagePreviewComposite(Composite parent,
            ICheckTreeWithDescriptionInputProvider provider, int style) {
        super(parent, style);
        this.provider = provider;

        checkTreeViewer = (style & SWT.CHECK) == SWT.CHECK;

        if (provider != null) {
            GridLayout layout = new GridLayout(4, false);
            layout.marginHeight = 0;
            layout.marginWidth = 0;
            super.setLayout(layout);

            // Create the main part of the page - the wizard selection
            SashForm sashForm = new SashForm(this, SWT.HORIZONTAL);
            GridData gridData = new GridData(GridData.FILL_BOTH);
            // limit the width of the sash form to avoid the wizard opening very
            // wide.
            gridData.horizontalSpan = 3;
            gridData.widthHint = 700;
            gridData.heightHint = 350;
            sashForm.setLayoutData(gridData);

            // Create the left hand side checkbox table viewer
            treeViewer = createTreeViewer(sashForm);
            treeViewer.getControl().setFont(getFont());

            descPgBook = new PageBook(sashForm, SWT.None);
            imgPreviewPage = new ImagePage();
            imgPreviewPage.createControl(descPgBook);

            descPage = new DescriptionPage();
            descPage.createControl(descPgBook);

            treeViewer.expandToLevel(2);
            sashForm.setWeights(new int[] { 30, 70 });

            /*
             * Select the first item in the table by default, unless an initial
             * selection has been provided
             */
            Object initialSelection = provider.getInitialSelection();
            if (initialSelection != null) {
                treeViewer.setSelection(new StructuredSelection(
                        initialSelection));
            } else {
                if (treeViewer.getTree().getItems().length > 0) {
                    treeViewer.getTree().setSelection(treeViewer.getTree()
                            .getItem(0));
                }
            }

            ISelection selection = treeViewer.getSelection();
            if (selection instanceof IStructuredSelection) {
                updateTemplatePreview((IStructuredSelection) selection);
            }
            // Add this class as the selection listener of the table viewer
            addSelectionChangedListener(this);

        } else {
            // No provider available
            throw new NullPointerException("provider is null."); //$NON-NLS-1$
        }
    }

    @Override
    public void setEnabled(boolean enabled) {
        treeViewer.getTree().setEnabled(enabled);
        templateImagePreview.setEnabled(enabled);
        // Need to redraw to refresh the description viewer - otherwise it will
        // not grey out when disabled
        super.setEnabled(enabled);
    }

    /**
     * Set all items checked if using the checkbox table viewer. NOTE: This
     * should be only used with a checkbox table viewer.
     * 
     * @param state
     *            <code>true</code> if the element should be checked, and
     *            <code>false</code> if it should be unchecked
     * 
     * @throws IllegalArgumentException
     *             If this call is made to a non-checkbox table viewer.
     */
    public void setAllChecked(boolean state) {
        if (checkTreeViewer) {
            ((CheckboxTreeViewer) treeViewer).setAllChecked(state);
        } else {
            throw new IllegalArgumentException(
                    "Table viewer is not a checkbox table."); //$NON-NLS-1$
        }
    }

    /**
     * Set the given objects as checked in the check box table viewer. All other
     * elements will be unchecked. NOTE: This should be only used with a
     * checkbox table viewer.
     * 
     * @param elements
     *            Elements to check in the checkbox table viewer
     * 
     * @throws IllegalArgumentException
     *             If this call is made to a non-checkbox table viewer.
     */
    public void SetCheckedElements(Object[] elements) {
        if (checkTreeViewer) {
            ((CheckboxTreeViewer) treeViewer).setCheckedElements(elements);
        } else {
            throw new IllegalArgumentException(
                    "Table viewer is not a checkbox table."); //$NON-NLS-1$
        }
    }

    /**
     * Get the checked elements in the table. NOTE: This should only be used for
     * a checkbox table viewer.
     * 
     * @return Checked elements.
     * 
     * @throws IllegalArgumentException
     *             If this call is made to a non-checkbox table viewer.
     */
    public Object[] getCheckedElements() {
        Object[] elements = new Object[0];

        if (checkTreeViewer) {
            elements = ((CheckboxTreeViewer) treeViewer).getCheckedElements();
        } else {
            throw new IllegalArgumentException(
                    "Table viewer is not a checkbox table."); //$NON-NLS-1$
        }

        return elements;
    }

    /**
     * Check if the given element is checked. NOTE: This should be only used
     * with a checkbox table viewer.
     * 
     * @param element
     * @return
     */
    public boolean getChecked(Object element) {
        if (checkTreeViewer) {
            return ((CheckboxTreeViewer) treeViewer).getChecked(element);
        } else {
            throw new IllegalArgumentException(
                    "Table viewer is not a checkbox table."); //$NON-NLS-1$
        }
    }

    /**
     * Add a check state listener to the table viewer. This will only apply if
     * the table viewer is set with the {@link SWT#CHECK} style.
     * 
     * @see CheckboxTableViewer#addCheckStateListener(ICheckStateListener)
     * 
     * @param listener
     */
    public void addCheckStateListener(ICheckStateListener listener) {
        if (treeViewer != null && checkTreeViewer) {
            ((CheckboxTreeViewer) treeViewer).addCheckStateListener(listener);
        }
    }

    /**
     * Remove a check state listener to the table viewer. This will only apply
     * if the table viewer is set with the {@link SWT#CHECK} style.
     * 
     * @see CheckboxTableViewer#removeCheckStateListener(ICheckStateListener)
     * 
     * @param listener
     */
    public void removeCheckStateListener(ICheckStateListener listener) {
        if (treeViewer != null && checkTreeViewer) {
            ((CheckboxTreeViewer) treeViewer)
                    .removeCheckStateListener(listener);
        }
    }

    /**
     * Add a selection change listener to the table viewer.
     * 
     * @see TableViewer#addSelectionChangedListener(ISelectionChangedListener)
     * 
     * @param listener
     */
    public void addSelectionChangedListener(ISelectionChangedListener listener) {
        if (treeViewer != null) {
            treeViewer.addSelectionChangedListener(listener);
        }
    }

    /**
     * Remove a selection change listener to the table viewer.
     * 
     * @see TableViewer#removeSelectionChangedListener(ISelectionChangedListener)
     * 
     * @param listener
     */
    public void removeSelectionChangedListener(
            ISelectionChangedListener listener) {
        if (treeViewer != null) {
            treeViewer.removeSelectionChangedListener(listener);
        }
    }

    /**
     * Get the selection from the table viewer.
     * 
     * @return <code>ISelection</code> if items are selected in the table, null
     *         if not or table not accessible.
     */
    public ISelection getSelection() {
        ISelection selection = null;

        if (treeViewer != null) {
            selection = treeViewer.getSelection();
        }

        return selection;
    }

    @Override
    public void setLayout(Layout layout) {
        // Do nothing as this class will control the layout
    }

    @Override
    public void dispose() {

        removeSelectionChangedListener(this);
        super.dispose();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.jface.viewers.ISelectionChangedListener#selectionChanged(
     * org.eclipse.jface.viewers.SelectionChangedEvent)
     */
    @Override
    public void selectionChanged(SelectionChangedEvent event) {
        // Update the description
        if (event.getSelection() instanceof IStructuredSelection) {
            updateTemplatePreview((IStructuredSelection) event.getSelection());
        }
    }

    /**
     * Update the description view with description of the given selectde
     * element in the table
     * 
     * @param selection
     */
    private void updateTemplatePreview(IStructuredSelection selection) {
        Object element = selection.getFirstElement();

        if (element != null) {
            String tooltip = ""; //$NON-NLS-1$

            if (element instanceof IFragment) {
                IFragment frag = (IFragment) element;
                templateImage = frag.getLocalizedImageData();

                templateImagePreview.setImageData(templateImage);
                descPgBook.showPage(imgPreviewPage.getControl());

            } else if (element instanceof IFragmentCategory) {
                IFragmentCategory cat = (IFragmentCategory) element;

                templateImagePreview.setImageData(null);

                tooltip = cat.getName();
                String desc = cat.getDescription();
                if (desc != null && desc.length() > 0) {
                    tooltip += "\n\n" + cat.getDescription(); //$NON-NLS-1$
                }
                descPgBook.showPage(imgPreviewPage.getControl());

            } else if (element instanceof ProcessInterface) {
                ProcessInterface processInterface = (ProcessInterface) element;

                StringBuffer descBuffer = new StringBuffer();

                String formTextMsg = "<form><p> " //$NON-NLS-1$
                        + getInterfaceTemplatePreviewTitle() + "<b>%s</b></p> "; //$NON-NLS-1$

                descBuffer.append(String.format(formTextMsg,
                        processInterface.getName()));

                if (processInterface.getDescription() != null
                        && processInterface.getDescription().getValue() != null
                        && processInterface.getDescription().getValue()
                                .length() > 0) {
                    descBuffer.append(String.format(descriptionSection,
                            processInterface.getDescription().getValue()));
                }
                descBuffer.append(formEnd);
                formText.setText(descBuffer.toString(), true, false);
                descPgBook.showPage(descPage.getControl());
            } else if (element instanceof PackageTemplate) {

            } else if (element instanceof PackageTemplateChildElement) {
                PackageTemplateChildElement pkgFrag =
                        (PackageTemplateChildElement) element;
                templateImage = pkgFrag.getImageData();

                templateImagePreview.setImageData(templateImage);
                descPgBook.showPage(imgPreviewPage.getControl());
            } else {
                templateImagePreview.setImageData(null);
            }
            templateImagePreview.setToolTipText(tooltip);
        }
    }

    /**
     * XPD-7316: Allow the implementers to change the title of the Interface
     * Preview depending on the type of interface.
     * 
     * @return the Title that should be displayed for Interface Preview
     */
    protected String getInterfaceTemplatePreviewTitle() {

        return Messages.TreeWithImagePreviewComposite_DescribeInterfaceTemplate_shortmsg;
    }

    /**
     * Create the description section
     * 
     * @param parent
     * @return <code>ScrolledFormText</code>
     */
    private TemplateImageControl createTemplatePreview(Composite parent) {
        return new TemplateImageControl(parent, SWT.None);
    }

    /**
     * Create the checkbox table viewer
     * 
     * @param parent
     * @return <code>CheckboxTableViewer</code>
     */
    private TreeViewer createTreeViewer(Composite parent) {
        TreeViewer viewer;

        if (checkTreeViewer) {
            viewer = new CheckboxTreeViewer(parent, SWT.BORDER | SWT.SINGLE);
        } else {
            viewer = new TreeViewer(parent, SWT.BORDER | SWT.SINGLE);
        }

        viewer.getTree().setLayoutData(new GridData(GridData.FILL_BOTH));

        IStructuredContentProvider contentProvider =
                provider.getContentProvider();

        if (contentProvider != null) {
            viewer.setContentProvider(contentProvider);
        } else {
            throw new NullPointerException("content provider is null."); //$NON-NLS-1$
        }

        ILabelProvider labelProvider = provider.getLabelProvider();

        if (labelProvider != null) {
            viewer.setLabelProvider(labelProvider);
        } else {
            throw new NullPointerException("label provider is null."); //$NON-NLS-1$
        }

        Object input = provider.getInput();

        if (input != null) {
            viewer.setInput(input);
        } else {
            throw new NullPointerException("input is null."); //$NON-NLS-1$
        }

        return viewer;
    }

    public TemplateImageControl getTemplateImagePreview() {
        return templateImagePreview;
    }

    public void setSelection(Object selection) {
        treeViewer.setSelection(new StructuredSelection(selection), true);
    }

    public TreeViewer getTreeViewer() {
        return treeViewer;
    }

}
