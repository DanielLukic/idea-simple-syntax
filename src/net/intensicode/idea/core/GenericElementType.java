package net.intensicode.idea.core;

import com.intellij.lang.Language;
import com.intellij.psi.tree.IElementType;



/**
 * TODO: Describe this!
 */
final class GenericElementType extends IElementType
{
    GenericElementType( final Object aID, final Language aLanguage )
    {
        super( aID.toString(), aLanguage );
    }
}
