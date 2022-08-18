package iwamih31.GroupHomeRecord;

import java.util.Arrays;

import javax.swing.table.AbstractTableModel;

public class OfficeInfo extends AbstractTableModel{
	private static Object[][] list;
	private static String[] columns = new String[]{"項目", "内容" };
	private String uri = TableData.getUri();
	private String[] rule;
	private String[][] columnRule;
	private DerbyC dbc;
	private String ascColumn;
	private String numberName;
	private static Object tableName = "officeinfo";
	private static int[] width;

	OfficeInfo() {

		System.out.println("");/////////////////////////////////////////////////////////
		System.out.println("OfficeInfo()します");////////////////////////////////////////////
		System.out.println("");/////////////////////////////////////////////////////////

		uri = TableData.getUri();

		tableName = "routine";

		numberName = "番号";

		columns = new String[]{"項目", "内容"};

		rule = new String[columns.length];

		String ruleVar = "varchar(20) DEFAULT ''";

//		String ruleInt = "int DEFAULT 0";

		for (int i = 0; i < rule.length; i++) {

			rule[i] = ruleVar;///////////////////////文字列

//			if (i == 4) {/////////////////条件の場合のみ
//
//				rule[i] = ruleInt;///////////////////数字列とする
//			}
		}

		columnRule = new String[2][columns.length];
		columnRule[0] = columns;
		columnRule[1] = rule;

		ascColumn = columns[0];

		  // DB作成( DBファイル名[アドレス], テーブル名, 行名&定義[二次元配列で],並べ替え基準行名,自動采番行名 )
		dbc = new DerbyC(uri, tableName, columnRule, ascColumn, numberName);

		list = createlist();

		width = new int[list[0].length];
		width[0] = 60;
		width[1] = 650;
	}

	public static Object[][] createlist() {

		Object[][] newList = data();

//		Object[][] newList = DerbyC.ascDataList(tableName, columns[0]);

		if (newList.length == 0){
			newList = new Object[1][columns.length];
			for (int i = 0; i < columns.length; i++) {
				newList[0][i] = "";
			}
		}
		return newList;
	}

	static Object[][] data() {
		return DerbyC.ascDataList(tableName, columns[0]);
	}

	static Object[] selectList() {

		String selectColumn = columns[0] + " || ' ' || " + columns[1] ;
		return DerbyC.switchQueues(DerbyC.dataList(selectColumn, tableName, "", columns[0]))[0];
	}

	private int sizeLike(Object tableName, Object whereColumn, Object whereData) {
		return DerbyC.sizeLike(tableName, whereColumn, whereData);
	}

	public static Object[] entry(){

		System.out.println("");////////////////////////////////////////
		System.out.println("Routine.entry() します");////////////////////
		System.out.println("");////////////////////////////////////////

		Object[] setRowData = null;

		Object[] returnData = null;

		while (setRowData == null) {

			String rTime = TableData.time("開始");
			if (rTime == null) break;

			String rName = TableData.inputData(tableName, columns[1]);
			if (rName == null) break;

			String rDetail = TableData.inputData(tableName, columns[2]);
			if (rDetail == null) break;

			setRowData = new Object[]{rTime, rName, rDetail};

			returnData = Arrays.copyOf(setRowData, setRowData.length);

			System.out.println("");//////////////
			System.out.print("setRowData = ");///
			for (Object object : setRowData) {///
				System.out.print(object);////////
			}////////////////////////////////////
			System.out.println("");//////////////
			System.out.println("");//////////////

			DerbyC.insert(tableName,setRowData);/////////////////////////DBテーブル＜tableName＞ に追加
		}
		return returnData;
	}

	public static Object[] rewriteData(Object[] selectRowData) {

		System.out.println("");////////////////////////////////////////
		System.out.println("RrewriteData(Object[] selectRowData) します");////////////////////
		System.out.println("");////////////////////////////////////////

		System.out.println("");////////////////////////////////////////
		System.out.println("変更行の " + columns[0] + " = " + selectRowData + " です");////
		System.out.println("");////////////////////////////////////////

		Object[] setRowData = null;

		Object[] returnData = null;

		while (setRowData == null) {

			Object rTime;
			if (inpCheck(columns[0] + "を")) {
				rTime = selectRowData[0];
			} else {
				rTime = TableData.time("開始",selectRowData[0]);
				if (rTime == null) break;
			}

			String rName = TableData.inputData(tableName, columns[1],selectRowData[1]);
			if (rName == null) break;

			String rDetail = TableData.inputData(tableName, columns[2],selectRowData[2]);
			if (rDetail == null) break;

			setRowData = new Object[]{rTime, rName, rDetail};

			returnData = Arrays.copyOf(setRowData, setRowData.length);

			System.out.println("");//////////////
			System.out.print("setRowData = ");///
			for (Object object : setRowData) {///
				System.out.print(object);////////
			}////////////////////////////////////
			System.out.println("");//////////////
			System.out.println("");//////////////

			DerbyC.delete(tableName, columns, selectRowData);

			DerbyC.insert(tableName,setRowData);/////////////////////////DBテーブル＜tableName＞ に追加

		}
		return returnData;
	}

	private static boolean inpCheck(String checkItem) {

		return Screen.checkD(checkItem + "変更しますか？","変更しない", "変更する");
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {

		boolean editable = true;

		if(columnIndex == 0){
			editable = false;
		}
		return editable;
	}

	@Override
	public void setValueAt(Object val, int rowIndex, int columnIndex) {
		list[rowIndex][columnIndex] = val;
		fireTableCellUpdated(rowIndex, columnIndex);
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return list[0][columnIndex].getClass();
	}

	@Override
	public String getColumnName(int columnIndex) {
		return (columns[columnIndex]);
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

	public void setWidth(int[] width) {
		OfficeInfo.width = width;
	}

	public int[] getWidth() {
		return width;
	}

	public static void change(Object[] deleteRowData, Object[] insertRowData) {

		DerbyC.delete(tableName, DerbyC.andWhere(columns,deleteRowData));
		DerbyC.insert(tableName, insertRowData);
	}

	public static void remove(Object[] deleteRowData) {

		DerbyC.delete(tableName, DerbyC.andWhere(columns,deleteRowData));
	}

	public static void setTableName(Object tableName) {
		OfficeInfo.tableName = tableName;
	}

	public static Object getTableName() {
		return tableName;
	}

	public static void setColumns(String[] columns) {
		OfficeInfo.columns = columns;
	}

	public static String[] getColumns() {
		return columns;
	}

	public static void setList(Object[][] list) {
		OfficeInfo.list = list;
	}

	public static Object[][] getList() {
		return list;
	}
}
