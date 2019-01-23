package com.tibco.xpd.xpdl2.util.ocl;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EParameter;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.ocl.Environment;
import org.eclipse.ocl.EvaluationEnvironment;
import org.eclipse.ocl.EvaluationVisitor;
import org.eclipse.ocl.EvaluationVisitorDecorator;
import org.eclipse.ocl.ParserException;
import org.eclipse.ocl.ecore.CallOperationAction;
import org.eclipse.ocl.ecore.Constraint;
import org.eclipse.ocl.ecore.EcoreEnvironmentFactory;
import org.eclipse.ocl.ecore.OCL;
import org.eclipse.ocl.ecore.OCLExpression;
import org.eclipse.ocl.ecore.SendSignalAction;
import org.eclipse.ocl.expressions.OperationCallExp;
import org.eclipse.ocl.helper.OCLHelper;

import com.tibco.xpd.xpdl2.Package;

/**
 * Adapter that can manage complex relationship between objects that is
 * described using OCL
 * 
 * Example:<br>
 * Activity that refer to ActivitySet should be notified when new activity will
 * be added to the set, activity set will be removed.
 * 
 * @author wzurek
 */
public class OclBasedNotifier implements Adapter {

    /**
     * Parsed OCL query that describe notifier.
     */
    private final org.eclipse.ocl.ecore.OCL.Query query;

    private final String originalExpression;

    /**
     * Main target - value of the 'self' variable in the query.
     */
    private Notifier mainTarget;

    /**
     * Set of notifiers on the way to the target of the query.
     */
    private Set<Notifier> notifiers = new HashSet<Notifier>();

    /**
     * Set of listeners for this notifier.
     */
    private Set listeners = new HashSet();

    /**
     * Cached old result.
     */
    private Object result;

    /** The OCL processing context. */
    private OCL ocl;

    /**
     * USed by OCL for returning 'invalid / undefined etc' instead of it
     * returning null.
     */
    private final Object OCL_INVALID_TYPE;

    /**
     * The Constructor.
     * 
     * @param oclExpression
     *            the expression that describe this notifier
     * @param context
     *            EClass of the main target
     */
    public OclBasedNotifier(final String oclExpression, final EClass context) {
        this.originalExpression = oclExpression;

        /*
         * The EvaluationVisitor created by the default EcoreEnvironmentFactory
         * logs exceptions to error log when the property for which an operation
         * is not physically set in the model.
         * 
         * i.e. the expression
         * "self.dataObject.getOtherElement("dataObjectAttributes")" would cause
         * a null exception to be logged if the dataObject property was not set
         * under 'self'.
         * 
         * Therefore we use our own factory, which creates an EvaluationVisitor
         * that checks that the base property for any operation is set, before
         * calling the standard EvaluationEnvironment to evaluate the operation
         * call. This then avoids the logging of null exceptions.
         */
        ocl = OCL.newInstance(XpdOCLEcoreEnvironmentFactory.INSTANCE);

        OCLHelper<EClassifier, EOperation, EStructuralFeature, Constraint> helper =
                ocl.createOCLHelper();

        helper.setContext(context);

        try {
            OCL_INVALID_TYPE =
                    ocl.getEnvironment().getOCLStandardLibrary().getInvalid();

            this.query = ocl.createQuery(helper.createQuery(oclExpression));

        } catch (ParserException e) {
            throw new RuntimeException("OCL parse error.", e); //$NON-NLS-1$
        }
    }

    /**
     * The Constructor.
     * 
     * this constructor add provided listener to the list of listeners of this
     * OclListenr
     * 
     * @param oclExpression
     *            the expression that describe this notifier
     * @param context
     *            EClass of the main target
     * @param listener
     *            listener for this notifier
     */
    public OclBasedNotifier(final String oclExpression, final EClass context,
            final OclQueryListener listener) {
        this(oclExpression, context);
        addListener(listener);
    }

