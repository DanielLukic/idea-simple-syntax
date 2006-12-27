package net.intensicode.idea.syntax;

import jfun.parsec.*;
import jfun.parsec.tokens.Tokenizers;
import junit.framework.TestCase;

/**
 * TODO: Describe this!
 */
public final class JParsecLexerTest extends TestCase
{
    public final void testLexing()
    {
        final char[] chars = "word 1704 ?".toCharArray();

        final JParsecLexer lexer = new JParsecLexer( createLexer() );
        lexer.start( chars, 0, chars.length );

        assertEquals( 0, lexer.findToken( 0 ).start );
        assertEquals( 4, lexer.findToken( 0 ).end );
        assertEquals( "Word", lexer.findToken( 0 ).id.toString() );

        assertEquals( 5, lexer.findToken( 4 ).start );
        assertEquals( 9, lexer.findToken( 4 ).end );
        assertEquals( "Integer", lexer.findToken( 4 ).id.toString() );

        assertEquals( 10, lexer.findToken( 9 ).start );
        assertEquals( 11, lexer.findToken( 9 ).end );
        assertEquals( "UNRECOGNIZED", lexer.findToken( 9 ).id.toString() );
    }

    // Implementation

    private static final Parser<Tok[]> createLexer()
    {
        final Parser<Tok> l_word = Lexers.word();
        final Parser<Tok> l_integer = Lexers.integer();
        final Parser<Tok> l_other = Lexers.lexer( Scanners.anyChar(), Tokenizers.forTypedToken( "UNRECOGNIZED" ) );
        final Parser<Tok> l_all = Parsers.alt( l_word, l_integer, l_other );

        final Parser<_> s_whitespace = Scanners.isWhitespaces().many();
        return Lexers.lexeme( s_whitespace, l_all );
    }
}
