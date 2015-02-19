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

import static com.vilt.minium.impl.WaitPredicates.whileEmpty;
import static com.vilt.minium.impl.Waits.waitForPredicate;

import com.vilt.minium.CoreWebElements;
import com.vilt.minium.TimeoutException;

/**
 * The Class WaitForElementsInteraction.
 */
public class WaitWhileEmptyInteraction extends WaitInteraction {

    /**
     * Instantiates a new wait for elements interaction.
     *
     * @param elems the elems
     */
    public WaitWhileEmptyInteraction(CoreWebElements<?> elems) {
        super(elems);
    }

    /*
     * (non-Javadoc)
     *
     * @see com.vilt.minium.actions.DefaultInteraction#doPerform()
     */
    @Override
    protected void doPerform() throws TimeoutException {
        if (getWaitingPreset() != null) {
            waitForPredicate(getSource(), getWaitingPreset(), whileEmpty());
        } else {
            waitForPredicate(getSource(), getTimeout(), getInterval(), whileEmpty());
        }
    }
}
