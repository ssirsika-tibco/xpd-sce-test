/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.fragments.wizard;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.jface.viewers.DecoratingLabelProvider;
import org.eclipse.jface.viewers.ILabelDecorator;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.ViewerFilter;
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
import org.eclipse.ui.forms.widgets.FormText;
import org.eclipse.ui.part.Page;
import org.eclipse.ui.part.PageBook;

import com.tibco.xpd.fragments.IFragment;
import com.tibco.xpd.fragments.IFragmentCategory;
import com.tibco.xpd.fragments.internal.FragmentsManager;
import com.tibco.xpd.fragments.internal.Messages;
import com.tibco.xpd.fragments.internal.view.FragmentRootViewerFilter;

/**
 * Composite control containing a Tree viewer and another composite to display
 * images.
 * 
 * @author rsomayaj
 * 
 */
public class TreeWithImagePreviewComposite extends Composite implements
        ISelectionChangedListener {

    private Image templateImage;

    private class TemplateImageControl extends ZoomImageControl {
        private Image image = null;
        private String description;
        private Image scaledImage = null;

        public TemplateImageControl(Composite parent, int style) {
            super(parent, style, true, null);
        }

        @Override
        protected String getDescription() {
            return description;
        }

        @Override
        protected Image createImage(Dimension sizeHint) {
            // Dispose the old scaled image if there is one.
            disposeScaledImage();

            // If not asked to scale or no image then return the original image.
            if (sizeHint == null || image == null) {
                return image;
            }

            // Maintain the original aspect ratio
            // base scale on the largest side.
            ImageData imgData = image.getImageData();

            double imageScaleX = (double) sizeHint.width
                    / (double) imgData.width;
            double imageScaleY = (double) sizeHint.height
                    / (double) imgData.height;

            double imgScale;

            if (imageScaleY < imageScaleX) {
                imgScale = imageScaleY;
            } else {
                imgScale = imageScaleX;
            }

            if (imgScale > 1.0) {
                imgScale = 1.0;
            }

            int finalWidth = (int) ((double) imgData.width * imgScale);
            int finalHeight = (int) ((double) imgData.height * imgScale);

            // 
            // Create a new image of the required size.
            scaledImage = new Image(Display.getDefault(), sizeHint.width,
                    sizeHint.height);
            GC scaleGC = new GC(scaledImage);

            // Windows image looks rubbish without interpolation=high
            // Linux looks rubbish with interpolation = high.
            if (System.getProperty("os.name").toUpperCase().startsWith( //$NON-NLS-1$
                    "WIN")) {//$NON-NLS-1$
                scaleGC.setInterpolation(SWT.HIGH);
            }

            scaleGC.drawImage(image, 0, 0, imgData.width, imgData.height,
                    (sizeHint.width / 2) - (finalWidth / 2),
                    (sizeHint.height / 2) - (finalHeight / 2), finalWidth,
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

        public void setImage(Image image, String description) {
            this.image = image;
            this.description = description;
            disposeScaledImage();

            refresh(true);
        }

        @Override
        protected void finalize() throws Throwable {
            // System.out.println("Finalizing image preview."); //$NON-NLS-1$
            dispose();
            super.finalize();
        }
    }

    private TemplateImageControl templateImagePreview;

    private TreeViewer treeViewer;

    private PageBook descPgBook;

    private ImagePage imgPreviewPage;

    private DescriptionPage descPage;

    private FormText formText;

    private final Map<IFragment, Image> images;

    private final String[] providerIds;

    /**
     * Constructor for the table with description composite.
     * 
     * @param parent
     *            The parent <code>Composite</code>
     * @param providerIds
     * @param style
     *            <code>{@link SWT#NONE}</code> for a table,
     *            <code>{@link SWT#CHECK}</code> for a checkbox table.
     */
    public TreeWithImagePreviewComposite(Composite parent,
            String[] providerIds, int style) {
        super(parent, style);
        this.providerIds = providerIds;

        images = new HashMap<IFragment, Image>();

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
        if (treeViewer.getTree().getItemCount() > 0) {
            treeViewer.getTree().setSelection(treeViewer.getTree().getItem(0));
        }

        ISelection selection = treeViewer.getSelection();
        if (selection instanceof IStructuredSelection) {
            updateTemplatePreview((IStructuredSelection) selection);
        }
        // Add this class as the selection listener of the table viewer
        addSelectionChangedListener(this);
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

        for (Image img : images.values()) {
            img.dispose();
        }
        images.clear();

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
                templateImage = images.get(frag);

                if (templateImage == null) {
                    templateImage = frag.createImage(getDisplay());
                    if (templateImage != null) {
                        images.put(frag, templateImage);
                    }
                }

                templateImagePreview.setImage(templateImage, frag
                        .getDescriptionLabel());

                tooltip = frag.getName();
                String desc = frag.getDescription();
                if (desc != null && desc.length() > 0) {
                    tooltip += "\n\n" + frag.getDescription(); //$NON-NLS-1$
                }
                descPgBook.showPage(imgPreviewPage.getControl());

            } else if (element instanceof IFragmentCategory) {
                IFragmentCategory cat = (IFragmentCategory) element;

                templateImagePreview.setImage(null, null);

                tooltip = cat.getName();
                String desc = cat.getDescription();
                if (desc != null && desc.length() > 0) {
                    tooltip += "\n\n" + desc; //$NON-NLS-1$

                    formText.setText(String.format("<form>%s</form>", desc), //$NON-NLS-1$
                            true, false);
                } else {
                    String msg = String
                            .format(
                                    Messages.TreeWithImagePreviewComposite_category_default_shortdesc,
                                    cat.getName());
                    formText.setText("<form>" + msg + "</form>", true, false); //$NON-NLS-1$ //$NON-NLS-2$
                }
                descPgBook.showPage(descPage.getControl());
            }

            templateImagePreview.setToolTipText(tooltip);
        }
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
        TreeViewer viewer = new TreeViewer(parent, SWT.BORDER | SWT.SINGLE);
        FragmentsManager manager = FragmentsManager.getInstance();

        viewer.getTree().setLayoutData(new GridData(GridData.FILL_BOTH));
        viewer.setContentProvider(manager.getFragmentsContentProvider());
        ILabelDecorator decorator = manager.getFragmentsLabelDecorator();
        ILabelProvider provider = manager.getFragmentsLabelProvider();

        if (decorator != null) {
            provider = new DecoratingLabelProvider(provider, decorator);
        }
        viewer.setLabelProvider(provider);
        viewer.setComparator(manager.getFragmentsViewerComparator());
        viewer.setFilters(new ViewerFilter[] { new FragmentRootViewerFilter(
                providerIds) });

        try {
            viewer.setInput(manager.getFragmentsContainer());
        } catch (CoreException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return viewer;
    }

    public TemplateImageControl getTemplateImagePreview() {
        return templateImagePreview;
    }

    public void setSelection(Object selection) {
        treeViewer.setSelection(new StructuredSelection(selection), true);
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

    public TreeViewer getTreeViewer() {
        return treeViewer;
    }

}
