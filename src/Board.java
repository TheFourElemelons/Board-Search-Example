import java.util.ArrayList;

public class Board
{
    private int [][] mBoard;
    private int mSize;
    private int mDepth;
    private int [][] mGoal;
    private ArrayList<Board> mPath = new ArrayList<Board>();

    /*
        Constructor given a 2-dimensional array of integers, assuming they are from 1 - (size - 1) with 0 being the empty
        tile.  Goal is created to be compared to the current board determining if the current board is in the goal state.
        Used for the root board of a search.
     */
    public Board(int [][] tiles)
    {
        mSize = tiles.length;
        mBoard = tiles;
        mGoal = new int[mSize][mSize];

        //Creates the goal board using the fact that is always in order of 1,2,3,... with 0 in the bottom right corner.
        for (int i = 0; i < mSize; i++)
        {
            for (int j = 0; j < mSize; j++)
            {
                mGoal[i][j] = ((i * mSize) + j + 1) % (mSize * mSize);
            }
        }
    }

    /*
        Constructor given a 2-dimensional array of integers, assuming they are from 1 - (size - 1) with 0 being the empty
        tile.  Goal is created to be compared to the current board determining if the current board is in the goal state.
        pathway added to store how the board was reached. Depth used for for BreadthFirstSearch Priority.
     */
    public Board(int [][] tiles, ArrayList<Board> pathway, int currentDepth)
    {
        mSize = tiles.length;
        mBoard = tiles;
        mDepth = currentDepth;
        mGoal = new int[mSize][mSize];
        mPath.addAll(pathway);

        //Creates the goal board using the fact that is always in order of 1,2,3,... with 0 in the bottom right corner.
        for (int i = 0; i < mSize; i++)
        {
            for (int j = 0; j < mSize; j++)
            {
                mGoal[i][j] = ((i * mSize) + j + 1) % (mSize * mSize);
            }
        }
        mGoal[mSize - 1][mSize - 1] = 0;
    }

    //Prints the path to reach the current board.
    public String printPath ()
    {
        String result = "";
        for (Board b: mPath)
        {
            result += b.toString() + "\n";
        }
        return result;
    }

    //Returns the arrayList containing the pathway of the board.
    public ArrayList<Board> getPath()
    {
        return mPath;
    }

    /*
        Evaluates the board creating a grade which is how many tiles aren't in their correct place, the lower the number
        the higher the priority in a Priority Queue, so the less tiles that aren't in their correct place the higher the
        priority.
    */
    public int evalBoard ()
    {
        int grade = 0;

        for (int i = 0; i < mSize; i++)
        {
            for (int j = 0; j < mSize; j++)
            {
                if (mBoard[i][j] != ((i * mSize) + j + 1)) { grade++; }
            }
        }

        return grade;
    }

    //Returns the depth of the board for BreadthFirstSearch.
    public int getDepth() { return mDepth; }

    //Returns the string representation of the board.
    public String toString()
    {
        String rString = "";

        for (int i = 0; i < mSize; i++)
        {
            for (int j = 0; j < mSize; j++)
            {
                rString += mBoard[i][j] + " ";
            }
            rString += "\n";
        }

        return rString;
    }

    //Adds this board to the path, has to be a valid board for update path to be called.
    public void updatePath()
    {
        mPath.add(this);
    }

    //Returns the tile at the desired row and column as long as it's a valid tile, throws an illegal state exception otherwise.
    public int tileAt (int row, int col)
    {
        if (row >= mSize && col >= mSize && row <= 0 && col <= 0)
        {
            throw new IllegalStateException();
        }
        return mBoard[row][col];
    }

    //Returns the size of the current board.
    public int mSize() { return mSize; }

    //Determines if the goal state has been reached.
    public boolean isGoal()
    {
        for (int i = 0; i < mSize; i++)
        {
            for (int j = 0; j < mSize; j++)
            {
                if (mGoal[i][j] != mBoard[i][j]) return false;
            }
        }
        return true;
    }

    //Checks each tile of the board to the tile of the given board, returning false if one does not equal the other.
    //Can only compare two boards of the same size.
    public boolean equals (Board y)
    {
        for (int i = 0; i < mSize; i++)
        {
            for (int j = 0; j < mSize; j++)
            {
                if (y.tileAt(i,j) != mBoard[i][j]) return false;
            }
        }
        return true;
    }

    //Returns an iterable object containing potentially four different boards based on the validity of each potential move.
    public Iterable<Board> neighbors()
    {
        //Creates an arrayList to store the boards, integer values to remember the position of 0, and 4 2-dimensional
        //integer arrays that originally store a copy of the original array, then if a move is valid, a swap is performed
        //and the 2-dimensional array is added to the ArrayList of Boards.
        ArrayList<Board> result = new ArrayList<>();
        int zeroRow = 0;
        int zeroCol = 0;
        int[][] leftBoard = new int[mSize][mSize];
        int[][] rightBoard = new int[mSize][mSize];
        int[][] upBoard = new int[mSize][mSize];
        int[][] downBoard = new int[mSize][mSize];

        //Checks every tile of the initial 2-dimensional array, creating the four copies, as well as checking for the zero position.
        for (int i = 0; i < mSize; i++)
        {
            for (int j = 0; j < mSize; j++)
            {
                leftBoard[i][j] = tileAt(i,j);
                rightBoard[i][j] = tileAt(i,j);
                upBoard[i][j] = tileAt(i,j);
                downBoard[i][j] = tileAt(i,j);
                if (mBoard[i][j] == 0)
                {
                    zeroRow = i;
                    zeroCol = j;
                }
            }
        }

        //Checks if the each move is valid, moving Left, Right, Up, and Down, if the move is valid a new Board is created
        //in the swap method, which is given the zero position, and the position it is moving to, as well as the board it
        //will be created from.
        if (zeroCol - 1 >= 0)
        {
            result.add(swap(zeroRow,zeroCol,zeroRow,zeroCol - 1, leftBoard));
        }

        if (zeroCol + 1 < mSize)
        {
            result.add(swap(zeroRow,zeroCol,zeroRow,zeroCol + 1, rightBoard));
        }

        if (zeroRow - 1 >= 0)
        {
            result.add(swap(zeroRow,zeroCol,zeroRow - 1,zeroCol, upBoard));
        }

        if (zeroRow + 1 < mSize)
        {
            result.add(swap(zeroRow,zeroCol,zeroRow + 1,zeroCol, downBoard));
        }

        return result;
    }

    //Swaps the values of the two positions given in the 2-dimensional array given, return a new Board with the 2-dimensional array.
    public Board swap (int zeroRow, int zeroCol, int row, int col, int [][] newBoard)
    {
        int value = newBoard[row][col];
        newBoard[row][col] = newBoard[zeroRow][zeroCol];
        newBoard[zeroRow][zeroCol] = value;
        return new Board(newBoard, mPath, mDepth + 1);
    }
}