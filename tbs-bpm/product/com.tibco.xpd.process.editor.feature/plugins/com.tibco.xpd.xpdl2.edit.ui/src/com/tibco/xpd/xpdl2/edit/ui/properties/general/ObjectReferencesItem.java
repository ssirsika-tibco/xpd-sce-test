package com.tibco.xpd.xpdl2.edit.ui.properties.general;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IWorkbenchSite;

import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.ui.projectexplorer.NavigatorUtil;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.BasicType;
import com.tibco.xpd.xpdl2.BasicTypeType;
import com.tibco.xpd.xpdl2.DataField;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.ModeType;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.TriggerType;
import com.tibco.xpd.xpdl2.TypeDeclaration;
import com.tibco.xpd.xpdl2.edit.ui.Xpdl2UiPlugin;
import com.tibco.xpd.xpdl2.edit.util.RevealProcessDiagramEObject;
import com.tibco.xpd.xpdl2.util.LocalPackageProcessInterfaceUtil;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * This class represents an item in the ObjectReferencesSection's tree view of
 * objects that reference a given object.
 * <p>
 * The item is an object that contains reference(s) to the object for which tree
 * view is being displayed.
 * 
 * @author aallway
 * 
 */
public abstract class ObjectReferencesItem {

    String name = ""; //$NON-NLS-1$

    Image image = null;

    Object modelObject = null;

    ObjectReferencesFolder parentFolder = null;

    private ObjectReferencesItem(String name, Image image, Object modelObject) {
        if (name != null) {
            this.name = name;
        }

        this.image = image;
        this.modelObject = modelObject;
    }

    public String getName() {
        return name;
    }

    public Image getImage() {
        return image;
    }

    public ObjectReferencesFolder getParentFolder() {
        return parentFolder;
    }

    /**
     * Sub-class should override to say whether can navigate and select
     * referencing object.
     * 
     * @return
     */
    protected abstract boolean canGoto();

    /**
     * Sub-class should override this to perform goto.
     * 
     * @return
     */
    protected abstract boolean gotoItem();

    /**
     * <b>internal use only!
     * 
     * @param folder
     */
    void _setParentFolder(ObjectReferencesFolder folder) {
        parentFolder = folder;
    }

    /**
     * Create an object references list item for given activity.
     * 
     * @param activity
     * @param workbenchSite
     * @return
     */
    public static ObjectReferencesItem create(Activity activity,
            final IWorkbenchSite workbenchSite) {
        String name = Xpdl2ModelUtil.getDisplayNameOrName(activity);

        if (name == null || name.length() == 0) {
            com.tibco.xpd.xpdExtension.StartMethod startMethod =
                    LocalPackageProcessInterfaceUtil
                            .getImplementedStartMethod(activity);
            if (startMethod != null) {
                name = Xpdl2ModelUtil.getDisplayNameOrName(startMethod);
            } else {
                com.tibco.xpd.xpdExtension.IntermediateMethod interMethod =
                        LocalPackageProcessInterfaceUtil
                                .getImplementedIntermediateMethod(activity);
                if (interMethod != null) {
                    name = Xpdl2ModelUtil.getDisplayNameOrName(interMethod);
                }
            }
        }

        Image img = WorkingCopyUtil.getImage(activity);

        ObjectReferencesItem item =
                new ObjectReferencesItem(name, img, activity) {

                    @Override
                    protected boolean gotoItem() {
                        return RevealProcessDiagramEObject
                                .revealEObject(workbenchSite,
                                        (EObject) modelObject,
                                        true);
                    }

                    @Override
                    protected boolean canGoto() {
                        return true;
                    }

                };

        return item;
    }

    /**
     * Create an object references list item for given transition.
     * 
     * @param activity
     * @param workbenchSite
     * @return
     */
    public static ObjectReferencesItem create(Transition transition,
            final IWorkbenchSite workbenchSite) {
        String name = Xpdl2ModelUtil.getDisplayNameOrName(transition);

        Image img = null;
        ImageRegistry ir = Xpdl2UiPlugin.getDefault().getImageRegistry();
        img = ir.get(Xpdl2UiPlugin.IMG_FLOW_CONDITIONAL);

        ObjectReferencesItem item =
                new ObjectReferencesItem(name, img, transition) {

                    @Override
                    protected boolean gotoItem() {
                        return RevealProcessDiagramEObject
                                .revealEObject(workbenchSite,
                                        (EObject) modelObject,
                                        true);
                    }

                    @Override
                    protected boolean canGoto() {
                        return true;
                    }

                };

        return item;
    }

