package net.intensicode.idea;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.application.Application;
import com.intellij.openapi.components.ApplicationComponent;
import com.intellij.openapi.components.ComponentConfig;
import com.intellij.openapi.extensions.PluginId;
import net.intensicode.idea.system.SystemContext;
import net.intensicode.idea.system.production.ProductionSystemContext;
import net.intensicode.idea.util.LoggerFactory;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import javax.swing.event.ListDataListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.ArrayList;



/**
 * TODO: Describe this!
 */
public final class SimpleSyntaxUI implements Configurable, ListModel
{
    public SimpleSyntaxUI()
    {
        final Application application = ApplicationManager.getApplication();
        LOG.info( "app.getComponent " + application.getComponent( "SimpleSyntax" ) );
        LOG.info( "app.getComponent " + application.getComponent( "net.intensicode.idea.SimpleSyntax" ) );
        LOG.info( "app.getComponent " + application.getComponent( SimpleSyntax.class ) );
        LOG.info( "app.getConfig " + application.getConfig( SimpleSyntax.class ) );
        LOG.info( "app.getPlugin " + application.getPlugin( PluginId.getId( "SimpleSyntax" )) );
        LOG.info( "app.getPlugin " + application.getPlugin( PluginId.getId( "Simple Syntax Highlighting" )) );

        final ComponentConfig configuration = application.getConfig( SimpleSyntax.class );

        final SimpleSyntax simpleSyntax = application.getComponent( SimpleSyntax.class );

        mySystemContext = new ProductionSystemContext();
        myInstances = new ArrayList<SimpleSyntaxInstance>();
    }

    public SimpleSyntaxUI( final SystemContext aSystemContext, final ArrayList<SimpleSyntaxInstance> aInstances )
    {
        mySystemContext = aSystemContext;
        myInstances = aInstances;
    }

    // From Configurable

    public final String getDisplayName()
    {
        return "SimpleSyntax";
    }

    @Nullable
    @NonNls
    public final String getHelpTopic()
    {
        return null;
    }

    public final Icon getIcon()
    {
        return null;
    }

    // From UnnamedConfigurable

    public final void apply() throws ConfigurationException
    {
        LOG.info( "apply" );
    }

    public final JComponent createComponent()
    {
        LOG.info( "createComponent" );

        myEditorPane = new JEditorPane();
        myEditorPane.setEnabled( false );
        myEditorPane.setPreferredSize( new Dimension( 640, 480 ) );

        myInstanceList = new JList( this );
        myInstanceList.setAutoscrolls( true );
        myInstanceList.setDragEnabled( false );
        myInstanceList.setSelectedIndex( 0 );
        myInstanceList.setSelectionMode( ListSelectionModel.SINGLE_SELECTION );
        myInstanceList.getSelectionModel().addListSelectionListener( new InstanceSelection() );

        final JPanel panel = new JPanel( new BorderLayout() );
        final JScrollPane scrollPane = new JScrollPane( myInstanceList, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );
        panel.add( scrollPane, BorderLayout.WEST );
        panel.add( myEditorPane, BorderLayout.CENTER );

        return panel;
    }

    public final void disposeUIResources()
    {
        LOG.info( "disposeUIResources" );
    }

    public final boolean isModified()
    {
        return false;
    }

    public final void reset()
    {
        LOG.info( "reset" );
    }

    // From ListModel

    public final void addListDataListener( final ListDataListener aListener )
    {
        LOG.info( "addListDataListener " + aListener );
        myListeners.add( aListener );
    }

    public final Object getElementAt( final int index )
    {
        LOG.info( "getElementAt " + index );
        return myInstances.get( index ).getName();
    }

    public final int getSize()
    {
        return myInstances.size();
    }

    public final void removeListDataListener( final ListDataListener aListener )
    {
        LOG.info( "removeListDataListener " + aListener );
        myListeners.remove( aListener );
    }



    private JList myInstanceList;

    private JEditorPane myEditorPane;

    private final SystemContext mySystemContext;

    private final ArrayList<SimpleSyntaxInstance> myInstances;

    private final ArrayList<ListDataListener> myListeners = new ArrayList<ListDataListener>();

    private static final Logger LOG = LoggerFactory.getLogger();



    private final class InstanceSelection implements ListSelectionListener
    {
        public final void valueChanged( final ListSelectionEvent aEvent )
        {
            if ( aEvent.getValueIsAdjusting() ) return;

            if ( myInstanceList.isSelectionEmpty() )
            {
                LOG.debug( "no selection" );
            }
            else
            {
                LOG.debug( "selection: " + myInstanceList.getSelectedValue() );
            }
        }
    }
}
