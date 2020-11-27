package tuan4;

import weka.clusterers.ClusterEvaluation;
import weka.clusterers.SimpleKMeans;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

public class kmeans_clustering {
	public static void main(String args[]) throws Exception{
		//load data
		String dataset="data_set/data_train1.arff";
		DataSource source=new DataSource(dataset);
		//get instances object
		Instances data=source.getDataSet();
		//new instance of cluster
		SimpleKMeans model=new SimpleKMeans();//Simple EM( exception maximisation)
		//number of cluster
		model.setNumClusters(2);
		//set distance function
		//model.setDistanceFuntion(new weka.core.ManhattanDistance());
		//build the cluster
		model.buildClusterer(data);
		System.out.println(model);
		//to cluster an instance ..return cluster number as int
		//modle.clusterInatance(instance);
		
		//return an array containing the estimated membership probabilities of the test instance ineach cluster
		//modle.distirbutionForInstance(Instance);
		
		/*we can evaluate a cluster with the clusterEvaluaton class
		 * for instance, separate train and test dataset can be used
		 * we can print out the number os cluster found
		 */
		ClusterEvaluation clsEval=new ClusterEvaluation();
		//load dataset
		String dataset1="data_set/data_test1.arff";
		DataSource source1=new DataSource(dataset1);
		//get instances object
		Instances data1=source1.getDataSet();
		clsEval.setClusterer(model);
		clsEval.evaluateClusterer(data1);
		
		System.out.println("#cluster:"+ clsEval.getNumClusters());
		
		
		
		
	}

}
