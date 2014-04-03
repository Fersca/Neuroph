package colores;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.ArrayList;

/**
 * Crea el trainigSet para la red 
 * @author fscasserra
 */
public class GenerateTrainigSet {

	/** Inicia el programa 
	 * @throws Exception */
	public static void main(String[] args) throws Exception {
		GenerateTrainigSet gts = new GenerateTrainigSet();
		gts.run();	
	}

	/** Genera el training set en base a varias URLs de imagenes 
	 * @throws Exception */
	private void run() throws Exception {

		//obtiene el listado de fotos
		ArrayList<String> fotosL = obtieneFotosLindas();
		ArrayList<String> fotosF = obtieneFotosFeas();

		//verifica si hay la misma cantidad (el algoritmo necesita eso sino pincha)
		if (fotosL.size()!=fotosF.size()){
			System.out.println("Cantidad invalida de fotos");
			return;		
		}

		//Crea el file para guardar el resultado de los colores de las fotos
	    File file1 = new File("trainingSet.txt");
	    Writer output = new BufferedWriter(new FileWriter(file1));
	    ColorDetector p1 = new ColorDetector();
	    
		//procesa cada foto
		for (int i=0;i<fotosL.size();i++) {

			//Lee los colores de la foto linda
			String result = p1.detectColors(fotosL.get(i));
			output.write(result+"1,0\n"); //Marca como linda la foto

			//Lee los colores de la foto fea
			result = p1.detectColors(fotosF.get(i));
			output.write(result+"0,1\n"); //Marca como linda la foto 

		}
		
		//Cierra el archivo
		output.close();
				
	}

	/** Devuelve el listado de fotos lindas*/
	private ArrayList<String> obtieneFotosLindas() {

		ArrayList<String> fotos = new ArrayList<>();

		//celular
		fotos.add("http://mla-s2-p.mlstatic.com/nokia-asha-311-nuevos-libres-2gb-gtia-camara-3mp-12774-MLA20064729742_032014-O.jpg");

		//iphone
		fotos.add("http://mla-s1-p.mlstatic.com/iphone-5s-apple-16gb-retina-tactil-3g-liberado-chip-a6-ios7-12713-MLA20066149108_032014-O.jpg");
		
		//samsung s2
		fotos.add("http://mla-s1-p.mlstatic.com/samsung-galaxy-note-3-n9000-libre-full-hd-wifi-octacore-andr-9310-MLA20015732067_122013-O.jpg");
		
		//mochila
		fotos.add("http://mla-s2-p.mlstatic.com/mochila-hoth-dc-envio-gratis-a-todo-el-pais-6179-MLA4633110882_072013-O.jpg");
		
		//mochila roja
		fotos.add("http://mla-s2-p.mlstatic.com/quiksilver-mochila-1969-s-envio-gratis-a-todo-el-pais-6937-MLA5139065793_102013-O.jpg");
		
		//zapato hombre
		fotos.add("http://mla-s2-p.mlstatic.com/zapatos-de-cuero-para-hombre-12034-MLA20053498006_022014-O.jpg");
		
		//zapato rojo mujer
		fotos.add("http://mla-s2-p.mlstatic.com/plataforma-zapato-mujer-ven-a-mi-outlet-ltimos-pares-12659-MLA20062872374_032014-O.jpg");
		
		//notebook bangho
		fotos.add("http://mla-s1-p.mlstatic.com/notebook-bangho-a1-amd-dual-core-8gb-750gb-ati-led-156-hdmi-13155-MLA20072906519_042014-O.jpg");
		
		//kindl
		fotos.add("http://mla-s1-p.mlstatic.com/lampara-de-luz-led-ebook-con-clip-pkindle-sony-ereader-10443-MLA20029834359_012014-O.jpg");
		
		//lampara led
		fotos.add("http://mla-s2-p.mlstatic.com/lampara-led-7w-rosca-comun-85-de-ahorro-de-energia-40000-hs-11263-MLA20040894428_012014-O.jpg");
	
		//imopresora epson linda
		fotos.add("http://mla-s1-p.mlstatic.com/impresora-epson-lx350-reemplaza-lx300-nuevo-modelo-10934-MLA20037259635_012014-O.jpg");
		
		//cartuchos lindos
		fotos.add("http://mla-s2-p.mlstatic.com/sistema-continuo-imprek-para-xp201-xp401-xp211-xp411-8436-MLA20004353060_112013-O.jpg");
		
		//botella linda
		fotos.add("http://mla-s1-p.mlstatic.com/tinta-recargas-hp-lexmark-100-cc-perfect-print-fotografica-4080-MLA101654612_1426-O.jpg");
		
			
		return fotos;
	}

	/** Devuelve el listado de fotos feas*/
	private ArrayList<String> obtieneFotosFeas() {

		ArrayList<String> fotos = new ArrayList<>();

		//Placa fea
		fotos.add("http://mla-s2-p.mlstatic.com/iphone-3gs-16gb-placa-logica-att-13252-MLA20073990501_042014-O.jpg");

		//celular con fondo rojo
		fotos.add("http://mla-s1-p.mlstatic.com/iphone-4-16-gb-impecable-12336-MLA20058947049_032014-O.jpg");

		//celular con fondo oscurisimo
		fotos.add("http://mla-s1-p.mlstatic.com/iphone-4-16gb-blanco-liberado-en-perfecto-estado-4-fundas-13187-MLA20072406941_032014-O.jpg");

		//compu con fondo rojo feo
		fotos.add("http://img2.mlstatic.com/pc-intel-dual-core-sandy-bridge-4g-500g-dvd-led-19-envio-sc_MLA-O-2937887752_072012.jpg");

		//pc de escritotio horrible
		fotos.add("http://mla-s2-p.mlstatic.com/pc-escritorio-hp-pavilion-t720m-impecable-11416-MLA20043809819_022014-O.jpg");

		//compu en el escritorio
		fotos.add("http://mla-s1-p.mlstatic.com/computadora-pc-completa-funcionando-perfecto-11731-MLA20048940217_022014-O.jpg");

		//compu con fondo de madera
		fotos.add("http://mla-s1-p.mlstatic.com/computadora-hp-d7800p-core-2-duo-233ghz-envio-gratis-10872-MLA20034708997_012014-O.jpg");

		//gabinete muy de cerca
		fotos.add("http://mla-s1-p.mlstatic.com/pc-amd-athlon-64-x2-perfecto-estado-9510-MLA20017691588_122013-O.jpg");

		//pancarta toda azul
		fotos.add("http://mla-s1-p.mlstatic.com/pc-amd-apu-fm2-a4-4000-ddr3-4gb-1600-500gb-7347-MLA5195493865_102013-O.jpg");

		//fondo amarillo fluo
		fotos.add("http://mla-s1-p.mlstatic.com/combo-actualizacion-pc-intel-i3-4130-4tagen-4gb-1600-asus-7352-MLA5204295864_102013-O.jpg");

		//fondo super oscuro
		fotos.add("http://mla-s2-p.mlstatic.com/iphone-4s-16-gb-12749-MLA20065710022_032014-O.jpg");
		
		//fondo rosa feo
		fotos.add("http://img2.mlstatic.com/impresora-epson-t50-sistema-continuo-tinta-de-sublimar-aqx_MLA-O-76911000_3812.jpg");
		
		//iphone fondo oscuro
		fotos.add("http://mla-s1-p.mlstatic.com/iphone-4s-16gb-3g-movistar-12201-MLA20055760454_022014-O.jpg");
		return fotos;
	}

}
