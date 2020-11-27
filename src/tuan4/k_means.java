package tuan4;
/**
 * @author MaiThiThao
 *
 */

import weka.classifiers.Evaluation;
import weka.clusterers.SimpleKMeans;
import weka.core.EuclideanDistance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

public class k_means extends ledgmodel{
	SimpleKMeans kmeans;
	Evaluation eval;
	//constructor
	public k_means() {
		
	}
	public k_means(String filename,String m_opts,String d_opts) throws Exception{
		super(filename,m_opts,d_opts);
	}
	//phuong thuc
	// xay dung huan luyen mo hinh
	public void buildKMeansModel(String filename) throws Exception{
		//read train set vao bo nho
		setTrainset(filename);
		//thiet lap mo hinh kmeans
		kmeans= new SimpleKMeans();
		kmeans.setNumClusters(2);
		kmeans.setDistanceFunction(new EuclideanDistance());
		kmeans.buildClusterer(trainset);
		//xuat thong so cua mo hinh ra man hinh
		System.out.println(kmeans);
	}
	// xay dung du doan
	public void predictCluster(String filename) throws Exception{
		//read du lieu vao bo nho
		DataSource ds=new DataSource(filename);
		Instances unlabel=ds.getDataSet();
		//du doan cluster
		for(int i=0;i<unlabel.numInstances();i++) {
			double predict=kmeans.clusterInstance(unlabel.instance(i));
			System.out.println("instance "+ i +" belong to cluster "+ (predict+1));
		}
	}
	public static void main(String args[]) throws Exception {
		
			k_means model=new k_means();
			model.buildKMeansModel("data_set/data_train.arff");  
			//model.predictCluster("data_set/data_testunlabelkmean.arff");  
			model.predictCluster("data_set/data_test.arff");
			System.out.println();
	}

	
	

}
