public class GameTreeTest
{
    //Assignment 1 Main
    public static void main (String [] args)
    {
        //2-dimensional integer array used to test Depth an Breadth first search
        int [][] board = {{0,1,3},{4,2,5},{7,8,6}};
        GameTree test = new GameTree(new Board(board), 3);

        System.out.println("BFS:\n" + test.BFS() + "\n");
        System.out.println("DFS:\n" + test.DFS() + "\n");
    }
}