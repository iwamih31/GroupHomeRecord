package iwamih31.GroupHomeRecord;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import javax.swing.BoxLayout;
import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JViewport;
import javax.swing.SpringLayout;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

@SuppressWarnings("serial")
public class Screen extends JFrame implements ActionListener, KeyListener {

	JLabel ansLabel;
	static JLabel display;
	protected static String value;
	String op1;
	String op2;
	String operator;
	int opMode;
	private static JLabel q;
	private static Object[] ynList;
	private static JTextArea inpT;
	private Story sto;
	private JTextArea textAreaN;
	private JTextArea textAreaW;
	private JTextArea textAreaC;
	private JTextArea textAreaE;
	private JTextArea textAreaS;
	private static Border border;
	private JTextArea textAreaB;
	private JTextArea menuAreaB;
	private JTextArea pictAreaB;
	private static String ent;// entボタン名
	private static Story story;
	private String buttonName;
	private String officeName;
	private JPanel eventPanel;
	private JPanel backPanel;
	private String cancel;// cancelボタン名
	private TableData tableData;
	private Object[][] userData;
	private Object selectName;
//	private Event eve;
	private Routine rout;
	private Object[][] beforeData;
	private TableModel tableModel;
	private Object[] lostRowData;
	private Object[][] oldData;
	private Object targetTableName;
	private String undo;
	private String redo;
	private String actionName;
	private Object[] changedRowData;
	private Object[] insertRowData;
	private Object selectRoom;
	private Object[] cutOutData;
	private static int w;
	private static int h;
	private static JTable selectJTable;
	private static String selectDate;
	private static String inpText;
	private static int count;
	private static String message;
	private static Object[] menu;
	private static int mode;
	private static Component panelN;
	private static Component panelW;
	private static Component panelC;
	private static Component panelE;
	private static Component panelS;
	private static JLabel labelN;
	private static JLabel labelW;
	private static JLabel labelC;
	private static JLabel labelE;
	private static JLabel labelS;
	private static String tex;
	private static JFrame frame;
	private static JPanel panelSet;
	private static JPanel changePanelSet;
	private static CardLayout cardLayout;
	private static JPanel clear;
	private static JLabel space;
	private static String entMark;
	private static Object[] clickedData;
	private static int fontSize;

	public Screen(Object[] mList) {
		super("メニュー");

		/*
		 * String exterior =
		 * "javax.swing.plaf.windows.WindowsLookAndFeel";//メタルはplaf
		 * .の後をmetal.MetalLookAndFeelへ変える try{
		 * UIManager.setLookAndFeel(exterior);
		 * SwingUtilities.updateComponentTreeUI(this.frame); } catch (Exception
		 * e) { e.printStackTrace(); }
		 */

		menu(mList);
	}

	public Screen(String s) {
		super(s);
		start(s);
		outer();
	}

//	初期設定
	private void start(String s) {
//		ディスプレイサイズを基準に、横1％、縦1％、フォントサイズを決定
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        w = screenSize.width / 100;
        h = screenSize.height / 100;
        fontSize = w;
		setMode(0);
		border = new LineBorder(Color.WHITE, 2, true);
		ynList = new Object[]{ "はい", "いいえ" };
		entMark = (" ⇒ ");
		ent = entMark;
		undo = "戻す";
		redo = "行う";
		cancel = "Cancel";
		labelSet("");
		int bWE = 30;
		textAreaB = textAreaSet("", 1, 5);
		menuAreaB = textAreaSet("", 1, 1);
		textAreaN = textAreaSet("", 5, 5);/////////////メンバーステータス
		textAreaW = textAreaSet("", 5, bWE);///////////現状
		textAreaC = textAreaSet("", 5, 9);////////////画面
		textAreaE = textAreaSet("", 5, bWE);///////////コマンド
		textAreaS = textAreaSet("", 1, 5);/////////////メッセージ

		labelN = labelSet("メンバーステータス");
		labelW = labelSet("現状");
		labelC = labelSet("画面");
		labelE = labelSet("コマンド");
		labelS = labelSet("メッセージ");

		panelN = panelSetUD(textAreaN, textAreaB);
		panelW = panelSetUD(null, textAreaW);
		panelC = panelSetUD(null, labelC);
		panelE = panelSetUD(null, textAreaE);
		panelS = panelSetUD(null, textAreaS);

		space = labelSet("                                       ");

		selectDate = today();

	}

	private static JLabel b() {
		JLabel b = labelSet(" ");
		return b;
	}

	private static JTextArea textAreaSet(String text, int rows, int columns) {

		JTextArea textAreaSet = new JTextArea(text, rows, columns);
		format(textAreaSet);
		textAreaSet.setEditable(false);
		textAreaSet.setOpaque(false);///////////////背景を透明にする
		return textAreaSet;
	}

	private static JLabel labelSet(String string) {

		JLabel labelSet = new JLabel(string, JLabel.CENTER);
		format(labelSet);
		labelSet.setOpaque(false);///////////////背景を透明にする
		return labelSet;
	}

	private static JPanel panelSetLR(Object left, Object right) {
		JPanel panelSet = new JPanel();
		format(panelSet);
		panelSet.setLayout(new BoxLayout(panelSet, BoxLayout.X_AXIS));
		if (left != null) {
			panelSet.add((Component) left);
		}
		if (right != null) {
			panelSet.add((Component) right);
		}
		return panelSet;
	}

	private static JPanel panelSetUD(Object up, Object down) {
		JPanel panelSet = new JPanel();
		format(panelSet);
		panelSet.setLayout(new BoxLayout(panelSet, BoxLayout.Y_AXIS));
		if (up != null) {
			panelSet.add((Component) up);
		}
		if (down != null) {
			panelSet.add((Component) down);
		}
		return panelSet;
	}

	private static JPanel panelSetWCE(Object west, Object center, Object east) {
		JPanel panelSet = new JPanel();
		format(panelSet);
		panelSet.setLayout(new BorderLayout());
		if (west != null) {
			panelSet.add((Component) west, BorderLayout.WEST);
		}
		if (center != null) {
			panelSet.add((Component) center, BorderLayout.CENTER);
		}
		if (east != null) {
			panelSet.add((Component) east, BorderLayout.EAST);
		}
		return panelSet;
	}

	private static JPanel panelSetNCS(Object north, Object center, Object south) {
		JPanel panelSet = new JPanel();
		format(panelSet);
		panelSet.setLayout(new BorderLayout());
		if (north != null) {
			panelSet.add((Component) north, BorderLayout.NORTH);
		}
		if (center != null) {
			panelSet.add((Component) center, BorderLayout.CENTER);
		}
		if (south != null) {
			panelSet.add((Component) south, BorderLayout.SOUTH);
		}
		return panelSet;
	}

	private static JPanel panelSetTMB(Object top, Object middle, Object bottom) {
		JPanel panelSet = new JPanel();
		format(panelSet);
		panelSet.setLayout(new FlowLayout());
		if (top != null) {
			panelSet.add((Component) top);
		}
		if (middle != null) {
			panelSet.add((Component) middle);
		}
		if (bottom != null) {
			panelSet.add((Component) bottom);
		}
		return panelSet;
	}

	private static void outer() {
		if (frame != null) {
			frame.setVisible(false);
		}
		frame = new JFrame("介護記録");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(w*100, h*100);
		frame.getContentPane().setBackground(Color.BLACK);
		frame.setLayout(new FlowLayout());

		changePanelSet = new JPanel();
		format(changePanelSet);
		cardLayout = new CardLayout();
		changePanelSet.setLayout(cardLayout);

		changePanelSet.add(panelSet(), "通常");

		clear = new JPanel();
		format(clear);
		changePanelSet.add(clear, "背景");

		frame.add(changePanelSet);

		change("通常");

		frame.setVisible(true);

	}

//	changePanelSetの表示カード書き換え
	private static void change(String mode) {

		cardLayout.removeLayoutComponent(panelSet);
		changePanelSet.add(panelSet, mode);
		cardLayout.show(changePanelSet, mode);
	}


//	panelSet更新後表示カード書き換え
	private static void change() {
		panelSet();
		change("通常");
	}

	private static JPanel panelSet() {

		panelN.setPreferredSize(new Dimension(w*70, h*13));

		JPanel panelSetC = new JPanel();
		format(panelSetC);
		panelSetC.setPreferredSize(new Dimension(w*70, h*80));
		panelSetC.setLayout(new BoxLayout(panelSetC, BoxLayout.Y_AXIS));
		panelSetC.add(panelN);
		panelSetC.add(panelC);
		panelSetC.add(panelS);

		panelSet = new JPanel();
		format(panelSet);
		panelSet.setLayout(new FlowLayout());
		panelSet.add(panelW);
		panelSet.add(b());
		panelSet.add(panelSetC);
		panelSet.add(b());
		panelSet.add(panelE);

		return panelSet;

	}

	private static void centerSet(Object west, Object center, Object east) {

		JLabel space = labelSet("                                       ");
		JPanel panelQ = panelSetWCE(west, center, east);
		JPanel panelQA = panelSetWCE(space, panelQ, space);
		int rows = 7;
		int columns = 75;
		JTextArea blankAreaN = textAreaSet(" ", rows, columns);
		JTextArea blankAreaS = textAreaSet(" ", rows, columns);

		panelC = panelSetNCS(blankAreaN, panelQA, blankAreaS);

		change();
	}

	private static void format(Component component, int width, int height) {
		format(component);
//		component.setSize(width, height);
		component.setPreferredSize(new Dimension(width, height));
	}

	private static void format(Component component) {
		format(component, fontSize);
	}

	private static void format(Component component, int fontSize) {

		component.setFont(new Font("Monospaced", Font.BOLD, fontSize));
		component.setForeground(Color.WHITE);
		component.setBackground(Color.BLACK);

//		if ((mode > 0 && Main.getParty()[1].getHp() < 1)) {
//			component.setForeground(Color.RED);
//		}

	}

	private static Border border() {

		border = new LineBorder(Color.WHITE, 2, true);

		return border;
	}

	//はい か いいえ の選択用部品
	static void que() {

		labelC = labelSet(tex);//質問

		JPanel bPanel = new JPanel();
		format(bPanel, 100, 500);
		bPanel.setLayout(new GridLayout(2, 0, 0, 0));

		JButton[] button = new JButton[2];// /////////////////ボタンの数

		for (int i = 0; i < 2; i++) {
			String bN = (String) ynList[i];
			button[i] = new JButton(bN);
			format(button[i]);
			button[i].setFocusPainted(false);
			button[i].addActionListener(Main.getSc());
			button[i].addKeyListener(Main.getSc());
			button[i].setBorder(border());

			bPanel.add(button[i]);
		}

		centerSet(space, labelC, bPanel);

		// more();
	}

