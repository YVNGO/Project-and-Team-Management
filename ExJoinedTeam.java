public class ExJoinedTeam extends Exception
{
    /**
    	 *
    	 */
    private static final long serialVersionUID = 1L;

public ExJoinedTeam() {
    super("Employee has joined a team already.");
}
public ExJoinedTeam(String message) { super(message); }
}

