package iwamih31.GroupHomeRecord;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;
import java.sql.Statement;

public class DerbyC{

	private static String schemaName;
	private static String uri;
	private static Object tableN;
	private static Connection conn;
	private static Statement st;
	private static PreparedStatement ps;
	private static String num;
	private static String columnRule;
	private static String sqlSection;
	private static String[] c;
	private static String[] r;
	private static String orderAscColumn;
	private static String whereColumnData;

//	private static String column;


	DerbyC(String setUri, Object name, String[][] setColumnAndRule, String ascColumn, String numberName) {

		System.out.println("");////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		System.out.println("new DerbyC(" + setUri + ", " + name + ", setColumnAndRule[][], " + ascColumn + ", " + numberName + ") します");///
		System.out.println("");////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

		// 接続用のURIを用意する(必要に応じて認証指示user/passwordを付ける)
		schemaName = setUri;
		uri = ("jdbc:derby:"+setUri+";create=true");//⇒(ここは、使用するDBによって変更する)
		tableN = name;
		num = numberName;///////////////////////////////////自動発番列行の名前
		orderAscColumn = (" ORDER BY " + ascColumn);
		whereColumnData = ("");
		sqlSection = sqlSection();

		c = setColumnAndRule[0];
		r = setColumnAndRule[1];

		show2DArrayA(setColumnAndRule);
		System.out.println("");//////////////////////////////
		show2DArrayB(setColumnAndRule);

		String number = (num + " int generated always as identity (start with 1,increment by 1)");
		String column = ("");

		for (int i = 0; i < c.length; i++) {

		column = column + ( c[i] + " " + r[i] + ",");

		}

//		String lastColumn = ( c[c.length-1] + " " + r[c.length-1] );

		columnRule =  column + number;

		System.out.println("");/////////////////////////////
		System.out.println("columnRule = " + columnRule);///
		System.out.println("");/////////////////////////////

		System.out.println("");///////////////////////////////////////////
		System.out.println("テーブル" + name + "が無ければ作成します");///
		System.out.println("");///////////////////////////////////////////

		if (tableCheck(name) == false){

			System.out.println("");///////////////////////////////////////////
			System.out.println("テーブル" + name + "が無いので作成します");///
			System.out.println("");///////////////////////////////////////////

			createTable(name);

			System.out.println("");///////////////////////////////////////
			System.out.println("テーブル " + name + " を作成しました");///
			System.out.println("");///////////////////////////////////////

		}else{

			System.out.println("");//////////////////////////////////////
			System.out.println("テーブル" + name + "を使用出来ます");////
			System.out.println("");//////////////////////////////////////
		}
		
		System.out.println("");/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		System.out.println("new DerbyC(" + setUri + ", " + name + ", setColumnAndRule[][], " + ascColumn + ", " + numberName + ") しました");///
		System.out.println("");/////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	}

	static void createTable(Object name) {

		createTable(name, columnRule);

	}

	static void createTable(Object name, String setColumnRule) {

		System.out.println("");////////////////////////////////////////////////////////
		System.out.println("createTable(" + name + ", String setColumnRule)します");///
		System.out.println("");////////////////////////////////////////////////////////

		if (tableCheck(name) == false) {

			System.out.println("");///////////////////////////////
			System.out.println(name + " を作成します");///////////
			System.out.println("");///////////////////////////////

			try {

				// DriverManagerクラスのメソッドで接続する
				conn = DriverManager.getConnection(uri);

				// SQL送信用インスタンスの作成
				st = conn.createStatement();

				String sql = ("create table " + name + "(" + setColumnRule + ")");

				System.out.println("");///////////////////////////////////////////
				System.out.println( sql + " を行います");/////////////////////////
				System.out.println("");///////////////////////////////////////////

				st.executeUpdate(sql);

				// shutdown();

				// 後始末(インスタンスの正常クローズ)
				st.close();
				conn.close();

				System.out.println("");///////////////////////////////////////////////////////////////////////////////////////
				System.out.println("     createTable(" + name + ", String setColumnRule)用インスタンスをクローズしました");///
				System.out.println("");///////////////////////////////////////////////////////////////////////////////////////

			} catch (SQLException e) {
				e.getMessage();
				e.printStackTrace();

				if (size(sqlSection()) > 0) {
					// すでにあるので作らない
					System.out.println("");////////////////////////////////////////////////////////
					System.out.println("  [ エラー ] テーブル " + name + " はすでに存在します");///
					System.out.println("");////////////////////////////////////////////////////////
				} else {
					System.out.println("");////////////////////////////////////////////////////////////////////////////////
					System.out.println("  createTable(" + name + ", String setColumnRule)でエラー。テーブルを作ります");///
					System.out.println("");////////////////////////////////////////////////////////////////////////////////

					createTable(name);
				}
			}
		}
	}


	static boolean tableCheck(Object name) {

		System.out.println("");////////////////////////////////////
		System.out.println("DerbyC.tableCheck("+name+")します");///
		System.out.println("");////////////////////////////////////

		boolean tableCount = false;

		try {

			// DriverManagerクラスのメソッドで接続する
			conn = DriverManager.getConnection(uri);

			// SQL送信用インスタンスの作成
			st = conn.createStatement();

			st.executeQuery("SELECT  *  FROM " + name );

			// shutdown();

			// 後始末(インスタンスの正常クローズ)
			st.close();
			conn.close();			
			
			System.out.println("");////////////////////////////////////////////////
			System.out.println("  " + name + " は存在します return true ");///
			System.out.println("");////////////////////////////////////////////////

			System.out.println("");///////////////////////////////////////////////////////////////////
			System.out.println("     DerbyC.tableCheck("+name+")用インスタンスをクローズしました");///
			System.out.println("");///////////////////////////////////////////////////////////////////

			tableCount = true;

		} catch (SQLSyntaxErrorException e) {

			System.out.println("");///////////////////////////////////////////////
			System.out.println("  " + name+" は存在しません return false");///
			System.out.println("");///////////////////////////////////////////////

			tableCount = false;

		} catch (SQLException e) {
			e.getMessage();
			e.printStackTrace();

		}
		return tableCount;
	}


	private void shutdown() throws SQLException {
		DriverManager.getConnection("jdbc:derby:;shutdown=true");
	}

	public static void insert(Object[] setRowData) {
		insert(tableN,setRowData);
	}

	public static void insert(Object setTableName, Object[] setRowData) {

		String valuesData = null;

		System.out.println("");///////////////////////////////////////////////////////////
		System.out.println("insert(" + setTableName + ", Object[] setRowData) します");///
		System.out.println("");///////////////////////////////////////////////////////////

		int afterSize = size(setTableName, "") + 1;

		System.out.println("");////////////////////////////////////////////////////////////////////////////////
		System.out.println("  insert(" + setTableName + ", Object[] setRowData)後の総行数 ＝ " + afterSize);///
		System.out.println("");////////////////////////////////////////////////////////////////////////////////

//		Object[] rowData = setRowData;

		for (int i = 0; i < setRowData.length; i++) {
			if (setRowData[i] == null) {
				setRowData[i] = "default";
			} else {
				if (isNum(setRowData[i]) == false) {
					setRowData[i] = ("'" + setRowData[i] + "'");
				}
			}
		}
		System.out.println(num + " = default");
		
		Object[] columns = columnsAll(setTableName);
		
		int columnsLength = columns.length - 1;//自動采番行を引く
		
		System.out.println("");///////////////////////////////////////////////////
		System.out.println("setRowData.length = "+ setRowData.length +" です");///
		System.out.println("");///////////////////////////////////////////////////

		if (setRowData.length == columnsLength) {////////////////番号列指定無しの場合
			
			System.out.println("");/////////////////////////////////////
			System.out.println("  ["+ num +"] は自動で采番されます");///
			System.out.println("");/////////////////////////////////////

			for (int i = 0; i < columnsLength; i++) {
				System.out.println(columns[i] + " = " + setRowData[i]);///////////////////(列名 = データ)
			}
			System.out.println(columns[columnsLength] + " = " + "default");//[自動采番行] = "default"
			System.out.println("");//////////////////////////////////////////////////////////

			// values セット
			valuesData = "";
			for (int i = 0; i < setRowData.length; i++) {
				valuesData = valuesData + (setRowData[i] + ", ");
			}
			valuesData = valuesData + "default";

		} else {////////////////////////////////////////番号列指定有り([]が１つ多い)の場合

			int many = setRowData.length - columnsLength;

			if(many == 1){
				System.out.println("");/////////////////////////////////////
				System.out.println("  ["+ num +"] は自動で采番されます");///
				System.out.println("");/////////////////////////////////////
			}else{
				System.out.println("");///////////////////////////////////////
				System.out.println("  列数が指定より" + many + "多いです");///
				System.out.println("");///////////////////////////////////////
			}
			for (int i = 0; i < columnsLength; i++) {
				System.out.println(columns[i] + " = " + setRowData[i]);////////////////(列名 = データ)
			}
			// values セット
			valuesData = ("");
			for (int i = 0; i < setRowData.length-1 ; i++) {/////////rowData[最後]を無視する
				valuesData = valuesData + (setRowData[i] + ", ");
			}
			valuesData = valuesData + "default";
		}

		String sql = "insert into " + setTableName + " values(" + valuesData + ")";

		System.out.println("");//////////////////////////////////////////
		System.out.println(setTableName + " に (" + valuesData + ")");///
		System.out.println("を insert します");//////////////////////////
		System.out.println("");//////////////////////////////////////////

		update(sql);

		int s = size(setTableName, "");
		System.out.println("");///////////////////////////////////////////////
		System.out.println("  テーブル" + setTableName + " の総行数 = " + s);//////
		System.out.println("");///////////////////////////////////////////////

		System.out.println("");//////////////////////////////////////////
		System.out.println(setTableName + " に (" + valuesData + ")");///
		System.out.println("を insert しました");//////////////////////////
		System.out.println("");//////////////////////////////////////////
	}

	static void update(String setSql) {
	
		System.out.println("");/////////////////////////////
		System.out.println("update(String setSql)");////////
		System.out.println("");/////////////////////////////
		System.out.println(" setSql ＜ "+setSql + " ＞");///
		System.out.println(" を 実行 します");//////////////
		System.out.println("");/////////////////////////////
	
		try {
			conn = DriverManager.getConnection(uri);
			// SQL送信用インスタンスの作成
			st = conn.createStatement();
			// SQL送信
			st.executeUpdate(setSql);
	
			System.out.println("");/////////////////////
			System.out.println("＜" + setSql + "＞");///
			System.out.println("で update しました");///
			System.out.println("");/////////////////////
	
			// 後始末(インスタンスの正常クローズ)
			st.close();
			conn.close();
	
			System.out.println("");/////////////////////////////////////
			System.out.println("  update(" + setSql + ")");/////////////
			System.out.println("  用インスタンスをクローズしました");///
			System.out.println("");/////////////////////////////////////
	
		} catch (SQLException e) {
			e.getMessage();
			e.printStackTrace();
		}
	
	}

	static void update(Object setColumnAndData, Object setWhere){
		update(tableN, setColumnAndData, setWhere);
	}

	static void update(Object setTableName, Object setColumnAndData, Object setWhere){

		String sql = " UPDATE " + setTableName + " SET " + setColumnAndData + " WHERE " + setWhere;
		update(sql);
	}

	static void update(Object setColumn, Object setData, Object whereClumn, Object whereData){
		update(tableN, setColumn, setData, whereClumn, whereData);
	}

	static void update(Object setTableName, Object setColumn, Object setData, Object whereA, Object whereB){

//		putCharSymbol(setColumn);

		if(isNum(setData)==false) setData = "'"+setData+"'";
		if(isNum(whereA)==false && isNum(whereB)==false) whereB = "'"+whereB+"'";
		if(whereA.equals("")) whereA = "''";
		if(whereB.equals("")) whereB = "''";

		String sql = " UPDATE " + setTableName + " SET " + setColumn + " = " + setData + " WHERE " + whereA + " = " + whereB;
		update(sql);
	}
	
	static void update(Object setTableName, Object setColumn, Object setData, Object[] whereColumn, Object[] whereData){
		
//		putCharSymbol(setColumn);

		String sql = " UPDATE " + setTableName + " SET " + is(setColumn, setData) + andWhere(whereColumn, whereData);
		
		update(sql);
	}
	
	static void update(Object setTableName, Object[] setColumn, Object[] setData, Object[] whereColumn, Object[] whereData){
		
//		putCharSymbol(setColumn);

		String sql = " UPDATE " + setTableName + commaSet(setColumn, setData) + andWhere(whereColumn, whereData);
		
		update(sql);
	}

	static int sumColumn(Object sumColumn){
		int sum = sumColumn(tableN, sumColumn, "");
		return sum;
	}

	static int sumColumn(Object setTableName, Object sumColumn) {
		int sum = sumColumn(setTableName, sumColumn, "");
		return sum;
	}

	static int sumColumn(Object setTableName, Object sumColumn, Object section) {

		System.out.println("");/////////////////////////////////////////////////////////////////////////////
		System.out.println("sumColumn(" + setTableName + ", " + sumColumn + ", " + section + ") します");///
		System.out.println("");/////////////////////////////////////////////////////////////////////////////

		String setSql = "SELECT SUM(" + sumColumn + ") FROM " + setTableName + " " + section;

		System.out.println("");///////////////////////////////////////
		System.out.println("  ＜" + setSql + "＞ を 実行 します");//////////////
		System.out.println("");///////////////////////////////////////

		if (setTableName.equals("")){
			setTableName = tableN;
		}

		ResultSet sRs = sqlScrollResultSetRead(setSql);

		int r = 0;

		try {

			sRs.last();
			r = sRs.getInt(1);

			System.out.println("");////////////////////////////////////
			System.out.println("sumColumn(" + setTableName + ", " + sumColumn + ", " + section + ") ＝ " + r);////////
			System.out.println("");////////////////////////////////////

			// shutdown();

			// 後始末(インスタンスの正常クローズ)
			st.close();
			conn.close();

			System.out.println("");/////////////////////////////////////////////////////////
			System.out.println("sumColumn(" + setTableName + ", " + sumColumn + ", " + section + ")用インスタンスをクローズしました");///////////////////
			System.out.println("");/////////////////////////////////////////////////////////

		} catch (SQLException e) {
			e.getMessage();
			e.printStackTrace();
			System.out.println("");/////////////////////////////////////////////////////////
			System.out.println("sumColumn(" + setTableName + ", " + sumColumn + ", " + section + ")出来ないので[" + r + "]を返します");//////////////////
			System.out.println("");/////////////////////////////////////////////////////////
		}

		return r;
	}

	static int sumColumn(Object setTableName, Object sumColumn, Object whereA, Object whereB) {

		String where = "  WHERE " + whereA + " = " + whereB;
		int sum = sumColumn(setTableName, sumColumn, where);
		return sum;

	}

	static Object maxColumn(Object selectColumn){
		Object max = maxColumn(tableN, selectColumn, "");
		return max;
	}

	static Object maxColumn(Object setTableName, Object selectColumn) {
		Object max = maxColumn(setTableName, selectColumn, "");
		return max;
	}

	static Object maxColumn(Object setTableName, Object selectColumn, Object section) {

		if (setTableName.equals("")) {
			setTableName = tableN;
		}

		System.out.println("");///////////////////////////////////////////////////////////////////////////////////////////
		System.out.println("maxColumn(" + setTableName + ", " + selectColumn + ", " + section + ") します");//////////////
		System.out.println("");///////////////////////////////////////////////////////////////////////////////////////////
		System.out.println("テーブル" + setTableName + " の " + selectColumn + "列の" + section + "の最大値を返します");//////////////
		System.out.println("");///////////////////////////////////////////////////////////////////////////////////////////

		String setSql = "SELECT MAX(" + selectColumn + ") FROM " + setTableName + " " + section;


		ResultSet sRs = sqlScrollResultSetRead(setSql);

		Object max = null;

		try {

			sRs.last();
			max = sRs.getObject(1);

			System.out.println("");////////////////////////////////////
			System.out.println("maxColumn(" + setTableName + ", " + selectColumn + ", " + section + ") ＝ " + max);////////
			System.out.println("");////////////////////////////////////

			// shutdown();

			// 後始末(インスタンスの正常クローズ)
			st.close();
			conn.close();

			System.out.println("");/////////////////////////////////////////////////////////
			System.out.println("maxColumn(" + setTableName + ", " + selectColumn + ", " + section + ")用インスタンスをクローズしました");///////////////////
			System.out.println("");/////////////////////////////////////////////////////////

		} catch (SQLException e) {
			e.getMessage();
			e.printStackTrace();
			System.out.println("");/////////////////////////////////////////////////////////
			System.out.println("maxColumn(" + setTableName + ", " + selectColumn + ", " + section + ")出来ないので[" + r + "]を返します");//////////////////
			System.out.println("");/////////////////////////////////////////////////////////
		}

		return max;
	}

	static Object maxColumn(Object setTableName, Object selectColumn, Object whereA, Object whereB) {

		String where = "  WHERE " + whereA + " = " + whereB;
		Object max = maxColumn(setTableName, selectColumn, where);
		return max;

	}

	public static void dropTable(String tableName) {

		String sql = "DROP TABLE " + tableName;

		System.out.println("");//////////////////////////////////////////
		System.out.println("テーブル " + tableName + " を削除します");///
		System.out.println("");//////////////////////////////////////////

		try {
			conn = DriverManager.getConnection(uri);
			// SQL送信用インスタンスの作成
			st = conn.createStatement();
			// SQL送信
			st.executeUpdate(sql);

			System.out.println("");/////////////////////////////////////////////
			System.out.println("  テーブル " + tableName + "を削除しました");///
			System.out.println("");/////////////////////////////////////////////

			// shutdown();

			// 後始末(インスタンスの正常クローズ)
//			ps.close();
			st.close();
			conn.close();

			System.out.println("");/////////////////////////////////////////////////////////////////
			System.out.println("  dropTable(" + tableName + ")用インスタンスをクローズしました");///
			System.out.println("");/////////////////////////////////////////////////////////////////

		} catch (SQLException e) {

			e.getMessage();
			e.printStackTrace();

		}

	}

public static void reNameTable(String tableName,String newTableName) {

		String sql = "RENAME TABLE " + tableName + " TO " + newTableName;

		System.out.println("");///////////////////////////////////////////////////////////
		System.out.println(tableName + " の名前を " + newTableName + " に変更します");////
		System.out.println("");///////////////////////////////////////////////////////////

		try {
			conn = DriverManager.getConnection(uri);
			// SQL送信用インスタンスの作成
			st = conn.createStatement();
			// SQL送信
			st.executeUpdate(sql);

			System.out.println("");/////////////////////////////////////////////////////////////
			System.out.println(tableName + " の名前を " + newTableName + " に変更しました");////
			System.out.println("");/////////////////////////////////////////////////////////////

			// shutdown();

			// 後始末(インスタンスの正常クローズ)
//			ps.close();
			st.close();
			conn.close();

			System.out.println("");//////////////////////////////////////////////////////////////////////////////////////////
			System.out.println("     reNameTable("+tableName + ", " + newTableName + ")用インスタンスをクローズしました");///
			System.out.println("");//////////////////////////////////////////////////////////////////////////////////////////

		} catch (SQLException e) {

			e.getMessage();
			e.printStackTrace();

		}

	}

	public static ResultSet systables(String tableName) {

		String sql = "select * from sys.systables where tablename='"+ tableName + "'" ;

		System.out.println("");////////////////////////////////////
		System.out.println(tableName + "を削除します");////////////
		System.out.println("");////////////////////////////////////

		ResultSet rs = null;
		try {
			conn = DriverManager.getConnection(uri);
			// SQL送信用インスタンスの作成
			st = conn.createStatement();
			// SQL送信
			rs = st.executeQuery(sql);

			System.out.println("");////////////////////////////////////
			System.out.println(tableName + "を削除しました");//////////
			System.out.println("");////////////////////////////////////

			// shutdown();

			// 後始末(インスタンスの正常クローズ)
//			ps.close();
			st.close();
			conn.close();

			System.out.println("");///////////////////////////////////////////////////////////////
			System.out.println("dropTable(" + tableName + ")用インスタンスをクローズしました");///
			System.out.println("");///////////////////////////////////////////////////////////////

		} catch (SQLException e) {

			e.getMessage();
			e.printStackTrace();

		}
		return rs;
	}

	private static boolean isNum(Object p) {
	    try {
	        Integer.parseInt(String.valueOf(p));
	        return true;
	    } catch (NumberFormatException e) {
	        return false;
	    }
	}

	public static int size() {

		return size(tableN,"");
	}

	public static int size(String setSqlSection) {

		return size(tableN,setSqlSection);
	}


	public static int size(Object tableName, String setSqlSection) {

		System.out.println("");/////////////////////////////////////////////////////////
		System.out.println("size(" + tableName +", "+ setSqlSection + ")します" );///
		System.out.println("");/////////////////////////////////////////////////////////

		String setSql = "SELECT  *  FROM " + tableName + " " + setSqlSection;

		if (tableName.equals("")){
			setSql = setSqlSection;
		}

		ResultSet sRs = sqlScrollResultSetRead(setSql);

		int r = 0;

		try {

			sRs.last();
			r = sRs.getRow();

			System.out.println("");////////////////////////////////////
			System.out.println("size(" + setSql + ") = " + r);////////
			System.out.println("");////////////////////////////////////

			// shutdown();

			// 後始末(インスタンスの正常クローズ)
			st.close();
			conn.close();

			System.out.println("");/////////////////////////////////////////////////////////
			System.out.println("size()用インスタンスをクローズしました");///////////////////
			System.out.println("");/////////////////////////////////////////////////////////

		} catch (SQLException e) {
			e.getMessage();
			e.printStackTrace();

			r = 0;

			System.out.println("");/////////////////////////////////////////////////////////
			System.out.println("size()出来ないので[" + r + "]を返します");//////////////////
			System.out.println("");/////////////////////////////////////////////////////////
		}

		return r;

	}

	public static int size(Object selectName, Object whereColumn, Object whereData) {

		if (isNum(whereData) == false) {
			whereData = putCharSymbol(whereData);
		}
		String where = "  WHERE " + whereColumn + " = " + whereData;
		return size(selectName, where);
	}

	public static int sizeLike(Object selectName, Object whereColumn, Object whereData) {

		if (isNum(whereData) == false) {
			whereData = putCharSymbol("%" + whereData + "%");
		}
		String where = "  WHERE " + whereColumn + " LIKE " + whereData;
		return size(selectName, where);
	}

	private static ResultSet scrollResultSetRead() {

		//	＊クローズ処理を行わないので使用先でクローズが必要＊

		String sql = "SELECT  *  FROM " + tableN  + sqlSection;

//		System.out.println("");/////////////////////////////////////////////////////////
		System.out.println("     "+sql+" で scrollResultSetRead() します");/////////////
		System.out.println("");/////////////////////////////////////////////////////////


		ResultSet sRs = null;

		try {
			conn = DriverManager.getConnection(uri);
			st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			sRs = st.executeQuery(sql);

			// 後始末(インスタンスの正常クローズ)＊しない
//			st.close();
//			conn.close();

			/////////////////////////////////////////////////////////////////////////////////
			System.out.println("     ＊ st と、conn  インスタンスはクローズしません ＊");////
			System.out.println("");//////////////////////////////////////////////////////////

		} catch (SQLException e) {
			e.getMessage();
			e.printStackTrace();
			System.out.println("");/////////////////////////////////////////////////////////
			System.out.println("scrollResultSetRead()出来ないので[null]を返します");////////
			System.out.println("");/////////////////////////////////////////////////////////
		}
		return sRs;
	}

	private static ResultSet scrollResultSetRead(String section) {

		//	＊クローズ処理を行わないので使用先でクローズが必要＊

//		System.out.println("");////////////////////////////////////////////////
		System.out.println("     scrollResultSetRead(”" + section + "”)します");///
		System.out.println("");////////////////////////////////////////////////

		String sql = "SELECT * FROM " + tableN + section;

		return sqlScrollResultSetRead(sql);
	}

	private static ResultSet scrollResultSetRead(Object setTableName, String section) {

			//	＊クローズ処理を行わないので使用先でクローズが必要＊

			if (setTableName.equals("")) setTableName = tableN;

	//		System.out.println("");///////////////////////////////////////////////////////////////////
			System.out.println("     scrollResultSetRead(" + setTableName + ", " + section + ")します");///
			System.out.println("");///////////////////////////////////////////////////////////////////

			String sql = "SELECT * FROM " + setTableName + " " + section;

			return sqlScrollResultSetRead(sql);
		}

	private static ResultSet sqlScrollResultSetRead(String setSql) {

		//	＊クローズ処理を行わないので使用先でクローズが必要＊

		String text = setSql.equals("") ? "””" : setSql;

//		System.out.println("");////////////////////////////////////////////////////////////
		System.out.println("     sqlScrollResultSetRead(" + text + ") します");///////
		System.out.println("");////////////////////////////////////////////////////////////

		String sql =  setSql;
		ResultSet sRs = null;

		try {
			conn = DriverManager.getConnection(uri);
			st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			sRs = st.executeQuery(sql);

			// 後始末(インスタンスの正常クローズ)＊しない
//			st.close();
//			conn.close();

			/////////////////////////////////////////////////////////////////////////////////
			System.out.println("     ＊ st と、conn  インスタンスはクローズしません ＊");////
			System.out.println("");//////////////////////////////////////////////////////////

		} catch (SQLException e) {
			e.getMessage();
			e.printStackTrace();
			System.out.println("");//////////////////////////////////////////////////////////////////////////////
			System.out.println("sqlScrollResultSetRead(”" + sql + "”)出来ないので[null]を返します");///////////
			System.out.println("");//////////////////////////////////////////////////////////////////////////////
		}
		return sRs;
	}

	private static ResultSet prepareScrollResultSetRead() {

		//	＊クローズ処理を行わないので使用先でクローズが必要＊

		System.out.println("");/////////////////////////////////////////////////////////
		System.out.println("     prepareScrollResultSetRead()します");//////////////////
		System.out.println("");/////////////////////////////////////////////////////////

		String sql = "SELECT  *  FROM " + tableN  + sqlSection();
		ResultSet sRs = null;

		try {
			conn = DriverManager.getConnection(uri);
			ps = conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
//			ps.setString(1, s);
//			ps.setInt(1, rowNumber);

			sRs = st.executeQuery(sql);

			// 後始末(インスタンスの正常クローズ)＊しない
//			ps.close();
//			conn.close();

			System.out.println("");///////////////////////////////////////////////////////////////////
			System.out.println("     ＊ st と、conn  インスタンスはクローズしません ＊");/////////////
			System.out.println("");///////////////////////////////////////////////////////////////////

		} catch (SQLException e) {
			e.getMessage();
			e.printStackTrace();
			System.out.println("");/////////////////////////////////////////////////////////
			System.out.println("scrollResultSetRead()出来ないので[null]を返します");////////
			System.out.println("");/////////////////////////////////////////////////////////
		}
		return sRs;
	}


	public static Object data(int rowNumber, int columnNumber) {

		System.out.println("");////////////////////////////////////////////////////////////
		System.out.println("data("+rowNumber+","+columnNumber+") を行います");/////////////
		System.out.println("");////////////////////////////////////////////////////////////

		String sql = "SELECT  *  FROM " + tableN  + sqlSection();
		ResultSet sRs = null;
		Object r = null;
		Judg:while (r == null) {

			try {
				conn = DriverManager.getConnection(uri);

				st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);
				sRs = st.executeQuery(sql);

				sRs.absolute(rowNumber);
				r = sRs.getObject(c[columnNumber]);

				System.out.println("");////////////////////////////////////////////////////////////
				System.out.println("データ["+rowNumber+"]["+columnNumber+"] ＝ " + r + "です");////
				System.out.println("");////////////////////////////////////////////////////////////

				// shutdown();

				// 後始末(インスタンスの正常クローズ)
//				ps.close();
				st.close();
				conn.close();

				System.out.println("");////////////////////////////////////////////////
				System.out.println("data(int,int)用インスタンスをクローズしました");///
				System.out.println("");////////////////////////////////////////////////

				break Judg;

			} catch (SQLException e) {
				e.getMessage();
				e.printStackTrace();

				if(size(sqlSection())==0){
					System.out.println("");/////////////////////////////////////
					System.out.println("データが有りません");///////////////////
					System.out.println("");/////////////////////////////////////
					break Judg;
				}else{

//				table();
//				data(rowNumber, columnNumber);

				System.out.println("");/////////////////////////////////////
				System.out.println("sRs＝" + sRs.toString());///////////////
				System.out.println("");/////////////////////////////////////

				System.out.println("");/////////////////////////////////////
				System.out.println("r＝" + r);//////////////////////////////
				System.out.println("");/////////////////////////////////////
				}
			}
		}
		return r;
	}

