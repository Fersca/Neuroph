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
				
		//mochila
		fotos.add("http://mla-s2-p.mlstatic.com/mochila-hoth-dc-envio-gratis-a-todo-el-pais-6179-MLA4633110882_072013-O.jpg");
		
		//mochila roja
		fotos.add("http://mla-s2-p.mlstatic.com/quiksilver-mochila-1969-s-envio-gratis-a-todo-el-pais-6937-MLA5139065793_102013-O.jpg");
				
		//zapato rojo mujer
		fotos.add("http://mla-s2-p.mlstatic.com/plataforma-zapato-mujer-ven-a-mi-outlet-ltimos-pares-12659-MLA20062872374_032014-O.jpg");
		
		//notebook bangho
		fotos.add("http://mla-s1-p.mlstatic.com/notebook-bangho-a1-amd-dual-core-8gb-750gb-ati-led-156-hdmi-13155-MLA20072906519_042014-O.jpg");
		
		//kindl
		fotos.add("http://mla-s1-p.mlstatic.com/lampara-de-luz-led-ebook-con-clip-pkindle-sony-ereader-10443-MLA20029834359_012014-O.jpg");
		
		//lampara led
		fotos.add("http://mla-s2-p.mlstatic.com/lampara-led-7w-rosca-comun-85-de-ahorro-de-energia-40000-hs-11263-MLA20040894428_012014-O.jpg");
			
		//cartuchos lindos
		fotos.add("http://mla-s2-p.mlstatic.com/sistema-continuo-imprek-para-xp201-xp401-xp211-xp411-8436-MLA20004353060_112013-O.jpg");
		
		//botella linda
		fotos.add("http://mla-s1-p.mlstatic.com/tinta-recargas-hp-lexmark-100-cc-perfect-print-fotografica-4080-MLA101654612_1426-O.jpg");
		
		//taza blanca
		fotos.add("http://mla-s1-p.mlstatic.com/taza-de-desayuno-x6u-c-plato-porcelana-para-gastronomia-13172-MLA20072734629_042014-O.jpg");
		
		//cafetera blanca
		fotos.add("http://mla-s2-p.mlstatic.com/cafetera-de-filtro-automatica-sikla-dk-20-3l-20-tazas-13195-MLA20072713705_042014-O.jpg");
		
		//tazza de hello kitty
		fotos.add("http://mla-s2-p.mlstatic.com/taza-hello-kitty-glitter-12993-MLA6051482686_032014-O.jpg");
		
		//carrito linto
		fotos.add("http://img1.mlstatic.com/bb-feliz-coche-cuna-duck-frontier-vip-manija-revatible25kg_MLA-O-2982146576_082012.jpg");
					
		return fotos;
	}

	/** Devuelve el listado de fotos feas*/
	private ArrayList<String> obtieneFotosFeas() {

		ArrayList<String> fotos = new ArrayList<>();
		//lampara linda
		fotos.add("http://mla-s2-p.mlstatic.com/lampara-colgantede-techoiluminacionpantalla-de-50cm-4097-MLA113136360_3120-O.jpg");
				
		//fondo amarillo fluo
		fotos.add("http://mla-s1-p.mlstatic.com/lampara-de-pie-moderna-varias-combinaciones-de-colores-5357-MLA4352787969_052013-O.jpg");

		//fondo super oscuro
		fotos.add("http://mla-s2-p.mlstatic.com/iphone-4s-16-gb-12749-MLA20065710022_032014-O.jpg");
		
		//fondo rosa feo
		fotos.add("http://mla-s2-p.mlstatic.com/lampara-de-pie-de-bronce-7898-MLA5290125474_102013-O.jpg");
		
		//iphone fondo oscuro
		fotos.add("http://mla-s1-p.mlstatic.com/iphone-4s-16gb-3g-movistar-12201-MLA20055760454_022014-O.jpg");
		
		//pacarta gris oscura
		fotos.add("http://mla-s2-p.mlstatic.com/todo-oscuro-sin-estrellas-stephen-king-tapas-duras-4517-MLA3726917997_012013-O.jpg");
		
		//taza con fondo oscuro
		fotos.add("http://mla-s1-p.mlstatic.com/monsters-high-tazas-nuevos-disenos-877-MLA4721145651_072013-O.jpg");
		
		//chapas verdes y marrones
		fotos.add("http://mla-s2-p.mlstatic.com/monsters-high-tazas-nuevos-disenos-859-MLA4721167472_072013-O.jpg");
		
		//placard negro
		fotos.add("http://mla-s2-p.mlstatic.com/porton-garage-pavir-negro-240x200-libre-de-mantenimiento-12719-MLA20065549506_032014-F.jpg");

		//iphone con fondo negro
		fotos.add("http://mla-s1-p.mlstatic.com/iphone-4s-16gb-negro-libre-para-movistar-en-perfecto-estado-12704-MLA20065483722_032014-O.jpg");
		
		//remera negra con fondo gris oscuro
		fotos.add("http://mla-s1-p.mlstatic.com/chombas-abercrombie-originales-importadas-4905-MLA4000356349_032013-O.jpg");
		
		//go pro fea
		fotos.add("http://mla-s2-p.mlstatic.com/gopro-hero-3-black-edition-surf-12032-MLA20053529564_022014-O.jpg");
		
		//bloque negro
		fotos.add("http://mla-s1-p.mlstatic.com/porcelanato-negro-o-blanco-60x60-pulido-y-rectificado-1-cal-12776-MLA20066029531_032014-O.jpg");
		
		//copas de chanpagne con fondo negro
		fotos.add("http://mla-s1-p.mlstatic.com/frapera-cristal-o-negra-plastica-de-excelente-calidad-5238-MLA4950637301_092013-O.jpg");
		
		return fotos;
	}

}
