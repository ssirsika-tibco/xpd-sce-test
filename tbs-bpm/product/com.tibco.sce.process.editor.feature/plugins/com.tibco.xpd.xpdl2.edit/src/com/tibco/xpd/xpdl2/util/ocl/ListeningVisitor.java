package com.tibco.xpd.xpdl2.util.ocl;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.ocl.ecore.OCL;
import org.eclipse.ocl.ecore.OCL.Query;
import org.eclipse.ocl.ecore.utilities.AbstractVisitor;
import org.eclipse.ocl.expressions.OperationCallExp;
import org.eclipse.ocl.expressions.PropertyCallExp;
import org.eclipse.ocl.expressions.VariableExp;

/**
 * Visitor that extract all objects from the query that should be listened in
 * order to keep relationship to the result up to date.<br>
 * <br>
 * 
 * <b>Notes:</b> <li>Limitations: query variables are not supported
 * 
 * @see OclBasedNotifier
 * 
 * @author wzurek
 */
public class ListeningVisitor extends AbstractVisitor {

    /**
     * Object used for 'self' in the query.
     */
    private final EObject base;

    /**
     * Final object.
     */
    private Set result = new HashSet();

    /** The OCL context, Shared with creator (OCLBaseNotifier). */
    private OCL ocl;

    /**
     * Setup during construction, this is the object that OCL uses to represent
     * missing or invalid ocl expression return types.
     */
    private final Object OCL_INVALID_TYPE;

    /**
     * The Constructor.
     * 
     * @param baseObject
     *            base object for the query
     */
    public ListeningVisitor(final EObject baseObject, OCL ocl) {
        this.base = baseObject;
        this.ocl = ocl;
        OCL_INVALID_TYPE =
                ocl.getEnvironment().getOCLStandardLibrary().getInvalid();
    }

    @Override
    public Object visitVariableExp(VariableExp v) {
        if ("self".equals(v.getName())) { //$NON-NLS-1$
            if (!result.contains(base)) {
                result.add(base);
            }
        }
        return result;
    }

    /**
     * Visitor for Property Call.
     * 
     * @return elements that needed to be listened
     */
    @Override
    public Object visitPropertyCallExp(final PropertyCallExp ac) {
        super.visitPropertyCallExp(ac);

        Query subQuery = ocl.createQuery(ac);
        Object val = subQuery.evaluate(base);

        if (val != null && (val instanceof EObject) && !result.contains(val)) {
            // the result might be an IvalidType from Ocl, we dont want to
            // liten to it
            if (null != ((EObject) val).eClass().getEPackage()
                    && !OCL_INVALID_TYPE.equals(val)) {
                result.add(val);
            }
        }
        return result;
    }

    @Override
    public Object visitOperationCallExp(OperationCallExp oc) {
        super.visitOperationCallExp(oc);

        if (oc.getReferredOperation() instanceof EOperation) {
            EOperation referredOperation =
                    (EOperation) oc.getReferredOperation();

            EClass ecl = referredOperation.getEContainingClass();
            Query subQuery;
            Object val;
            if (ecl == null) {
                try {
                    if ("first".equals(referredOperation.getName())) { //$NON-NLS-1$
                        subQuery = ocl.createQuery(oc);
                    } else {
                        subQuery = ocl.createQuery(oc.getSource());
                    }
                    val = subQuery.evaluate(base);
                } catch (NullPointerException e) {
                    val = null;
                }

            } else {
                subQuery = ocl.createQuery(oc);
                val = subQuery.evaluate(base);
            }

            if (val instanceof Collection) {
                for (Iterator iter = ((Collection) val).iterator(); iter
                        .hasNext();) {
                    Object obj = iter.next();
                    if (val != null && !OCL_INVALID_TYPE.equals(val)
                            && !result.contains(obj)) {
                        result.add(obj);
                    }
                }
            } else if (val != null && !OCL_INVALID_TYPE.equals(val)
                    && !result.contains(val)) {
                result.add(val);
            }
        }

        return result;
    }
}