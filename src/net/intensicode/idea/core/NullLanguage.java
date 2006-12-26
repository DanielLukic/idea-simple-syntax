package net.intensicode.idea.core;

import com.intellij.lang.Language;

/**
 * TODO: Describe this!
 */
public final class NullLanguage extends Language
{
    public static final NullLanguage INSTANCE = new NullLanguage();

    private NullLanguage()
    {
        super( "NULL" );
    }
}
