package py.com;

import java.util.ArrayList;

import ij.IJ;
import ij.ImagePlus;
import ij.process.ImageConverter;
import ij.process.ImageProcessor;
import inra.ijpb.morphology.Morphology;
import inra.ijpb.morphology.Strel;
import inra.ijpb.morphology.strel.DiskStrel;

public class TestMMALCE {
	
	public static void main(String[] args){
		String ruta = "C:\\Users\\Benicio Grossling\\Documents\\Proyectos\\Proyectos java\\ImageJ-PDI\\imagenes\\1.jpg";
		ImagePlus im = IJ.openImage(ruta);	// cargamos la imagen
		im.show();
		ImagePlus im2 = im.duplicate();
		ImageConverter ic = new ImageConverter(im2);
		ic.convertToGray8();
		ImageProcessor ip = im2.getProcessor();
		
		
		//Parametros
		int n = 7;	//iteraciones
	
		//Primer TH y BH
		
		ImageProcessor th;
		ImageProcessor bh;
		ArrayList<ImageProcessor> matrizSW = new ArrayList<>();
		ArrayList<ImageProcessor> matrizSB = new ArrayList<>();
		
		for (int i = 1; i <= n; i++){
			int r = i;
			Strel B = DiskStrel.fromRadius(r); 
			th = Morphology.whiteTopHat(ip, B);
			bh = Morphology.blackTopHat(th, B);
			matrizSW.add(th);
			matrizSB.add(bh);
		}
		
		ImageProcessor SW = matrizSW.get(0);
		ImageProcessor SB = matrizSB.get(0);
		
		for (int x = 1; x < n; x++){
			SW = SumaImagenes (SW, matrizSW.get(x));
			SB = SumaImagenes (SB, matrizSB.get(x));
		}
		ImageProcessor iE = MMALCE (ip, SW, SB);
		ImagePlus IE = new ImagePlus ("Imagen Mejorada", iE);
		IE.show();
	}
	
	
	private static ImageProcessor MMALCE(ImageProcessor ip, ImageProcessor sW, ImageProcessor sB) {
		int M = ip.getWidth();
		int N = ip.getHeight();
		
		ImageProcessor mmalce = ip.createProcessor(M,N);
		for (int i = 0; i < M; i++){
			for (int j = 0; j < N; j++){
				int s = (int)(ip.getPixel(i, j) + 0.5*sW.getPixel(i, j) - 0.5*sB.getPixel(i,j));
				if (s > 255){
					s = 255;
				}
				mmalce.putPixel(i, j, s);
			}
		}
		return mmalce;
	}

	public static ImageProcessor SumaImagenes (ImageProcessor ip1, ImageProcessor ip2){
	int M = ip1.getWidth();
	int N = ip1.getHeight();
	
	ImageProcessor suma = ip1.createProcessor(M,N);
	for (int i = 0; i < M; i++){
		for (int j = 0; j < N; j++){
			int s = ip1.getPixel(i, j) + ip2.getPixel(i, j);
			if (s > 255){
				s = 255;
			}
			suma.putPixel(i, j, s);
		}
	}
	return suma;	
	}
	
}
