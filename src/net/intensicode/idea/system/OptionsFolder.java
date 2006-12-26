package net.intensicode.idea.system;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;



/**
 * TODO: Describe this!
 */
public interface OptionsFolder
{
    String[] findConfigurations();

    Icon loadIcon( String aRelativeFileName );

    boolean fileExists( String aRelativeFileName );

    InputStream streamFile( String aRelativeFileName ) throws FileNotFoundException;

    String readFileIntoString( String aRelativeFileName ) throws IOException;

    void writeFileFromStream( String aRelativeFileName, InputStream aStream ) throws IOException;
}
