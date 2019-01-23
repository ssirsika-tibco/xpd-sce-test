/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.validation.bpmn.developer.rules;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.eclipse.emf.common.util.EList;

import com.tibco.xpd.implementer.nativeservices.java.JavaConstants;
import com.tibco.xpd.implementer.nativeservices.java.javaservice.mapper.JavaServiceMappingUtil;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptMappingCompositor;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptMappingCompositorFactory;
import com.tibco.xpd.processeditor.xpdl2.properties.script.SingleMappingCompositor;
import com.tibco.xpd.processeditor.xpdl2.util.DataMappingUtil;
import com.tibco.xpd.validation.xpdl2.rules.ActivityValidationRule;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.DirectionType;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TaskService;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Validator utility class that checks for non-contiguous array types in data
 * mapping / assignment scripts in the java mapping.
 * 
 * @author njpatel
 * 
 */
public class JavaServiceContiguousArrayRule extends ActivityValidationRule {

    // Contiguous array error issue ID
    private static final String ARRAY_ISSUE_ID = "bpmn.dev.mappingArrayNotContiguous"; //$NON-NLS-1$

    // General mapping error issue ID
    public static final String MAPPING_ISSUE_ID = "bpmn.dev.mappingError"; //$NON-NLS-1$

    @Override
    public void validate(Activity container) {
        if (container != null) {
            boolean result = true;

            if (container.getImplementation() instanceof Task) {
                TaskService taskService = ((Task) container.getImplementation())
                        .getTaskService();

                if (taskService != null) {

                    String type = (String) Xpdl2ModelUtil.getOtherAttribute(
                            taskService, XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_ImplementationType());

                    if ("Java".equals(type)) { //$NON-NLS-1$

                        if (taskService.getMessageIn() != null
                                && taskService.getMessageIn().getDataMappings() != null) {

                            result = validateInputMappings(taskService
                                    .getMessageIn().getDataMappings());
                        }

                        if (result) {
                            if (taskService.getMessageOut() != null
                                    && taskService.getMessageOut()
                                            .getDataMappings() != null) {

                                result = validateOutputMappings(container,
                                        taskService.getMessageOut()
                                                .getDataMappings());
                            }

                        }

                        if (!result) {
                            // Raise issue
                            addIssue(ARRAY_ISSUE_ID, container,
                                    new ArrayList<String>());
                        }
                    }
                }
            }
        }
    }