	public static Object data(int rowNumber, String columnName) {

		System.out.println("");////////////////////////////////////////////////////////////
		System.out.println("data("+rowNumber+","+columnName+") を行います");///////////////
		System.out.println("");////////////////////////////////////////////////////////////

		Object r = null;

		Judg:while (r == null) {

			ResultSet sRs = scrollResultSetRead();

			try {
				sRs.absolute(rowNumber);
				r = sRs.getObject(columnName);

	//			System.out.println("");////////////////////////////////////////////////////////////
				System.out.println("データ["+rowNumber+"]["+columnName+"] ＝ " + r + "です");//////
	//			System.out.println("");////////////////////////////////////////////////////////////

				// shutdown();

				// 後始末(インスタンスの正常クローズ)
				st.close();
				conn.close();

				System.out.println("");////////////////////////////////////////////////////////
				System.out.println("     data(int,String)用インスタンスをクローズしました");///
				System.out.println("");////////////////////////////////////////////////////////

				break Judg;

			} catch (SQLException e) {
				e.getMessage();
				e.printStackTrace();

				if(size(sqlSection())==0){
					System.out.println("");////////////////////////////////
					System.out.println("データが有りません");//////////////
					System.out.println("");////////////////////////////////
					break Judg;
				}else{

//				table();
//				data(rowNumber, columnNumber);

					System.out.println("");/////////////////////////////////////////////////////////////////////////////////
					System.out.println("data("+rowNumber+","+columnName+") ＝ " + r + "もしくはエラーです");////////////////
					System.out.println("");/////////////////////////////////////////////////////////////////////////////////

					System.out.println("");/////////////////////////////////////
					System.out.println("sRs＝" + sRs.toString());///////////////
					System.out.println("");/////////////////////////////////////

					System.out.println("");//////////////////////////////////
					System.out.println("r＝" + r);///////////////////////////
					System.out.println("");//////////////////////////////////
				}
			}
		}
		return r;
	}

