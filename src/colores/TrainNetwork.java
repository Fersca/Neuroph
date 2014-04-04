package colores;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.events.LearningEvent;
import org.neuroph.core.events.LearningEventListener;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;
import org.neuroph.core.learning.LearningRule;
import org.neuroph.nnet.MultiLayerPerceptron;
import org.neuroph.nnet.learning.BackPropagation;
import org.neuroph.nnet.learning.MomentumBackpropagation;
import org.neuroph.util.TransferFunctionType;

public class TrainNetwork implements LearningEventListener {

    public static void main(String[] args) throws IOException {
        new TrainNetwork().run();
    }
    
    public void run() throws IOException {

    	//cantidad de neuronas de inicio, cantidad de fin
        DataSet trainingSet = DataSet.createFromFile("trainingSet.txt", 1200, 2, ",");
        
        //lee el archivo
    	/*
        BufferedReader reader = new BufferedReader(new FileReader("/home/fersca/colores.txt"));
    	String line = null;
    	
    	while ((line = reader.readLine()) != null) {
    		String[] numeros = line.split(",");
    		System.out.println(numeros);
    		trainingSet.addRow(new DataSetRow(new double[]{
    				Double.parseDouble(numeros[0]), 
    				Double.parseDouble(numeros[1]),
    				Double.parseDouble(numeros[2]),
    				Double.parseDouble(numeros[3]),
    				Double.parseDouble(numeros[4]),
    				Double.parseDouble(numeros[5]),
    				Double.parseDouble(numeros[6]),
    				Double.parseDouble(numeros[7]),
    				Double.parseDouble(numeros[8]),
    				Double.parseDouble(numeros[9])
    				}, new double[]{Double.parseDouble(numeros[10]),Double.parseDouble(numeros[11]),Double.parseDouble(numeros[12])})); 
    	}    	
    	*/
    	//trainingSet.normalize();
        // or you can do as below
        //trainingSet.normalize();

    	
        // create multi layer perceptron
        MultiLayerPerceptron myMlPerceptron = new MultiLayerPerceptron(TransferFunctionType.SIGMOID, 1200, 300, 50, 2);

        // enable batch if using MomentumBackpropagation
        if( myMlPerceptron.getLearningRule() instanceof MomentumBackpropagation )
        	((MomentumBackpropagation)myMlPerceptron.getLearningRule()).setBatchMode(true);

        LearningRule learningRule = myMlPerceptron.getLearningRule();
        
        System.out.println("LR:"+learningRule);
                
        learningRule.addListener(this);
        
        // learn the training set
        System.out.println("Training neural network...");
        myMlPerceptron.learn(trainingSet);

        // test perceptron
        System.out.println("Testing trained neural network");
        
        //testNeuralNetwork(myMlPerceptron, trainingSet);

        // save trained neural network
        myMlPerceptron.save("fondoBlanco.nnet");

        
        // load saved neural network
        //NeuralNetwork loadedMlPerceptron = NeuralNetwork.load("fondoBlanco.nnet");

        // test loaded neural network
        //System.out.println("Testing loaded neural network");
        System.out.println("End.");
        //testNeuralNetwork(loadedMlPerceptron, trainingSet);
        
        /*
        System.out.println("Prueba final");
        testNeuralNetworkUnico(loadedMlPerceptron,new DataSetRow(new double[]{
				0.2, 
				0.1,
				0.12,
				0.13,
				0.15,
				0.05,
				0.3,
				0.2,
				0.15,
				0.4
				}));

        testNeuralNetworkUnico(loadedMlPerceptron,new DataSetRow(new double[]{
				0.7, 
				0.8,
				0.82,
				0.73,
				0.85,
				0.85,
				0.7,
				0.8,
				0.65,
				0.9
				}));
        */
    }

    /**
     * Prints network output for the each element from the specified training set.
     * @param neuralNet neural network
     * @param trainingSet training set
     */
    public static void testNeuralNetwork(NeuralNetwork neuralNet, DataSet testSet) {

        for(DataSetRow testSetRow : testSet.getRows()) {
            neuralNet.setInput(testSetRow.getInput());
            neuralNet.calculate();
            double[] networkOutput = neuralNet.getOutput();

            System.out.print("Input: " + Arrays.toString( testSetRow.getInput() ) );
            System.out.println(" Output: " + Arrays.toString( networkOutput) );
        }
    }

    public static void testNeuralNetworkUnico(NeuralNetwork neuralNet, DataSetRow testSetRow) {

            neuralNet.setInput(testSetRow.getInput());
            neuralNet.calculate();
            double[] networkOutput = neuralNet.getOutput();

            System.out.print("Input: " + Arrays.toString( testSetRow.getInput() ) );
            System.out.println(" Output: " + Arrays.toString( networkOutput) );

    }

    @Override
    public void handleLearningEvent(LearningEvent event) {
        BackPropagation bp = (BackPropagation)event.getSource();
        System.out.println(bp.getCurrentIteration() + ". iteration : "+ bp.getTotalNetworkError());
        if (bp.getCurrentIteration()==1000)
        	bp.stopLearning();	
    }    

}
