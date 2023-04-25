package py.com;

import ij.ImagePlus;
import ij.process.ImageProcessor;

public class Call_Histograma {

	public static void main(String[] args) {
		
		ImagePlus im = new ImagePlus("C:\\Users\\Benicio Grossling\\Documents\\Proyectos\\Proyectos java\\ImageJ-PDI\\imagenes\\einstein-low-contrast.tif");
		
		Compute_Histogram ch = new Compute_Histogram();
		Histograma_Acumulativo ch2 = new Histograma_Acumulativo();
		
		ch.setup(null, im);
		ch2.setup(null, im);
		
		ImageProcessor ip = im.getProcessor();
		ImageProcessor ip2 = im.getProcessor();
		
		ch.run(ip);
		ch2.run(ip2);
		
	}

}
