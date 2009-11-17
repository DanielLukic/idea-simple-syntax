package net.intensicode.idea.ui;

import com.intellij.openapi.diagnostic.Logger;
import net.intensicode.idea.*;
import net.intensicode.idea.util.LoggerFactory;

import javax.swing.*;
import javax.swing.event.ListDataListener;
import java.util.*;



public final class AvailableInstancesModel implements ListModel
{
    public AvailableInstancesModel( final SimpleSyntaxAPI aSimpleSyntaxAPI )
    {
        mySimpleSyntaxAPI = aSimpleSyntaxAPI;
        myInstances = aSimpleSyntaxAPI.loadedInstances();
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

    private final List<LoadedInstance> myInstances;

    private final SimpleSyntaxAPI mySimpleSyntaxAPI;

    private final ArrayList<ListDataListener> myListeners = new ArrayList<ListDataListener>();

    private static final Logger LOG = LoggerFactory.getLogger();
}
