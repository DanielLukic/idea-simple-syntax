package scripting.jflex;

import com.intellij.psi.tree.IElementType;
import net.intensicode.idea.FakeElementType;

/**
 * TODO: Describe this!
 */
public class FakePythonLanguage
{
    public final IElementType IMPORT_KEYWORD = new FakeElementType( "IMPORT_KEYWORD" );

    public final IElementType SPACE = new FakeElementType( "SPACE" );

    public final Object getTokenTypes()
    {
        return this;
    }
}
