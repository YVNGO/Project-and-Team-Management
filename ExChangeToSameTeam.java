public class ExChangeToSameTeam extends Exception
{
    /**
    	 *
    	 */
    private static final long serialVersionUID = 1L;

    public ExChangeToSameTeam() {
    super("The old and new teams should not be the same.");
}
public ExChangeToSameTeam(String message) { super(message); }
}

