package ASTStatements;

import AST.Expression;
import AST.RUNTIME_CONTEXT;

public class PrintStatement extends Statement{

    private Expression exp;

    public PrintStatement(Expression exp) {
        this.exp = exp;
    }

    @Override
    public boolean execute(RUNTIME_CONTEXT con) {
        double a = exp.evaluate(con);
        System.out.print(a);
        return true;
    }
}
