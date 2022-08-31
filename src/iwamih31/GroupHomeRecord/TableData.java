package iwamih31.GroupHomeRecord;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import javax.swing.table.AbstractTableModel;

public class TableData extends AbstractTableModel implements Serializable {

	private static String uri;
	static Connection conn;
	static Statement st;
	static String now;
	private static String tableName;
	private static String[] columns;
	private static String[] rule;
	private static String[][] ColumnRule;
	private static Object[] newRow;
	private static ArrayList<String> textSet;
	private static String text;
	private static String numberName;
	private static String ascColumn;
	private static DerbyC dbc;
	private static String listName;
	private static String orderBy;
	private static String asc;
	private static String[] ascData;
	private static String selectName;
	private static Object[][] selectData;
	private static Object[][] data;
	private static String lastDate;
	private static int[] width;
	private static Object[] levelList;
	private static int capacity;// 部屋数

	TableData() {

		System.out.println("");/////////////////////////////////////////////
		System.out.println("new TableData() します");//////////////////////////
		System.out.println("");/////////////////////////////////////////////

		uri = "DbCaRe " + Main.getOfficeName();

		tableName = "userList";

		levelList = new Object[]{ "要介護１", "要介護２", "要介護３", "要介護４", "要介護５", "要支援１", "要支援２", };

//		listName = "[ " + tableName + " ] 一覧";

		System.out.println("");///////////////////////////////////////////////////////////////////////////
		System.out.println("uri = " + uri + ", tableName = " + tableName + ", listName = " + listName);///
		System.out.println("");///////////////////////////////////////////////////////////////////////////

//		columns = Goods.getColumns();
//
		columns = new String[9];
		columns[0] = "部屋番号";
		columns[1] = "氏名";
		columns[2] = "生年月日";
		columns[3] = "年齢";
		columns[4] = "入居日";
		columns[5] = "介護度";
		columns[6] = "認定開始日";
		columns[7] = "認定終了日";
		columns[8] = "備考";

		width = new int[columns.length];
		width[0] = 5;
		width[1] = 12;
		width[2] = 11;
		width[3] = 5;
		width[4] = 11;
		width[5] = 8;
		width[6] = 11;
		width[7] = 11;
		width[8] = 20;

//		newRow = Goods.getNewRow();

//		System.out.println("");////////////////////////////////////////////
//		System.out.println("newRow = " + newRow);//////////////////////////
//		System.out.println("");////////////////////////////////////////////

		rule = new String[columns.length];

		rule[0] = "int DEFAULT 0";
		rule[1] = "varchar(20) DEFAULT ''";
		rule[2] = "varchar(20) DEFAULT ''";
		rule[3] = "varchar(20) DEFAULT ''";
		rule[4] = "varchar(20) DEFAULT ''";
		rule[5] = "varchar(20) DEFAULT ''";
		rule[6] = "varchar(20) DEFAULT ''";
		rule[7] = "varchar(20) DEFAULT ''";
		rule[8] = "varchar(20) DEFAULT ''";

		ColumnRule = new String[2][columns.length];
		ColumnRule[0] = columns;
		ColumnRule[1] = rule;

		selectName = "";

		numberName = "番号";

		capacity = 20;

		ascColumn = columns[0];

		ascData = new String[]{ "昇順", "降順" };

		asc = " ( " + ascColumn + " = " + ascData[0] + " )";

		// DB作成( DBファイル名[アドレス], テーブル名, 行名&定義[二次元配列で],並べ替え基準行名,自動采番行名 )
		dbc = new DerbyC(uri, tableName, ColumnRule, ascColumn, numberName);

//		data = new Object[1][columns.length];//仮のデータ

		data = allUser();
	}


	public void start() {

		if (size() == 0) {

			for (int i = 0; i < capacity; i++) {
				insertBlankRow(i + 1);
			}
		}
	}

	static void insertBlankRow(int i) {

		Object[] blankData = new Object[columns.length];
		blankData[0] = i;
		insert(blankData);
	}

	private Object[] arraySort(Object[] sortArray, int firstElement) {

		System.out.println("");/////////////////////////////////////////////////////////
		System.out.println("arraySort(Object[] sortArray, int firstElement)");//////////
		System.out.println("sortArray を [" + firstElement + "] 先頭で並べ替えます");///
		System.out.println("");/////////////////////////////////////////////////////////

		Object[] array = new Object[sortArray.length];

		for (int i = 0; i < array.length; i++) {
			int j = i + firstElement;
			if (j > array.length - 1) j = j - sortArray.length;
			array[i] = sortArray[j];
		}
		return array;
	}


	private boolean search(Object whereTargetRow, Object searchData) {

		System.out.println("");////////////////////////////////////////////////////////////
		System.out.println("search(" + whereTargetRow + ", " + searchData + ") します");///
		System.out.println("");////////////////////////////////////////////////////////////

		int count = 0;

		for (int i = 4; i < columns.length + 2; i++) {

			if (data(whereTargetRow, i).equals(searchData)) {

				count++;
			}
		}

		if (count == 0) {
			System.out.println("");//////////////////////////////
			System.out.println(searchData + "は存在しません");///
			System.out.println("");//////////////////////////////
			return false;
		} else {
			System.out.println("");////////////////////////////
			System.out.println(searchData + "は存在します");///
			System.out.println("");////////////////////////////
			return true;
		}

	}

