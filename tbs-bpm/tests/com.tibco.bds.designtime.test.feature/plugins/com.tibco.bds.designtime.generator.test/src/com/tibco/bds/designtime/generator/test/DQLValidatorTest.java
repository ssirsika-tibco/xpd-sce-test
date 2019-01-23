package com.tibco.bds.designtime.generator.test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.uml2.uml.Class;

import com.tibco.bds.designtime.api.BDSAPI;
import com.tibco.bds.designtime.api.exception.DQLValidationException;
import com.tibco.bds.designtime.api.validation.ValidationResult;
import com.tibco.xpd.resources.XpdResourcesPlugin;

/**
 * Unit tests for DQL validator
 * 
 * @author smorgan
 */
public class DQLValidatorTest extends BaseTest {
    public static final String VERSION = "1.0.0.1"; //$NON-NLS-1$

    public void testBadExpressions() {
        // Lexical error: mismatched quote
        runSyntaxBadCheck("attr = '"); //$NON-NLS-1$

        // Syntax error: no operator
        runSyntaxBadCheck("attr 23"); //$NON-NLS-1$
        
        // Use of reserved word 'type' without escaping with curly braces
        runSyntaxBadCheck("type = '1'"); //$NON-NLS-1$
        // ...and some other reserved words
        runSyntaxBadCheck("between = 1"); //$NON-NLS-1$
        runSyntaxBadCheck("order > 5"); //$NON-NLS-1$
        // ...and using as a parameter name
        runSyntaxBadCheck("attr = :type"); //$NON-NLS-1$
    }

    private void runSyntaxBadCheck(String dql) {
        try {
            List<ValidationResult> result = BDSAPI.validateDQL(null, dql);
            assertTrue("Expected errors while parsing [" + dql + "]", //$NON-NLS-1$ //$NON-NLS-2$
                    ValidationResult.containsErrors(result));
        } catch (DQLValidationException e) {
            e.printStackTrace();
            fail("Unexpected Exception: " + e.getClass().getName() + ": " //$NON-NLS-1$ //$NON-NLS-2$
                    + e.getLocalizedMessage());
        }
    }

    public void testGoodExpressions() {
        // These DQL statements should parse OK
        runGoodCheck(null, "attr = 'BDS'"); //$NON-NLS-1$
        runGoodCheck(null, "attr1[ALL].attr2 > 23"); //$NON-NLS-1$
        // Reserved word 'type' escaped with curly braces
        runGoodCheck(null, "{type} = '1'"); //$NON-NLS-1$
        // ...and some other reserved words
        runGoodCheck(null, "{between} = 1"); //$NON-NLS-1$
        runGoodCheck(null, "{order} > 5"); //$NON-NLS-1$
        // ...and using as a parameter name
        runGoodCheck(null, "attr = :{type}"); //$NON-NLS-1$
    }

    public void testNotCase() throws CoreException, IOException {
        doTestGDComp("Wheel", "diameter = 0", 1, 0); //$NON-NLS-1$ //$NON-NLS-2$
    }

