package tuan2;
/**
 * @author MaiThiThao
 *
 */
import java.io.BufferedWriter;
import java.io.FileWriter;

import weka.classifiers.Evaluation;
import weka.classifiers.functions.SMO;
import weka.core.Debug;
import weka.core.Instances;
import weka.core.Debug.Random;
import weka.core.converters.ConverterUtils.DataSource;

//java weka.core.converters.TextDirectoryLoader -dir "">""\n.arff
public class svm extends ledmodel {
	SMO svm;
	// phuong thuc hoi tao khong tham so
	public svm() {
	}
	// phuong thuc khoi tao co tham so
	public svm(String filename,String m_opts, String d_opts) throws Exception {
		super(filename,m_opts,d_opts);
	}
	
	public void buildSVM(String filename) throws Exception {
		setTrainset(filename);
		this.trainset.setClassIndex(this.trainset.numAttributes() - 1);
		
		this.svm = new SMO();
		//svm.setOptions(this.model_option);
		svm.buildClassifier(this.trainset);
		
	}
	
	public void evaluatetoSVM(String filename) throws Exception{
		setTestset(filename);
		this.testset.setClassIndex(this.testset.numAttributes() - 1);
		
		// danh gia mo hinh
		Random rnd = new Debug.Random();
		int folds = 10;
		Evaluation eval = new Evaluation(this.trainset);
		eval.crossValidateModel(svm, this.testset, folds, rnd);
		System.out.println(eval.toSummaryString("SVMmodel: \n",false));
		
	}
	public void predictClassLabel(String FileIn, String fileOut) throws Exception{
		DataSource ds = new DataSource(FileIn);
		Instances unlable = ds.getDataSet();
		unlable.setClassIndex(unlable.numAttributes() - 1);
		// du doan ket qua 
		for ( int i = 0; i < unlable.numInstances(); i++) {
			double predict = svm.classifyInstance(unlable.instance(i));
			unlable.instance(i).setClassValue(predict);
		}
		
		//xuat ket qua
		
		BufferedWriter outWrite = new BufferedWriter(new FileWriter(fileOut));
		outWrite.write(unlable.toString());
		outWrite.newLine();
		outWrite.flush();
		outWrite.close();
	}
	
		public static void main(String[] args) throws Exception {
			// TODO Auto-generated method stub
			
		
			svm model = new svm();
			model.buildSVM("data_set/data_train.arff");
			model.evaluatetoSVM("data_set/data_test.arff");
			
		}

	

}

