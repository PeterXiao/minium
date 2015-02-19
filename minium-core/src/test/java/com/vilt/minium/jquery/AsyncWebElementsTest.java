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
package com.vilt.minium.jquery;

import static com.vilt.minium.Minium.$;
import static org.testng.Assert.assertEquals;

import java.util.concurrent.TimeUnit;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.vilt.minium.Async;
import com.vilt.minium.DefaultWebElementsDriver;
import com.vilt.minium.JQueryResources;
import com.vilt.minium.MiniumBaseTest;
import com.vilt.minium.WebElements;

public class AsyncWebElementsTest extends MiniumBaseTest {

    @JQueryResources("js/async-test.js")
    public interface AsyncWebElements extends WebElements {
        @Async
        public String asyncHelloWorld(String name);
    }

    @BeforeClass
    @Override
    public void before() {
        wd = new DefaultWebElementsDriver(createNativeWebDriver(), AsyncWebElements.class);
        wd.manage().timeouts().setScriptTimeout(5, TimeUnit.SECONDS);
    }

    @BeforeMethod
    public void openPage() {
        get("minium/tests/jquery-test.html");
    }

    @Test
    public void testAsyncHelloWorld() {
        assertEquals("Hello, World!", ((AsyncWebElements) $(wd)).asyncHelloWorld("World"));
    }
}
