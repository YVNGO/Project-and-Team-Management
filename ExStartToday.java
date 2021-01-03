public class ExStartToday extends Exception
{
    /**
    	 *
    	 */
    private static final long serialVersionUID = 1L;

public ExStartToday() {
    super("The earliest start day is tomorrow.");
}
public ExStartToday(String message) { super(message); }
}

