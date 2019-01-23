/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.process.datamapper.common;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.transaction.TransactionalEditingDomain;

import com.tibco.xpd.datamapper.api.DataMapperUtils;
import com.tibco.xpd.datamapper.scripts.AbstractScriptDataMapperEditorProvider;
import com.tibco.xpd.process.datamapper.internal.Messages;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptGrammarFactory;
import com.tibco.xpd.xpdExtension.ScriptDataMapper;
import com.tibco.xpd.xpdExtension.XpdExtensionFactory;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.DirectionType;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.util.ResetExpressionContentCommand;

/**
 * ScriptDataMapper provider for scenarios where ScriptDatamapper element is
 * stored within an Expression.
 * 
 * @author aallway
 * @since 6 May 2015
 */
public abstract class AbstractExpressionScriptDataMapperProvider extends
        AbstractScriptDataMapperEditorProvider {

    /**
     * @param mapperContext
     * @param mappingDirection
     */
    public AbstractExpressionScriptDataMapperProvider(String mapperContext,
            DirectionType mappingDirection) {
        super(mapperContext, mappingDirection);
    }

    /**
     * Return the Expression element that should contain the
     * xpdExt:ScriptDataMapper element for this context.
     * 
     * @param contextInputObject
     */
    protected abstract Expression getExpression(Object contextInputObject);

    /**
     * @see com.tibco.xpd.datamapper.scripts.IScriptDataMapperProvider#getScriptDataMapper(java.lang.Object)
     * 
     * @param contextInputObject
     * @return
     */
    @Override
    public final ScriptDataMapper getScriptDataMapper(Object contextInputObject) {
        Expression expression = getExpression(contextInputObject);

        if (expression != null) {
            return DataMapperUtils.getExistingScriptDataMapper(expression);
        }

        return null;
    }

    /**
     * @see com.tibco.xpd.datamapper.scripts.AbstractScriptDataMapperEditorProvider#createScriptDataMapper(java.lang.Object,
     *      org.eclipse.emf.edit.domain.EditingDomain,
     *      org.eclipse.emf.common.command.CompoundCommand)
     * 
     * @param contextInputObject
     * @param editingDomain
     * @param optionalCreationCommand
     * @return
     */
    @Override
    protected ScriptDataMapper createScriptDataMapper(
            Object contextInputObject, EditingDomain editingDomain,
            CompoundCommand optionalCreationCommand) {
        ScriptDataMapper scriptDataMapper = null;

        final Expression expression = getExpression(contextInputObject);

        if (expression != null) {
            scriptDataMapper =
                    XpdExtensionFactory.eINSTANCE.createScriptDataMapper();

            optionalCreationCommand
                    .append(new ResetExpressionContentCommand(
                            (TransactionalEditingDomain) editingDomain,
                            Messages.AbstractExpressionScriptDataMapperProvider_CreateDataMapperContent_menu,
                            expression, XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_ScriptDataMapper(),
                            scriptDataMapper));

        }

        return scriptDataMapper;
    }

    /**
     * @see com.tibco.xpd.datamapper.scripts.IScriptDataMapperProvider#usesDataMapperGrammer(java.lang.Object)
     * 
     * @param contextInputObject
     * @return true, REST data mapper only uses DataMapper grammar.
     */
    @Override
    public boolean usesDataMapperGrammer(Object contextInputObject) {
        boolean usesDataMapperGrammer = false;
        Expression expression = getExpression(contextInputObject);
        if (expression != null) {
            String grammar = expression.getScriptGrammar();
            if (ScriptGrammarFactory.DATAMAPPER.equals(grammar)) {
                usesDataMapperGrammer = true;
            }
        }
        return usesDataMapperGrammer;
    }

    /**
     * @see com.tibco.xpd.datamapper.scripts.AbstractScriptDataMapperEditorProvider#getDataMapperDeselectedCommand(org.eclipse.emf.edit.domain.EditingDomain,
     *      java.lang.Object)
     * 
     * @param editingDomain
     * @param contextInputObject
     * @return
     */
    @Override
    public Command getDataMapperDeselectedCommand(EditingDomain editingDomain,
            Object contextInputObject) {
        /*
         * This mapping scenario does not allow grammar selection, so never need
         * to do any clean up when different grammar selected.
         */
        return null;
    }
}
