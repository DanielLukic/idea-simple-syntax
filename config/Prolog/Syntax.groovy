/*
 * Stupid "Prolog" syntax definition. I have no clue about Prolog.. :)
 *
 * These variables are available:
 * context: includes a source() and currentDir() function
 * configFolder: is a File representing the config/options/SimpleSyntax folder
 * systemContext: references the shared SystemContext object of the plugin
 * configuration: is the LanguageConfiguration for this script
 */

package Prolog;

import jfun.parsec.*;
import jfun.parsec.tokens.*;
import jfun.parsec.pattern.*;

import java.util.regex.Pattern;



class MyPrologSyntax extends jparsec.LexerBase
    {
    def myBaseFolder
    
    MyPrologSyntax(aBaseFolder)
        {
        myBaseFolder = aBaseFolder
        }

    void setup()
        {
        def s_block_open = Scanners.isPattern( Patterns.regex( /(?m:^\/\*)/ ), "BLOCK_OPEN" )
        def s_block_close = Scanners.isPattern( Patterns.regex( /(?m:^\*\/$)/ ), "BLOCK_CLOSE" )
        def s_block_commented = Scanners.anyChar()

        scanner "BLOCK_COMMENT",    Scanners.isBlockComment( s_block_open, s_block_close, s_block_commented )

        regex "LINE_COMMENT",       /(?m:%.*$)/

        regex "STRING",             /\'(?:[^\'\n\r]|\\')*\'/

        new File(myBaseFolder,'Prolog/Keywords.txt').eachLine {
            def word = Pattern.quote(it)
            regex "KEYWORD", /\b$word\b/
            }

        regex "NAME",               /\b[A-Z]\w*\b/
        regex "IDENTIFIER",         /\b[a-z]\w*\b/
        regex "NUMBER",             /\b(?:0x[0-9A-Fa-f]+)|(?:[0-9]+(?:\.[0-9]+)?)\b/
        }
    }

return new MyPrologSyntax(configFolder).lexer( configuration )
