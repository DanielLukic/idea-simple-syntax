package net.intensicode.idea.util;

import groovy.lang.GroovyShell;
import groovy.lang.Script;
import jfun.parsec.Parser;
import jfun.parsec.Tok;
import net.intensicode.idea.syntax.JParsecLexer;
import net.intensicode.idea.system.OptionsFolder;
import net.intensicode.idea.system.SystemContext;

import java.io.FileNotFoundException;

/**
 * TODO: Describe this!
 */
public final class GroovyContext
{
    public GroovyContext( final GroovyShell aShell, final SystemContext aSystemContext )
    {
        myShell = aShell;
        myFolder = aSystemContext.getOptionsFolder();
    }

    public static final JParsecLexer makeLexer( final Script aScript )
    {
        return new JParsecLexer( ( Parser<Tok[]> ) aScript.run() );
    }

    public final Object source( final String aScriptName ) throws FileNotFoundException
    {
        return myShell.evaluate( myFolder.streamFile( aScriptName ) );
    }

    private final GroovyShell myShell;

    private final OptionsFolder myFolder;
}
