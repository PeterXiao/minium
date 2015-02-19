/*
 * Copyright (C) 2013 The Minium Authors
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
package com.vilt.minium.impl;

import static com.google.common.collect.Iterables.size;

import java.util.Collections;

import com.google.common.collect.Iterables;
import com.vilt.minium.CoreWebElements;
import com.vilt.minium.WebElementsDriver;
import com.vilt.minium.WebElementsException;

public class FrozenWindowWebElementsImpl<T extends CoreWebElements<T>> extends WindowWebElementsImpl<T> {

    private WebElementsDriver<T> frozenWebElementsDriver;

    public void init(WebElementsFactory factory, T parent, String expr) {
        init(factory, parent, parent.windows().find(expr));
    }

    @Override
    public Iterable<WebElementsDriver<T>> candidateWebDrivers() {
        if (frozenWebElementsDriver == null) {
            WebElementsDriver<T> driver = null;
            Iterable<WebElementsDriver<T>> candidateHandles = super.candidateWebDrivers();
            int size = size(candidateHandles);
            if (size == 1) {
                driver = Iterables.getFirst(candidateHandles, null);
            } else if (size > 1) {
                throw new WebElementsException("Cannot freeze window because more than one window matched");
            }

            if (driver != null) {
                frozenWebElementsDriver = new WindowWebElementsDriver<T>(rootWebDriver(), factory, driver.getWindowHandle());
            }
        }

        if (frozenWebElementsDriver == null) {
            return Collections.emptyList();
        }

        return Collections.singletonList(frozenWebElementsDriver);
    }

}
