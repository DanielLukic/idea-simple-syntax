package net.intensicode.idea.system;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;



/**
 * TODO: Describe this!
 */
public interface OptionsFolder
{
    String[] findConfigurations();

    Icon loadIcon( String aRelativeFileName );

    boolean fileExists( String aRelativeFileName );

    String makeFileName( String aRelativeFileName );

    Reader readFile( String aRelativeFileName ) throws IOException;

    InputStream streamFile( String aRelativeFileName ) throws FileNotFoundException;

    String readFileIntoString( String aRelativeFileName ) throws IOException;

    void writeFileFromStream( String aRelativeFileName, InputStream aStream ) throws IOException;

    String classPathBase();
}