	public static Object data(Object selectRowSql, int selectColumnNumber) {

		return data(tableN, selectColumnNumber, selectRowSql);
	}

	public static Object data(String selectColumn, Object selectRowSql) {

		return data(tableN, selectColumn, selectRowSql);
	}

	public static Object data(Object selectTable, String selectColumn, Object selectRowSql) {

		System.out.println("");/////////////////////////////////////////////////////////////////////////////
		System.out.println("data(" + selectTable + ", " + selectColumn + ", " + selectRowSql + ") します");///
		System.out.println("");/////////////////////////////////////////////////////////////////////////////

		String sql = " SELECT * FROM " + selectTable  + " " + selectRowSql;

		Object data = null;

		ResultSet sRs = sqlScrollResultSetRead(sql);

		try {

			sRs.last();
			data = sRs.getObject(selectColumn+"");

			System.out.println("");////////////////////////////////////////////////////////////////////////////////
			System.out.println("data(" + selectTable + ", " + selectColumn + ", " + selectRowSql + ") ＝ " + data);///
			System.out.println("");////////////////////////////////////////////////////////////////////////////////

			// 後始末(インスタンスの正常クローズ)
			st.close();
			conn.close();

			System.out.println("");//////////////////////////////////////////////////
			System.out.println("     data(" + selectTable + ", " + selectColumn + ", " + selectRowSql + ")用インスタンスをクローズしました");////////
			System.out.println("");//////////////////////////////////////////////////

		} catch (SQLException e) {
			e.getMessage();
			e.printStackTrace();
			System.out.println("");///////////////////////////////////////////////////////////
			System.out.println("  data(" + selectTable + ", " + selectColumn + ", " + selectRowSql + ")出来ないので[null]を返します");/////////////////////
			System.out.println("");///////////////////////////////////////////////////////////
		}

		return data;
	}