	private void addTie(Object tiedPhrase, Object addTieData) {

		System.out.println("");////////////////////////////////////////////////////////
		System.out.println("addTie(" + tiedPhrase + ", " + addTieData + ") します");///
		System.out.println("");////////////////////////////////////////////////////////

		Object[] phraseData = selection(columns[0], tiedPhrase)[0];

		if (phraseData.length > 0) {

			for (int i = phraseData.length - 1; i > 3; i--) {
				phraseData[i] = phraseData[i - 1];
			}
			phraseData[3] = addTieData;

			delete(columns[0], tiedPhrase);
			insert(phraseData);
		}
	}


	public void feelSet(int point) {

		System.out.println("");////////////////////////////////
		System.out.println("feelSet(" + point + ") します");///
		System.out.println("");////////////////////////////////

		System.out.println("");/////////////////////////////////////
		System.out.println("textSet.size() = " + textSet.size());///
		System.out.println("");/////////////////////////////////////

		for (String phrase : textSet) {
			int newGain = (Integer) data(columns[1], wherePhrase(phrase)) + point;
			update(columns[1], newGain, columns[0], phrase);
		}
	}

//	private int getHp() {
//		return Main.getParty()[1].getHp();
//	}
//
//	private void setHp(int i) {
//		Main.getParty()[1].setHp(i);
//	}

	private int randomTie() {
		// TODO 自動生成されたメソッド・スタブ
		int tie = 2;
		int random = 0;
		while (random == 0 && tie < 12) {
			int a = 15;
			int b = 10;
			random = new java.util.Random().nextInt(a) / b; ///// a 回の内 b 回の確率で 0 になる
			if (random == 0) {
				tie++;
			}
		}
		return tie;
	}

	private String where(Object columnName, Object data) {

		String r;

		if (isNumber(data) == true) {
			r = "WHERE " + columnName + " = " + data;
		} else {
			r = "WHERE " + columnName + " = '" + data + "'";
		}
		return r;
	}

	private String wherePhrase(Object data) {

		Object columnName = columns[0];

		String r;

		if (isNumber(data) == true) {
			r = "WHERE " + columnName + " = " + data;
		} else {
			r = "WHERE " + columnName + " = '" + data + "'";
		}
		return r;
	}

	private String whereGain(Object data) {

		Object columnName = columns[1];

		String r;

		if (isNumber(data) == true) {
			r = "WHERE " + columnName + " = " + data;
		} else {
			r = "WHERE " + columnName + " = '" + data + "'";
		}
		return r;
	}

	public static void insert(Object[] nR) {

		DerbyC.insert(tableName, nR);

//		initial();

	}

	public static int size() {

		int r = DerbyC.size(tableName,"");
		return r;
	}

	public static int size(String setSqlSection) {

		int r = DerbyC.size(setSqlSection);
		return r;
	}

	public static int size(String setTableName, String setSqlSection) {

		int r = DerbyC.size(setTableName, setSqlSection);
		return r;
	}

	public static Object data(int rowNumber, String columnName) {

		Object r = DerbyC.data(rowNumber, columnName);
		return r;
	}

	public static Object data(Object selectRowSql, int columnNamber) {

		Object r = DerbyC.data(selectRowSql, columnNamber);
		return r;
	}

	public static Object data(String selectColumn, String selectRowSql) {

		return DerbyC.data(selectColumn, selectRowSql);
	}

	public static Object[][] dataList(String setSql) {

		return DerbyC.dataList(setSql);

	}

	public static Object[][] ascDataList(Object setTableName, String ascColumn) {

		return DerbyC.ascDataList(setTableName, ascColumn);

	}

	public static Object[][] dataListAll() {

		return DerbyC.dataListAll();

	}

	public static Object[][] dataList() {

		return DerbyC.dataList();

	}

	public static Object[][] selection(Object selectColumn, Object selectData) {

		return DerbyC.selection(selectColumn, selectData);
	}

	public static void delete(Object columnName, int data) {

		DerbyC.delete(columnName, data);
	}

	public static void delete(Object columnName, Object data) {

		DerbyC.delete(tableName, columnName, data);
	}


	static void initial(String setSqlSection) {

		System.out.println("");///////////////////////////////////////////////////////////
		System.out.println("データベース内のデータを" + setSqlSection + "の結果で書き換えます");///////
		System.out.println("");///////////////////////////////////////////////////////////

		Object[][] sectionAllData = DerbyC.dataList(setSqlSection);/////引数setSqlSectionに従い全データ取り出し

		DerbyC.reDataTable(tableName, sectionAllData);////////////////////データベース内容書き換え

	}

	static void initial() {

		System.out.println("");///////////////////////////////////////////////////////////
		System.out.println("データベース内のデータを計算し直してから書き換えます");///////
		System.out.println("");///////////////////////////////////////////////////////////

		Object[][] ascAllData = DerbyC.dataList(" ORDER BY " + columns[5]);/////全データ取り出し(日付昇順)

		Object[][] ascAllDataCalculated = calculateData(ascAllData);////////////////合計値再計算

		DerbyC.reDataTable(tableName, ascAllDataCalculated);////////////////////データベース内容書き換え

	}

	private static Object[][] calculateData(Object[][] dataList) {

		System.out.println("");///////////////////////////////////////////////////////////
		System.out.println("Object[][] dataListを計算し直します");///////
		System.out.println("");///////////////////////////////////////////////////////////

		int plus = 0;
		int minus = 0;
		int total = 0;

		for (int j = 0; j < dataList.length; j++) {
			plus = plus + (Integer) dataList[j][1];
			System.out.println("plus 合計 = " + plus);///////
			minus = minus + (Integer) dataList[j][2];
			System.out.println("minus 合計 = " + minus);///////
			total = (plus - minus);
			dataList[j][3] = (total);
			System.out.println("total = " + dataList[j][3]);///////

		}
		return dataList;
	}

