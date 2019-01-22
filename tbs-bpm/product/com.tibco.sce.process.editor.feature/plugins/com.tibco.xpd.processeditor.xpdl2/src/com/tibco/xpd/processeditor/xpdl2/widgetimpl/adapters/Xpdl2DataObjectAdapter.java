/*
 ** 
 **  MODULE:             $RCSfile: $ 
 **                      $Revision: $ 
 **                      $Date: $ 
 ** 
 **  ENVIRONMENT:  Java - Platform independent 
 ** 
 **  COPYRIGHT:    (c) 2005 TIBCO Software Inc., All Rights Reserved. 
 ** 
 **  MODIFICATION HISTORY: 
 ** 
 **    $Log: $ 
 ** 
 */

package com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.common.command.AbstractCommand;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.ui.model.IWorkbenchAdapter;

import com.tibco.xpd.processeditor.xpdl2.Xpdl2ProcessEditorPlugin;
import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.processwidget.ProcessWidgetConstants;
import com.tibco.xpd.processwidget.adapters.BaseGraphicalNodeAdapter;
import com.tibco.xpd.processwidget.adapters.BaseProcessAdapter;
import com.tibco.xpd.processwidget.adapters.DataObjectAdapter;
import com.tibco.xpd.processwidget.adapters.DropObjectPopupItem;
import com.tibco.xpd.processwidget.adapters.DropTypeInfo;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdExtension.XpdExtDataObjectAttributes;
import com.tibco.xpd.xpdExtension.XpdExtensionFactory;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Artifact;
import com.tibco.xpd.xpdl2.Coordinates;
import com.tibco.xpd.xpdl2.DataObject;
import com.tibco.xpd.xpdl2.GraphicalNode;
import com.tibco.xpd.xpdl2.NodeGraphicsInfo;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;
import com.tibco.xpd.xpdl2.util.ocl.OclBasedNotifier;

/**
 * @author wzurek
 */
