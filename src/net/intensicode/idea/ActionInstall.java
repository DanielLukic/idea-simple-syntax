package net.intensicode.idea;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.options.ConfigurationException;
import net.intensicode.idea.system.ResourceLoader;
import net.intensicode.idea.system.SystemContext;
import net.intensicode.idea.system.production.ProductionSystemContext;
import net.intensicode.idea.util.ClassPathInstaller;
import net.intensicode.idea.util.CopyHandler;
import net.intensicode.idea.util.ZipStreamInstaller;

import java.io.File;
import java.io.InputStream;
import java.util.zip.ZipInputStream;



/**
 * TODO: Describe this!
 */
public final class ActionInstall extends AnAction
{
    public ActionInstall()
    {
        this( new ProductionSystemContext() );
    }

    public ActionInstall( final SystemContext aSystemContext )
    {
        mySystemContext = aSystemContext;
        myResourceLoader = aSystemContext.getResourceLoader();
    }

    // From AnAction

    public final void actionPerformed( final AnActionEvent aEvent )
    {
        try
        {
            final CopyHandler copyHandler = new CopyHandler( mySystemContext );

            if ( myResourceLoader.isAvailable( SIMPLE_SYNTAX_CONFIG_ZIP ) )
            {
                final InputStream inputStream = myResourceLoader.stream( SIMPLE_SYNTAX_CONFIG_ZIP );
                final ZipInputStream zipStream = new ZipInputStream( inputStream );
                new ZipStreamInstaller( copyHandler ).install( zipStream );
            }
            else
            {
                final String configFolderPath = getConfigFolderPath();
                final File configFolder = new File( configFolderPath );
                new ClassPathInstaller( copyHandler ).install( configFolder );
            }
        }
        catch ( final Throwable t )
        {
            mySystemContext.getErrorHandler().onSimpleSyntaxInstallFailed( t );
        }
    }

    // Implementation

    private final String getConfigFolderPath()
    {
        return mySystemContext.getPluginFolder().getPath();
    }



    private final SystemContext mySystemContext;

    private final ResourceLoader myResourceLoader;

    private static final String SIMPLE_SYNTAX_CONFIG_ZIP = "SimpleSyntax-config.zip";
}
