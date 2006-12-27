package net.intensicode.idea.system.production;

import com.intellij.openapi.application.PathManager;
import com.intellij.openapi.diagnostic.Logger;
import net.intensicode.idea.system.OptionsFolder;
import net.intensicode.idea.util.FileUtils;
import net.intensicode.idea.util.LoggerFactory;
import net.intensicode.idea.util.StreamUtils;

import javax.swing.*;
import java.io.*;
import java.util.HashMap;



/**
 * TODO: Describe this!
 */
final class ProductionOptionsFolder implements OptionsFolder, FilenameFilter
{
    ProductionOptionsFolder()
    {
    }

    // From OptionsFolder

    public String[] findConfigurations()
    {
        final File configFolder = getConfigurationFolder();
        LOG.info( "Scanning for SimpleSyntax config files in " + configFolder );
        return configFolder.list( this );
    }

    public final synchronized Icon loadIcon( final String aRelativeFileName )
    {
        final String fileName = makeFileName( aRelativeFileName );
        if ( myLoadedIcons.containsKey( fileName ) == false )
        {
            final byte[] data = tryLoadingFile( fileName );
            final ImageIcon icon = ( data != null ) ? new ImageIcon( data ) : new ImageIcon();
            myLoadedIcons.put( fileName, icon );
        }
        return myLoadedIcons.get( fileName );
    }

    public final boolean fileExists( final String aRelativeFileName )
    {
        final String fileName = makeFileName( aRelativeFileName );
        return new File( fileName ).exists();
    }


    public final String makeFileName( final String aRelativeFileName )
    {
        if ( new File( aRelativeFileName ).isAbsolute() ) return aRelativeFileName;
        final File baseFolder = getConfigurationFolder();
        return new File( baseFolder, aRelativeFileName ).getPath();
    }

    public final Reader readFile( final String aRelativeFileName ) throws IOException
    {
        return new FileReader( makeFileName( aRelativeFileName ) );
    }

    public final InputStream streamFile( String aRelativeFileName ) throws FileNotFoundException
    {
        final String fileName = makeFileName( aRelativeFileName );
        return new FileInputStream( fileName );
    }

    public final String readFileIntoString( final String aRelativeFileName ) throws IOException
    {
        final String fileName = makeFileName( aRelativeFileName );
        return FileUtils.readIntoString( fileName );
    }

    public final void writeFileFromStream( final String aRelativeFileName, final InputStream aStream ) throws IOException
    {
        final String fileName = makeFileName( aRelativeFileName );

        final File target = new File( fileName );
        target.getParentFile().mkdirs();

        final FileOutputStream output = new FileOutputStream( target );
        try
        {
            StreamUtils.copy( aStream, output );
        }
        finally
        {
            output.close();
        }
    }

    public final String classPathBase()
    {
        return getConfigurationFolder().toString();
    }


    // From FileNameFilter

    public final boolean accept( final File dir, final String name )
    {
        return name.startsWith( CONFIG_FILE_NAME_PREFIX ) && name.endsWith( CONFIG_FILE_NAME_SUFFIX );
    }

    // Implementation

    private final byte[] tryLoadingFile( final String aFileName )
    {
        try
        {
            return FileUtils.loadFile( aFileName );
        }
        catch ( final IOException e )
        {
            return null;
        }
    }

    private final File getConfigurationFolder()
    {
        final File configFolder = new File( PathManager.getOptionsPath(), CONFIG_SUB_FOLDER );
        if ( configFolder.exists() == false ) configFolder.mkdirs();
        return configFolder;
    }



    private static final Logger LOG = LoggerFactory.getLogger();

    private static final String CONFIG_FILE_NAME_SUFFIX = ".config";

    private static final String CONFIG_FILE_NAME_PREFIX = "";

    private static final String CONFIG_SUB_FOLDER = "SimpleSyntax";

    private static final HashMap<String, ImageIcon> myLoadedIcons = new HashMap<String, ImageIcon>();
}
