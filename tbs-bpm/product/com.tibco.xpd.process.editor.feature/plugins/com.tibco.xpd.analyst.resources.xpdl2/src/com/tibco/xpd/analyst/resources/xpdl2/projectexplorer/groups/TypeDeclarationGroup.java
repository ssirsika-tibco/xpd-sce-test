package com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.groups;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.swt.graphics.Image;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesConsts;
import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesPlugin;
import com.tibco.xpd.analyst.resources.xpdl2.internal.Messages;
import com.tibco.xpd.analyst.resources.xpdl2.processeditorconfiguration.ProcessEditorElementType;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * Type Declarations (logical) group of a Package in the Project Explorer tree
 * 
 * @author njpatel
 * 
 */
public class TypeDeclarationGroup extends AbstractAssetGroup {

    private static final String TITLE = Messages.TypeDeclarationGroup_TITLE;

    /**
     * Default constructor
     * 
     * @param parent
     */
    public TypeDeclarationGroup(EObject parent) {
        super(parent, ProcessEditorElementType.type_declaration);
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
        return Xpdl2Package.eINSTANCE.getPackage_TypeDeclarations();
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
                .get(Xpdl2ResourcesConsts.ICON_TYPEDECLARATION);
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

    @Override
    public EClass getReferenceType() {
        return Xpdl2Package.eINSTANCE.getTypeDeclaration();
    }

}
