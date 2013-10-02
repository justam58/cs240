package client.views;

public class Cell {
	public Cell(int row, int col){
		record = row;
		field = col;
	}
	int record;
	int field;

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cell other = (Cell) obj;
		if (field != other.field)
			return false;
		if (record != other.record)
			return false;
		return true;
	}
}
