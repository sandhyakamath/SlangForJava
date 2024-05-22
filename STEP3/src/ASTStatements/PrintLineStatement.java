package ASTStatements;

import AST.Expression;
import AST.RUNTIME_CONTEXT;

public class PrintLineStatement extends Statement{

    private Expression exp;

    public PrintLineStatement(Expression exp) {
        this.exp = exp;
    }

    @Override
    public boolean execute(RUNTIME_CONTEXT con) {
        double a = exp.evaluate(con);
        System.out.println(a);
        return true;
    }
}
