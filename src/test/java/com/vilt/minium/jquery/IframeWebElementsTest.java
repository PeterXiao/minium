package com.vilt.minium.jquery;

import static com.vilt.minium.Minium.$;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebElement;

import com.google.common.collect.Iterables;
import com.vilt.minium.MiniumBaseTest;

public class IframeWebElementsTest extends MiniumBaseTest {

	@Before
	public void openPage() {
		get("minium/tests/iframe-test.html");
	}
	
	@Test
	public void testIframe() {
		DefaultWebElements elems = $(wd).frame("#jquery-frame-1").find("input#name");
		assertThat(elems, hasSize(1));
		Iterables.get(elems, 0).sendKeys("Hello World!");
	}
	
	@Test
	public void testIframes() {
		DefaultWebElements elems = $(wd).frame().find("input#name");
		assertThat(elems, hasSize(2));
		
		for (WebElement elem : elems) {
			elem.sendKeys("Hello World!");
		}
	}
	

	@Test
	public void testIframeRelativeElements() {
		DefaultWebElements frame = $(wd).frame("#jquery-frame-1");
		DefaultWebElements input = frame.find("input#name");
		DefaultWebElements label = frame.find("label").rightOf(input);
		assertThat(label, hasSize(1));
	}
}