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
package minium.actions.internal;

import minium.Elements;

/**
 * The Class WaitOrTimeoutWhileElementsInteraction.
 */
public class CheckForUnexistenceInteraction extends WaitInteraction {

    /**
     * Instantiates a new wait or timeout while elements interaction.
     *
     * @param elems
     *            the elems
     */
    public CheckForUnexistenceInteraction(Elements elems, String waitingPreset) {
        super(elems, false, waitingPreset);
    }

    @Override
    protected void doPerform() {
        waitOrTimeoutFor(WaitPredicates.forUnexistence());
    }
}
