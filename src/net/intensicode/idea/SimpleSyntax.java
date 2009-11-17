package net.intensicode.idea;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.ApplicationComponent;
import com.intellij.openapi.diagnostic.Logger;
import net.intensicode.idea.config.FileTypeConfiguration;
import net.intensicode.idea.config.InstanceConfiguration;
import net.intensicode.idea.config.loaded.LoadedConfiguration;
import net.intensicode.idea.system.OptionsFolder;
import net.intensicode.idea.system.SystemContext;
import net.intensicode.idea.system.production.ProductionSystemContext;
import net.intensicode.idea.util.LoggerFactory;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;



/**
 * TODO: Describe <code>this</code>!
 */
public final class SimpleSyntax implements ApplicationComponent
{
    public SimpleSyntax()
    {
    }

    public Iterator<SimpleSyntaxInstance> instances()
    {
        return myInstances.iterator();
    }

    // From ApplicationComponent

    public final void initComponent()
    {
        if ( !initialize() ) return;

        ApplicationManager.getApplication().runWriteAction( new Runnable()
        {
            public final void run()
            {
                for ( final SimpleSyntaxInstance instance : myInstances )
                {
                    instance.init();
                }
            }
        } );
    }

    public final void disposeComponent()
    {
        if ( !myInitStatus ) return;

        ApplicationManager.getApplication().runWriteAction( new Runnable()
        {
            public final void run()
            {
                if ( !myInitStatus ) return;

                for ( final SimpleSyntaxInstance instance : myInstances )
                {
                    instance.dispose();
                }
                mySystemContext.getErrorHandler().forgetConfirmationAnswers();

                myInstances.clear();
                myInitStatus = false;
            }
        } );
    }

    @NotNull
    public final String getComponentName()
    {
        return "net.intensicode.idea.SimpleSyntax";
    }

    // Implementation

    private synchronized boolean initialize()
    {
        if ( myInitStatus ) return true;
        try
        {
            final ArrayList<InstanceConfiguration> configurations = loadConfigurations();
            for ( final InstanceConfiguration config : configurations )
            {
                myInstances.add( new SimpleSyntaxInstance( mySystemContext, config ) );
            }
            return myInitStatus = true;
        }
        catch ( final Throwable t )
        {
            LOG.error( getComponentName(), t );
            return myInitStatus = false;
        }
    }

    private ArrayList<InstanceConfiguration> loadConfigurations()
    {
        final OptionsFolder optionsFolder = mySystemContext.getOptionsFolder();
        final String[] fileNames = optionsFolder.findConfigurations();

        final ArrayList<InstanceConfiguration> configurations = new ArrayList<InstanceConfiguration>();
        for ( final String fileName : fileNames )
        {
            LOG.info( "Reading configuration file " + fileName );

            final InstanceConfiguration config = LoadedConfiguration.tryLoading( mySystemContext, fileName );
            if ( config == null )
            {
                LOG.info( "Failed reading configuration from " + fileName );
                continue;
            }
            if ( isAlreadyDefined( config, configurations ) )
            {
                LOG.info( "Ignoring duplicate configuration for " + config.getName() );
                continue;
            }

            LOG.info( "Adding configuration for " + config.getName() );
            configurations.add( config );
        }
        return configurations;
    }

    private boolean isAlreadyDefined( final InstanceConfiguration aNewConfig, final ArrayList<InstanceConfiguration> aResult )
    {
        for ( final InstanceConfiguration config : aResult )
        {
            if ( aNewConfig.getName().equals( config.getName() ) ) return true;

            final FileTypeConfiguration newFileType = aNewConfig.getFileTypeConfiguration();
            final FileTypeConfiguration oldFileType = config.getFileTypeConfiguration();
            if ( newFileType.getDefaultExtension().equals( oldFileType.getDefaultExtension() ) )
            {
                return true;
            }
        }
        return false;
    }



    private boolean myInitStatus = false;

    private final SystemContext mySystemContext = new ProductionSystemContext();

    private final ArrayList<SimpleSyntaxInstance> myInstances = new ArrayList<SimpleSyntaxInstance>();

    private static final Logger LOG = LoggerFactory.getLogger();
}
