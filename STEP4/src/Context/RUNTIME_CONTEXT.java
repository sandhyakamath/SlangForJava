package Context;

///////////////////////////////////////////
// Class that stores the stack frame
//

import Scope.SymbolTable;

public class RUNTIME_CONTEXT {

    private SymbolTable table;

    public RUNTIME_CONTEXT() {
        table = new SymbolTable();
    }

    public SymbolTable getTable() {
        return table;
    }

    public void setTable(SymbolTable table) {
        this.table = table;
    }
}
