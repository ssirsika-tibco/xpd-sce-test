/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.implementer.script;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Script;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;
import org.mozilla.javascript.UniqueTag;

import com.tibco.xpd.process.js.model.ProcessJsConsts;
import com.tibco.xpd.process.js.parser.util.ScriptParserUtil;
import com.tibco.xpd.process.js.parser.validator.wsdl.WsdlVarNameResolver;
import com.tibco.xpd.script.parser.antlr.JScriptParser;
import com.tibco.xpd.script.parser.internal.validator.IVarNameResolver;
import com.tibco.xpd.script.parser.validator.ErrorMessage;
import com.tibco.xpd.script.parser.validator.ISymbolTable;
import com.tibco.xpd.script.parser.validator.IValidationStrategy;
import com.tibco.xpd.script.parser.validator.SymbolTable;

/**
 * @author nwilson
 */
public class JavaScriptExpressionParser implements IExpressionParser {

    /** JavaScript grammar identifier. */
    public static final String JAVASCRIPT = "JavaScript"; //$NON-NLS-1$

    /**
     * @param script
     *            The script to parse.
     * @return The collection of DataField names.
     * @throws FieldParserException
     *             If there was a problem parsing the script.
     */
    public Collection<String> getParameterNamesOld(String script)
            throws FieldParserException {
        Collection<String> names = new ArrayList<String>();
        if (script != null) {
            script = script.replace(".@", "._at_"); //$NON-NLS-1$ //$NON-NLS-2$
            script = script.replace("::", "__cc__"); //$NON-NLS-1$ //$NON-NLS-2$
            Context context = Context.enter();
            ReferenceWatcher scope;
            try {
                Script compiled = context.compileString(script, "", 0, null); //$NON-NLS-1$
                Scriptable scriptable = context.initStandardObjects();
                scope = new ReferenceWatcher(scriptable);
                compiled.exec(context, scope);
            } catch (Exception e) {
                throw new FieldParserException(e.getMessage());
            }
            for (String name : scope.getNames()) {
                name = name.replace("__cc__", "::"); //$NON-NLS-1$ //$NON-NLS-2$
                names.add(name.replace("._at_", ".@")); //$NON-NLS-1$ //$NON-NLS-2$
            }
        }
        return names;
    }

    @Override
    public Collection<String> getParameterNames(String script,
            IValidationStrategy strategy, String scriptType)
            throws FieldParserException {
        IVarNameResolver varNameResolver = new WsdlVarNameResolver();
        SymbolTable symbolTable = new SymbolTable();
        // setting validation Strategy
        List<IValidationStrategy> strategyList =
                new ArrayList<IValidationStrategy>();
        if (strategy != null) {
            strategyList.add(strategy);
        }
        // setting destination
        List<String> destList = new ArrayList<String>();
        destList.add(ProcessJsConsts.JSCRIPT_DESTINATION);
        Map<String, List<ErrorMessage>> validationErrorMap =
                new HashMap<String, List<ErrorMessage>>();
        Map<String, List<ErrorMessage>> validationWarningMap =
                new HashMap<String, List<ErrorMessage>>();
        JScriptParser parser;
        try {
            parser =
                    ScriptParserUtil.validateScript(script,
                            destList,
                            strategyList,
                            symbolTable,
                            varNameResolver,
                            validationErrorMap,
                            validationWarningMap,
                            scriptType);
            if (parser == null) {
                Set<Entry<String, List<ErrorMessage>>> errorSet =
                        validationErrorMap.entrySet();
                List<ErrorMessage> comprehensiveErrorList =
                        new ArrayList<ErrorMessage>();
                for (Entry<String, List<ErrorMessage>> entry : errorSet) {
                    String destName = entry.getKey();
                    if (destList.contains(destName)) {
                        comprehensiveErrorList.addAll(entry.getValue());
                    }
                }
                throw new FieldParserException(
                        Messages.JavaScriptExpressionParser_ScriptParserNotFound_message,
                        comprehensiveErrorList);
            }
        } catch (Exception e) {
            throw new FieldParserException(e.getMessage());
        }
        ISymbolTable parserSymbolTable = parser.getSymbolTable();
        // getting the list of process data/local vars in use
        List<String> inUseVarList = parserSymbolTable.getVariablesInUse();
        // System.out.println("The variables in use are "+inUseVarList);

        symbolTable.dispose();
        symbolTable = null;

        return inUseVarList;
    }