    /**
     * Result of the OCL Query evaluated on the adapter's target.
     * 
     * @return query result, may be null
     */
    public Object getResult() {
        return result;
    }

    /**
     * @return The result of evaluating the OCL expression for the current
     *         mainTarget.
     */
    private Object computeResult() {
        query.getEvaluationEnvironment().clear();
        try {
            Object queryResult = query.evaluate(mainTarget);

            /*
             * Sid. Only return the result if it is (a) Not an EObject at all OR
             * (b) if it is an EObject that is NOT the OCL InvalidType
             * (indicating missing value etc).
             */
            if (!(queryResult instanceof EObject && isQueryResultTypeInvalidType(queryResult))) {
                return queryResult;
            }
        } catch (NullPointerException e) {
            // ignore
        }
        return null;
    }

    /**
     * @param queryResult
     * @return <code>true</code> if the result type of a query is the OCL
     *         InvalidType
     */
    private boolean isQueryResultTypeInvalidType(Object queryResult) {
        return OCL_INVALID_TYPE.equals(queryResult);
    }

    /**
     * Add listener that will be notified about all changes to the results of
     * Ocl query. Do nothing if the same listener is alredy added.
     * 
     * @param listener
     */
    public void addListener(OclQueryListener listener) {
        if (!listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    /**
     * Unregister listener.
     * 
     * @param listener
     */
    public void removeListener(OclQueryListener listener) {
        listeners.remove(listener);
    }

    /**
     * @see {@link #notifyChanged(Notification)}
     */
    @Override
    public void notifyChanged(Notification notification) {

        // There are some circumstances where we can be called after being
        // unregistered
        // (BaseNotifierImpl class takes a copy of emf object eAdapters and then
        // iterates thru copy).
        // If one of the notifyChanged() calls made from eNotify removes an ocl
        // base notifier at
        // any point during this loop then it will still be in copy of list and
        // therefore
        // we may get removed twice.
        // So before doing anything, make sure we haven'ty already been moved.
        if (mainTarget == null) {
            return;
        }

        if (notification.getEventType() == Notification.REMOVING_ADAPTER) {
            // unregister form any other notifier when removing from
            // main notifier, ignore other such events
            unregister(notification);
            return;
        }

        /*
         * If our target has been moved from model then any expression that is
         * based upon parent tree it can result in a ClassCastException (because
         * for some reason the element in getParentPool() etc expression can be
         * DynamicEObject
         * 
         * So ignore if we're not part of model anymore.
         */
        if (mainTarget instanceof EObject) {
            EObject parentPkg = ((EObject) mainTarget).eContainer();

            while (!(parentPkg instanceof Package) && parentPkg != null) {
                parentPkg = parentPkg.eContainer();
            }

            if (parentPkg == null) {
                return;
            }
        }

        if (notification.getEventType() == Notification.SET) {
            Object feature = notification.getFeature();
            // chck if the notification is about UNsetting it parent, and ignore
            // notification if so.
            // This is because we will also get notification of removal of
            // item from it's container.
            if (feature instanceof EReference
                    && ((EReference) feature).isContainer()
                    && notification.getNewValue() == null) {
                return;
            }
        }

        // reconcile targets with new result of the query
        Set toRemove = new HashSet(notifiers);
        Set toAdd = new HashSet();

        OCLExpression exp = query.getExpression();
        Set newNotfiers =
                (Set) exp
                        .accept(new ListeningVisitor((EObject) mainTarget, ocl));

        for (Iterator iter = newNotfiers.iterator(); iter.hasNext();) {
            Object newObj = iter.next();
            if (!toRemove.remove(newObj)) {
                toAdd.add(newObj);
            }
        }

        for (Iterator iter = toRemove.iterator(); iter.hasNext();) {
            Notifier n = (Notifier) iter.next();
            n.eAdapters().remove(this);
        }

        for (Iterator iter = toAdd.iterator(); iter.hasNext();) {
            Notifier n = (Notifier) iter.next();
            n.eAdapters().add(this);
        }

        notifiers.addAll(toAdd);
        notifiers.removeAll(toRemove);

        /**
         * Sid. The following piece of code was pretty much a copy of what is
         * done in computeResult called straight after!
         * 
         * So don't see how it is possible that the value of target would not be
         * exactly the same as the value of newResult!
         * 
         * So commenting it out for now.
         */
        // Object target = null;
        // try {
        // query.getEvaluationEnvironment().clear();
        // target = query.evaluate(mainTarget);
        // } catch (NullPointerException e) {
        // // ignore
        // }

        Object newResult = computeResult();
        if (newResult != result
                || (newResult != null && notification.getNotifier() == newResult)) {
            result = newResult;
            fireEvent((EObject) mainTarget,
                    newResult /* target */,
                    notification);

        } else if (notification.getEventType() == NotificationsConstants.REFRESH_REFERENCED_ELEMENT
                || notification.getEventType() == NotificationsConstants.REFRESH_VISUAL) {
            fireEvent((EObject) mainTarget,
                    newResult /* target */,
                    notification);
        }
    }

    /**
     * @param notification
     */
    private void unregister(Notification notification) {
        // unregister form any other notifier when removing from
        // main notifier, ignore other such events
        if (notification.getNotifier() == mainTarget) {
            for (Iterator iter = notifiers.iterator(); iter.hasNext();) {
                Notifier n = (Notifier) iter.next();
                if (n != mainTarget) {
                    n.eAdapters().remove(this);
                }
            }
            notifiers.clear();
            mainTarget = null;
        }
    }

    /**
     * Fire listeners.
     * 
     * @param base
     * @param target
     * @param notification
     */
    protected void fireEvent(EObject base, Object target,
            Notification notification) {
        for (Iterator iter = listeners.iterator(); iter.hasNext();) {
            OclQueryListener lst = (OclQueryListener) iter.next();
            lst.oclNotifyChanged(base, target, notification);
        }
    }

    @Override
    public Notifier getTarget() {
        return mainTarget;
    }

    @Override
    public void setTarget(Notifier newTarget) {
        if (mainTarget == null && newTarget != null) {
            mainTarget = newTarget;

            OCLExpression exp = query.getExpression();

            notifiers =
                    (Set) exp.accept(new ListeningVisitor((EObject) mainTarget,
                            ocl));

            for (Iterator iter = notifiers.iterator(); iter.hasNext();) {
                Notifier n = (Notifier) iter.next();
                if (n != mainTarget) {
                    n.eAdapters().add(this);
                }
            }
            result = computeResult();
        }
    }

    @Override
    public boolean isAdapterForType(Object type) {
        return false;
    }

    /**
     * @see java.lang.Object#toString()
     * 
     * @return
     */
    @Override
    public String toString() {
        return String
                .format("%s: Expression(%s) ResultType(%s) MainTarget(%s)", //$NON-NLS-1$
                        this.getClass().getSimpleName(),
                        originalExpression,
                        (query != null ? query.resultType() : "??"), //$NON-NLS-1$
                        (mainTarget != null ? mainTarget.toString() : "null")); //$NON-NLS-1$
    }

    /**
     * The {@link EvaluationVisitor} created by the default
     * {@link EcoreEnvironmentFactory} logs exceptions to error log when the
     * property for which an operation is not physically set in the model.
     * <p>
     * i.e. the expression
     * "self.dataObject.getOtherElement("dataObjectAttributes")" would cause a
     * null exception to be logged if the dataObject property was not set under
     * 'self'.
     * <p>
     * Therefore we use our own factory, which creates an EvaluationVisitor that
     * checks that the base property for any operation is set, before calling
     * the standard EvaluationEnvironment to evaluate the operation call. This
     * then avoids the logging of null exceptions.
     * 
     * @author aallway
     * @since 4 Jan 2013
     */
    private static class XpdOCLEcoreEnvironmentFactory extends
            EcoreEnvironmentFactory {
        static final XpdOCLEcoreEnvironmentFactory INSTANCE =
                new XpdOCLEcoreEnvironmentFactory();

        /**
         * @see org.eclipse.ocl.ecore.EcoreEnvironmentFactory#createEvaluationVisitor(org.eclipse.ocl.Environment,
         *      org.eclipse.ocl.EvaluationEnvironment, java.util.Map)
         * 
         * @param env
         * @param evalEnv
         * @param extentMap
         * @return
         */
        @Override
        public EvaluationVisitor<EPackage, EClassifier, EOperation, EStructuralFeature, EEnumLiteral, EParameter, EObject, CallOperationAction, SendSignalAction, Constraint, EClass, EObject> createEvaluationVisitor(
                Environment<EPackage, EClassifier, EOperation, EStructuralFeature, EEnumLiteral, EParameter, EObject, CallOperationAction, SendSignalAction, Constraint, EClass, EObject> env,
                EvaluationEnvironment<EClassifier, EOperation, EStructuralFeature, EClass, EObject> evalEnv,
                Map<? extends EClass, ? extends Set<? extends EObject>> extentMap) {

            EvaluationVisitor<EPackage, EClassifier, EOperation, EStructuralFeature, EEnumLiteral, EParameter, EObject, CallOperationAction, SendSignalAction, Constraint, EClass, EObject> delegateVisitor =
                    super.createEvaluationVisitor(env, evalEnv, extentMap);

            return new XpdOCLEcoreEvaluationVisitor(delegateVisitor);
        }
    }

    /**
     * The EvaluationVisitor created by the default EcoreEnvironmentFactory logs
     * exceptions to error log when the property for which an operation is not
     * physically set in the model.
     * <p>
     * This class 'decorates' (in the OCL terminology) the EvaluationVisitor
     * returned by the standard factory by implementing the
     * visitOperationCallExp() method so that the standard visitor's evaluate
     * does not get called if the source property on which the operation is
     * called is not set in the model.
     * <p>
     * i.e. Otherwise the expression
     * "self.dataObject.getOtherElement("dataObjectAttributes")" would cause a
     * null exception to be logged if the dataObject property was not set under
     * 'self'.
     * 
     * @author aallway
     * @since 4 Jan 2013
     */
    private static class XpdOCLEcoreEvaluationVisitor
            extends
            EvaluationVisitorDecorator<EPackage, EClassifier, EOperation, EStructuralFeature, EEnumLiteral, EParameter, EObject, CallOperationAction, SendSignalAction, Constraint, EClass, EObject> {

        /**
         * @param decorated
         */
        protected XpdOCLEcoreEvaluationVisitor(
                EvaluationVisitor<EPackage, EClassifier, EOperation, EStructuralFeature, EEnumLiteral, EParameter, EObject, CallOperationAction, SendSignalAction, Constraint, EClass, EObject> delegateVisitor) {
            super(delegateVisitor);
        }

        /**
         * @param callExp
         * @return
         * @see org.eclipse.ocl.utilities.Visitor#visitOperationCallExp(org.eclipse.ocl.expressions.OperationCallExp)
         */
        @Override
        public Object visitOperationCallExp(
                OperationCallExp<EClassifier, EOperation> callExp) {
            /*
             * Do not call the standard visiOperationCallExp() BECAUSE if the
             * property on which operation is called is undefined in model, it
             * will throw and catch NullPointerException and LOG an error
             * because operation's parent property is null. As we can't
             * guarantee the model is complete according to the expression then
             * we need to suppress that.
             */
            Object sourceVal = null;
            try {
                sourceVal = callExp.getSource().accept(this);
            } catch (Exception e) {
            }

            if (sourceVal == null) {
                return getEnvironment().getOCLStandardLibrary().getInvalid();
            }

            Object res = super.visitOperationCallExp(callExp);
            return res;
        }

    }

}