	public void menu(Object[] mList) {

		JPanel panel = new JPanel();
		format(panel);
		panel.setLayout(new GridLayout(5, 0, 0, 0));
		panel.setPreferredSize(new Dimension(w*10, h*50));

		JButton[] button = new JButton[mList.length];// /////////////////ボタンの数

		for (int i = 0; i < (mList.length); i++) {
			String bN = String.valueOf(mList[i]);
			button[i] = new JButton(bN);
			format(button[i]);
			button[i].setMargin(new Insets(15, 10, 15, 10));///////文字周りの幅
			button[i].setFocusPainted(false);
			button[i].addActionListener(this);
			button[i].addKeyListener(this);
			button[i].setBorder(border());
			panel.add(button[i]);
		}

		String bN = cancel;
		JButton jButton = new JButton(bN);
		format(jButton);
		jButton.setMargin(new Insets(20, 8, 20, 8));///////文字周りの幅
		jButton.setFocusPainted(false);
		jButton.addActionListener(this);
		jButton.addKeyListener(this);
		jButton.setBorder(border());
		jButton.setPreferredSize(new Dimension(w*10, h*13));
//			panel.add(jButton);

		JPanel bPanel = panelSetNCS(panel, menuAreaB, jButton);
		bPanel.setPreferredSize(new Dimension(w*10, h*80));

		panelE = panelSetWCE(null, bPanel, null);

	}

	public void actionPerformed(ActionEvent e) {

		String select = e.getActionCommand();

		buttonName = select;

		System.out.println("");// ////////////////////////////////////////
		System.out.println("buttonName = " + buttonName);// //////////////////////////
		System.out.println("");// ////////////////////////////////////////

		switch (mode) {
			case 0 ://最初
				if (buttonName.equals(ynList[0])) {

					tableData = new TableData();
					toNormal();
				}
				if (buttonName.equals(ynList[1])) {

					begin();
					opening();

				} else {
					opening();
				}
				break;

			case 1 ://メイン画面
				new OnTime(selectDate);
				count = 0;
				mainAction(buttonName);
				break;

			case 2 ://行事予定表

				count = 0;
				eventAction(buttonName);
				break;

			case 3 ://To-Do登録

				count = 0;
				toDoAction(buttonName);
				break;

			case 4 ://事業所情報

				break;

			case 5 ://

				break;

			default :

				break;
		}

		actionPerformedSwitch1();

	}

	private void begin() {
		tex = "事業所の名前を入力して下さい";
		inputName("名前");
	}

	private void inputName(String s) {

			buttonName = null;

			inpT = new JTextArea(1, 20);

			format(inpT);

			inpT.setBorder(border());

			JPanel bPanel = new JPanel();
			format(bPanel, 100, 500);
			bPanel.setLayout(new GridLayout(0, 2, 0, 0));
			bPanel.setPreferredSize(new Dimension(60, 20));

			int bI = 1;// //////////////////////////////////ボタンの数

			JButton[] button = new JButton[bI];

			String[] bList = { "OK" };

			for (int i = 0; i < bI; i++) {
				String bN = (String) bList[i];
				button[i] = new JButton(bN);
				format(button[i], 20, 10);
	//				button[i].setMargin(new Insets(20, 10, 20, 10));///////文字周りの幅
				button[i].setFocusPainted(false);
				button[i].addActionListener(this);
				button[i].addKeyListener(this);
				button[i].setBorder(border());
				bPanel.add(button[i]);
			}

			q = labelSet(tex);

			JPanel a = panelSetLR(inpT, bPanel);

			centerSet(space, q, a);

		}

	private void opening() {

		if (buttonName.equals("OK")) {
			officeName = null;
			String inputName = inpT.getText();

			int p = 0;
			char[] chars = inputName.toCharArray();
			for (int i = 0; i < chars.length; i++) {
				p += (String.valueOf(chars[i]).getBytes().length);
			}
			System.out.println("");// ////////////////////////////////////////
			System.out.println("文字バイト数 = " + p);// /////////////////////
			System.out.println("");// ////////////////////////////////////////

			while (officeName == null) {
				if (p < 50) {
					if (inputName.equals("")) {
						inputName = Main.getOfficeName();
					}
					Main.setOfficeName(inputName);
					officeName = inputName;

					System.out.println("");// ////////////////////////////////////////
					System.out.println("officeName = " + officeName);// ////////////////////////
					System.out.println("");// ////////////////////////////////////////
				} else {

					System.out.println("");// ////////////////////////////////////////
					System.out.println("officeName = " + officeName);// ////////////////////////
					System.out.println("");// ////////////////////////////////////////

					buttonName.equals(null);
					tex = "もう少し短い名前でお願いします";
					change();
					inputName("名前");
				}
			}

			Main.begin();
			tableData = new TableData();
			tableData.start();
			story = new Story();
			story.on("[ " + officeName + " ] の介護記録を開始します");
			prologue();
		}
		if (buttonName.equals(ent)) {

			toNormal();
		}
	}


	void prologue() {

		System.out.println("");//////////////////////////////////////////
		System.out.println("prologue() します");/////////////////////////
		System.out.println("");//////////////////////////////////////////

		buttonName = null;

		partyStBlank();
		info("", "", "");
		scene();
		menu(Command.menu());
		comment();

		change();
	}


	private void toNormal() {

		System.out.println("");//////////////////////////////////////////
		System.out.println("toNormal() します");//////////////////////////////
		System.out.println("");//////////////////////////////////////////

		setMode(1);/////////////////////////////////////通常モードへ
		TableData.allUser();
		data();

		setMessage("どうしますか?");
		normal();
		targetTableName = TableData.getTableName();
	}

	private void normal() {

				System.out.println("");//////////////////////////////////////////
				System.out.println("normal() します");//////////////////////////////
				System.out.println("");//////////////////////////////////////////

				buttonName = null;

				officeStatus();
				info(dateInfo(), timeInfo(), toDoInfo());
				centerTable();
				menu = new String[]{ "利用者選択", "行事予定表", "To-Do一覧","事業所情報" , "データ変更" };

				menu(menu);
				comment();
				change();

			}

	private void mainAction(String selectButtonName) {

		System.out.println("");//////////////////////////////////////////
		System.out.println("action(" + selectButtonName + ") します");////////
		System.out.println("");//////////////////////////////////////////

		if (selectButtonName.equals(menu[0])) {//利用者選択

			if(selectRow() < 0){
				userSelect();
			}else{
				personal(selectRow());
			}
		}

		if (selectButtonName.equals(menu[1])) {//行事予定表

			event();
		}

		if (selectButtonName.equals(menu[2])) {//To-Do登録

			routine();
		}

		if (selectButtonName.equals(menu[3])) {//事業所情報

//			officeInfo();

			toNormal();
		}

		if (selectButtonName.equals(menu[4])) {//データ変更

			cutOut();
			toNormal();
		}

		if (selectButtonName.equals(cancel)) {

			toNormal();
		}
	}

	private void cutOut() {

		if (selectRow() == -1) {

			showMessageD("入力行を選択して下さい", "入力行未選択");
		} else {

			cutOutData = selectRowData();

			Object[] columns = OnTime.getColumns();

			Object[] whereColumn = new Object[]{ columns[0], columns[1], columns[2] };

			Object[] whereData = new Object[]{ cutOutData[0], cutOutData[1], selectDate };

			Object[] selectData = new Object[]{ "変更", "移動", "削除" };

			String action = selectD("選択した行をどうしますか？", selectData);

			if (action.equals(selectData[0])) {

				Object[] setRowData = rewriteUser();

				if(setRowData[1].equals(cutOutData[1]) == false){

					Object upDateData = setRowData[1];

					updateOnTime(columns[1], upDateData, whereColumn, whereData);
				}
			}

			if (action.equals(selectData[1])) {

				int upDateData = movingUser();

				if(upDateData > 0){
					updateOnTime(columns[0], upDateData, whereColumn, whereData);
				}
			}

			if (action.equals(selectData[2])) {
				removeOwner(cutOutData[0]);
			}

			toNormal();

		}
	}

	private void addOnTimeU(Object[] setRowData) {

		String question = setRowData[0] +" "+ setRowData[1] + " " + selectDate;

		question = question + " のToDoリストを追加しますか？";

		if (checkD(question) == true) {

			new OnTime(selectDate);

			OnTime.insertBlankU(selectDate, setRowData[0], setRowData[1]);
		}
	}

	private void updateOnTime(Object updateColumn, Object updateData, Object[] whereColumn, Object[] whereData) {

		String question = selectDate + " の ToDoリスト も " + updateColumn + " = " + updateData + " に変更しますか？";

		boolean answer = checkD(question);

		if (answer == true) {

			new OnTime(selectDate);

			OnTime.update(updateColumn, updateData, whereColumn, whereData);
		}
	}

	private Object[] rewriteUser() {

		return TableData.rewriteUser(selectRowData());
	}

	private int movingUser() {
		Object[] selectData = new Object[TableData.getCapacity()];

		Object[] columnData = columns();

		int nextRoomNunber = 0;

		for (int i = 0; i < selectData.length; i++) {
			selectData[i] = i + 1;
		}
		nextRoomNunber = Integer.parseInt(selectD("移動先の" + columnData[0] + "を選択して下さい", selectData));

		System.out.println("");//////////////////////////////////////////
		System.out.println(" nextRoomNunber = " + nextRoomNunber + " です");//////////////////////////////
		System.out.println("");//////////////////////////////////////////

		if (nextRoomNunber > 0) {

			String question = "データを移動しますか？";

			if (checkD(question) == true) {
				Object[] enptyRoomData = selectRowData(nextRoomNunber - 1);

				System.out.println("");//////////////////////////////////////////
				System.out.println(" enptyRoomData[1] = " + enptyRoomData[1] + " です");//////////////////////////////
				System.out.println("");//////////////////////////////////////////

				if (enptyRoomData[1].equals("")) {

					int nowRoomNunber = Integer.parseInt(cutOutData[0] + "");

					DerbyC.delete(targetTableName, columnData[0], nextRoomNunber);

					DerbyC.update(targetTableName, columnData[0], nextRoomNunber, columnData[0], nowRoomNunber);

					TableData.insertBlankRow(nowRoomNunber);

				} else {
					showMessageD("移動先にはデータが存在します", "移動出来ません");

					nextRoomNunber = 0;
				}
			}
		}
		return nextRoomNunber;
	}

	private void removeOwner(Object roomNumber) {

		String question = "" + columns()[0] + roomNumber + "のデータを削除しますか？";

		if (checkD(question) == true) {
			removeRow("");
			TableData.insertBlankRow(Integer.parseInt(roomNumber + ""));

		}
	}

	private void userSelect() {

//		allUser();

		setMode(10);

		menu = new String[]{ "1-5", "6-10", "11-15", "16-20" , "データ切り取り"};

		setMessage("⇒部屋番号を選択しで下さい");

		user();
	}

//	private void allUser() {
//
//		data = TableData.allUser();
//
//	}

	private void event() {

		setMode(2);

		schedule();

		targetTableName = Event.getTableName();

		beforeData = tableData(tableModel);
	}

