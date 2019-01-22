package com.tibco.xpd.processeditor.xpdl2.extensions.taskBinding;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.tibco.xpd.processwidget.adapters.TaskType;

public class Bindings {
	private final Set<TaskBinding> tasks;
	private final MatrixMap<TaskType, String> tasksByType;
	private final MatrixMap<DestinationAndTypeKey, TaskReference> tasksByKey;


	Bindings(){
		tasks=new HashSet<TaskBinding>();
		tasksByType=new MatrixMap<TaskType, String>();
		tasksByKey=new MatrixMap<DestinationAndTypeKey, TaskReference>();
	}

	public void addAll(Set<TaskBinding> tasks){
		for (TaskBinding binding : tasks) {
			add(binding);
		}
	}

	public void add(TaskBinding binding){
		this.tasks.add(binding);
		String dId=binding.getDestination();
		Set<TaskReference> tIds=binding.tasks();
		for (TaskReference tRef : tIds) {
			Set<TaskType> types = tRef.getTypes();
			for (TaskType type : types) {
				DestinationAndTypeKey key=new DestinationAndTypeKey(dId,type);
				tasksByKey.put(key, tRef);
				tasksByType.put(type, tRef.getId());
			}
		}
	}

	public Set<TaskReference> getTasks(Collection<String> destinations, TaskType taskType){
		Set<TaskReference> result=new HashSet<TaskReference>();
		for (String destination : destinations) {
			Set<TaskReference> tasks = getTasks(destination, taskType);
			if(tasks!=null){
				result.addAll(tasks);
			}
		}
		return result;
	}

	public Set<TaskReference> getTasks(String destination, TaskType taskType){
		DestinationAndTypeKey key = new DestinationAndTypeKey(destination,taskType);
		return tasksByKey.get(key);
	}

	public Map<TaskType, Set<String>> getTasks(String destination){
		MatrixMap<TaskType, String> result=new MatrixMap<TaskType, String>();
		for (DestinationAndTypeKey key : tasksByKey.keySet()) {
			if(key.destination.equals(destination)){
				for (TaskReference reference : tasksByKey.get(key)) {
					result.put(key.type,reference.getId());
				}
			}
		}

		return result.toMap();
	}

	public Set<String> getTasks(TaskType taskType){
		return tasksByType.get(taskType);
	}

	public Set<TaskBinding> getBindings(){
		return Collections.unmodifiableSet(tasks);
	}

	public Set<TaskReference> getAllGeneric(){
		throw new UnsupportedOperationException();
	}

	private class DestinationAndTypeKey {

		private final String destination;
		private final TaskType type;

		public DestinationAndTypeKey(String id, TaskType type) {
			this.destination = id;
			this.type = type;
		}

		@Override
		public int hashCode() {
			final int PRIME = 31;
			int result = 1;
			result = PRIME * result + ((destination == null) ? 0 : destination.hashCode());
			result = PRIME * result + ((type == null) ? 0 : type.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			final DestinationAndTypeKey other = (DestinationAndTypeKey) obj;
			if (destination == null) {
				if (other.destination != null)
					return false;
			} else if (!destination.equals(other.destination))
				return false;
			if (type == null) {
				if (other.type != null)
					return false;
			} else if (!type.equals(other.type))
				return false;
			return true;
		}

		@Override
		public String toString() {
			StringBuilder sb=new StringBuilder();
			sb.append("["); //$NON-NLS-1$
			sb.append(destination);
			sb.append(","); //$NON-NLS-1$
			sb.append(type);
			sb.append("]"); //$NON-NLS-1$
			return sb.toString();
		}
	}

	private class MatrixMap<K, V> {
		private Map<K, Set<V>> map;

		public MatrixMap() {
			map = new HashMap<K,Set<V>>();
		}

		/**
		 * Copying constructor.
		 * @param orginal
		 */
		MatrixMap(MatrixMap<K, V> orginal) {
			map = new HashMap<K,Set<V>>();
			for (K key : orginal.keySet()) {
				Set<V> values = orginal.get(key);
				Set<V> valuesCopy= new HashSet<V>(values);
				putAll(key,valuesCopy);
			};
		}

		private void putAll(K key, Set<V> values) {
			map.put(key,values);
		}

		private Set<K> keySet() {
			return map.keySet();
		}

		public Map<K, Set<V>> toMap(){
			return new HashMap<K, Set<V>>(map);
		}

		void put(K key, V value) {
			Set<V> v;
			if(map.containsKey(key)){
				v=map.get(key);
			} else {
				v = new HashSet<V>();
				map.put(key,v);
			}
			v.add(value);
		}

		Set<V> get(K key){
			return map.get(key);
		}

		@Override
		public String toString() {
			return map.toString();
		}
	}
}
