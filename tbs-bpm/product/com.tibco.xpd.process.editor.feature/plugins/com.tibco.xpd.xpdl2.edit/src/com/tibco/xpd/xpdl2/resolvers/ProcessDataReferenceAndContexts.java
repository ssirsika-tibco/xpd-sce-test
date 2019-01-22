/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.xpdl2.resolvers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.tibco.xpd.xpdl2.ProcessRelevantData;

/**
 * Stores info on a reference process data field / parameter and the contexts in
 * which it is referenced.
 * 
 * @author aallway
 * @since 19 Jun 2012
 */
public class ProcessDataReferenceAndContexts {

    public static final Set<DataReferenceContext> CONTEXT_UNKNOWN_SET =
            new HashSet<DataReferenceContext>(
                    Collections
                            .singletonList(DataReferenceContext.CONTEXT_UNKNOWN));

    /**
     * The referenced data.
     */
    private ProcessRelevantData referencedData;

    /**
     * The set of contexts in which the data is referenced. This is decided by
     * the field resolver extensions (see {@link DataReferenceContext} for
     * common context identifiers)
     */
    private Set<DataReferenceContext> contexts = null;

    /**
     * Sid XPD-7078 The is the set of contexts including duplicate contexts with
     * different labels. This is so that in scenarios like process data mapper
     * where data can be referenced in the same context BUT with different
     * labels they can both be displayed (because main context set is set of
     * unique id'd {@link DataReferenceContext}'s
     * 
     * For instance a target field that is both referenced directly in a mapping
     * AND array inflation configuration will both have "ScrtipTaskScript"
     * context but we want to show both references in UI.
     */
    private List<DataReferenceContext> allUniqueLabelledContext = null;

    /**
     * 
     * @param referencedData
     *            The data that is referenced
     */
    public ProcessDataReferenceAndContexts(ProcessRelevantData referencedData) {
        super();
        this.referencedData = referencedData;
    }

    /**
     * 
     * @param referencedData
     *            The data that is referenced
     * @param context
     *            The context in which the data is referenced (i.e. type of
     *            'thing' that references the field - see
     *            {@link DataReferenceContext} for common context identifiers)
     */
    public ProcessDataReferenceAndContexts(ProcessRelevantData referencedData,
            DataReferenceContext context) {
        super();
        this.referencedData = referencedData;
        this.addContext(context);
    }

    /**
     * 
     * @return true if there are specific identified reference contexts for data
     *         field (not including {@link #CONTEXT_UNKNOWN}.
     */
    public boolean hasContexts() {
        if (contexts == null || contexts.isEmpty()) {
            return false;
        }

        if (contexts.size() == 1) {
            DataReferenceContext context = contexts.iterator().next();
            if (DataReferenceContext.CONTEXT_UNKNOWN.equals(context)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Add the given reference context to the set of contexts in which this
     * field is referenced.
     * 
     * @param context
     *            The context in which the data is referenced (i.e. type of
     *            'thing' that references the field - see
     *            {@link DataReferenceContext} for common context identifiers)
     */
    public void addContext(DataReferenceContext context) {
        if (context != null) {
            if (contexts == null) {
                contexts = new HashSet<DataReferenceContext>();
            }

            contexts.add(context);

            addUniqueLabelledContext(context);
        }
    }

    /**
     * Add the given reference contexts to the set of contexts in which this
     * field is referenced.
     * 
     * @param context
     */
    public void addAllContexts(Collection<DataReferenceContext> moreContexts) {
        if (contexts == null) {
            contexts = new HashSet<DataReferenceContext>();
        }

        contexts.addAll(moreContexts);

        for (DataReferenceContext dataReferenceContext : moreContexts) {
            addUniqueLabelledContext(dataReferenceContext);
        }
    }

    /**
     * @return the referencedData
     */
    public ProcessRelevantData getReferencedData() {
        return referencedData;
    }

    /**
     * Get the set of contexts that the data is referenced in The context in
     * which the data is referenced (i.e. type of 'thing' that references the
     * field - see {@link DataReferenceContext} for common context identifiers)
     * 
     * @return The set of context id's that the field is referenced in.
     */
    public Set<DataReferenceContext> getContexts() {
        /*
         * If we have had no contexts set then return a single "unknown" entry,
         * because the existence of this class object means it has been
         * referenced but nothing has said how.
         */
        if (contexts == null) {
            return CONTEXT_UNKNOWN_SET;
        }
        return contexts;
    }

    /**
     * Unlike {@link #getContexts()} which is a set based on unique
     * {@link DataReferenceContext} context-ids, this method returns the set of
     * contexts that have unique id+label
     * <p>
     * In other words if data is referenced as id="ScriptTaskScript" with Label
     * "Script Task" and again with id="ScriptTaskScript" with Label
     * "Script Task (Mapping Script)" the {@link #getContexts()} will return one
     * context whereas {@link #getUniquelyLabelledContexts()} will return both.
     * 
     * @return The set of contexts that have unique id+label
     */
    public Collection<DataReferenceContext> getUniquelyLabelledContexts() {
        if (allUniqueLabelledContext == null) {
            return CONTEXT_UNKNOWN_SET;
        }
        return allUniqueLabelledContext;
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     * 
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        return referencedData.equals(obj);
    }

    /**
     * @see java.lang.Object#hashCode()
     * 
     * @return
     */
    @Override
    public int hashCode() {
        return referencedData.hashCode();
    }

    /**
     * @see java.lang.Object#toString()
     * 
     * @return
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(String
                .format("Referenced Data '%s'. Reference Contexts:", referencedData.getName())); //$NON-NLS-1$

        for (DataReferenceContext context : getContexts()) {
            sb.append(String.format(" '%s'", context.toString())); //$NON-NLS-1$
        }
        sb.append('\n');
        return sb.toString();
    }

    /**
     * Sid XPD-7078: Add the context to the set of context with same id+label
     * 
     * @param context
     */
    private void addUniqueLabelledContext(DataReferenceContext context) {
        if (allUniqueLabelledContext == null) {
            allUniqueLabelledContext = new ArrayList<>();
        }

        for (DataReferenceContext dataReferenceContext : allUniqueLabelledContext) {
            if (context.getContextId()
                    .equals(dataReferenceContext.getContextId())
                    && context.getLabel()
                            .equals(dataReferenceContext.getLabel())) {
                /*
                 * Already have a reference context with this id and label -
                 * don't add again
                 */
                return;
            }
        }

        allUniqueLabelledContext.add(context);

    }
}
