
public class CmdCreateProject extends RecordedCommand 
{
    private Project p;
   
	@Override
	public void execute(String[] cmdParts)
	{
        try{
            if(cmdParts.length<3){
                throw new ExInsufficientArguments();
            }

            Company company = Company.getInstance();

            if(company.projectExists(cmdParts[1])){
                throw new ExDuplicateProject();
            }

            if(Integer.parseInt(cmdParts[2])<=0){
                throw new ExInsufficientManpower();
            }

            p = company.createProject(cmdParts[1], cmdParts[2]);
          
            addUndoCommand(this); 
            clearRedoList(); 

            System.out.println("Done.");

        }catch(NumberFormatException e){
            System.out.println("Wrong number format -- Please enter an integer.");
        }catch(ExInsufficientArguments e){
            System.out.println(e.getMessage());
        }catch(ExDuplicateProject e){
            System.out.println(e.getMessage());
        }catch(ExInsufficientManpower e){
            System.out.println(e.getMessage());
            System.out.println("Consider changing " + cmdParts[2] + " to a positive non-zero amount.");
        }		
	}
	
	@Override
	public void undoMe()
	{
    Company.getInstance().delProject(p);
		addRedoCommand(this); 
	}
	
	@Override
	public void redoMe()
	{
		Company.getInstance().addProject(p);
        addUndoCommand(this); 
	}
}