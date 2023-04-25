package py.com;

import ij.IJ;
import ij.ImagePlus;
import ij.process.ImageConverter;
import ij.process.ImageProcessor;
import inra.ijpb.morphology.Morphology;
import inra.ijpb.morphology.Strel;
import inra.ijpb.morphology.strel.DiskStrel;


public class TestOperacionesMorfologicasMatematicas {

	public static void main(String[] args){
		String ruta = "C:\\Users\\Benicio Grossling\\Documents\\Proyectos\\Proyectos java\\ImageJ-PDI\\imagenes\\1.jpg";
		ImagePlus im = IJ.openImage(ruta);	// cargamos la imagen
		im.show();
		ImagePlus im2 = im.duplicate();
		ImageConverter ic = new ImageConverter(im2);
		ic.convertToGray8();
		ImageProcessor ip = im2.getProcessor();
		
		
		//Parametros
		int r = 1;
		Strel B = DiskStrel.fromRadius(r);
		
		//Erosion
		ImageProcessor erosion = Morphology.erosion(ip, B);
		ImagePlus eroImage = new ImagePlus("Erosion", erosion);
		eroImage.show();
		
		//Dilatacion
		ImageProcessor dilatacion = Morphology.dilation(ip, B);
		ImagePlus dilImage = new ImagePlus("Dilatacion", dilatacion);
		dilImage.show();
		
		//Apertura
		ImageProcessor apertura = Morphology.dilation(erosion, B);
		ImagePlus aperturaImagen = new ImagePlus("Apertura", apertura);
		aperturaImagen.show();
		
		//Cierre
		ImageProcessor cierre = Morphology.erosion(dilatacion, B);
		ImagePlus cierreImagen = new ImagePlus("Cierre", cierre);
		cierreImagen.show();
		
		//Top-Hat
		ImageProcessor topHat = Diferencia(ip, apertura);
		ImagePlus thImage = new ImagePlus("Top-Hat", topHat);
		thImage.show();
		
		//Botton-Hat
		ImageProcessor bottonHat = Diferencia(cierre, ip);
		ImagePlus bhImage = new ImagePlus("Botton-Hat", bottonHat);
		bhImage.show();
			
	}
	private static ImageProcessor Diferencia(ImageProcessor ip1, ImageProcessor ip2){
		ImageProcessor ip_dif = ip1.createProcessor(ip1.getWidth(), ip1.getHeight());
		for (int i = 0; i < ip1.getWidth(); i++){
			for (int j = 0; j < ip1.getHeight(); j++){
				int dif = ip1.getPixel(i, j) - ip2.getPixel(i, j);
				if (dif <0){
					dif = 0;
				}
				ip_dif.putPixel(i, j, dif);
			}
		}
		return ip_dif;
	}
}
