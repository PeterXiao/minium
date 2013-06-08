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
package com.vilt.minium;

import static com.vilt.minium.Minium.$;
import static com.vilt.minium.impl.WaitPredicates.whileEmpty;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.google.common.collect.Iterables;

public class WaitWebElementsTest extends MiniumBaseTest {

	@BeforeMethod
	public void openPage() {
		get("minium/tests/jquery-test.html");
	}
	
	@Test(expectedExceptions = TimeoutException.class)
	public void testUnexistingElement() {
		$(wd, "#no-element").wait(whileEmpty());
	}
	
	@Test
	public void testExistingElement() {
		DefaultWebElements wait = $(wd, "input").wait(whileEmpty());
		Assert.assertTrue(Iterables.size(wait) > 0);
	}
}
