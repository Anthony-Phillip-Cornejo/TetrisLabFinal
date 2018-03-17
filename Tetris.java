public class Tetris implements ArrowListener
{
	private BoundedGrid<Block> grid;	// The grid containing the Tetris pieces.
	private BlockDisplay display;		// Displays the grid.
	private Tetrad activeTetrad;		// The active Tetrad (Tetris Piece).

	// Constructs a Tetris Game
	public Tetris()
	{
		grid = new BoundedGrid(20,10);
		display = new BlockDisplay(grid);
		display.setArrowListener(this);
		display.setTitle("Tetris");
		activeTetrad = new Tetrad(grid);
	}

	// Play the Tetris Game
	public void play()
	{
		boolean FOREVER = true;
		while(FOREVER) {
			try { Thread.sleep(1000); } catch(Exception e) {}
			if(!activeTetrad.translate(1,0)) {
				activeTetrad = new Tetrad(grid);
			}
			for(int i = 0; i < grid.getNumRows(); i++) {
				if(isCompletedRow(i)) {
					System.out.println("clear detected");
					clearRow(i);
				}
			}
			display.showBlocks();
		}
	}


	// Precondition:  0 <= row < number of rows
	// Postcondition: Returns true if every cell in the given row
	//                is occupied; false otherwise.
	private boolean isCompletedRow(int row)
	{
		for (int i = 0; i < grid.getNumCols(); i++) {
            if (grid.get(new Location(row, i)) == null) { //calling isValid did not seem to work, StackOverflow recommended == null instead
                return false;
            }
        }
        return true;
	}

	// Precondition:  0 <= row < number of rows;
	//                The given row is full of blocks.
	// Postcondition: Every block in the given row has been removed, and
	//                every block above row has been moved down one row.
	private void clearRow(int row) {
		boolean whileBoolean = true;
		Location clearLoc;
		int count = 0;
		for(int i = 0; i < grid.getNumCols(); i++) {
			clearLoc = new Location(row, i);
			grid.remove(clearLoc);
		}
		
		Location currentLoc;
		Location newLoc;
		
		for (int i = row - 1; i >= 0; i--) {
            for (int j = 0; j < grid.getNumCols(); j++) {
                while (whileBoolean) {
                    try {
                    	currentLoc = new Location(i,j);
                    	newLoc = new Location(i + 1, j);
                        grid.get(currentLoc).moveTo(newLoc);
                    }
                    catch(NullPointerException exception) { 
                    	break; //if block is null, breaks loop & cancels action
                    }
                    catch(IndexOutOfBoundsException exception) {
                        break; //if newLocation is out of bounds, break loop & cancels action
                    }
                }
            }
        }
	}

	// Postcondition: All completed rows have been cleared.
	private void clearCompletedRows()
	{
		for (int i = 0; i < grid.getNumRows(); i++) {
            if(isCompletedRow(i)) {
                clearRow(i);
            }
        }
	}

	// Sleeps (suspends the active thread) for duration seconds.
	private void sleep(double duration)
	{
		final int MILLISECONDS_PER_SECOND = 1000;

		int milliseconds = (int)(duration * MILLISECONDS_PER_SECOND);

		try
		{
			Thread.sleep(milliseconds);
		}
		catch (InterruptedException e)
		{
			System.err.println("Can't sleep!");
		}
	}


	// Creates and plays the Tetris game.
	public static void main(String[] args)
	{
		Tetris test = new Tetris();
		test.display.showBlocks();
		test.play();
	}

	@Override
	public void upPressed() {
		activeTetrad.rotate();
		display.showBlocks();
		
	}

	@Override
	public void downPressed() {
		activeTetrad.translate(1, 0);
		display.showBlocks();
		
	}

	@Override
	public void leftPressed() {
		activeTetrad.translate(0, -1);
		display.showBlocks();
		
	}

	@Override
	public void rightPressed() {
		activeTetrad.translate(0, 1);
		display.showBlocks();
	}

	@Override
	public void spacePressed() {
		// TODO Auto-generated method stub
		
	}
}
