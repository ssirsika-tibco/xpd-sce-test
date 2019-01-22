package com.tibco.xpd.rest.schema.ui.internal.editor;

import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Property;

import com.tibco.xpd.rest.schema.ui.internal.Messages;

/**
 * Label provider for the titles of property sections.
 * 
 * @author nwilson
 * @since 13 Jul 2015
 */
public class UmlJsonSchemaTitleLabelProvider extends UmlJsonSchemaLabelProvider {

    @Override
    public String getText(Object element) {
        String title = ""; //$NON-NLS-1$
        Object el = getElement(element);
        if (el instanceof Class) {
            title = Messages.UmlJsonSchemaTitleLabelProvider_Class;
        } else if (el instanceof Property) {
            title = Messages.UmlJsonSchemaTitleLabelProvider_Property;
        }
        return title;
    }
}
