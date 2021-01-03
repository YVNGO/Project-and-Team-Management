
public class CmdListProjects extends RecordedCommand
{
	
	@Override
	public void execute(String[] cmdParts)
	{
		Company company = Company.getInstance();
        company.listProjects();

	}
	
	@Override
	public void undoMe()
	{
        
    }
	
	@Override
	public void redoMe()
	{
        
    }
}