package iwamih31.GroupHomeRecord;

import javax.swing.table.AbstractTableModel;

public class ToDo extends AbstractTableModel{

	private String[] columns;
	private Object[][] data;
	private Object[][] list;
	private int items;
	private int rows;

	ToDo() {

		System.out.println("");/////////////////////////////////////////////////////////
		System.out.println("ToDo()します");////
		System.out.println("");/////////////////////////////////////////////////////////
		
		columns = new String[]{"内容"};
		
		items = 8;// 表示項目数
		
		rows = 2;// 1項目に使用する行数
		
		new Routine();
		
		data = Routine.createlist();
		
		int current = 0;
		
		list = new Object[items * rows][1];
		
		for (int i = 0; i < list.length; i++) {
			list[i][0] = "";
		}
		
		for (int i = 0; i < items; i++) {
			if (data.length > current + i) {
				for (int j = 0; j < rows; j++) {
					list[j + (rows * i)][0] = data[current + i][j];
				}
//				list[0 + (rows * i)][0] = data[current + i][0];
//				list[1 + (rows * i)][0] = data[current + i][1];
			}
		}
//		if (data.length > current) {
//			list[0][0] = data[current][0];
//			list[1][0] = data[current][1];
//		}
//		if (data.length > current + 1) {
//			list[2][0] = data[current + 1][0];
//			list[3][0] = data[current + 1][1];
//		}
//		if (data.length > current + 2) {
//			list[4][0] = data[current + 2][0];
//			list[5][0] = data[current + 2][1];
//		}
//		if (data.length > current + 3) {
//			list[6][0] = data[current + 3][0];
//			list[7][0] = data[current + 3][1];
//		}
//		if (data.length > current + 4) {
//			list[8][0] = data[current + 4][0];
//			list[9][0] = data[current + 4][1];
//		}
		
	}

	private String data(String string, String string2) {
		
		return null;
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return list[0][columnIndex].getClass();
	}

	@Override
	public String getColumnName(int column) {
		return columns[column];
	}

	@Override
	public int getRowCount() {
		return list.length;
	}

	@Override
	public int getColumnCount() {
		return columns.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return list[rowIndex][columnIndex];
	}

	public static String event() {
		// TODO 自動生成されたメソッド・スタブ
		String event = "通常";
		return event;
	}
}
