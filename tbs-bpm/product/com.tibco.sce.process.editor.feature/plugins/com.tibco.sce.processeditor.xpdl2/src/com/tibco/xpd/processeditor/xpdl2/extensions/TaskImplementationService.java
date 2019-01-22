package com.tibco.xpd.processeditor.xpdl2.extensions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.Platform;

import com.tibco.xpd.processeditor.xpdl2.Xpdl2ProcessEditorPlugin;
import com.tibco.xpd.processwidget.ProcessWidgetPlugin;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.logger.LoggerFactory;
import com.tibco.xpd.script.ui.internal.extension.CertifiedContributions;
import com.tibco.xpd.script.ui.internal.extension.CertifiedTaskImpl;

/**
 * <p>
 * Note that the extension point was originally in processwidget but has been
 * deprecated and moved here to processeditor. We still support contributions to
 * the deprecated processwidget extension point so must handle both.
 * 
 * @author aallway
 * 
 */
public class TaskImplementationService {

    private static final String TASKIMPLEMENTATION_EXTENSIONPOINT =
            "taskImplementation"; //$NON-NLS-1$

    public final static TaskImplementationService INSTANCE =
            new TaskImplementationService();

    private final Logger log =
            LoggerFactory.createLogger(Xpdl2ProcessEditorPlugin.ID);

    private TaskImplementationService() {
    }

    public synchronized Implementations getImplementations() {
        Implementations implementations = null;
        try {
            implementations = loadImplementations();
        } catch (NullPointerException ex) {
            log.error(ex);
        } catch (Exception ex) {
            log.error(ex);
            throw new RuntimeException(ex);
        }
        return implementations;
    }

    private Implementations loadImplementations() {
        Implementations implementations = new Implementations();

        //
        // load contributions to OLD processwidget extension point.
        //
        IExtensionPoint point =
                Platform.getExtensionRegistry()
                        .getExtensionPoint(ProcessWidgetPlugin.ID,
                                TASKIMPLEMENTATION_EXTENSIONPOINT);
        if (point != null) {
            IExtension[] extensions = point.getExtensions();
            if (extensions != null) {
                for (IExtension ext : extensions) {
                    IConfigurationElement[] elements = getValidElements(ext);
                    if (elements != null) {
                        for (IConfigurationElement elem : elements) {
                            // Add the section to the map
                            TaskImplementationElement tiElem =
                                    new TaskImplementationElement(elem);
                            implementations.add(tiElem);
                        }
                    }
                }
            }
        }

        //
        // And then new processeditor extension point
        //
        point =
                Platform.getExtensionRegistry()
                        .getExtensionPoint(Xpdl2ProcessEditorPlugin.ID,
                                TASKIMPLEMENTATION_EXTENSIONPOINT);
        if (point != null) {
            IExtension[] extensions = point.getExtensions();
            if (extensions != null) {
                for (IExtension ext : extensions) {
                    IConfigurationElement[] elements = getValidElements(ext);
                    if (elements != null) {
                        for (IConfigurationElement elem : elements) {
                            // Add the section to the map
                            TaskImplementationElement tiElem =
                                    new TaskImplementationElement(elem);
                            implementations.add(tiElem);
                        }
                    }
                }
            }
        }
        return implementations;

    }

    private IConfigurationElement[] getValidElements(IExtension ext) {
        List<IConfigurationElement> result =
                new ArrayList<IConfigurationElement>();
        IConfigurationElement[] configurationElements =
                ext.getConfigurationElements();
        for (IConfigurationElement element : configurationElements) {
            String id = element.getAttribute(TaskImplementationElement.ATT_ID);
            String type =
                    element
                            .getAttribute(TaskImplementationElement.ATT_TASKTYPE);
            CertifiedTaskImpl c = CertifiedContributions.getCertifiedTaskImpl();
            if (c.isCertified(id, type)) {
                result.add(element);
            }
        }
        return result.toArray(new IConfigurationElement[result.size()]);
    }

    public class Implementations {
        private final MatrixMap<String, TaskType> byId;

        private final MatrixMap<TaskType, TaskImplementationElement> byType;

        private final Collection<TaskImplementationElement> elements;

        private Implementations() {
            byId = new MatrixMap<String, TaskType>();
            byType = new MatrixMap<TaskType, TaskImplementationElement>();
            elements = new ArrayList<TaskImplementationElement>();
        }

        public void add(TaskImplementationElement ti) {
            if (ti == null) {
                throw new NullPointerException(
                        "TaskImplementationElement cannot be null."); //$NON-NLS-1$
            }
            String id = ti.getId();
            byId.put(id, ti.getTaskType());
            TaskType type = ti.getTaskType();
            byType.put(type, ti);
            elements.add(ti);
        }

        public Set<TaskType> getTaskTypes(String taskId) {
            if (taskId == null) {
                throw new NullPointerException("Task id cannot be null."); //$NON-NLS-1$
            }
            Set<TaskType> result;
            if (byId.containsKey(taskId)) {
                result = byId.get(taskId);
            } else {
                result = Collections.emptySet();
            }
            return result;
        }

        public Set<TaskImplementationElement> getTasks(TaskType taskType) {
            if (taskType == null) {
                throw new NullPointerException("Task type cannot be null."); //$NON-NLS-1$
            }
            return byType.get(taskType);
        }

        public Collection<TaskImplementationElement> getElements() {
            return elements;
        }
    }

    private class MatrixMap<K, V> {
        private Map<K, Set<V>> map;

        public MatrixMap() {
            map = new HashMap<K, Set<V>>();
        }

        void put(K key, V value) {
            Set<V> v;
            if (map.containsKey(key)) {
                v = map.get(key);
            } else {
                v = new HashSet<V>();
                map.put(key, v);
            }
            v.add(value);
        }

        Set<V> get(K key) {
            return map.get(key);
        }

        boolean containsKey(K key) {
            return map.containsKey(key);
        }

        @Override
        public String toString() {
            return map.toString();
        }
    }

}
