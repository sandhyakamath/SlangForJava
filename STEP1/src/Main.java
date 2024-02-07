public class Main {
    public static void main(String[] args) {
        // Abstract Syntax Tree (AST) for 5*10
        Expression exp = new BinaryExpression(new NumericConstant(5), new NumericConstant(10) ,OPERATOR.MUL);
        System.out.println(exp.evaluate(null));
        // Abstract Syntax Tree (AST) for 5*10
        Expression exp1 = new UnaryExpression(new NumericConstant(5), OPERATOR.MINUS);
        System.out.println(exp1.evaluate(null));
    }
}