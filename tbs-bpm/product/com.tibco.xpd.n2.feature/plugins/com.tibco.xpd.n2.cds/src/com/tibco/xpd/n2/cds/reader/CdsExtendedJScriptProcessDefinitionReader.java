/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.n2.cds.reader;

import java.net.URL;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.swt.graphics.Image;
import org.eclipse.uml2.uml.Class;

import com.tibco.xpd.n2.cds.CDSActivator;
import com.tibco.xpd.n2.cds.CdsConsts;
import com.tibco.xpd.n2.cds.script.N2JScriptTypeResolver;
import com.tibco.xpd.n2.cds.utils.CDSUtils;
import com.tibco.xpd.script.model.JsConsts;
import com.tibco.xpd.script.model.client.DefaultJsClassDefinitionReader;
import com.tibco.xpd.script.model.client.ITypeResolver;
import com.tibco.xpd.script.model.internal.client.IContentAssistIconProvider;
import com.tibco.xpd.script.ui.internal.IProcessJsDefinitionReader;

/**
 * @author mtorres
 */
public class CdsExtendedJScriptProcessDefinitionReader extends
        DefaultJsClassDefinitionReader implements IProcessJsDefinitionReader {

    private static final String DEFAULT_MULTIPLE_CLASS_NAME = "List";//$NON-NLS-1$

    private ITypeResolver typeResolver = null;

    @Override
    protected URI getURI() {
        URL entry =
                CDSActivator.getDefault().getBundle()
                        .getEntry(CdsConsts.CDS_MODEL_FILE_NAME);
        return URI.createURI(entry.toExternalForm());
    }

    public Image getIcon() {
        Image image =
                CDSActivator.getDefault().getImageRegistry()
                        .get(CdsConsts.CDS_CLASS);
        return image;
    }

    @Override
    public Class getMultipleClass(Class umlClass, List<Class> allUmlClasses) {
        // All classes will have the list
        for (Class classCandidate : allUmlClasses) {
            if (classCandidate != null && classCandidate.getName() != null) {
                if (DEFAULT_MULTIPLE_CLASS_NAME
                        .equals(classCandidate.getName())
                        || JsConsts.PAGINATEDLIST.equals(classCandidate
                                .getName())) {
                    return classCandidate;
                }
            }
        }
        return null;
    }

    @Override
    protected IContentAssistIconProvider getContentAssistIconProvider() {
        return CDSUtils.getCdsContentAssistIconProvider();
    }

    @Override
    public ITypeResolver getTypeResolver() {
        if (typeResolver == null) {
            typeResolver = new N2JScriptTypeResolver();
            typeResolver.setSupportedJsClasses(getSupportedClasses());
        }
        return typeResolver;
    }

}