public class Xpdl2DataObjectAdapter extends Xpdl2BaseArtifactAdapter implements
        DataObjectAdapter {

    private OclBasedNotifier dataObjDetailListener = null;

    private OclBasedNotifier dataObjAttrListener = null;

    private static final Logger LOG = Xpdl2ProcessEditorPlugin.getDefault()
            .getLogger();

    private static class DeleteDataObjectCommand extends AbstractCommand {

        private final Artifact note;

        private CompoundCommand command;

        private final EditingDomain editingDomain;

        private final AdapterFactory adapterFactory;

        private Command removeCommand;

        public DeleteDataObjectCommand(EditingDomain editingDomain,
                Artifact note, AdapterFactory adapterFactory) {
            this.editingDomain = editingDomain;
            this.note = note;
            this.adapterFactory = adapterFactory;
            setLabel(Messages.Xpdl2DataObjectAdapter_DeleteDataObject_menu);
        }

        @Override
        protected boolean prepare() {
            removeCommand = RemoveCommand.create(editingDomain, note);
            command = new CompoundCommand();
            return removeCommand.canExecute();
        }

        @Override
        public void execute() {
            BaseGraphicalNodeAdapter fo =
                    (BaseGraphicalNodeAdapter) adapterFactory.adapt(note,
                            ProcessWidgetConstants.ADAPTER_TYPE);

            addAndExecute(fo.getSourceAssociations(), command);
            addAndExecute(fo.getTargetAssociations(), command);

            command.appendAndExecute(RemoveCommand.create(editingDomain, note));
        }

        private void addAndExecute(List list, CompoundCommand cmd) {
            for (Iterator iter = list.iterator(); iter.hasNext();) {
                EObject eo = (EObject) iter.next();
                BaseProcessAdapter ad =
                        (BaseProcessAdapter) adapterFactory.adapt(eo,
                                ProcessWidgetConstants.ADAPTER_TYPE);
                cmd.appendAndExecute(ad.getDeleteCommand(editingDomain));
            }
        }

        @Override
        public void redo() {
            command.redo();
        }

        @Override
        public void undo() {
            command.undo();
        }
    }

    @Override
    public Command getDeleteCommand(EditingDomain editingDomain) {
        Artifact note = getArtifact();
        return new DeleteDataObjectCommand(editingDomain, note,
                getAdapterFactory());
    }

    @Override
    public String getId() {
        return getArtifact().getId();
    }

    @Override
    public String getName() {
        String name = null;

        if (getArtifact() != null) {
            name = Xpdl2ModelUtil.getDisplayNameOrName(getArtifact());
        }

        if (name == null) {
            name = ""; //$NON-NLS-1$
        }

        return name;
    }

    @Override
    public String getTokenName() {
        String name = null;

        if (getArtifact() != null) {
            name = getArtifact().getName();
        }

        if (name == null) {
            name = ""; //$NON-NLS-1$
        }

        return name;
    }

    @Override
    public Command getSetNameCommand(EditingDomain editingDomain, String newName) {
        CompoundCommand cmd = new CompoundCommand();
        cmd.setLabel(Messages.Xpdl2DataObjectAdapter_SetNameDataObject_menu);
        cmd.append(Xpdl2ModelUtil.getSetOtherAttributeCommand(editingDomain,
                getArtifact(),
                XpdExtensionPackage.eINSTANCE.getDocumentRoot_DisplayName(),
                newName));

        return cmd;
    }

    @Override
    public String getFillColor() {
        NodeGraphicsInfo gi = getGraphicalInfo(getArtifact());
        if (gi != null) {
            return gi.getFillColor();
        }
        return null;
    }

    @Override
    public Command getSetFillColorCommand(EditingDomain editingDomain,
            String newColor) {

        CompoundCommand cmd = new CompoundCommand();
        GraphicalNode act = getGraphicalNode();

        NodeGraphicsInfo gInfo =
                Xpdl2ModelUtil.getOrCreateNodeGraphicsInfo(act,
                        editingDomain,
                        cmd);

        cmd.append(SetCommand.create(editingDomain,
                gInfo,
                Xpdl2Package.eINSTANCE.getNodeGraphicsInfo_FillColor(),
                newColor));

        cmd.setLabel(Messages.Xpdl2DataObjectAdapter_SetFillColorDataObject_menu);

        return cmd;
    }

    @Override
    public String getLineColor() {
        NodeGraphicsInfo gi = getGraphicalInfo(getArtifact());
        if (gi != null) {
            return gi.getBorderColor();
        }
        return null;
    }

    @Override
    public Command getSetLineColorCommand(EditingDomain editingDomain,
            String newColor) {
        CompoundCommand cmd = new CompoundCommand();
        GraphicalNode act = getGraphicalNode();

        NodeGraphicsInfo gInfo =
                Xpdl2ModelUtil.getOrCreateNodeGraphicsInfo(act,
                        editingDomain,
                        cmd);

        cmd.append(SetCommand.create(editingDomain,
                gInfo,
                Xpdl2Package.eINSTANCE.getNodeGraphicsInfo_BorderColor(),
                newColor));

        cmd.setLabel(Messages.Xpdl2DataObjectAdapter_SetLineDataObject_menu);
        return cmd;
    }

    /**
     * Get the artifact element representing this data object
     * 
     * @return
     */
    private Artifact getArtifact() {
        return (Artifact) getTarget();
    }

    /**
     * Get the data object definition detail.
     * 
     * @return
     */
    public DataObject getDataObject() {
        if (getArtifact() != null) {
            return getArtifact().getDataObject();
        }
        return null;
    }

    /**
     * Get the data object extended definition detail.
     * 
     * @return
     */
    private XpdExtDataObjectAttributes getXpdExtDataObjectAttributes() {
        XpdExtDataObjectAttributes extData = null;

        DataObject dataObj = getDataObject();
        if (dataObj != null) {
            extData =
                    (XpdExtDataObjectAttributes) Xpdl2ModelUtil
                            .getOtherElement(dataObj,
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_DataObjectAttributes());
        }

        return extData;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.processwidget.adapters.DataObjectAdapter#getExternalReference
     * ()
     */
    @Override
    public String getExternalReference() {
        String extRef = null;

        XpdExtDataObjectAttributes extData = getXpdExtDataObjectAttributes();

        if (extData != null) {
            extRef = extData.getExternalReference();
        }

        if (extRef == null) {
            extRef = ""; //$NON-NLS-1$
        }
        return extRef;
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.processwidget.adapters.DataObjectAdapter#
     * getExternalReferenceImage()
     */
    @Override
    public ImageData getExternalReferenceImage() {
        ImageData imgData = null;

        imgData = internalGetExtRefImage(getExternalReference());

        return imgData;
    }

    /**
     * @param imgData
     * @return
     */
    private ImageData internalGetExtRefImage(String externalRef) {
        ImageData imgData = null;

        String extRef = externalRef.trim();

        WorkingCopy wc = WorkingCopyUtil.getWorkingCopyFor(getArtifact());

        if (wc != null) {
            if (!wc.getEclipseResources().isEmpty()) {
                IProject project = wc.getEclipseResources().get(0).getProject();

                if (project != null) {
                    IResource resource = project.findMember(extRef);

                    if (resource != null && resource.exists()
                            && resource instanceof IFile) {
                        IFile file = (IFile) resource;

                        try {
                            imgData = new ImageData(file.getContents());

                        } catch (SWTException e) {
                            if (e.code == SWT.ERROR_UNSUPPORTED_FORMAT) {
                                // This is not an image file so get the icon of
                                // the file and display that instead
                                if (file instanceof IAdaptable) {
                                    IWorkbenchAdapter adapter =
                                            (IWorkbenchAdapter) ((IAdaptable) file)
                                                    .getAdapter(IWorkbenchAdapter.class);

                                    if (adapter != null) {
                                        ImageDescriptor descriptor =
                                                adapter.getImageDescriptor(file);

                                        if (descriptor != null) {
                                            imgData = descriptor.getImageData();
                                        }
                                    }
                                }

                            } else {
                                e.printStackTrace();
                            }
                        } catch (CoreException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return imgData;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.processwidget.adapters.DataObjectAdapter#getState()
     */
    @Override
    public String getState() {
        String state = null;

        DataObject dataObj = getDataObject();
        if (dataObj != null) {
            state = dataObj.getState();
        }

        if (state == null) {
            state = ""; //$NON-NLS-1$
        }

        return state;
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.
     * Xpdl2BaseGraphicalNodeAdapter
     * #setTarget(org.eclipse.emf.common.notify.Notifier)
     */
    @Override
    public void setTarget(Notifier newTarget) {
        if (getTarget() != null) {
            if (dataObjDetailListener != null) {
                dataObjDetailListener.getTarget().eAdapters()
                        .remove(dataObjDetailListener);
            }
            if (dataObjAttrListener != null) {
                dataObjAttrListener.getTarget().eAdapters()
                        .remove(dataObjAttrListener);
            }

        }

        super.setTarget(newTarget);

        if (getTarget() != null) {
            if (dataObjDetailListener == null) {
                dataObjDetailListener = new OclBasedNotifier("self.dataObject", //$NON-NLS-1$
                        Xpdl2Package.eINSTANCE.getArtifact(), this);
            }

            getTarget().eAdapters().add(dataObjDetailListener);

            if (dataObjAttrListener == null) {
                dataObjAttrListener =
                        new OclBasedNotifier(
                                "self.dataObject.getOtherElement('dataObjectAttributes')", //$NON-NLS-1$
                                Xpdl2Package.eINSTANCE.getArtifact(), this);
            }

            getTarget().eAdapters().add(dataObjAttrListener);

        }
    }

    public static CompoundCommand getSetExternalReferenceCommand(
            EditingDomain editingDomain, String extRef, Artifact artifact) {
        CompoundCommand cmd = new CompoundCommand();

        XpdExtDataObjectAttributes extData =
                getOrCreateXpdExtDataObjectAttributes(editingDomain,
                        cmd,
                        artifact);

        cmd.append(SetCommand.create(editingDomain,
                extData,
                XpdExtensionPackage.eINSTANCE
                        .getXpdExtDataObjectAttributes_ExternalReference(),
                extRef));

        // When setting the external reference then also reset the size of the
        // data object
        Dimension newSize =
                new Dimension(ProcessWidgetConstants.DATAOBJECT_WIDTH_SIZE,
                        ProcessWidgetConstants.DATAOBJECT_HEIGHT_SIZE);

        if (extRef != null && extRef.length() > 0) {
            ImageData imgData = internalGetExtRefImage(extRef, artifact);

            if (imgData != null) {
                // Limit initial size to a reasonable amount.
                Dimension sz = new Dimension(300, 200); // Max size.

                double imageScaleX = (double) sz.width / (double) imgData.width;
                double imageScaleY =
                        (double) sz.height / (double) imgData.height;

                double imgScale;

                if (imageScaleY < imageScaleX) {
                    imgScale = imageScaleY;
                } else {
                    imgScale = imageScaleX;
                }

                if (imgScale > 1.0) {
                    imgScale = 1.0;
                }

                int finalWidth =
                        (int) (imgData.width * imgScale)
                                + (ProcessWidgetConstants.DATAOBJECT_CORNERBEND_SIZE * 2);
                int finalHeight =
                        (int) (imgData.height * imgScale)
                                + (ProcessWidgetConstants.DATAOBJECT_CORNERBEND_SIZE * 2);

                if (finalWidth > newSize.width || finalHeight > newSize.height) {
                    newSize.width = finalWidth;
                    newSize.height = finalHeight;
                }
            }
        }

        cmd.append(getSetLocationCommand(editingDomain,
                getLocation(artifact),
                newSize,
                artifact));

        return cmd;
    }

    /**
     * @param imgData
     * @return
     */
    public static ImageData internalGetExtRefImage(String externalRef,
            Artifact artifact) {
        ImageData imgData = null;

        String extRef = externalRef.trim();

        WorkingCopy wc = WorkingCopyUtil.getWorkingCopyFor(artifact);

        if (wc != null) {
            if (!wc.getEclipseResources().isEmpty()) {
                IProject project = wc.getEclipseResources().get(0).getProject();

                if (project != null) {
                    IResource resource = project.findMember(extRef);

                    if (resource != null && resource.exists()
                            && resource instanceof IFile) {
                        IFile file = (IFile) resource;

                        try {
                            imgData = new ImageData(file.getContents());

                        } catch (SWTException e) {
                            if (e.code == SWT.ERROR_UNSUPPORTED_FORMAT) {
                                // This is not an image file so get the icon of
                                // the file and display that instead
                                if (file instanceof IAdaptable) {
                                    IWorkbenchAdapter adapter =
                                            (IWorkbenchAdapter) ((IAdaptable) file)
                                                    .getAdapter(IWorkbenchAdapter.class);

                                    if (adapter != null) {
                                        ImageDescriptor descriptor =
                                                adapter.getImageDescriptor(file);

                                        if (descriptor != null) {
                                            imgData = descriptor.getImageData();
                                        }
                                    }
                                }

                            } else {
                                LOG.error(e,
                                        "DataObject External Reference not am image or file with icon"); //$NON-NLS-1$
                            }
                        } catch (CoreException e) {
                            LOG.error(e);
                        }
                    }
                }
            }
        }
        return imgData;
    }

    private static DataObject getDataObject(Artifact art) {
        if (art != null) {
            return art.getDataObject();
        }
        return null;
    }

    private static Point getLocation(Artifact artifact) {
        NodeGraphicsInfo gi = Xpdl2ModelUtil.getNodeGraphicsInfo(artifact);
        if (gi != null) {
            Coordinates coords = gi.getCoordinates();
            if (coords != null) {
                return new Point(coords.getXCoordinate(),
                        coords.getYCoordinate());
            }
        }
        return new Point(10, 10);
    }

    /**
     * Get the data object definition detail and, if it does not already exist,
     * append a command to create it to the given command.
     * 
     * @param editingDomain
     * @param cmd
     * @return
     */
    public static DataObject getOrCreateDataObject(EditingDomain editingDomain,
            CompoundCommand cmd, Artifact art) {
        DataObject dataObj = getDataObject(art);

        if (dataObj == null) {
            dataObj = Xpdl2Factory.eINSTANCE.createDataObject();

            // dataObj.setRequiredForStart(false);
            // dataObj.setProducedAtCompletion(false);

            cmd.append(SetCommand.create(editingDomain,
                    art,
                    Xpdl2Package.eINSTANCE.getArtifact_DataObject(),
                    dataObj));
        }

        return dataObj;
    }

    /**
     * Get the data object extended definition detail and, if it does not
     * already exist, append a command to create it to the given command.
     * 
     * @param editingDomain
     * @param cmd
     * @return
     */
    public static XpdExtDataObjectAttributes getOrCreateXpdExtDataObjectAttributes(
            EditingDomain editingDomain, CompoundCommand cmd, Artifact art) {
        XpdExtDataObjectAttributes extData = null;
        if (getDataObject(art) != null) {
            extData = getXpdExtDataObjectAttributes(getDataObject(art));
        }

        if (extData == null) {
            DataObject dataObj = getOrCreateDataObject(editingDomain, cmd, art);

            extData =
                    XpdExtensionFactory.eINSTANCE
                            .createXpdExtDataObjectAttributes();

            cmd.append(Xpdl2ModelUtil.getSetOtherElementCommand(editingDomain,
                    dataObj,
                    XpdExtensionPackage.eINSTANCE
                            .getDocumentRoot_DataObjectAttributes(),
                    extData));
        }

        return extData;
    }

    private static Command getSetLocationCommand(EditingDomain ed, Point loc,
            Dimension dim, Artifact art) {
        CompoundCommand cmd = new CompoundCommand();

        NodeGraphicsInfo gInfo =
                Xpdl2ModelUtil.getOrCreateNodeGraphicsInfo(art, ed, cmd);

        cmd.append(SetCommand.create(ed,
                gInfo,
                Xpdl2Package.eINSTANCE.getNodeGraphicsInfo_Coordinates(),
                Xpdl2Factory.eINSTANCE.createCoordinates(loc.x, loc.y)));

        cmd.append(SetCommand.create(ed,
                gInfo,
                Xpdl2Package.eINSTANCE.getNodeGraphicsInfo_Height(),
                new Double(dim.height)));
        cmd.append(SetCommand.create(ed,
                gInfo,
                Xpdl2Package.eINSTANCE.getNodeGraphicsInfo_Width(),
                new Double(dim.width)));

        return cmd;
    }

    /**
     * Get the data object extended definition detail.
     * 
     * @return
     */
    public static XpdExtDataObjectAttributes getXpdExtDataObjectAttributes(
            DataObject dataObj) {
        XpdExtDataObjectAttributes extData = null;

        if (dataObj != null) {
            extData =
                    (XpdExtDataObjectAttributes) Xpdl2ModelUtil
                            .getOtherElement(dataObj,
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_DataObjectAttributes());
        }

        return extData;
    }

    @Override
    public DropTypeInfo getDropTypeInfo(List<Object> dropObjects,
            Point location, Object actualTarget, int userRequestedDropType) {
        DropTypeInfo dropType = new DropTypeInfo(DND.DROP_NONE);

        //
        // Allow drop of an IFile object onto data object (sets external
        // reference.
        //
        if (dropObjects.size() == 1) {

            Object dropObj = dropObjects.get(0);

            IFile iFile = getDropObjectIFile(dropObj);

            if (iFile != null) {
                dropType.setDndDropType(DND.DROP_MOVE);
            }
        }

        //
        // If we don't have anything that handles drop type then all other
        // contributers to have a go.
        if (dropType.getDndDropType() == DND.DROP_NONE) {
            dropType =
                    super.getDropTypeInfo(dropObjects,
                            location,
                            actualTarget,
                            userRequestedDropType);
        }
        return dropType;
    }

    @Override
    public List<DropObjectPopupItem> getDropPopupItems(
            EditingDomain editingDomain, List<Object> dropObjects,
            Point location, Object actualTarget, int userRequestedDropType) {

        List<DropObjectPopupItem> returnPopupItems =
                new ArrayList<DropObjectPopupItem>();

        //
        // Allow drop of an IResource object onto data object (sets external
        // reference.
        //
        if (dropObjects.size() == 1) {

            Object dropObj = dropObjects.get(0);

            IFile iFile = getDropObjectIFile(dropObj);

            if (iFile != null) {
                String extRef = iFile.getProjectRelativePath().toString();

                returnPopupItems
                        .add(DropObjectPopupItem
                                .createCommandItem(getSetExternalReferenceCommand(editingDomain,
                                        extRef,
                                        getArtifact()),
                                        Messages.Xpdl2DataObjectAdapter_SetExtRef_menu,
                                        null));
            }
        }

        //
        // Call super to allow any other external contributions of drop popup
        // items.
        List<DropObjectPopupItem> otherPopupItems =
                super.getDropPopupItems(editingDomain,
                        dropObjects,
                        location,
                        actualTarget,
                        userRequestedDropType);

        if (otherPopupItems != null && otherPopupItems.size() > 0) {
            returnPopupItems.addAll(otherPopupItems);
        }

        return returnPopupItems;
    }

    /**
     * @param dropObj
     * @return
     */
    private IFile getDropObjectIFile(Object dropObj) {
        IFile iRes = null;
        if (dropObj instanceof IFile) {
            iRes = (IFile) dropObj;
        } else if (dropObj instanceof IAdaptable) {
            iRes = (IFile) ((IAdaptable) dropObj).getAdapter(IFile.class);
        }
        return iRes;
    }

}
