import manager_file.ManagerSales;

public class PosBase {

	public static void main(String[] args) {
		
		// main화면에서 관리자 메뉴 누르면 이렇게 호출해주세요.
		ManagerSales ms = new ManagerSales();
		ms.setDefaultOptions();
	}
}
