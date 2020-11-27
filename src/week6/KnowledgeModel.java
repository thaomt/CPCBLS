package week6;

import java.io.File;
import java.io.IOException;

import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.core.converters.CSVSaver;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Remove;

public class KnowledgeModel {
	// luu tur mo hinh du lieu
	DataSource source;
	// luu tru du lieu dau vao
	Instances dataset;
	String[] model_options;
	String[] data_options;
	// cac phuong thuc
	public KnowledgeModel() {
		
	}
	
	public KnowledgeModel(String filename,String m_opts, String d_opts) throws Exception {
		// doc tu file
		this.source= new DataSource(filename);
		// doc tu file arff vao bo nho, luu o dataset
		this.dataset=source.getDataSet();
		this.model_options=weka.core.Utils.splitOptions(m_opts);
		this.data_options=weka.core.Utils.splitOptions(d_opts);
		
	}
	public Instances removeData(Instances orginalData) throws Exception {
		Remove remove=new Remove();
		remove.setOptions(data_options);
		remove.setInputFormat(orginalData);
		return Filter.useFilter(orginalData, remove);
	}
	public void saveData(String filename) throws IOException {
		ArffSaver outData=new ArffSaver();
		outData.setInstances(this.dataset);
		outData.setFile(new File(filename));
		outData.writeBatch();
		System.out.println("finish");
		
		
	}
	public void saveData2CSV(String filename) throws IOException {
		CSVSaver outData=new CSVSaver();
		outData.setInstances(this.dataset);
		outData.setFile(new File(filename));
		outData.writeBatch();
		System.out.println("converted");
	}
	@Override
	public String toString() {
		return dataset.toSummaryString();
	}
	

}
