package manager_file;

import java.util.*;

public class EmployeesInfo {

	private int emp_no;
	private String name;
	private String tel;
	private Date start_date;
	private String emp_degree;
	
	public EmployeesInfo(int emp_no, String name, String tel, Date start_date, String emp_degree) {
		super();
		this.emp_no = emp_no;
		this.name = name;
		this.tel  = tel;
		this.start_date = start_date;
		this.emp_degree = emp_degree;
	}
	
	public void setEmp_no(int emp_no) {
		this.emp_no = emp_no;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setTel(String tel) {
		this.tel = tel;
	}
	
	public void setStart_date(Date start_date) {
		this.start_date = start_date;
	}
	
	public void setEmp_degree(String emp_degree) {
		this.emp_degree = emp_degree;
	}
	
	public int getEmp_no() {
		return emp_no;
	}
	
	public String getName() {
		return name;
	}
	
	public String getTel() {
		return tel;
	}
	
	public Date getStart_date() {
		return start_date;
	}
	
	public String getEmp_degree() {
		return emp_degree;
	}
	
	@Override
	public String toString() {
		return String.format("%d, %s, %s, %s, %s", emp_no, name, tel, start_date, emp_degree);
	}
}
