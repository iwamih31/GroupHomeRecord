package iwamih31.GroupHomeRecord;

import javax.swing.table.AbstractTableModel;


public abstract class MyAbstractTableModel extends AbstractTableModel{

	private static int[] width;
	private static int[] horizontalAlignments;
	private static int headerHeight;
	private static int rowHeight;

	public void setWidth(int[] width) {
		MyAbstractTableModel.width = width;
	}
	public int[] getWidth() {
		return width;
	}
	public void setHorizontalAlignments(int[] horizontalAlignments) {
		MyAbstractTableModel.horizontalAlignments = horizontalAlignments;
	}
	public int[] getHorizontalAlignments() {
		return horizontalAlignments;
	}
	public void setHeaderHeight(int headerHeight) {
		MyAbstractTableModel.headerHeight = headerHeight;
	}
	public int getHeaderHeight() {
		return headerHeight;
	}
	public void setRowHeight(int rowHeight) {
		MyAbstractTableModel.rowHeight = rowHeight;
	}
	public int getRowHeight() {
		return rowHeight;
	}

}
