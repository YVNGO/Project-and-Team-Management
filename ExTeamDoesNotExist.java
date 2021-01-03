public class ExTeamDoesNotExist extends Exception
{
    /**
    	 *
    	 */
    private static final long serialVersionUID = 1L;

public ExTeamDoesNotExist() {
    super("Team does not exist.");
}
public ExTeamDoesNotExist(String message) { super(message); }
}

