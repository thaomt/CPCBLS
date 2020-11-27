package tuan4;

/*import java.io.File;
import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.core.converters.CSVSaver;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.NumericToNominal;
import weka.filters.unsupervised.attribute.Remove;
import weka.filters.unsupervised.instance.RemovePercentage;

public class ledgmodel {
	DataSource source;
	Instances dataset;
	String[] model_options;
	String[] data_options;
	Instances trainset;
	Instances testset;
	
	public ledgmodel() {
		
	}
	public ledgmodel(String filename,String m_opts,String d_opts) throws Exception {
		this.source=new DataSource(filename);
		this.dataset=source.getDataSet();
		if(m_opts!=null) {
			this.model_options=weka.core.Utils.splitOptions(m_opts);
		}
		if(d_opts!=null) {
			this.data_options=weka.core.Utils.splitOptions(d_opts);
		}
		
	}
	public Instances removeData(Instances orginalData) throws Exception{
		Remove remove=new Remove();
		remove.setOptions(data_options);
		remove.setInputFormat(orginalData);
		return Filter.useFilter(orginalData, remove);
		
	}
	public Instances convertData(Instances orginalData) throws Exception{
		NumericToNominal n2n=new NumericToNominal();
		n2n.setOptions(data_options);
		n2n.setInputFormat(orginalData);
		return Filter.useFilter(orginalData, n2n);
		
	}
	public void saveData(String filename) throws Exception{
		ArffSaver outData=new ArffSaver();         
		outData.setInstances(this.dataset);
		outData.setFile(new File(filename));
		outData.writeBatch();
		System.out.println("finished");
	}
	public void saveData2CSV(String filename) throws Exception{
		CSVSaver outData=new CSVSaver();
		outData.setInstances(this.dataset);
		outData.setFile(new File(filename));
		outData.writeBatch();
		System.out.println("convertes");
	}
	public Instances divideTrainSet(Instances orginalSet,double percent,boolean isTest) throws Exception {
		RemovePercentage rp=new RemovePercentage();
		rp.setPercentage(percent);
		rp.setInvertSelection(isTest);
		rp.setInputFormat(orginalSet);
		return Filter.useFilter(orginalSet, rp);
	}
	public void setTrainset(String filename) throws Exception {
		DataSource trainSource = new DataSource(filename);
		this.trainset =  trainSource.getDataSet();
	}
	public void setTestset(String filename) throws Exception{
		DataSource testSource = new DataSource(filename);
		this.testset =  testSource.getDataSet();
	}
	@Override
	public String toString() {
		return dataset.toSummaryString();
	}
	

}*/




import java.io.File;
import java.io.IOException;

import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.Filter;
import weka.filters.unsupervised.instance.RemovePercentage;

/**
 * @author MaiThiThao
 *
 */
public class ledgmodel{
	// luu tru mo hinh du lieu
	DataSource source;
	// luu tru du lieu dau vao
	Instances dataset;
	Instances trainset;
	Instances testset;
	String[] model_option;
	String[] data_option;
	
	// cca phuong thuc
	public ledgmodel() {
	
	}
	// doc du lieu
	public ledgmodel(String filename, String m_opts, String d_opts) throws Exception {
		if(!filename.isEmpty()) {
			// doc tu file
			this.source = new DataSource(filename);
			// doc du lieu tu file arff vao bo nho, luu tru trorng thuoc tinh dataset
			this.dataset = source.getDataSet();
		}
		if(m_opts!=null) {
			this.model_option=weka.core.Utils.splitOptions(m_opts);
			
		}
		if(d_opts!=null) {
			this.model_option=weka.core.Utils.splitOptions(d_opts);
		}
	}
	// xuat du lieu
	
	public void saveData(String filename) throws IOException {
		ArffSaver outData = new ArffSaver();
		outData.setInstances(this.dataset);
		outData.setFile(new File(filename));
		// ghi du lieu ra file
		outData.writeBatch();
		System.out.println("Finished");
	}
	// pt tao ra trainset va testset
	public Instances divideTraniTest( Instances originalSet, double percent, boolean isTest) throws Exception {
		RemovePercentage rp = new RemovePercentage();
		rp.setPercentage(percent);
		rp.setInvertSelection(isTest);
		rp.setInputFormat(originalSet);
		return Filter.useFilter(originalSet, rp);
	}
	
	public void setTrainset(String filename) throws Exception {
		DataSource trainSource = new DataSource(filename);
		this.trainset =  trainSource.getDataSet();
	}
	public void setTestset(String filename) throws Exception{
		DataSource testSource = new DataSource(filename);
		this.testset =  testSource.getDataSet();
	}
	// thong bao cac gia tri da doc vao bo nho
    @Override
	public String toString() {
		// TODO Auto-generated method stub
    	// tra ve chuoi ghi toan bo gia tri tong hop cac du lieu tai vao bo nho
		return dataset.toSummaryString();
	}
	
	
}