    public void testBooleanExpression() throws CoreException, IOException {
        // Good operators
        doTestGDComp("BooleanCase", "booleanSA = true", 0, 0); //$NON-NLS-1$ //$NON-NLS-2$
        doTestGDComp("BooleanCase", "booleanSA != true", 0, 0); //$NON-NLS-1$ //$NON-NLS-2$
        doTestGDComp("BooleanCase", "booleanSA in (true, false)", 0, 0); //$NON-NLS-1$ //$NON-NLS-2$
        doTestGDComp("BooleanCase", "booleanSA not in (true, false)", 0, 0); //$NON-NLS-1$ //$NON-NLS-2$
        doTestGDComp("BooleanCase", "booleanSA = null", 0, 0); //$NON-NLS-1$ //$NON-NLS-2$
        doTestGDComp("BooleanCase", "booleanSA != null", 0, 0); //$NON-NLS-1$ //$NON-NLS-2$

        // Bad operators
        doTestGDComp("BooleanCase", "booleanSA > true", 1, 0); //$NON-NLS-1$ //$NON-NLS-2$
        doTestGDComp("BooleanCase", "booleanSA >= true", 1, 0); //$NON-NLS-1$ //$NON-NLS-2$
        doTestGDComp("BooleanCase", "booleanSA < true", 1, 0); //$NON-NLS-1$ //$NON-NLS-2$
        doTestGDComp("BooleanCase", "booleanSA <= true", 1, 0); //$NON-NLS-1$ //$NON-NLS-2$
        doTestGDComp("BooleanCase", "booleanSA between true and false", 1, 0); //$NON-NLS-1$ //$NON-NLS-2$

        // Good values
        doTestGDComp("BooleanCase", "booleanSA = true", 0, 0); //$NON-NLS-1$ //$NON-NLS-2$
        doTestGDComp("BooleanCase", "booleanSA = 'true'", 0, 0); //$NON-NLS-1$ //$NON-NLS-2$
        doTestGDComp("BooleanCase", "booleanSA = trUE", 0, 0); //$NON-NLS-1$ //$NON-NLS-2$
        doTestGDComp("BooleanCase", "booleanSA = FALSE", 0, 0); //$NON-NLS-1$ //$NON-NLS-2$
        doTestGDComp("BooleanCase", "booleanSA = FaLsE", 0, 0); //$NON-NLS-1$ //$NON-NLS-2$

        // Bad values
        doTestGDComp("BooleanCase", "booleanSA = 'mostly true'", 1, 0); //$NON-NLS-1$ //$NON-NLS-2$
        doTestGDComp("BooleanCase", "booleanSA = 'largely false'", 1, 0); //$NON-NLS-1$ //$NON-NLS-2$
    }
    
    public void testDurExpressions() throws CoreException, IOException
    {
        // Bad operators
        doTestGDComp("DurCase", "durSA = null", 1, 1); //$NON-NLS-1$ //$NON-NLS-2$
        doTestGDComp("DurCase", "durSA != null", 1, 1); //$NON-NLS-1$ //$NON-NLS-2$
        doTestGDComp("DurCase", "durSA > 'P1Y'", 1, 1); //$NON-NLS-1$ //$NON-NLS-2$
        doTestGDComp("DurCase", "durSA >= 'P1Y'", 1, 1); //$NON-NLS-1$ //$NON-NLS-2$
        doTestGDComp("DurCase", "durSA < 'P1Y'", 1, 1); //$NON-NLS-1$ //$NON-NLS-2$
        doTestGDComp("DurCase", "durSA <= 'P1Y'", 1, 1); //$NON-NLS-1$ //$NON-NLS-2$
        doTestGDComp("DurCase", "durSA in ('P1Y', 'P2Y')", 1, 1); //$NON-NLS-1$ //$NON-NLS-2$
        doTestGDComp("DurCase", "durSA not in ('P1Y', 'P2Y')", 1, 1); //$NON-NLS-1$ //$NON-NLS-2$
        doTestGDComp("DurCase", "durSA between 'P1Y' and 'P2Y'", 1, 1); //$NON-NLS-1$ //$NON-NLS-2$
        doTestGDComp("DurCase", "durSA not between 'P1Y' and 'P2Y'", 1, 1); //$NON-NLS-1$ //$NON-NLS-2$
        doTestGDComp("DurCase", "durSA type of 'Martin.Rohde'", 1, 1); //$NON-NLS-1$ //$NON-NLS-2$
    }

