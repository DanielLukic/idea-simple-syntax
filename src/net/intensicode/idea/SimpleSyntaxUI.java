package net.intensicode.idea;

import com.intellij.openapi.application.*;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.extensions.PluginId;
import com.intellij.openapi.options.*;
import net.intensicode.idea.system.SystemContext;
import net.intensicode.idea.system.production.ProductionSystemContext;
import net.intensicode.idea.ui.OptionsPanel;
import net.intensicode.idea.util.LoggerFactory;
import org.jetbrains.annotations.*;

import javax.swing.*;



public final class SimpleSyntaxUI extends SettingsEditor implements Configurable
{
    public SimpleSyntaxUI()
    {
        final Application application = ApplicationManager.getApplication();
        LOG.info( "app.getComponent " + application.getComponent( "SimpleSyntax" ) );
        LOG.info( "app.getComponent " + application.getComponent( "net.intensicode.idea.SimpleSyntax" ) );
        LOG.info( "app.getComponent " + application.getComponent( SimpleSyntax.class ) );
        LOG.info( "app.getConfig " + application.getConfig( SimpleSyntax.class ) );
        LOG.info( "app.getPlugin " + application.getPlugin( PluginId.getId( "SimpleSyntax" ) ) );
        LOG.info( "app.getPlugin " + application.getPlugin( PluginId.getId( "Simple Syntax Highlighting" ) ) );

        mySimpleSyntax = ( SimpleSyntax ) application.getComponent( "net.intensicode.idea.SimpleSyntax" );
        mySystemContext = new ProductionSystemContext();
    }

    // From SettingsEditor

    protected void resetEditorFrom( final Object o )
    {
        LOG.info( "resetEditorFrom " + o );
    }

    protected void applyEditorTo( final Object o ) throws ConfigurationException
    {
        LOG.info( "applyEditorTo " + o );
    }

    @NotNull
    protected JComponent createEditor()
    {
        return new OptionsPanel( mySystemContext, mySimpleSyntax );
    }

    protected void disposeEditor()
    {
        LOG.info( "disposeEditor" );
    }

    // From Configurable

    @Nls
    public String getDisplayName()
    {
        return "Simple Syntax";
    }

    public Icon getIcon()
    {
        return null;
    }

    public String getHelpTopic()
    {
        return null;
    }

    public JComponent createComponent()
    {
        LOG.info( "createComponent" );
        return createEditor();
    }

    public boolean isModified()
    {
        LOG.info( "isModified" );
        return false;
    }

    public void apply() throws ConfigurationException
    {
        LOG.info( "apply" );
    }

    public void reset()
    {
        LOG.info( "reset" );
    }

    public void disposeUIResources()
    {
        LOG.info( "disposeUIResources" );
    }



    private final SimpleSyntax mySimpleSyntax;

    private final SystemContext mySystemContext;

    private static final Logger LOG = LoggerFactory.getLogger();
}
