package net.intensicode.idea.system;

import java.io.IOException;

/**
 * TODO: Describe this!
 */
public interface ScriptSupport
{
    /**
     * Executes a script and transforms returned object into a target class instance - if necessary.
     */
    Object createObject( String aScriptFileName, Class aTargetClass ) throws IOException;
}
