
public class CmdstartNewDay extends RecordedCommand 
{
    private String dateBeforeChange;
    private String dateAfterChange;
	@Override
	public void execute(String[] cmdParts)
	{   
        dateBeforeChange = SystemDate.getInstance().toString();
        dateAfterChange=cmdParts[1];
        SystemDate.getInstance().set(cmdParts[1]);
		addUndoCommand(this); 
		clearRedoList(); 

		System.out.println("Done.");
	}
	
	@Override
	public void undoMe()
	{
        SystemDate.getInstance().set(dateBeforeChange);

		addRedoCommand(this); 
	}
	
	@Override
	public void redoMe()
	{
        SystemDate.getInstance().set(dateAfterChange);
        addUndoCommand(this); 
	}
}