    /**
     * Validate the output mapping (assignments).
     * 
     * @param activity
     *            The activity.
     * @param mappings
     *            The mappings.
     * @return <code>true</code> if validated, <code>false</code> if
     *         non-contiguous arrays found.
     */
    private boolean validateOutputMappings(Activity activity, EList mappings) {
        boolean ret = true;

        if (mappings != null) {
            List<ParameterNode> rootNodes = new ArrayList<ParameterNode>();

            for (Object next : mappings) {
                if (next instanceof DataMapping) {
                    DataMapping mapping = (DataMapping) next;
                    String target = DataMappingUtil.getTarget(mapping);
                    String script = DataMappingUtil.getScript(mapping);
                    String grammar = DataMappingUtil.getGrammar(mapping);

                    if (target != null && script != null && grammar != null) {
                        ScriptMappingCompositorFactory factory = ScriptMappingCompositorFactory
                                .getCompositorFactory(grammar,
                                        DirectionType.OUT_LITERAL,
                                        JavaServiceMappingUtil.SCRIPT_CONTEXT);
                        if (factory != null) {
                            ScriptMappingCompositor compositor = factory
                                    .getCompositor(activity, target, script);
                            if (compositor instanceof SingleMappingCompositor) {
                                SingleMappingCompositor scriptMapping = (SingleMappingCompositor) compositor;

                                Collection<String> parameterNames = scriptMapping
                                        .getSourceItemNames();

                                if (parameterNames != null) {
                                    for (String param : parameterNames) {
                                        processParameter(rootNodes, param);
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // Check for contiguous arrays
            for (ParameterNode node : rootNodes) {
                if (!(ret = areArraysContiguous(node))) {
                    break;
                }
            }
        }
        return ret;
    }

    /**
     * Validate the input mapping (data mappings)
     * 
     * @param dataMappings
     * @return <code>true</code> if validated, <code>false</code> if
     *         non-contiguous arrays found.
     */
    private boolean validateInputMappings(EList dataMappings) {
        boolean ret = true;

        if (dataMappings != null) {
            List<ParameterNode> rootNodes = new ArrayList<ParameterNode>();

            for (Object next : dataMappings) {
                if (next instanceof DataMapping) {
                    DataMapping mapping = (DataMapping) next;

                    if (mapping.getFormal() != null) {
                        processParameter(rootNodes, mapping.getFormal());
                    }
                }
            }

            // Check for non-contiguous arrays
            for (ParameterNode node : rootNodes) {
                if (!(ret = areArraysContiguous(node))) {
                    break;
                }
            }
        }

        return ret;
    }

    /**
     * Check arrays
     * 
     * @param node
     * @return
     */
    private boolean areArraysContiguous(ParameterNode node) {
        boolean ret = true;

        if (node != null) {
            Set<ParameterNode> children = node.getChildren();

            if (children != null) {
                String currentParam = null;
                int idx = 0;
                for (ParameterNode child : children) {
                    if (!child.getName().equals(currentParam)) {
                        currentParam = child.getName();
                        idx = 0;
                    }

                    if (child.getIndex() >= 0) {
                        if (!(ret = child.getIndex() == idx++)) {
                            break;
                        }
                    }

                    if (!(ret = areArraysContiguous(child))) {
                        break;
                    }
                }
            }
        }

        return ret;
    }

    /**
     * Process the parameters from the mappings and create a parameter tree.
     * 
     * @param rootNodes
     * @param qualifiedParameterName
     */
    private void processParameter(List<ParameterNode> rootNodes,
            String qualifiedParameterName) {
        if (rootNodes != null && qualifiedParameterName != null
                && qualifiedParameterName.matches("(.*\\[\\d+\\]).*")) { //$NON-NLS-1$
            // Get the first segment of the parameter name
            int pos = qualifiedParameterName
                    .indexOf(JavaConstants.PARAMETER_SEPARATOR);

            if (pos > 0) {
                String rootName = qualifiedParameterName.substring(0, pos);
                qualifiedParameterName = qualifiedParameterName
                        .substring(pos + 1);
                ParameterNode root = null;

                for (ParameterNode node : rootNodes) {
                    if (node.getName().equals(rootName)) {
                        root = node;
                        break;
                    }
                }

                if (root == null) {
                    root = new ParameterNode(rootName, null);
                    rootNodes.add(root);
                }

                root.addChild(qualifiedParameterName);
            }
        }
    }

    /**
     * Tree node for parsing parameters to check for non-contiguous arrays
     */
    private class ParameterNode implements Comparable<ParameterNode> {

        private final String name;

        private final ParameterNode parent;

        private int index;

        private Set<ParameterNode> children = new TreeSet<ParameterNode>();

        /**
         * Constructor.
         * 
         * @param name
         * @param parent
         */
        public ParameterNode(String name, ParameterNode parent) {
            this(name, parent, -1);
        }

        /**
         * Constructor.
         * 
         * @param name
         * @param parent
         * @param index
         */
        public ParameterNode(String name, ParameterNode parent, int index) {
            this.name = name;
            this.parent = parent;
            this.index = index;
        }

        /**
         * Get the index of this element.
         * 
         * @return
         */
        public int getIndex() {
            return index;
        }

        /**
         * Get the parameter name
         * 
         * @return
         */
        public String getName() {
            return name;
        }

        /**
         * Get the parent parameter of this parameter
         * 
         * @return
         */
        public ParameterNode getParent() {
            return parent;
        }

        /**
         * Get the children of this parameter
         * 
         * @return
         */
        public Set<ParameterNode> getChildren() {
            return children;
        }

        /**
         * Check if this parameter is an array type
         * 
         * @return
         */
        public boolean isArray() {
            return index >= 0;
        }

        /**
         * Add a child to this parameter. This will take care of adding
         * grand-children depending on the path in the qualified parameter name
         * provided.
         * 
         * @param qualifiedParameterName
         */
        public void addChild(String qualifiedParameterName) {
            String paramName = qualifiedParameterName;

            if (paramName != null && paramName.length() > 0) {
                int pos = paramName.indexOf(JavaConstants.PARAMETER_SEPARATOR);

                if (pos > 0) {
                    paramName = paramName.substring(0, pos);
                    qualifiedParameterName = qualifiedParameterName
                            .substring(pos + 1);
                } else {
                    qualifiedParameterName = null;
                }

                // Check if param name is an index into an array parameter type
                int idx = -1;

                if (paramName.matches(".+\\[\\d+\\]")) { //$NON-NLS-1$
                    int start = paramName.indexOf('[');
                    idx = Integer.parseInt(paramName.substring(start + 1,
                            paramName.indexOf(']', start)));
                    paramName = paramName.substring(0, start);
                }

                ParameterNode child = findChild(paramName, idx);

                if (child == null) {
                    child = new ParameterNode(paramName, this, idx);
                    children.add(child);
                }

                // Continue adding grand-children
                child.addChild(qualifiedParameterName);
            }
        }

        @Override
        public String toString() {
            String str = getName();

            if (isArray()) {
                str += String.format("[%d]", index); //$NON-NLS-1$
            }

            if (parent != null) {
                str = parent.toString() + "." + str; //$NON-NLS-1$
            }

            return str;
        }

        /**
         * Find a child parameter with the given name
         * 
         * @param name
         * @param idx
         * @return
         */
        private ParameterNode findChild(String name, int idx) {
            ParameterNode child = null;

            if (name != null) {
                for (ParameterNode node : children) {
                    if (node.getName().equals(name) && node.getIndex() == idx) {
                        child = node;
                        break;
                    }
                }
            }

            return child;
        }

        /*
         * (non-Javadoc)
         * 
         * @see java.lang.Comparable#compareTo(java.lang.Object)
         */
        public int compareTo(ParameterNode o) {
            int compare = getName().compareTo(o.getName());

            if (compare == 0) {
                compare = getIndex() - o.getIndex();
            }
            return compare;
        }

    }
}
