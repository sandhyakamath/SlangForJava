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
    TOK_SEMI(11),

    TOK_VAR_NUMBER(12),        // NUMBER data type
    TOK_VAR_STRING(13),        // STRING data type
    TOK_VAR_BOOL(14),          // Bool data type
    TOK_NUMERIC(15),            // [0-9]+
    TOK_COMMENT(16) ,      // Comment Token ( presently not used )
    TOK_BOOL_TRUE(17),         // Boolean TRUE
    TOK_BOOL_FALSE(18)  ,   // Boolean FALSE
    TOK_STRING(19),         // String Literal
    TOK_ASSIGN (20) ;        // Assignment Symbol =


    private int tok;

    private TOKEN(int tok) {
        this.tok = tok;
    }

    public int getToken() {
        return this.tok;
    }
}
