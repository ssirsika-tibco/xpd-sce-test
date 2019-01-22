package com.tibco.xpd.implementer.resources.xpdl2.properties.filter;

public class AndConjunction extends Conjunction {

	AndConjunction() {}

	@Override
	Operator getOperator() {
		return new AndOp();
	}

	class AndOp implements Operator {

		private boolean result=true;

		public void add(boolean result) {
			this.result = this.result && result;
		}

		public boolean getResult() {
			return result;
		}
	}
}
