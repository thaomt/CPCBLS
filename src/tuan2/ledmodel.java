package tuan2;

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
public class ledmodel{
	// luu tru mo hinh du lieu
	DataSource source;
	// luu tru du lieu dau vao
	Instances dataset;
	Instances trainset;
	Instances testset;
	String[] model_option;
	String[] data_option;
	
	// cac phuong thuc
	public ledmodel() {
	
	}
	// doc du lieu
	public ledmodel(String filename, String m_opts, String d_opts) throws Exception {
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