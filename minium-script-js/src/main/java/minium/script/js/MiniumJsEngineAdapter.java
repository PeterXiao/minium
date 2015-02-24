package minium.script.js;

import minium.web.actions.Browser;

public class MiniumJsEngineAdapter {

    private Browser<?> browser;
    private JsBrowserFactory browserFactory;

    public MiniumJsEngineAdapter(Browser<?> browser, JsBrowserFactory factory) {
        this.browser = browser;
        browserFactory = factory;
    }

    public void adapt(JsEngine engine) {
        engine.put("__browser", browser);
        engine.put("__browserFactory", browserFactory);
        try {
            engine.eval("console = require('minium/console')", 1);
            engine.eval("minium = require('minium'); $ = minium.$; if (typeof minium.browser !== 'undefined') browser = minium.browser;", 1);
            engine.eval("expect = require('minium/expect-webelements.js')", 1);
            engine.eval("minium.__browserFactory = __browserFactory;", 1);
        } finally {
            engine.delete("__browserFactory");
            engine.delete("__browser");
        }
    }
}
