public class ExProjectDoesNotExist extends Exception
{
    /**
    	 *
    	 */
    private static final long serialVersionUID = 1L;

public ExProjectDoesNotExist() {
    super("Project does not exist.");
}
public ExProjectDoesNotExist(String message) { super(message); }
}

