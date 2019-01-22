package com.tibco.xpd.implementer.resources.xpdl2.properties.filter;

import org.eclipse.jface.viewers.IFilter;

import com.tibco.xpd.processwidget.adapters.TaskType;

class CompositeFilterImpl implements IFilter {

	private final IFilter[] filters;
	private final Conjunction conj;

	public CompositeFilterImpl(Conjunction operator, IFilter[] filters) {
		this.conj = operator;
		this.filters = filters;
	}

	public boolean select(Object toTest) {
		Operator operator=conj.getOperator();
		for (IFilter filter : filters) {
			boolean filterResult = filter.select(toTest);
			operator.add(filterResult);
		}
		return operator.getResult();
	}

}
