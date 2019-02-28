public class HeuristicSearch extends BestFirstSearch
{
    public HeuristicSearch(Board start, long stoppingTime) { super(start, stoppingTime); }

    public int calculatePriority(Board b)
    {
        return b.evalBoard();
    }
}