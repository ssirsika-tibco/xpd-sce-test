/**
 *
 */
package com.tibco.xpd.implementer.resources.xpdl2.properties.filter;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

import com.tibco.xpd.implementer.resources.xpdl2.Messages;
import com.tibco.xpd.implementer.script.IWsdlPath;

public class WsdlFilter extends ViewerFilter {

    private final WsdlFilter.WsdlDirection direction;

    public enum WsdlDirection {
        IN {
            boolean check(IWsdlPath path) {
                return path.isInput();
            }
        },
        OUT {
            boolean check(IWsdlPath path) {
                return path.isOutput();
            }
        },
        FAULT {
            @Override
            boolean check(IWsdlPath path) {
                return false;
            }
        };

        abstract boolean check(IWsdlPath path);
    };

    public WsdlFilter(WsdlFilter.WsdlDirection direction) {
        if (direction == null) {
            throw new NullPointerException(
                    Messages.WsdlFilter_DirectionCannotBeNull_message);
        }
        this.direction = direction;
    }

    @Override
    public boolean select(Viewer viewer, Object parentElement, Object element) {
        boolean result = false;
        if (element instanceof IWsdlPath) {
            result = direction.check((IWsdlPath) element);
        }
        return result;
    }
}