    /**
     * Create an object references list item for given data field.
     * 
     * @param activity
     * @param workbenchSite
     * @return
     */
    public static ObjectReferencesItem create(DataField dataField) {
        String name = Xpdl2ModelUtil.getDisplayNameOrName(dataField);

        Image img = null;
        ImageRegistry ir = Xpdl2UiPlugin.getDefault().getImageRegistry();

        BasicTypeType type = null;
        if (null != dataField.getDataType()) {
            if (dataField.getDataType() instanceof BasicType) {
                BasicType basicType = (BasicType) dataField.getDataType();
                type = basicType.getType();
            }
        }
        if (BasicTypeType.PERFORMER_LITERAL.equals(type)) {
            img = ir.get(Xpdl2UiPlugin.IMG_DATA_FIELD_PERFORMER);
        } else {
            img = ir.get(Xpdl2UiPlugin.IMG_DATAFIELD_DECLAREDTYPE);
        }

        ObjectReferencesItem item =
                new ObjectReferencesItem(name, img, dataField) {

                    @Override
                    protected boolean gotoItem() {
                        return NavigatorUtil
                                .setProjectExplorerSelection(new StructuredSelection(
                                        modelObject));
                    }

                    @Override
                    protected boolean canGoto() {
                        return true;
                    }

                };

        return item;
    }

    /**
     * Create an object references list item for given FormalParameter.
     * 
     * @param activity
     * @param workbenchSite
     * @return
     */
    public static ObjectReferencesItem create(FormalParameter formalParam) {
        String name = Xpdl2ModelUtil.getDisplayNameOrName(formalParam);

        Image img = null;
        ImageRegistry ir = Xpdl2UiPlugin.getDefault().getImageRegistry();

        if (formalParam.eContainer() instanceof ProcessInterface) {
            if (ModeType.IN_LITERAL.equals(formalParam.getMode())) {
                img = ir.get(Xpdl2UiPlugin.IMG_INTERFACE_PARAM_IN);
            } else if (ModeType.OUT_LITERAL.equals(formalParam.getMode())) {
                img = ir.get(Xpdl2UiPlugin.IMG_INTERFACE_PARAM_OUT);
            } else {
                img = ir.get(Xpdl2UiPlugin.IMG_INTERFACE_PARAM_INOUT);
            }
        } else {
            if (ModeType.IN_LITERAL.equals(formalParam.getMode())) {
                img = ir.get(Xpdl2UiPlugin.IMG_FORMALPARAM_IN);
            } else if (ModeType.OUT_LITERAL.equals(formalParam.getMode())) {
                img = ir.get(Xpdl2UiPlugin.IMG_FORMALPARAM_OUT);
            } else {
                img = ir.get(Xpdl2UiPlugin.IMG_FORMALPARAM_INOUT);
            }
        }

        ObjectReferencesItem item =
                new ObjectReferencesItem(name, img, formalParam) {

                    @Override
                    protected boolean gotoItem() {
                        return NavigatorUtil
                                .setProjectExplorerSelection(new StructuredSelection(
                                        modelObject));
                    }

                    @Override
                    protected boolean canGoto() {
                        return true;
                    }

                };

        return item;
    }

    /**
     * Create an object references list item for given data field.
     * 
     * @param activity
     * @param workbenchSite
     * @return
     */
    public static ObjectReferencesItem create(TypeDeclaration typeDeclaration) {
        String name = Xpdl2ModelUtil.getDisplayNameOrName(typeDeclaration);

        Image img = null;
        ImageRegistry ir = Xpdl2UiPlugin.getDefault().getImageRegistry();
        img = ir.get(Xpdl2UiPlugin.IMG_TYPEDECLARATION_DECLAREDTYPE);

        ObjectReferencesItem item =
                new ObjectReferencesItem(name, img, typeDeclaration) {

                    @Override
                    protected boolean gotoItem() {
                        return NavigatorUtil
                                .setProjectExplorerSelection(new StructuredSelection(
                                        modelObject));
                    }

                    @Override
                    protected boolean canGoto() {
                        return true;
                    }

                };

        return item;

    }

