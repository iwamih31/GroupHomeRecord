package iwamih31.GroupHomeRecord;

import javax.swing.table.AbstractTableModel;

public class Story extends AbstractTableModel{

	static String[][] sent = { { "" }, { "" }, { "" }, };

	private static String[] textList;

	Story(){

		textList = new String[1];
		textList[0] = "スタート";
	}


	@Override
	public int getRowCount() {
		// TODO 自動生成されたメソッド・スタブ
		return sent.length;
	}

	@Override
	public int getColumnCount() {
		// TODO 自動生成されたメソッド・スタブ
		return sent[0].length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return sent[rowIndex][columnIndex];
	}

	public void on(String s) {

		System.out.println("");//////////////////////////////////////////
		System.out.println("Story.on(" + s + ") します ");// //////////////////////////
		System.out.println("");//////////////////////////////////////////

		Story.sent[1][0] = (s);
		fireTableDataChanged();//テーブル更新
	}

	public static void setTextList(String[] textList) {
		Story.textList = textList;
	}

	public String[] getTextList() {
		return textList;
	}
}
