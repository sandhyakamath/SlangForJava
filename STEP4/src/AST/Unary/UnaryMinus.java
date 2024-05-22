package AST.Unary;////////////////////////////////////////////////////////
// This class supports Unary Operators like +, -, *,/
//

import AST.Expression;
import Context.COMPILATION_CONTEXT;
import Context.RUNTIME_CONTEXT;
import Lexer.SymbolInfo;
import Lexer.TYPE_INFO;
import Visitor.IExpressionVisitor;

public class UnaryMinus extends Expression {

    private Expression right;

    public Expression getRight() {
        return right;
    }

    public void setRight(Expression right) {
        this.right = right;
    }

    public UnaryMinus(Expression exp) {
        this.right = exp;
    }

    @Override
    public SymbolInfo accept (IExpressionVisitor visitor, RUNTIME_CONTEXT context) throws Exception {
        return visitor.visit(this, context);
    }

    @Override
    public TYPE_INFO accept(IExpressionVisitor visitor, COMPILATION_CONTEXT context) throws Exception {
        return visitor.visit(this, context);
    }

}