	private void schedule() {

		System.out.println("");//////////////////////////////////////////
		System.out.println("schedule() します");//////////////////////////
		System.out.println("");//////////////////////////////////////////

		buttonName = null;

		officeStatus();
		info(dateInfo(), timeInfo(), toDoInfo());
		centerTableE(new Event());
		menu = new String[]{"利用者一覧", "月選択", "行事入力", "行事変更", "行事削除"};
		menu(menu);
		comment("⇒選択しで下さい");
		change();

	}

	private void eventAction(String selectButtonName) {//行事予定表

		System.out.println("");//////////////////////////////////////////
		System.out.println("eventAction(" + selectButtonName + ") します");////////
		System.out.println("");//////////////////////////////////////////

		if (selectButtonName.equals(menu[0])) {//利用者選択

			recording();

			toNormal();
		}

		if (selectButtonName.equals(menu[1])) {//月選択

			recording();

			Event.selectMonth();

			event();
		}

		if (selectButtonName.equals(menu[2])) {//行事入力

			recording();

			Event.entry();

			event();
		}

		if (selectButtonName.equals(menu[3])) {//行事変更

			recording();

			Event.rewriteData(selectRowData());

			event();
		}

		if (selectButtonName.equals(menu[4])) {//行事削除

			recording();

			removeRow();

			event();
		}

		if (selectButtonName.equals(cancel)) {

			recording();

			toNormal();
		}

		if (selectButtonName.equals(undo)) {
			undo();
		}

		if (selectButtonName.equals(redo)) {
			redo();
		}
	}

	private void routine() {

		rout = new Routine();

		targetTableName = Routine.getTableName();

		setMode(3);

		toDo();

		beforeData = tableData(tableModel);
	}

	private void toDo() {

		System.out.println("");//////////////////////////////////////////
		System.out.println("toDo() します");//////////////////////////
		System.out.println("");//////////////////////////////////////////

		buttonName = null;

		officeStatus();
		info(dateInfo(), timeInfo(), toDoInfo());
		centerTableE(rout,rout.getWidth());
		menu = new String[]{"利用者一覧", "To-Do作成", "To-Do変更", "To-Do削除"};
		menu(menu);
		comment("⇒選択しで下さい");
		change();

	}

	private void toDoAction(String selectButtonName) {//

		System.out.println("");//////////////////////////////////////////
		System.out.println("action(" + selectButtonName + ") します");////////
		System.out.println("");//////////////////////////////////////////

		if (selectButtonName.equals(menu[0])) {//利用者一覧

			recording();

			toNormal();
		}

		if (selectButtonName.equals(menu[1])) {//新規作成

			recording();

			Object[] newRoutine = Routine.entry();

			new OnTime(selectDate);

//			OnTime.insertBlankR(selectDate, newRoutine[0], newRoutine[1]);

			routine();
		}

		if (selectButtonName.equals(menu[2])) {//変更

			recording();

			Object[] newRoutine = Routine.rewriteData(selectRowData());

			new OnTime(selectDate);

			OnTime.insertBlankR(selectDate, newRoutine[0], newRoutine[1]);

			routine();
		}

		if (selectButtonName.equals(menu[3])) {//削除

			recording();

			removeRow();

			routine();
		}

		if (selectButtonName.equals(cancel)) {

			recording();

			toNormal();
		}

		if (selectButtonName.equals(undo)) {

			undo();
		}

		if (selectButtonName.equals(redo)) {

			redo();
		}
	}

	private void undo() {

		System.out.println("");/////////////////////
		System.out.println("undo() します");////////
		System.out.println("");/////////////////////

		if(actionName.equals("changeData")){
			changeData(targetTableName, columns(), beforeData, oldData, "");
		}
		if(actionName.equals("changeRow")){
			changeRow(targetTableName, columns(), changedRowData, lostRowData,"");
		}
		if(actionName.equals("removeRow")){
			DerbyC.insert(targetTableName, lostRowData);

			insertRowData = Arrays.copyOf(lostRowData,lostRowData.length);
			beforeData = tableData(tableModel);
			ent = undo;
			actionName = "removeRow";
		}
		ent = redo;
	}

	private void redo() {

		System.out.println("");/////////////////////////////////////////
		System.out.println("redo() します");////////////////////////////
		System.out.println("");/////////////////////////////////////////
		System.out.println("  actionName = " + actionName + " です");///
		System.out.println("");/////////////////////////////////////////

		if(actionName.equals("changeData")){
			changeData(targetTableName, columns(), tableData(tableModel), oldData);
		}
		if(actionName.equals("changeRow")){
			changeRow(targetTableName, columns(), changedRowData, lostRowData);
		}
		if(actionName.equals("removeRow")){

			DerbyC.delete(targetTableName, DerbyC.andWhere(columns(), insertRowData));

			lostRowData = Arrays.copyOf(insertRowData, insertRowData.length);
			beforeData = tableData(tableModel);
			ent = undo;
			actionName = "removeRow";
		}
		ent = undo;
	}

	private void recording() {

		if(confirmChange() == true ){

			changeData();
		}
	}

	private void recordingJ() {

		System.out.println("");///////////////////////////
		System.out.println("recordingJ() します");////////
		System.out.println("");///////////////////////////

		int columnsLength = Private.getColumns().length;

		Object[][] tableData = tableData();

		Object[][] newData = new Object[tableData.length][columnsLength];


		for (int i = 0; i < newData.length; i++) {
			newData[i][0] = selectRoom;
			newData[i][1] = selectName;
			newData[i][2] = selectDate;
			for (int j = 3; j < columnsLength; j++) {
				newData[i][j] = tableData[i][j - 3];
			}
		}

		recording(beforeData, newData);
	}

	private void recordingR() {

		System.out.println("");//////////////////////////////////////////
		System.out.println("recordingR() します");////////
		System.out.println("");//////////////////////////////////////////

		Object[][] newData = OnTime.data(tableData(),selectDate);

		recording(beforeData, newData);
	}

	private void recording(Object[][] objects) {

		if(confirmChange(objects) == true ){

			changeData();
		}
	}

	private void recording(Object setTableName) {

		if(confirmChange() == true ){

			Object[] columns = DerbyC.columns(setTableName);

			changeData(setTableName,columns);
		}
	}

	private void recording(Object[][] oldData, Object[][] newData) {

		recording(targetTableName, oldData, newData);
	}

	private void recording(Object setTableName, Object[][] oldData, Object[][] newData) {

		if(different(oldData, newData) == true ){

			changeData(setTableName, oldData, newData);
		}
	}

	private boolean confirmChange() {
		return confirmChange(tableData());
	}

	private boolean confirmChange(Object[][] nowTableData) {

		System.out.println("");//////////////////////////////////////////
		System.out.println("confirmChange(Object[][] nowTableData) します");////////
		System.out.println("");//////////////////////////////////////////

		boolean change = false;

		if (beforeData.length > 0 && nowTableData != null) {

			System.out.println("");//////////////////////////////////////////
			System.out.println("beforeData.length =  " + beforeData.length + " です");////////
			System.out.println("beforeData[0].length =  " + beforeData[0].length + " です");////////
			System.out.println("nowTableData[0].length =  " + nowTableData[0].length + " です");////////
			System.out.println("");//////////////////////////////////////////


			if (beforeData.length == nowTableData.length) {

				int changeCount = 0;

				for (int i = 0; i < beforeData.length; i++) {

					for (int j = 0; j < beforeData[0].length; j++) {

						if ((beforeData[i][j]).equals(nowTableData[i][j]) == false) {

							System.out.println("");//////////////////////////////////////////
							System.out.println(beforeData[i][j] + " が " + nowTableData[i][j] + " に変わっています");////////
							System.out.println("");//////////////////////////////////////////

							changeCount++;
						}
					}
				}
				if (changeCount > 0) change = true;

			} else {
				change = true;
			}
		}
		return change;
	}

	private boolean different(Object[][] tableDataA, Object[][] tableDataB) {

		System.out.println("");//////////////////////////////////////////
		System.out.println("different(Object[][] tableDataA, Object[][] tableDataB) します");////////
		System.out.println("");//////////////////////////////////////////

		boolean change = false;

		if (tableDataA.length > 0 && tableDataB != null) {

			System.out.println("");////////////////////////////////////////////////////////////
			System.out.print("tableDataA.length =  " + tableDataA.length + " です  ");/////////
			System.out.println("tableDataA[0].length =  " + tableDataA[0].length + " です");///
			System.out.print ("tableDataB.length =  " + tableDataB.length + " です  ");////////
			System.out.println("tableDataB[0].length =  " + tableDataB[0].length + " です");///
			System.out.println("");////////////////////////////////////////////////////////////


			if (tableDataA.length == tableDataB.length) {

				int changeCount = 0;

				for (int i = 0; i < tableDataA.length; i++) {

					for (int j = 0; j < tableDataA[0].length; j++) {

						if ((tableDataA[i][j]).equals(tableDataB[i][j]) == false) {

							System.out.println("");/////////////////////////////////////////////////////////////////////////////////////////////////
							System.out.println("[" + i + "][" + j + "]の " + tableDataA[i][j] + " が " + tableDataB[i][j] + " に変わっています");///
							System.out.println("");/////////////////////////////////////////////////////////////////////////////////////////////////

							changeCount++;
						}
					}
				}
				if (changeCount > 0) change = true;

			} else {
				change = true;
			}
		}

		System.out.println("");////////////////////////////////////
		System.out.println("change = " + change + " です");////////
		System.out.println("");////////////////////////////////////

		return change;
	}

	private Object[] columns() {
		return DerbyC.columns(targetTableName);
	}

	//	private void changeRow() {
//
//	changeRow(selectTable, selectRowData(), selectRowData());
//}

	private void changeRow(Object[] columnData) {

		changeRow(targetTableName, columnData, clickedData, selectRowData());
	}

	private void changeRow(Object setTableName, Object[] columnData) {

		changeRow(setTableName, columnData, clickedData, selectRowData());
	}

	private void changeRow(Object setTableName, Object[] columnData, Object[] insertRowData) {

		changeRow(setTableName, columnData, selectRowData(), insertRowData);
	}

	private void changeRow( Object[] columnData, Object[] deleteRowData, Object[] insertRowData) {
		changeRow(targetTableName, columnData, deleteRowData, insertRowData);
	}

	private void changeRow( Object[] columnData, Object[] deleteRowData, Object[] insertRowData, String checkDQuestion) {
		changeRow(targetTableName, columnData, deleteRowData, insertRowData, checkDQuestion);
	}

	private void changeRow(Object setTableName, Object[] columnData, Object[] deleteRowData, Object[] insertRowData) {

		String checkDQuestion = "選択行を変更しますか？";

		changeRow(setTableName, columnData, deleteRowData, insertRowData, checkDQuestion);
	}

