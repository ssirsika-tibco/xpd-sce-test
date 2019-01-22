package com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.groups;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.swt.graphics.Image;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesConsts;
import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesPlugin;
import com.tibco.xpd.analyst.resources.xpdl2.internal.Messages;
import com.tibco.xpd.analyst.resources.xpdl2.processeditorconfiguration.ProcessEditorElementType;
import com.tibco.xpd.xpdl2.DataField;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * Data Fields (logical) group of the Package or Process of the Project Explorer
 * tree
 * 
 * @author njpatel
 * 
 */
public class DataFieldGroup extends AbstractAssetGroup {

    private static final String TITLE = Messages.DataFieldGroup_TITLE;

    /**
     * Default constructor
     * 
     * @param parent
     */
    public DataFieldGroup(EObject parent) {
        super(parent, ProcessEditorElementType.datafield);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.groups.BpmArtefactGroup
     * #getFeature()
     */
    @Override
    public EStructuralFeature getFeature() {
        return Xpdl2Package.eINSTANCE.getDataFieldsContainer_DataFields();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.groups.BpmArtefactGroup
     * #getText()
     */
    @Override
    public String getText() {
        return TITLE;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.groups.BpmArtefactGroup
     * #getImage()
     */
    @Override
    public Image getImage() {
        return Xpdl2ResourcesPlugin.getDefault().getImageRegistry()
                .get(Xpdl2ResourcesConsts.ICON_DATAFIELD);
    }

    @Override
    public EClass getReferenceType() {
        return Xpdl2Package.eINSTANCE.getDataField();
    }

    @Override
    public List<?> getChildren() {
        List<Object> filtered = new ArrayList<Object>();
        List<?> allChildren = super.getChildren();
        for (Object next : allChildren) {
            if (next instanceof DataField) {
                DataField field = (DataField) next;
                if (!field.isCorrelation()) {
                    filtered.add(field);
                }
            }
        }
        return filtered;
    }
}
