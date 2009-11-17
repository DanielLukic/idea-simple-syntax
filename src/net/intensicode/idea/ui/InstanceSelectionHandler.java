package net.intensicode.idea.ui;

import com.intellij.openapi.diagnostic.Logger;
import net.intensicode.idea.util.LoggerFactory;

import javax.swing.*;
import javax.swing.event.*;

public final class InstanceSelectionHandler implements ListSelectionListener
{
    public InstanceSelectionHandler( final JList aInstancesList )
    {
        myInstancesList = aInstancesList;
    }

    public final void valueChanged( final ListSelectionEvent aEvent )
    {
        if ( aEvent.getValueIsAdjusting() ) return;

        if ( myInstancesList.isSelectionEmpty() )
        {
            LOG.debug( "no selection" );
        }
        else
        {
            LOG.debug( "selection: " + myInstancesList.getSelectedValue() );
        }
    }

    private final JList myInstancesList;

    private static final Logger LOG = LoggerFactory.getLogger();
}
