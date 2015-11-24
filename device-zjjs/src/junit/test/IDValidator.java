package junit.test;


public class IDValidator {

	private final int[] xishu = new int[] { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 };

	private final char[] last = new char[] { '1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2' };

	private String id;

	public IDValidator(String id) {
		this.id = id;
	}

	public String validate() {
		char [] ids = id.toCharArray();
		int sum = 0;
		for (int i = 0; i < ids.length; i++) {
			int num  = Integer.parseInt(ids[i]+"");
			sum += num * xishu[i];
		}
		return last[sum % 11]+"";
	}

	public static void main(String[] args) {
		//Scanner in = new Scanner(System.in);
		String id = "42052819870415382";
		IDValidator validator = new IDValidator(id); 
		//validator.validate();
		System.out.println(id+validator.validate());
	}

}
