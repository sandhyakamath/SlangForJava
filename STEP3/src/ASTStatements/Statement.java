package ASTStatements;

import AST.RUNTIME_CONTEXT;

public abstract class Statement {
    public abstract boolean execute(RUNTIME_CONTEXT con);
}
