package net.intensicode.idea.system;

import java.io.IOException;
import java.util.HashMap;

/**
 * TODO: Describe this!
 */
public interface ScriptSupport
{
    Object createObject( String aScriptFileName, HashMap<String, Object> aVariables, Class aTargetClass ) throws IOException;
}
