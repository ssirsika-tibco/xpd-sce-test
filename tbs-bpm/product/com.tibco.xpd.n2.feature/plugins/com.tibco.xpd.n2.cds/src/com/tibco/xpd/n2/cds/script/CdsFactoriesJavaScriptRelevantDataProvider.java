/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.n2.cds.script;

import java.util.List;

import org.eclipse.uml2.uml.Package;

import com.tibco.xpd.n2.cds.utils.CDSUtils;
import com.tibco.xpd.process.js.model.DefaultJavaScriptRelevantDataProvider;
import com.tibco.xpd.script.model.client.IScriptRelevantData;

/**
 * @author mtorres
 */
public class CdsFactoriesJavaScriptRelevantDataProvider
        extends DefaultJavaScriptRelevantDataProvider {

    @Override
    public List<IScriptRelevantData> getScriptRelevantDataList() {


        List<Package> referencedBomPackages =
                CDSUtils.getReferencedBomPackages(getActivity(),
                        getProcess(),
                        getPackage(),
                        getAssociatedProcessRelevantData());

        List<IScriptRelevantData> factoryAndPkgClasses =
                AceCdsFactoriesWrapperFactory.getDefault()
                        .createProcessDataWrapper(referencedBomPackages);

        return factoryAndPkgClasses;
    }

}
