package iwamih31.GroupHomeRecord;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import javax.swing.SwingUtilities;
import javax.swing.table.AbstractTableModel;

public class Main extends AbstractTableModel implements Serializable{

	static String saveFile  = "sData.txt";

	private static String officeName = "Care office";

	private static String name = officeName;
//	static int hp = 100;//////////////////////削除

	private static int g;

	private static String mName = "きみ";
	static int bHp = 3;

	private static String bName = "きみ";

	static Main mai;

	private static Screen sc;

	private static String a;

	private static String[][] officeData;

	private static String[] doText;

	private static String[] text;

	private static ArrayList<String> array;

	private static Talk tal;

	private static String[] columns;

	private static String[] data;

	Main(){

	}


	public static void main(String[] args) {///////////////////スタート

		mai = new Main();///////////////////////////////////////////////////

		bHp = 3;
		g = 0;

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

			array = new ArrayList<String>();

			System.out.println("");
			System.out.println("  ・・・");
	}


	static void action(int select) {

		Object[] choice;
		arrayClear();

		switch (select) {

			case 1 :

				array.add(("―――――" + getName() + "は探検を続けた―――――"));

				setText(array);

				break;

			case 2 :


				break;
			case 3 :


				break;

			case 4 :


				break;

			default :
		}
		save();
	}

	private static void arrayClear() {

		if (array == null){
			array = new ArrayList<String>();
		}else{
			for (int i = array.size(); i > 0; i--) {
				array.remove(i-1);
			}
		}
	}


	private static void button(Object[] choices) {
		// TODO 自動生成されたメソッド・スタブ
		Screen.setMenu(choices);
	}



	private static void message(String setMessage) {

		Screen.setMessage(setMessage);
	}

	static void save() {
		try {
			ObjectOutputStream sData = new ObjectOutputStream(new FileOutputStream(saveFile));

			sData.writeObject(officeName);
			sData.writeInt(bHp);
			sData.writeInt(g);
//			sData.writeObject(Item.getItemList());

			sData.close();

		} catch (FileNotFoundException e) {
			// TODO 自動生成された catch ブロック
			e.getMessage();
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.getMessage();
			e.printStackTrace();
		}

	}


	static void load() {

		try {
			ObjectInputStream sData = new ObjectInputStream(new FileInputStream(saveFile));

			officeName = ""+ sData.readObject();
			bHp = sData.readInt();
			g = sData.readInt();
//			Object ItemList = sData.readObject();

			sData.close();

			name = "" + officeName;

//			Item.setItemList((Object[][]) ItemList);


		} catch (FileNotFoundException e) {
			// TODO 自動生成された catch ブロック
			e.getMessage();
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.getMessage();
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO 自動生成された catch ブロック
			e.getMessage();
			e.printStackTrace();
		}
	//	Story.beBack();
	}


	private static void end() {

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

	public static void setmName(String mName) {
		Main.mName = mName;
	}

	public static String getmName() {
		return mName;
	}

	public static void setG(int g) {
		Main.g = g;
	}

	public static int getG() {
		return g;
	}

	public static void setbName(String bName) {
		Main.bName = bName;
	}

	public static String getbName() {
		return bName;
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