	private void changeRow(Object setTableName, Object[] columnData, Object[] deleteRowData, Object[] insertRowData, String checkDQuestion) {

		boolean check;

		if (checkDQuestion.equals("")){
			check = true;
		}else{
			check = checkD(checkDQuestion);
		}


			if (check == true) {

				DerbyC.delete(setTableName, DerbyC.andWhere(columnData, deleteRowData));
				DerbyC.insert(setTableName, insertRowData);

				lostRowData = Arrays.copyOf(deleteRowData, deleteRowData.length);
				changedRowData = Arrays.copyOf(insertRowData, insertRowData.length);
				beforeData = tableData(tableModel);
				ent = undo;
				actionName = "changeRow";
		}
	}

	private void changeData() {

		changeData(targetTableName, columns(), beforeData, tableData());
	}

	private void changeData(Object setTableName, Object[] columnData) {

		changeData(setTableName, columnData, beforeData, tableData());

	}

	private void changeData(Object setTableName,  Object[][] deleteData, Object[][] insertData) {

	changeData(setTableName,DerbyC.columns(setTableName), deleteData, insertData);
	}

	private void changeData(Object setTableName, Object[] columnData, Object[][] deleteData, Object[][] insertData) {

		String question = "データを更新しますか？";

		changeData( setTableName,  columnData,  deleteData,  insertData,  question);

	}

	private void changeData(Object setTableName, Object[] columnData, Object[][] deleteData, Object[][] insertData, String question) {

		String method = "changeData(" + setTableName + ", Object[] columnData, Object[][] deleteData, Object[][] insertData, " + question + ")";

		System.out.println("");///////////////////////////////////
		System.out.println("changeData(" + method + " します");///
		System.out.println("");///////////////////////////////////

		System.out.print("");//////////////////////////////////////////////////////////////////////////////////
		System.out.println("  deleteData = [" + deleteData.length + "][" + deleteData[0].length + "] です");///
		System.out.print("");//////////////////////////////////////////////////////////////////////////////////
		System.out.println("  insertData = [" + insertData.length + "][" + insertData[0].length + "] です");///
		System.out.println("");////////////////////////////////////////////////////////////////////////////////

		boolean check;

		if (question.equals("")){
			check = true;
		}else{
			check = checkD(question);
		}

		if (check == true) {

			int change;

			for (int i = 0; i < deleteData.length; i++) {

				change = 0;

				for (int j = 0; j < columnData.length; j++) {
					if (deleteData[i][j].equals(insertData[i][j]) == false) change++;
				}

				if (change > 0) {

					String deleteI = "";

					String insertI = "";

					for (int j = 0; j < columnData.length; j++) {
						deleteI = deleteI + "[" + deleteData[i][j] + "]";
						insertI = insertI + "[" + insertData[i][j] + "]";
					}

					System.out.println("");///////////////////////////////////////////
					System.out.println("deleteData[" + i + "] =  " + deleteI);//////////
					System.out.println("");///////////////////////////////////////////

					System.out.println("");///////////////////////////////////////////
					System.out.println("insertData[" + i + "] =  " + insertI);//////////
					System.out.println("");///////////////////////////////////////////

					DerbyC.delete(setTableName, DerbyC.andWhere(columnData, deleteData[i]));
					DerbyC.insert(setTableName, insertData[i]);
				}
			}
			oldData = Arrays.copyOf(deleteData, deleteData.length);
			beforeData = Arrays.copyOf(insertData, insertData.length);
			ent = undo;
			actionName = "changeData";
		}
	}

	private void removeRow() {

		removeRow(targetTableName, columns(), selectRowData());
	}

	private void removeRow(String checkDQuestion) {

		removeRow(targetTableName, columns(), selectRowData(), checkDQuestion);
	}

	private void removeRow(int selectRow) {

		removeRow(targetTableName, columns(), selectRowData(selectRow));
	}

	private void removeRow(Object[] selectRowData) {
		removeRow(targetTableName,  columns(), selectRowData);
	}

	private void removeRow(Object setTableName, Object[] columnData) {
		removeRow(setTableName, columnData, selectRowData());
	}

	private void removeRow(Object[] columnData, Object[] selectRowData) {
		removeRow(targetTableName, columnData, selectRowData);
	}


	private void removeRow(Object setTableName, Object[] columnData, Object[] selectRowData) {

		String checkDQuestion = "選択行を削除しますか？";

		removeRow(setTableName, columnData, selectRowData, checkDQuestion);

	}

	private void removeRow(Object setTableName, Object[] columnData, Object[] selectRowData, String checkDQuestion) {

		if(checkDQuestion.equals("")){
			DerbyC.delete(setTableName, DerbyC.andWhere(columnData, selectRowData));

			lostRowData = Arrays.copyOf(selectRowData, selectRowData.length);
			beforeData = tableData(tableModel);
			ent = undo;
			actionName = "removeRow";
		}else{

		if(checkD(checkDQuestion) == true){

			DerbyC.delete(setTableName, DerbyC.andWhere(columnData, selectRowData));

			lostRowData = Arrays.copyOf(selectRowData, selectRowData.length);
			beforeData = tableData(tableModel);
			ent = undo;
			actionName = "removeRow";
		}
		}
	}

	private Object[] selectRowData() {
		return selectRowData(selectRow());
	}

	private Object[] selectRowData(int selectRow) {

		int columnLength = selectJTable.getColumnCount();

		Object[] rowData = new Object[columnLength];

		for (int i = 0; i < columnLength; i++) {
			rowData[i] = selectJTable.getValueAt(selectRow, i);
		}
		return rowData;
	}

	public static int selectColumn() {
		int column = selectJTable.getSelectedColumn();

		System.out.println("");////////////////////////////////////
		System.out.println("selectColumn() = " + column);///////////
		System.out.println("");////////////////////////////////////

		return column;
	}


	public static int selectRow() {

		return selectRow(selectJTable);
	}

	public static int selectRow(JTable setTable) {
		int row = setTable.getSelectedRow();

		System.out.println("");///////////////////////////////////////
		System.out.println("SelectRow() = " + row);///
		System.out.println("");///////////////////////////////////////

		return row;
	}

	private Object[][] tableData() {

		return tableData(tableModel);
	}

	private Object[][] tableData(JTable setJTable) {

		int rowCount = setJTable.getRowCount();

		int columnCount = setJTable.getColumnCount();

		Object[][] tableData = new Object[rowCount][columnCount];

		for (int i = 0; i < rowCount; i++) {
			for (int j = 0; j < columnCount; j++) {
				tableData[i][j] = setJTable.getValueAt(i, j);
			}
		}
		return tableData;
	}

	private Object[][] tableData(TableModel model) {

		int rowCount = model.getRowCount();

		int columnCount = model.getColumnCount();

		System.out.println("");///////////////////////////////////////
		System.out.println("tableData(TableModel " + model.getClass() + ") します");///
		System.out.println("");///////////////////////////////////////
		System.out.println(" rowCount = " + rowCount);///
		System.out.println(" columnCount = " + columnCount);///
		System.out.println("");///////////////////////////////////////

		Object[][] tableData = new Object[rowCount][columnCount];

		for (int i = 0; i < rowCount; i++) {
			for (int j = 0; j < columnCount; j++) {
				tableData[i][j] = model.getValueAt(i, j);
			}
		}
		return tableData;
	}

	private Object[][] data() {

		userData = TableData.getData();

		return userData;
	}

	public void actionPerformedSwitch1() {

		switch (mode) {

			case 10 :// 利用者選択 [利用者×５]

				setMessage("⇒利用者様を選択しで下さい");

				Object[] menuList = menu;

				for (int i = 0; i < menu.length - 1; i++) {

					if (buttonName.equals(menu[i])) {

						setMode(11 + i);

						menuList = new String[5];

						int position = i * menuList.length;

						for (int j = 0; j < menuList.length; j++) {

							menuList[j] = userData[position + j][0] + "." + userData[position + j][1];

							System.out.println("");/////////////////////////////////////////////////////////////
							System.out.println("menuList[" + j + "] = " + menuList[j]);///
							System.out.println("");/////////////////////////////////////////////////////////////
						}
					}
				}

				if (buttonName.equals(menu[menu.length - 1])) {//データ切り取り
					cutOut();
					break;
				}

				if (buttonName.equals(cancel)) {
					toNormal();
					break;
				}
				menu = menuList;
				user();

				break;


			case 11 :// 利用者選択[1-5]

				userSelect(1);

				break;

			case 12 :// 利用者選択[6-10]

				userSelect(2);

				break;

			case 13 :// 利用者選択[11-15]

				userSelect(3);

				break;

			case 14 :// 利用者選択[16-20]

				userSelect(4);

				break;

			case 15 :// 利用者選択[21-25]

				userSelect(5);

				break;


			case 111 ://journal
				count = 0;

//				targetTableName = String.valueOf(selectName).replaceAll(" ","").replaceAll("　","");

				journalAction(buttonName);

				break;

			case 1112 :// 定期入力
				count = 0;

				targetTableName = OnTime.getTableName();

				regularAction(buttonName);

				break;
		}
	}

	private void userSelect(int page) {

		int position = (page - 1) * menu.length;

		for (int i = 0; i < menu.length; i++) {

			if (buttonName.equals(menu[i])) {

				int row = position + i;

				personal(row);
			}
		}

		if (buttonName.equals(cancel)) {
			toNormal();
		}
	}

	private void personal(int row) {

		setMode(111);

		selectRoom = data()[row][0];

		selectName = data()[row][1];

		if (selectName.equals("")) {

			entryUser(selectRoom);

			toNormal();

		} else {

			TableData.user(selectRoom);

			journal();

			targetTableName = Private.getTableName();

			beforeData = Private.data(selectRoom, selectName, selectDate);

			System.out.println("");//////////////////////////////////////////
			System.out.println(" selectName = " + selectName + " です");//////////////////////////////
			System.out.println("");//////////////////////////////////////////
		}

	}

	private void entryUser(Object roomNamber) {

		Object[] setRowData = TableData.entryUser(roomNamber);

		System.out.println("");//////////////////////////////////////////
		System.out.println(" setRowData[1] = " + setRowData[1] + " です");//////////////////////////////
		System.out.println("");//////////////////////////////////////////

//		addOnTimeU(setRowData);
	}


	private void user() {

		System.out.println("");//////////////////////////////////////////
		System.out.println("user() します");//////////////////////////////
		System.out.println("");//////////////////////////////////////////

		buttonName = null;

		officeStatus();
		info(dateInfo(), timeInfo(), toDoInfo());
		centerTable();
		menu(menu);
		comment();
		change();

	}

	private void journal() {

		System.out.println("");//////////////////////////////////////////
		System.out.println("journal() します");//////////////////////////
		System.out.println("");//////////////////////////////////////////

		buttonName = null;

		userStatus();
		info(dateInfo(), timeInfo(), toDoInfo());
		centerTableU();
		menu = new String[]{ "利用者選択", "常時入力", "定期入力", "生活記録" , "日付選択"};
		menu(menu);
		comment();
		change();

	}

