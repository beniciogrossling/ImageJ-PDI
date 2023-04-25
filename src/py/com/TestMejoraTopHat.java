package py.com;

import ij.IJ;
import ij.ImagePlus;
import ij.process.ImageConverter;
import ij.process.ImageProcessor;
import inra.ijpb.morphology.Morphology;
import inra.ijpb.morphology.Strel;
import inra.ijpb.morphology.strel.DiskStrel;

public class TestMejoraTopHat {

	public static void main(String[] args){
		String ruta = "C:\\Users\\Benicio Grossling\\Documents\\Proyectos\\Proyectos java\\ImageJ-PDI\\imagenes\\1.jpg";
		ImagePlus im = IJ.openImage(ruta);	// cargamos la imagen
		im.show();
		ImagePlus im2 = im.duplicate();
		ImageConverter ic = new ImageConverter(im2);
		ic.convertToGray8();
		ImageProcessor ip = im2.getProcessor();
		
		
		//Parametros
		int r = 7;	//iteraciones
		Strel B = DiskStrel.fromRadius(r);
	
		// TH
		ImageProcessor th = Morphology.whiteTopHat(ip, B);

		// BH
		ImageProcessor bh = Morphology.blackTopHat(th, B);
	
		// Mejora de contraste (IE = I + th - bh)
		ImageProcessor IE = imageEnhancement(ip,th,bh);
		ImagePlus newImage = new ImagePlus("Imagen Mejorada", IE);
		newImage.show();
	
	
	}

	private static ImageProcessor imageEnhancement(ImageProcessor I, ImageProcessor th, ImageProcessor bh) {
		
		int M = I.getWidth();
		int N = I.getHeight();
		
		ImageProcessor iE = I.createProcessor(M,N);
		for (int i = 0; i < M; i++){
			for (int j = 0; j < N; j++){
				int pix = (int)(I.getPixel(i, j) + th.getPixel(i, j) - bh.getPixel(i,j));
				if (pix > 255){
					pix = 255;
				}
				if (pix < 0){
					pix = 0;
				}
				iE.putPixel(i, j, pix);
			}
		}
		return iE;
	}	
}
