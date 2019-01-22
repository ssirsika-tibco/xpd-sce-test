/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.bizassets.resources.wizard;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

import com.tibco.xpd.bizassets.resources.BusinessAssetsConstants;
import com.tibco.xpd.bizassets.resources.BusinessassetsPlugin;
import com.tibco.xpd.bizassets.resources.internal.Messages;

/**
 * @author nwilson
 */
public class QualityArchiveProjectContentsPage extends WizardPage {

    private TableViewer table;

    /**
     * @param pageName
     */
    protected QualityArchiveProjectContentsPage() {
        super(Messages.QualityArchiveProjectContentsPage_projectContentsPageName);
        setTitle(Messages.QualityArchiveProjectContentsPage_projectContentsTitle);
        setMessage(Messages.QualityArchiveProjectContentsPage_projectContentsMessage);
        setPageComplete(true);
    }

    /**
     * @param parent
     * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
     */
    public void createControl(Composite parent) {
        table = new TableViewer(parent, SWT.FULL_SELECTION);
        table.setContentProvider(new QualityArchiveProjectContentProvider());
        table.setLabelProvider(new QualityArchiveProjectLabelProvider());
        table.setInput(this);
        table.getTable().select(0);

        setControl(table.getTable());
    }

    public String getSelectedFile() {
        String file = null;
        ISelection selection = table.getSelection();
        if (selection instanceof IStructuredSelection) {
            IStructuredSelection structured = (IStructuredSelection) selection;
            if (structured.size() == 1) {
                Object item = structured.getFirstElement();
                if (item instanceof QualityArchiveContents) {
                    QualityArchiveContents contents =
                            (QualityArchiveContents) item;
                    file = contents.getFile();
                }
            }
        }
        return file;
    }

    class QualityArchiveProjectContentProvider implements
            IStructuredContentProvider {

        private Object[] elements;

        public QualityArchiveProjectContentProvider() {
            elements = new Object[2];
            elements[0] =
                    new QualityArchiveContents(Messages.QualityArchiveProjectContentsPage_emptyProject, null,
                            PlatformUI.getWorkbench().getSharedImages()
                                    .getImage(ISharedImages.IMG_OBJ_FOLDER));
            elements[1] =
                    new QualityArchiveContents(
                            Messages.QualityArchiveProjectContentsPage_prince2Project,
                            BusinessAssetsConstants.PRINCE2_TEMPLATE_JAR_LOCATION,
                            BusinessassetsPlugin.getDefault()
                                    .getImageRegistry().get(
                                            BusinessAssetsConstants.IMG_FOLDER));
        }

        /**
         * @param inputElement
         * @return
         * @see org.eclipse.jface.viewers.IStructuredContentProvider#getElements(java.lang.Object)
         */
        public Object[] getElements(Object inputElement) {
            return elements;
        }

        /**
         * 
         * @see org.eclipse.jface.viewers.IContentProvider#dispose()
         */
        public void dispose() {
        }

        /**
         * @param viewer
         * @param oldInput
         * @param newInput
         * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer,
         *      java.lang.Object, java.lang.Object)
         */
        public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
        }

    }

    class QualityArchiveProjectLabelProvider implements ILabelProvider {

        /**
         * @param element
         * @return
         * @see org.eclipse.jface.viewers.ILabelProvider#getImage(java.lang.Object)
         */
        public Image getImage(Object element) {
            Image image = null;
            if (element instanceof QualityArchiveContents) {
                QualityArchiveContents contents =
                        (QualityArchiveContents) element;
                image = contents.getImage();
            }
            return image;
        }

        /**
         * @param element
         * @return
         * @see org.eclipse.jface.viewers.ILabelProvider#getText(java.lang.Object)
         */
        public String getText(Object element) {
            String text = ""; //$NON-NLS-1$
            if (element instanceof QualityArchiveContents) {
                QualityArchiveContents contents =
                        (QualityArchiveContents) element;
                text = contents.getName();
            }
            return text;
        }

        /**
         * @param listener
         * @see org.eclipse.jface.viewers.IBaseLabelProvider#addListener(org.eclipse.jface.viewers.ILabelProviderListener)
         */
        public void addListener(ILabelProviderListener listener) {
        }

        /**
         * 
         * @see org.eclipse.jface.viewers.IBaseLabelProvider#dispose()
         */
        public void dispose() {
        }

        /**
         * @param element
         * @param property
         * @return
         * @see org.eclipse.jface.viewers.IBaseLabelProvider#isLabelProperty(java.lang.Object,
         *      java.lang.String)
         */
        public boolean isLabelProperty(Object element, String property) {
            return false;
        }

        /**
         * @param listener
         * @see org.eclipse.jface.viewers.IBaseLabelProvider#removeListener(org.eclipse.jface.viewers.ILabelProviderListener)
         */
        public void removeListener(ILabelProviderListener listener) {
        }

    }

    class QualityArchiveContents {

        private String name;

        private String file;

        private Image image;

        public QualityArchiveContents(String name, String file, Image image) {
            this.name = name;
            this.file = file;
            this.image = image;
        }

        public String getName() {
            return name;
        }

        public String getFile() {
            return file;
        }

        public Image getImage() {
            return image;
        }
    }
}
