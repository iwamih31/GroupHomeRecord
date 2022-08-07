package iwamih31.GroupHomeRecord;

public class Command {
	private static String[ ][ ] menuList;



	public static void command( ) {
		for( String[ ] menus : menuList ){
			for( String menu : menus ){
			System.out.print(menu);
			}
		}
	}

	public void setMenuList(String[][] menuList) {
		Command.menuList = menuList;
	}

	public static Object[][] getMenuList() {
		return menuList;
	}

	public static String[] menu() {
		// TODO 自動生成されたメソッド・スタブ
		String[] menu = new String[menuList.length - 1];
		for (int i = 0; i < menu.length; i++) {
			menu[i] = menuList[i + 1][1];
		}

		return menu;
	}

}
