/*
 * Copyright (C) 2015 The Minium Authors
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
package minium.web.internal.actions;

import static minium.web.internal.actions.WebWaitPredicates.forClosedWindow;
import minium.Elements;
import minium.ElementsException;
import minium.actions.internal.WaitInteraction;

/**
 * The Class WaitWindowClosedElementsInteraction.
 */
public class WaitWindowClosedElementsInteraction extends WaitInteraction {

    /**
     * Instantiates a new wait window closed elements interaction.
     *
     * @param elems the elems
     */
    public WaitWindowClosedElementsInteraction(Elements elems, String preset) {
        super(elems, true, preset);
    }

    /* (non-Javadoc)
     * @see com.vilt.minium.actions.DefaultInteraction#doPerform()
     */
    @Override
    protected void doPerform() throws ElementsException {
        if (getWaitingPreset() != null) {
            wait(getSource(), getWaitingPreset(), forClosedWindow());
        }
    }
}
