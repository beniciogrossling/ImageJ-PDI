package py.com;

import ij.ImagePlus;
import ij.process.ImageProcessor;

import java.util.Random;

import ij.IJ;



public class Imagenes_nros_aleatorios {
	
	public static void main(String[] args) {
		
		
		//Codigo para la iamgen con valores de pixeles aleatorios con la funcion Math.random()
		
		ImagePlus imp = IJ.createImage("Random_Image_Math", "8-bit white", 256, 256, 0);	// Creamos una imagen de tamaño 256x256 en blanco
		
		ImageProcessor ip = imp.getProcessor();
		
		for(int i = 0; i < 256; i++){ 		
			for(int j = 0; j < 256; j++){
				ip.putPixelValue(i, j, (int) (Math.random()*255)) ; //Creamos valores aleatorios entre 0 y 255
			}
		}
		imp.show();
		
		//Codigo para la iamgen con valores de pixeles aleatorios con la funcion Randon.nextGaussian()
		
		ImagePlus imp2 = IJ.createImage("Random_Image_Gaussian", "8-bit white", 256, 256, 0);	// Creamos una imagen de tamaño 256x256 en blanco
		
		Random r = new Random();
		ImageProcessor ip2 = imp2.getProcessor();
		int media = 128;
		int dev_st = 50;
		
		for(int i = 0; i < 256; i++){ 		
			for(int j = 0; j < 256; j++){
				ip2.putPixelValue(i, j, (int) ((r.nextGaussian()*dev_st)) + media) ; //Creamos valores gaussianos con media:128 y desviacion estandar de 50
			}
		}
		imp2.show();
		
		// Para imagen con Math		
		Compute_Histogram ch = new Compute_Histogram();
		ch.setup(null, imp);
		ch.run(ip);
		
		// Para imagen con nextGaussian	
		Compute_Histogram ch2 = new Compute_Histogram();
		ch2.setup(null, imp2);
		ch2.run(ip2);
		
		
		}
}	
