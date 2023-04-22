package py.com;

import java.io.File;

import ij.IJ;
import ij.ImagePlus;

public class TestMejora {

	public static void main(String[] args) {

		String sCarpAct = System.getProperty("user.dir").concat("\\imagenes");
		File carpeta = new File(sCarpAct);
		String[] listado = carpeta.list();
		if (listado == null || listado.length == 0) {
		    System.out.println("No hay elementos dentro de la carpeta actual");
		    return;
		}
		else {
		    for (int i=0; i< listado.length; i++) {
		        System.out.println(listado[i]);
		        String ruta = sCarpAct.concat("\\").concat(listado[i]);
		        System.out.println(ruta);
		        ImagePlus im = IJ.openImage(ruta);
		        im.show();
		        
		        ImagePlus im2 = im.duplicate();
		        
		        //Inicio
		        long time_star = System.currentTimeMillis();
		        
		        //Ecualizacion del histograma
		        //EcualizacionHistograma eH = new EcualizacionHistograma();
		        //ImageProcessor ip = im2.getProcessor();
		        //eH.run(ip);
		        
		        //fin
		        long time_end = System.currentTimeMillis();
		        long time = time_end - time_star;
		        
		        im2.show();
		        
		        //guardar resultados
		        String rGuardar = System.getProperty("user.dir").concat("\\resultados\\EcualizacionHistograma\\").concat(listado[i]);
		        IJ.saveAs(im2,"tif", rGuardar);
		        
		        
		    } 
		}
		
		
	}

}
