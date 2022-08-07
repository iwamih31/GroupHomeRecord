package iwamih31.GroupHomeRecord;

import javax.swing.table.AbstractTableModel;

public class Private extends AbstractTableModel{
	
	private static Object[][] list;
	private static String[] columns;
	private static String name;
	private Object date;
	private Object[] times;
	private Object time;
	private String uri;
	private String[] rule;
	private String[][] ColumnRule;
	private DerbyC dbc;
	private static Object tableName;
	private static String ascColumn;
	private String numberName;
	private int listStart;
	private static int[] width;

	Private(Object selectRoom, Object selectName, Object selectDate) {

		System.out.println("");/////////////////////////////////////////////////////////
		System.out.println("new Private(" + selectRoom + ", " + selectName + ", " + selectDate + ")します");////
		System.out.println("");/////////////////////////////////////////////////////////

		uri = TableData.getUri();

		name = String.valueOf(selectName);

		tableName = tableName();
		
		numberName = "番号";

		date = selectDate;

		times = times();

		columns = new String[]{ "部屋番号", "氏名", "日付", "時間", "睡眠", "水分", "排尿1", "排尿2", "排便", "下剤", "服薬", "処置", "様子" };

		ascColumn = columns[3];
		
		rule = new String[columns.length];

		String ruleVar = "varchar(20) DEFAULT ''";

		String ruleInt = "int DEFAULT 0";

		for (int i = 0; i < rule.length; i++) {

			rule[i] = ruleVar;///////////////////////文字列

			if (i == 0) {/////////////////条件の場合のみ

				rule[i] = ruleInt;///////////////////数字列とする
			}
		}

		ColumnRule = new String[2][columns.length];
		ColumnRule[0] = columns;
		ColumnRule[1] = rule;
		
		listStart = 3;// リスト取り入れスタート値

		list = new Object[times.length][columns.length - listStart];

		width = new int[list[0].length];
		width[0] = 60;
		width[1] = 50;
		width[2] = 50;
		width[3] = 50;
		width[4] = 50;
		width[5] = 50;
		width[6] = 50;
		width[7] = 50;
		width[8] = 50;
		width[9] = 500;

		  // DB作成( DBファイル名[アドレス], テーブル名, 行名&定義[二次元配列で],並べ替え基準行名,自動采番行名 )
		dbc = new DerbyC(uri, tableName, ColumnRule, ascColumn, numberName);
		
		System.out.println("");////////////////////////
		System.out.println(tableName + "の" + columns[2] + "列に" + date + "が在るか調べます");///
		System.out.println("");////////////////////////
		
		int rowSize = size(tableName,columns[2],date);

		if (rowSize < 1){
			
			System.out.println("");//////////////////////////////////////
			System.out.println(date + "のデータが無いので作成します");///
			System.out.println("");//////////////////////////////////////
			
			insertBlank(selectRoom, name,date);
			
		}else{
			
			System.out.println("");///////////////////////////////////////////////
			System.out.println(date + "のデータは " + rowSize + "行 存在します");///
			System.out.println("");///////////////////////////////////////////////
		}
		

		Object[][] data =  data(selectRoom, selectName, selectDate);
		
		for (int i = 0; i < list.length; i++) {
			for (int j = 0; j < list[0].length; j++) {
				list[i][j] = data[i][j + listStart];
			}
		}
		
//		for (int i = 0; i < times.length; i++) {
//
//			time = times[i];
//
//			list[i] = new Object[]{ time, sleep(), water(), pee(1), pee(2), poop(), laxative(), medicine(), ointment(), situation() };
//		}

	}

	static Object[][] data(Object selectRoom, Object selectName, Object selectDate) {
		
		Object[] whereColumnList = new Object[]{columns[0],columns[1],columns[2]};
		Object[] whereDataList = new Object[]{selectRoom,selectName, selectDate};
		
//		String listColumns = columns[3];
//		for (int i = 4; i < columns.length; i++) {
//			listColumns = listColumns + ", " + columns[i];
//		}
		String sql = DerbyC.whereAsc(whereColumnList, whereDataList, ascColumn);
		
		return DerbyC.minusNumList(tableName(), sql);
	}

	private Object[] times() {

		System.out.println("");////////////////////////
		System.out.println("Private.times()します");///
		System.out.println("");////////////////////////
		
		times = new Object[48];
		String hour;

		for (int i = 0; i < times.length / 2; i++) {
			
			System.out.print("＜ hour " + i + " ＞ ");///
			hour = TableData.digit(i, 2);
			times[i * 2] = hour + ":00";
			times[i * 2 + 1] = hour + ":30";
			
			System.out.println("times[" + (i * 2) + "] = " + times[i * 2] + ", " + "times[" + (i * 2 + 1) + "] = " + times[i * 2 + 1]);///
			System.out.println("");////////////////////////
		}
		return times;
	}