    /**
     * Create an object references list item for given process field.
     * 
     * @param process
     * @return
     */
    public static ObjectReferencesItem create(Process process) {
        String name = Xpdl2ModelUtil.getDisplayNameOrName(process);

        Image img = null;
        ImageRegistry ir = Xpdl2UiPlugin.getDefault().getImageRegistry();
        img = WorkingCopyUtil.getImage(process);

        ObjectReferencesItem item =
                new ObjectReferencesItem(name, img, process) {

                    @Override
                    protected boolean gotoItem() {
                        return NavigatorUtil
                                .setProjectExplorerSelection(new StructuredSelection(
                                        modelObject));
                    }

                    @Override
                    protected boolean canGoto() {
                        return true;
                    }

                };

        return item;

    }

    /**
     * Create an object references list item for process interface start method.
     * 
     * @param activity
     * @param workbenchSite
     * @return
     */
    public static ObjectReferencesItem create(
            com.tibco.xpd.xpdExtension.StartMethod startMethod) {
        String name = Xpdl2ModelUtil.getDisplayNameOrName(startMethod);

        Image img = null;
        ImageRegistry ir = Xpdl2UiPlugin.getDefault().getImageRegistry();

        if (TriggerType.MESSAGE_LITERAL.equals(startMethod.getTrigger())) {
            img = ir.get(Xpdl2UiPlugin.IMG_INTERFACE_STARTMETHOD_MESSAGE);
        } else {
            img = ir.get(Xpdl2UiPlugin.IMG_INTERFACE_STARTMETHOD);
        }

        ObjectReferencesItem item =
                new ObjectReferencesItem(name, img, startMethod) {

                    @Override
                    protected boolean gotoItem() {
                        return NavigatorUtil
                                .setProjectExplorerSelection(new StructuredSelection(
                                        modelObject));
                    }

                    @Override
                    protected boolean canGoto() {
                        return true;
                    }

                };

        return item;
    }

    /**
     * Create an object references list item for process interface interface
     * method.
     * 
     * @param activity
     * @param workbenchSite
     * @return
     */
    public static ObjectReferencesItem create(
            com.tibco.xpd.xpdExtension.IntermediateMethod interMethod) {
        String name = Xpdl2ModelUtil.getDisplayNameOrName(interMethod);

        Image img = null;
        ImageRegistry ir = Xpdl2UiPlugin.getDefault().getImageRegistry();

        if (TriggerType.MESSAGE_LITERAL.equals(interMethod.getTrigger())) {
            img =
                    ir
                            .get(Xpdl2UiPlugin.IMG_INTERFACE_INTERMEDIATEMETHOD_MESSAGE);
        } else {
            img = ir.get(Xpdl2UiPlugin.IMG_INTERFACE_INTERMEDIATEMETHOD);
        }

        ObjectReferencesItem item =
                new ObjectReferencesItem(name, img, interMethod) {

                    @Override
                    protected boolean gotoItem() {
                        return NavigatorUtil
                                .setProjectExplorerSelection(new StructuredSelection(
                                        modelObject));
                    }

                    @Override
                    protected boolean canGoto() {
                        return true;
                    }

                };

        return item;
    }

    /**
     * Create an object references list item for given data field.
     * 
     * @param activity
     * @param workbenchSite
     * @return
     */
    public static ObjectReferencesItem createWarningItem(String warning) {

        Image img = null;
        ImageRegistry ir = Xpdl2UiPlugin.getDefault().getImageRegistry();
        img = ir.get(Xpdl2UiPlugin.IMG_WARNING);

        ObjectReferencesItem item =
                new ObjectReferencesItem(warning, img, warning) {

                    @Override
                    protected boolean gotoItem() {
                        return false;
                    }

                    @Override
                    protected boolean canGoto() {
                        return false;
                    }

                };

        return item;

    }

}
