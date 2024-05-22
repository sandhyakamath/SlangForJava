package AST;

public class BinaryExpression extends Expression {
    private Expression exp1, exp2;
    private OPERATOR op;

    public BinaryExpression(Expression exp1, Expression exp2, OPERATOR op) {
        this.exp1 = exp1;
        this.exp2 = exp2;
        this.op = op;
    }

    @Override
    public double evaluate(RUNTIME_CONTEXT context) {
        switch (op) {
            case PLUS -> { return exp1.evaluate(context) + exp2.evaluate(context); }
            case MINUS -> { return exp1.evaluate(context) - exp2.evaluate(context); }
            case MUL -> { return exp1.evaluate(context) * exp2.evaluate(context); }
            case DIV -> { return exp1.evaluate(context) / exp2.evaluate(context); }
        }
        return Double.NaN;
    }
}
