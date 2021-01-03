public class ExTeamNotAvailablePeriod extends Exception
{
    /**
    	 *
    	 */
    private static final long serialVersionUID = 1L;

public ExTeamNotAvailablePeriod() {
    super("The team is not available during the period ");
}
public ExTeamNotAvailablePeriod(String message) { super(message); }
}