	private void journalAction(String selectButtonName) {

		System.out.println("");/////////////////////////////////////////////////
		System.out.println("journalAction(" + selectButtonName + ") します");///
		System.out.println("");/////////////////////////////////////////////////

		if (selectButtonName.equals(menu[0])) {//利用者選択

			recordingJ();

			toNormal();
		}

		if (selectButtonName.equals(menu[1])) {//常時入力

			recordingJ();

			situation();

			journal();
		}

		if (selectButtonName.equals(menu[2])) {//定期入力

			recordingJ();

			regularRec();
		}

		if (selectButtonName.equals(menu[3])) {//生活記録

			recordingJ();


			journal();

		}

		if (selectButtonName.equals(menu[4])) {//日付選択

			recordingJ();

			selectDate(TableData.parseYMDE(selectDate));

			journal();

		}

		if (buttonName.equals(cancel)) {

			recordingJ();

			toNormal();
		}

		if (selectButtonName.equals(undo)) {
			undo();
		}

		if (selectButtonName.equals(redo)) {
			redo();
		}
	}

	private void situation() {

		System.out.println("");//////////////////////////////////////////
		System.out.println("situation() します");////////
		System.out.println("");//////////////////////////////////////////

		int selectRow = selectRow();

		if (selectRow() == -1) {
			showMessageD("入力行を選択して下さい", "入力行未選択");
		} else {

			String onText;

			String situation = null;

			String[] columns = Private.getColumns();

			String inputcolumn = columns[12];

			String time = TableData.time(inputcolumn + "を記録する", selectRowData(selectRow)[0]);

			if (time == null) {
				onText = "";
			} else {

				situation = inpDS(inputcolumn + "を入力して下さい");

				if (situation == null) {
					onText = "";

				} else {

					if (situation.equals("")) {

						Object[] selectList = TableData.distinctList(targetTableName, inputcolumn, inputcolumn);
						situation = Screen.selectDS("選択して下さい", selectList);
					}
					if (situation == null) {
						onText = "";

					} else {
						onText = time + " " + situation;
					}
				}
			}
			System.out.println("");/////////////////////////////////////////////////////
			System.out.println("入力値 = " + onText + " です");/////////////////////////
			System.out.println("");/////////////////////////////////////////////////////

			Object targetCellData = Private.getList()[selectRow][9];

			if (onText.equals("")) {
				onText = targetCellData + "";
			} else {
				if (targetCellData.equals("") == false) {
					onText = targetCellData + " " + onText;
				}
			}

			if(onText.equals(targetCellData) == false){// 変化があれば

			// 更新する

			Object[] whereColumns = new Object[]{ columns[0], columns[1], columns[2], columns[3] };

			Object[] whererows = new Object[]{ selectRoom, selectName, selectDate, selectJTable.getValueAt(selectRow, 0) };

			DerbyC.update(targetTableName, inputcolumn, onText, whereColumns, whererows);

			//String targetCellData = String.valueOf(Private.getList() [selectRow()][9]);
			//
			//Private.getList()[selectRow()][9] = targetCellData + onText;

			oldData = Arrays.copyOf(beforeData, beforeData.length);
			beforeData = Private.data(selectRoom, selectName, selectDate);
			ent = undo;
			actionName = "changeData";
			}
		}
	}

	private void regularRec() {

		setMode(1112);

		onTime();

		targetTableName = OnTime.getTableName();

		beforeData = OnTime.data(tableData(tableModel), selectDate);

	}

	private void onTime() {

		System.out.println("");//////////////////////////////////////////
		System.out.println("onTime() します");//////////////////////////
		System.out.println("");//////////////////////////////////////////

		buttonName = null;

		userStatus();
		info(dateInfo(), timeInfo(), toDoInfo());
		centerTableE(new OnTime(selectDate));
		menu = new String[]{ "利用者選択", "入力", "変更", "行削除", "日付選択"};
		menu(menu);
		comment();
		change();

	}

	private void regularAction(String selectButtonName) {

		System.out.println("");/////////////////////////////////////////////////
		System.out.println("regularAction(" + selectButtonName + ") します");///
		System.out.println("");/////////////////////////////////////////////////

		if (selectButtonName.equals(menu[0])) {//利用者選択

			recordingR();

			toNormal();
		}

		if (selectButtonName.equals(menu[1])) {//入力

			recordingR();

			detail();

			regularRec();
		}

		if (selectButtonName.equals(menu[2])) {//変更

			recordingR();

//			regularRec();

			regularRec();
		}

		if (selectButtonName.equals(menu[3])) {//行削除

			recordingR();

			removeRow(beforeData[selectRow()]);

			regularRec();

		}

		if (selectButtonName.equals(menu[4])) {//日付選択

			recordingR();

			selectDate(TableData.parseYMDE(selectDate));

			regularRec();

		}

		if (buttonName.equals(cancel)) {

			recordingR();

			toNormal();
		}

		if (selectButtonName.equals(undo)) {
			undo();

			regularRec();
		}

		if (selectButtonName.equals(redo)) {
			redo();

			regularRec();
		}
	}

//	private void detail() {// 行選択パターン
//
//		System.out.println("");//////////////////////////////////////////
//		System.out.println("detail() します");////////
//		System.out.println("");//////////////////////////////////////////
//
//		int selectRow = selectRow();
//
//		if (selectRow() == -1) {
//			showMessageD("入力行を選択して下さい", "入力行未選択");
//		} else {
//
//			String addedText;
//
//			String detail = null;
//
//			String[] columns = OnTime.getTableColumns();
//
//			String timeColumn = columns[0];
//
//			String detailColumn = columns[4];
//
//			Object[] selectRowData = selectRowData(selectRow);
//
//			String time = TableData.time(detailColumn + "を記録する", selectRowData[0]);
//
//			if (time == null) {
//				addedText = selectRowData[0] + "";
//			} else {
//
//				detail = inpDS(detailColumn + "を入力して下さい", selectRowData[4]);
//
//				if (detail == null) {
//					addedText = "";
//
//				} else {
//
//					if (detail.equals("")) {
//
//						String whereSql = DerbyC.is(columns[1], selectRowData[1]);
//						Object[] selectList = TableData.distinctList(targetTableName, detailColumn, whereSql, detailColumn);
//						detail = Screen.selectDS("選択して下さい", selectList);
//					}
//					if (detail == null) {
//						detail = "";
//					}
//					addedText = time + detail;
//				}
//			}
//			System.out.println("");/////////////////////////////////////////////////////
//			System.out.println("入力値 = " + addedText + " です");/////////////////////////
//			System.out.println("");/////////////////////////////////////////////////////
//
//			if (addedText.equals("" + selectRowData[0] + selectRowData[4]) == false) {// 変化があれば
//
//				// 更新する
//				Object[] setColumn = new Object[]{ timeColumn, detailColumn };
//
//				Object[] setData = new Object[]{ time, detail };
//
//				Object[] whereColumns = OnTime.getColumns();;
//
//				Object[] whererows = new Object[]{ selectRowData[2], selectRowData[3], selectDate, selectRowData[0], selectRowData[1], selectRowData[4] };
//
//				DerbyC.update(targetTableName, setColumn, setData, whereColumns, whererows);
//
//				backup(beforeData, oldData);
////				oldData = Arrays.copyOf(beforeData, beforeData.length);
////				beforeData = tableData(tableModel);
//				ent = undo;
//				actionName = "changeData";
//			}
//		}
//	}

	private void detail() {// ToDo選択パターン

		System.out.println("");//////////////////////////////////////////
		System.out.println("detail() します");////////
		System.out.println("");//////////////////////////////////////////

		String toDo = selectD("ToDoを選択して下さい", Routine.selectList(), "");

		if (toDo == null) {
			showMessageD("入力を中止します", "ToDo未選択");

		} else {

			String addedText;

			String detail = null;

			String[] columns = OnTime.getColumns();

			String timeColumn = columns[3];

			String detailColumn = columns[5];

			String[] splitToDo = toDo.split(" ", 0);

			splitToDo[0] = splitToDo[0].replace(" ", "");

			splitToDo[1] = splitToDo[1].replace(" ", "");

			Object[][] selectData = Detail.list(Screen.getSelectDate(), splitToDo[0], splitToDo[1]);


			Object[] selectList = new Object[selectData.length];

			for (int i = 0; i < selectList.length; i++) {
				selectList[i] = selectData[i][0] + " " + selectData[i][1];
			}

			String user = selectD("利用者を選択して下さい", selectList, "");

			if (user == null) {
				showMessageD("入力を中止します", "利用者未選択");

			} else {

				String[] splitUser = user.split(" ", 0);

				Object[] selectRowData = null;

				for (int i = 0; i < selectData.length; i++) {

					System.out.println("");/////////////////////////////////////////////////////
					System.out.println("splitUser[0] = " + splitUser[0] + " です");/////////////
					System.out.println("");/////////////////////////////////////////////////////
					System.out.println("selectData[i][0] = " + selectData[i][0] + " です");/////
					System.out.println("");/////////////////////////////////////////////////////


					if(splitUser[0].replace(" ", "").equals(selectData[i][0]+"")){

						selectRowData = selectData[i];

						System.out.println("");/////////////////////////////////////////////////////
						System.out.println("selectData = [" + selectData.length + "][" + selectData[0].length + "] です");/////////////
						System.out.println("");/////////////////////////////////////////////////////
					}
				}

				String time = TableData.time(detailColumn + "を記録する", splitToDo[0]);

				if (time == null) {
					addedText = selectRowData[0] + "";
				} else {

					System.out.println("");/////////////////////////////////////////////////////
					System.out.println("selectRowData[2] = " + selectRowData[2] + " です");/////////////
					System.out.println("");/////////////////////////////////////////////////////

					detail = inpDS(detailColumn + "を入力して下さい", selectRowData[2]);

					if (detail == null) {
						addedText = "";

					} else {

						if (detail.equals("")) {

							String whereSql = DerbyC.is(columns[4], splitToDo[1]);
							selectList = TableData.distinctList(targetTableName, detailColumn, whereSql, detailColumn);
							detail = Screen.selectDS("選択して下さい", selectList);
						}
						if (detail == null) {
							detail = "";
						}
						addedText = time + detail;
					}
				}
				System.out.println("");/////////////////////////////////////////////////////
				System.out.println("入力値 = " + addedText + " です");/////////////////////////
				System.out.println("");/////////////////////////////////////////////////////

				if (addedText.equals("" + selectData[0] + selectData[2]) == false) {// 変化があれば

					// 更新する
					Object[] setColumn = new Object[]{ timeColumn, detailColumn };

					Object[] setData = new Object[]{ time, detail };

					Object[] whereColumns = OnTime.getColumns();

					Object[] whererows = new Object[]{ selectRowData[0], selectRowData[1], selectDate, splitToDo[0], splitToDo[1], selectRowData[2] };

					DerbyC.update(targetTableName, setColumn, setData, whereColumns, whererows);

					backup(beforeData, oldData);
	//				oldData = Arrays.copyOf(beforeData, beforeData.length);
	//				beforeData = tableData(tableModel);
					ent = undo;
					actionName = "changeData";
				}
			}
		}
	}

