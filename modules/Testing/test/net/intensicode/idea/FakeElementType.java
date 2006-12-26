package net.intensicode.idea;

import com.intellij.lang.Language;
import com.intellij.psi.tree.IElementType;



/**
 * TODO: Describe this!
 */
public final class FakeElementType extends IElementType
{
    public FakeElementType( final Object aID, final Language aLanguage )
    {
        super( aID.toString(), aLanguage, false );
    }
}
