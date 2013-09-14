package com.vilt.minium.script;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.Enumeration;
import java.util.Map;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.tools.shell.Global;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;

public class MiniumScriptEngine {

	private static final String RHINO_BOOTSTRAP_JS = "rhino/bootstrap.js";
	private static final String BOOTSTRAP_EXTS_JS = "rhino/bootstrap-extension.js";

	private static final Logger logger = LoggerFactory.getLogger(MiniumScriptEngine.class);
	private ClassLoader classLoader;

	private WebElementsDrivers webElementsDrivers;
	private Map<String, Object> context = Maps.newHashMap();

	private Global scope;

	public MiniumScriptEngine() {
		this(null);
	}

	public MiniumScriptEngine(WebElementsDrivers webElementsDrivers) {
		this(webElementsDrivers, MiniumScriptEngine.class.getClassLoader());
	}

	public MiniumScriptEngine(WebElementsDrivers webElementsDrivers, ClassLoader classLoader) {
		this.webElementsDrivers = webElementsDrivers;
		this.classLoader = classLoader;
		initScope();
	}

	public void put(String varName, Object object) {
		if (scope != null) {
			scope.put(varName, scope, object);
		} else {
			context.put(varName, object);
		}
	}

	public Object eval(String expression) throws Exception {
		logger.debug("Evaluating expression: {}", expression);

		Context cx = Context.enter();
		try {
			Object result = cx.evaluateString(scope, expression, "<expression>", 1, null);
			return result;
		} catch (Exception e) {
			logger.error("Evaluation of {} failed", expression, e);
			throw e;
		} finally {
			Context.exit();
		}
	}

	protected void initScope() {
		Context cx = Context.enter();
		try {
			scope = new Global(cx); // This gives us access to global functions
									// like load()
			scope.put("webElementsDrivers", scope, webElementsDrivers);

			logger.debug("Loading minium bootstrap file");
			InputStreamReader bootstrap = new InputStreamReader(classLoader.getResourceAsStream(RHINO_BOOTSTRAP_JS), "UTF-8");
			cx.evaluateReader(scope, bootstrap, RHINO_BOOTSTRAP_JS, 1, null);

			Enumeration<URL> resources = classLoader.getResources(BOOTSTRAP_EXTS_JS);

			while (resources.hasMoreElements()) {
				URL resourceUrl = resources.nextElement();
				Reader reader = resourceUrlReader(resourceUrl);
				if (reader != null) {
					logger.debug("Loading extension bootstrap from '{}'", resourceUrl.toString());
					cx.evaluateReader(scope, reader, resourceUrl.toString(), 1, null);
				}
			}

			for (String varName : context.keySet()) {
				scope.put(varName, scope, context.get(varName));
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			Context.exit();
		}
	}

	private Reader resourceUrlReader(URL resourceUrl) {
		try {
			InputStream is = resourceUrl.openStream();
			return new BufferedReader(new InputStreamReader(is));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
