package Lexer;

////////////////////////////////////
// Enumeration of tokens
//
public enum TOKEN {
    ILLEGAL_TOKEN (-1), // Not a Token
    TOK_PLUS (1), // '+'
    TOK_MUL(2), // '*'
    TOK_DIV(3), // '/'
    TOK_SUB(4), // '-'
    TOK_OPAREN(4), // '('
    TOK_CPAREN(5), // ')'
    TOK_DOUBLE(6), // '('
    TOK_NULL(7), //
    TOK_PRINT(8), // Print Statement
    TOK_PRINTLN(9), // PrintLine
    TOK_UNQUOTED_STRING(10),
    TOK_SEMI(11);

    private int tok;

    private TOKEN(int tok) {
        this.tok = tok;
    }

    public int getToken() {
        return this.tok;
    }
}
