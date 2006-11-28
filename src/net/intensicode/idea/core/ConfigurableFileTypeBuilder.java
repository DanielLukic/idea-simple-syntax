package net.intensicode.idea.core;

import com.intellij.lang.Language;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.fileTypes.FileNameMatcher;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.fileTypes.FileTypeManager;
import net.intensicode.idea.config.FileTypeConfiguration;
import net.intensicode.idea.config.InstanceConfiguration;
import net.intensicode.idea.system.Confirmation;
import net.intensicode.idea.system.SystemContext;
import net.intensicode.idea.system.SystemErrorHandler;
import net.intensicode.idea.util.LoggerFactory;

import java.util.ArrayList;
import java.util.List;



/**
 * TODO: Describe this!
 */
public final class ConfigurableFileTypeBuilder
{
    public ConfigurableFileTypeBuilder( final SystemContext aContext )
    {
        myErrorHandler = aContext.getErrorHandler();
    }

    public final ConfigurableFileType getOrCreate( final InstanceConfiguration aConfiguration, final Language aLanguage )
    {
        checkExtensions( aConfiguration.getFileTypeConfiguration() );
        removeIncompatibleFileTypes( aConfiguration );
        return ConfigurableFileType.getOrCreate( aConfiguration, aLanguage );
    }

    // Implementation

    private final void checkExtensions( final FileTypeConfiguration aConfiguration )
    {
        final List<FileNameMatcher> matchers = aConfiguration.getExtensions();

        final FileTypeManager manager = FileTypeManager.getInstance();
        for ( final FileType fileType : manager.getRegisteredFileTypes() )
        {
            final List<FileNameMatcher> usedExtensions = findUsedExtensions( fileType, matchers );
            if ( usedExtensions.size() == 0 ) continue;

            final Confirmation confirmation = myErrorHandler.onFileTypeInUseConfirmation( usedExtensions );
            if ( confirmation.isYes() ) unregister( fileType, usedExtensions );
            if ( confirmation == Confirmation.CANCEL )
            {
                throw new RuntimeException( "File type extension in use" );
            }
        }
    }

    private final void removeIncompatibleFileTypes( final InstanceConfiguration aConfiguration )
    {
        final String name = aConfiguration.getName();
        final FileTypeManager manager = FileTypeManager.getInstance();
        for ( final FileType fileType : manager.getRegisteredFileTypes() )
        {
            if ( fileType.getName().equals( name ) == false ) continue;
            if ( fileType instanceof ConfigurableFileType == true ) continue;

            final Confirmation confirmation = myErrorHandler.onFileTypeReplaceConfirmation( name );
            if ( confirmation.isYes() ) unregister( fileType );
            if ( confirmation == Confirmation.CANCEL )
            {
                throw new RuntimeException( "Incompatible file type registered" );
            }
            ConfigurableFileTypeBuilder.LOG.info( "Trying to replace incompatible file type for " + name );
        }
    }

    private static final void unregister( final FileType aFileType )
    {
        final FileTypeManager manager = FileTypeManager.getInstance();
        unregister( aFileType, manager.getAssociations( aFileType ) );
    }

    private static final void unregister( final FileType aFileType, final List<FileNameMatcher> aExtensions )
    {
        final FileTypeManager manager = FileTypeManager.getInstance();
        for ( final FileNameMatcher matcher : aExtensions )
        {
            manager.removeAssociation( aFileType, matcher );
        }
    }

    private static final List<FileNameMatcher> findUsedExtensions( final FileType aFileType, final List<FileNameMatcher> aMatchers )
    {
        final FileTypeManager manager = FileTypeManager.getInstance();
        final List<FileNameMatcher> matchers = manager.getAssociations( aFileType );

        final ArrayList<FileNameMatcher> result = new ArrayList<FileNameMatcher>();
        for ( final FileNameMatcher matcher : aMatchers )
        {
            if ( matchers.contains( matcher ) ) result.add( matcher );
        }
        return result;
    }



    private final SystemErrorHandler myErrorHandler;

    private static final Logger LOG = LoggerFactory.getLogger();
}
