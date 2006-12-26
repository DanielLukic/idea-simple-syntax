package jfun.parsec;

import jfun.parsec.tokens.TokenType;
import jfun.parsec.tokens.Tokenizers;
import jfun.parsec.tokens.TypedToken;
import junit.framework.TestCase;

/**
 * TODO: Describe this!
 */
public final class ParserTest extends TestCase
{
    private static final Object UNRECOGNIZED = new Object();

    public final void testStuff()
    {
        final Parser<Tok[]> lexer = createLexer();

        final Tok[] tokens = ( Tok[] ) Parsers.runParser( "1+1\n00a \n\r x", lexer, "" );
        assertNotNull( tokens );
        assertEquals( 11, tokens.length );
    }

    public final void testLexer()
    {
        final Parser<Tok[]> lexer = createLexer();

        final Tok[] tokens = ( Tok[] ) Parsers.runParser( "test this 17 times + 4! (or not)", lexer, "" );
        assertNotNull( tokens );
        assertEquals( 11, tokens.length );

        checkTyped( tokens, 0, TokenType.Word, "test" );
        checkTyped( tokens, 1, TokenType.Word, "this" );
        checkTyped( tokens, 2, TokenType.Decimal, "17" );
        checkTyped( tokens, 3, TokenType.Word, "times" );
        checkReserved( tokens, 4, "+" );
        checkTyped( tokens, 5, TokenType.Decimal, "4" );
        checkTyped( tokens, 6, UNRECOGNIZED, "!" );
        checkReserved( tokens, 7, "(" );
        checkTyped( tokens, 8, TokenType.Word, "or" );
        checkTyped( tokens, 9, TokenType.Word, "not" );
        checkReserved( tokens, 10, ")" );
    }

    private static final Parser<Tok[]> createLexer()
    {
        final Parser<_> s_whitespace = Scanners.isWhitespaces().many();

        final Parser<Tok> l_word = Lexers.word();
        final Parser<Tok> l_decimal = Lexers.decimal();
        final Parser<Tok> l_other = Lexers.lexer( Scanners.anyChar(), Tokenizers.forTypedToken( UNRECOGNIZED ) );

        final Parser<Tok> l_all = Parsers.alt( l_word, l_decimal, l_other );
        return Lexers.lexeme( s_whitespace, l_all );
    }

    private final void checkTyped( final Tok[] aTokens, final int aIndex, final Object aType, final String aText )
    {
        final TypedToken token = ( TypedToken ) aTokens[ aIndex ].getToken();
        assertEquals( aType, token.getType() );
        assertEquals( aText, token.getText() );
    }

    private final void checkReserved( final Tok[] aTokens, final int aIndex, final String aText )
    {
        assertEquals( aText, aTokens[ aIndex ].toString() );
    }
}
