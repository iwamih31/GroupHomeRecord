package iwamih31.GroupHomeRecord;

import javax.swing.table.AbstractTableModel;

public class ThisDay extends AbstractTableModel{

	private static Object[][] list;

	ThisDay() {

		System.out.println("");/////////////////////////////////////////////////////////
		System.out.println("ThisDay()します");////
		System.out.println("");/////////////////////////////////////////////////////////

		String thisDay = Screen.getSelectDate();
		list = new Object[ ][ ]{
				{thisDay},
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
