package net.intensicode.idea;

import com.intellij.lang.Language;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.fileTypes.FileNameMatcher;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.fileTypes.FileTypeManager;
import com.intellij.openapi.options.colors.ColorSettingsPage;
import com.intellij.openapi.options.colors.ColorSettingsPages;
import com.intellij.openapi.extensions.Extensions;
import com.intellij.openapi.extensions.Extension;
import com.intellij.openapi.extensions.ExtensionPoint;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.application.ApplicationManager;
import net.intensicode.idea.config.FileTypeConfiguration;
import net.intensicode.idea.config.InstanceConfiguration;
import net.intensicode.idea.core.ConfigurableColorSettingsPage;
import net.intensicode.idea.core.ConfigurableFileTypeBuilder;
import net.intensicode.idea.system.SystemContext;
import net.intensicode.idea.util.LoggerFactory;



/**
 * TODO: Describe this!
 */
final class SimpleSyntaxInstance
{
    SimpleSyntaxInstance( final SystemContext aSystemContext, final InstanceConfiguration aConfiguration )
    {
        mySystemContext = aSystemContext;
        myConfiguration = aConfiguration;
        myFileTypeBuilder = new ConfigurableFileTypeBuilder( aSystemContext );
    }

    final String getName()
    {
        return myConfiguration.getName();
    }

    final void init()
    {
        LOG.info( "Initializing " + myConfiguration.getName() );
        registerFileType();
        registerColorSettingsPage();
    }

    final void dispose()
    {
        LOG.info( "Disposing " + getName() );
        unregisterFileType();
    }

    // Implementation

    private final void registerFileType()
    {
        final FileTypeManager manager = mySystemContext.getFileTypeManager();
        if ( manager == null ) return;

        final Language language = myConfiguration.getLanguageConfiguration().getLanguage();
        final FileType fileType = myFileTypeBuilder.getOrCreate( myConfiguration, language );

        LOG.info( "Registering file type " + getName() );
        final FileTypeConfiguration config = myConfiguration.getFileTypeConfiguration();
        manager.registerFileType( fileType, config.getExtensions() );

        myFileType = fileType;
    }

    private final void registerColorSettingsPage()
    {
        final ColorSettingsPages instance = ColorSettingsPages.getInstance();
        if ( instance == null ) return;

        for ( final ColorSettingsPage page : instance.getRegisteredPages() )
        {
            if ( page instanceof ConfigurableColorSettingsPage == false ) continue;

            final ConfigurableColorSettingsPage oldPage = ( ConfigurableColorSettingsPage ) page;
            if ( oldPage.getDisplayName().equals( myConfiguration.getName() ) == false ) continue;

            LOG.info( "Updating color settings page for " + getName() );
            oldPage.reset( myConfiguration );
            return;
        }

        LOG.info( "Registering color settings page for " + getName() );
        instance.registerPage( new ConfigurableColorSettingsPage( myConfiguration ) );
    }

    private final void unregisterFileType()
    {
        final FileTypeManager manager = mySystemContext.getFileTypeManager();
        if ( manager == null || myFileType == null ) return;

        LOG.info( "Unregistering file type " + getName() );
        final FileTypeConfiguration config = myConfiguration.getFileTypeConfiguration();
        for ( final FileNameMatcher matcher : config.getExtensions() )
        {
            manager.removeAssociation( myFileType, matcher );
        }
    }



    private FileType myFileType;

    private final SystemContext mySystemContext;

    private final InstanceConfiguration myConfiguration;

    private final ConfigurableFileTypeBuilder myFileTypeBuilder;

    private static final Logger LOG = LoggerFactory.getLogger();
}
