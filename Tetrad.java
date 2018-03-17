import java.awt.Color;
import java.util.*;

// Represents a Tetris piece.
public class Tetrad
{
	private Block[] blocks;	// The blocks for the piece.
	private Location[] tempLoc;
	private Color tempColor;
	private BoundedGrid gridStorage;

	// Constructs a Tetrad.
	public Tetrad(BoundedGrid<Block> grid)
	{
		blocks = new Block[4];
		tempLoc = new Location[4];
		Random r = new Random();
		int random = r.nextInt(7); 
		gridStorage = grid;
		
		if(random == 0) {
			tempColor = Color.RED;
			tempLoc[0] = new Location(0,4);
			tempLoc[1] = new Location(0,3);
			tempLoc[2] = new Location(0,5);
			tempLoc[3] = new Location(0,6);	
		
			for(int j = 0; j < 4; j++) {
				blocks[j] = new Block();
				blocks[j].setColor(tempColor);
			}
		}
		
		if(random == 1) {
			tempColor = Color.GRAY;
			tempLoc[0] = new Location(0,4);
			tempLoc[1] = new Location(0,3);
			tempLoc[2] = new Location(0,5);
			tempLoc[3] = new Location(1,4);	
		
			for(int j = 0; j < 4; j++) {
				blocks[j] = new Block();
				blocks[j].setColor(tempColor);
			}
		}
		
		if(random == 2) {
			//bad rotate
			tempColor = Color.CYAN;
			tempLoc[0] = new Location(0,4);
			tempLoc[1] = new Location(1,4);
			tempLoc[2] = new Location(0,5);
			tempLoc[3] = new Location(1,5);		
			for(int j = 0; j < 4; j++) {
				blocks[j] = new Block();
				blocks[j].setColor(tempColor);
			}
		}
		
		if(random == 3) {
			tempColor = Color.YELLOW;
			tempLoc[0] = new Location(0,3);
			tempLoc[1] = new Location(1,3);
			tempLoc[2] = new Location(0,4);
			tempLoc[3] = new Location(0,5);
		
			for(int j = 0; j < 4; j++) {
				blocks[j] = new Block();
				blocks[j].setColor(tempColor);
			}
		}
		
		if(random == 4) {
			tempColor = Color.MAGENTA;
			tempLoc[0] = new Location(0,5);
			tempLoc[1] = new Location(1,5);
			tempLoc[2] = new Location(0,4);
			tempLoc[3] = new Location(0,3);
		
			for(int j = 0; j < 4; j++) {
				blocks[j] = new Block();
				blocks[j].setColor(tempColor);
			}
		}
		
		if(random == 5) {
			tempColor = Color.BLUE;
			tempLoc[0] = new Location(0,4);
			tempLoc[1] = new Location(1,4);
			tempLoc[2] = new Location(1,3);
			tempLoc[3] = new Location(0,5);
		
			for(int j = 0; j < 4; j++) {
				blocks[j] = new Block();
				blocks[j].setColor(tempColor);
			}
		}
		
		if(random == 6) {
			tempColor = Color.GREEN;
			tempLoc[0] = new Location(1,4);
			tempLoc[1] = new Location(0,4);
			tempLoc[2] = new Location(0,3);
			tempLoc[3] = new Location(1,5);
		
			for(int j = 0; j < 4; j++) {
				blocks[j] = new Block();
				blocks[j].setColor(tempColor);
			}
		}
		
		addToLocations(grid, tempLoc);
		
	}


	// Postcondition: Attempts to move this tetrad deltaRow rows down and
	//						deltaCol columns to the right, if those positions are
	//						valid and empty.
	//						Returns true if successful and false otherwise.
	public boolean translate(int deltaRow, int deltaCol)
	{
		Location[] newLocs = new Location[4];
		
		for(int i = 0; i < newLocs.length; i++) {
			newLocs[i] = new Location(tempLoc[i].getRow() + deltaRow, tempLoc[i].getCol() + deltaCol);
		}
		
		this.removeBlocks();
				
		if(areEmpty(gridStorage, newLocs)) {
			this.addToLocations(gridStorage, newLocs);
			tempLoc = newLocs;
			return true;
		} else {
			this.addToLocations(gridStorage, tempLoc);
			return false;
		}
		
			
	}

	// Postcondition: Attempts to rotate this tetrad clockwise by 90 degrees
	//                about its center, if the necessary positions are empty.
	//                Returns true if successful and false otherwise.
	public boolean rotate()
	{
		Location[] newLocs = new Location[blocks.length];
		int row = tempLoc[0].getRow();
		int col = tempLoc[0].getCol();
		
		for (int i = 0; i < newLocs.length; i++) {
			newLocs[i] = new Location(row - col + tempLoc[i].getCol(), row + col - tempLoc[i].getRow());
		}
		
		this.removeBlocks();
		
		if (areEmpty(gridStorage, newLocs)) {
			addToLocations(gridStorage, newLocs);
			tempLoc = newLocs;
			return true;
		} else {
			addToLocations(gridStorage, tempLoc);
			return false;
		}
	}


	// Precondition:  The elements of blocks are not in any grid;
	//                locs.length = 4.
	// Postcondition: The elements of blocks have been put in the grid
	//                and their locations match the elements of locs.
	private void addToLocations(BoundedGrid<Block> grid, Location[] locs)
	{
		for(int i = 0; i < locs.length; i++) {
			blocks[i].putSelfInGrid(grid, locs[i]);
		}
	}

	// Precondition:  The elements of blocks are in the grid.
	// Postcondition: The elements of blocks have been removed from the grid
	//                and their old locations returned.
	private Location[] removeBlocks()
	{
		Location[] locs = new Location[4];
		for (int i = 0; i < 4; i++) {
			locs[i] = blocks[i].getLocation();
			blocks[i].removeSelfFromGrid();
		}
		return locs;
	}

	// Postcondition: Returns true if each of the elements of locs is valid
	//                and empty in grid; false otherwise.
	private boolean areEmpty(BoundedGrid<Block> grid, Location[] locs)
	{
		List<Location> location = grid.getOccupiedLocations();		
		boolean returnEmpty = true;
		for (int i = 0; i < locs.length; i++) {
			for (Location loc : location) {
				if (loc.equals(locs[i])) {
					returnEmpty = false;
				}
			}
			if (!grid.isValid(locs[i]) || !returnEmpty) {
				return false;
			}
		}
		return true;
	}
}
