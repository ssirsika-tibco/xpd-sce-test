package com.tibco.xpd.process.xpath.parser.antlr;

import org.jaxen.Context;
import org.jaxen.expr.DefaultExpr;
import org.jaxen.expr.NumberExpr;

public class TCDefaultNumberExpr extends DefaultExpr implements NumberExpr {
    /**
     * 
     */
    private static final long serialVersionUID = -6021898973386269611L;

    boolean isInteger;

    private int iNumber;

    private Double number;

    public TCDefaultNumberExpr(Double number) {
        this.number = number;
        isInteger = false;
    }

    public TCDefaultNumberExpr(Integer iNumber) {
        this.iNumber = iNumber;
        isInteger = true;
    }

    public Number getNumber() {
        if (isInteger()) {
            return this.iNumber;
        } else {
            return this.number;
        }
    }

    public String toString() {
        return "[(DefaultNumberExpr): " + getNumber() + "]";
    }

    public String getText() {
        return getNumber().toString();
    }

    public Object evaluate(Context context) {
        return getNumber();
    }

    public boolean isInteger() {
        return isInteger;
    }

}
