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

import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.net.URI;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Undefined;
import org.mozilla.javascript.Wrapper;
import org.mozilla.javascript.tools.shell.Global;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;

public class MiniumScriptEngine {

    interface ContextCallable<V, X extends Exception> {
        public V call(Context cx) throws X;
    }

    private static final Logger logger = LoggerFactory.getLogger(MiniumScriptEngine.class);
    private static final Function<String, String> TO_URI_FN = new Function<String, String>() {

        @Override
        public String apply(String input) {
            try {
                URI uri = new URI(input);
                return uri.toURL().toString();
            } catch (Exception e) {
                return new File(input).toURI().toString();
            }
        }
    };

    private Global scope;
    private MiniumContextLoader contextLoader;
    private RhinoPreferences preferences;

    public MiniumScriptEngine() {
        this(new RhinoPreferences());
    }

    public MiniumScriptEngine(RhinoPreferences preferences) {
        this(new MiniumContextLoader(), preferences);
    }

    public MiniumScriptEngine(MiniumContextLoader contextLoader, RhinoPreferences preferences) {
        this.contextLoader = contextLoader;
        this.preferences = preferences;
        initScope();
    }

    public boolean contains(final String varName) {
        return runWithContext(new ContextCallable<Boolean, RuntimeException>() {

            @Override
            public Boolean call(Context cx) {
                return scope.get(varName) != null;
            }

        });
    }

    public Object get(final String varName) {
        return runWithContext(new ContextCallable<Object, RuntimeException>() {

            @Override
            public Object call(Context cx) {
                return getUnwrappedResult(scope.get(varName));
            }

        });
    }

    @SuppressWarnings("unchecked")
    public <T> T get(final String varName, Class<T> clazz) {
        Object object = get(varName);
        return (T) object;
    }

    public void put(final String varName, final Object object) {
        runWithContext(new ContextCallable<Void, RuntimeException>() {

            @Override
            public Void call(Context cx) {
                scope.put(varName, scope, object != null ? Context.javaToJS(object, scope) : null);
                return null;
            }

        });
    }

    public void delete(final String varName) {
        runWithContext(new ContextCallable<Void, RuntimeException>() {

            @Override
            public Void call(Context cx) {
                scope.delete(varName);
                return null;
            }

        });
    }

    public void run(final File file) throws Exception {
        logger.debug("Executing file: {}", file);
        FileReader reader = new FileReader(file);
        try {
            doRun(reader, file.getPath());
        } finally {
            IOUtils.closeQuietly(reader);
        }
    }

    public void run(final Reader reader, final String sourceName) throws Exception {
        logger.debug("Executing reader for sourceName: {}", sourceName);
        doRun(reader, sourceName);
    }

    public Object eval(String expression) throws Exception {
        return eval(expression, 1);
    }

    public Object eval(final String expression, final int lineNumber) throws Exception {
        logger.debug("Evaluating expression: {}", expression);
        return runWithContext(new ContextCallable<Object, Exception>() {
            @Override
            public Object call(Context cx) throws Exception {
                try {
                    Object result = cx.evaluateString(scope, expression, "<expression>", lineNumber, null);
                    return getUnwrappedResult(result);
                } catch (Exception e) {
                    logger.error("Evaluation of {} failed", expression, e);
                    throw e;
                }
            }
        });
    }

    protected void doRun(final Reader reader, final String sourceName) throws Exception {
        runWithContext(new ContextCallable<Void, Exception>() {

            @Override
            public Void call(Context cx) throws Exception {
                try {
                    cx.evaluateReader(scope, reader, sourceName, 1, null);
                    return null;
                } catch (Exception e) {
                    logger.error("Execution of {} failed", sourceName, e);
                    throw e;
                }
            }
        });
    }

    protected void initScope() {
        runWithContext(new ContextCallable<Void, RuntimeException>() {
            @Override
            public Void call(Context cx) {
                try {
                    // Global gives us access to global functions like load()
                    scope = new Global(cx);
                    List<String> modulePathURIs = getModulePathURIs();
                    logger.info("Module paths: {}", Joiner.on(", ").join(modulePathURIs));
                    scope.installRequire(cx, modulePathURIs, false);
                    contextLoader.load(cx, scope);
                    return null;
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    protected <V, X extends Exception> V runWithContext(ContextCallable<V, X> fn) throws X {
        Context cx = Context.enter();
        try {
            return fn.call(cx);
        } finally {
            Context.exit();
        }
    }

    protected List<String> getModulePathURIs() {
       return Lists.transform(preferences.getModulePath(), TO_URI_FN);
    }

    private Object getUnwrappedResult(Object result) {
        if (result instanceof Undefined)
            return null;
        if (result instanceof Wrapper)
            return ((Wrapper) result).unwrap();
        return result;
    }
}
