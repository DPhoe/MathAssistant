import org.testng.Assert;
import org.testng.annotations.Test;
import project.MorphAndCalculator;

public class CalculationsMorphsTests {

    @Test
    public void checkRootCalculation () {
        MorphAndCalculator calc = new MorphAndCalculator();
        String[] equations = new String[] {"10-x=0", "10*x=0", "x*x=x*x", "25*(x-20-(5+10*x)+-35)=10"};
        String[] roots = new String[] {"5 10 20 30 40 50", "5 10 20 30 40 50", "0 0.1 0.001 0.99999 25324325423 -0.000000001",
        "-6.711111111111 10 0 35 38 6"};

        Assert.assertTrue(calc.evaluateRoot(equations[0], roots[0]).getFirst().contains("10"));
        Assert.assertEquals(calc.evaluateRoot(equations[0], roots[0]).size(), 1);

        Assert.assertEquals(calc.evaluateRoot(equations[1], roots[1]).size(), 0);

        Assert.assertTrue(calc.evaluateRoot(equations[2], roots[2]).getFirst().contains("0"));
        Assert.assertTrue(calc.evaluateRoot(equations[2], roots[2]).get(1).contains("0.1"));
        Assert.assertTrue(calc.evaluateRoot(equations[2], roots[2]).get(2).contains("0.001"));
        Assert.assertTrue(calc.evaluateRoot(equations[2], roots[2]).get(3).contains("0.99999"));
        Assert.assertTrue(calc.evaluateRoot(equations[2], roots[2]).get(4).contains("25324325423"));
        Assert.assertTrue(calc.evaluateRoot(equations[2], roots[2]).get(5).contains("-0.000000001"));
        Assert.assertEquals(calc.evaluateRoot(equations[2], roots[2]).size(), 6);

        Assert.assertEquals(calc.evaluateRoot(equations[3], roots[3]).getFirst(), "-6.711111111111");
        Assert.assertEquals(calc.evaluateRoot(equations[3], roots[3]).size(), 1);
    }
}
