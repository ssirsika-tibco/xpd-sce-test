/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.processeditor.xpdl2.properties.script;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.xpdExtension.ScriptInformation;
import com.tibco.xpd.xpdExtension.XpdExtensionFactory;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.DirectionType;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * @author nwilson
 */
public class ActivityScriptContentProvider implements
        IStructuredContentProvider {

    /** The direction for this provider. */
    private MappingDirection direction;

    /**
     * @param direction
     *            The direction for this provider.
     * @param adapterFactory
     *            The adapter factory.
     */
    public ActivityScriptContentProvider(MappingDirection direction) {
        this.direction = direction;
    }

    /**
     * @param inputElement
     * @return
     * @see org.eclipse.jface.viewers.IStructuredContentProvider#getElements(java.lang.Object)
     */
    public Object[] getElements(Object element) {
        Set<ScriptInformation> scripts =
                new TreeSet<ScriptInformation>(
                        new ScriptInformationComparator());
        DirectionType directionType;
        if (MappingDirection.IN.equals(direction)) {
            directionType = DirectionType.IN_LITERAL;
        } else {
            directionType = DirectionType.OUT_LITERAL;
        }
        if (element instanceof Activity) {
            Activity activity = (Activity) element;

            String grammar =
                    ScriptGrammarFactory.getScriptGrammar(activity,
                            directionType);
            if (grammar == null) {
                grammar =
                        ScriptGrammarFactory.getDefaultScriptGrammar(activity);
            }
            ScriptInformation details =
                    XpdExtensionFactory.eINSTANCE.createScriptInformation();
            details.setActivity(activity);
            details
                    .setName(Messages.ActivityScriptContentProvider_NewScriptLabel);
            details.setDirection(directionType);
            Expression expression = Xpdl2ModelUtil.createExpression(""); //$NON-NLS-1$
            expression.setScriptGrammar(grammar);
            details.setExpression(expression);
            scripts.add(details);

            List<?> others =
                    Xpdl2ModelUtil.getOtherElementList(activity,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_Script());
            for (Object other : others) {
                if (other instanceof ScriptInformation) {
                    ScriptInformation information = (ScriptInformation) other;
                    if (information.getName() != null
                            && directionType.equals(information.getDirection())) {
                        scripts.add(information);
                    }
                }
            }
            List<DataMapping> mappings =
                    Xpdl2ModelUtil.getDataMappings(activity, directionType);
            for (DataMapping mapping : mappings) {
                Object other =
                        Xpdl2ModelUtil.getOtherElement(mapping,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_Script());
                if (other instanceof ScriptInformation) {
                    ScriptInformation information = (ScriptInformation) other;
                    scripts.add(information);
                }
            }
        }
        return scripts.toArray();
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
