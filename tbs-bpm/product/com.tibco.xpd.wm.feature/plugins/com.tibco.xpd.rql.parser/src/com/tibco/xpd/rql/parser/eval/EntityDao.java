/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */
package com.tibco.xpd.rql.parser.eval;

import java.util.Collection;

import com.tibco.xpd.script.model.client.IScriptRelevantData;

public interface EntityDao
{

    public Collection<IScriptRelevantData> findByName(ComparisonNodeComparator aComparator);

    public Collection<IScriptRelevantData> findByMetaType(ComparisonNodeComparator aComparator);

    public Collection<IScriptRelevantData> findByGuid(ComparisonNodeComparator aComparator);

    public Collection<IScriptRelevantData> findByAttribute(ComparisonNodeComparator aComparator);

}