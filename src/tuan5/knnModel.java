package tuan5;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Random;

import weka.classifiers.Evaluation;
import weka.classifiers.lazy.IBk;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

public class knnModel extends ledg {
	IBk knn;
	Evaluation eval;
	public knnModel() {
	}

	public knnModel(String filename,String m_opts,String d_opts) throws Exception {
		super(filename,m_opts,d_opts);
	}
	public void buildkNN(String filename) throws Exception{
		// read train set vao bo nho
		setTrainset(filename);
		this.trainset.setClassIndex(this.trainset.numAttributes()-1);
		// huan luyen mo hinh knn
		this.knn=new IBk();
		knn.setOptions(model_options);
		knn.buildClassifier(this.trainset);
	}
	public void evaluatekNN(String filename) throws Exception{
		//doc test set vao bo nho
		setTestset(filename);
		this.testset.setClassIndex(this.testset.numAttributes()-1);
		//danh gia mo hinh bang 10-fold cross-validation
		Random rnd=new Random(1);
		int folds=10;
		eval=new Evaluation(this.trainset);
		eval.crossValidateModel(knn,this.testset,folds,rnd);
		System.out.println(eval.toSummaryString(
				"\nket qua danh gia mo hinh \n----\n",false));
		
	}
	public void predictClassLabel(String fileIn,String fileOut) throws Exception{
		//doc du lieu can du doan vao bo nho 
		DataSource ds=new DataSource(fileIn);
		Instances unlabel=ds.getDataSet();
		unlabel.setClassIndex(unlabel.numAttributes()-1);
		// du doan classlabel cho tung instance
		for(int i=0;i<unlabel.numInstances();i++) {
			double predict=knn.classifyInstance(unlabel.instance(i));
			unlabel.instance(i).setClassValue(predict);
			
		}
		//xuat ra ket qua fileout
		BufferedWriter outWriter=new BufferedWriter(new FileWriter(fileOut));
		outWriter.write(unlabel.toString());
		outWriter.newLine();
		outWriter.flush();
		outWriter.close();
			
	}
	public static void main(String args[]) throws Exception {
		knnModel model= new knnModel();
		model.buildkNN("data_set/data_train.arff");
		model.evaluatekNN("data_set/data_test.arff");
		model.predictClassLabel("data_set/data_train.arff", "data_set/data_test.arff");
		System.out.println();
		
	
		
	}

}
