package iwamih31.GroupHomeRecord;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;

public class Talk implements Serializable{

	static String uri;
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

	Talk() {

		System.out.println("");/////////////////////////////////////////////
		System.out.println("new Talk() します");//////////////////////////
		System.out.println("");/////////////////////////////////////////////

		uri = "HuDb";

		tableName = "talk";

//		listName = "[ " + tableName + " ] 一覧";

		System.out.println("");///////////////////////////////////////////////////////////////////////////
		System.out.println("uri = " + uri + ", tableName = " + tableName + ", listName = " + listName);///
		System.out.println("");///////////////////////////////////////////////////////////////////////////

//		columns = Goods.getColumns();
//
		columns = new String[12];
		columns[0] = "会話";
		columns[1] = "利益度";
		columns[2] = "紐付け1";
		columns[3] = "紐付け2";
		columns[4] = "紐付け3";
		columns[5] = "紐付け4";
		columns[6] = "紐付け5";
		columns[7] = "紐付け6";
		columns[8] = "紐付け7";
		columns[9] = "紐付け8";
		columns[10] = "紐付け9";
		columns[11] = "紐付け10";

//		newRow = Goods.getNewRow();

//		System.out.println("");////////////////////////////////////////////
//		System.out.println("newRow = " + newRow);//////////////////////////
//		System.out.println("");////////////////////////////////////////////

		rule = new String[12];

		rule[0] = "varchar(20) DEFAULT ''";
		rule[1] = "int DEFAULT 0";
		rule[2] = "varchar(20) DEFAULT ''";
		rule[3] = "varchar(20) DEFAULT ''";
		rule[4] = "varchar(20) DEFAULT ''";
		rule[5] = "varchar(20) DEFAULT ''";
		rule[6] = "varchar(20) DEFAULT ''";
		rule[7] = "varchar(20) DEFAULT ''";
		rule[8] = "varchar(20) DEFAULT ''";
		rule[9] = "varchar(20) DEFAULT ''";
		rule[10] = "varchar(20) DEFAULT ''";
		rule[11] = "varchar(20) DEFAULT ''";

		ColumnRule = new String[2][columns.length];
		ColumnRule[0] = columns;
		ColumnRule[1] = rule;

		selectName ="";

		numberName = "番号";

		ascColumn = numberName;

		ascData = new String[]{ "昇順", "降順" };

		asc = " ( " + ascColumn + " = " + ascData[0] + " )";

		  // DB作成( DBファイル名[アドレス] , テーブル名 , 行名&定義[二次元配列で] )
		dbc = new DerbyC(uri, tableName, ColumnRule, ascColumn, numberName);
	}

//	void branch(Object[] phraseSet) {
//
//		System.out.println("");////////////////////////////////////
//		System.out.println("branch(Object[] phraseSet) します");///
//		System.out.println("");////////////////////////////////////
//
//		for (int i = 0; i < phraseSet.length; i++) {
//
//			Object[] sortArray = arraySort(phraseSet, i);
//
//			if (size(wherePhrase(phraseSet[i])) == 0) {
//
//				dataSet(sortArray);
//
//			} else {
//
//				//phraseSet[i]に利益度をプラス
//				System.out.println("");////////////////////////////////////
//				System.out.println(phraseSet[i] + " の利益度を + (" + Main.getLp() + ") します");///
//				System.out.println("");////////////////////////////////////
//
//				int newGain = (Integer) data(columns[1], wherePhrase(phraseSet[i])) + Main.getLp();
//				update(columns[1],newGain,columns[0], phraseSet[i]);
//
//				//他の会話と紐付けする
//				for (int j = 1; j < sortArray.length; j++) {
//
//					if( search(wherePhrase(phraseSet[i]), sortArray[j]) == false){//紐付けに無い場合
//
//						addTie(phraseSet[i],sortArray[j]);//紐付けの最初に追加
//					}
//				}
//			}
//		}
//	}
//
//	private Object[] arraySort(Object[] sortArray, int firstElement) {
//
//		System.out.println("");/////////////////////////////////////////////////////////
//		System.out.println("arraySort(Object[] sortArray, int firstElement)");//////////
//		System.out.println("sortArray を [" + firstElement + "] 先頭で並べ替えます");///
//		System.out.println("");/////////////////////////////////////////////////////////
//
//		Object[] array = new Object[sortArray.length];
//
//		for (int i = 0; i < array.length; i++) {
//			int j = i + firstElement;
//			if(j > array.length - 1) j = j - sortArray.length;
//			array[i] = sortArray[j];
//		}
//		return array;
//	}
//
//	void dataSet(Object[] phraseSet) {
//
//		Object[] data = new Object[columns.length];/////insertData作成
//		data[0] = phraseSet[0];
//		data[1] = Main.getLp();
//
//		for (int i = 1; i < phraseSet.length; i++) {
//			data[i + 1] =  phraseSet[i];
//		}
//		for (int i = 0; i < data.length; i++) {
//			if (data[i] == null) {
//				data[i] = " ";
//			}
//		}
//
//
//		String da = "";/////////////////////////// da(data表示用文字列)セット
//		for (int i = 0; i < data.length -1; i++) {
//			da = da + ( data[i] + ", " );
//		}
//		da =  da + ( data[data.length-1] );
//
//		System.out.println("");////////////////////////////////////
//		System.out.print(tableName+" に "+ da +" を挿入します");///
//		System.out.println("");////////////////////////////////////
//
//		  // 1行目を挿入
//		insert(data);
//
//		System.out.println("");////////////////////////////////////
//		System.out.println("DBデータ数" + size(""));///////////////
//		System.out.println("");////////////////////////////////////
//	}
//
//	private boolean search(Object whereTargetRow, Object searchData) {
//
//		System.out.println("");////////////////////////////////////////////////////////////
//		System.out.println("search(" + whereTargetRow + ", " + searchData + ") します");///
//		System.out.println("");////////////////////////////////////////////////////////////
//
//		int count = 0;
//
//		for (int i = 4; i < columns.length + 2; i++) {
//
//			if (data(whereTargetRow, i).equals(searchData)) {
//
//				count++;
//			}
//		}
//
//		if (count == 0) {
//			System.out.println("");//////////////////////////////
//			System.out.println(searchData + "は存在しません");///
//			System.out.println("");//////////////////////////////
//			return false;
//		} else {
//			System.out.println("");////////////////////////////
//			System.out.println(searchData + "は存在します");///
//			System.out.println("");////////////////////////////
//			return true;
//		}
//
//	}
//
//	private void addTie(Object tiedPhrase, Object addTieData) {
//
//		System.out.println("");////////////////////////////////////////////////////////
//		System.out.println("addTie(" + tiedPhrase + ", " + addTieData + ") します");///
//		System.out.println("");////////////////////////////////////////////////////////
//
//		Object[] phraseData = selection(columns[0], tiedPhrase)[0];
//
//		if (phraseData.length > 0) {
//
//			for (int i = phraseData.length - 1; i > 3; i--) {
//				phraseData[i] = phraseData[i - 1];
//			}
//			phraseData[3] = addTieData;
//
//			delete(columns[0], tiedPhrase);
//			insert(phraseData);
//		}
//	}
//
//	public void reply(String[] phraseSet) {
//
//		System.out.println("");////////////////////////////////////////////////////////
//		System.out.println("reply(String[] phraseSet) します");///
//		System.out.println("");////////////////////////////////////////////////////////
//
//		textSet = new ArrayList<String>();
//		int likeLine = 10;
//		int gain = 0;
//		String reply;
//
//		for (String phrase : phraseSet) {
//
//			if(size(wherePhrase(phrase)) > 0){
//
//			gain = (Integer) data(columns[1], wherePhrase(phrase));
//
//			}
//
//			if (gain > likeLine) {
//				setHp(getHp() + 1);
//			}
//
//			if (gain < likeLine) {
//				setHp(getHp() - 1);
//			}
//
//			if (gain > 1) {//////////////////////////
//				int tie = randomTie();
//				reply = (String) data(columns[tie], wherePhrase(phrase));
//				if(reply == null) reply = "";
//				textSet.add(reply);
//			}
//		}
//		text = "・・・";
//		for (String phrase : textSet) {
//			text = text + phrase;
//		}
//		text = text + "。";
//	}
//
//	public void feelSet(int point) {
//
//		System.out.println("");////////////////////////////////
//		System.out.println("feelSet(" + point + ") します");///
//		System.out.println("");////////////////////////////////
//
//		System.out.println("");/////////////////////////////////////
//		System.out.println("textSet.size() = " + textSet.size());///
//		System.out.println("");/////////////////////////////////////
//
//		for (String phrase : textSet) {
//			int newGain = (Integer) data(columns[1], wherePhrase(phrase)) + point;
//			update(columns[1],newGain,columns[0], phrase);
//		}
//	}
//
//	private int getHp() {
//		return Main.getParty()[1].getHp();
//	}
//
//	private void setHp(int i) {
//		Main.getParty()[1].setHp(i);
//	}
//
//	private int randomTie() {
//		// TODO 自動生成されたメソッド・スタブ
//		int tie = 2;
//		int random = 0;
//		while (random == 0 && tie < 12) {
//			int a = 15;
//			int b = 10;
//			random = new java.util.Random().nextInt(a) / b; ///// a 回の内 b 回の確率で 0 になる
//			if (random == 0) {
//				tie++;
//			}
//		}
//		return tie;
//	}

