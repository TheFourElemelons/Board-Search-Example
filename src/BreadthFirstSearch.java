public class BreadthFirstSearch extends BestFirstSearch
{
    public BreadthFirstSearch(Board start, long stoppingTime) { super(start, stoppingTime); }

    public int calculatePriority(Board b) { return b.getDepth(); }
}