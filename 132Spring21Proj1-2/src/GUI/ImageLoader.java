package GUI;
import java.net.*;
import java.awt.*;
import java.awt.image.*;
import java.util.*;

/*
 * Load an image from a String or URL
 * 
 * @author Fawzi Emad @author Ben Bederson @author Bill Pugh 
 * Copyright (C) 2003-2020 University of Maryland
 *  
 */
public class ImageLoader {
	private static HashMap<Object, BufferedImage> cache = new HashMap<>();

	public synchronized static BufferedImage getImage(String f) {
		BufferedImage img = (BufferedImage) cache.get(f);
		if (img == null) {
			img = loadImage(f);
			cache.put(f, img);
		}
		return img;
	}

	public synchronized static BufferedImage getImage(URL u) {
		BufferedImage img = (BufferedImage) cache.get(u);
		if (img == null) {
			img = loadImage(u);
			cache.put(u, img);
		}
		return img;
	}

	//////////////////////////////////////////////////////////////
	///////////////// PRIVATE IMPLEMENTATION /////////////////////
	//////////////////////////////////////////////////////////////
	/**
	 * Loads the specified image. The new image will replace any previous image.
	 * 
	 * @param imageName
	 *            Can be either a local filename or a URL of a GIF, JPG, or PNG
	 *            image.
	 */
	private static BufferedImage loadImage(String imageName) {
		java.awt.Image origImage = Toolkit.getDefaultToolkit().getImage(
				imageName);
		return loadImage(origImage);
	}

	private static BufferedImage loadImage(URL imageURL) {
		java.awt.Image origImage = Toolkit.getDefaultToolkit().getImage(
				imageURL);
		return loadImage(origImage);
	}

	private static BufferedImage loadImage(Image origImage) {
		// Java normally loads images in a background thread.
		// This waits for the image to finish loading.
		try {
			MediaTracker tracker = new MediaTracker(new Panel());
			tracker.addImage(origImage, 0);
			tracker.waitForID(0);
			if (tracker.statusID(0, true) != MediaTracker.COMPLETE) {
				throw new RuntimeException("Unable to load image");
			}
		} catch (InterruptedException e) {
			// won't be interrupted
		}
		// If image loaded, then create a BufferedImage which is modifiable
		int imageWidth = origImage.getWidth(null);
		int imageHeight = origImage.getHeight(null);
/*		BufferedImage buf = new BufferedImage(imageWidth, imageHeight,
				BufferedImage.TYPE_INT_RGB); */
		BufferedImage buf = new BufferedImage(imageWidth, imageHeight,
				BufferedImage.TYPE_INT_ARGB);
		Graphics g = buf.createGraphics();
		g.drawImage(origImage, 0, 0, null);
		return buf;
	}
}