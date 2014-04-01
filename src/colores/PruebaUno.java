package colores;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

import javax.imageio.ImageIO;

import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;

public class PruebaUno {

	public static void main(String[] args) throws Exception {

		//Obtiene la foto
		String foto = "http://cdn.inaxiom.net/web/wp-content/uploads/2009/03/renault-sandero-pack-00.jpg";
		
		//detecta los colores de la foto
		PruebaUno p1 = new PruebaUno();
		String colorAuto = p1.detectColors(foto);
		
		//Crea el file con el resultado de los colores de la foto
	    File file1 = new File("coloresUnAuto.txt");
	    Writer output = new BufferedWriter(new FileWriter(file1));
		output.write(colorAuto+"0,0,0,0,0,0,0");
		output.close();	    

		//Crea un trainig set en base a este archivo
		DataSet trainingSet = DataSet.createFromFile("coloresUnAuto.txt", 300, 7, ",");
		
		//Carga la red (ya previamente entrenada)
		NeuralNetwork loadedMlPerceptron = NeuralNetwork.load("myMlPerceptronCentro50-plata.nnet");
		
		//Por cada uno de los trainig sets, calcula el resultado
        for(DataSetRow testSetRow : trainingSet.getRows()) {
        	
        	//setea como input el set que estamos procesando
        	loadedMlPerceptron.setInput(testSetRow.getInput());
        	
        	//calcula el resultado de la red
        	loadedMlPerceptron.calculate();
        	
        	//obtiene el output de la red
            double[] networkOutput = loadedMlPerceptron.getOutput();	

            //procesa el output
            processOutput(testSetRow, networkOutput);
            
        }
		
        
	}

	private static void processOutput(DataSetRow testSetRow, double[] networkOutput) {
		System.out.println("Input: " + Arrays.toString( testSetRow.getInput() ) );
		System.out.println(" Output: " + Arrays.toString( networkOutput) );
		
		int posMax=0;
		int posMax2=0;
		int pos=0;
		int pos2=0;
		double max=0;
		double max2=0;
		
		for (double numero : networkOutput) {
			System.out.println("cant: "+numero);
			if (numero>max){
				max=numero;
				posMax=pos;
			}
			pos++;
		}

		networkOutput[posMax]=0;
		
		for (double numero : networkOutput) {
			if (numero>max2){
				max2=numero;
				posMax2=pos2;
			}
			pos2++;
		}

		
		String color="";
		String color2="";
		if (posMax==0) color="Plata";
		if (posMax==1) color="Rojo";
		if (posMax==2) color="Gris";
		if (posMax==3) color="Negro";
		if (posMax==4) color="Blanco";
		if (posMax==5) color="Azul";
		if (posMax==6) color="Verde";
		if (posMax2==0) color2="Plata";
		if (posMax2==1) color2="Rojo";
		if (posMax2==2) color2="Gris";
		if (posMax2==3) color2="Negro";
		if (posMax2==4) color2="Blanco";
		if (posMax2==5) color2="Azul";
		if (posMax2==6) color2="Verde";
		
		System.out.println("Color: " + color+" - "+(int)(max*100)+"%" );
		System.out.println("Color: " + color2+" - "+(int)(max2*100)+"%" );
	}

	private static final int TAMANO_BLOQUE = 20;
	
	public String detectColors(String foto) throws Exception {
					
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
			for (int i=0;i<TAMANO_BLOQUE;i++){
				for (int j=0;j<TAMANO_BLOQUE;j++){
					
					if (i>=5 && i<=14 && j>=5 && j<=14 )
						colores1 = colores1 + matrizColorNet[j][i]+",";
					
				}
			}
		
			return colores1;
			
		} catch (Exception e){
			throw e;
		}
	}

}
