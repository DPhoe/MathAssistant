import org.testng.Assert;
import org.testng.annotations.Test;
import project.UtilityValidation;

public class EquationValidationsTets {
    @Test
    public void checkForParenthesesNegative() throws Exception {
        UtilityValidation utilityValidation = new UtilityValidation();
        String[] input = new String[] {"x=5---10", "20-((10*-5)-25*(5-50)=x",
                "5*(10-x*(50+x*(25-x)))))=52*(x-25*(108/x))", ")x==x", "10-10=xx-10", "()+10=x", "sdgsdgs"};
        for (String inp : input)
            Assert.assertFalse(utilityValidation.runCheck(inp));
    }
    @Test
    public void checkCorrectEquations() throws Exception {
        UtilityValidation utilityValidation = new UtilityValidation();
        String[] input = new String[] {"x=5-10", "20-(10*-5)-25*(5-50)=x", "5*(10-x*(50+x*(25-x)))=52*(x-25*(108/x))", "x=x", "10-10=x-10"};
        for (String inp : input)
        Assert.assertTrue(utilityValidation.runCheck(inp));
    }
}


