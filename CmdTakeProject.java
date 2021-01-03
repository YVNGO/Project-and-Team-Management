public class CmdTakeProject extends RecordedCommand 
{
    private Team t;
    private Project p;
    private Day startDay;
    private Day endDay;
	
	@Override
	public void execute(String[] cmdParts)
	{
        try {    
            if (cmdParts.length<4)
                throw new ExInsufficientArguments();
            
            Company company = Company.getInstance();
            
            if(SystemDate.getInstance().toString().equals(cmdParts[3])){
                throw new ExStartToday();
            }

            if(company.teamExist(cmdParts[1])==false){
                throw new ExTeamDoesNotExist();
            }

            if(company.projectExists(cmdParts[2])==false){
                throw new ExProjectDoesNotExist();
            }

            t = company.findTeam(cmdParts[1]);
            p = company.findProject(cmdParts[2]);

            if(p.hasTeam()==true){
                throw new ExProjectHasTeam();
            }

            if(Day.dateValid(cmdParts[3])==false){
                throw new ExInvalidDate();
            }

            startDay = new Day(cmdParts[3]);

            Double workDays =  (double) p.getEstManpower() / (double) t.getTeamMembers().size();
            endDay = startDay.next_i_day((int) Math.ceil(workDays)-1);    

            if(t.busyDate(startDay, endDay)){
                throw new ExTeamNotAvailablePeriod();
            }

            company.assignProjectToTeam(t,p, startDay, endDay);

            addUndoCommand(this); 
            clearRedoList(); 

            System.out.println("Done.");

        } catch (ExInsufficientArguments e){
            System.out.println(e.getMessage());
        } catch (ExStartToday e){
            System.out.println(e.getMessage());
        } catch (ExTeamDoesNotExist e){
            System.out.println(e.getMessage());
        } catch (ExProjectDoesNotExist e){
            System.out.println(e.getMessage());
        } catch (ExProjectHasTeam e){
            System.out.println(e.getMessage());
        } catch (ExTeamNotAvailablePeriod e){
            System.out.print(e.getMessage());
            System.out.println("(" + startDay.toString() +" to "+ endDay.toString() + ").");
        } catch (ExInvalidDate e){
            System.out.println(e.getMessage());
        }
	}
	
	@Override
	public void undoMe()
	{
        Company.getInstance().removeProjectToTeam(t,p, startDay, endDay);
		addRedoCommand(this); 
	}
	
	@Override
	public void redoMe()
	{
        Company.getInstance().assignProjectToTeam(t,p, startDay, endDay);
        addUndoCommand(this); 
	}
}