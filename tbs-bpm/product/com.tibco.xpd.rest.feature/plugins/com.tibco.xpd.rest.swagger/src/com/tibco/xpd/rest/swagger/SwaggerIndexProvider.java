/*
 * Copyright (c) 2004-2024. Cloud Software Group, Inc. All Rights Reserved.
 */
package com.tibco.xpd.rest.swagger;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IResource;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.indexer.IndexerItem;
import com.tibco.xpd.resources.indexer.IndexerItemImpl;
import com.tibco.xpd.resources.indexer.WorkingCopyIndexProvider;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.resources.projectconfig.SpecialFolders;
import com.tibco.xpd.rest.schema.ui.RestConstants;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.PathItem.HttpMethod;
import io.swagger.v3.oas.models.Paths;

/**
 * 
 * Provides index for Swagger working copies. Indexes all the methods (under all paths) in the Swagger definition
 *
 * @author nkelkar
 * @since Aug 7, 2024
 */
public class SwaggerIndexProvider implements WorkingCopyIndexProvider {

    /**
	 * Swagger indexer ID.
	 */
	public static final String	INDEXER_ID = "com.tibco.xpd.rest.indexer.swagger";	//$NON-NLS-1$

    /**
     * Indexer property key for http method (GET, POST,...).
     */
    public static final String HTTP_METHOD = "httpMethod"; //$NON-NLS-1$

    /**
     * Indexer property key for resource name.
     */
    public static final String RESOURCE_NAME = "resourceName"; //$NON-NLS-1$

    /**
     * Indexer property key for service name.
     */
    public static final String SERVICE_NAME = "serviceName"; //$NON-NLS-1$

    /**
     * Indexer property key for method id.
     */
    public static final String METHOD_ID = "methodId"; //$NON-NLS-1$

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<IndexerItem> getIndexItems(WorkingCopy wc) {
        if (isInSpecialFolder(wc)) {
            EObject root = wc.getRootElement();
			if (root instanceof SwaggerObjectImpl)
			{
				return getSwaggerIndexerItems((SwaggerObjectImpl) root);
            }
        }
        return Collections.emptyList();
    }

    /**
	 * Checks if the working copy is in the rest special folder.
	 * 
	 * @param wc
	 * @return
	 */
    private boolean isInSpecialFolder(WorkingCopy wc) {
        IResource eclipseResource = wc.getEclipseResources().get(0);
        ProjectConfig projectConfig =
                XpdResourcesPlugin.getDefault()
                        .getProjectConfig(eclipseResource.getProject());

        if (projectConfig != null) {
            SpecialFolders specialFolders = projectConfig.getSpecialFolders();
            SpecialFolder sf =
                    specialFolders.getFolderContainer(eclipseResource);
            return sf != null
                    && RestConstants.REST_SPECIAL_FOLDER_KIND.equals(sf
                            .getKind());
        }
        return false;
    }

	/**
	 * Returns the indexer items for a given Swagger root element (SwaggerObjectImpl)
	 * 
	 * @param swaggerObject
	 * @return a collection of indexer items for the swagger object.
	 */
	private Collection<IndexerItem> getSwaggerIndexerItems(final SwaggerObjectImpl swaggerObject)
	{
		List<IndexerItem> items = new ArrayList<>();

		IResource resource = swaggerObject.getResource();
		OpenAPI openAPIModel = swaggerObject.getModel();

		if (openAPIModel != null)
		{
			Paths paths = openAPIModel.getPaths();

			if (paths != null)
			{
				Iterator<String> it = paths.keySet().iterator();

				while (it.hasNext())
				{
					String aPath = it.next();
					PathItem pathItem = paths.get(aPath);

					if (pathItem != null)
					{
						Map<HttpMethod, Operation> operations = pathItem.readOperationsMap();

						if (operations != null)
						{
							Iterator<HttpMethod> op = operations.keySet().iterator();
							while (op.hasNext())
							{
								HttpMethod aHttpMethod = op.next();
								Operation aOperation = operations.get(aHttpMethod);

								if (aOperation != null)
								{
									Map<String, String> additionalAttributes = new HashMap<>();
									additionalAttributes.put(HTTP_METHOD, aHttpMethod.name());
									additionalAttributes.put(RESOURCE_NAME, aPath);
									additionalAttributes.put(SERVICE_NAME, openAPIModel.getInfo().getTitle());

									SwaggerOperationReference methodId = new SwaggerOperationReference(aPath, aHttpMethod);

									additionalAttributes.put(METHOD_ID, methodId.getSwaggerOperationRef());

									String indexName = ""; //$NON-NLS-1$
									/*
									 * Nikita ACE-8249 If a summary is not defined, fallback to the description which is
									 * always defined for all Swagger/OpenAPI versions;
									 */
									if (aOperation.getSummary() != null)
									{
										indexName = aOperation.getSummary();
									}
									else if (aOperation.getDescription() != null)
									{
										indexName = aOperation.getDescription();

									}
									else
									{
										indexName = methodId.getSwaggerOperationRef();
									}

									IndexerItemImpl indexerItem = new IndexerItemImpl(indexName,
											aOperation.getClass().getName(),
											getURI(resource, methodId.getSwaggerOperationRef()), additionalAttributes); // $NON-NLS-1$

									items.add(indexerItem);
								}
							}
						}
					}
				}
			}
		}
		return items;
	}

	/**
	 * @see com.tibco.xpd.resources.indexer.WorkingCopyIndexProvider#isAffectingEvent(java.beans.PropertyChangeEvent)
	 * 
	 * @param event
	 * @return
	 */
	@Override
	final public boolean isAffectingEvent(PropertyChangeEvent event)
	{
		return true;
	}

	/**
	 * Returns the URI (with operationID fragment appended) for a given resource.
	 */
	private String getURI(IResource aResource, String operationId)
	{
		if (aResource != null)
		{
			URI resourceURI = URI.createPlatformResourceURI(aResource.getFullPath().toString(), true);
			URI uri = resourceURI.appendFragment(operationId);
			return uri.toString();
		}
		return null;
	}
}
