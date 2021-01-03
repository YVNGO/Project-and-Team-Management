public class CmdShowProjectWorkerDetails extends RecordedCommand 
{
	
	@Override
	public void execute(String[] cmdParts)
	{
		Company company = Company.getInstance();
        company.listProjectWorkerDetails(cmdParts[1]);
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