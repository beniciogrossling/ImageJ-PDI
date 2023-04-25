package py.com;

import java.util.Arrays;

import ij.ImagePlus;
import ij.plugin.filter.PlugInFilter;
import ij.process.ByteProcessor;
import ij.process.ImageProcessor;

public class Histograma_Acumulativo implements PlugInFilter {
	
	ImagePlus im;
	
	public int setup(String arg, ImagePlus im){
		this.im = im;
		return DOES_8G + NO_CHANGES;
	}

	// Obtenemos el histograma de ip
	public void run(ImageProcessor ip) {
		int[] h = new int[256];
		int[] hist = ip.getHistogram();
		int k = hist.length;
	
		// Histograma acumulativo
		for(int i = 0; i < 256; i++){
			if(i == 0){
				h[i] = hist[0];
			}
			else {
				h[i] = h[i-1] + hist[i];
			}
			
		}
		
		System.out.println(Arrays.toString(h));
		
		// Creamos la imagen del histograma
		ImageProcessor hip = new ByteProcessor (k, 100);
		hip.setValue(255);
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
		String imTitle = im.getShortTitle();
		String histTitle = "Histogra de " + imTitle;

		// desplegamos el histograma:
		ImagePlus him = new ImagePlus(histTitle, hip);
		him.show();
		
	}
	private int maximo(int[] H){
		int maxi = H[0];
		int ta = H.length; //Tamaño del vector o arreglo unidimensional
		for(int t = 0; t < ta; t++){  //Recorremos el vector
			if(H[t]>maxi){
				maxi = H[t];
			}
		}
		return maxi;
	}	


}