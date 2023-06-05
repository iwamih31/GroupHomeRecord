package iwamih31.GroupHomeRecord;

import javax.swing.JLabel;

public class Detail extends MyAbstractTableModel{

	private static Object[][] list;
	private static String[] columns;
	private static String name;
	private Object selectDate;
	private Object[] times;
	private Object time;
	private String uri;
	private String[] rule;
	private String[][] ColumnRule;
	private DerbyC dbc;
	private static final Object TABLENAME = tableName();
	private static String ascColumn;
	private String numberName;
	private int listStart;
	private Object[][] dataList;
	private static String[] tableColumns;
	private static int[] width;
	private static int[] horizontalAlignments;

	Detail(Object setTime, Object setToDo) {

		System.out.println("");//////////////////////////////////////////////////////
		System.out.println("new Detail(" + setTime + ", " + setToDo + ")します");////
		System.out.println("");//////////////////////////////////////////////////////

		uri = TableData.getUri();

		numberName = "番号";

		selectDate = Screen.getSelectDate();

		dataList = OnTime.oneDay(selectDate);

//		times = times();

		columns();

		ascColumn = columns[0];

		rule = new String[columns.length];

		tableColumns = new String[]{columns[0],columns[1],columns[5]};

//		String ruleVar = "varchar(20) DEFAULT ''";
//
//		String ruleInt = "int DEFAULT 0";
//
//		for (int i = 0; i < rule.length; i++) {
//
//			rule[i] = ruleVar;///////////////////////文字列
//
//			if (i == 0) {/////////////////条件の場合のみ
//
//				rule[i] = ruleInt;///////////////////数字列とする
//			}
//		}
//
//		ColumnRule = new String[2][columns.length];
//		ColumnRule[0] = columns;
//		ColumnRule[1] = rule;
//
//
//
//
//		  // DB作成( DBファイル名[アドレス], テーブル名, 行名&定義[二次元配列で],並べ替え基準行名,自動采番行名 )
//		dbc = new DerbyC(uri, TABLENAME, ColumnRule, ascColumn, numberName);
//
//		System.out.println("");////////////////////////
//		System.out.println(TABLENAME + "の" + columns[2] + "列に" + selectDate + "が在るか調べます");///
//		System.out.println("");////////////////////////
//
//		insertBlank(selectDate);
//
////		Object[][] data =  data(selectDate);
//
		list = list(selectDate, setTime, setToDo);
//
//
////		Object[][] data = data(list, selectDate);

		width = new int[tableColumns.length];
		width[0] = 80;
		width[1] = 120;
		width[2] = 300;

		horizontalAlignments = new int[tableColumns.length];
		horizontalAlignments[0] = JLabel.CENTER;
		horizontalAlignments[1] = JLabel.CENTER;
		horizontalAlignments[2] = JLabel.LEFT;


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

	static Object[][] selectData(String whereColumn, Object whereData) {
		return DerbyC.selection("", tableName(), whereColumn, whereData, ascColumn());
	}

	private static Object ascColumn() {

		return columns()[0];
	}

	static Object[][] list(Object selectDate, Object setTime, Object setToDo) {

		columns();

		tableColumns = new String[]{columns[0],columns[1],columns[5]};

		Object[] whereColumnList = new Object[]{columns[2], columns[3], columns[4]};
		Object[] whereDataList = new Object[]{ selectDate, setTime, setToDo};

		String sql = DerbyC.selectWhereAsc(tableColumns, tableName(), whereColumnList, whereDataList, ascColumn);

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

						DerbyC.insert(tableName(), newRow);
					}
				}
			}
		}
	}

	private static String[] columns() {

		columns = OnTime.getColumns();

		return columns;
	}

	static Object[][] selectList() {

		String selectColumn = columns[0] + " + ' ' + " + columns[1];

		return DerbyC.dataList(selectColumn, tableName(), "", columns[0]);
	}

	static void insertBlankU(Object selectDate, Object setroomNamber, Object setUserName) {

		int roomNamber = Integer.parseInt(String.valueOf(setroomNamber));

		Object[][] toDoList = Routine.data();

		for (int i = 0; i < toDoList.length; i++) {

			if (setUserName.equals("") == false) {
				Object[] newRow = new Object[]{ roomNamber, setUserName, selectDate, toDoList[i][0], toDoList[i][1], "" };

				DerbyC.insert(tableName(), newRow);
			}
		}
	}

	static void insertBlankR(Object selectDate, Object toDoTime, Object toDoName) {

		Object[][] userList = TableData.allUser();

		for (int i = 0; i < userList.length; i++) {

			if (userList[i][1].equals("") == false) {
				Object[] newRow = new Object[]{ userList[i][0], userList[i][1], selectDate, toDoTime, toDoName, "" };

				DerbyC.insert(tableName(), newRow);
			}
		}
	}

	public static void update(Object updateColumn, Object updateData, Object[] whereColumn, Object[] whereData) {

		DerbyC.update(tableName(), updateColumn, updateData, whereColumn, whereData);
	}

	private static Object tableName() {

		return OnTime.getTableName();
	}

	private static int size(Object whereColumn, Object whereData) {
		return DerbyC.size(tableName(), whereColumn, whereData);
	}

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

	public void setColumns(String[] columns) {
		Detail.columns = columns;
	}

	public static String[] getColumns() {
		return columns;
	}

	public static void setList(Object[][] list) {
		Detail.list = list;
	}

	public static Object[][] getList() {
		return list;
	}

	public static Object getTableName() {
		return TABLENAME;
	}

	public static void setTableColumns(String[] tableColumns) {
		Detail.tableColumns = tableColumns;
	}

	public static String[] getTableColumns() {
		return tableColumns;
	}
}