	private void backup(Object[][] originalData, Object[][] copyData) {

		System.out.println("");///////////////////////////////////////////////////////////////
		System.out.println(" backup(Object[][] originalData, Object[][] copyData) します");///
		System.out.println("");///////////////////////////////////////////////////////////////

		copyData = Arrays.copyOf(originalData, originalData.length);

		System.out.print("");/////////////////////////////////////////////
		System.out.println("originalData = " + originalData + " です");///
		System.out.println("copyData = " + copyData + " です");///////////
		System.out.println("");///////////////////////////////////////////
	}

	private void selectDate(Date initialSelectionDate) {

		selectDate = TableData.sdformatYMDE(TableData.date("表示する", initialSelectionDate));
	}


	private void officeStatus() {

		System.out.println("");//////////////////////////////////////////
		System.out.println("officeStatus() します");//////////////////////////
		System.out.println("");//////////////////////////////////////////

		JTable oTab = new JTable();
		format(oTab);
		OfficeSt tableModel = new OfficeSt();
		oTab.setModel(tableModel);
//		pTab.setAutoCreateRowSorter(true);
		oTab.setRowHeight(fontSize*2);
		oTab.setShowVerticalLines(true);// 縦枠
		oTab.setShowHorizontalLines(true);// 横枠
		oTab.setPreferredSize(new Dimension(w*70, h*8));
		oTab.setBorder(border());

		DefaultTableCellRenderer tableCellRendererC = new DefaultTableCellRenderer();
		tableCellRendererC.setHorizontalAlignment(JLabel.CENTER);

		TableColumn[] name = new TableColumn[oTab.getColumnModel().getColumnCount()];

		for (int i = 0; i < name.length; i++) {
			name[i] = oTab.getColumnModel().getColumn(i);
			name[i].setCellRenderer(tableCellRendererC);
		}

		format(oTab.getTableHeader());
		oTab.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);

		new Event();

		JLabel event = labelSet(Event.dayList(selectDate));

		JPanel panel = panelSetNCS(oTab, event, null);
		format(panel);
		panel.setBorder(border());

