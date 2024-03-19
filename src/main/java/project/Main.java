package project;



import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {

        EquationDAO equationDAO = new EquationDAO();
        UtilityValidation util = new UtilityValidation();
        MorphAndCalculator calc = new MorphAndCalculator();
        Scanner input = new Scanner(System.in);

        while (true) {
            System.out.println("\n Welcome to the Math Assistant application prototype. " +
                    "\n You can start using it by typing key words in the console (key sensitive)" +
                    "\n If you're not familiar with commands, type Help to display available inputs" +
                    "\n Type Exit to close down application" +
                    "\n");
            String userInput = input.nextLine();
            switch (userInput) {
                case "Update roots by ID":
                    System.out.println("Enter equation ID:");
                    int id = Integer.parseInt(input.nextLine());
                    Equation equationForRoots = equationDAO.findEquationById(id);
                    if (equationForRoots != null) {
                        System.out.println("Equation found: " + equationForRoots.getEquation());
                        System.out.println("Please enter the roots in format 0.000 separated by space");
                        String rootsToUpdateWith = input.nextLine();

                        ArrayList<String> roots = calc.evaluateRoot(equationForRoots.getEquation(), rootsToUpdateWith);
                        equationDAO.updateEquationRoots(equationForRoots.getId(), roots);
                        System.out.println("Roots updated for equation ID: " + id);
                    } else {
                        System.out.println("No equation found with ID: " + id);
                    }
                    break;
                case "Pull equation by ID":
                    System.out.println("Please enter ID of the equation you're looking for" +
                            "\n");
                    String userInputId = input.nextLine();
                    Equation equationById = equationDAO.findEquationById(Integer.parseInt(userInputId));
                    System.out.println("ID: " + equationById.getId() + " equation: "
                            + equationById.getEquation() + " roots: " + Arrays.toString(equationById.getRoots()));
                    break;
                case "Pull equations by root value":
                    System.out.println("Please enter the root you want equation to be found by" +
                            "\n");
                    String userInputRootValue = input.nextLine();
                    List<Equation> equations = equationDAO.findEquationsByRootValue(Double.parseDouble(userInputRootValue));

                    if (equations != null && !equations.isEmpty()) {
                        for (Equation equation : equations) {
                            System.out.println("ID: " + equation.getId() + " equation: "
                                    + equation.getEquation() + " roots: " + Arrays.toString(equation.getRoots()) +
                                    "| matching root: " + userInputRootValue);
                        }
                    } else {
                        System.out.println("No equations found with a root equal to " + userInputRootValue);
                    }
                    break;
                case "Pull equations by number of roots":
                    System.out.println("Please enter the number of roots equation has to have" +
                            "\n");
                    String numberOfRootsInput = input.nextLine();
                    List<Equation> equations1 = equationDAO.findEquationsByNonZeroRootsCount(Integer.parseInt(numberOfRootsInput));

                    if (equations1 != null) {
                        for (Equation equation : equations1) {
                            System.out.println("ID: " + equation.getId()
                                    + " equation: " + equation.getEquation()
                                    + " roots: " + Arrays.toString(equation.getRoots()).replace(" 0.0,", ""));
                        }
                    } else {
                        System.out.println("No equations found with the given number of roots.");
                    }
                    break;
                case "Pull all equations":
                    List<Equation> equations2 = equationDAO.getAllQuestions();
                    for (Equation equation : equations2) {
                        System.out.println("ID: " + equation.getId() + " equation: "
                                + equation.getEquation() + " roots: "
                                + Arrays.toString(equation.getRoots()));
                    }
                    break;
                case "Add new equation":
                    System.out.println("Please enter your equation");
                    String userEquationInput = input.nextLine();
                    util.runCheck(userEquationInput);
                    int lastEquationEnteredId = equationDAO.insertEquationAndGetId(userEquationInput);
                    System.out.println("Your equation's ID: " + lastEquationEnteredId +
                            "\n Do you want to add possible roots to the equation? " +
                            "\n Type 'Yes' if you do, anything else to return to the beginning");
                    String userInputForRoots = input.nextLine();
                    switch (userInputForRoots) {
                        case "Yes":
                            System.out.println("Please input roots you want to check and add to equation, providing they are correct" +
                                            "\n Input roots in format 0.0000 separated with spaces");
                            String rootInput = input.nextLine();
                            equationDAO.updateEquationRoots(lastEquationEnteredId, calc.evaluateRoot(userEquationInput, rootInput));
                            System.out.println("Correct roots are saved in the DB with the same record ID: " + lastEquationEnteredId +
                                    "\n Returning to the beginning"+
                                    "\n");
                            break;
                        default:
                            System.out.println("Returning to the beginning" +
                                    "\n");
                            break;
                    }
                    break;
                case "Help":
                    System.out.println("Type 'Pull all equations' to pull whole table of equations" +
                            "\n Type 'Add new equation' to add a new equation and roots for it" +
                            "\n Type 'Pull equations by number of roots' to display all equations which have exact amount of roots records in the DB" +
                            "\n Type 'Pull equations by root value' to display all equations that have one of the roots with given value" +
                            "\n Type 'Pull equation by ID' to find equation with given ID" +
                            "\n Type 'Update roots by ID' to update roots of equation found by id, provided roots are correct");
                    break;
                case "Exit":
                    System.out.println("Exiting application...");
                    input.close();
                    equationDAO.closeDataSource();
                    System.exit(0);
                default:
                    System.out.println("Sorry, we can't recognize your inputs, type Help for commands list or Exit to close the application");
            }
        }
    }
}