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
package com.vilt.minium.impl.actions.touch;

import com.vilt.minium.CoreWebElements;
import com.vilt.minium.actions.touch.TouchInteraction;

/**
 * The Class TouchScrollInteraction.
 */
public class TouchScrollInteraction extends TouchInteraction {

    /**
     * Instantiates a new touch scroll interaction.
     *
     * @param elems the elems
     * @param xOffset the x offset
     * @param yOffset the y offset
     */
    public TouchScrollInteraction(CoreWebElements<?> elems, int xOffset, int yOffset) {
        super(elems);
    }

    @Override
    protected void doPerform() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Not implemented yet");
    }

}
