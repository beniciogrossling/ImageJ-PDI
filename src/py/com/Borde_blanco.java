package py.com;

import ij.IJ;
import ij.ImagePlus;
import ij.process.ImageConverter;
import ij.process.ImageProcessor;

public class Borde_blanco {


		public static void main(String[] args) {
			String ruta = "C:\\Users\\Benicio Grossling\\OneDrive\\Escritorio\\grises.jpg";
			ImagePlus im = IJ.openImage(ruta); 				// carga la imagen
			ImagePlus im2 = im.duplicate();					// duplicamos la imagen
			ImageConverter ic = new ImageConverter(im2);
			ic.convertToGray8();
			ImageProcessor ip = im2.getProcessor(); 		// Para procesar la imagen
			
			int M = ip.getWidth(); 							//Ancho de la imagen
			int N = ip.getHeight(); 						//Alto de la imagen
			
			// iteramos sobre todas las coordenadas de la imagen (u,v)
			for(int v = 0; v < N; v++) {
				for(int u = 0; u < M; u++) {
					if ((v <= 10) | (N - v <= 10)){			//Nos ubicamos en primeras 10 filas o las ultimas 10 filas
						ip.putPixelValue(u, v, 255);		
					}
					else if ((u <= 10) | (M - u <= 10)){	//Nos ubicamos en primeras 10 columnas o las ultimas 10 columnas
						ip.putPixelValue(u, v, 255);
					}
					else {									
						int p  = ip.getPixel(u, v);
						ip.putPixelValue(u, v, p);
					}
				}
			}
			
			im.show(); // Muestra la imagen original
			im2.show(); //Muestra la imagen con el borde blanco
			
			//Guardamos la imagen
			IJ.save(im2, "C:\\Users\\Benicio Grossling\\OneDrive\\Escritorio\\grises-Brode-Blanco.jpg");
		}

}
