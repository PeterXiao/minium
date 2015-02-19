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
package com.vilt.minium.script;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;

import java.io.File;
import java.io.Reader;
import java.net.URL;
import java.util.List;

import org.junit.Test;
import org.mozilla.javascript.Function;
import org.openqa.selenium.Platform;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.vilt.minium.prefs.AppPreferences;

public class MiniumScriptEngineTest {

   @Test
   public void testEngineDefaultConst() throws Exception {
       // given
       MiniumScriptEngine engine = new MiniumScriptEngine();

       // when
       Object load = engine.eval("load");
       Object require = engine.eval("require");

       // then
       assertThat(load, instanceOf(Function.class));
       assertThat(require, instanceOf(Function.class));
   }

   @Test
   public void testEnginePut() throws Exception {
      // given
      MiniumScriptEngine engine = new MiniumScriptEngine();
      engine.put("foo", "bar");

      // when
      Object result = engine.eval("foo");

      // then
      assertThat(result, instanceOf(String.class));
      assertThat((String) result, equalTo("bar"));
   }

   @Test
   public void testNativeJavaObjects() throws Exception {
       // given
       MiniumScriptEngine engine = new MiniumScriptEngine();

       // when
       Object result = engine.eval("java.lang.Integer.valueOf(1)");

       // then
       assertThat(result, instanceOf(Integer.class));
   }

   @Test
   public void testRunScript() throws Exception {
       // given
       MiniumScriptEngine engine = new MiniumScriptEngine();

       // when
       URL resource = getClass().getClassLoader().getResource("test.js");
       engine.run(new File(resource.getFile()));

       // then
       assertThat((String) engine.get("foo"), equalTo("bar"));
   }

   @Test
   public void testModulePathURIs() throws Exception {
       // given
       System.setProperty("basedir", Platform.getCurrent().is(Platform.WINDOWS) ? "c:\\minium-app" : "/opt/minium-app");
       Reader reader = Resources.asCharSource(Resources.getResource("minium-prefs.json"), Charsets.UTF_8).openStream();
       AppPreferences preferences = new AppPreferences(reader);
       MiniumScriptEngine engine = new MiniumScriptEngine(RhinoPreferences.from(preferences));

       // when
       List<String> modulePathURIs = engine.getModulePathURIs();

       // then
       String modules = Platform.getCurrent().is(Platform.WINDOWS) ? "file:/c:/minium-app/modules" : "file:/opt/minium-app/modules";
       assertThat(modulePathURIs, containsInAnyOrder(modules, "http://www.lodash.com"));
   }
}