	private String where(Object columnName, Object data) {

		String r;

		if(isNumber(data) == true){
			r = "WHERE " + columnName + " = " + data;
		}else{
			r = "WHERE " + columnName + " = '" + data + "'";
		}
		return r;
	}

	private String wherePhrase(Object data) {

		Object columnName = columns[0];

		String r;

		if(isNumber(data) == true){
			r = "WHERE " + columnName + " = " + data;
		}else{
			r = "WHERE " + columnName + " = '" + data + "'";
		}
		return r;
	}

	private String whereGain(Object data) {

		Object columnName = columns[1];

		String r;

		if(isNumber(data) == true){
			r = "WHERE " + columnName + " = " + data;
		}else{
			r = "WHERE " + columnName + " = '" + data + "'";
		}
		return r;
	}

	public static void insert(Object[] nR) {

		DerbyC.insert(nR);

//		initial();

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

	public static Object data(String selectColumn, String selectRowSql){

		return DerbyC.data(selectColumn, selectRowSql);
	}

	public static Object[][] dataList(String setSql) {

		Object[][] r = DerbyC.dataList(setSql);
		return r;
	}

	public static Object[][] dataList() {

		Object[][] dataList =  DerbyC.dataList();

		return dataList;
	}

	public static Object[][] selection(Object selectColumn, Object selectData) {

		return DerbyC.selection(selectColumn, selectData);
	}

	public static void delete(Object columnName, Object data) {
		DerbyC.delete("",columnName, data);
	}

//	public static void delete() {

//		DerbyC.delete(DerbyC.sqlSection(), Monitor.selectRow() + 1);
//
//		initial();
//
//	}
//
//	public static  Object[][] select() {
//
////		selectData = DerbyC.dataList("");//////////serect用データ
//
//		Object[][] r = null;
//
//		while (r == null) {
//
//			String columnName = (String) Monitor.selectD("何を選びますか？",columns);
//			if(columnName == null){
//				selectName ="";
//				DerbyC.setWhereColumnData(selectName);
//				DerbyC.sqlSection();
//				dataList(numberName);
//				Monitor.display.setText(listName());
//				break;
//			}
//
//			Object[] itemData = distinctList(columnName," ORDER BY " + columnName);
//
//			Object dataName = Monitor.selectD("どれを選びますか？",itemData);
//			if(dataName == null){
//				selectName ="";
//				DerbyC.setWhereColumnData(selectName);
//				DerbyC.sqlSection();
//				dataList(numberName);
//				Monitor.display.setText(listName());
//				break;
//			}
//
//			selectName = (columnName + " [ " + dataName + " ] 一覧");
//
//			Monitor.display.setText(listName());
//
//			r = DerbyC.selection(columnName,dataName);
//
//			dataList();
//
//		}
//		return r;
//
//	}

//	public static Object[][] sort() {
//		Object[][] r = null;
//
//		while (r == null) {
//
//			Object columnName = Monitor.selectD("どの行でソートしますか？", columns);
//			if (columnName == null) {
//				orderBy = (" ORDER BY " + numberName);
//				DerbyC.setOrderAscColumn(orderBy);
//				DerbyC.sqlSection();
//				dataList();
//				break;
//			}
//
//			Object ascName = Monitor.selectD("どの順番でソートしますか？", ascData);
//			if (ascName == null) {
//				orderBy = (" ORDER BY " + numberName);
//				DerbyC.setOrderAscColumn(orderBy);
//				DerbyC.sqlSection();
//				dataList();
//				break;
//			}
//
//			if (ascName.equals(ascData[0])) {/////昇順の場合
//
//				ascColumn = (String) columnName;
//				orderBy = (" ORDER BY " + ascColumn);
//
//			}
//
//			if (ascName.equals(ascData[1])) {/////降順の場合
//
//				ascColumn = (String) columnName;
//				orderBy = (" ORDER BY " + ascColumn + " DESC");
//			}
//
//			DerbyC.setOrderAscColumn(orderBy);
//			DerbyC.sqlSection();
//
//			System.out.println("");//////////////////////////////////////////////
//			System.out.println("DerbyC.sqlSection ＝" + DerbyC.sqlSection());////
//			System.out.println("");//////////////////////////////////////////////
//
//			asc = (" ( " + columnName + " = " + ascName + " )");
//			Monitor.display.setText(listName());
//
//			r = dataList();
//
//		}
//		return r;
//	}


	static void initial(String setSqlSection) {

		System.out.println("");///////////////////////////////////////////////////////////
		System.out.println("データベース内のデータを"+setSqlSection+"の結果で書き換えます");///////
		System.out.println("");///////////////////////////////////////////////////////////

		Object[][] sectionAllData = DerbyC.dataList(setSqlSection);/////引数setSqlSectionに従い全データ取り出し

		DerbyC.reDataTable(tableName,sectionAllData);////////////////////データベース内容書き換え

	}

	static void initial() {

		System.out.println("");///////////////////////////////////////////////////////////
		System.out.println("データベース内のデータを計算し直してから書き換えます");///////
		System.out.println("");///////////////////////////////////////////////////////////

		Object[][] ascAllData = DerbyC.dataList(" ORDER BY " + columns[5]);/////全データ取り出し(日付昇順)

		Object[][] ascAllDataCalculated = calculateData(ascAllData);////////////////合計値再計算

		DerbyC.reDataTable(tableName,ascAllDataCalculated);////////////////////データベース内容書き換え

	}


	private static Object[][] calculateData(Object[][] dataList) {

		System.out.println("");///////////////////////////////////////////////////////////
		System.out.println("Object[][] dataListを計算し直します");///////
		System.out.println("");///////////////////////////////////////////////////////////

		int plus = 0;
		int minus = 0;
		int total = 0;

		for (int j = 0; j < dataList.length; j++) {
			plus = plus + (Integer)dataList[j][1];
			System.out.println("plus 合計 = " + plus);///////
			minus = minus + (Integer)dataList[j][2];
			System.out.println("minus 合計 = " + minus);///////
			total = (plus - minus);
			dataList[j][3] = (total);
			System.out.println("total = " + dataList[j][3]);///////

		}
		return dataList;
	}

	static void update(Object setColumnAndData, Object setWhere){
		DerbyC.update(setColumnAndData, setWhere);
	}

	static void update(Object setColumn, Object setData, Object whereColumn, Object whereData){
		DerbyC.update(setColumn, setData, whereColumn, whereData);
	}

	public static Object[] distinctList(String columnName, String string) {

		Object[][] selectData = DerbyC.distinctList(columnName," ORDER BY " + columnName);

		int columnI = 0;//列番号
		  /////選んだ列が何番目か判定し、列番号(columnI)に代入
		for (int i = 0; i < selectData[0].length; i++) {
			if(columnName.equals(columns[i]))columnI=i;
		}
		Object[] itemData = new Object[selectData.length];/////選択用データ配列
		/////選んだ列にあるデータを全部、選択用データ配列に代入
		for (int j = 0; j < itemData.length; j++) {
			itemData[j] = selectData[j][columnI];
		}
		return itemData;

	}

	static String listName() {

		listName = (selectName + asc );
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

	public void setText(String text) {
		Talk.text = text;
	}

	public String getText() {
		return text;
	}

}


