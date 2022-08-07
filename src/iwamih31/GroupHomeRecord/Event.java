package iwamih31.GroupHomeRecord;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import javax.swing.table.AbstractTableModel;

public class Event extends MyAbstractTableModel{
	private static Object[][] list;
	private static String[] columns;
	private String name;
	private Object date;
	private Object[] times;
	private Object time;
	private String uri;
	private String[] rule;
	private String[][] columnRule;
	private DerbyC dbc;
	private static String selectMonth;
	private static Object tableName;
	private static int[] width;

	Event() {

		System.out.println("");/////////////////////////////////////////////////////////
		System.out.println("Event()します");////////////////////////////////////////////
		System.out.println("");/////////////////////////////////////////////////////////

		uri = TableData.getUri();

		tableName = "event";

		columns = new String[]{ "日付", "時間", "行事名", "内容" };

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



		  // DB作成( DBファイル名[アドレス], テーブル名, 行名&定義[二次元配列で],並べ替え基準行名,自動采番行名 )
		dbc = new DerbyC(uri, tableName, columnRule, columns[0], "番号");

		String todaysMonth = TableData.sdformatYM(new Date());

		if(selectMonth == null) selectMonth = todaysMonth;

		if (sizeLike(tableName,columns[0],selectMonth) == 0){
//			insertBlank();
		}

		list = monthly(selectMonth);

		width = new int[list[0].length];
		width[0] = 180;
		width[1] = 60;
		width[2] = 200;
		width[3] = 500;
	}

public static Object[][] monthly(Object selectMonth) {

		String likeMonth = "%" + selectMonth + "%";

		list = DerbyC.selectionLike("*", tableName, columns[0],likeMonth, columns[0]);

		if (list.length == 0){
			list = new Object[1][columns.length];
			for (int i = 0; i < columns.length; i++) {
				list[0][i] = "";
			}
		}
		return list;
	}

	private int sizeLike(Object tableName, Object whereColumn, Object whereData) {
		return DerbyC.sizeLike(tableName, whereColumn, whereData);
	}

	public static void selectMonth() {

		System.out.println("");////////////////////////////////////////
		System.out.println("selectMonth() します");////////////////////
		System.out.println("");////////////////////////////////////////

		Date initialSelectionDate = TableData.parseYM(selectMonth);

		selectMonth = TableData.yearMonth("行事の", TableData.todayY() + 8, initialSelectionDate);

		System.out.println("");/////////////////////////////////////////
		System.out.println("selectMonth = " + selectMonth + " です");///
		System.out.println("");/////////////////////////////////////////
		
		Screen.setEnt(selectMonth.substring(5));
	}

	public static void entry(){

		System.out.println("");////////////////////////////////////////
		System.out.println("Event.entry() します");///////////////////////////
		System.out.println("");////////////////////////////////////////
		
		Date initialSelectionDate = TableData.parseYM(selectMonth);

		Object[] setRowData = null;

		while (setRowData == null) {

			int inpDate = TableData.date("行事の",TableData.todayY() + 8, initialSelectionDate);
			Date parseYMD = TableData.parseYMD(inpDate);
			selectMonth = TableData.sdformatYM(parseYMD);
			String eDate = TableData.sdformatYMDE(parseYMD);
			if (eDate == "0") break;

			String eTime = TableData.time("開始");
			if (eTime == null) break;

			String eName = TableData.inputData(tableName, columns[2]);
			if (eName == null) break;

			String eDetail = TableData.inputData(tableName, columns[3]);
			if (eDetail == null) break;

			setRowData = new Object[]{ eDate, eTime, eName, eDetail};

			System.out.println("");//////////////
			System.out.print("setRowData = ");///
			for (Object object : setRowData) {///
				System.out.print(object);////////
			}////////////////////////////////////
			System.out.println("");//////////////
			System.out.println("");//////////////

			DerbyC.insert(tableName,setRowData);/////////////////////////DBテーブル＜tableName＞ に追加

		}
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

			int inp;
			
			Object eDate;
			if (inpCheck(columns[0] + "を")) {
				eDate = selectRowData[0];
			} else {
				Date oldDate = TableData.parseYMDE(selectRowData[0]);// yyyy年MM月dd日(E) を Date型 に変換
				int inpDate = TableData.date("行事の",TableData.todayY() + 8, oldDate);
				Date parseYMD = TableData.parseYMD(inpDate);// yyyyMMdd を Date型 に変換
				eDate = TableData.sdformatYMDE(parseYMD);// Date型 を yyyy年MM月dd日(E) に変換
				if (eDate == null) break;
			}

			Object eTime;
			if (inpCheck(columns[1] + "を")) {
				eTime = selectRowData[1];
			} else {
				eTime = TableData.time("開始",selectRowData[1]);
				if (eTime == null) break;
			}

			String eName = TableData.inputData(tableName, columns[2],selectRowData[2]);
			if (eName == null) break;

			String eDetail = TableData.inputData(tableName, columns[3],selectRowData[3]);
			if (eDetail == null) break;

			setRowData = new Object[]{eDate, eTime, eName, eDetail};
			
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

	public static String dayList(String selectDate) {
		// TODO 自動生成されたメソッド・スタブ

		Object[][] dayList = DerbyC.selection("*", tableName, columns[0], selectDate, columns[1]);

		String returnList = "";

		for (int i = 0; i < dayList.length; i++) {
			returnList = returnList + dayList[i][2] + "(" + dayList[i][1] + "～) ";
		}

		if (dayList.length == 0) returnList = "通常";
		return returnList;
	}

	private Object whereTime() {
		return " WHERE " + columns[1] +" = '"+ date + "' AND " + columns[2] +" = '"+ time + "'";
	}

//	private Object data(int i) {
//
//		return DerbyC.data(name, columns[i], whereTime());
//	}

	private Object sleep() {

		return DerbyC.data(name, columns[3], whereTime());
	}

	private Object water() {

		return DerbyC.data(name, columns[4], whereTime());
	}

	private Object pee(int i) {

		return DerbyC.data(name, columns[4 + i], whereTime());
	}

	private Object poop() {

		return DerbyC.data(name, columns[7], whereTime());
	}

	private Object laxative() {

		return DerbyC.data(name, columns[8], whereTime());
	}

	private Object medicine() {

		return DerbyC.data(name, columns[9], whereTime());
	}

	private Object ointment() {

		return DerbyC.data(name, columns[10], whereTime());
	}

	private Object situation() {

		return DerbyC.data(name, columns[11], whereTime());
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {

		boolean editable = true;

		if(columnIndex < 2){
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
		Event.width = width;
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
		Event.tableName = tableName;
	}

	public static Object getTableName() {
		return tableName;
	}

	public static void setColumns(String[] columns) {
		Event.columns = columns;
	}

	public static String[] getColumns() {
		return columns;
	}

	public static void setList(Object[][] list) {
		Event.list = list;
	}

	public static Object[][] getList() {
		return list;
	}

}
