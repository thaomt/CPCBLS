package tuan4;
/**
 * @author MaiThiThao
 *
 */


import weka.classifiers.Evaluation;

import weka.clusterers.EM;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
/*
 * use Expectation maximization
 * 
 * */


//java weka.core.converters.TextDirectoryLoader -dir "">""\n.arff
public class emModel extends ledgmodel{
	EM em;
	Evaluation eval;
	//constructor
	// phuong thuc hoi tao khong tham so
	public emModel() {
		
	}
	// phuong thuc khoi tao co tham so
	public emModel(String filename,String m_opts,String d_opts) throws Exception{
		super(filename,m_opts,d_opts);
	}
	//phuong thuc
	// xay dung huan luyen mo hinh
	public void buildEMModel(String filename) throws Exception{
		
		//read train set vao bo nho
		setTrainset(filename);
		
		//thiet lap mo hinh EM
		em= new EM();
		em.buildClusterer(trainset);
		
		//xuat thong so cua mo hinh ra man hinh
		System.out.println(em);
	}
	// xay dung du doan
	public void predictCluster(String filename) throws Exception{
		
		//read du lieu vao bo nho
		DataSource ds=new DataSource(filename);
		Instances unlabel=ds.getDataSet();
		//du doan cluster
		for(int i=0;i<unlabel.numInstances();i++) {
			double predict=em.clusterInstance(unlabel.instance(i));
			System.out.println("instance "+ i +" belong to cluster"+ (predict+1));
		}
	}
	public static void main(String args[]) throws Exception {
			emModel model=new emModel();
			model.buildEMModel("data_set/data_train.arff");  
			model.predictCluster("data_set/data_test.arff");
			System.out.println();
	}

	
	

}
