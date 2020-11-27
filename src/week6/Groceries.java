package week6;
/**
 * @author MaiThiThao
 *
 */

import weka.associations.Apriori;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

//java weka.core.converters.TextDirectoryLoader -dir "">""\n.arff

public class Groceries extends KnowledgeModel{
	Apriori apriori;
	Instances newData;
	// phuong thuc hoi tao khong tham so
	public Groceries() {
		
	}
	// phuong thuc khoi tao co tham so
	public Groceries(String filename,String m_opts, String d_opts) throws Exception {
		super(filename,m_opts,d_opts);
		this.apriori=new Apriori();
		
	}
	public void mineAssociationRules() throws Exception {
		//loc du lieu
		this.newData=removeData(this.dataset);
		// thiet lap thong so cho model apriori
		apriori.setOptions(this.model_options);
		//khai pha luat ket hop bang thuat toan apriori
		apriori.buildAssociations(this.newData);
		
	}
	@Override
	public String toString() {
		return apriori.toString();
		//to change body of generated methods, choose tools,templates
	}
	public static void main(String[] args) throws Exception {
		/*Groceries model=new Groceries(
				"data_set/grorice.arff",
				"-N 10 -T 0 -C 0.9 -D 0.05 -U 1.0 -M 0.1 -S -1.0 -c -1",
				"-R 1-9,11,57,70,79-81,88-89,98,100-102");
		//-R 1-9,11,57,70,79-81,88-89,98,100-102,107-114,116-120,122-130,137-179,189,192-199,201-216
		model.mineAssociationRules();//goi ket qua khai pha luat ket hop
		System.out.println(model);*/
		//load dataset
		String dataset="data_set/grorice.arff";
		DataSource source=new DataSource(dataset);
		// get instance object
		Instances data=source.getDataSet();
		// the apriori algothrim
		Apriori model=new Apriori();
		// build model
		model.buildAssociations(data);
		// print out the extracted rules
		System.out.println(model);
	}
	
	

}