    public void testIntFixedExpressions() throws CoreException, IOException {

        // Good values
        doTestGDComp("IntFixedCase", "intFixedSA = 0", 0, 0); //$NON-NLS-1$ //$NON-NLS-2$
        doTestGDComp("IntFixedCase", "intFixedSA = 123456", 0, 0); //$NON-NLS-1$ //$NON-NLS-2$
        doTestGDComp("IntFixedCase", "intFixedSA = -123456", 0, 0); //$NON-NLS-1$ //$NON-NLS-2$
        // These are fine because they fit in a BigInteger
        doTestGDComp("IntFixedCase", //$NON-NLS-1$
                "intFixedSA = 123456789123456789123456789123456789", //$NON-NLS-1$
                0,
                0);
        doTestGDComp("IntFixedCase", //$NON-NLS-1$
                "intFixedSA = -123456789123456789123456789123456789", //$NON-NLS-1$
                0,
                0);

        // Bad values
        doTestGDComp("IntFixedCase", "intFixedSA = 'indigo'", 1, 0); //$NON-NLS-1$ //$NON-NLS-2$
        doTestGDComp("IntFixedCase", "intFixedSA = 1.2", 1, 0); //$NON-NLS-1$ //$NON-NLS-2$
        doTestGDComp("IntFixedCase", "intFixedSA = true", 1, 0); //$NON-NLS-1$ //$NON-NLS-2$

        // Good operators
        doTestGDComp("IntFixedCase", "intFixedSA > 100", 0, 0); //$NON-NLS-1$ //$NON-NLS-2$
        doTestGDComp("IntFixedCase", "intFixedSA >= 100", 0, 0); //$NON-NLS-1$ //$NON-NLS-2$
        doTestGDComp("IntFixedCase", "intFixedSA < 100", 0, 0); //$NON-NLS-1$ //$NON-NLS-2$
        doTestGDComp("IntFixedCase", "intFixedSA <= 100", 0, 0);  //$NON-NLS-1$//$NON-NLS-2$
        doTestGDComp("IntFixedCase", "intFixedSA between 100 and 10000", 0, 0); //$NON-NLS-1$ //$NON-NLS-2$
        doTestGDComp("IntFixedCase", "intFixedSA in (80,900,9856)", 0, 0); //$NON-NLS-1$ //$NON-NLS-2$
    }

    public void testIntSignedExpressions() throws CoreException, IOException {

        // Good values
        doTestGDComp("IntSignedCase", "intSignedSA = 0", 0, 0); //$NON-NLS-1$ //$NON-NLS-2$
        doTestGDComp("IntSignedCase", "intSignedSA = 123456", 0, 0); //$NON-NLS-1$ //$NON-NLS-2$
        doTestGDComp("IntSignedCase", "intSignedSA = -123456", 0, 0); //$NON-NLS-1$ //$NON-NLS-2$

        // Bad values
        doTestGDComp("IntSignedCase", "intSignedSA = 'indigo'", 1, 0); //$NON-NLS-1$ //$NON-NLS-2$
        doTestGDComp("IntSignedCase", "intSignedSA = 1.2", 1, 0); //$NON-NLS-1$ //$NON-NLS-2$
        doTestGDComp("IntSignedCase", "intSignedSA = true", 1, 0); //$NON-NLS-1$ //$NON-NLS-2$
        // These are too big for an Integer
        doTestGDComp("IntSignedCase", //$NON-NLS-1$
                "intSignedSA = 123456789123456789123456789123456789", //$NON-NLS-1$
                1,
                0);
        doTestGDComp("IntSignedCase", //$NON-NLS-1$
                "intSignedSA = -123456789123456789123456789123456789", //$NON-NLS-1$
                1,
                0);

        // Good operators
        doTestGDComp("IntSignedCase", "intSignedSA > 100", 0, 0); //$NON-NLS-1$ //$NON-NLS-2$
        doTestGDComp("IntSignedCase", "intSignedSA >= 100", 0, 0); //$NON-NLS-1$ //$NON-NLS-2$
        doTestGDComp("IntSignedCase", "intSignedSA < 100", 0, 0); //$NON-NLS-1$ //$NON-NLS-2$
        doTestGDComp("IntSignedCase", "intSignedSA <= 100", 0, 0); //$NON-NLS-1$ //$NON-NLS-2$
        doTestGDComp("IntSignedCase", "intSignedSA between 100 and 10000", 0, 0); //$NON-NLS-1$ //$NON-NLS-2$
        doTestGDComp("IntSignedCase", "intSignedSA in (80,900,9856)", 0, 0); //$NON-NLS-1$ //$NON-NLS-2$
    }