    /**
     * @author nwilson
     */
    class ReferenceWatcher extends ScriptableObject {

        /** Default serial ID. */
        private static final long serialVersionUID = 1L;

        /** The parent scope. */
        private Scriptable parent;

        /** The collected names. */
        private List<String> names;

        /** The current path. */
        private String path;

        /** The current parameter. */
        private String current;

        /**
         * @param parent
         *            The parent scope.
         */
        public ReferenceWatcher(Scriptable parent) {
            this.parent = parent;
            names = new ArrayList<String>();
        }

        /**
         * @param parent
         *            The parent scope.
         * @param names
         *            A list of names to add to.
         * @param name
         *            The current name.
         */
        public ReferenceWatcher(ReferenceWatcher parent, List<String> names,
                String name) {
            this.parent = parent;
            this.names = names;
            current = name;
            if (parent.path == null) {
                path = name;
            } else {
                path = parent.path + "." + name; //$NON-NLS-1$
            }
        }

        /**
         * @param parent
         *            The parent scope.
         * @param names
         *            A list of names to add to.
         * @param index
         *            The current index.
         */
        public ReferenceWatcher(ReferenceWatcher parent, List<String> names,
                int index) {
            this.parent = parent;
            this.names = names;
            current = "[" + index + "]"; //$NON-NLS-1$ //$NON-NLS-2$
            path = parent.path + current;
        }

        /**
         * @return The collected names.
         */
        public Collection<String> getNames() {
            return names;
        }

        /**
         * @param name
         *            The parameter to find.
         * @param scope
         *            The scope.
         * @return The value of the parameter.
         * @see org.mozilla.javascript.ScriptableObject#get(java.lang.String,
         *      org.mozilla.javascript.Scriptable)
         */
        @Override
        public Object get(String name, Scriptable scope) {
            Object o = null;
            if (!(parent instanceof ReferenceWatcher)) {
                o = parent.get(name, scope);
            } else {
                o = UniqueTag.NOT_FOUND;
            }
            if (o != null) {
                if (o.equals(UniqueTag.NOT_FOUND)) {
                    String value;
                    if (path == null) {
                        value = name;
                    } else {
                        value = path + "." + name; //$NON-NLS-1$
                    }
                    if (!names.isEmpty()
                            && names.get(names.size() - 1).equals(path)) {
                        names.remove(names.size() - 1);
                    }
                    names.add(value);
                    o = new ReferenceWatcher(this, names, name);
                }
            }
            return o;
        }

        /**
         * @param index
         *            The parameter index.
         * @param scope
         *            The scope.
         * @return The object at the index.
         * @see org.mozilla.javascript.ScriptableObject#get(int,
         *      org.mozilla.javascript.Scriptable)
         */
        @Override
        public Object get(int index, Scriptable scope) {
            String i = "[" + index + "]"; //$NON-NLS-1$ //$NON-NLS-2$
            if (!names.isEmpty() && names.get(names.size() - 1).equals(path)) {
                names.remove(names.size() - 1);
                names.add(path + i);
            }
            return new ReferenceWatcher(this, names, index);
        }

        /**
         * @return The class name.
         * @see org.mozilla.javascript.ScriptableObject#getClassName()
         */
        @Override
        public String getClassName() {
            return "ReferenceWatcher"; //$NON-NLS-1$
        }

        /**
         * @param cls
         *            The class.
         * @return "0".
         * @see org.mozilla.javascript.ScriptableObject#getDefaultValue(java.lang.Class)
         */
        @Override
        public Object getDefaultValue(Class cls) {
            return "0"; //$NON-NLS-1$
        }

    }
}
