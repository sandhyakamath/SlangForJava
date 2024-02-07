import AST.*;
import Builder.ExpressionBuilder;

public class Main {
    public static void main(String[] args) {
        ExpressionBuilder b = new ExpressionBuilder("-2*3+3");
        Expression e = b.getExpression();
        System.out.println(e.evaluate(null));

    }
}