    public void testDecimalFixedExpressions() throws CoreException, IOException {

        // Good values
        doTestGDComp("DecFixedCase", "decFixedSA = 4", 0, 0); //$NON-NLS-1$ //$NON-NLS-2$
        doTestGDComp("DecFixedCase", "decFixedSA = 4.5", 0, 0); //$NON-NLS-1$ //$NON-NLS-2$
        doTestGDComp("DecFixedCase", "decFixedSA = 4.56789", 0, 0); //$NON-NLS-1$ //$NON-NLS-2$
        doTestGDComp("DecFixedCase", "decFixedSA = -4", 0, 0); //$NON-NLS-1$ //$NON-NLS-2$
        doTestGDComp("DecFixedCase", "decFixedSA = -4.5", 0, 0); //$NON-NLS-1$ //$NON-NLS-2$
        doTestGDComp("DecFixedCase", "decFixedSA = -4.56789", 0, 0); //$NON-NLS-1$ //$NON-NLS-2$
        doTestGDComp("DecFixedCase", //$NON-NLS-1$
                "decFixedSA = '123456789123456789123456789.1234'", //$NON-NLS-1$
                0,
                0);
        doTestGDComp("DecFixedCase", //$NON-NLS-1$
                "decFixedSA = '-123456789123456789123456789.1234'", //$NON-NLS-1$
                0,
                0);
        // Good for a BigDecimal (would be too big for a Double)
        doTestGDComp("DecFixedCase", "decFixedSA = '1.0E310'", 0, 0); //$NON-NLS-1$ //$NON-NLS-2$
        doTestGDComp("DecFixedCase", "decFixedSA = '-1.0E310'", 0, 0); //$NON-NLS-1$ //$NON-NLS-2$

        // Bad values
        doTestGDComp("DecFixedCase", "decFixedSA = true", 1, 0); //$NON-NLS-1$ //$NON-NLS-2$
        doTestGDComp("DecFixedCase", "decFixedSA = 'sugar'", 1, 0); //$NON-NLS-1$ //$NON-NLS-2$

        // Good operators
        doTestGDComp("DecFixedCase", "decFixedSA > 4.5", 0, 0); //$NON-NLS-1$ //$NON-NLS-2$
        doTestGDComp("DecFixedCase", "decFixedSA >= 4.5", 0, 0); //$NON-NLS-1$ //$NON-NLS-2$
        doTestGDComp("DecFixedCase", "decFixedSA < 4.5", 0, 0); //$NON-NLS-1$ //$NON-NLS-2$
        doTestGDComp("DecFixedCase", "decFixedSA <= 4.5", 0, 0); //$NON-NLS-1$ //$NON-NLS-2$
        doTestGDComp("DecFixedCase", "decFixedSA between -4.5 and 4", 0, 0); //$NON-NLS-1$ //$NON-NLS-2$
        doTestGDComp("DecFixedCase", "decFixedSA in (-7.1,8,-9.56)", 0, 0); //$NON-NLS-1$ //$NON-NLS-2$
    }

