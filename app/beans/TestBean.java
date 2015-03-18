package beans;

public class TestBean {
	public String name;
	public String num;
	long total;
	public TestBean(String name,String num,long total){
		this.name = name;
		this.num = num;
		this.total = total;
	}
	public TestBean(String name,String num){
		this.name=name;
		this.num=num;
	}
}