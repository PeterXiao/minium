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

import java.util.Collections;

import com.google.common.base.Objects;
import com.vilt.minium.CoreWebElements;
import com.vilt.minium.WebElementsDriver;

public class RootWebElementsImpl<T extends CoreWebElements<T>> extends DocumentRootWebElementsImpl<T> {

    private WebElementsDriver<T> wd;

    @SuppressWarnings("unchecked")
    public void init(WebElementsFactory factory, WebElementsDriver<?> wd) {
        super.init(factory);
        this.wd = (WebElementsDriver<T>) wd;
    }

    @Override
    protected Iterable<WebElementsDriver<T>> candidateWebDrivers() {
        return Collections.<WebElementsDriver<T>>singletonList(wd);
    }

    @Override
    public WebElementsDriver<T> rootWebDriver() {
        return wd;
    }

    @Override
    protected T root(T filter, boolean freeze) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean equals(Object obj) {
        if (obj instanceof RootWebElementsImpl) {
            RootWebElementsImpl<T> elem = (RootWebElementsImpl<T>) obj;
            return Objects.equal(elem.wd, this.wd);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(wd);
    }
}
