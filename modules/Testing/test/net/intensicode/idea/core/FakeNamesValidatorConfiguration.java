package net.intensicode.idea.core;

import com.intellij.lang.refactoring.NamesValidator;
import net.intensicode.idea.config.NamesValidatorConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * TODO: Describe this!
 */
final class FakeNamesValidatorConfiguration implements NamesValidatorConfiguration
{
    public NamesValidator createExternalImplementation()
    {
        throw new RuntimeException( "NYI" );
    }

    public Pattern getIdentifierRegex()
    {
        return Pattern.compile( "onlyme" );
    }

    public List<String> getKeywordList()
    {
        return new ArrayList<String>()
        {
            {
                add( "abc" );
                add( "def" );
            }
        };
    }

    public boolean hasExternalImplementation()
    {
        throw new RuntimeException( "NYI" );
    }
}
