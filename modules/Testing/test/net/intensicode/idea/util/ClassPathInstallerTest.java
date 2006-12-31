/************************************************************************/
/* {{PROJECT_NAME}}             {{COMPANY}}             {{DATE_CREATE}} */
/************************************************************************/

package net.intensicode.idea.util;

import junit.framework.TestCase;
import net.intensicode.idea.ActionInstallTest;
import net.intensicode.idea.VirtualSystemContext;

import java.io.File;
import java.io.IOException;

/**
 * TODO: Describe this!
 */
public final class ClassPathInstallerTest extends TestCase
{
    public final void testInstall() throws IOException
    {
        final VirtualSystemContext context = new VirtualSystemContext();
        final CopyHandler copyHandler = new CopyHandler( context );
        final ClassPathInstaller installer = new ClassPathInstaller( copyHandler );
        installer.install( new File( "config" ) );
        ActionInstallTest.validateInstallation( context );
    }
}
