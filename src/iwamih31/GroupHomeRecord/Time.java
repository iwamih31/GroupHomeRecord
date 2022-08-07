package iwamih31.GroupHomeRecord;

import javax.swing.table.AbstractTableModel;

public class Time extends AbstractTableModel{

	private static Object[][] list;

	Time(){

		System.out.println("");//////////////////////////////////
		System.out.println("Time() します");/////////////////////
		System.out.println("");//////////////////////////////////

		String time = TableData.todayMDEHm();
		list = new Object[ ][ ]{
				{time},
				};
	}

	public void setList(String[][] setList) {
		list = setList;
	}

	public static Object[][] getList() {
		return list;
	}	

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return list[0][columnIndex].getClass();
	}

	@Override
	public String getColumnName(int column) {
		return String.valueOf(list[0][column]);
	}
	
	@Override
	public int getRowCount() {
		return list.length;
	}


	@Override
	public int getColumnCount() {
		return list[0].length;
	}


	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return list[rowIndex][columnIndex];
	}

}
