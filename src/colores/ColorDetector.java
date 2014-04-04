package colores;

import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class ColorDetector {

	private static final int TAMANO_BLOQUE = 20;
	
	public String detectColors(String foto) throws Exception {
					
		System.out.println("Processing: "+foto);
		
		int c;
		int red=0;
		int green=0;
		int blue=0;
		
		//Crea las matrices para trabajar
		ArrayList<Bloque> bloques = new ArrayList<Bloque>();
		String[][] matrizColorNet = new String[TAMANO_BLOQUE][TAMANO_BLOQUE];
		
		try {

			//Ontiene la imagen a calcular
			BufferedImage image;
			if (foto.contains("http://")||foto.contains("https://")){
				URL url = new URL(foto);
				image = ImageIO.read(url);
			}
			else {
				File file = new File(foto);
				image = ImageIO.read(file);
			}
			
			//Calcula los anchos y altos de los bloques
			int ancho = image.getWidth();
			int alto = image.getHeight();
			int anchoBloque = ancho/TAMANO_BLOQUE;
			int altoBloque = alto/TAMANO_BLOQUE;
			
			//Llena la lista de bloques
			Bloque bloque;
			for (int row=0;row<=(TAMANO_BLOQUE-1);row++){
				for (int col=0;col<=(TAMANO_BLOQUE-1);col++){
					bloque = new Bloque(row, col);
					bloques.add(bloque);
				}
			}
			
			//Calcula el promedio de color de cada bloque
			for (Bloque blo : bloques) {
				//Resetea los colores
				red=0;
				green=0;
				blue=0;				
				//obtiene el color de cada pixel y acumula las cantidades
				for (int x=(anchoBloque*blo.row); x<((anchoBloque*blo.row)+anchoBloque);x++){
					for (int y=(altoBloque*blo.col); y<((altoBloque*blo.col)+altoBloque);y++){
						c = image.getRGB(x,y);
						red = red + ((c & 0x00ff0000) >> 16);
						green = green + ((c & 0x0000ff00) >> 8);
						blue = blue +(c & 0x000000ff);
					}
				}
				//guarda el promedio de colores
				int superficieBloque = anchoBloque*altoBloque;
				blo.red=red/superficieBloque;
				blo.green=green/superficieBloque;
				blo.blue=blue/superficieBloque;
								
				//Guarda la informacion en las matrices
				matrizColorNet[blo.col][blo.row] = blo.red+","+blo.green+","+blo.blue;
			}
													
		    String colores1="";
		    int cant=0;
		    int cant2=0;
		    
		    //final int BORDE = 3;
		    
			for (int i=0;i<TAMANO_BLOQUE;i++){
				for (int j=0;j<TAMANO_BLOQUE;j++){
					
					//solo obtiene los cuadraditos del borde
					//if (i<BORDE || i>(TAMANO_BLOQUE-1-BORDE) || j<BORDE || j>TAMANO_BLOQUE-1-BORDE){
						colores1 = colores1 + matrizColorNet[j][i]+",";
						cant++;
					//}
					cant2++;
					
				}
			}
		
			System.out.println("Cant output: "+cant);
			System.out.println("Cant2 output: "+cant2);
			return colores1;
			
		} catch (Exception e){
			throw e;
		}
	}

	
}
