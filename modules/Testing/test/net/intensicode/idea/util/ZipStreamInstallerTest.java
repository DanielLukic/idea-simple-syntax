/************************************************************************/
/* {{PROJECT_NAME}}             {{COMPANY}}             {{DATE_CREATE}} */
/************************************************************************/

package net.intensicode.idea.util;

import junit.framework.TestCase;
import net.intensicode.idea.ActionInstallTest;
import net.intensicode.idea.VirtualSystemContext;

import java.io.IOException;
import java.util.zip.ZipInputStream;

/**
 * TODO: Describe this!
 */
public final class ZipStreamInstallerTest extends TestCase
{
    public final void testInstall() throws IOException
    {
        final VirtualSystemContext context = new VirtualSystemContext();
        final CopyHandler copyHandler = new CopyHandler( context );
        final ZipStreamInstaller installer = new ZipStreamInstaller( copyHandler );
        installer.install( new ZipInputStream( getClass().getResourceAsStream( "ConfigTest.zip" ) ) );
        ActionInstallTest.validateInstallation( context );
    }
}
