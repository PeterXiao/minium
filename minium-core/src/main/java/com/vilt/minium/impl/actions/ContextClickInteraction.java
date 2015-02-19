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
package com.vilt.minium.impl.actions;

import org.openqa.selenium.WebElement;

import com.vilt.minium.CoreWebElements;
import com.vilt.minium.Offsets.Offset;
import com.vilt.minium.Point;

/**
 * The Class ContextClickInteraction.
 */
public class ContextClickInteraction extends MouseInteraction {

    /**
     * Instantiates a new context click interaction.
     *
     * @param source the source
     */
    public ContextClickInteraction(CoreWebElements<?> source, Offset offset) {
        super(source, offset);
    }

    /* (non-Javadoc)
     * @see com.vilt.minium.actions.DefaultInteraction#doPerform()
     */
    @Override
    protected void doPerform() {
        WebElement source = isSourceDocumentRoot() ? null : getFirstElement();
        Point offsetPoint = getOffsetPoint(source);
        if (offsetPoint == null) {
            getActions().contextClick(source).perform();
        } else {
            getActions().moveToElement(source, offsetPoint.x(), offsetPoint.y()).contextClick().perform();
        }
    }
}