		panelN = panelSetWCE(null, panel, null);

	}

	private void userStatus() {
		JTable table = new JTable();
		format(table);
		tableModel = tableData;
		table.setModel(tableModel);
		//		table.setAutoCreateRowSorter(true);
		table.setRowHeight(fontSize * 2);
		table.setShowVerticalLines(true);// 縦枠
		table.setShowHorizontalLines(false);// 横枠
//		table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.setBorder(border());

		DefaultTableColumnModel columnModel
	    = (DefaultTableColumnModel)table.getColumnModel();

		TableColumn column = null;
		int[] width = TableData.getWidth();

		for (int i = 0; i < columnModel.getColumnCount(); i++) {
			column = columnModel.getColumn(i);
			column.setPreferredWidth(width[i] * fontSize);
		}

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.getViewport().setBackground(Color.BLACK);
		scrollPane.setPreferredSize(new Dimension(w*70, h*8));
		scrollPane.setBorder(border());

		setColumnHeaderHeight(scrollPane, fontSize / 2 * 3);

//		scrollPane.setColumnHeader(new JViewport() {///JViewport#getPreferredSize()をオーバーライド
//
//					public Dimension getPreferredSize() {
//						Dimension d = super.getPreferredSize();
//						d.height = 30;/////////////////////////////////////JTableHeaderの高さを設定
//						return d;
//					}
//				});

		DefaultTableCellRenderer tableCellRendererC = new DefaultTableCellRenderer();
		tableCellRendererC.setHorizontalAlignment(JLabel.CENTER);

		TableColumn[] name = new TableColumn[table.getColumnModel().getColumnCount()];

		for (int i = 0; i < name.length; i++) {
			name[i] = table.getColumnModel().getColumn(i);
			name[i].setCellRenderer(tableCellRendererC);
		}

		Object[] selectlist = TableData.getLevelList();

		comboCellEditor(name[5],selectlist);

		format(table.getTableHeader());

		new Event();

		JLabel event = labelSet(Event.dayList(selectDate));

		JPanel panel = panelSetNCS(scrollPane, event, null);
		format(panel);
		panel.setBorder(border());

		panelN = panelSetWCE(null, panel, null);

	}

	private void partyStBlank() {

		JTextArea partyStBlank = textAreaSet(" ", 5, 10);
		panelN = panelSetNCS(null, partyStBlank, null);
	}

	private JPanel setBackPanel(String backURL) {

		ImageIcon iconBack = new ImageIcon(backURL);
		JLabel labelBack = new JLabel(iconBack);

		backPanel = new JPanel();
		format(backPanel);
		backPanel.add(labelBack);
//		backPanel.setPreferredSize(new Dimension(900, 200));

		return backPanel;
	}

	private JPanel dateInfo() {
		return infoTable(new ThisDay(),"帳票日付");
	}

	private JPanel timeInfo() {
		return infoTable(new Time(),"現在日時");
	}

	private JPanel toDoInfo() {
		return infoTable(new ToDo(),"Next To-Do");
	}

	private JPanel infoTable(Object setTableModel, String tableName) {

		JTable pTab = new JTable();
		format(pTab);
		pTab.setModel((TableModel) setTableModel);
		pTab.setAutoCreateRowSorter(true);
		pTab.setRowHeight(fontSize * 2);
		pTab.setShowVerticalLines(false);// 縦枠
		pTab.setShowHorizontalLines(false);// 横枠
//		pTab.setPreferredSize(new Dimension(w*25, pTab.getRowCount() * fontSize));

		DefaultTableCellRenderer tableCellRendererC = new DefaultTableCellRenderer();
		tableCellRendererC.setHorizontalAlignment(JLabel.LEFT);

		TableColumn[] name = new TableColumn[pTab.getColumnModel().getColumnCount()];

		for (int i = 0; i < name.length; i++) {
			name[i] = pTab.getColumnModel().getColumn(i);
			name[i].setCellRenderer(tableCellRendererC);
		}

		JLabel tName = labelSet(tableName);

		JPanel panelT = panelSetLR(b(), pTab);
		panelT.setBorder(border());

		JPanel panel = panelSetUD(tName, panelT);
		format(panel);
		panel.setBorder(border());
		panel.setPreferredSize(new Dimension(w*13, (pTab.getRowCount()+1) * fontSize * 2 - 4));

		return panel;

	}

	private JPanel infoTable(Object setTableModel) {
		return infoTable(setTableModel, "");
	}

	private void info(Object top, Object middle, Object bottom) {

		if (top.equals("")) top = null;
		if (middle.equals("")) middle = null;
		if (bottom.equals("")) bottom = null;

		JPanel infoPanel = panelSetTMB(top, middle, bottom);
		format(infoPanel);
		infoPanel.setPreferredSize(new Dimension(w*13, h*80));
		panelW = panelSetNCS(infoPanel, null, null);

	}

	private JPanel ent() {

		int bI = 1;// //////////////////////////////////ボタンの数

		JPanel panel = new JPanel();
		format(panel);
		// panel.setSize(300, 300);
		panel.setLayout(new GridLayout(bI, 0, 10, 1));
		LineBorder b = new LineBorder(getForeground(), 2, true);
		panel.setBorder(b);

		JButton[] button = new JButton[bI];

		String[] bList = { ent };

		System.out.println("");//////////////////////////////////////////
		System.out.println("bList[0] = " + bList[0]);////////////////////////////
		System.out.println("");//////////////////////////////////////////

		for (int i = 0; i < bI; i++) {
			String bN = (String) bList[i];
			button[i] = new JButton(bN);
			format(button[i]);
			// button[i].setSize(100, 50);
			button[i].setFocusPainted(false);
			button[i].addActionListener(this);
			button[i].addKeyListener(this);
			panel.add(button[i]);
		}
		return panel;
	}

	private void centerTable() {
		JTable table = new JTable();
		format(table);
		tableModel = tableData;
		table.setModel(tableModel);
		//		table.setAutoCreateRowSorter(true);
		table.setRowHeight(fontSize * 2);
		table.setShowVerticalLines(true);// 縦枠
		table.setShowHorizontalLines(false);// 横枠
//		table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.setBorder(border());

		DefaultTableColumnModel columnModel
	    = (DefaultTableColumnModel)table.getColumnModel();

		TableColumn column = null;
		int[] width = TableData.getWidth();

		for (int i = 0; i < columnModel.getColumnCount(); i++) {
			column = columnModel.getColumn(i);
			column.setPreferredWidth(width[i] * fontSize);
		}

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.getViewport().setBackground(Color.BLACK);
		scrollPane.setPreferredSize(new Dimension(w*70, h*70));
		scrollPane.setBorder(border());

		setColumnHeaderHeight(scrollPane, fontSize * 2);

//		scrollPane.setColumnHeader(new JViewport() {///JViewport#getPreferredSize()をオーバーライド
//
//					public Dimension getPreferredSize() {
//						Dimension d = super.getPreferredSize();
//						d.height = 25;/////////////////////////////////////JTableHeaderの高さを設定
//						return d;
//					}
//				});

		DefaultTableCellRenderer tableCellRendererC = new DefaultTableCellRenderer();
		tableCellRendererC.setHorizontalAlignment(JLabel.CENTER);

		TableColumn[] name = new TableColumn[table.getColumnModel().getColumnCount()];

		for (int i = 0; i < name.length; i++) {

			if (i != 8) {
				name[i] = table.getColumnModel().getColumn(i);
				name[i].setCellRenderer(tableCellRendererC);
			}
		}

		format(table.getTableHeader());

		JPanel panel = panelSetNCS(null, scrollPane, null);
		format(panel);
		panel.setBorder(border());

		panelC = panelSetNCS(centerAreaB(), scrollPane, centerAreaB());

		selectJTable = table;
	}

	private void centerTable(MyAbstractTableModel setTableModel) {
		int[] setWidth = setTableModel.getWidth();
		int[] horizontalAlignments = setTableModel.getHorizontalAlignments();
		int headerHeight = setTableModel.getHeaderHeight();
		int rowHeight = setTableModel.getRowHeight();
		centerTable(setTableModel, headerHeight, rowHeight, setWidth, horizontalAlignments);
	}

	private void centerTableE(MyAbstractTableModel setTableModel) {
		int[] setWidth = setTableModel.getWidth();

		centerTableE(setTableModel,setWidth);
	}

	private void centerTable(Object setTableModel,int[] setWidth) {

		JTable table = new JTable();
		format(table);
		tableModel = (TableModel) setTableModel;
		table.setModel(tableModel);
//		table.setAutoCreateRowSorter(true);
		table.setRowHeight(25);
		table.setShowVerticalLines(true);// 縦枠
		table.setShowHorizontalLines(false);// 横枠
//		table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.setBorder(border());

//		beforeData = tableData(table.getModel());

		DefaultTableColumnModel columnModel
	    = (DefaultTableColumnModel)table.getColumnModel();

		TableColumn column = null;
		int[] width = setWidth;

		for (int i = 0; i < columnModel.getColumnCount(); i++) {
			column = columnModel.getColumn(i);
			column.setPreferredWidth(width[i]);
		}

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.getViewport().setBackground(Color.BLACK);
		scrollPane.setPreferredSize(new Dimension(890, 430));
		scrollPane.setBorder(border());

		setColumnHeaderHeight(scrollPane, fontSize * 2);

		DefaultTableCellRenderer tableCellRendererC = new DefaultTableCellRenderer();
		tableCellRendererC.setHorizontalAlignment(JLabel.CENTER);

		TableColumn[] name = new TableColumn[table.getColumnModel().getColumnCount()];

		for (int i = 0; i < name.length; i++) {

//			if (i == 0) {
				name[i] = table.getColumnModel().getColumn(i);
				name[i].setCellRenderer(tableCellRendererC);
//			}
		}

		format(table.getTableHeader());

		JPanel panel = panelSetNCS(null, scrollPane, null);
		format(panel);
		panel.setBorder(border());

		panelC = panelSetNCS(centerAreaB(), scrollPane, centerAreaB());

		selectJTable = table;
	}

	private void centerTable(Object setTableModel,int headerHeight, int rowHeight, int[] setWidth, int[] horizontalAlignments) {

		if(headerHeight < 1) headerHeight = 25;
		if(rowHeight < 1) rowHeight = 25;

		JTable table = new JTable();
		format(table);
		tableModel = (TableModel) setTableModel;
		table.setModel(tableModel);
//		table.setAutoCreateRowSorter(true);
		table.setRowHeight(rowHeight);
		table.setShowVerticalLines(true);// 縦枠
		table.setShowHorizontalLines(false);// 横枠
//		table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.setBorder(border());

//		beforeData = tableData(table.getModel());

		DefaultTableColumnModel columnModel
	    = (DefaultTableColumnModel)table.getColumnModel();

		if (setWidth != null) {

			TableColumn column;

			for (int i = 0; i < setWidth.length; i++) {
				column = columnModel.getColumn(i);
				column.setPreferredWidth(setWidth[i]);
			}

		}

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.getViewport().setBackground(Color.BLACK);
		scrollPane.setPreferredSize(new Dimension(890, 430));
		scrollPane.setBorder(border());

		setColumnHeaderHeight(scrollPane, headerHeight);

		DefaultTableCellRenderer tableCellRendererC = new DefaultTableCellRenderer();
		tableCellRendererC.setHorizontalAlignment(JLabel.CENTER);

		TableColumn[] tableColumns = new TableColumn[table.getColumnModel().getColumnCount()];

		for (int i = 0; i < tableColumns.length; i++) {

			int horizontalAlignment;

			if (horizontalAlignments == null || i < horizontalAlignments.length){
				horizontalAlignment = JLabel.CENTER;
			}else{

				if(i < horizontalAlignments.length){
					horizontalAlignment = horizontalAlignments[i];
				}else{
					horizontalAlignment = JLabel.CENTER;
				}
			}

			DefaultTableCellRenderer tableCellRenderer = new DefaultTableCellRenderer();
			tableCellRenderer.setHorizontalAlignment(horizontalAlignment);

			tableColumns[i] = table.getColumnModel().getColumn(i);
			tableColumns[i].setCellRenderer(tableCellRenderer);
		}

		format(table.getTableHeader());

		JPanel panel = panelSetNCS(null, scrollPane, null);
		format(panel);
		panel.setBorder(border());

		panelC = panelSetNCS(centerAreaB(), scrollPane, centerAreaB());

		selectJTable = table;
	}

	private void centerTableE(Object setTableModel,int[] setWidth) {

		JTable table = new JTable();
		format(table);
		tableModel = (TableModel) setTableModel;
		table.setModel(tableModel);
//		table.setAutoCreateRowSorter(true);
		table.setRowHeight(25);
		table.setShowVerticalLines(true);// 縦枠
		table.setShowHorizontalLines(false);// 横枠
//		table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.setBorder(border());

//		beforeData = tableData(table.getModel());

		DefaultTableColumnModel columnModel
	    = (DefaultTableColumnModel)table.getColumnModel();

		TableColumn column = null;
		int[] width = setWidth;

		for (int i = 0; i < columnModel.getColumnCount(); i++) {
			column = columnModel.getColumn(i);
			column.setPreferredWidth(width[i]);
		}

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.getViewport().setBackground(Color.BLACK);
		scrollPane.setPreferredSize(new Dimension(890, 430));
		scrollPane.setBorder(border());

		setColumnHeaderHeight(scrollPane, fontSize * 2);

		tableColumnFormE(table);

//		DefaultTableCellRenderer tableCellRendererC = new DefaultTableCellRenderer();
//		tableCellRendererC.setHorizontalAlignment(JLabel.CENTER);
//
//		TableColumn[] name = new TableColumn[table.getColumnModel().getColumnCount()];
//
//		for (int i = 0; i < name.length; i++) {
//
////			if (i == 0) {
//				name[i] = table.getColumnModel().getColumn(i);
//				name[i].setCellRenderer(tableCellRendererC);
////			}
//		}

		format(table.getTableHeader());

		JPanel panel = panelSetNCS(null, scrollPane, null);
		format(panel);
		panel.setBorder(border());

		panelC = panelSetNCS(centerAreaB(), scrollPane, centerAreaB());

		selectJTable = table;
	}

	private void centerTableU() {
			JTable table = new JTable();
			format(table);
			tableModel = new Private(selectRoom, selectName, selectDate);
			table.setModel(tableModel);
	//		table.setAutoCreateRowSorter(true);
			table.setRowHeight(fontSize * 2);
			table.setShowVerticalLines(true);// 縦枠
			table.setShowHorizontalLines(false);// 横枠
			table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			table.setBorder(border());

			DefaultTableColumnModel columnModel
		    = (DefaultTableColumnModel)table.getColumnModel();

			TableColumn column = null;
			int[] width = Private.getWidth();

			for (int i = 0; i < columnModel.getColumnCount(); i++) {
				column = columnModel.getColumn(i);
				column.setPreferredWidth(width[i] * fontSize);
			}

			JScrollPane scrollPane = new JScrollPane(table);
			scrollPane.getViewport().setBackground(Color.BLACK);
			scrollPane.setPreferredSize(new Dimension(w*70, h*70));

			setColumnHeaderHeight(scrollPane, fontSize * 2);

			tableColumnFormU(table);

//			DefaultTableCellRenderer tableCellRendererC = new DefaultTableCellRenderer();
//			tableCellRendererC.setHorizontalAlignment(JLabel.CENTER);
//
//			TableColumn[] name = new TableColumn[table.getColumnModel().getColumnCount()];
//
//			for (int i = 0; i < name.length; i++) {
//
//				name[i] = table.getColumnModel().getColumn(i);
//
//				if (i != 9) {// セルレンダラー中央寄せ行
//					name[i].setCellRenderer(tableCellRendererC);
//				}
//
//				if (0 < i && i < 9) {// セルエディター振り分け
//					comboCellEditor(name[i], Private.selectlist(i));// ドロップダウンリスト
//				} else {
//	//				JTextField textField = inputTextField(20,Private.getSelectlist(i));
//					name[i].setCellEditor(new DefaultCellEditor(new JTextField(20)));// テキストフィールド
//				}
//			}

			format(table.getTableHeader());

			panelC = panelSetNCS(centerAreaB(), scrollPane, centerAreaB());

			selectJTable = table;
		}

	private void tableColumnFormE(JTable table) {

		DefaultTableCellRenderer tableCellRendererC = new DefaultTableCellRenderer();
		tableCellRendererC.setHorizontalAlignment(JLabel.CENTER);

		TableColumn[] name = new TableColumn[table.getColumnModel().getColumnCount()];

		for (int i = 0; i < name.length; i++) {

			name[i] = table.getColumnModel().getColumn(i);

			if (i != name.length -1) {// セルレンダラー中央寄せ行
				name[i].setCellRenderer(tableCellRendererC);
			}

//			if (0 < i && i < 9) {// セルエディター振り分け
//				comboCellEditor(name[i], Private.selectlist(i));// ドロップダウンリスト
//			} else {
////				JTextField textField = inputTextField(20,Private.getSelectlist(i));
//				name[i].setCellEditor(new DefaultCellEditor(new JTextField(20)));// テキストフィールド
//			}
		}
	}

	private void tableColumnFormU(JTable table) {

		DefaultTableCellRenderer tableCellRendererC = new DefaultTableCellRenderer();
		tableCellRendererC.setHorizontalAlignment(JLabel.CENTER);

		TableColumn[] name = new TableColumn[table.getColumnModel().getColumnCount()];

		for (int i = 0; i < name.length; i++) {

			name[i] = table.getColumnModel().getColumn(i);

			if (i != 9) {// セルレンダラー中央寄せ行
				name[i].setCellRenderer(tableCellRendererC);
			}

			if (0 < i && i < 9) {// セルエディター振り分け
				comboCellEditor(name[i], Private.selectlist(i));// ドロップダウンリスト
			} else {
//				JTextField textField = inputTextField(20,Private.getSelectlist(i));
				name[i].setCellEditor(new DefaultCellEditor(new JTextField(20)));// テキストフィールド
			}
		}
	}

	private void setColumnHeaderHeight(JScrollPane scrollPane, final int setHeight) {

		scrollPane.setColumnHeader(new JViewport() {///JViewport#getPreferredSize()をオーバーライド

			public Dimension getPreferredSize() {
				Dimension d = super.getPreferredSize();
				d.height = setHeight;/////////////////////////////////////JTableHeaderの高さを設定
				return d;
			}
		});
	}

	private void addMouseClicked(JTable setTable) {

		final JTable table = setTable;

		table.addMouseListener(new java.awt.event.MouseAdapter(){

			public void mouseClicked(MouseEvent e) {

				clickedData = selectRowData(selectRow(table));

				System.out.print("clickedData = ");

				for (int i = 0; i < clickedData.length; i++) {
					System.out.print("[" + clickedData[i] + "]");
				}
			}

		});
	}

	private void comboCellEditor(TableColumn tableColumn, Object[] selectlist) {
	    // tableColumn のセルエディターにドロップダウンを使用
	    JComboBox combo = new JComboBox();

	    combo.addItem("");

	    for (int i = 0; i < selectlist.length; i++) {
	    	combo.addItem(selectlist[i]);
		}
	    tableColumn.setCellEditor(new DefaultCellEditor(combo));
	}

	private JTextField inputTextField(int textLength, Object[] selectList) {

		String text = inpDS("入力して下さい");

		if (text == null) {
			text = "";
		}else{
			if (text.equals("")) {
				text = (String) Screen.selectDS("選択して下さい", selectList);
			}
		}
			System.out.println("");/////////////////////////////////////////////////////
			System.out.println("入力値 = " + text + " です");/////////////////////////////////
			System.out.println("");/////////////////////////////////////////////////////

		return new JTextField(text,textLength);
	}

	private void scene() {

		setBackPanel("フィールド.png");

		JPanel fieldPanel = new JPanel();
		format(fieldPanel);
		fieldPanel.setPreferredSize(new Dimension(890, 200));
		SpringLayout layout = new SpringLayout();
		fieldPanel.setLayout(layout);
		layout.putConstraint(SpringLayout.NORTH, backPanel, 1, SpringLayout.NORTH, fieldPanel);
		layout.putConstraint(SpringLayout.NORTH, eventPanel, 1, SpringLayout.NORTH, fieldPanel);
		layout.putConstraint(SpringLayout.WEST, backPanel, 0, SpringLayout.WEST, fieldPanel);
		layout.putConstraint(SpringLayout.WEST, eventPanel, 0, SpringLayout.WEST, fieldPanel);
		layout.putConstraint(SpringLayout.EAST, backPanel, 0, SpringLayout.EAST, fieldPanel);
		layout.putConstraint(SpringLayout.EAST, eventPanel, 0, SpringLayout.EAST, fieldPanel);
		fieldPanel.add(eventPanel);
		fieldPanel.add(backPanel);

		JPanel scene = panelSetNCS(null, fieldPanel, null);
		scene.setPreferredSize(new Dimension(890, 400));
		scene.setForeground(Color.BLACK);
		scene.setBackground(Color.GRAY);
		scene.setBorder(border());

		panelC = panelSetNCS(centerAreaB(), scene, centerAreaB());

	}



	private Object centerAreaB() {
		pictAreaB = textAreaSet("", 1, 4);
		pictAreaB.setFont(new Font("Monospaced", Font.BOLD, 16));
		return pictAreaB;
	}

	private void comment() {

		sto = new Story();

		JTable st = new JTable();
		Story tableModel = sto;
		st.setAutoCreateRowSorter(true);
		st.setModel(tableModel);

		DefaultTableCellRenderer tableCellRendererC = new DefaultTableCellRenderer();
		tableCellRendererC.setHorizontalAlignment(JLabel.CENTER);

		TableColumn col = st.getColumnModel().getColumn(0);
		col.setCellRenderer(tableCellRendererC);
		format(st);
		st.setRowHeight(30);
		st.setShowVerticalLines(false);// 縦枠
		st.setShowHorizontalLines(false);// 横枠
		st.setPreferredSize(new Dimension(800, 100));
		format(st.getTableHeader(), 30, 30);
		st.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);

		JPanel panel = new JPanel();
		format(panel);
		panel.setLayout(new BorderLayout());
		panel.setBorder(border());
		panel.add(st, BorderLayout.CENTER);

		panelS = panelSetWCE(null, panel, ent());

	}

	private void comment(String message) {

		setMessage(message);

		comment();

	}

	private static String today() {
		Date today = new Date();
		SimpleDateFormat sdformat = new SimpleDateFormat("yyyy年MM月dd日(E)");
		String todaysDate = sdformat.format(today);
		return todaysDate;

	}

	public void keyTyped(KeyEvent e) {
		// clickButton(String.valueOf(e.getKeyChar()));
	}

	public void keyPressed(KeyEvent e) {
		// if (e.getKeyCode() == KeyEvent.VK_ENTER )
		// Money.getMon().job(Money.menu[ 0 ]);
		// if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE)
		// Money.getMon().job(Money.menu[ 1 ]);
		// if (e.getKeyCode() == KeyEvent.VK_DELETE )
		// Money.getMon().job(Money.menu[ 2 ]);
		// if (e.getKeyCode() == KeyEvent.VK_ESCAPE )
		// Money.getMon().job(Money.menu[ 3 ]);
		// if (e.getKeyCode() == KeyEvent.VK_SPACE )
		// Money.getMon().job(Money.menu[ 4 ]);

		// blank = Money.getMon().data.length;
		// table.setRowSelectionInterval(0, blank-1);
		// table.setRowSelectionInterval(blank, blank);
	}

	public void keyReleased(KeyEvent e) {
	}


	public static String inpDS(String requestMessage) {

		return inpDS( requestMessage, "");
	}

	public static String inpDS(String requestMessage, Object initialSelectionValue) {

		System.out.println("");//////////////////////////////////////////////////
		System.out.println("inpDS(" + requestMessage + ", " + initialSelectionValue + ") します");////////////////////////////
		System.out.println("");//////////////////////////////////////////////////

		UIManager.put("OptionPane.okButtonText", "OK");
		UIManager.put("OptionPane.cancelButtonText", "Cancel");
		do {
			value = JOptionPane.showInputDialog(requestMessage, initialSelectionValue);
			if (value == null) {//Cancelボタンが押された時

				showMessageD("取消されました。", "cancel");
				break;

			}
		} while (value.equals(null));

//		display.setText(value + "");

		System.out.println("");//////////////////////////////////////////////////
		System.out.println("入力値は (" + value + ") です");/////////////////////
		System.out.println("");//////////////////////////////////////////////////

		return value;
	}

	public static int inpDI(String requestMessage) {

		return inpDI( requestMessage, "");
	}

	public static int inpDI(String requestMessage, Object initialSelectionValue) {

		System.out.println("");//////////////////////////////////////////////////
		System.out.println("inpDI(" + requestMessage + ", " + initialSelectionValue + ") します");////////////////////////////
		System.out.println("");//////////////////////////////////////////////////

		UIManager.put("OptionPane.okButtonText", "OK");
		UIManager.put("OptionPane.cancelButtonText", "Cancel");
		int r = -1;

		do {

			value = JOptionPane.showInputDialog(requestMessage, initialSelectionValue);

			if (value == null) {//Cancelボタンが押された時

				showMessageD("取消されました。", "cancel");
				break;

			}
		} while (value == null);

		if (value == null || value.equals("")) {
			if (value.equals("")) r = -1;
		} else if (isNumber(value) == false) {

			showMessageD("数値を入力してください。", "入力値は数字でお願いします");
			r = inpDI(requestMessage, initialSelectionValue);

		} else {

			r = Integer.parseInt(value);

		}
//		display.setText(value + "");

		System.out.println("");//////////////////////////////////////////////////
		System.out.println("入力値は (" + r + ") です");/////////////////////////
		System.out.println("");//////////////////////////////////////////////////

		return r;
	}

	private boolean checkD(String question) {

		return checkD(question, "はい", "いいえ");
	}

	static boolean checkD(String question,String returnTrueWord, String returnFalseWord) {

		boolean answer = false;

		Object[] selectData = {returnTrueWord, returnFalseWord};

		String choice = selectD(question, selectData);

		if(choice.equals(selectData[0])){
			answer = true;
		}

		return answer;
	}

	public static boolean isNumber(String num) {
		try {
			Integer.parseInt(num);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	public static String selectD(String message, Object[] selectData) {

		return selectD(message, selectData, selectData[0]);
	}

	public static String selectD(String message, Object[] selectData, Object initialValue) {

		System.out.println("");////////////////////////////////////////////////////////
		System.out.println("selectD(" + message + ", Object[] selectData, " + initialValue + ") します");////
		System.out.println("");////////////////////////////////////////////////////////

		JPanel pan = new JPanel();
		Object obj = message;
		String title = "SELECT";
		int messageType = JOptionPane.INFORMATION_MESSAGE;
		ImageIcon icon = null;
		Object value = JOptionPane.showInputDialog(pan, obj, title, messageType, icon, selectData, initialValue);

		if (value == null) {//Cancelボタンが押された時

			showMessageD("取消されました。", "Not selected");
//			display.setText(ImData.listName());

		} else {

//			display.setText(value + "");

		}

		System.out.println("");/////////////////////////////////
		System.out.println("選んだのは " + value + " です");////
		System.out.println("");/////////////////////////////////

		return String.valueOf(value);
	}

	public static Object selectDI(String message, Object[] selectData) {
		Object[] selectList = new Object[selectData.length + 1];
		selectList[0] = 0;
		for (int i = 0; i < selectData.length; i++) {
			selectList[i + 1] = selectData[i];
		}
		return selectD(message, selectList);
	}

	public static String selectDS(String message, Object[] selectData) {

		String re = "";

		if (selectData != null) {

			String[] selectList = new String[selectData.length + 1];
			selectList[0] = " ";
			for (int i = 0; i < selectData.length; i++) {
				selectList[i + 1] = String.valueOf(selectData[i]);
			}
			re = selectD(message, selectList);

			if (re.equals(" ")) re = "";
		}
		return re;
	}

	public static void showMessageD(Object message, String label) {

		JPanel pan = new JPanel();
		Object obj = message;
		String title = label;
		int messageType = JOptionPane.INFORMATION_MESSAGE;
		ImageIcon icon = null;
		JOptionPane.showMessageDialog(pan, obj, title, messageType, icon);

	}

	public static void setFrame(JFrame frame) {
		Screen.frame = frame;
	}

	public JFrame getFrame() {
		return frame;
	}

	public static void rem() {
		// Money tableModel = Money.getMon();
		// Table.win.table.setModel(tableModel);
		// table.setRowSelectionInterval(0, Money.data.length - 1);
		// table.revalidate();
	}

	public static void setTex(String text) {
		tex = text;
	}

	public String getTex() {
		return tex;
	}

	public static void setMessage(String text) {

		if(ent.equals(entMark)) ent = " 　 ";

		story = new Story();

		story.on(text);
	}

	public static void setMessageEnt(String text) {

		ent = entMark;

		story = new Story();

		story.on(text + "     next" + ent);
	}

	public String getMessage() {
		return message;
	}

	public static void setMode(int mode) {

		System.out.println("");//////////////////////////////////////////
		System.out.println("Screen.setMode(" + mode + ") します");////////
		System.out.println("");//////////////////////////////////////////

		Screen.mode = mode;
	}

	public static int getMode() {
		return mode;
	}

	public static void setMenu(Object[] menu) {
		Screen.menu = menu;
	}

	public Object[] getMenu() {
		return menu;
	}

	public static void setCount(int count) {
		Screen.count = count;
	}

	public static int getCount() {
		return count;
	}

	public static void setEnt(String ent) {
		Screen.ent = ent;
	}

	public static String getEnt() {
		return ent;
	}

	public void setSelectDay(String selectDate) {
		Screen.selectDate = selectDate;
	}

	public static String getSelectDay() {
		return selectDate;
	}

	public static void setSelectDate(String selectDate) {
		Screen.selectDate = selectDate;
	}

	public static String getSelectDate() {
		return selectDate;
	}
}
