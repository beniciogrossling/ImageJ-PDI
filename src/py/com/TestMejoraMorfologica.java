package py.com;

import java.util.ArrayList;

import ij.IJ;
import ij.ImagePlus;
import ij.process.ImageConverter;
import ij.process.ImageProcessor;
import inra.ijpb.morphology.Morphology;
import inra.ijpb.morphology.Reconstruction;
import inra.ijpb.morphology.Strel;
import inra.ijpb.morphology.strel.DiskStrel;

public class TestMejoraMorfologica {

	public static void main(String[] args) {
		String ruta = "C:\\Users\\Benicio Grossling\\Documents\\Proyectos\\Proyectos java\\ImageJ-PDI\\imagenes\\1.jpg";
		ImagePlus im = IJ.openImage(ruta); // cargamos la imagen
		im.show();
		ImagePlus im2 = im.duplicate();
		ImageConverter ic = new ImageConverter(im2);
		ic.convertToGray8();
		ImageProcessor ip = im2.getProcessor();

		// Parametros
		int n = 7; // iteraciones

		// Primer TH y BH

		ImageProcessor rth;
		ImageProcessor rbh;
		
		ImageProcessor aReconst = null;
		ImageProcessor cReconst = null;

		
		ArrayList<ImageProcessor> matrizSW = new ArrayList<>();
		ArrayList<ImageProcessor> matrizSB = new ArrayList<>();

		for (int i = 1; i <= n; i++) {
			int r = i;
			Strel B = DiskStrel.fromRadius(r);

			// Erosion
			ImageProcessor eRosion = Morphology.erosion(ip, B);

			// Apertura por reconstrucción
			aReconst = Reconstruction.reconstructByDilation(eRosion, ip, 8);

			// Transformada de Top-Hat por Reconstrucción
			rth = restaImagenes(ip, aReconst);
	
			// Dilatacion
			ImageProcessor dIlatacion = Morphology.dilation(ip, B);

			// Cierre por reconstruccion
			cReconst = Reconstruction.reconstructByErosion(dIlatacion, ip, 8);

			// Transformada de Bottom-Hat por Reconstrucción
			rbh = restaImagenes(cReconst, ip);

			matrizSW.add(rth);
			matrizSB.add(rbh);
		}


		ImageProcessor SW = matrizSW.get(0);
		ImageProcessor SB = matrizSB.get(0);

		for (int x = 1; x < n; x++) {
			SW = SumaImagenes(SW, matrizSW.get(x));
			SB = SumaImagenes(SB, matrizSB.get(x));
		}
		
		
		ImageProcessor A;
		
		A = semisuma(aReconst, cReconst);
		ImagePlus AM = new ImagePlus("Imagen base", A);
		AM.show();
		
		
		ImageProcessor iE = MMALCER(A, SW, SB);
		ImagePlus IE = new ImagePlus("Imagen Mejorada MMALCER", iE);
		IE.show();
	}

	private static ImageProcessor MMALCER(ImageProcessor ip, ImageProcessor sW, ImageProcessor sB) {
		int M = ip.getWidth();
		int N = ip.getHeight();

		ImageProcessor mmalce = ip.createProcessor(M, N);
		for (int i = 0; i < M; i++) {
			for (int j = 0; j < N; j++) {
				int s = (int) (ip.getPixel(i, j) + 0.5 * sW.getPixel(i, j) - 0.5 * sB.getPixel(i, j));
				if (s > 255) {
					s = 255;
				}
				mmalce.putPixel(i, j, s);
			}
		}
		return mmalce;
	}

	public static ImageProcessor SumaImagenes(ImageProcessor ip1, ImageProcessor ip2) {
		int M = ip1.getWidth();
		int N = ip1.getHeight();

		ImageProcessor suma = ip1.createProcessor(M, N);
		for (int i = 0; i < M; i++) {
			for (int j = 0; j < N; j++) {
				int s = ip1.getPixel(i, j) + ip2.getPixel(i, j);
				if (s > 255) {
					s = 255;
				}
				suma.putPixel(i, j, s);
			}
		}
		return suma;
	}

	private static ImageProcessor restaImagenes(ImageProcessor img1, ImageProcessor img2) {
		int M = img1.getWidth();
		int N = img1.getHeight();

		// Defino la imagen a retornar
		ImageProcessor imgR = img1.createProcessor(M, N);
		for (int i = 0; i < M; i++) {
			for (int j = 0; j < N; j++) {
				int dif = img1.getPixel(i, j) - img2.getPixel(i, j);
				if (dif < 0) {
					dif = 0;
				}
				imgR.putPixel(i, j, dif);
			}
		}
		return imgR;
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

}
