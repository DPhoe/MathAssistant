package project;

public class Equation {

    private int id;
    private String equation;
    private double[] roots = new double[10];

    public Equation() {
    }

    public Equation(String equation, double[] roots) {
        this.equation = equation;
        this.roots = roots;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEquation() {
        return equation;
    }

    public void setEquation(String equation) {
        this.equation = equation;
    }

    public double[] getRoots() {
        return roots;
    }

    public void setRoots(double[] roots) {
        this.roots = roots;
    }

}