    public void testDecimalFloatExpressions() throws CoreException, IOException {

        // Good values
        doTestGDComp("DecFloatCase", "decSA = 4", 0, 0); //$NON-NLS-1$ //$NON-NLS-2$
        doTestGDComp("DecFloatCase", "decSA = 4.5", 0, 0); //$NON-NLS-1$ //$NON-NLS-2$
        doTestGDComp("DecFloatCase", "decSA = 4.56789", 0, 0); //$NON-NLS-1$ //$NON-NLS-2$
        doTestGDComp("DecFloatCase", "decSA = -4", 0, 0); //$NON-NLS-1$ //$NON-NLS-2$
        doTestGDComp("DecFloatCase", "decSA = -4.5", 0, 0); //$NON-NLS-1$ //$NON-NLS-2$
        doTestGDComp("DecFloatCase", "decSA = -4.56789", 0, 0); //$NON-NLS-1$ //$NON-NLS-2$
        doTestGDComp("DecFloatCase", //$NON-NLS-1$
                "decSA = '123456789123456789123456789123456789.1234'", //$NON-NLS-1$
                0,
                0);
        doTestGDComp("DecFloatCase", //$NON-NLS-1$
                "decSA = '-123456789123456789123456789123456789.1234'", //$NON-NLS-1$
                0,
                0);

        // Bad values
        doTestGDComp("DecFloatCase", "decSA = true", 1, 0); //$NON-NLS-1$ //$NON-NLS-2$
        doTestGDComp("DecFloatCase", "decSA = 'sugar'", 1, 0); //$NON-NLS-1$ //$NON-NLS-2$
        // Slightly too big for a Double
        doTestGDComp("DecFloatCase", "decSA = '1.0E310'", 1, 0); //$NON-NLS-1$ //$NON-NLS-2$
        doTestGDComp("DecFloatCase", "decSA = '1.0E310'", 1, 0); //$NON-NLS-1$ //$NON-NLS-2$

        // Good operators
        doTestGDComp("DecFloatCase", "decSA > 4.5", 0, 0); //$NON-NLS-1$ //$NON-NLS-2$
        doTestGDComp("DecFloatCase", "decSA >= 4.5", 0, 0); //$NON-NLS-1$ //$NON-NLS-2$
        doTestGDComp("DecFloatCase", "decSA < 4.5", 0, 0); //$NON-NLS-1$ //$NON-NLS-2$
        doTestGDComp("DecFloatCase", "decSA <= 4.5", 0, 0);  //$NON-NLS-1$//$NON-NLS-2$
        doTestGDComp("DecFloatCase", "decSA between -4.5 and 4", 0, 0); //$NON-NLS-1$ //$NON-NLS-2$
        doTestGDComp("DecFloatCase", "decSA in (-7.1,8,-9.56)", 0, 0); //$NON-NLS-1$ //$NON-NLS-2$
    }

    public void testCalendarExpressions() throws CoreException, IOException {

        // Good times
        doTestGDComp("TimeCase", "timeSA = '10'", 0, 0); //$NON-NLS-1$ //$NON-NLS-2$
        doTestGDComp("TimeCase", "timeSA = '10:00'", 0, 0); //$NON-NLS-1$ //$NON-NLS-2$
        doTestGDComp("TimeCase", "timeSA = '10:00:00'", 0, 0); //$NON-NLS-1$ //$NON-NLS-2$
        doTestGDComp("TimeCase", "timeSA = '10:00:00.000'", 0, 0); //$NON-NLS-1$ //$NON-NLS-2$

        // Bad times
        doTestGDComp("TimeCase", "timeSA = 'monday morning'", 1, 0); //$NON-NLS-1$ //$NON-NLS-2$
        doTestGDComp("TimeCase", "timeSA = 'the witching hour'", 1, 0); //$NON-NLS-1$ //$NON-NLS-2$
        doTestGDComp("TimeCase", "timeSA = '2011-01-01'", 1, 0); //$NON-NLS-1$ //$NON-NLS-2$
        doTestGDComp("TimeCase", "timeSA = '2011-01-01T10:00:00'", 1, 0); //$NON-NLS-1$ //$NON-NLS-2$

        // Good dates
        doTestGDComp("DateCase", "dateSA = '2013'", 0, 0); //$NON-NLS-1$ //$NON-NLS-2$
        doTestGDComp("DateCase", "dateSA = '2013-12'", 0, 0); //$NON-NLS-1$ //$NON-NLS-2$
        doTestGDComp("DateCase", "dateSA = '2013-12-31'", 0, 0); //$NON-NLS-1$ //$NON-NLS-2$

        // Bad dates
        doTestGDComp("DateCase", "dateSA = 'Freddy Krueger'", 1, 0); //$NON-NLS-1$ //$NON-NLS-2$

        // Good datetimes
        doTestGDComp("DateTimeCase", "dateTimeSA = '2013'", 0, 0); //$NON-NLS-1$ //$NON-NLS-2$
        doTestGDComp("DateTimeCase", "dateTimeSA = '2013-12'", 0, 0); //$NON-NLS-1$ //$NON-NLS-2$
        doTestGDComp("DateTimeCase", "dateTimeSA = '2013-12-31'", 0, 0); //$NON-NLS-1$ //$NON-NLS-2$
        doTestGDComp("DateTimeCase", //$NON-NLS-1$
                "dateTimeSA = '2001-02-03T04:05:06.007'", //$NON-NLS-1$
                0,
                0);

        // Bad datetimes
        doTestGDComp("DateTimeCase", "dateTimeSA = 'stollen'", 1, 0); //$NON-NLS-1$ //$NON-NLS-2$

        // Good datetimeTZs
        doTestGDComp("DateTimeTZCase", "dateTimeTZSA = '2013-01-01Z'", 0, 0); //$NON-NLS-1$ //$NON-NLS-2$
        doTestGDComp("DateTimeTZCase", //$NON-NLS-1$
                "dateTimeTZSA = '2013-01-01T10:00:00+04:00'", //$NON-NLS-1$
                0,
                0);

        // Bad datetimeTZs
        doTestGDComp("DateTimeTZCase", "dateTimeTZSA = 'Alienoid'", 1, 0); //$NON-NLS-1$ //$NON-NLS-2$
    }

