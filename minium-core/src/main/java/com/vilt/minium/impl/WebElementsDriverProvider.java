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

import org.openqa.selenium.WebDriver;

import com.vilt.minium.Configuration;
import com.vilt.minium.CoreWebElements;
import com.vilt.minium.WebElements;
import com.vilt.minium.WebElementsDriver;

/**
 * The Interface WebElementsDriverProvider.
 *
 * @param <T> the generic type
 */
public interface WebElementsDriverProvider<T extends CoreWebElements<T>> extends WebElements {

    /**
     * Web drivers.
     *
     * @return the iterable
     */
    public Iterable<WebElementsDriver<T>> webDrivers();

    /**
     * Web driver.
     *
     * @return the web elements driver
     */
    public WebElementsDriver<T> webDriver();

    /**
     * Root web elements driver.
     *
     * @return the root web elements driver
     */
    public WebElementsDriver<T> rootWebDriver();

    /**
     * Native web driver.
     *
     * @return the web driver
     */
    public WebDriver nativeWebDriver();

    /**
     * Configuration.
     *
     * @return the configuration
     */
    public Configuration configure();
}
