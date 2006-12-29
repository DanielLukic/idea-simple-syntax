package net.intensicode.idea.config;

import com.intellij.lang.refactoring.NamesValidator;

import java.util.List;
import java.util.regex.Pattern;

/**
 * TODO: Describe this!
 */
public interface NamesValidatorConfiguration
{
    boolean hasExternalImplementation();

    NamesValidator createExternalImplementation();

    Pattern getIdentifierRegex();

    List<String> getKeywordList();
}
