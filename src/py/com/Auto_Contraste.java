package py.com;

import ij.IJ;
import ij.ImagePlus;
import ij.process.ImageConverter;
import ij.process.ImageProcessor;

public class Auto_Contraste {

	public static void main(String[] args) {
		String ruta = ("C:\\Users\\Benicio Grossling\\Documents\\Proyectos\\Proyectos java\\ImageJ-PDI\\imagenes\\einstein-low-contrast.tif");
		ImagePlus im = IJ.openImage(ruta); 				// carga la imagen
		ImagePlus im2 = im.duplicate();					// duplicamos la imagen
		ImageConverter ic = new ImageConverter(im2);
		ic.convertToGray8();
		ImageProcessor ip = im2.getProcessor(); 		// Para procesar la imagen
				
		int M = ip.getWidth(); 							//Ancho de la imagen
		int N = ip.getHeight(); 						//Alto de la imagen
		
		int aMin = 0;
		int aMax = 255;
		
		int aHi = 0;
		int aLo = 255;
		
		// iteramos sobre todas las coordenadas de la imagen (u,v)
		for(int v = 0; v < N; v++){
			for(int u = 0; u < M; u++){
				if (ip.getPixel(u, v) > aHi) {
					aHi = ip.getPixel(u, v);
				}
				if (ip.getPixel(u, v) < aLo) {
					aLo = ip.getPixel(u, v);
				}
			}
		}
		
		IJ.log("aHi: " + aHi);
		IJ.log("aLo: " + aLo);
		
		for(int v = 0; v < N; v++){
			for(int u = 0; u < M; u++){
				int a = ip.getPixel(u, v);
				int fac = aMin + (a - aLo) * ((aMax - aMin) / (aHi - aLo));
				if (fac < 0) {
					fac = 0;
				}
				if (fac > 255) {
					fac = 255;
				}
				ip.putPixel(u, v, fac);
			}
		}
				
		im.show(); // Muestra la imagen original
		im2.show(); //Muestra la imagen modificada
				
		//Guardamos la imagen
		IJ.save(im2, "C:\\Users\\Benicio Grossling\\OneDrive\\Escritorio\\grises-Brode-Blanco.jpg");
		}

}


