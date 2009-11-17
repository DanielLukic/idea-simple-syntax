package net.intensicode.idea;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SettingsEditor;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.application.Application;
import com.intellij.openapi.components.ApplicationComponent;
import com.intellij.openapi.components.ComponentConfig;
import com.intellij.openapi.extensions.PluginId;
import com.jgoodies.forms.layout.FormLayout;
import net.intensicode.idea.system.SystemContext;
import net.intensicode.idea.system.production.ProductionSystemContext;
import net.intensicode.idea.util.LoggerFactory;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nls;

import javax.swing.*;
import javax.swing.event.ListDataListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;



public final class SimpleSyntaxUI extends SettingsEditor implements Configurable, ListModel
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

        mySystemContext = new ProductionSystemContext();
        myInstances = new ArrayList<SimpleSyntaxInstance>();

        final SimpleSyntax simpleSyntax = application.getComponent( SimpleSyntax.class );
        final Iterator<SimpleSyntaxInstance> instances = simpleSyntax.instances();
        while ( instances.hasNext() ) myInstances.add( instances.next() );
    }

    public SimpleSyntaxUI( final SystemContext aSystemContext, final ArrayList<SimpleSyntaxInstance> aInstances )
    {
        mySystemContext = aSystemContext;
        myInstances = aInstances;
    }

    // From SettingsEditor

    protected void resetEditorFrom( final Object o )
    {
    }

    protected void applyEditorTo( final Object o ) throws ConfigurationException
    {
    }

    @NotNull
    protected JComponent createEditor()
    {
        final JLabel myNameLabel = new JLabel( "Label" );
        final JTextField myNameField = new JTextField();

        final JLabel myDescriptionLabel = new JLabel( "Description" );
        final JTextField myDescriptionField = new JTextField();

        final JLabel myKindLabel = new JLabel( "Configuration Kind" );
        final JComboBox myKindBox = new JComboBox( new String[]{"Keywords only", "Groovy JFlex", "Groovy JParsec", "JRuby JParsec"} );
        
        final JPanel basicSettings = new JPanel( new GridLayout(0, 2 ) );
        basicSettings.add( myNameLabel );
        basicSettings.add( myNameField );
        basicSettings.add( myDescriptionLabel );
        basicSettings.add( myDescriptionField );
        basicSettings.add( myKindLabel );
        basicSettings.add( myKindBox );

        final JEditorPane configEditor = new JEditorPane();
        final JTextArea information = new JTextArea();
        information.setEditable( false );

        final JPanel configuration = new JPanel( new BorderLayout( ) );
        configuration.add( basicSettings, BorderLayout.NORTH );
        configuration.add( configEditor, BorderLayout.CENTER );
        configuration.add( information, BorderLayout.SOUTH );

        myInstanceList = new JList( this );
        myInstanceList.setAutoscrolls( true );
        myInstanceList.setDragEnabled( false );
        myInstanceList.setSelectedIndex( 0 );
        myInstanceList.setSelectionMode( ListSelectionModel.SINGLE_SELECTION );
        myInstanceList.getSelectionModel().addListSelectionListener( new InstanceSelection() );

        final JScrollPane instances = new JScrollPane( myInstanceList, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );

        final JPanel myConfigButtons = new JPanel( new GridLayout( 0, 3 ) );
        myConfigButtons.add( new JButton( "Add") );
        myConfigButtons.add( new JButton( "Clone") );
        myConfigButtons.add( new JButton( "Remove") );

        final JPanel selector = new JPanel( new BorderLayout() );
        selector.add( instances, BorderLayout.CENTER );
        selector.add( myConfigButtons, BorderLayout.SOUTH );

        final JPanel panel = new JPanel( new BorderLayout() );
        panel.add( selector, BorderLayout.WEST );
        panel.add( configuration, BorderLayout.CENTER );

        return panel;
    }

    protected void disposeEditor()
    {
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
        return createEditor();
    }

    public boolean isModified()
    {
        return false;
    }

    public void apply() throws ConfigurationException
    {
    }

    public void reset()
    {
    }

    public void disposeUIResources()
    {
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
