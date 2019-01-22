package com.tibco.xpd.processeditor.xpdl2.extensions.taskBinding;

import java.util.Set;

import com.tibco.xpd.processwidget.adapters.TaskType;

class TaskReferenceImpl implements TaskReference {

	private final Set<TaskType> types;
	private final String id;

	public TaskReferenceImpl(String id,Set<TaskType> types) {
		if(id==null){
			throw new NullPointerException("Task id is null."); //$NON-NLS-1$
		}
		if(types==null){
			throw new NullPointerException("Task type is null."); //$NON-NLS-1$
		}
		this.id = id;
		this.types = types;
	}

	public String getId(){
		return id;
	}

	public Set<TaskType> getTypes() {
		return types;
	}

	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((id == null) ? 0 : id.hashCode());
		result = PRIME * result + ((types == null) ? 0 : types.hashCode());
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
		final TaskReferenceImpl other = (TaskReferenceImpl) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (types == null) {
			if (other.types != null)
				return false;
		} else if (!types.equals(other.types))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("id: "); //$NON-NLS-1$
		builder.append(id);
		builder.append(" type: "); //$NON-NLS-1$
		builder.append(types.toString());
		return builder.toString();
	}



}
