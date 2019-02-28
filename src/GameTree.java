import java.util.*;

public class GameTree
{
    private Board board;
    private int depth;

    public GameTree(Board initial, int depth)
    {
        board = initial;
        this.depth = depth;
    }

    //Performs a Breadth First Search on the stored Board to the given depth, returns the path of the search.
    public String BFS()
    {
        //Result is string to be returned, search is a queue used to perform the depth first search, nextLevel is
        //an arrayList to store the boards on the depth after the current depth. The initial board is added to the queue
        //and the search is begun.
        String result = "";
        Deque<Board> search = new ArrayDeque<>();
        search.addFirst(board);
        Deque<Board> nextLevel = new ArrayDeque<>();


        //For each depth level the boards are added to the result string, then their neighbours are added to
        //the nextLevel arrayList, once all boards are checked on the current level the nextLevel arrayList is unloaded
        //into the queue and searched through, until the desired depth is reached.
        for (int i = 1; i <= depth; i++)
        {
            //Empties the queue adding each board to the return string.
            while(search.peekFirst() != null)
            {
                Board currentBoard = search.removeLast();
                result += "\n" + currentBoard.toString();

                //Checks if the current board is the goal, ending the search if it is, otherwise it will put the
                //neighbours of the currentBoard into the arrayList nextLevel.
                if (!currentBoard.isGoal())
                {
                    Iterable<Board> neighbors = currentBoard.neighbors();
                    Iterator<Board> it = neighbors.iterator();
                    while (it.hasNext())
                    {
                        nextLevel.addFirst(it.next());
                    }
                }
            }

            //Empties the arrayList nextLevel into the queue
            while (!nextLevel.isEmpty())
            {
                search.addFirst(nextLevel.removeLast());
            }
        }

        return result;
    }

    //Performs a Depth First Search on the stored board, calling the recursive Depth First Search method.
    public String DFS()
    {
        return DFS(board, 1);
    }

    //Recursive Depth First Search on give board to the given depth.  Adds the given board to the results string, then
    //decides if the desired depth has been reached, if not the recursive Depth First Search method will be performed
    //on each neighbours of the currentBoard, increasing the depth. Returning the currentBoard or the currentBoard plus
    //the results of the recursive Depth First Search to the method call.
    public String DFS(Board currentBoard, int currentDepth)
    {
        String result = "\n" + currentBoard.toString();
        if (currentDepth < depth && !currentBoard.isGoal())
        {
            Iterable<Board> neighbors = currentBoard.neighbors();
            Iterator<Board> it = neighbors.iterator();

            while(it.hasNext())
            {
                result = result + DFS(it.next(), currentDepth + 1);
            }
        }
        return result;
    }
}