	public static Object data(Object selectTable, int selectColumnNumber, Object selectRowSql) {

		System.out.println("");/////////////////////////////////////////////////////////////////////////////
		System.out.println("data(" + selectTable + ", " + selectColumnNumber + ", " + selectRowSql + ") します");///
		System.out.println("");/////////////////////////////////////////////////////////////////////////////

		String sql = " SELECT * FROM " + selectTable  + " " + selectRowSql;

		Object data = null;

		ResultSet sRs = sqlScrollResultSetRead(sql);

		try {

			sRs.last();
			data = sRs.getObject(selectColumnNumber);

			System.out.println("");////////////////////////////////////////////////////////////////////////////////
			System.out.println("data(" + selectTable + ", " + selectColumnNumber + ", " + selectRowSql + ") ＝ " + data);///
			System.out.println("");////////////////////////////////////////////////////////////////////////////////

			// 後始末(インスタンスの正常クローズ)
			st.close();
			conn.close();

			System.out.println("");//////////////////////////////////////////////////
			System.out.println("     data(" + selectTable + ", " + selectColumnNumber + ", " + selectRowSql + ")用インスタンスをクローズしました");////////
			System.out.println("");//////////////////////////////////////////////////

		} catch (SQLException e) {
			e.getMessage();
			e.printStackTrace();
			System.out.println("");///////////////////////////////////////////////////////////
			System.out.println("  data(" + selectTable + ", " + selectColumnNumber + ", " + selectRowSql + ")出来ないので[null]を返します");/////////////////////
			System.out.println("");///////////////////////////////////////////////////////////
		}

		return data;
	}

	public static Object data(Object selectTable, Object selectColumn, Object whereColumn, Object whereData) {

		if (isNum(whereData) == false) {
			whereData = putCharSymbol(whereData);
		}

		String where = (" WHERE " + whereColumn + " = " + whereData + " ");

		return data(selectTable, (String) selectColumn, where);

	}
	
	static Object[] columns(Object selectTable) {
		
		System.out.println("");//////////////////////////////////////
		System.out.println("columns(" + selectTable + ") します");///
		System.out.println("");//////////////////////////////////////
		
		Object[] columnsAll = columnsAll(selectTable);
		Object numColumn = columnsAll[columnsAll.length - 1];
		Object[] columnsNotAll = new Object[columnsAll.length - 1];
		int colCount = columnsNotAll.length;
		
		for (int i = 0; i < colCount; i++) {
			columnsNotAll[i] = columnsAll[i];
		}
		
		System.out.println("");//////////////////////////////////////////////////////////////
		System.out.println("(" + selectTable + ") から "+ numColumn +" 行を省いた数 は " + colCount + " です");///
		System.out.println("");//////////////////////////////////////////////////////////////

		return columnsNotAll;
	}
	
	static Object[] columnsAll(Object selectTable) {
		
		System.out.println("");/////////////////////////////////////////
		System.out.println("columnsAll(" + selectTable + ") します");///
		System.out.println("");/////////////////////////////////////////

		String sql = " SELECT * FROM " + selectTable;

		ResultSet sRs = sqlScrollResultSetRead(sql);

		int colCount = 0;
		
		Object[] columns = null;
		
		try {

            colCount = sRs.getMetaData().getColumnCount();
            
            columns = new Object[colCount];
            
            System.out.println("");//////////////////////////////////////////////////////////////
            
            for (int i = 0; i < columns.length; i++) {
            	columns[i] = sRs.getMetaData().getColumnName(i + 1);
            	
				System.out.print("[" + columns[i] + "]");
			}

			System.out.println("");//////////////////////////////////////////////////////////////
			System.out.println("(" + selectTable + ") の 総column数 は " + colCount + "です");///
			System.out.println("");//////////////////////////////////////////////////////////////

			// 後始末(インスタンスの正常クローズ)
			st.close();
			conn.close();

			System.out.println("");///////////////////////////////////////////////////////////////////////
			System.out.println("     columnsAll(" + selectTable + ") 用インスタンスをクローズしました");///
			System.out.println("");///////////////////////////////////////////////////////////////////////

		} catch (SQLException e) {
			e.getMessage();
			e.printStackTrace();
			System.out.println("");////////////////////////////////////////////////////////////////
			System.out.println("  columnsAll(" + selectTable + ") 出来ないので [null] を返します");///
			System.out.println("");////////////////////////////////////////////////////////////////
		}

		return columns;
	}

