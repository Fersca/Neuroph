package colores;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class DetectWhiteBackground {


	public static void main(String[] args) throws Exception {

		DetectWhiteBackground ti = new DetectWhiteBackground();
				
		ArrayList<String> fotos = new ArrayList<String>();
		
		fotos.addAll(ti.getURLsFromSite("ipod"));
		fotos.addAll(ti.getURLsFromSite("ipad"));
		fotos.addAll(ti.getURLsFromSite("iphone"));
		fotos.addAll(ti.getURLsFromSite("macbook"));
		fotos.addAll(ti.getURLsFromSite("galaxy"));
				
		ti.checkImagesFromWord(fotos);
		
		System.out.println("Fin");
		
	}

	private void checkImagesFromWord(ArrayList<String> fotos) throws Exception{

		//abre el archivo para lindas
	    Writer fotosFile = new BufferedWriter(new FileWriter(new File("fotos.html")));

		iniciar(fotosFile);
	    
		ArrayList<String> lindas = new ArrayList<>();
		ArrayList<String> intermedias = new ArrayList<>();
		ArrayList<String> feas = new ArrayList<>();
		
		//verifica cada foto
		for (String foto : fotos) {
			
			//verifica la foto
			int result = checkImage(foto);
	
			//genera la frase a imprimir
			String resultText = "<img src=\""+foto+"\" alt=\""+result+"\">\n";
			
			//si es mas de 80% linda
			if (result==1)
				lindas.add(resultText);
			else if (result==3) //si es mas de 80% fea
				feas.add(resultText);
			else //si no es ni una ni la otra
				intermedias.add(resultText);
			
		}

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
	
		//mensaje
		System.out.println("Obtiene fotos de: "+word);
		
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

	private int checkImage(String foto) throws Exception {

		if (foto==null || "".equals(foto.trim()))
			return 3;
		
		//detecta los colores de la foto
		ColorDetector c = new ColorDetector();
		String result = c.detectColors(foto);
		
		//cuenta la cantidad
		String[] numeros = result.split(",");
		
		double total = numeros.length;
		double claras=0L;
		double oscuras=0L;
		//double indetermiandas=0L;
		
		for (String num : numeros) {
			if (Integer.parseInt(num)>250)
				claras++;
			else if (Integer.parseInt(num)<200)
				oscuras++;
		}
		
		if ((claras/total)>0.7)
			return 1;
		else if ((oscuras/total)>0.4)
			return 3;
		else 
			return 2;
		
        
	}

}
