/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */
package com.tibco.xpd.rql.parser.eval;

import java.util.regex.Pattern;

import com.tibco.n2.de.rql.parser.base.ComparisonNode;
import com.tibco.n2.de.rql.parser.base.Operator;

/**
 * Compares the values of a {@link ComparisonNode} with a given value. Allows
 * the ComparisonNode to be passed to the EntityDao.findByXXX() method.
 * 
 * @copyright 2011 TIBCO Software Inc.
 * @author pwatson
 * @version 1.3
 */
class ComparisonNodeComparator
{
    private static final char WILDCARD = '*';
    private static final char ANYCHAR = '?';
    private static final char ESCAPE = '\\';

    private ComparisonNode comparisonNode;

    private Boolean containsWildcard;

    /**
     * Holds the value of this Comparator as it would be applied to regulat
     * expression. This is computed by the {@link #containsWildcard()} method.
     * If the expression contains no wildcards, this property will be null.
     */
    private Pattern regex;

    ComparisonNodeComparator(ComparisonNode aComparisonNode)
    {
        comparisonNode = aComparisonNode;
    }

    /**
     * Tests whether the comparison should be case-insensitive.
     */
    private boolean isCaseInsensitve()
    {
        return(false);
    }

    /**
     * Tests whether the given string is null, empty or contains only whitespace.
     * @param aStr the string to be tested.
     * @return <code>true</code> if the string is considered to be empty.
     */
    private boolean isEmpty(String aStr)
    {
        if ((aStr != null) && (aStr.length() > 0))
        {
            // if it only contains whitespace
            for (int i = 0, n = aStr.length(); i < n; i++)
            {
                if (aStr.charAt(i) != ' ')
                {
                    return(false);
                }
            }
        }

        return(true);
    }

    public boolean isNull()
    {
        return((comparisonNode == null) ||
               (isEmpty(comparisonNode.getValue())));
    }

    /**
     * Tests whether this comparator will match NO values that it is presented.
     * This allows for some optimizing on the DAO's side, by allowing it to
     * avoid calling the {@link #eval(String, String)} method.
     *
     * @return <code>true</code> if this comparator will compare negatively to
     *         whatever value it is presented with.
     */
    public boolean isMatchNone()
    {
        if (isEmpty(comparisonNode.getValue()))
        {
            Operator operator = comparisonNode.getOperator();
            if ((operator == Operator.EQ) ||
                (operator == Operator.LT) ||
                (operator == Operator.LTEQ))
            {
                return(true);
            }
        }

        return(false);
    }

    /**
     * Tests whether this comparator will match all values that it is presented.
     * This allows for some optimizing on the DAO's side, by allowing it to
     * avoid calling the {@link #eval(String, String)} method.
     *
     * @return <code>true</code> if this comparator will compare positively to
     *         whatever value it is presented with.
     */
    public boolean isMatchAll()
    {
        if (isEmpty(comparisonNode.getValue()))
        {
            Operator operator = comparisonNode.getOperator();
            if ((operator == Operator.GT) ||
                (operator == Operator.GTEQ) ||
                (operator == Operator.NEQ))
            {
                return(true);
            }
        }

        else if ((comparisonNode.getValue().length() == 1) &&
                 (comparisonNode.getValue().charAt(0) == ComparisonNodeComparator.WILDCARD))
        {
            return(true);
        }

        return(false);
    }

    /**
     * Tests whether the value of the Comparator contains any wildcard
     * characters. The following wildcards characters recognised:
     * <ul>
     * <li>* - match one or more characters upto the next character in the
     * sequence, or the end of sequence.</li>
     * <li>? - match any character.</li>
     * </ul>
     * If a string is to contain a wildcard character as a literal, it should
     * be escaped by preceding it with the '\' character.
     *
     * @return <code>true</code> if the value contains a wildcard.
     */
    public boolean containsWildcard()
    {
        if (containsWildcard == null)
        {
            containsWildcard = Boolean.FALSE;

            if (! isEmpty(comparisonNode.getValue()))
            {
                StringBuilder pattern = new StringBuilder();

                String val = comparisonNode.getValue();
                boolean escaped = false;
                char c;
                for (int i = 0; i < val.length(); i++)
                {
                    c = val.charAt(i);

                    if (c == ComparisonNodeComparator.ESCAPE)
                    {
                        escaped = true;
                    }
                    else if ((! escaped) && (c == ComparisonNodeComparator.WILDCARD))
                    {
                        pattern.append(".*");
                        containsWildcard = Boolean.TRUE;
                        escaped = false;
                    }
                    else if ((! escaped) && (c == ComparisonNodeComparator.ANYCHAR))
                    {
                        pattern.append('.');
                        containsWildcard = Boolean.TRUE;
                        escaped = false;
                    }
                    else
                    {
                        pattern.append(c);
                        escaped = false;
                    }
                }

                if (containsWildcard == Boolean.TRUE)
                {
                    int flags = (isCaseInsensitve()) ?  Pattern.CASE_INSENSITIVE : 0;
                    regex = Pattern.compile(pattern.toString(), flags);
                }
            }
        }

        return(containsWildcard.booleanValue());
    }

    /**
     * Returns a regex form of the comparison value. If the value does not
     * contain any {@link #containsWildcard() wildcards}, the return value will
     * be <code>null</code>.
     */
    protected Pattern getRegularExpression()
    {
        return(containsWildcard() ? regex : null);
    }

    /**
     * Evaluates the comparison of the given value with that of the
     * ComparisonNode, according to the rules of this Comparator (i.e. its
     * operator)
     * <p>
     * The right-hand operand (parameter <code>B</code>) is the value as held by
     * this Comparator.
     * 
     * @param A the left-hand operand of the comparison.
     * @return <code>true</code> if the evaluation of the comparator's operator,
     *         against the two values, is positive.
     */
    public boolean eval(String A)
    {
        Operator op = comparisonNode.getOperator();
        if (op == null)
        {
            op = Operator.EQ;
        }

        if (isEmpty(A))
        {
            if (isEmpty(comparisonNode.getValue()))
            {
                return(true);
            }

            Operator operator = comparisonNode.getOperator();
            if ((operator == Operator.LT) ||
                (operator == Operator.LTEQ))
            {
                return(true);
            }

            return(false);
        }

        if (isMatchAll())
        {
            return(true);
        }

        if (isMatchNone())
        {
            return(false);
        }

        if (containsWildcard())
        {
            Pattern regex = getRegularExpression();
            boolean result = regex.matcher(A).matches();
            if (comparisonNode.getOperator() == Operator.NEQ)
            {
                return(! result);
            }
            return(result);
        }

        String value = comparisonNode.getValue();
        if (isCaseInsensitve())
        {
            value = value.toUpperCase();
            A = A.toUpperCase();
        }
        return(op.eval(A, value));
    }
}