    public void testSortAttributes() throws CoreException, IOException {
        // Good
        doTestOrder("orderId != 'hello' order by orderId", 0, 0); //$NON-NLS-1$
        doTestOrder("orderId != 'hello' order by dispatchNote.dateOfDispatch", //$NON-NLS-1$
                0,
                0);

        // Bad - don't exist
        doTestOrder("orderId != 'hello' order by banana", 1, 0); //$NON-NLS-1$
        doTestOrder("orderId != 'hello' order by cheddar.cheese", 1, 0); //$NON-NLS-1$

        // Bad - leaf multiplicity
        doTestOrder("orderId != 'hello' order by comments", 1, 0); //$NON-NLS-1$

        // Bad - branch multiplicity
        doTestOrder("orderId != 'hello' order by orderLines.quantity", 1, 0); //$NON-NLS-1$

        // Bad - object
        doTestOrder("orderId != 'hello' order by dispatchNote", 1, 0); //$NON-NLS-1$
    }

    public void testOrderExpressions() throws CoreException, IOException {

        // Bad name
        doTestOrder("noSuchAttribute = null", 1, 0); //$NON-NLS-1$

        // Multiples
        doTestOrder("comments = 'x' or comments = 'y' or comments = 'z'", 0, 3); //$NON-NLS-1$
        doTestOrder("orderId > 5 or comments <= 'a' or orderLines.quantity > 'hello'", //$NON-NLS-1$
                3,
                2);

        // Path extends beyond PT
        doTestOrder("orderId.extra", 1, 0); //$NON-NLS-1$

        // Path extends beyond enum
        doTestOrder("orderLines.orderItem.itemEvents.event.extra", 1, 0); //$NON-NLS-1$

        // Bad values for enum
        doTestOrder("orderLines.orderItem.itemEvents.event = 'NO_SUCH_EVENT'", //$NON-NLS-1$
                1,
                1);

        // Bad values for integer
        doTestOrder("orderLines.quantity = 'not a number'", 1, 1); //$NON-NLS-1$
        doTestOrder("orderLines.quantity = true", 1, 1); //$NON-NLS-1$

        // Good use of size operators
        doTestOrder("size(comments) = 5", 0, 1); //$NON-NLS-1$
        doTestOrder("size(orderLines) > 7", 0, 0); //$NON-NLS-1$
        doTestOrder("size(orderLines[ALL].orderItem.itemEvents) < 7", 0, 0); //$NON-NLS-1$

        // Bad use of size operators
        doTestOrder("size(orderId) = 5", 1, 0); //$NON-NLS-1$
        doTestOrder("size(comments[1]) = 5", 1, 1);   //$NON-NLS-1$
        doTestOrder("size(comments[ALL]) = 5", 1, 1);   //$NON-NLS-1$
        doTestOrder("size(comments[$a]) = 5", 1, 1);   //$NON-NLS-1$
        doTestOrder("size(orderLines.orderItem) = 5", 1, 0); //$NON-NLS-1$
        doTestOrder("size(orderLines.orderItem.itemEvents.event) = 5", 1, 1); //$NON-NLS-1$
        doTestOrder("size(orderLines[ALL]) = 5", 1, 0); //$NON-NLS-1$

        // Good use of =null on collection
        doTestOrder("orderLines = null", 0, 0); //$NON-NLS-1$
        
        // Bad use of =null on collection
        doTestOrder("orderLines[ALL] = null", 1, 0); //$NON-NLS-1$
        doTestOrder("orderLines[1] = null", 1, 0); //$NON-NLS-1$
        doTestOrder("orderLines[$a] = null", 1, 0); //$NON-NLS-1$

        // Good use of !=null on collection
        doTestOrder("orderLines != null", 0, 0); //$NON-NLS-1$
        
        // Bad use of !=null on collection
        doTestOrder("orderLines[ALL] != null", 1, 0); //$NON-NLS-1$
        doTestOrder("orderLines[1] != null", 1, 0); //$NON-NLS-1$
        doTestOrder("orderLines[$a] != null", 1, 0); //$NON-NLS-1$
        
        // Good operators for string
        doTestOrder("comments = 'hello'", 0, 1); //$NON-NLS-1$
        doTestOrder("comments != 'hello'", 0, 1); //$NON-NLS-1$
        doTestOrder("comments = null", 0, 1); //$NON-NLS-1$
        doTestOrder("comments != null", 0, 1); //$NON-NLS-1$

        // Bad operators for string
        doTestOrder("comments > 'hello'", 1, 1); //$NON-NLS-1$
        doTestOrder("comments >= 'hello'", 1, 1); //$NON-NLS-1$
        doTestOrder("comments < 'hello'", 1, 1); //$NON-NLS-1$
        doTestOrder("comments <= 'hello'", 1, 1); //$NON-NLS-1$
        doTestOrder("comments between 'here' and 'there'", 1, 1); //$NON-NLS-1$
        doTestOrder("comments not between 'dis' and 'dat'", 1, 1); //$NON-NLS-1$

        // Good operators for enum
        doTestOrder("orderLines.orderItem.itemEvents.event = 'RECEIVED'", 0, 1); //$NON-NLS-1$
        doTestOrder("orderLines.orderItem.itemEvents.event != 'RECEIVED'", 0, 1); //$NON-NLS-1$
        doTestOrder("orderLines.orderItem.itemEvents.event = null", 0, 1); //$NON-NLS-1$
        doTestOrder("orderLines.orderItem.itemEvents.event != null", 0, 1); //$NON-NLS-1$

        // Bad operators for enum
        doTestOrder("orderLines.orderItem.itemEvents.event > 'RECEIVED'", 1, 1); //$NON-NLS-1$
        doTestOrder("orderLines.orderItem.itemEvents.event >= 'RECEIVED'", 1, 1); //$NON-NLS-1$
        doTestOrder("orderLines.orderItem.itemEvents.event < 'RECEIVED'", 1, 1); //$NON-NLS-1$
        doTestOrder("orderLines.orderItem.itemEvents.event <= 'RECEIVED'", 1, 1); //$NON-NLS-1$

        // Bad operators for a many
        doTestOrder("orderLines = 'blah'", 1, 0); //$NON-NLS-1$
        doTestOrder("orderLines != 'blah'", 1, 0); //$NON-NLS-1$
        doTestOrder("orderLines > 'blah'", 1, 0); //$NON-NLS-1$
        doTestOrder("orderLines >= 'blah'", 1, 0); //$NON-NLS-1$
        doTestOrder("orderLines < 'blah'", 1, 0); //$NON-NLS-1$
        doTestOrder("orderLines >= 'blah'", 1, 0); //$NON-NLS-1$

        // Bad value for size
        doTestOrder("size(orderLines) = 'huge'", 1, 0); //$NON-NLS-1$
        
        // Associations
        doTestOrder("customer = null", 0, 0); //$NON-NLS-1$
        doTestOrder("customer != null", 0, 0); //$NON-NLS-1$
        
        // Asociations' attributes
        doTestOrder("customer.name = 'Bryan Cranston'", 0, 0); //$NON-NLS-1$
        doTestOrder("customer.name != 'Aaron Paul'", 0, 0); //$NON-NLS-1$
    }

