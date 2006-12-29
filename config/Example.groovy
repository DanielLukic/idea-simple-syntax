
/*
 * This is an example Groovy lexer configuration. There are some issues when using Groovy in this context:
 *
 * 1. You can't simply 'source' another file like in the JRuby configurations. This is because Groovy treats every
 * script as a class. There is no global object space. There is a binding, yes. But it does not contain global methods.
 * AFAIK there's no easy way around this. Therefore I introduced a global variable 'context' in the binding.This can be
 * used like "context.source( aFileName )". However, you can't reference classes because they aren't available to the
 * Groovy compiler when parsing 'this' file. The problem should become obvious now..
 *
 * 2. Because of the first issue, I introduced a helper class, JParsecLexerBase. You can use it as shown below. Of
 * course you don't have to use it. You can create the JParsec lexer any way you like. Unfortunately I'm not sure what
 * the best way is to load additional libs and/or classes. In other words, you can't simply load (read: import) stuff
 * (read: classes) stored in the config/options/SimpleSyntax folder. I haven't found a nice solution for this, yet!
 *
 * 3. Because I didn't find anything concerning 'current dir' in the GroovyShell interface, I added a variable to the
 * binding ('currentDir') and a method to the 'context' object ('context.currentDir()').
 *
 * Overall, the JRuby approach is probably the better solution for now.
 */

import jfun.parsec.*;
import jfun.parsec.tokens.*;
import jfun.parsec.pattern.*;

class MyRubySyntax extends net.intensicode.idea.syntax.JParsecLexerBase
    {
    protected void setup()
        {
        def s_block_open = Scanners.isPattern( Patterns.regex( /(?m:^=begin)/ ), "BLOCK_OPEN" )
        def s_block_close = Scanners.isPattern( Patterns.regex( /(?m:^=end$)/ ), "BLOCK_CLOSE" )
        def s_block_commented = Scanners.anyChar()

        scanner "DOC_COMMENT",	        Scanners.isBlockComment( s_block_open, s_block_close, s_block_commented )

        regex "LINE_COMMENT",           /(?m:#.*$)/
        regex "SPECIAL_QUOTED_STRING",  /%[qQ]\{(?:(?:\\\})|(?:[^\}]))*\}/
        regex "SPECIAL_QUOTED_STRING",  /%[qQ]\[(?:(?:\\\])|(?:[^\]]))*\]/
        regex "SPECIAL_QUOTED_STRING",  /%[qQ]\((?:(?:\\\))|(?:[^\)]))*\)/
        regex "SPECIAL_QUOTED_STRING",  /%[qQ](.).*\1/
        regex "SINGLE_QUOTED_STRING",   /\'(?:[^\'\n\r]|\\')*\'/
        regex "DOUBLE_QUOTED_STRING",   /\"(?:[^\"\n\r]|\\")*\"/

        regex "KEYWORD",                /\b(?:alias|and|BEGIN|begin|break|case|class|def|defined|do|else|elsif|END|end|ensure|false|for|if|in|module|next|nil|not|or|redo|rescue|retry|return|self|super|then|true|undef|unless|until|when|while|yield)\b/

        regex "REGEX_SLASHED",          /(?m:\/(?:[^\/\n\r]|\\\/)*\/)/
        regex "CONSTANT",               /\b[A-Z][A-Z_]*\b/
        regex "CLASS_NAME",             /\b[A-Z]*(?:(?:[A-Z][a-z_]+)|[A-Z])+\b/
        regex "INSTANCE_VARIABLE",      /@[a-z_]+\b/
        regex "CLASS_VARIABLE",         /@@[a-z_]+\b/
        regex "SYMBOL",                 /\:\w+\b/
        regex "IDENTIFIER",             /\b[a-z]\w*\b/
        regex "NUMBER",                 /\b(?:0x[0-9A-Fa-f]+)|(?:[0-9]+(?:\.[0-9]+)?)\b/
        }
    }

return new MyRubySyntax().lexer()
