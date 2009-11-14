/*
 * Copyright 2000-2009 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.intellij.psi.impl.cache.impl.idCache;

import com.intellij.lang.properties.parsing.PropertiesLexer;
import com.intellij.lexer.FilterLexer;
import com.intellij.lexer.Lexer;
import com.intellij.psi.impl.cache.impl.BaseFilterLexer;
import com.intellij.psi.impl.cache.impl.id.LexerBasedIdIndexer;

/**
 * Created by IntelliJ IDEA.
* User: Maxim.Mossienko
* Date: 11.02.2009
* Time: 20:34:27
* To change this template use File | Settings | File Templates.
*/
public class PropertiesIdIndexer extends LexerBasedIdIndexer {
  protected Lexer createLexer(final BaseFilterLexer.OccurrenceConsumer consumer) {
    final PropertiesFilterLexer propsLexer =
      new PropertiesFilterLexer(new PropertiesLexer(), consumer);
    return new FilterLexer(propsLexer, new FilterLexer.SetFilter(PropertiesTodoIndexer.WHITE_SPACE_SET));
  }
}