    public void doTestOrder(String dql, int expectedErrorCount,
            int expectedWarningCount) throws CoreException, IOException {

        doTest(dql,
                "com.example.ordermodel.bom", //$NON-NLS-1$
                "Order", //$NON-NLS-1$
                expectedErrorCount,
                expectedWarningCount);
    }

    public void doTestGDComp(String caseClassName, String dql,
            int expectedErrorCount, int expectedWarningCount)
            throws CoreException, IOException {
        doTest(dql,
                "com.example.gdcomp.bom", //$NON-NLS-1$
                caseClassName,
                expectedErrorCount,
                expectedWarningCount);
    }

    private void doTest(String dql, String bomName, String caseClassName,
            int expectedErrorCount, int expectedWarningCount)
            throws CoreException, IOException {
        IProject project = createBOMProject("BOMProj", VERSION); //$NON-NLS-1$
        configureN2Destination(project, VERSION);
        IFolder folder = createBusinessObjectsFolderInProject(project);
        File file = new File(folder.getLocation().toFile(), bomName);
        if (!file.exists()) {
            writeResourceToFile(RESOURCE_ROOT + "/" + bomName, file); //$NON-NLS-1$
            project.refreshLocal(IResource.DEPTH_INFINITE, null);
        }
        IFile bomFile =
                (IFile) project.findMember("Business Objects/" + bomName); //$NON-NLS-1$
        // Get root package from BOM
        org.eclipse.uml2.uml.Model model =
                (org.eclipse.uml2.uml.Model) XpdResourcesPlugin.getDefault()
                        .getWorkingCopy(bomFile).getRootElement();
        org.eclipse.uml2.uml.Class clazz =
                (Class) model.getOwnedType(caseClassName);
        try {
            List<ValidationResult> result = BDSAPI.validateDQL(clazz, dql);
            assertEquals("Wrong number of errors: " //$NON-NLS-1$
                    + ValidationResult.getMessages(ValidationResult
                            .getErrors(result)),
                    expectedErrorCount,
                    ValidationResult.getErrors(result).size());
            assertEquals("Wrong number of warnings: " //$NON-NLS-1$
                    + ValidationResult.getMessages(ValidationResult
                            .getWarnings(result)),
                    expectedWarningCount,
                    ValidationResult.getWarnings(result).size());
        } catch (DQLValidationException e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    private void runGoodCheck(org.eclipse.uml2.uml.Class clazz, String dql) {
        try {
            List<ValidationResult> result = BDSAPI.validateDQL(clazz, dql);
            System.out.println(ValidationResult.getMessages(result));
            assertFalse("Expected no errors while parsing [" + dql + "]", //$NON-NLS-1$ //$NON-NLS-2$
                    ValidationResult.containsErrors(result));
        } catch (DQLValidationException e) {
            e.printStackTrace();
            fail("Unexpected Exception: " + e.getClass().getName() + ": " //$NON-NLS-1$ //$NON-NLS-2$
                    + e.getLocalizedMessage());
        }
    }
}
