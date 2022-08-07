package iwamih31.GroupHomeRecord;

import javax.swing.table.AbstractTableModel;

public class OfficeSt extends AbstractTableModel{

	private String[] columns;
	private String[] data;
	private String[][] officeData;

	OfficeSt() {

		System.out.println("");/////////////////////////////////////////////////////////
		System.out.println("OfficeSt()します");////
		System.out.println("");/////////////////////////////////////////////////////////

		columns = new String[3];
		columns[0] = "事業所名";
		columns[1] = "部署名";
		columns[2] = "備考";

		data = new String[3];

		data[0] = Main.getOfficeName();
		data[1] = "2階";
		data[2] = "";

		officeData = new String[2][columns.length];
		officeData[0] = columns;
		officeData[1] = data;
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return officeData[0][columnIndex].getClass();
	}

	@Override
	public String getColumnName(int column) {
		return columns[column];
	}

	@Override
	public int getRowCount() {
		return officeData.length;
	}

	@Override
	public int getColumnCount() {
		return columns.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return officeData[rowIndex][columnIndex];
	}

	public static String event() {
		// TODO 自動生成されたメソッド・スタブ
		String event = Event.dayList(Screen.getSelectDate());
		return event;
	}
}
