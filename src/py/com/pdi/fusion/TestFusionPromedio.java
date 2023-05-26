package py.com.pdi.fusion;

import ij.IJ;
import ij.ImagePlus;
import ij.process.ImageProcessor;
import inra.ijpb.morphology.Morphology;
import inra.ijpb.morphology.Strel;
import inra.ijpb.morphology.strel.DiskStrel;

public class TestFusionPromedio {

	public static void main(String[] args) {
		
		String ruta1 = "C:\\Users\\Benicio Grossling\\Documents\\Proyectos\\Proyectos java\\ImageJ-PDI\\imagenes\\Camp_IR.png";
		String ruta2 = "C:\\Users\\Benicio Grossling\\Documents\\Proyectos\\Proyectos java\\ImageJ-PDI\\imagenes\\Camp_Vis.png";
	
		ImagePlus IR = IJ.openImage(ruta1); // carga la imagen infrarroja
		ImagePlus VIS = IJ.openImage(ruta2); // carga la imagen visible
		
		IR.show();
		VIS.show();
		
		//Fusion Promedio
		//IB = (IR + VIS)/2
		
		ImageProcessor semisuma = semisuma(IR.getProcessor(), VIS.getProcessor());
		ImagePlus IB = new ImagePlus("Imagen Fusionada", semisuma);
		IB.show();

		
		//Fusion TOP-HAT
		int r = 2;
		Strel B = DiskStrel.fromRadius(r);
		
		ImageProcessor TH_VIS = Morphology.whiteTopHat(VIS.getProcessor(), B);
		ImageProcessor TH_IR = Morphology.whiteTopHat(IR.getProcessor(), B);
		
		ImageProcessor BH_VIS = Morphology.blackTopHat(VIS.getProcessor(), B);
		ImageProcessor BH_IR = Morphology.blackTopHat(IR.getProcessor(), B);
		
		ImageProcessor max_TH = Maximo(TH_IR, TH_VIS);
		ImagePlus SUM = new ImagePlus("Imagen maximo de TH_IR_VIS", max_TH);
		
		ImageProcessor max_BH = Maximo(BH_IR, BH_VIS);
		ImagePlus DIF = new ImagePlus("Imagen maximo de BH_IR_VIS", max_BH);
				
		ImageProcessor im_1 = suma(IB.getProcessor(), SUM.getProcessor());
		ImagePlus IM_1 = new ImagePlus("Suma de la imagen base y maximo de TH_IR_VIS", im_1);
		
		ImageProcessor fusion = Diferencia(IM_1.getProcessor(), DIF.getProcessor());
		ImagePlus FUSION = new ImagePlus("Imagen Fusionada TH y BH", fusion);
		FUSION.show();
		
	}
	private static ImageProcessor Maximo(ImageProcessor im1, ImageProcessor im2) {
		int M = im1.getWidth();
		int N = im1.getHeight();
		
		// Defino la imagen a retornar
		ImageProcessor pix_max = im1.createProcessor(M, N);

		for (int i = 0; i < M; i++) {
			for (int j = 0; j < N; j++) {
				int v = Math.max(im1.getPixel(i, j), im2.getPixel(i, j));

				pix_max.putPixel(i, j, v);
			}
		}
		return pix_max;
	}
	
	private static ImageProcessor semisuma(ImageProcessor img1, ImageProcessor img2) {
		int M = img1.getWidth();
		int N = img1.getHeight();

		// Defino la imagen a retornar
		ImageProcessor semisuma = img1.createProcessor(M, N);
		for (int i = 0; i < M; i++) {
			for (int j = 0; j < N; j++) {
				int sum = (img1.getPixel(i, j) + img2.getPixel(i, j))/2;
				semisuma.putPixel(i, j, sum);
			}
		}
		return semisuma;
	}
	
	private static ImageProcessor suma(ImageProcessor iB, ImageProcessor img2) {
		int M = iB.getWidth();
		int N = iB.getHeight();

		// Defino la imagen a retornar
		ImageProcessor suma = iB.createProcessor(M, N);
		for (int i = 0; i < M; i++) {
			for (int j = 0; j < N; j++) {
				int sum = (iB.getPixel(i, j) + img2.getPixel(i, j));
				suma.putPixel(i, j, sum);
			}
		}
		return suma;
	}

	private static ImageProcessor Diferencia(ImageProcessor ip1, ImageProcessor ip2){
		ImageProcessor ip_dif = ip1.createProcessor(ip1.getWidth(), ip1.getHeight());
		for (int i = 0; i < ip1.getWidth(); i++){
			for (int j = 0; j < ip1.getHeight(); j++){
				int dif = ip1.getPixel(i, j) - ip2.getPixel(i, j);
				if (dif < 0){
					dif = 0;
				}
				ip_dif.putPixel(i, j, dif);
			}
		}
		return ip_dif;
	}
	
}