	private void insertBlank(Object selectRoom, Object selectName, Object selectDate) {


		for (int i = 0; i < times.length; i++) {

			time = times[i];

			Object[] newRow = new Object[]{selectRoom, selectName, selectDate,time, "", "", "", "", "", "", "", "", "" };

			DerbyC.insert(tableName, newRow);
		}
	}

	private static Object tableName() {
		return tableName = name.replaceAll(" ","").replaceAll("　","");
	}

	public static Object[] selectlist(int listNumber) {
		
		Object[] returnList;
		
		switch (listNumber) {
			case 0 :
				returnList = new Object[]{};
				break;
			case 1 :
				returnList = new Object[]{ "起床", "臥床", "軽眠" };
				break;
			case 2 :
				returnList = new Object[]{ " 50", " 100", " 150", " 200", " 250", " 300", " 350", " 400" };
				break;
			case 3 :
				returnList = new Object[]{ "パ交", "リ交", "ト有", "ト無", "ト未", "失禁", "拒否" };
				break;
			case 4 :
				returnList = new Object[]{ "パ交", "リ交", "ト有", "ト無", "ト未", "失禁", "拒否" };
				break;
			case 5 :
				returnList = new Object[]{ "普多", "普中", "普小", "軟多", "軟中", "軟小" };
				break;
			case 6 :
				returnList = new Object[]{ " 5", " 6", " 7", " 8", " 9", " 10", " 11", " 12", " 13", " 14", " 15", " 16" };
				break;
			case 7 :
				returnList = new Object[]{ "○", "×", "拒否" };
				break;
			case 8 :
				returnList = new Object[]{ "○", "×", "拒否" };
				break;
			case 9 :
				returnList = TableData.distinctList(tableName, columns[12], columns[12]);
				break;
			default :
				returnList = new Object[]{};
		}
		return returnList;
	}

	private int size(Object selectTableName, Object whereColumn, Object whereData) {
		return DerbyC.size(selectTableName, whereColumn, whereData);
	}

//	private Object whereTime() {
//		return " WHERE " + columns[2] +" = '"+ date + "' AND " + columns[3] +" = '"+ time + "'";
//	}

//	private Object data(int i) {
//
//		return DerbyC.data(name, columns[i], whereTime());
//	}

//	private Object sleep() {
//
//		return DerbyC.data(name, columns[4], whereTime());
//	}
//
//	private Object water() {
//
//		return DerbyC.data(name, columns[5], whereTime());
//	}
//
//	private Object pee(int i) {
//
//		return DerbyC.data(name, columns[5 + i], whereTime());
//	}
//
//	private Object poop() {
//
//		return DerbyC.data(name, columns[8], whereTime());
//	}
//
//	private Object laxative() {
//
//		return DerbyC.data(name, columns[9], whereTime());
//	}
//
//	private Object medicine() {
//
//		return DerbyC.data(name, columns[10], whereTime());
//	}
//
//	private Object ointment() {
//
//		return DerbyC.data(name, columns[11], whereTime());
//	}
//
//	private Object situation() {
//
//		return DerbyC.data(name, columns[12], whereTime());
//	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		
		boolean isCellEditable = true;
		
		if(columnIndex == 0){
			isCellEditable = false;
		}
		return isCellEditable;
	}

	@Override
	public void setValueAt(Object val, int rowIndex, int columnIndex) {
		list[rowIndex][columnIndex] = val;
		fireTableCellUpdated(rowIndex, columnIndex);
		
		System.out.println("");/////////////////////////////////////////////////////
		System.out.println("list[" + rowIndex + "][" + columnIndex + "] = " + list[rowIndex][columnIndex] + " です");///
		System.out.println("");/////////////////////////////////////////////////////
	}


	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return list[0][columnIndex].getClass();
	}

	@Override
	public String getColumnName(int columnIndex) {
		return (columns[columnIndex + listStart]);
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

	public void setWidth(int[] width) {
		Private.width = width;
	}

	public static int[] getWidth() {
		return width;
	}

	public void setColumns(String[] columns) {
		Private.columns = columns;
	}

	public static String[] getColumns() {
		return columns;
	}

	public static void setList(Object[][] list) {
		Private.list = list;
	}

	public static Object[][] getList() {
		return list;
	}

	public static void setTableName(Object tableName) {
		Private.tableName = tableName;
	}

	public static Object getTableName() {
		return tableName;
	}

}