	static void update(Object setColumnAndData, Object setWhere) {
		DerbyC.update(setColumnAndData, setWhere);
	}

	static void update(Object setColumn, Object setData, Object whereColumn, Object whereData) {
		DerbyC.update(setColumn, setData, whereColumn, whereData);
	}

	public static Object[] distinctList(String selectColumn, String orderColumn) {

		return distinctList(tableName, selectColumn, orderColumn);
	}

	public static Object[] distinctList(Object setTableName, String distinctColumn, String orderColumn) {

		return distinctList(setTableName, distinctColumn, "", orderColumn);

	}

	public static Object[] distinctList(Object setTableName, String distinctColumn, String whereSql, String orderColumn) {

		Object[][] distinctData = DerbyC.distinctList(setTableName, distinctColumn, whereSql, orderColumn);

		Object[] tableColumns = DerbyC.columnsAll(setTableName);

		int columnI = 0;//列番号
		/////選んだ列が何番目か判定し、列番号(columnI)に代入
		for (int i = 0; i < distinctData[0].length; i++) {
			if (distinctColumn.equals(tableColumns[i])) columnI = i;
		}
		Object[] itemData = new Object[distinctData.length];/////選択用データ配列
		/////選んだ列にあるデータを全部、選択用データ配列に代入
		for (int j = 0; j < itemData.length; j++) {
			itemData[j] = distinctData[j][columnI];
		}
		return itemData;
	}

	static String listName() {

		listName = (selectName + asc);
		return listName;

	}

