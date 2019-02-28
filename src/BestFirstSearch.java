import java.util.Map;
import java.util.PriorityQueue;

//BestFirstSearch class used as an abstract class so other classes can create their own priority for each board.
public abstract class BestFirstSearch
{
    //Member variables to store the initial board, as well as the time in which the algorithm must be completed.
    private Board mInit;
    private long mTime;

    public BestFirstSearch(Board start, long stoppingTime)
    {
        mInit = start;
        mTime = stoppingTime;
    }

    public String run()
    {
        /*
           Priority Queue performs the organization based on the calculatePriority method. calculatePriority method is
           based on what type of search is to be performed, implemented in Heuristic Search and Breadth First Search.
           The initial board's priority is calculated and added to the priority queue as a PQEntry.
         */
        PriorityQueue<PQEntry<Integer, Board>> pq = new PriorityQueue<>();
        int p = calculatePriority(mInit);
        pq.add(new PQEntry<>(p, mInit));

        //The time is recorded to compare to the max time.
        long startTime = System.currentTimeMillis();

        //Repeats the search process until time runs out, or the goal is found.
        while (mTime > (System.currentTimeMillis() - startTime))
        {
            //The next board is pulled from the Priority Queue, the path to reach the board is updated.
            Board currentBoard = pq.poll().getValue();
            currentBoard.updatePath();

            /*
                If the current board is equal to the goal board then the path String is returned, otherwise the neighbours
                of the current board are iterated through, checking if the current path contains any of them, adding them
                to the priority queue as a PQEntry, getting the priority from the calculatePriority, if they aren't
                already in the path.
             */
            if (currentBoard.isGoal()) { return currentBoard.printPath(); }
            else
            {
                for (Board b : currentBoard.neighbors())
                {
                    if (!b.getPath().contains(b))
                    {
                        p = calculatePriority(b);
                        pq.add(new PQEntry<>(p, b));
                    }
                }
            }
        }

        //Returns the Time Out string as the while loop has ended meaning the algorithm has taken too much time.
        return "The algorithm was unable to find the solution in the given time.\n";
    }

    //CalculatePriority method declared abstract so it can be implemented in each other search.
    public abstract int calculatePriority(Board b);

    /*
        PQEntry used to store boards and their priority value, implements Map.Entry and Comparable to be used in the
        Priority Queue and to be able to compare each PQEntry.

     */
    private class PQEntry<K,V> implements Map.Entry, Comparable<PQEntry>
    {
        final private K KEY;
        private V value;

        private PQEntry(K k, V v)
        {
            KEY = k;
            value = v;
        }

        @Override
        public K getKey() { return KEY; }

        @Override
        public V getValue() { return value;}

        @Override
        public Object setValue(Object value)
        {
            Object result = this.value;
            this.value = (V)value;
            return result;
        }

        //compareTo method used for comparing two PQEntries for sorting in Priority Queue.
        @Override
        public int compareTo(PQEntry e) { return (Integer)KEY - (Integer)e.getKey(); }
    }
}