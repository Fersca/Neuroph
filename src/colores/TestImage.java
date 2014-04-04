package colores;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;

public class TestImage {

	public static void main(String[] args) throws Exception {

		TestImage ti = new TestImage();
		//ti.processOutput(ti.checkImage("http://mla-s2-p.mlstatic.com/taza-jarrito-de-cafe-porcelna-tsuji-modelo-1600-4225-MLA2892249164_072012-O.jpg"));
		
		ti.checkImagesFromWord("ipod");
		
		System.out.println("Fin");
		
	}

	//Carga la red (ya previamente entrenada)
	@SuppressWarnings("rawtypes")
	NeuralNetwork loadedMlPerceptron = NeuralNetwork.load("fondoBlanco.nnet");

	private void checkImagesFromWord(String word) throws Exception{

		//obtiene las urls de las fotos
		ArrayList<String> fotos = getURLsFromSite(word);

		//abre el archivo para lindas
	    Writer fotosFile = new BufferedWriter(new FileWriter(new File("fotos.html")));

		//abre el archivo para lindas
	    //Writer lindas = new BufferedWriter(new FileWriter(new File("lindas.html")));

		//abre el archivo para feas
	    //Writer feas= new BufferedWriter(new FileWriter(new File("feas.html")));
	    
		//abre el archivo para indeterminadas
	    //Writer indeterminadas= new BufferedWriter(new FileWriter(new File("intermedias.html")));

	    //iniciar(lindas);
	    //iniciar(feas);
	    //iniciar(indeterminadas);
		iniciar(fotosFile);
	    
		ArrayList<String> lindas = new ArrayList<>();
		ArrayList<String> intermedias = new ArrayList<>();
		ArrayList<String> feas = new ArrayList<>();
		
		//verifica cada foto
		for (String foto : fotos) {
			
			//verifica la foto
			double[] result = checkImage(foto);
	
			//genera la frase a imprimir
			//String resultText = result[0]+" - " + foto+"\n";
			String resultText = "<img src=\""+foto+"\" alt=\""+result[0]+"\">\n";
			
			//si es mas de 80% linda
			if (result[0]>0.8)
				lindas.add(resultText);
			else if (result[0]<0.3) //si es mas de 80% fea
				feas.add(resultText);
			else //si no es ni una ni la otra
				intermedias.add(resultText);
			
		}

		//cierro los archivos
		//cerrar(lindas);
		//cerrar(feas);
		//cerrar(indeterminadas);
		
		
		procesaContenido(fotosFile, lindas, intermedias, feas);
		
		cerrar(fotosFile);
				
	}

	private void procesaContenido(Writer fotosFile, ArrayList<String> lindas,
			ArrayList<String> intermedias, ArrayList<String> feas) throws Exception {


		fotosFile.write("<br><br>LINDAS<hr><br><br>");
		
		for (String string : lindas) {
			fotosFile.write(string);	
		}
		
		fotosFile.write("<br><br>INTERMEDIAS<hr><br><br>");
		
		for (String string : intermedias) {
			fotosFile.write(string);	
		}
		
		fotosFile.write("<br><br>FEAS<hr><br><br>");
		
		for (String string : feas) {
			fotosFile.write(string);	
		}
		
	}

	private void cerrar(Writer archivo) throws Exception {
		archivo.write("</body>");
		archivo.write("</html>");
		archivo.close();
	}

	private void iniciar(Writer archivo) throws Exception {
		archivo.write("<html>");
		archivo.write("<body>");
	}

	private ArrayList<String> getURLsFromSite(String word) throws Exception {
	
		//Genera el arraylist
		ArrayList<String> fotos = new ArrayList<String>();
		
		//get URL content
		URL url = new URL("https://api.mercadolibre.com/sites/MLA/search?q="+word);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestProperty("Accept", "application/json");

		// open the stream and put it into BufferedReader
		BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
 
		String inputLine;
  
		StringBuilder contenido= new StringBuilder();
		
		//genera todo el contenido junto
		while ((inputLine = br.readLine()) != null) {			
			contenido.append(inputLine);
		}
 			
		//separa los campos por coma
		String[] oraciones = contenido.toString().split(",");
		
		for (String oracion : oraciones) {
			//se queda con los que tienen thumbnail 
			if (oracion.contains("thumbnail")){
				//obtiene la url de la imagen
				String[] separacion = oracion.split("\":\"");
				fotos.add((separacion[1].substring(0, separacion[1].length()-1)));
			}
		}
		
		return fotos;
	}

	private synchronized double[] checkImage(String foto) throws Exception {

		//detecta los colores de la foto
		ColorDetector p1 = new ColorDetector();
		String colorAuto = p1.detectColors(foto);
		
		//Crea el file con el resultado de los colores de la foto
	    File file1 = new File("foto-pibot.txt");
	    Writer output = new BufferedWriter(new FileWriter(file1));
		output.write(colorAuto+"0,0");
		output.close();	    
		
		//Crea un trainig set en base a este archivo
		DataSet trainingSet = DataSet.createFromFile("foto-pibot.txt", 612, 2, ",");
				
		double[] networkOutput=null;
		
		//Por cada uno de los trainig sets, calcula el resultado
        for(DataSetRow testSetRow : trainingSet.getRows()) {
        	
        	//setea como input el set que estamos procesando
        	loadedMlPerceptron.setInput(testSetRow.getInput());
        	
        	//calcula el resultado de la red
        	loadedMlPerceptron.calculate();
        	
        	//obtiene el output de la red
            networkOutput = loadedMlPerceptron.getOutput();	

            //procesa el output
            //processOutput(testSetRow, networkOutput);
            
        }
        
        //devuelve el resultado
        return networkOutput;
        
	}

	private void processOutput(double[] networkOutput) {
		//System.out.println("Input: " + Arrays.toString( testSetRow.getInput() ) );
		System.out.println(" Output: " + Arrays.toString( networkOutput) );
		
		if (networkOutput[0]>networkOutput[1])
			System.out.println("Linda");
		else
			System.out.println("Fea");		
	}

}
