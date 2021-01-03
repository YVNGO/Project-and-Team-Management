public class ExProjectHasTeam extends Exception
{
    /**
    	 *
    	 */
    private static final long serialVersionUID = 1L;

public ExProjectHasTeam() {
    super("Project has already been assigned to a team.");
}
public ExProjectHasTeam(String message) { super(message); }
}

