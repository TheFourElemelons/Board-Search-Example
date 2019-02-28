public class SearchTest
{
    //Assignment 2 Main
    public static void main (String [] args)
    {
        //Test arrays for both searches, as well as the maximum time searches are allowed.
        int [][] test1 = {{1,2,3}, {4,0,5}, {7,8,6}};
        int [][] test2 = {{0,1,2}, {4,5,3}, {7,8,6}};
        int [][] test3 = {{0,8,6}, {2,7,5}, {1,3,4}};
        long time = 2000;

        //Creates the first BestFirstSearch that will be re-initiated for each search. Running each search on each board.
        BestFirstSearch search = new HeuristicSearch(new Board(test1), time);
        System.out.println("Heuristic Test 1: \n" + search.run());


        search = new HeuristicSearch(new Board(test2), time);
        System.out.println("Heuristic Test 2: \n" + search.run());

        search = new HeuristicSearch(new Board(test3), time);
        System.out.println("Heuristic Test 3: \n" + search.run());


        search = new BreadthFirstSearch(new Board(test1), time);
        System.out.println("Breadth First Test 1: \n" + search.run());


        search = new BreadthFirstSearch(new Board(test2), time);
        System.out.println("Breadth First Test 2: \n" + search.run());

        search = new BreadthFirstSearch(new Board(test3), time);
        System.out.println("Breadth First Test 3: \n" + search.run());
    }
}