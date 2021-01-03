public class CmdChangeTeam extends RecordedCommand 
{
	private Employee e;
    private Team initialTeam;
    private Team finalTeam;

	@Override
	public void execute(String[] cmdParts)
	{
        try {    
            if (cmdParts.length<3)
                throw new ExInsufficientArguments();
            
            Company company = Company.getInstance();

            if(company.employeeExist(cmdParts[1])==false){
                throw new ExEmployeeDoesNotExist();
            }

            if(company.teamExist(cmdParts[2])==false){
                throw new ExTeamDoesNotExist();
            }

            e = company.findEmployee(cmdParts[1]);
            initialTeam = company.findTeam(e.getJoinedTeam().getTeamName());
            finalTeam = company.findTeam(cmdParts[2]);

            if(initialTeam.equals(finalTeam)){
                throw new ExChangeToSameTeam();
            }

            company.joinTeam(e, finalTeam);


            addUndoCommand(this); 
            clearRedoList(); 

            System.out.println("Done.");

        } catch (ExInsufficientArguments e){
            System.out.println(e.getMessage());
        } catch(ExChangeToSameTeam e){
            System.out.println(e.getMessage());
        }catch(ExEmployeeDoesNotExist e){
            System.out.println(e.getMessage());
        }catch(ExTeamDoesNotExist e){
            System.out.println(e.getMessage());
        }
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