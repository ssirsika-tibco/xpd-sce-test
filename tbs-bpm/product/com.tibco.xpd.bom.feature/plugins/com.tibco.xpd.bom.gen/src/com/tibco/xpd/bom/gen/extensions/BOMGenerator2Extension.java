/*
 * Copyright (c) TIBCO Software Inc. 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.bom.gen.extensions;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import com.tibco.xpd.bom.gen.api.BOMGenerator2;
import com.tibco.xpd.destinations.GlobalDestinationUtil;

/**
 * Represents the <code>com.tibco.xpd.bom.gen.bomGenerator</code> extension.
 * 
 * @author njpatel
 * 
 */
public class BOMGenerator2Extension implements
        Comparable<BOMGenerator2Extension> {

    /**
     * Generation modes.
     */
    public enum GenerationMode {
        /**
         * Invokes generators every time bom changes.
         */
        INCREMENTAL,

        /**
         * Invokes generator only when requested.
         */
        ON_DEMAND
    };

    private static final String ATTR_ID = "id"; //$NON-NLS-1$

    private static final String ELEM_SUPPORTS = "supports"; //$NON-NLS-1$

    private static final String ELEM_VALIDATION = "validation"; //$NON-NLS-1$

    private static final String ELEM_DESTINATION = "destination"; //$NON-NLS-1$

    private static final String ATTR_DESTINATION_ID = "destinationId"; //$NON-NLS-1$

    private static final String ATTR_ALLOW_EMPTY_MODELS = "allowEmptyModels"; //$NON-NLS-1$

    private static final String ELEM_NATURE = "nature"; //$NON-NLS-1$

    private static final String ATTR_NATURE_ID = "natureId"; //$NON-NLS-1$

    private static final String EXPRESSION_AND = "and"; //$NON-NLS-1$

    private static final String EXPRESSION_OR = "or"; //$NON-NLS-1$

    private String id;

    private String label;

    private String suffix;

    private ImageDescriptor imgDesc;

    private BOMGenerator2 generator;

    private IConfigurationElement element;

    private Boolean isEmptyBOMSupported;

    /** Supported natures and global destinations (expressions) */
    private List<Expression> supports;

    private List<String> markerIds;

    private GenerationMode generationMode;

    /**
     * Represents the <code>com.tibco.xpd.bom.gen.bomGenerator</code> extension.
     * 
     * @param element
     *            configuration element.
     */
    protected BOMGenerator2Extension(IConfigurationElement element) {
        this.element = element;
    }

    /**
     * Get the id of the extension.
     * 
     * @return id
     */
    public String getId() {
        if (id == null) {
            id = element.getAttribute(ATTR_ID);
        }
        return id;
    }

    /**
     * Get the human-readable name of this extension.
     * 
     * @return label
     */
    public String getLabel() {
        if (label == null) {
            label = element.getAttribute("label"); //$NON-NLS-1$
            Assert.isNotNull(label, String
                    .format("No label provider for BOM Generator extension %s", //$NON-NLS-1$
                            getId()));
        }
        return label;
    }

    /**
     * Get the suffix from this extension. (This is used for the generated
     * project name.)
     * 
     * @return
     */
    public String getSuffix() {
        if (suffix == null) {
            suffix = element.getAttribute("suffix"); //$NON-NLS-1$
            Assert.isNotNull(suffix,
                    String.format("No suffix provider for BOM Generator extension %s", //$NON-NLS-1$
                            getId()));
        }
        return suffix;
    }

    /**
     * Get the image descriptor of the icon of this extension.
     * 
     * @return {@link ImageDescriptor} or <code>null</code> if not specified.
     */
    public ImageDescriptor getIcon() {
        if (imgDesc == null) {
            String imgPath = element.getAttribute("icon"); //$NON-NLS-1$
            if (imgPath != null && imgPath.length() > 0) {
                imgDesc =
                        AbstractUIPlugin.imageDescriptorFromPlugin(element
                                .getNamespaceIdentifier(), imgPath);
            }
        }
        return imgDesc;
    }

    /**
     * Get the {@link BOMGenerator2 generator} of this extension.
     * 
     * @return generator
     * @throws CoreException
     */
    public BOMGenerator2 getGenerator() throws CoreException {
        if (generator == null) {
            generator =
                    (BOMGenerator2) element.createExecutableExtension("class"); //$NON-NLS-1$
            if (generator != null) {
                generator.setExtension(this);
            }
        }
        return generator;
    }

    /**
     * Check if this extension supports the generation from empty BOMs.
     * 
     * @return <code>true</code> if the extension supports empty BOMs,
     *         <code>false</code> otherwise.
     * 
     * @since 3.5
     */
    public boolean isEmptyBOMSupported() {
        if (isEmptyBOMSupported == null) {
            String value = element.getAttribute(ATTR_ALLOW_EMPTY_MODELS);
            if (value != null) {
                isEmptyBOMSupported = Boolean.parseBoolean(value);
            } else {
                isEmptyBOMSupported = Boolean.FALSE;
            }
        }
        return isEmptyBOMSupported;
    }

    /**
     * Check if this extension supports the given project. This will check the
     * "supports" expression in the extension.
     * 
     * @param project
     * @return
     * @throws CoreException
     */
    public boolean isSupported(IProject project) throws CoreException {
        if (project != null) {
            List<Expression> expressions = getSupportExpressions();

            if (expressions != null) {
                for (Expression expression : expressions) {
                    if (!expression.evaluate(project)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * Get all the project Natures and Destination (expressions) that this
     * extension supports.
     * 
     * @return
     */
    private List<Expression> getSupportExpressions() {
        if (supports == null) {
            supports = new ArrayList<Expression>();
            IConfigurationElement[] children =
                    element.getChildren(ELEM_SUPPORTS);
            if (children != null) {
                supports.addAll(readExpressions(children[0]));
            }
        }
        return supports;
    }

    /**
     * Get all marker type ids that the selected resource should be validated
     * against for this generator.
     * 
     * @return list of marker ids, empty list if none specified.
     */
    public List<String> getMarkerTypes() {
        if (markerIds == null) {
            markerIds = new ArrayList<String>();
            IConfigurationElement[] children =
                    element.getChildren(ELEM_VALIDATION);
            if (children != null && children.length > 0) {
                children = children[0].getChildren("marker"); //$NON-NLS-1$
                if (children != null) {
                    for (IConfigurationElement child : children) {
                        String id = child.getAttribute("markerType"); //$NON-NLS-1$
                        if (id != null) {
                            markerIds.add(id);
                        }
                    }
                }
            }
        }
        return markerIds;
    }

    /**
     * Returns the generation mode as defined in the generator extension.
     * Default is {@link GenerationMode#INCREMENTAL}.
     * 
     * @return generation mode.
     * @since 3.5.3
     * @see GenerationMode
     * @throws IllegalArgumentException
     *             if the string value from extension doesn't match
     *             {@link GenerationMode} enumeration literal.
     */
    public GenerationMode getGenerationMode() {
        if (generationMode == null) {
            String genModeString = element.getAttribute("generationMode"); //$NON-NLS-1$
            if (genModeString != null && !genModeString.trim().isEmpty()) {
                generationMode = GenerationMode.valueOf(genModeString);
            } else {
                generationMode = GenerationMode.INCREMENTAL;
            }
        }
        return generationMode;
    }

    @Override
    public String toString() {
        return "bomGenerator2 extension: " + getId(); //$NON-NLS-1$
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    @Override
    public int compareTo(BOMGenerator2Extension o) {
        return getLabel().compareToIgnoreCase(o.getLabel());
    }

    /**
     * Read the nature/destination expression from the extension.
     * 
     * @param element
     * @return
     */
    private List<Expression> readExpressions(IConfigurationElement element) {
        List<Expression> expressions = new ArrayList<Expression>();

        if (element != null && element.getName().equals(ELEM_SUPPORTS)) {
            for (IConfigurationElement child : element.getChildren()) {
                if (child.getName().equals(EXPRESSION_AND)) {
                    expressions.add(readANDExpression(child));
                } else if (child.getName().equals(EXPRESSION_OR)) {
                    expressions.add(readORExpression(child));
                } else {
                    Expression operand = readOperandExpression(child);
                    if (operand != null) {
                        expressions.add(operand);
                    }
                }
            }
        }

        return expressions;
    }

    /**
     * Read the destination/nature AND expression from the extension.
     * 
     * @param element
     * @return
     */
    private AndExpression readANDExpression(IConfigurationElement element) {
        AndExpression expr = new AndExpression();
        if (element != null && element.getName().equals(EXPRESSION_AND)) {
            for (IConfigurationElement child : element.getChildren()) {
                if (child.getName().equals(EXPRESSION_AND)) {
                    expr.add(readANDExpression(child));
                } else if (child.getName().equals(EXPRESSION_OR)) {
                    expr.add(readORExpression(child));
                } else {
                    Expression operand = readOperandExpression(child);
                    if (operand != null) {
                        expr.add(operand);
                    }
                }
            }
        }
        return expr;
    }

    /**
     * Read the destination/nature OR expression from the extension.
     * 
     * @param element
     * @return
     */
    private OrExpression readORExpression(IConfigurationElement element) {
        OrExpression expr = new OrExpression();

        if (element != null && element.getName().equals(EXPRESSION_OR)) {
            for (IConfigurationElement child : element.getChildren()) {
                if (child.getName().equals(EXPRESSION_AND)) {
                    expr.add(readANDExpression(child));
                } else if (child.getName().equals(EXPRESSION_OR)) {
                    expr.add(readORExpression(child));
                } else {
                    Expression operand = readOperandExpression(child);
                    if (operand != null) {
                        expr.add(operand);
                    }
                }
            }
        }
        return expr;
    }

    /**
     * Get the operand from the extension expression for nature/destination in
     * the supports element.
     * 
     * @param element
     * @return
     */
    private Expression readOperandExpression(IConfigurationElement element) {
        if (element.getName().equals(ELEM_DESTINATION)) {
            String id = element.getAttribute(ATTR_DESTINATION_ID);
            if (id != null) {
                return new DestinationExpression(id);
            }
        } else if (element.getName().equals(ELEM_NATURE)) {
            String id = element.getAttribute(ATTR_NATURE_ID);
            if (id != null) {
                return new NatureExpression(id);
            }
        }

        return null;
    }

    /**
     * Abstract class that represents the supports expression in the extension.
     * 
     * @author njpatel
     * 
     */
    private abstract class Expression {
        protected final List<Expression> expressions =
                new ArrayList<Expression>();

        public void add(Expression element) {
            if (element != null) {
                expressions.add(element);
            }
        }

        public abstract boolean evaluate(IProject project) throws CoreException;
    }

    /**
     * OR expression. The evaluate method will return <code>true</code> if any
     * one of the elements evaluates to <code>true</code>.
     * 
     * @author njpatel
     * 
     */
    private class OrExpression extends Expression {

        @Override
        public boolean evaluate(IProject project) throws CoreException {
            if (project != null) {
                if (!expressions.isEmpty()) {
                    for (Expression expression : expressions) {
                        if (expression.evaluate(project)) {
                            return true;
                        }
                    }
                    return false;
                }
            }
            return true;
        }
    }

    /**
     * AND expression. The evaluate method will return <code>true</code> if all
     * elements evaluates to <code>true</code>.
     * 
     * @author njpatel
     * 
     */
    private class AndExpression extends Expression {

        @Override
        public boolean evaluate(IProject project) throws CoreException {
            if (project != null) {
                for (Expression expression : expressions) {
                    if (!expression.evaluate(project)) {
                        return false;
                    }
                }
            }
            return true;
        }
    }

    /**
     * Project Nature expression. This evaluates to <code>true</code> if the
     * project has the given nature.
     * 
     * @author njpatel
     * 
     */
    private class NatureExpression extends Expression {

        private final String natureId;

        public NatureExpression(String natureId) {
            this.natureId = natureId;
        }

        @Override
        public boolean evaluate(IProject project) throws CoreException {
            return project != null && project.hasNature(natureId);
        }
    }

    /**
     * Project Destination expression. This evaluates to <code>true</code> if
     * the project has the given global destination enabled.
     * 
     * @author njpatel
     * 
     */
    private class DestinationExpression extends Expression {

        private final String destinationId;

        public DestinationExpression(String destinationId) {
            this.destinationId = destinationId;
        }

        @Override
        public boolean evaluate(IProject project) throws CoreException {
            return project != null
                    && GlobalDestinationUtil
                            .isGlobalDestinationEnabled(project, destinationId);
        }
    }
}