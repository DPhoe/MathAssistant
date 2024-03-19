import org.testng.Assert;
import org.testng.annotations.Test;
import project.Equation;
import project.EquationDAO;

import static java.sql.Types.NULL;

public class DataBaseOperationsTests {

    EquationDAO equationDAO = new EquationDAO();
    @Test
    public void findEquationByIdTest () {
        Assert.assertEquals(equationDAO.findEquationById(5).getId(), 5);
        Assert.assertEquals(equationDAO.findEquationById(5).getEquation(), "100*(x)=10");
        Assert.assertEquals(equationDAO.findEquationById(5).getRoots()[0], 0.1);

    }

    @Test
    public void findEquationsByRootValueTest () {
        Assert.assertEquals(equationDAO.findEquationsByRootValue(10).getFirst().getId(), 7);
        Assert.assertEquals(equationDAO.findEquationsByRootValue(10).getFirst().getEquation(), "10*x=10*x");
        Assert.assertEquals(equationDAO.findEquationsByRootValue(10).getFirst().getRoots()[1], 2);
        Assert.assertEquals(equationDAO.findEquationsByRootValue(10).getFirst().getRoots()[4], 5);
        Assert.assertEquals(equationDAO.findEquationsByRootValue(10).getFirst().getRoots()[9], 10);

        Assert.assertEquals(equationDAO.findEquationsByRootValue(10).get(1).getId(), 13);
        Assert.assertEquals(equationDAO.findEquationsByRootValue(10).get(1).getEquation(), "10-x=10-x");
        Assert.assertEquals(equationDAO.findEquationsByRootValue(10).get(1).getRoots()[0], 10);
        Assert.assertEquals(equationDAO.findEquationsByRootValue(10).get(1).getRoots()[1], 20);
        Assert.assertEquals(equationDAO.findEquationsByRootValue(10).get(1).getRoots()[2], 30);

    }

    @Test
    public void findEquationsByNumberOfRootsTest () {
        Assert.assertEquals(equationDAO.findEquationsByNonZeroRootsCount(10).getFirst().getId(), 7);
        Assert.assertEquals(equationDAO.findEquationsByNonZeroRootsCount(10).get(1).getId(), 9);
        Assert.assertEquals(equationDAO.findEquationsByNonZeroRootsCount(10).get(2).getId(), 10);
        Assert.assertEquals(equationDAO.findEquationsByNonZeroRootsCount(10).size(), 3);
    }
}
