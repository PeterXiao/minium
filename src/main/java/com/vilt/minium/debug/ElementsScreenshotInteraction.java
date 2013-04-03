package com.vilt.minium.debug;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;

import com.vilt.minium.CoreWebElements;
import com.vilt.minium.WebElements;

/**
 * The Class ElementsScreenshotInteraction.
 */
public class ElementsScreenshotInteraction extends ScreenshotInteraction {

	/**
	 * Instantiates a new elements screenshot interaction.
	 *
	 * @param elems the elems
	 * @param stream the stream
	 */
	public ElementsScreenshotInteraction(WebElements elems, OutputStream stream) {
		super(elems, stream);
	}

	/* (non-Javadoc)
	 * @see com.vilt.minium.actions.DefaultInteraction#doPerform()
	 */
	@Override
	protected void doPerform() {
		try {
			WebElement elem = getFirstElement();
			
			Point p = elem.getLocation();
			int width = elem.getSize().getWidth();
			int height = elem.getSize().getHeight();

			CoreWebElements<?> coreWebElements = (CoreWebElements<?>) getSource();
			byte[] screenshot = coreWebElements.webDriver().getScreenshotAs(OutputType.BYTES);

			BufferedImage img = null;

			img = ImageIO.read(new ByteArrayInputStream(screenshot));
			BufferedImage dest = img.getSubimage(p.getX(), p.getY(), width, height);

			ImageIO.write(dest, "png", stream);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
}
