/////////////////////////////////////////////////
// Class that stores number
//

public class NumericConstant extends Expression{

    private double value;

    public NumericConstant(double value) {
        this.value = value;
    }
    @Override
    public double evaluate(RUNTIME_CONTEXT context) {
        return this.value;
    }
}