	private static boolean isNumber(Object p) {
		try {
			Integer.parseInt(String.valueOf(p));
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	public static Object[] entryUser(Object roomNumber) {

		System.out.println("");////////////////////////////////////////
		System.out.println("entryUser(" + roomNumber + ") します");////
		System.out.println("");////////////////////////////////////////

		Object[] setRowData = null;

		Object[] returnData = null;
		while (setRowData == null) {

			int inp;

			String inpName = userName("");
			if (inpName == null) break;

			inp = date("生まれた", 2000);
			if (inp < 0) break;
			String birth = dateJPN(inp);
			String age = age(inp);

			inp = date("入居");
			if (inp < 0) break;
			String moveIn = dateJPN(inp);

			String level = level("");
			if (level == null) break;

			int st = date("認定開始");
			if (inp < 0) break;
			String start = dateJPN(st);

			int judge = -1;

			while (judge < 0) {
				inp = date("認定終了", st / 10000 + 8);
				if (inp < 0){
					judge = 1;
					break;
				}
				if (inp == 0) inp = st;
				judge = (inp - st);
			}
			if (inp < 0) break;
			String period = dateJPN(inp);

			String note = note("");
			if (note == null) break;

//			createTable(tableName);

			setRowData = new Object[]{ roomNumber, inpName, birth, age, moveIn, level, start, period, note };

			returnData = Arrays.copyOf(setRowData, setRowData.length);

			System.out.println("");//////////////
			System.out.print("setRowData = ");///
			for (Object object : setRowData) {///
				System.out.print(object);////////
			}////////////////////////////////////
			System.out.println("");//////////////
			System.out.println("");//////////////

			delete(columns[0], roomNumber);

			insert(setRowData);/////////////////////////DBテーブル userList に追加

//			plus = sumColumn(inpName, columns[1]);
//			minus = sumColumn(inpName, columns[2]);
//			total = (plus - minus);
//
//			System.out.println("");///////////////////////////////////////////////////////////////
//			System.out.println("plus = " + plus + ", minus = " + minus + ", total = " + total);///
//			System.out.println("");///////////////////////////////////////////////////////////////
//
//			setRowData = new Object[]{inpName, plus, minus, total, note, date };
//
//			System.out.println("");/////////////////////////////////////////////////////////////////////////////////////
//			System.out.println("setRowData = " + inpName +", "+ plus +", "+ minus +", "+ total +", "+ note +", "+ date);///
//			System.out.println("");/////////////////////////////////////////////////////////////////////////////////////
//
//			if ((size((String) setRowData[0],"")) == 1) {
//
//				System.out.println("");////////////////////////////////////
//				System.out.println(setRowData[0] + " は新規です");/////////
//				System.out.println("");////////////////////////////////////
//
//				insert(mainTable, setRowData);//////////////メインテーブルに挿入
//			}else{
//
//				System.out.println("");///////////////////////////////////////////////////////////////////////////////
//				System.out.println("テーブル " + mainTable + " の " + setRowData[0] + " 行のデータを書き換えます");///
//				System.out.println("");///////////////////////////////////////////////////////////////////////////////
//
//
//
//				for (int i = 1; i < setRowData.length; i++) {
//					update(mainTable, columns[i], setRowData[i], columns[0], "'" + inpName + "'");
//				}
//				reNumber(mainTable, columns[5]);
////				update(mainTable, columns[3] + " = " + total, columns[0] + " = '" + item + "'");
//			}
		}
		return returnData;
	}


	public static Object[] rewriteUser(Object[] selectRowData) {

		System.out.println("");////////////////////////////////////////
		System.out.println("rewriteUser(Object[] selectRowData) します");////
		System.out.println("");////////////////////////////////////////

		System.out.println("");////////////////////////////////////////
		System.out.println("変更行の " + columns[0] + " = " + selectRowData + " です");////
		System.out.println("");////////////////////////////////////////

		Object[] setRowData = null;

		Object[] returnData = null;

		while (setRowData == null) {

			int inp;

			String inpName = userName(selectRowData[1]);
			if (inpName == null) break;

			Object birth;
			if (inpCheck(columns[2] + "を")) {
				birth = selectRowData[2];
				inp = parseIntJPN(selectRowData[2]);
			} else {
				inp = date("生まれた", 2000, parseJPN(selectRowData[2]));
				if (inp < 0) break;
				birth = dateJPN(inp);
			}
			String age = age(inp);

			Object moveIn;
			if (inpCheck(columns[4] + "を")) {
				moveIn = selectRowData[4];
			} else {
				inp = date("入居", parseJPN(selectRowData[4]));
				if (inp < 0) break;
				moveIn = dateJPN(inp);
			}

			String level = level(selectRowData[5]);
			if (level == null) break;

			int st;
			Object start;
			if (inpCheck(columns[6] + "を")) {
				start = selectRowData[6];
				st = parseIntJPN(selectRowData[6]);
			} else {
				st = date("認定開始", parseJPN(selectRowData[6]));
				if (inp < 0) break;
				start = dateJPN(st);
			}

			Object period = null;
			int judge = -1;
			while (judge < 0) {

				if (inpCheck(columns[7] + "を")) {
					inp = parseIntJPN(selectRowData[7]);
				} else {

					inp = date("認定終了", st / 10000 + 8, parseJPN(selectRowData[7]));

					System.out.println("");/////////////////////////////////////////////////////
					System.out.println(columns[7] + "の入力値 = " + inp + " です");/////////////////////////////////
					System.out.println("");/////////////////////////////////////////////////////

					if (inp < 0) {
						judge = 1;
						break;
					}
				}
				if (inp == 0) inp = st;

				judge = (inp - st);

				if (inp < 0) break;

				period = dateJPN(inp);
			}

			String note = note(selectRowData[8]);
			if (note == null) break;



			setRowData = new Object[]{ selectRowData[0], inpName, birth, age, moveIn, level, start, period, note };

			returnData = Arrays.copyOf(setRowData, setRowData.length);

			System.out.println("");//////////////
			System.out.print("setRowData = ");///
			for (Object object : setRowData) {///
				System.out.print(object);////////
			}////////////////////////////////////
			System.out.println("");//////////////
			System.out.println("");//////////////

			delete(columns[0], selectRowData[0]);

			insert(setRowData);/////////////////////////DBテーブル userList に追加
		}
		return returnData;
	}

	private static int parseIntJPN(Object yMdJ){

		return Integer.parseInt(sdformatYMD(parseJPN(yMdJ)));
	}

	private static boolean inpCheck(String checkItem) {

		return Screen.checkD(checkItem + "変更しますか？","変更しない", "変更する");
	}

	private void createTable(String tableName) {

		DerbyC.createTable(tableName);
	}

	private static String userName(Object initialelectionName) {

		String inpName = Screen.inpDS("利用者名は？", initialelectionName);
		if (inpName != null) {
//			if (isNumber(inpName)) inpName = "品番" + inpName;
			if (inpName.equals("")) {
				String columnName = columns[1];
				Object[] selectData = distinctList(columnName, columnName);
				inpName = (String) Screen.selectDS("名前を選択して下さい", selectData);
			}

			System.out.println("");/////////////////////////////////////////////////////
			System.out.println("inpName＝(" + inpName + ")");/////////////////////////////////
			System.out.println("");/////////////////////////////////////////////////////

			if (inpName.equals("") || inpName.equals(" ")) {
				inpName = "仮" + todayYMDEHms();
				System.out.println("");///////////////////////////////////////////////////
				System.out.println(columns[1] + " を (" + inpName + ") に設定しました");//////
				System.out.println("");///////////////////////////////////////////////////
			}
		}
//		Monitor.display.setText("品名 = " + inpName);

		return inpName;
	}

	private static int birth() {

		int dateYMD = 0;
		int year = 0;
		int month = 0;
		int day = 0;
		Date today = new Date();
		int sAD = 2000;/////////////////////////////////////////最大西暦

		Object sel;

		while (dateYMD == 0) {

			year = Screen.inpDI("生れた年を入力して下さい");
			if (year == 0) {
				Object[] selectYear = new Object[100];////////////選択肢数
				selectYear[0] = 0;
				for (int i = 1; i < selectYear.length; i++) {
					selectYear[i] = sAD - (i - 1);
				}
				sel = Screen.selectD("生れた年を選択して下さい", selectYear);
				if (sel != null) {
					year = (Integer) sel;
//				if(year > 2000) sAD = (Integer) sel;
				}
			}
			if (year == 0) break;

			int yyyy = 2000;
			if (year < yyyy) year = (year + yyyy);

			month = Screen.inpDI("生れた月を入力して下さい");
			if (month == 0) {
				Object[] selectmonth = new Object[]{ 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 };
				sel = Screen.selectD("生れた月を選択して下さい", selectmonth);
				if (sel != null) month = (Integer) sel;
			}
			if (month == 0) break;

			day = Screen.inpDI("生れた日を入力して下さい");
			if (day == 0) {
				int d = 32;
				if (month == 2) d = 30;
				if (month == 4 || month == 6 || month == 9 || month == 11) d = 31;

				Object[] selectDay = new Object[d];///////////選択肢数
				selectDay[0] = 0;
				for (int i = 1; i < selectDay.length; i++) {
					selectDay[i] = i;
				}
				sel = Screen.selectD("生れた日を選択して下さい", selectDay);
				if (sel != null) day = (Integer) sel;
			}
			if (day == 0) break;

			dateYMD = Integer.parseInt(digit(year, 4) + digit(month, 2) + digit(day, 2));

			lastDate = dateJPN(dateYMD);

			age(dateYMD);
			////////////////////////////////////////////////age作成

		}

//		if (sel != null && date == null) {
//
//			Object[] selectDate = new Object[]{ lastDate, jToday() };/////選択用データ配列
//			date = (String) Screen.selectD("年月日を選択して下さい", selectDate);
//			lastDate = date;
//		}
		return dateYMD;
	}

	static String dateJPN(int yyyyMMdd) {

		int year = yyyyMMdd / 10000;
		int month = (yyyyMMdd % 10000) / 100;
		int day = yyyyMMdd % 100;

//		String y = String.format("%4d", year);
//		String m = String.format("%2d", month);
//		String d = String.format("%2d", day);

		String dateJ = year + "年" + month + "月" + day + "日";

		return dateJ;
	}

	private static String age(int birthYMD) {

		String age = ((Integer.parseInt(todayYMD()) - birthYMD) / 10000) + "歳";

		return age;
	}

	private static String moveIn(String question) {

		String date = null;
		int year = 0;
		int month = 0;
		int day = 0;

		Date today = new Date();
		SimpleDateFormat sdformat = new SimpleDateFormat("yyyy");
		String todaysYear = sdformat.format(today);
		int sAD = Integer.parseInt(todaysYear) + 1;///////////西暦(ドロップダウンリスト用) 開始値

		Object sel = null;

		while (date == null) {

			year = Screen.inpDI(question + "年を入力して下さい");
			if (year == 0) {
				Object[] selectYear = new Object[100];////////////選択肢数
				selectYear[0] = 0;
				for (int i = 1; i < selectYear.length; i++) {
					selectYear[i] = sAD - (i - 1);
				}
				sel = Screen.selectD(question + "年を選択して下さい", selectYear);
				if (sel != null) {
					year = (Integer) sel;
//				if(year > 2000) sAD = (Integer) sel;
				}
			}
			if (year == 0) break;

			int yyyy = 2000;
			if (year < yyyy) year = (year + yyyy);

			month = Screen.inpDI(question + "月を入力して下さい");
			if (month == 0) {
				Object[] selectmonth = new Object[]{ 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 };
				sel = Screen.selectD(question + "月を選択して下さい", selectmonth);
				if (sel != null) month = (Integer) sel;
			}
			if (month == 0) break;

			day = Screen.inpDI(question + "日を入力して下さい");
			if (day == 0) {
				Object[] selectDay = new Object[32];///////////選択肢数
				selectDay[0] = 0;
				for (int i = 1; i < selectDay.length; i++) {
					selectDay[i] = i;
				}
				sel = Screen.selectD(question + "日を選択して下さい", selectDay);
				if (sel != null) day = (Integer) sel;
			}
			if (day == 0) break;

			////////////////////////////////////////////////date作成
			String y = String.format("%4d", year);
			String m = String.format("%2d", month);
			String d = String.format("%2d", day);
			date = y + "年" + m + "月" + d + "日";
			lastDate = date;

		}

		if (sel != null && date == null) {

			Object[] selectDate = new Object[]{ lastDate, todayJPN() };/////選択用データ配列
			date = (String) Screen.selectD("年月日を選択して下さい", selectDate);
			lastDate = date;
		}
		return date;
	}

	private static String level(Object initialSelectionLevel) {

		String inpName = Screen.inpDS("介護度は？", initialSelectionLevel);
		if (inpName != null) {
//			if (isNumber(inpName)) inpName = "品番" + inpName;
			if (inpName.equals("")) {
				Object[] selectData = new Object[]{ "要介護１", "要介護２", "要介護３", "要介護４", "要介護５", "要支援１", "要支援２", };/////選択用データ配列
				inpName = (String) Screen.selectDS("介護度を選択して下さい", selectData);
			}

			System.out.println("");/////////////////////////////////////////////////////
			System.out.println("inpName＝(" + inpName + ")");/////////////////////////////////
			System.out.println("");/////////////////////////////////////////////////////

			if (inpName.equals("") || inpName.equals(" ")) {
//				inpName = "I" + todayEHms();
				System.out.println("");///////////////////////////////////////////////////
				System.out.println("介護度 を入力日時 (" + inpName + ") に設定しました");//////
				System.out.println("");///////////////////////////////////////////////////
			}
		}
//		Monitor.display.setText("品名 = " + inpName);

		return inpName;
	}

	private static int startAD() {

		Date today = new Date();
		SimpleDateFormat sdformat = new SimpleDateFormat("yyyy");
		String todaysYear = sdformat.format(today);
		int startAD = Integer.parseInt(todaysYear) + 1;///////////西暦(ドロップダウンリスト用) 開始値
		return startAD;
	}

	static int date(String question) {

		return date(question, startAD());
	}

	static int date(String question, Date initialSelectionDate) {
		return date(question, startAD(), initialSelectionDate);
	}

	static int date(String question, int startAD) {

		return date(question, startAD, 0);
	}

	static int date(String question, int startAD, Date initialSelectionDate) {
		int initialDate = Integer.parseInt(sdformatYMD(initialSelectionDate));

		return date(question, startAD, initialDate);
	}

	static int date(String question, int startAD, int yyyyMMdd) {

		int dateYMD = 0;
		int year = 0;
		int month = 0;
		int day = 0;
		int initialDate = yyyyMMdd;

		String sel = null;

		while (dateYMD < 1) {

			year = Screen.inpDI(question + "年を入力して下さい", initialDate / 10000);

			if (year < 1) {
				Object[] selectYear = new Object[100];////////////選択肢数
				selectYear[0] = 0;
				for (int i = 1; i < selectYear.length; i++) {
					selectYear[i] = startAD - (i - 1);
				}
				sel = Screen.selectD(question + "年を選択して下さい", selectYear);

				if (sel == null) break;

				year = Integer.parseInt(sel);
			}

			month = Screen.inpDI(question + "月を入力して下さい", initialDate % 10000 / 100);
			if (month < 1 || 12 < month) {
				Object[] selectmonth = new Object[]{ 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 };
				sel = Screen.selectD(question + "月を選択して下さい", selectmonth);
				if (sel != null) month = Integer.parseInt(sel);
			}
			if (month < 1) break;

			day = Screen.inpDI(question + "日を入力して下さい", initialDate % 100);
			if (day < 1 || 31 < day) {
				Object[] selectDay = new Object[32];///////////選択肢数
				selectDay[0] = 0;
				for (int i = 1; i < selectDay.length; i++) {
					selectDay[i] = i;
				}
				sel = Screen.selectD(question + "日を選択して下さい", selectDay);
				if (sel != null){

					day = Integer.parseInt(sel);
				}
			}else{
				sel = String.valueOf(day);
			}
			if (day < 1) break;

			dateYMD = Integer.parseInt(digit(year, 4) + digit(month, 2) + digit(day, 2));

			lastDate = dateJPN(dateYMD);

		}
		if (sel == null) dateYMD = -1;

		if (dateYMD == 0) {

			dateYMD = Integer.parseInt(todayYMD());
			lastDate = todayJPN();
		}
		return dateYMD;
	}

	static String yearMonth(String question, int startAD) {

		return yearMonth(question, startAD, new Date());
	}

	static String yearMonth(String question, int startAD, Date initialSelectionDate) {

		String dateYM = null;
		int year = 0;
		int month = 0;

		String sel = null;

//		String selectDate = sdformatYMD(parseYMDE(Screen.getSelectDate()));

		int yyyyMMdd = sdformatIntYMD(initialSelectionDate);

		while (dateYM == null) {

			year = Screen.inpDI(question + "年を入力して下さい", yyyyMMdd / 10000);
			if (year < 1) {
				Object[] selectYear = new Object[100];////////////選択肢数
				selectYear[0] = 0;
				for (int i = 1; i < selectYear.length; i++) {
					selectYear[i] = startAD - (i - 1);
				}
				sel = Screen.selectD(question + "年を選択して下さい", selectYear);
				if (sel != null) {
					year = Integer.parseInt(sel);
				}
			}
			if (year < 1) break;

			month = Screen.inpDI(question + "月を入力して下さい", yyyyMMdd % 10000 / 100);
			if (month < 1) {
				Object[] selectmonth = new Object[]{ 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 };
				sel = Screen.selectD(question + "月を選択して下さい", selectmonth);
				if (sel != null) month = Integer.parseInt(sel);
			}
			if (month < 1) break;

			dateYM = digit(year, 4) + "年" + digit(month, 2) + "月";

		}

		if (sel != null && dateYM == null) {

			dateYM = todayYM();
		}
		return dateYM;
	}

	static String time(String question) {
		return time(question, "");
	}

	static String time(String question, Object initialSelectionTime) {

		int initialTime;

		Object initialHour = "";

		Object initialminute = "";

		String hhMM = String.valueOf(initialSelectionTime).replaceAll(":", "");

		if (isNumber(hhMM)) {

			initialTime = Integer.parseInt(hhMM);

			initialHour = initialTime / 100;

			initialminute = initialTime % 100;
		}

		String timeHm = null;
		String hour = null;
		String minute = null;

		String sel = null;

		while (timeHm == null) {

			int inputH = Screen.inpDI(question + "時間を入力して下さい", initialHour);

			if (-1 < inputH && inputH < 24) {

				hour = String.valueOf(inputH);

			}else{

				String[] choices = new String[24];////////////選択肢数

				for (int i = 0; i < choices.length; i++) {
					choices[i] = String.valueOf(i);
				}
				sel = Screen.selectDS(question + "時間を選択して下さい", choices);
				if (sel != null) {
					hour = sel;
				}
			}
			if (hour == null) break;

			int inputM = Screen.inpDI(question + "分を入力して下さい", initialminute);

			if (-1 < inputM && inputM < 60) {

				minute = String.valueOf(inputM);

			}else{

				String[] choices = new String[60];////////////選択肢数
				for (int i = 0; i < choices.length; i++) {
					choices[i] = String.valueOf(i);
				}
				sel = Screen.selectDS(question + "分を選択して下さい", choices);
				if (sel != null) {
					minute = sel;
				}
			}
			if (minute == null) break;

			timeHm = digit(hour, 2) + ":" + digit(minute, 2);
		}
		if (hour.equals("")) {

			timeHm = "終 日";
		}
		return timeHm;
	}

	static String digit(Object number, int digit) {

		System.out.println("");// //////////////////
		System.out.print("元の数 = " + number + "希望桁数 = " + digit);///

		String zero = "";
		String returnNum = "" + number;
		int chars = chars(number);
		int blank = digit - chars;

		for (int i = 0; i < blank; i++) {
			zero = zero + "0";
		}
		returnNum = zero + returnNum;

		System.out.println("修正後の数 = " + returnNum);///
		System.out.println("");// //////////////////

		return returnNum;
	}

	private static int chars(Object text) {
		// TODO 自動生成されたメソッド・スタブ

		String inputName = String.valueOf(text);

		int p = 0;
		char[] chars = inputName.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			p += (String.valueOf(chars[i]).getBytes().length);
		}
		System.out.println("");// ////////////////////////////////////////
		System.out.println("文字バイト数 = " + p);// /////////////////////
		System.out.println("");// ////////////////////////////////////////

		return p;
	}

	private static String inpDateJPN(String question) {

		String date = null;
		int year = 0;
		int month = 0;
		int day = 0;

		Date today = new Date();
		SimpleDateFormat sdformat = new SimpleDateFormat("yyyy");
		String todaysYear = sdformat.format(today);
		int sAD = Integer.parseInt(todaysYear) + 1;///////////西暦(ドロップダウンリスト用) 開始値

		Object sel = null;

		while (date == null) {

			year = Screen.inpDI(question + "年を入力して下さい");
			if (year == 0) {
				Object[] selectYear = new Object[100];////////////選択肢数
				selectYear[0] = 0;
				for (int i = 1; i < selectYear.length; i++) {
					selectYear[i] = sAD - (i - 1);
				}
				sel = Screen.selectD(question + "年を選択して下さい", selectYear);
				if (sel != null) {
					year = (Integer) sel;
//				if(year > 2000) sAD = (Integer) sel;
				}
			}
			if (year == 0) break;

			int yyyy = 2000;
			if (year < yyyy) year = (year + yyyy);

			month = Screen.inpDI(question + "月を入力して下さい");
			if (month == 0) {
				Object[] selectmonth = new Object[]{ 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 };
				sel = Screen.selectD(question + "月を選択して下さい", selectmonth);
				if (sel != null) month = (Integer) sel;
			}
			if (month == 0) break;

			day = Screen.inpDI(question + "日を入力して下さい");
			if (day == 0) {
				Object[] selectDay = new Object[32];///////////選択肢数
				selectDay[0] = 0;
				for (int i = 1; i < selectDay.length; i++) {
					selectDay[i] = i;
				}
				sel = Screen.selectD(question + "日を選択して下さい", selectDay);
				if (sel != null) day = (Integer) sel;
			}
			if (day == 0) break;

			////////////////////////////////////////////////date作成
			String y = String.format("%4d", year);
			String m = String.format("%2d", month);
			String d = String.format("%2d", day);
			date = y + "年" + m + "月" + d + "日";
			lastDate = date;

		}

		if (sel != null && date == null) {

			Object[] selectDate = new Object[]{ lastDate, todayJPN() };/////選択用データ配列
			date = (String) Screen.selectD("年月日を選択して下さい", selectDate);
			lastDate = date;
		}
		return date;
	}

	private static String note(Object initialSelectionNote) {

		String inpName = Screen.inpDS(columns[8] + "は？", initialSelectionNote);
		if (inpName != null) {
//			if (isNumber(inpName)) inpName = "品番" + inpName;
			if (inpName.equals("")) {
				String columnName = columns[8];
				Object[] selectData = distinctList(tableName, columnName, columnName);
				inpName = (String) Screen.selectDS(columns[8] + "を選択して下さい", selectData);
			}

			System.out.println("");/////////////////////////////////////////////////////
			System.out.println("inpName＝(" + inpName + ")");/////////////////////////////////
			System.out.println("");/////////////////////////////////////////////////////

			if (inpName.equals("") || inpName.equals(" ")) {
				inpName = "t" + todayYMDEHms();
				System.out.println("");///////////////////////////////////////////////////
				System.out.println(columns[8] + " を入力日時 (" + inpName + ") に設定しました");//////
				System.out.println("");///////////////////////////////////////////////////
			}
		}
//		Monitor.display.setText("品名 = " + inpName);

		return inpName;
	}

	static String inputData(Object tableName, String inputColumn) {
		return inputData(tableName, inputColumn, "");
	}

	static String inputData(Object tableName, String inputColumn, Object initialSelectionValue) {

		String inpName = Screen.inpDS(inputColumn + "は？", initialSelectionValue);
		if (inpName != null) {
			if (inpName.equals("")) {
				Object[] selectData = distinctList(tableName, inputColumn, inputColumn);
				inpName = (String) Screen.selectDS(inputColumn + "を選択して下さい", selectData);
			}

			System.out.println("");/////////////////////////////////////////////////////
			System.out.println("inpName＝(" + inpName + ")");/////////////////////////////////
			System.out.println("");/////////////////////////////////////////////////////

			if (inpName.equals("") || inpName.equals(" ")) {
				inpName = "仮" + todayYMDEHms();
				System.out.println("");///////////////////////////////////////////////////
				System.out.println(columns[8] + " を入力日時 (" + inpName + ") に設定しました");//////
				System.out.println("");///////////////////////////////////////////////////
			}
		}
//		Monitor.display.setText("品名 = " + inpName);

		return inpName;
	}

	private static String today(String format) {
		Date today = new Date();
		SimpleDateFormat sdformat = new SimpleDateFormat(format);
		String todaysDate = sdformat.format(today);
		return todaysDate;

	}

	private static String todayJPN() {
		return today("yyyy年MM月dd日");
	}

	private static String todayYMDEHms() {
		return today("yyyyMMddEHHmmss");
	}

	static String todayTHH() {
		return today("HH");
	}

	static String todayTmm() {
		return today("mm");
	}

	static String todayTHm() {
		return today("HH:mm");
	}

	static String todayMDEHm() {
		return today("MM月dd日(E)HH:mm");
	}

	private static String todayYMDE() {
		return today("yyyyMMdd(E)");
	}

	private static String todayYMD() {
		return today("yyyyMMdd");
	}

	private static String todayYM() {
		return today("yyyy年MM月");
	}

	static int todayY() {
		return Integer.parseInt(today("yyyy"));
	}

	public static Date parseYM(Object sdformatYM) {

		return parseDate( sdformatYM, "yyyy年MM月");

	}

	public static int parseIntYM(Object sdformatYM) {

		return parseDateInt( sdformatYM, "yyyy年MM月");

	}

	public static Date parseYMD(Object yyyyMMdd) {

		return parseDate( yyyyMMdd, "yyyyMMdd");

	}

	public static Date parseYMDE(Object yMdE) {

		return parseDate( yMdE, "yyyy年MM月dd日(E)");

	}

	public static Date parseJPN(Object yMdJ) {

		return parseDate( yMdJ, "yyyy年MM月dd日");

	}

	public static Date parseDate(Object sdformatDate, String format) {

		SimpleDateFormat sdformat = new SimpleDateFormat(format);
		Date date = null;
		try {
			date = sdformat.parse(String.valueOf(sdformatDate));

		} catch (ParseException e) {

			e.getMessage();
			e.printStackTrace();
		}
		return date;

	}

	public static int parseDateInt(Object sdformatDate, String format) {
		return sdformatIntYMD((parseDate(sdformatDate, format)));
	}

	static String sdformatYMD(Date date) {
		SimpleDateFormat sdformat = new SimpleDateFormat("yyyyMMdd");
		return sdformat.format(date);
	}

	static int sdformatIntYMD(Date date) {
		return Integer.parseInt(sdformatYMD(date));
	}

	static String sdformatYMDE(Date date) {
		SimpleDateFormat sdformat = new SimpleDateFormat("yyyy年MM月dd日(E)");
		return sdformat.format(date);
	}

	static String sdformatYMDE(int yyyyMMdd) {
		Date date = parseYMD(yyyyMMdd);
		SimpleDateFormat sdformat = new SimpleDateFormat("yyyy年MM月dd日(E)");
		return sdformat.format(date);
	}

	static String sdformatYM(Date date) {
		SimpleDateFormat sdformat = new SimpleDateFormat("yyyy年MM月");
		return sdformat.format(date);
	}

	static String sdformatHm(Date date) {
		SimpleDateFormat sdformat = new SimpleDateFormat("HH:mm");
		return sdformat.format(date);
	}

	static String sdformat(Date date, String format) {
		SimpleDateFormat sdformat = new SimpleDateFormat(format);
		return sdformat.format(date);
	}

	public void setText(String text) {
		TableData.text = text;
	}

	public String getText() {
		return text;
	}

//	@Override //セル入力可否
//	public boolean isCellEditable(int rowIndex, int columnIndex) {
//		return true;
//	}
//
//	@Override //セル入力値反映
//	public void setValueAt(Object val, int rowIndex, int columnIndex) {
//		data[rowIndex][columnIndex] = val;
//		fireTableCellUpdated(rowIndex, columnIndex);
//	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return data[0][columnIndex].getClass();
	}