	public static  int columnCount(Object selectTable) {

		System.out.println("");/////////////////////////////////////////////////////////////////////////////
		System.out.println("columnCount(" + selectTable + ") します");///
		System.out.println("");/////////////////////////////////////////////////////////////////////////////

		String sql = " SELECT * FROM " + selectTable;

		ResultSet sRs = sqlScrollResultSetRead(sql);

		int colCount = 0;
		
		try {

            colCount = sRs.getMetaData().getColumnCount();

			System.out.println("");//////////////////////////////////////////////////////////////
			System.out.println("(" + selectTable + ") の 総column数 は " + colCount + "です");///
			System.out.println("");//////////////////////////////////////////////////////////////

			// 後始末(インスタンスの正常クローズ)
			st.close();
			conn.close();

			System.out.println("");///////////////////////////////////////////////////////////////////////
			System.out.println("     columnCount(" + selectTable + ")用インスタンスをクローズしました");///
			System.out.println("");///////////////////////////////////////////////////////////////////////

		} catch (SQLException e) {
			e.getMessage();
			e.printStackTrace();
			System.out.println("");////////////////////////////////////////////////////////////////
			System.out.println("  columnCount(" + selectTable + ")出来ないので[0]を返します");///
			System.out.println("");////////////////////////////////////////////////////////////////
		}

		return colCount;
	}

	public static Object ascData(String ascColumn, int rowNumber, String columnName) {

		System.out.println("");///////////////////////////////////////////////////////////////////////////////
		System.out.println("ascdata("+ ascColumn + "," + rowNumber + "," + columnName + ") を行います");//////
		System.out.println("");///////////////////////////////////////////////////////////////////////////////
		orderAscColumn =" ORDER BY " + ascColumn;
		String sql = "SELECT * FROM " + tableN + sqlSection();
		ResultSet sRs = null;
		Object r = null;
		Judg:while (r == null) {

			try {
				conn = DriverManager.getConnection(uri);

				st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);
				sRs = st.executeQuery(sql);

				System.out.println("");//////////////////////////////////////////////
				System.out.println("  sql ＝ "+sql+" です");//////////////////////////
				System.out.println("");/////////////////////////////////////////////

				size(sqlSection());

				sRs.absolute(rowNumber);
				r = sRs.getObject(columnName);

				System.out.println("");////////////////////////////////////////////////////////////
				System.out.println("  データ["+rowNumber+"]["+columnName+"] ＝ " + r + "です");//////
				System.out.println("");////////////////////////////////////////////////////////////

				// shutdown();

				// 後始末(インスタンスの正常クローズ)
				st.close();
				conn.close();

				System.out.println("");/////////////////////////////////////////////////////////
				System.out.println("     data(int,String)用インスタンスをクローズしました");/////////
				System.out.println("");/////////////////////////////////////////////////////////

				break Judg;

			} catch (SQLException e) {
				e.getMessage();
				e.printStackTrace();

				if(size(sqlSection())==0){
					System.out.println("");////////////////////////////////////
					System.out.println("  データが有りません");//////////////////
					System.out.println("");////////////////////////////////////
					break Judg;
				}else{

//				table();
//				data(rowNumber, columnNumber);

					System.out.println("");/////////////////////////////////////////////////////////////////////
					System.out.println("  data("+rowNumber+","+columnName+") ＝ " + r + "もしくはエラーです");////
					System.out.println("");/////////////////////////////////////////////////////////////////////

					System.out.println("");////////////////////////////////////
					System.out.println("  sRs＝" + sRs.toString());//////////////
					System.out.println("");////////////////////////////////////

					System.out.println("");/////////////////////////////////
					System.out.println("  r＝" + r);//////////////////////////
					System.out.println("");/////////////////////////////////
				}
			}
		}
		return r;
	}

	public static Object[][] dataListAll() {

		String sql = "SELECT  *  FROM " + tableN;

		return dataList(sql);
	}

	public static Object[][] dataList() {

		String sql = "SELECT  *  FROM " + tableN  + sqlSection;

		System.out.println("");//////////////
		System.out.println("dataList()");////
		System.out.println("");////////////////////////////////////////////////////////////////
		System.out.println(" setSql = ＜ " + sql + " ＞ で dataList(String setSql) します");///
		System.out.println("");////////////////////////////////////////////////////////////////

		return dataList(sql);

	}

	public static Object[][] dataList(String setSql) {

		System.out.println("");//////////////////////////
		System.out.println("dataList(String setSql)");///
		System.out.println("");/////////////////////////////////////////////////////
		System.out.println("  setSql ＜" + setSql + "＞ ");/////////////////////////
		System.out.println("  によって得たデータを二次元配列に代入し、返します");///
		System.out.println("");/////////////////////////////////////////////////////

		Object[][] dataList = null;

		ResultSet sRs = sqlScrollResultSetRead(setSql);

		try {

			sRs.last();

			int rowSize = sRs.getRow();
			ResultSetMetaData rSMD = sRs.getMetaData();
			int columnLength = rSMD.getColumnCount();

//			System.out.println("");//////////////////////////////////////////////////////////////////////
			System.out.println("  rowSize = " + rowSize + "  columnLength = " + columnLength + " です");//
//			System.out.println("");//////////////////////////////////////////////////////////////////////

			dataList = new Object[rowSize][columnLength];

			for (int i = 0; i < rowSize ; i++) {
				System.out.println("");/////////////////////////////
				System.out.print(i + " ");//////////////////////////
				for (int j = 0; j < columnLength; j++) {
					sRs.absolute(i+1);
					dataList[i][j] = sRs.getObject(j+1);
					
					System.out.print("[" + dataList[i][j] + "]");///
				}
			}
			System.out.println("");/////////////////////////////////
			
			// 後始末(インスタンスの正常クローズ)
			st.close();
			conn.close();

			System.out.println("");////////////////////////////////////////
			System.out.println("     dataList(" + setSql + ")");///////////
			System.out.println("     用インスタンスをクローズしました");///
			System.out.println("");////////////////////////////////////////

		} catch (SQLException e) {
			e.getMessage();
			e.printStackTrace();
			System.out.println("");///////////////////////////////////////////////////////////
			System.out.println("  dataList(" + setSql + ")出来ないので[null]を返します");/////////////////////
			System.out.println("");///////////////////////////////////////////////////////////
		}
		return dataList;

	}
	
	public static Object[][] dataList(Object setTableName, String setSqlSection) {
	
		String sql = " SELECT  *  FROM " + setTableName  + setSqlSection;
		return dataList(sql);
	}
	
	public static Object[][] dataList(Object selectColumn, Object tableName, Object whereSql, Object orderBySql) {
		
		String sql = select(selectColumn, tableName, whereSql, orderBySql);
		return dataList(sql);
	}

	public static Object[][] minusNumList(String setSql) {

		System.out.println("");//////////////////////////////
		System.out.println("minusNumList(String setSql)");///
		System.out.println("");//////////////////////////////
		System.out.println("  setSql ＜" + setSql + "＞ ");////////////////////////////////////////
		System.out.println("  によって得たデータから二次元配列に代入し、Num行を省いて返します");///
		System.out.println("");////////////////////////////////////////////////////////////////////

		Object[][] dataList = null;

		ResultSet sRs = sqlScrollResultSetRead(setSql);

		try {

			sRs.last();

			int rowSize = sRs.getRow();
			ResultSetMetaData rSMD = sRs.getMetaData();
			int columnLength = rSMD.getColumnCount() - 1;

//			System.out.println("");//////////////////////////////////////////////////////////////////////
			System.out.println("  rowSize = " + rowSize + "  columnLength = " + columnLength + " です");//
//			System.out.println("");//////////////////////////////////////////////////////////////////////

			dataList = new Object[rowSize][columnLength];

			for (int i = 0; i < rowSize ; i++) {
				System.out.println("");/////////////////////////////
				System.out.print(i + " ");//////////////////////////
				for (int j = 0; j < columnLength; j++) {
					sRs.absolute(i+1);
					dataList[i][j] = sRs.getObject(j+1);
					
					System.out.print("[" + dataList[i][j] + "]");///
				}
			}
			System.out.println("");/////////////////////////////////
			
			// 後始末(インスタンスの正常クローズ)
			st.close();
			conn.close();

			System.out.println("");////////////////////////////////////////
			System.out.println("     minusNumList(" + setSql + ")");///////
			System.out.println("     用インスタンスをクローズしました");///
			System.out.println("");////////////////////////////////////////

		} catch (SQLException e) {
			e.getMessage();
			e.printStackTrace();
			System.out.println("");/////////////////////////////////////////////////////////////
			System.out.println("  minusNumList(" + setSql + ")出来ないので[null]を返します");///
			System.out.println("");/////////////////////////////////////////////////////////////
		}
		return dataList;

	}

	public static Object[][] minusNumList(Object setTableName, String setSqlSection) {
		
		String sql = " SELECT  *  FROM " + setTableName  + setSqlSection;
		
		return minusNumList(sql);
	}

	public static Object[][] ascDataList(String ascColumn) {

		return ascDataList(tableN, " ORDER BY " + ascColumn );

	}

	public static Object[][] ascDataList(Object setTableName, String ascColumn) {
		/////DBの中身を二次元配列で返す

		System.out.println("");////////////////////////////////////////////////////////////////////////////////////
		System.out.println("テーブル" + setTableName + "内のデータを" + ascColumn + "の昇順で二次元配列に代入します");///
		System.out.println("");////////////////////////////////////////////////////////////////////////////////////

		return dataList(setTableName, " ORDER BY " + ascColumn );
	}

