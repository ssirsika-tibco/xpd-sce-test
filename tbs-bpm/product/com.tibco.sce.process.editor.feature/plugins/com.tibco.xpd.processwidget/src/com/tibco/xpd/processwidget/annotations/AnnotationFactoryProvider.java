package com.tibco.xpd.processwidget.annotations;

/**
 * Annotation provider factory for use with the
 * <code>com.tibcop.xpd.process.widget.diagramAnnotation</code> extension point.
 * 
 * @author aallway
 * @since v1
 * @deprecated Use of the new {@link AnnotationFactoryProviderEx} class is
 *             preferred (and gives access to process diagram adapter / viewer.
 */
@Deprecated
public interface AnnotationFactoryProvider {

    AnnotationFactory getAnnotationFactory();

}
