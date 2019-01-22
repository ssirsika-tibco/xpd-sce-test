package com.tibco.xpd.implementer.resources.xpdl2.properties.filter;

class OrConjunction extends Conjunction {

	OrConjunction() {}

	@Override
	Operator getOperator() {
		return new OrOp();
	}

	class OrOp implements Operator {
		private boolean result;

		public void add(boolean result) {
			this.result = this.result || result;
		}

		public boolean getResult() {
			return result;
		}

	}
}
