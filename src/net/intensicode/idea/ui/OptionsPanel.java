package net.intensicode.idea.ui;

import com.intellij.openapi.diagnostic.Logger;
import net.intensicode.idea.SimpleSyntaxAPI;
import net.intensicode.idea.system.SystemContext;
import net.intensicode.idea.util.LoggerFactory;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;



public final class OptionsPanel extends JPanel
{
    public OptionsPanel( final SystemContext aSystemContext, final SimpleSyntaxAPI aSimpleSyntaxAPI )
    {
        mySystemContext = aSystemContext;
        mySimpleSyntaxAPI = aSimpleSyntaxAPI;

        final JLabel myNameLabel = new JLabel( "Label" );
        final JTextField myNameField = new JTextField();

        final JLabel myDescriptionLabel = new JLabel( "Description" );
        final JTextField myDescriptionField = new JTextField();

        final JLabel myKindLabel = new JLabel( "Configuration Kind" );
        final JComboBox myKindBox = new JComboBox( new String[]{ "Keywords only", "Groovy JFlex", "Groovy JParsec", "JRuby JParsec" } );

        final JPanel basicSettings = new JPanel( new GridLayout( 0, 2 ) );
        basicSettings.setBorder( new TitledBorder( "Basic Settings" ) );
        basicSettings.add( myNameLabel );
        basicSettings.add( myNameField );
        basicSettings.add( myDescriptionLabel );
        basicSettings.add( myDescriptionField );
        basicSettings.add( myKindLabel );
        basicSettings.add( myKindBox );

        final JEditorPane configEditor = new JEditorPane();

        final JPanel configEditorPanel = new JPanel( new BorderLayout() );
        configEditorPanel.setBorder( new TitledBorder( "Configuration Editor" ) );
        configEditorPanel.add( configEditor, BorderLayout.CENTER );

        final JTextArea information = new JTextArea();
        information.setEditable( false );

        final JPanel informationPanel = new JPanel( new BorderLayout() );
        informationPanel.setBorder( new TitledBorder( "Information" ) );
        informationPanel.add( information, BorderLayout.CENTER );

        final JPanel configuration = new JPanel( new BorderLayout() );
        configuration.add( basicSettings, BorderLayout.NORTH );
        configuration.add( configEditorPanel, BorderLayout.CENTER );
        configuration.add( informationPanel, BorderLayout.SOUTH );

        final AvailableInstancesModel instancesModel = new AvailableInstancesModel( mySimpleSyntaxAPI );

        final JList instancesList = new JList( instancesModel );
        instancesList.setAutoscrolls( true );
        instancesList.setDragEnabled( false );
        instancesList.setSelectedIndex( 0 );
        instancesList.setSelectionMode( ListSelectionModel.SINGLE_SELECTION );

        final InstanceSelectionHandler instanceSelectionHandler = new InstanceSelectionHandler( instancesList );

        final ListSelectionModel instancesSelectionModel = instancesList.getSelectionModel();
        instancesSelectionModel.addListSelectionListener( instanceSelectionHandler );

        final JScrollPane instances = new JScrollPane( instancesList, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );

        final JPanel myConfigButtons = new JPanel( new GridLayout( 0, 3 ) );
        myConfigButtons.add( new JButton( "Add" ) );
        myConfigButtons.add( new JButton( "Clone" ) );
        myConfigButtons.add( new JButton( "Remove" ) );

        final JPanel selector = new JPanel( new BorderLayout() );
        selector.setBorder( new TitledBorder( "Available Instances" ) );
        selector.add( instances, BorderLayout.CENTER );
        selector.add( myConfigButtons, BorderLayout.SOUTH );

        setLayout( new BorderLayout() );
        add( selector, BorderLayout.WEST );
        add( configuration, BorderLayout.CENTER );
    }



    private final SystemContext mySystemContext;

    private final SimpleSyntaxAPI mySimpleSyntaxAPI;

    private static final Logger LOG = LoggerFactory.getLogger();
}