package AST;////////////////////////////////////////////////////////
// This class supports Unary Operators like +, -, *,/
//

import AST.OPERATOR;
import AST.RUNTIME_CONTEXT;

public class UnaryExpression extends Expression {

    private Expression exp1;
    private OPERATOR op;

    public UnaryExpression(Expression exp, OPERATOR op) {
        this.exp1 = exp;
        this.op = op;
    }
    @Override
    public double evaluate(RUNTIME_CONTEXT context) {
        switch (op) {
            case PLUS:
                return exp1.evaluate(context);
            case MINUS:
                return -(exp1.evaluate(context));
        }
        return Double.NaN;

    }
}