	@Override
	public String getColumnName(int column) {
		return columns[column];
	}

	@Override
	public int getRowCount() {
		return data.length;
	}

	@Override
	public int getColumnCount() {
//		if (Screen.getMode() < (1)) {
//			pGet();
//		}else{
//			pSt();
//		}

		return columns.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return data[rowIndex][columnIndex];
	}

	public static Object[][] allUser() {
		// TODO 自動生成されたメソッド・スタブ
		data = ascDataList(tableName, columns[0]);
		if (data == null) {
			data = new Object[][]{ { "", "" }, { "", "" } };
		}
		return data;
	}

	public static void setData(Object[][] data) {
		TableData.data = data;
	}

	public static Object[][] getData() {
		return data;
	}

	public static void user(Object roomNamber) {

		data = DerbyC.selection("", tableName, columns[0], roomNamber, ascColumn);
	}

	public static void setUri(String uri) {
		TableData.uri = uri;
	}

	public static String getUri() {
		return uri;
	}

	public void setWidth(int[] width) {
		TableData.width = width;
	}

	public static int[] getWidth() {
		return width;
	}

	public void setLevelList(Object[] levelList) {
		TableData.levelList = levelList;
	}

	public static Object[] getLevelList() {
		return levelList;
	}

	public void setCapacity(int capacity) {
		TableData.capacity = capacity;
	}

	public static int getCapacity() {
		return capacity;
	}

	public static void setTableName(String tableName) {
		TableData.tableName = tableName;
	}

	public static String getTableName() {
		return tableName;
	}

	public static void setColumns(String[] columns) {
		TableData.columns = columns;
	}

	public static String[] getColumns() {
		return columns;
	}

}
