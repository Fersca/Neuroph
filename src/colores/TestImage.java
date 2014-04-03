package colores;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.Arrays;

import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;

public class TestImage {

	public static void main(String[] args) throws Exception {

		//Obtiene la foto
		String foto = "http://mla-s1-p.mlstatic.com/iphone-5s-apple-16gb-retina-tactil-3g-liberado-chip-a7-ios-7-12909-MLA20069183603_032014-O.jpg";
		
		//detecta los colores de la foto
		ColorDetector p1 = new ColorDetector();
		String colorAuto = p1.detectColors(foto);
		
		//Crea el file con el resultado de los colores de la foto
	    File file1 = new File("foto-pibot.txt");
	    Writer output = new BufferedWriter(new FileWriter(file1));
		output.write(colorAuto+"0,0");
		output.close();	    
		
		//Crea un trainig set en base a este archivo
		DataSet trainingSet = DataSet.createFromFile("foto-pibot.txt", 675, 2, ",");
		
		//Carga la red (ya previamente entrenada)
		NeuralNetwork loadedMlPerceptron = NeuralNetwork.load("fondoBlanco.nnet");
		
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
		
		if (networkOutput[0]>networkOutput[1])
			System.out.println("Linda");
		else
			System.out.println("Fea");
		
		/*
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
		*/
	}

}
