package net.intensicode.idea.config.loaded;

import com.intellij.openapi.fileTypes.ExtensionFileNameMatcher;
import com.intellij.openapi.fileTypes.FileNameMatcher;
import net.intensicode.idea.config.ConfigurationProperties;
import net.intensicode.idea.config.FileTypeConfiguration;
import net.intensicode.idea.system.OptionsFolder;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;



/**
 * TODO: Describe this!
 */
final class LoadedFileTypeConfiguration implements FileTypeConfiguration
{
    LoadedFileTypeConfiguration( final ConfigurationProperties aProperties, final OptionsFolder aOptionsFolder )
    {
        myProperties = aProperties;
        myOptionsFolder = aOptionsFolder;

        if ( myProperties.isValidProperty( FILE_TYPE_EXTENSIONS ) )
        {
            final String extensionsList = myProperties.getProperty( FILE_TYPE_EXTENSIONS );
            for ( final String extension : extensionsList.split( ",[ \t]*" ) )
            {
                myExtensions.add( new ExtensionFileNameMatcher( extension ) );
            }
        }
    }

    // From FileTypeConfiguration

    public final Icon getIcon()
    {
        if ( myProperties.isValidProperty( FILE_TYPE_ICON ) == false ) return null;
        return myOptionsFolder.loadIcon( myProperties.getProperty( FILE_TYPE_ICON ) );
    }

    public final List<FileNameMatcher> getExtensions()
    {
        return myExtensions;
    }

    public final String getDefaultExtension()
    {
        if ( myProperties.isValidProperty( FILE_TYPE_DEFAULT_EXTENSION ) )
        {
            return myProperties.getProperty( FILE_TYPE_DEFAULT_EXTENSION );
        }
        return NO_EXTENSION;
    }



    private final OptionsFolder myOptionsFolder;

    private final ConfigurationProperties myProperties;

    private final ArrayList<FileNameMatcher> myExtensions = new ArrayList<FileNameMatcher>();

    private static final String NO_EXTENSION = "";

    private static final String FILE_TYPE_ICON = "FileType.Icon";

    private static final String FILE_TYPE_EXTENSIONS = "FileType.Extensions";

    private static final String FILE_TYPE_DEFAULT_EXTENSION = "FileType.DefaultExtension";
}
