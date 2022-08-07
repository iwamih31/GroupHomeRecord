package iwamih31.GroupHomeRecord;

import java.util.Arrays;

import javax.swing.table.AbstractTableModel;

public class OnTime extends MyAbstractTableModel{

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
	private static final Object TABLENAME = "OnTime";
	private static String ascColumn;
	private String numberName;
	private int listStart;
	private static String[] tableColumns;
	private static int[] width;

	OnTime(Object selectDate) {

		System.out.println("");/////////////////////////////////////////////////////////
		System.out.println("new OnTime(" + selectDate + ")します");////
		System.out.println("");/////////////////////////////////////////////////////////

		uri = TableData.getUri();

		numberName = "番号";

		date = selectDate;

//		times = times();

		columns = new String[]{ "部屋番号", "氏名", "日付", "時間", "行動", "内容" };

		ascColumn = columns[3] + ", "+ columns[0];

		rule = new String[columns.length];

		tableColumns = new String[]{columns[3],columns[4],columns[0],columns[1],columns[5]};

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




		  // DB作成( DBファイル名[アドレス], テーブル名, 行名&定義[二次元配列で],並べ替え基準行名,自動采番行名 )
		dbc = new DerbyC(uri, TABLENAME, ColumnRule, ascColumn, numberName);

		System.out.println("");////////////////////////
		System.out.println(TABLENAME + "の" + columns[2] + "列に" + date + "が在るか調べます");///
		System.out.println("");////////////////////////

		insertBlank(selectDate);

//		Object[][] data =  data(selectDate);

		list = list(selectDate);


//		Object[][] data = data(list, selectDate);

		width = new int[tableColumns.length];
		width[0] = 60;
		width[1] = 250;
		width[2] = 80;
		width[3] = 120;
		width[4] = 400;



//		for (int i = 0; i < list.length; i++) {
//			for (int j = 0; j < list[0].length; j++) {
//				list[i][j] = data[i][j + listStart];
//			}
//		}
//
//		for (int i = 0; i < times.length; i++) {
//
//			time = times[i];
//
//			list[i] = new Object[]{ time, sleep(), water(), pee(1), pee(2), poop(), laxative(), medicine(), ointment(), situation() };
//		}

	}
	
	static Object[][] oneDay(Object selectDate) {
		return DerbyC.selection("", TABLENAME, columns()[2], selectDate, ascColumn);
	}

	static Object[][] selectData(String whereColumn, Object whereData) {
		return DerbyC.selection("", TABLENAME, whereColumn, whereData, ascColumn);
	}

	static Object[][] list(Object selectDate) {

		tableColumns = new String[]{columns[3],columns[4],columns[0],columns[1],columns[5]};

		Object[] whereColumnList = new Object[]{columns[2]};
		Object[] whereDataList = new Object[]{ selectDate};

		String sql = DerbyC.selectWhereAsc(tableColumns, TABLENAME, whereColumnList, whereDataList, ascColumn);

//		return DerbyC.minusNumList(sql);
		return DerbyC.dataList(sql);
	}

	static Object[][] data(Object[][] tableData, Object selectDate) {

		int columnsLength = columns().length;

		Object[][] newData = new Object[tableData.length][columnsLength];

		for (int i = 0; i < newData.length; i++) {
			newData[i][0] = tableData[i][2];
			newData[i][1] = tableData[i][3];
			newData[i][2] = selectDate;
			newData[i][3] = tableData[i][0];
			newData[i][4] = tableData[i][1];
			newData[i][5] = tableData[i][4];
		}
		return newData;
	}

