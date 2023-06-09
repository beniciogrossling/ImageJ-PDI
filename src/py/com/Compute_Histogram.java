package py.com;

import java.util.Arrays;

import ij.ImagePlus;
import ij.plugin.filter.PlugInFilter;
import ij.process.ByteProcessor;
import ij.process.ImageProcessor;

public class Compute_Histogram implements PlugInFilter{
	
	ImagePlus imp;
	
	public int setup(String arg, ImagePlus imp) {
		this.imp = imp;
		return DOES_8G + NO_CHANGES;
	}

	public void run(ImageProcessor ip) {
		int[] h = new int[256];
		
		// Obtenemos la dimension de la imagen
		int M = ip.getWidth();
		int N = ip.getHeight();
		
		// Obtenemos la frecuencia de cada pixel
		for(int v = 0; v < N; v++){
			for(int u = 0; u < M; u++){
				int i = ip.getPixel(u, v);
				h[i] = h[i] + 1;	// frecuencia acumulada
			}
		}
		
		System.out.println(Arrays.toString(h));

		// creamos la imagen del histograma:
		ImageProcessor hip = new ByteProcessor(256, 100);
		hip.setValue(255); // white = 255
		hip.fill();

				
		//Obtenemos la frecuencia mas alta del vector h
		int ma = maximo(h);
		
		// dibujamos los valores del histograma como barras negras en hip
		for(int x = 0; x < 256; x++){
			int esc = (h[x]*100)/ma;		//normalizamos
			for(int y = 0; y<=esc; y++){
				hip.putPixel(x, 100-y, 0);
			}
		}
		
		// colocamos el titulo al histograma:
		String imTitle = imp.getShortTitle();
		String histTitle = "Histogra de " + imTitle;

		// desplegamos el histograma:
		ImagePlus him = new ImagePlus(histTitle, hip);
		him.show();
	}
	
	private int maximo(int[] H){
		int maxi = H[0];
		int ta = H.length; //Tama�o del vector o arreglo unidimensional
		for(int t = 0; t < ta; t++){  //Recorremos el vector
			if(H[t]>maxi){
				maxi = H[t];
			}
		}
		return maxi;
	}
}
