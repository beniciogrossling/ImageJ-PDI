package py.com;

import ij.ImagePlus;
import ij.process.ImageProcessor;

public class Histograma {

	public static void main(String[] args) {
		
		ImagePlus im = new ImagePlus("C:\\Users\\Benicio Grossling\\Downloads\\imagenes\\cameraman.tif");
		
		Compute_Histogram ch = new Compute_Histogram();
		
		ch.setup(null, im);
		
		ImageProcessor ip = im.getProcessor();
		
		ch.run(ip);
		
	}

}
