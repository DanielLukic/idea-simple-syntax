package net.intensicode.idea.util;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import jfun.parsec.Parser;
import jfun.parsec.Tok;
import net.intensicode.idea.system.OptionsFolder;
import net.intensicode.idea.system.SystemContext;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * TODO: Describe this!
 */
public final class GroovyContext
{
    public GroovyContext( final SystemContext aSystemContext )
    {
        myFolder = aSystemContext.getOptionsFolder();
    }

    public final Parser<Tok[]> makeLexer( final String aScript )
    {
        final Binding binding = myShell.getContext();
        binding.setVariable( "context", this );
        binding.setVariable( "currendDir", myFolder.getConfigurationFolder() );
        return ( Parser<Tok[]> ) myShell.evaluate( aScript );
    }

    public final Object source( final String aScriptName ) throws FileNotFoundException
    {
        return myShell.evaluate( myFolder.streamFile( aScriptName ) );
    }

    public final File currentDir()
    {
        return myFolder.getConfigurationFolder();
    }



    private final OptionsFolder myFolder;

    private final GroovyShell myShell = new GroovyShell();
}
