package net.intensicode.idea;

import net.intensicode.idea.config.InstanceConfiguration;

import java.util.Iterator;
import java.util.List;

public interface SimpleSyntaxAPI
{
    //void add( InstanceConfiguration aNewConfiguration );
    //
    //void remove( LoadedInstance aLoadedInstance );

    List<LoadedInstance> loadedInstances();
}
