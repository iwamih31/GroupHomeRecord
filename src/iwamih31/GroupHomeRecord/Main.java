package iwamih31.GroupHomeRecord;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.SwingUtilities;
import javax.swing.table.AbstractTableModel;

public class Main extends AbstractTableModel implements Serializable{

	static String saveFile  = "sData.txt";

	private static String officeName = "Care office";

	private static String name = officeName;

	static Main mai;

	private static Screen sc;

	private static String a;

	private static String[][] officeData;

	private static String[] doText;

	private static String[] text;

	private static Talk tal;

	private static String[] columns;

	private static String[] data;

	Main(){

	}


	public static void main(String[] args) {///////////////////スタート

		mai = new Main();///////////////////////////////////////////////////

		columns = new String[3];
		columns[0] = "事業所名";
		columns[1] = "部署名";
		columns[2] = "備考";

		data = new String[3];

		data[0] = officeName;
		data[1] = "2階";
		data[2] = "";

		officeData = new String[2][columns.length];
		officeData[0] = columns;
		officeData[1] = data;

		Input.clear();///////////////////////////////////////////画面クリア

		File newfile = new File("sData.txt");////////セーブ用ファイル作成//////////
		try {
			newfile.createNewFile();
		} catch (IOException e) {
			e.getMessage();
			e.printStackTrace();
		}//////////////////////////////////////////////////////////////////////////

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {

				Screen.setTex("つづきから始めますか？・・・");

				sc = new Screen("Care Record");

					Screen.que();
			}
		});

		System.out.println("");
		System.out.println("");
		System.out.println("つづきから始めますか？・・・ [ １. はい ][ ２. いいえ ]");
		System.out.println("");
	}

	public static void begin() {

		System.out.println("");
		System.out.println("");
		System.out.println("事業所の名前を入力して下さい");
		System.out.println("");

			name = officeName;

			new ArrayList<String>();

			System.out.println("");
			System.out.println("  ・・・");
	}


	static void scroll(int d) {
		for (int i = 0; i < 30; i++) {
			System.out.println("");
			Robot rob;
			try {
				rob = new Robot();
				rob.delay(d);///////////////////表示ディレイ値
			} catch (AWTException e1) {
				e1.printStackTrace();
			}
		}
	}

	public static void setName(String name) {
		Main.name = name;
	}

	public static String getName() {
		return name;
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

	public static void setA(String a) {
		Main.a = a;
	}

	public static String getA() {
		return a;
	}

	public static void setSc(Screen sc) {
		Main.sc = sc;
	}

	public static Screen getSc() {
		return sc;
	}

	public static void ent() {
		// TODO 自動生成されたメソッド・スタブ
		try {
			Robot rob = new Robot();
			rob.delay(200);
			rob.keyPress(KeyEvent.VK_ENTER);
			rob.keyRelease(KeyEvent.VK_ENTER);

		} catch (AWTException e) {
			// TODO 自動生成された catch ブロック
			e.getMessage();
			e.printStackTrace();
		}
	}

	public static void setText(ArrayList<String> arrayList) {

		text = new String[arrayList.size()];

		for (int i = 0; i < arrayList.size(); i++) {
			text[i] = arrayList.get(i);
		}
	}

	public static String[] getText() {
		return text;
	}

	public static void setDoText(String[] doText) {
		Main.doText = doText;
	}

	public static String[] getDoText() {
		return doText;
	}

	public static void setLp(int lp) {
	}

	public static void setTal(Talk tal) {
		Main.tal = tal;
	}

	public static Talk getTal() {
		return tal;
	}


	static void setOfficeName(String officeName) {
		Main.officeName = officeName;
	}


	public static String getOfficeName() {
		return officeName;
	}
}