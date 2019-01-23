/**
 *
 */
package com.tibco.xpd.implementer.resources.xpdl2.properties.filter;

import java.util.Arrays;
import java.util.List;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.uml2.uml.Property;

import com.tibco.xpd.implementer.resources.xpdl2.Messages;
import com.tibco.xpd.processeditor.xpdl2.properties.ChoiceConceptPath;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.ModeType;

public class FormalParamsFilter extends ChainingViewerFilter {
    private List<ModeType> supportedModes;

    public FormalParamsFilter(ModeType... modeTypes) {
        if (modeTypes == null) {
            throw new NullPointerException(
                    Messages.FormalParamsFilter_ModeTypesCannotBeNull_message);
        }
        if (modeTypes.length == 0) {
            throw new IllegalArgumentException(
                    Messages.FormalParamsFilter_ModeTypesCannotBeEmpty_message);
        }
        supportedModes = Arrays.asList(modeTypes);
    }

    @Override
    public synchronized boolean doSelect(Viewer viewer, Object parentElement,
            Object element) {
        boolean result = false;

        if (element instanceof ChoiceConceptPath) {
            return true;
        }

        if (element instanceof ConceptPath) {
            element = ((ConceptPath) element).getItem();
        }

        if (element instanceof FormalParameter) {
            ModeType mode = ((FormalParameter) element).getMode();
            result = supportedModes.contains(mode);
        } else if (element instanceof Property) {
            result = true;
        }
        return result;
    }
}