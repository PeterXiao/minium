package minium.web.actions;

import minium.Dimension;
import minium.Point;
import minium.actions.Configuration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.Options;

/**
 * Extends {@link Configuration} by allowing cookies and browser windows management.
 *
 * @see Options
 * @author rui.figueira
 */
public interface WebConfiguration extends Configuration {

    /**
     * This is a Minium adapter for {@link WebDriver.Window}.
     *
     * @see org.openqa.selenium.WebDriver.Window
     * @author rui.figueira
     */
    interface Window {

        /**
         * Set the size of the current window. This will change the outer window dimension, not just the view port,
         * synonymous to {@code window.resizeTo()} in javascript.
         *
         * @param targetSize the target size.
         */
        void setSize(Dimension targetSize);

        /**
         * Set the position of the current window. This is relative to the upper left corner of the screen,
         * synonymous to {@code window.moveTo()} in javascript.
         *
         * @param targetPosition the target position of the window.
         */
        void setPosition(Point targetPosition);

        /**
         * Get the size of the current window. This will return the outer window dimension, not just the view port.
         *
         * @return the current window size
         */
        Dimension getSize();

        /**
         * Get the position of the current window, relative to the upper left corner of the screen.
         *
         * @return the current window position.
         */
        Point getPosition();

        /**
         * Maximizes the current window if it is not already maximized.
         */
        void maximize();
    }

    /**
     * <p>Maintains all cookies for this browser. It is possible to access cookies, as well as add and remove them.
     * This is a Minium adapter for {@link WebDriver.Options}</p>
     * <p>This is a chainable interface (to go back to the browser {@link WebConfiguration}, call {@code .done()}).</p>
     *
     * @see WebDriver.Options
     * @author rui.figueira
     */
    interface CookieCollection extends Iterable<Cookie> {

        /**
         * Adds a cookie to this browser.
         *
         * @param cookie the cookie to add
         * @return this {@link CookieCollection}
         */
        WebConfiguration.CookieCollection add(Cookie cookie);

        /**
         * Removes a cookie from this browser.
         *
         * @param name name of the cookie we want to remove.
         * @return this {@link CookieCollection}
         */
        WebConfiguration.CookieCollection remove(String name);

        /**
         * Removes a cookie from this browser.
         *
         * @param cookie the cookie to remove
         * @return this {@link CookieCollection}
         */
        WebConfiguration.CookieCollection remove(Cookie cookie);

        /**
         * Removes all cookies from this browser.
         *
         * @return this {@link CookieCollection}
         */
        WebConfiguration.CookieCollection clear();

        /**
         * Gets a cookie by name.
         *
         * @param name name of the cookie to get
         * @return this {@link CookieCollection}
         */
        Cookie get(String name);

        /**
         * Goes back to this browser {@link WebConfiguration}.
         *
         * @return this browser {@link WebConfiguration}
         */
        public WebConfiguration done();
    }

    /**
     * Gets the cookies collection so that cookies can be added or removed.
     *
     * @return the cookies collection
     */
    WebConfiguration.CookieCollection cookies();

    /**
     * Gets the window manager object, which allows window position and dimensions to be
     * controlled.
     *
     * @return this configuration {@link Window}
     */
    WebConfiguration.Window window();
}