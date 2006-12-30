package net.intensicode.idea.core;

import net.intensicode.idea.system.OptionsFolder;
import net.intensicode.idea.util.ReaderUtils;

import javax.swing.*;
import java.io.*;



/**
 * TODO: Describe this!
 */
public final class FakeOptionsFolder implements OptionsFolder
{
    public FakeOptionsFolder( final Object aReferenceObject )
    {
        myReferenceObject = aReferenceObject;
    }

    public final File getConfigurationFolder()
    {
        return new File( "." );
    }

    public final String[] findConfigurations()
    {
        throw new RuntimeException( "NYI" );
    }

    public final Icon loadIcon( final String aRelativeFileName )
    {
        return new ImageIcon( myReferenceObject.getClass().getResource( aRelativeFileName ) );
    }

    public final boolean fileExists( final String aRelativeFileName )
    {
        throw new RuntimeException( "NYI" );
    }

    public final String makeFileName( final String aRelativeFileName )
    {
        return aRelativeFileName;
    }

    public final Reader readFile( final String aRelativeFileName )
    {
        throw new RuntimeException( "NYI" );
    }

    public final InputStream streamFile( final String aRelativeFileName )
    {
        return myReferenceObject.getClass().getResourceAsStream( aRelativeFileName );
    }

    public final String readFileIntoString( final String aRelativeFileName ) throws IOException
    {
        final InputStream stream = streamFile( aRelativeFileName );
        if ( stream == null ) throw new FileNotFoundException( aRelativeFileName );

        final InputStreamReader reader = new InputStreamReader( stream );
        return ReaderUtils.readIntoString( reader );
    }

    public final void writeFileFromStream( final String aRelativeFileName, final InputStream aStream )
    {
        throw new RuntimeException( "NYI" );
    }

    private final Object myReferenceObject;
}
