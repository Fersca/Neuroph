/**
 * Copyright 2010 Neuroph Project http://neuroph.sourceforge.net
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


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

/**
 * This sample shows how to create, train, save and load simple Multi Layer Perceptron for the XOR problem.
 * This sample shows basics of Neuroph API.
 * @author Zoran Sevarac <sevarac@gmail.com>
 */
public class PruebaFer implements LearningEventListener {

    public static void main(String[] args) throws IOException {
        new PruebaFer().run();
    }
    
    /**
     * Runs this sample
     * @throws IOException 
     */
    public void run() throws IOException {

        // create training set (logical XOR function)
        //DataSet trainingSet = new DataSet(10, 3);

    	//cantidad de neuronas de inicio, cantidad de fin
        DataSet trainingSet = DataSet.createFromFile("/home/fersca/coloresAutosCentro50-plata.txt", 300, 7, ",");
        
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
        MultiLayerPerceptron myMlPerceptron = new MultiLayerPerceptron(TransferFunctionType.SIGMOID, 300, 50, 7);

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
        myMlPerceptron.save("myMlPerceptronCentro50-plata.nnet");

        
        
        // load saved neural network
        //NeuralNetwork loadedMlPerceptron = NeuralNetwork.load("myMlPerceptronCentro50.nnet");

        // test loaded neural network
        System.out.println("Testing loaded neural network");
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
