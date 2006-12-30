package net.intensicode.idea.core;

import net.intensicode.idea.system.OptionsFolder;
import net.intensicode.idea.util.FileUtils;

import javax.swing.*;
import java.io.*;

/**
 * TODO: Describe this!
 */
public class FakeFileOptionsFolder implements OptionsFolder
{
    public FakeFileOptionsFolder( final String aFolderPath )
    {
        myFolderPath = aFolderPath;
    }

    public final File getConfigurationFolder()
    {
        return new File( myFolderPath );
    }

    public final String[] findConfigurations()
    {
        throw new RuntimeException( "NYI" );
    }

    public final Icon loadIcon( final String aRelativeFileName )
    {
        return new ImageIcon( new File( myFolderPath, aRelativeFileName ).getPath() );
    }

    public final boolean fileExists( final String aRelativeFileName )
    {
        throw new RuntimeException( "NYI" );
    }

    public final String makeFileName( final String aRelativeFileName )
    {
        return new File( myFolderPath, aRelativeFileName ).getPath();
    }

    public final Reader readFile( final String aRelativeFileName )
    {
        throw new RuntimeException( "NYI" );
    }

    public final InputStream streamFile( final String aRelativeFileName ) throws FileNotFoundException
    {
        return new FileInputStream( new File( myFolderPath, aRelativeFileName ) );
    }

    public final String readFileIntoString( final String aRelativeFileName ) throws IOException
    {
        return FileUtils.readIntoString( new File( myFolderPath, aRelativeFileName ).toString() );
    }

    public final void writeFileFromStream( final String aRelativeFileName, final InputStream aStream )
    {
        throw new RuntimeException( "NYI" );
    }

    private final String myFolderPath;
}
