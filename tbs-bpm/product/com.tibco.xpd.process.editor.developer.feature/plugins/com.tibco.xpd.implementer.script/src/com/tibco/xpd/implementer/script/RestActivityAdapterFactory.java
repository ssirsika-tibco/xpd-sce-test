/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.implementer.script;

import java.util.Arrays;
import java.util.List;

import com.tibco.xpd.xpdl2.Activity;

/**
 * Adapter factory to create message adapters for a REST Service activity.
 *
 * @author jarciuch
 * @since 7 Apr 2015
 */
public class RestActivityAdapterFactory {

    private static final String REST_SERVICE_IMPL_NAME = "RESTService"; //$NON-NLS-1$

    /**
     * List of supported rest activity message adapters.
     */
    private final List<? extends RestActivityMessageProvider> adapters = Arrays
            .asList(new RestServiceTaskMessageAdapter(),
                    new RestSendTaskMessageAdapter(),
                    new RestEventMessageAdapter());

    /**
     * Singleton instance.
     */
    private static final RestActivityAdapterFactory INSTANCE =
            new RestActivityAdapterFactory();

    /**
     * Prevents instantiation. Use {@link #getInstance()} to obtain factory
     * instance.
     */
    private RestActivityAdapterFactory() {
    }

    /**
     * An instance of this factory.
     * 
     * @return an instance of this factory.
     */
    public static RestActivityAdapterFactory getInstance() {
        return INSTANCE;
    }

    /**
     * Returns a message adapter for a rest activity or 'null' if not supported.
     * 
     * @param activity
     *            the activity to get adapter for.
     * @return message adapter specific to the type of activity.
     */
    public RestActivityMessageProvider getMessageProvider(Activity activity) {
        for (RestActivityMessageProvider adapter : adapters) {
            if (adapter.isSupported(activity)) {
                return adapter;
            }
        }
        throw new RuntimeException(
                String.format("Message adapter for '%1$s' activity is not supported.", //$NON-NLS-1$
                        activity.getName()));
    }

    /**
     * Returns 'true' if a rest activity that can be adapted to the
     * {@link RestActivityMessageProvider}.
     * 
     * @param activity
     *            the activity to test.
     * @return 'true' if a rest activity that can be adapted to the
     *         {@link RestActivityMessageProvider}
     */
    public boolean isSupportedActivity(Activity activity) {
        for (RestActivityMessageProvider adapter : adapters) {
            if (adapter.isSupported(activity)) {
                return true;
            }
        }
        return false;
    }

    /* package */static String getRestServiceImplName() {
        return REST_SERVICE_IMPL_NAME;
    }

}