//	public static Object[][] ascDataList(String ascColumn) {
//		/////DBの中身を二次元配列で返す
//
//		System.out.println("");////////////////////////////////////////////////////////////////////////////////////
//		System.out.println("テーブル" + tableN + "内のデータを" + ascColumn + "の昇順で二次元配列に代入します");///
//		System.out.println("");////////////////////////////////////////////////////////////////////////////////////
//
//		Object[][] dataList = new Object[size(sqlSection())][c.length];
//
//		for (int i = 0; i < size(sqlSection()); i++) {
//			for (int j = 0; j < c.length; j++) {
//				dataList[i][j] = ascData(ascColumn, (i + 1), c[j]);
//
//				System.out.println("");//////////////////////////////////////////////////////////////////////
//				System.out.println("  データ[" + i + "][" + j + "]に " + dataList[i][j] + " を代入しました");//
//				System.out.println("");//////////////////////////////////////////////////////////////////////
//
//			}
//		}
//
//		System.out.println("");//////////////////////////////////////////////////////////////////
//		System.out.println("データを" + ascColumn + "の昇順で代入した二次元配列を返します");/////
//		System.out.println("");//////////////////////////////////////////////////////////////////
//
//		return dataList;
//	}


	public static void delete(Object columnName,int rowNumber) {

		System.out.println("");//////////////////////////////////////////////////////////////////
		System.out.println("テーブル "+tableN +" ＜" + columnName + "＞ の " + rowNumber + "行目を削除します");///
		System.out.println("");//////////////////////////////////////////////////////////////////

//		ResultSet sRs = scrollResultSetRead(section);
//
//		try {
//
//			sRs.absolute(i);
//			int row = sRs.getInt(1);
//
//			String sql = "DELETE FROM " + tableN + " WHERE " + num + " = " + row;
//			st.executeUpdate(sql);
//
//			System.out.println("");//////////////////////////////////////////////////////////
//			System.out.println(section + " の " + i + "行目をdelete(String s,int i)出来ました");///
//			System.out.println("");//////////////////////////////////////////////////////////
//
////			initial();
//
//			System.out.println("");////////////////////////////////////
//			System.out.println("行数＝" + size(section));////////////////////
//			System.out.println("");////////////////////////////////////
//
//			System.out.println("");////////////////////////////////////
//			System.out.println("インスタンスをクローズします");////////
//			System.out.println("");////////////////////////////////////
//
//			// shutdown();
//
//			// 後始末(インスタンスの正常クローズ)
//			st.close();
//			conn.close();
//
//			System.out.println("");/////////////////////////////////////////////////////////////////////
//			System.out.println("delete(" + section + ", " + i + ")用インスタンスをクローズしました");///
//			System.out.println("");/////////////////////////////////////////////////////////////////////
//
//		} catch (SQLException e) {
//			e.getMessage();
//			e.printStackTrace();
//
//			System.out.println("");////////////////////////////////////////////////////////////////
//			System.out.println("delete(" + section + ", " + i + ")出来ませんでした");//////////////
//			System.out.println("");////////////////////////////////////////////////////////////////
//		}

		delete(tableN, columnName, rowNumber);
	}

	public static void delete(Object setTableName, String section, int i) {

		System.out.println("");///////////////////////////////////////////////////////////////////////////////
		System.out.println("テーブル " + setTableName + " ＜" + section + "＞ の " + i + "行目を 削除 します");///
		System.out.println("");///////////////////////////////////////////////////////////////////////////////

		if (i == 0) {
			System.out.println("");///////////////////////////////
			System.out.println("  削除する行が選ばれていません");///
			System.out.println("");///////////////////////////////

		} else {

			ResultSet sRs = scrollResultSetRead(setTableName, section);

			try {

				sRs.absolute(i);
				int row = sRs.getInt(1);

				String sql = "  DELETE FROM " + setTableName + " WHERE " + num + " = " + row;
				st.executeUpdate(sql);

				System.out.println("");///////////////////////////////////////////////////////////////////////////////////
				System.out.println("  テーブル " + setTableName + " ＜" + section + "＞ の " + i + "行目を 削除 しました");///
				System.out.println("");///////////////////////////////////////////////////////////////////////////////////

//			initial();

				System.out.println("");////////////////////////////////////////
				System.out.println("  行数＝" + size(setTableName, section));////
				System.out.println("");////////////////////////////////////////

				// 後始末(インスタンスの正常クローズ)
				st.close();
				conn.close();

				System.out.println("");/////////////////////////////////////////////////////////////////////
				System.out.println("  delete(" + section + ", " + i + ")用インスタンスをクローズしました");///
				System.out.println("");/////////////////////////////////////////////////////////////////////

			} catch (SQLException e) {
				e.getMessage();
				e.printStackTrace();

				System.out.println("");////////////////////////////////////////////////////////////////
				System.out.println("  delete(" + section + ", " + i + ")出来ませんでした");//////////////
				System.out.println("");////////////////////////////////////////////////////////////////
			}
		}
	}

	public static void delete(Object setTableName, String whereSql) {

			if(setTableName.equals("")) setTableName = tableN;

			System.out.println("");//////////////////////////////////////////////////////////
			System.out.println("テーブル " + setTableName + " から ＜" + whereSql + "＞");///
			System.out.println("の行を 削除 します");////////////////////////////////////////
			System.out.println("");//////////////////////////////////////////////////////////

			ResultSet sRs = scrollResultSetRead(setTableName, whereSql);

			try {

				String sql = "  DELETE FROM " + setTableName + whereSql;
				st.executeUpdate(sql);

				System.out.println("");////////////////////////////////////////////////////////////
				System.out.println("テーブル " + setTableName + " から ＜" + whereSql + "＞た");///
				System.out.println("の行を 削除 しました");////////////////////////////////////////
				System.out.println("");////////////////////////////////////////////////////////////

	//			initial();

				System.out.println("");//////////////////////////////////////////////////////////////////////////////////
				System.out.println("  テーブル " + setTableName + " の 総行数 = " + size(setTableName,"") + " です");////
				System.out.println("");//////////////////////////////////////////////////////////////////////////////////

				// 後始末(インスタンスの正常クローズ)
				st.close();
				conn.close();

				System.out.println("");///////////////////////////////////////////////////
				System.out.println("  delete(" + setTableName + ", " + whereSql + ")");///
				System.out.println("  用インスタンスをクローズしました");/////////////////
				System.out.println("");///////////////////////////////////////////////////

			} catch (SQLException e) {
				e.getMessage();
				e.printStackTrace();

				System.out.println("");///////////////////////////////////////////////////
				System.out.println("  delete(" + setTableName + ", " + whereSql + ")");///
				System.out.println("  出来ませんでした");/////////////////////////////////
				System.out.println("");///////////////////////////////////////////////////
			}
		}

	public static void delete(Object setTableName, Object whereColumn, Object whereData) {

		delete(setTableName, where(whereColumn, whereData));
	}
	
	public static void delete(Object setTableName, Object[] whereColumnList, Object[] whereDataList) {

		delete(setTableName, andWhere(whereColumnList, whereDataList));
	}

	static void reDataTable(String tableName, Object[][] tableData) {

		System.out.println("");//////////////////////////////////////////////////////////////////////////////////////////
		System.out.println("initial(" + tableName + ", Object[][] tableData) [テーブルデータの書き換え] を行います");////
		System.out.println("");//////////////////////////////////////////////////////////////////////////////////////////

		String oldTable = "temporaryTableName";

		reNameTable(tableName, oldTable);

		createTable(tableName);

		System.out.println("");/////////////////////////////////////////////////////////////////////
		System.out.println("  新しく作成した空の "+tableName + " に受け取ったデータを挿入します");/////
		System.out.println("");/////////////////////////////////////////////////////////////////////

		for (Object[] rowData : tableData) {
			insert(tableName,rowData);
		}

		dropTable(oldTable);

		System.out.println("");//////////////////////////////////////////////
		System.out.println(tableName + " を新しいデータで書き換えました");///
		System.out.println("");//////////////////////////////////////////////



	}

	public static Object[][] selection(Object whereColumn, Object whereData) {

		System.out.println("");/////////////////////////////////////////////////////////
		System.out.println("selection(" + whereColumn + "," + whereData + ")します");///
		System.out.println("");/////////////////////////////////////////////////////////

		return selection("*", tableN, whereColumn, whereData, orderAscColumn);
	}

	public static Object[][] selection(Object selectColumn, Object setTableName, Object whereColumn, Object whereData) {

		System.out.println("");////////////////////////////////////////////////
		System.out.println("selection(" + selectColumn + "," + setTableName + "," + whereColumn + "," + whereData + ")します");////
		System.out.println("");////////////////////////////////////////////////

		return selection(selectColumn, setTableName, whereColumn, whereData, orderAscColumn);
	}
	
	public static Object[][] selection(Object selectColumn, Object setTableName, Object whereColumn, Object whereData, Object ascColumn) {
		
		Object[] whereColumnList = new Object[]{whereColumn};
		Object[] whereDataList = new Object[]{whereData};
		
		return selection(selectColumn, setTableName, whereColumnList, whereDataList, ascColumn);
	}

	public static Object[][] selection(Object selectColumn, Object setTableName, Object[] whereColumnList, Object[] whereDataList, Object ascColumn) {

		String method = ("selection(" + selectColumn + ", " + setTableName + ", Object[] whereColumnList, Object[] whereDataList, " + ascColumn + ")");

		System.out.println("");////////////////////////////////////////////////
		System.out.println(method + "します");/////////////////////////////////
		System.out.println("");////////////////////////////////////////////////

		if(selectColumn.equals("") || selectColumn == null) selectColumn = "*";

		if(setTableName.equals("") || setTableName == null) setTableName = tableN;

		whereColumnData = andWhere(whereColumnList, whereDataList);
		
		orderAscColumn = orderByAsc(ascColumn);

		System.out.println("");//////////////////////////////////////////////////////////////
		System.out.println("  whereColumnData に " + whereColumnData + " を代入しました");///
		System.out.println("");//////////////////////////////////////////////////////////////

		System.out.println("");//////////////////////////////////////////////////////////////
		System.out.println("  orderAscColumn に " + orderAscColumn + " を代入しました");///
		System.out.println("");//////////////////////////////////////////////////////////////

		if (selectColumn.equals("*") == false){
			selectColumn = "'" + selectColumn + "'";
		}

		String sql = (" SELECT " + selectColumn + " FROM " + setTableName + " " + sqlSection());

		return dataList(sql);

	}
	
	static String orderBy(Object orderBySql) {
		
		String sql = (" ORDER BY " + orderBySql);

		if(orderBySql.equals("") || orderBySql == null) sql = "";
		
		return sql;
	}

	static String orderByAsc(Object ascColumn) {
		
		String orderSql = (" ORDER BY " + ascColumn);

		if(ascColumn == null) {
			orderSql = "";
		}else{
			if(ascColumn.equals("")){
				orderSql = "";
			}
		}
		
		return orderSql;
	}
	
	private static String orderByDesc(Object descColumn) {
		
		String orderSql = (" ORDER BY " + descColumn + " DESC ");

		if(descColumn.equals("") || descColumn == null) orderSql = "";
		
		return orderSql;
	}

	public static Object[][] selectionLike(Object selectColumn, Object setTableName, Object whereColumn, Object whereData, Object ascColumn) {

		String method = ("selectionLike(" + selectColumn + "," + setTableName + "," + whereColumn + "," + whereData +  "," + ascColumn + ")");

		System.out.println("");////////////////////////////////////////////////
		System.out.println(method + "します");/////////////////////////////////
		System.out.println("");////////////////////////////////////////////////

		if(selectColumn.equals("") || selectColumn == null) selectColumn = "*";

		if(setTableName.equals("") || setTableName == null) setTableName = tableN;

		if (isNum(whereData) == false) {
			whereData = putCharSymbol(whereData);
		}

		whereColumnData = (" WHERE " + whereColumn + " LIKE " + whereData);

		if(whereColumn.equals("") || whereColumn == null) whereColumnData = "";

		if(whereData.equals("") || whereData == null) whereColumnData = "";

		orderAscColumn = (" ORDER BY " + ascColumn);

		if(ascColumn.equals("") || ascColumn == null) orderAscColumn = "";

		System.out.println("");//////////////////////////////////////////////////////////////
		System.out.println("  whereColumnData に " + whereColumnData + " を代入しました");///
		System.out.println("");//////////////////////////////////////////////////////////////

		System.out.println("");//////////////////////////////////////////////////////////////
		System.out.println("  orderAscColumn に " + orderAscColumn + " を代入しました");///
		System.out.println("");//////////////////////////////////////////////////////////////

		if (selectColumn.equals("*") == false){
			selectColumn = "'" + selectColumn + "'";
		}

		String sql = (" SELECT " + selectColumn + " FROM " + setTableName + " " + sqlSection());

		return dataList(sql);

	}


	public static Object[][] ascSelection(String ascColumn, Object column, Object data) {

		System.out.println("");//////////////////////////////////////////////////////////////////////////////
		System.out.println("ascSelection(" + ascColumn + ", " + column + ", " + data + ")します");///////////
		System.out.println("");//////////////////////////////////////////////////////////////////////////////

		if(isNum(data)==false){
			data = putCharSymbol(data);
//			data = ("'" + data + "'");
		}
		sqlSection();
		orderAscColumn = (" ORDER BY " + ascColumn);
		whereColumnData = (" WHERE " + column + " = " + data);
		sqlSection = whereColumnData + getOrderAscColumn();
		String sql = "SELECT  *  FROM " + tableN  + sqlSection();
		ResultSet sRs = null;
		Object[][] dataList = null;

		System.out.println("");////////////////////////////////////
		System.out.println("sql ＝ " + sql);///////////////////////
		System.out.println("");////////////////////////////////////

		try {

			conn = DriverManager.getConnection(uri);
			st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			sRs = st.executeQuery(sql);
//			sRs.last();
//			int size = sRs.getRow();

			dataList = new Object[size(sqlSection())][c.length];

			for (int i = 0; i < size(sqlSection()) ; i++) {
				for (int j = 0; j < c.length; j++) {
					sRs.absolute(i+1);
					dataList[i][j] = sRs.getObject(c[j]);

					System.out.println("");//////////////////////////////////////////////////////////////////////
					System.out.println("  データ[" + i + "][" + j + "]に " + dataList[i][j] + " を代入しました");//
					System.out.println("");//////////////////////////////////////////////////////////////////////

				}
			}

			// 後始末(インスタンスの正常クローズ)
			st.close();
			conn.close();

			System.out.println("");//////////////////////////////////////////////////
			System.out.println("    st と、conn  インスタンス、をクローズしました ");///
			System.out.println("");//////////////////////////////////////////////////

		} catch (SQLException e) {
			e.getMessage();
			e.printStackTrace();
			System.out.println("");///////////////////////////////////////////////////////////////////////////////////////////////////
			System.out.println("  ascSelection(" + ascColumn + ", " + column + ", " + data + ")出来ないので[null]を返します");//////////
			System.out.println("");///////////////////////////////////////////////////////////////////////////////////////////////////
		}
		return dataList;

	}


	public static Object[][] distinctList(String distinctColumn, String setSqlSection) {
		return distinctList(tableN , distinctColumn, setSqlSection);
	}

	public static Object[][] distinctList(Object tableName , String distinctColumn, String setSqlSection) {

		System.out.println("");/////////////////////////////////////////////////////////////
		System.out.println("distinctList(" + tableName + ", " + distinctColumn + ", " + setSqlSection + ") します");///
		System.out.println("");/////////////////////////////////////////////////////////////

		String sql = "SELECT DISTINCT " + distinctColumn + " FROM " + tableName +" "+ setSqlSection;

		System.out.println("");///////////////////////////////////////////////////////////
		System.out.println("  ＜" + sql + "＞ で得たデータを二次元配列に代入します");///////
		System.out.println("");///////////////////////////////////////////////////////////

		Object[][] dataList = null;

		ResultSet sRs = sqlScrollResultSetRead(sql);

		try {

			sRs.last();

			int rowSize = sRs.getRow();

			int columnCount = sRs.getMetaData().getColumnCount();

			dataList = new Object[rowSize][columnCount];

			if (rowSize == 0) {
				dataList = new Object[1][columnCount];
				for (int i = 0; i < columnCount; i++) {
					dataList[0][i] = "";

					System.out.println("");///////////////////////////////////////
					System.out.println("  行 ( Row レコード ) が存在しません");///
					System.out.println("");///////////////////////////////////////////////
					System.out.println("  データ[0][" + i + "]に ”” を代入しました");///
					System.out.println("");///////////////////////////////////////////////

				}
			}

			for (int i = 0; i < rowSize; i++) {
				for (int j = 0; j < columnCount; j++) {
					sRs.absolute(i + 1);
					dataList[i][j] = sRs.getObject(j + 1);

//					System.out.println("");/////////////////////////////////////////////////////////////////////////
					System.out.println("  データ[" + i + "][" + j + "]に " + dataList[i][j] + " を代入しました");///
//					System.out.println("");/////////////////////////////////////////////////////////////////////////

				}
			}

			// 後始末(インスタンスの正常クローズ)
			st.close();
			conn.close();

			System.out.println("");//////////////////////////////////////////////////
			System.out.println("     dataList()用インスタンスをクローズしました");///
			System.out.println("");//////////////////////////////////////////////////

		} catch (SQLException e) {
			e.getMessage();
			e.printStackTrace();
			System.out.println("");///////////////////////////////////////////////////////////
			System.out.println("  dataList()出来ないので[null]を返します");///////////////////
			System.out.println("");///////////////////////////////////////////////////////////
		}
		return dataList;

	}
	
	public static Object[][] distinctList(Object setTableName, String distinctColumn, String whereSql, String orderColumn) {
		
		String setSqlSection = "";
		
		if (whereSql.equals("") == false){
			setSqlSection = setSqlSection + " WHERE " + whereSql;
		}
		if (orderColumn.equals("") == false){
			setSqlSection = setSqlSection + " ORDER BY " + orderColumn;
		}
		
		return distinctList(setTableName, distinctColumn, setSqlSection);
	}

	public static Object[][] sqlList(String setSql) {

		System.out.println("");/////////////////////////////////////////////////////
		System.out.println(setSql + " で得たデータを二次元配列に代入します");///////
		System.out.println("");/////////////////////////////////////////////////////

		Object[][] dataList = null;

		ResultSet sRs = sqlScrollResultSetRead(setSql);

		try {

			sRs.last();

			int size = sRs.getRow();
			
			int columnCount = sRs.getMetaData().getColumnCount();

			dataList = new Object[size][columnCount];

			for (int i = 0; i < size ; i++) {
				for (int j = 0; j < columnCount; j++) {
					sRs.absolute(i+1);
					dataList[i][j] = sRs.getObject(c[j]);

//					System.out.println("");/////////////////////////////////////////////////////////////////////////
					System.out.println("  データ[" + i + "][" + j + "]に " + dataList[i][j] + " を代入しました");///
//					System.out.println("");/////////////////////////////////////////////////////////////////////////

				}
			}

			// 後始末(インスタンスの正常クローズ)
			st.close();
			conn.close();

			System.out.println("");//////////////////////////////////////////////////
			System.out.println("     dataList()用インスタンスをクローズしました");///
			System.out.println("");//////////////////////////////////////////////////

		} catch (SQLException e) {
			e.getMessage();
			e.printStackTrace();
			System.out.println("");///////////////////////////////////////////////////////////
			System.out.println("  dataList()出来ないので[null]を返します");///////////////////
			System.out.println("");///////////////////////////////////////////////////////////
		}
		return dataList;

	}


	static String sqlSection() {

		if(whereColumnData == null) whereColumnData = "";

		if(orderAscColumn == null) orderAscColumn = "";

		sqlSection = whereColumnData + orderAscColumn;

		System.out.println("");/////////////////////////////////////////////////
		System.out.println("  sqlSection() は " + sqlSection + " です ");///////
		System.out.println("");/////////////////////////////////////////////////

		return sqlSection;
	}


	private static Object putCharSymbol(Object data) {
		data = ("'" + data + "'");
		return data;
	}


	private void show2DArrayA(Object[][] setArray) {
		for (Object[] objects : setArray) {
			for (Object object : objects) {
				System.out.print((String) object);////////////////////////
				System.out.print("|");////////////////////////////////////
			}
			System.out.println("");///////////////////////////////////////
		}

	}

	private void show2DArrayB(Object[][] setArray) {

		for (int i = 0; i < setArray[0].length; i++) {
			for (int j = 0; j < setArray.length; j++) {
				System.out.print((String) setArray[j][i]);
				System.out.print("|");///////////////////////////////////////
			}
			System.out.println("");//////////////////////////////////////////
		}

	}


	public static void setOrderAscColumn(String orderAscColumn) {
		DerbyC.orderAscColumn = orderAscColumn;
	}


	public static String getOrderAscColumn() {
		return orderAscColumn;
	}


	public static void setWhereColumnData(String whereColumnData) {
		DerbyC.whereColumnData = whereColumnData;
	}


	public static String getWhereColumnData() {
		return whereColumnData;
	}

	private static String where(Object whereColumnName, Object whereData) {
		
		String r = null;

		Object[] arrays = {};

		if (whereColumnName.getClass() == arrays.getClass()) {
			
			Object[] columnData = (Object[]) whereColumnName;
			
			Object[] rowData = (Object[]) whereData;
			
			r = andWhere(columnData, rowData);
			
		} else {

			if (isNum(whereData) == true) {
				r = " WHERE " + whereColumnName + " = " + whereData;
			} else {
				r = " WHERE " + whereColumnName + " = '" + whereData + "'";
			}
		}
		return r;
	}
	
	private static String where(Object whereSql) {
		
		String sql = " WHERE " + whereSql;
		
		if(whereSql.equals("") || whereSql == null) sql = "";
		
		return sql;
	}
	
	private static String set(Object whereColumnName, Object whereData) {
		
		String r = null;

		Object[] arrays = {};

		if (whereColumnName.getClass() == arrays.getClass()) {
			
			Object[] columnData = (Object[]) whereColumnName;
			
			Object[] rowData = (Object[]) whereData;
			
			r = commaSet(columnData, rowData);
			
		} else {

			if (isNum(whereData) == false) {
				
				whereData = putCharSymbol(whereData);
			}
			r = " Set " + whereColumnName + " = '" + whereData + "'";
		}
		return r;
	}
	
	static String whereAsc(Object whereColumnName, Object whereData, Object ascColumnName) {

		String where = where(whereColumnName,  whereData);

		return where + orderByAsc(ascColumnName);
	}
	
	private static String whereDesc(Object whereColumnName, Object whereData, Object descColumnName) {

		String where = where(whereColumnName,  whereData);

		return where + orderByDesc(descColumnName);
	}

	static String is(Object columnName, Object data) {

		String r = "";
		
		if (columnName != null && columnName.equals("") == false) {

			if (isNum(data) == true) {
				r = columnName + " = " + data;
			} else {
				r = columnName + " = '" + data + "'";
			}
		}
		return r;
	}


	private static Object[] is(Object[] columnData, Object[] rowData) {
		
		int isLength = columnData.length;;
		
		if (columnData.length > rowData.length) {
			isLength = rowData.length;
		}

		Object[] whereData = new Object[isLength];

		for (int i = 0; i < whereData.length; i++) {

			whereData[i] = is(columnData[i], rowData[i]);
		}
		return whereData;
	}
	
	public static String comma(Object[] connectData) {

		String sql = "" + connectData[0];

		for (int i = 1; i < connectData.length; i++) {

			if(connectData[i] != null){
				
				if(connectData[i].equals("")) connectData[i] = "''";
				
				sql = sql + ", " + connectData[i];
			}
		}
		return sql + " ";
	}
	
	public static String comma(Object[] columnData, Object[] rowData) {

		return comma(is(columnData, rowData));
	}
	
	public static String commaSet(Object[] whereData) {

		return " SET " + comma(whereData);
	}

	public static String commaSet(Object[] columnData, Object[] rowData) {

		return " SET " + comma(is(columnData, rowData));
	}
	
	public static String and(Object connectDataA, Object connectDataB) {
		
		String sql = connectDataA + " AND " + connectDataB;
		
		return sql;
	}

	public static String and(Object[] connectData) {

		String sql = "" + connectData[0];

		for (int i = 1; i < connectData.length; i++) {

			if(connectData[i] != null){
				
				if(connectData[i].equals("")) connectData[i] = "''";
				
				sql = sql + " AND " + connectData[i];
			}
		}
		return sql + " ";
	}

	public static String and(Object[] columnData, Object[] rowData) {

		return and(is(columnData, rowData));
	}

	public static String andWhere(Object[] whereData) {

		return " WHERE " + and(whereData);
	}

	public static String andWhere(Object[] columnData, Object[] rowData) {

		return " WHERE " + and(is(columnData, rowData));
	}
	
	public static String select(Object[] selectList) {
	
		String sql = " SELECT ";
		for (int i = 0; i < selectList.length; i++) {
			sql = sql + selectList[i];
			if(i != selectList.length - 1){
				sql = sql + ", ";
			}
		}
		return sql;
	}

	public static String select(Object[] selectList, Object tableName) {
		String sql = select(selectList) + from(tableName);
		return sql;
	}
	
	public static String select(Object selectColumn, Object tableName) {
		String sql = select(selectColumn) + from(tableName);
		return sql;
	}
	
	public static String select(Object selectColumn, Object tableName, Object whereSql) {
		String sql = select(selectColumn) + from(tableName) + where(whereSql);
		return sql;
	}
	
	public static String select(Object selectColumn, Object tableName, Object whereSql, Object orderBySql) {
		String sql = select(selectColumn) + from(tableName) + where(whereSql) + orderBy(orderBySql);
		return sql;
	}
	
	public static String select(Object selectColumn) {
		
		if(selectColumn.equals("") || selectColumn == null) selectColumn = "*";
		
		String sql = " SELECT " + selectColumn + " ";
		return sql;
	}

	public static String from(Object tableName) {

		String sql = " FROM " + tableName + " ";
		
		return sql;
	}

	public static String selectWhereAsc(Object[] selectList, Object tableName, Object[] whereColumnList, Object[] whereDataList, String ascColumn) {

		String select = DerbyC.select(selectList, tableName);
		
		String whereAsc = DerbyC.whereAsc(whereColumnList, whereDataList, ascColumn);
		
		String sql = select + whereAsc;
		
		return sql;
	}
	
	static Object[][] switchQueues(Object[][] dataList) {
		
		Object[][] switchQueues = new Object[dataList[0].length][dataList.length];
		for (int i = 0; i < dataList.length; i++) {
			for (int j = 0; j < dataList[0].length; j++) {
				switchQueues[j][i] = dataList[i][j];
			}
		}
		return switchQueues;
	}
	
	static Object[] selectList(Object setTableName, String distinctColumn, String whereSql, String orderColumn) {
		
		return switchQueues(distinctList(setTableName, distinctColumn, whereSql, orderColumn))[0]; 
	}
}