	static void insertBlank(Object selectDate) {

		Object[][] selectDateToDo = selectData(columns()[2], selectDate);
		
		int rowCount = selectDateToDo.length;
				
		if (selectDateToDo.length > 0) {
			
			int columnCount = selectDateToDo[0].length;
			
			System.out.println("");////////////////////////////////////////////////////////////////////////////////////////////////////////
			System.out.println(selectDate + " の ToDoデータ は [" + rowCount + "][" + columnCount + "] です");///
			System.out.println("");////////////////////////////////////////////////////////////////////////////////////////////////////////
		} else {
			System.out.println("");///////////////////////////////////////////////
			System.out.println(selectDate + " の ToDoデータ は 存在しません");///
			System.out.println("");///////////////////////////////////////////////
		}
		
		Object[][] toDoList = Routine.data();

		Object[][] userList = TableData.allUser();

		for (int i = 0; i < toDoList.length; i++) {

			for (int j = 0; j < userList.length; j++) {

				if (userList[j][1].equals("") == false) {

					Object[] newRow = new Object[]{ userList[j][0], userList[j][1], selectDate, toDoList[i][0], toDoList[i][1], "" };

					int equalCount = 0;
					for (int k = 0; k < selectDateToDo.length; k++) {

						String existData = "";
						String insertData = "";
						for (int l = 0; l < newRow.length - 1; l++) {
							if (l != 1) {
								if (l == 3) {
									int exTime = Integer.parseInt(String.valueOf(selectDateToDo[k][l]).replaceAll(":", "")) / 100;
									int insTime = Integer.parseInt(String.valueOf(newRow[l]).replaceAll(":", "")) / 100;
									existData = existData + exTime;
									insertData = insertData + insTime;
								} else {
									existData = existData + selectDateToDo[k][l];
									insertData = insertData + newRow[l];
								}

							}

						}
						if (existData.equals(insertData)) equalCount++;
					}

					if (equalCount == 0) {

						DerbyC.insert(TABLENAME, newRow);
					}
				}
			}
		}
	}

	private static String[] columns() {
		
		columns = new String[]{ "部屋番号", "氏名", "日付", "時間", "行動", "内容" };
		
		return columns;
	}

	static void insertBlankU(Object selectDate, Object setroomNamber, Object setUserName) {

		int roomNamber = Integer.parseInt(String.valueOf(setroomNamber));

		Object[][] toDoList = Routine.data();

		for (int i = 0; i < toDoList.length; i++) {

			if (setUserName.equals("") == false) {
				Object[] newRow = new Object[]{ roomNamber, setUserName, selectDate, toDoList[i][0], toDoList[i][1], "" };

				DerbyC.insert(TABLENAME, newRow);
			}
		}
	}

	static void insertBlankR(Object selectDate, Object toDoTime, Object toDoName) {

		Object[][] userList = TableData.allUser();

		for (int i = 0; i < userList.length; i++) {

			if (userList[i][1].equals("") == false) {
				Object[] newRow = new Object[]{ userList[i][0], userList[i][1], selectDate, toDoTime, toDoName, "" };

				DerbyC.insert(TABLENAME, newRow);
			}
		}
	}

	public static void update(Object updateColumn, Object updateData, Object[] whereColumn, Object[] whereData) {

		DerbyC.update(TABLENAME, updateColumn, updateData, whereColumn, whereData);
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
				returnList = TableData.distinctList(TABLENAME, columns[12], columns[12]);
				break;
			default :
				returnList = new Object[]{};
		}
		return returnList;
	}

	private static int size(Object whereColumn, Object whereData) {
		return DerbyC.size(TABLENAME, whereColumn, whereData);
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

		boolean isCellEditable = false;

		if(columnIndex == tableColumns.length - 1){
			isCellEditable = true;
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
		return (tableColumns[columnIndex]);
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
		OnTime.width = width;
	}

	public int[] getWidth() {
		return width;
	}

	public void setColumns(String[] columns) {
		OnTime.columns = columns;
	}

	public static String[] getColumns() {
		return columns;
	}

	public static void setList(Object[][] list) {
		OnTime.list = list;
	}

	public static Object[][] getList() {
		return list;
	}

	public static Object getTableName() {
		return TABLENAME;
	}

	public static void setTableColumns(String[] tableColumns) {
		OnTime.tableColumns = tableColumns;
	}

	public static String[] getTableColumns() {
		return tableColumns;
	}

}
