package net.intensicode.idea.core;

import net.intensicode.idea.util.ReaderUtils;
import net.intensicode.idea.system.OptionsFolder;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.FileNotFoundException;

import javax.swing.Icon;
import javax.swing.ImageIcon;



/**
 * TODO: Describe this!
 */
public class FakeOptionsFolder implements OptionsFolder
{
    public FakeOptionsFolder( final Object aReferenceObject )
    {
        myReferenceObject = aReferenceObject;
    }

    public String[] findConfigurations()
    {
        throw new RuntimeException( "NYI" );
    }

    public Icon loadIcon( String aRelativeFileName )
    {
        return new ImageIcon( myReferenceObject.getClass().getResource( aRelativeFileName ) );
    }

    public boolean fileExists( String aRelativeFileName )
    {
        throw new RuntimeException( "NYI" );
    }

    public InputStream streamFile( String aRelativeFileName ) throws FileNotFoundException
    {
        return myReferenceObject.getClass().getResourceAsStream( aRelativeFileName );
    }

    public String readFileIntoString( String aRelativeFileName ) throws IOException
    {
        final InputStreamReader reader = new InputStreamReader( streamFile( aRelativeFileName ) );
        return ReaderUtils.readIntoString( reader );
    }

    public void writeFileFromStream( String aRelativeFileName, InputStream aStream ) throws IOException
    {
        throw new RuntimeException( "NYI" );
    }

    private final Object myReferenceObject;
}
