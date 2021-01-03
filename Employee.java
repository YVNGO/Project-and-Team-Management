import java.util.ArrayList;

public class Employee implements Comparable<Employee>{
    private String name;
    private Team joinedTeam;
    private ArrayList<Team> allJoinedTeams = new ArrayList<>();
    private ArrayList<Day> allTeamJoinedDates = new ArrayList<>();

    public Employee(String n) {
        name=n;//Set name
        } 

    public String getName() { return name;/*simply return the name string */}

    public static Employee searchEmployee(ArrayList<Employee> list,
    String nameToSearch) {
    for(int i=0; i<list.size(); i++){
        if(list.get(i).getName().equals(nameToSearch)){
            return list.get(i);
        }
    } //search the arrayList for the employee with the given name
    return null;
    }

    @Override
    public int compareTo(Employee another) {
        return this.name.compareTo(another.name);
    }
    
    public void addTeam(Team t){
        joinedTeam = t;
    }

    public void leaveTeam(){
        joinedTeam = null;
    }

    public Team getJoinedTeam(){
        return joinedTeam;
    }

    public void showJoinedTeams(){
        System.out.println("The teams that " + this.name + " has joined: ");
        for(int i=0; i<allJoinedTeams.size(); i++){
            if(allJoinedTeams.get(i).getTeamName().equals(joinedTeam.getTeamName())){
                if(allJoinedTeams.get(i).getLeaderName().equals(this.name)){
                    System.out.println(allJoinedTeams.get(i).getTeamName() + " (" + allTeamJoinedDates.get(allTeamJoinedDates.size()-1)+ " to --), as a leader");
                }
                else{
                    System.out.println(allJoinedTeams.get(i).getTeamName() + " (" + allTeamJoinedDates.get(allTeamJoinedDates.size()-1)+ " to --)");

                }

            }
            else{
                System.out.println(allJoinedTeams.get(i).getTeamName() + " (" + allTeamJoinedDates.get(2*i)+ " to " + allTeamJoinedDates.get(2*i+1) +")");
            }
        }
    }

    public void joinTeam(Team t){
        Day d = new Day(SystemDate.getInstance().toString());
        allTeamJoinedDates.add(d);
        allJoinedTeams.add(t);
        if(t.getCurrentProject()!=null){
            t.getCurrentProject().addAssignedTeamMembersHistory(this);
        }
    }

    public void addLastDay(){
        Day d = new Day(SystemDate.getInstance().previous().toString());
        allTeamJoinedDates.add(d);
    }

    public String projectStartDate(Project p, Team t){
        for(int i=0; i<allJoinedTeams.size(); i++){
            if(allJoinedTeams.get(i).getTeamName().equals(t.getTeamName())){
                if(allTeamJoinedDates.get(2*i).isEarlier(p.getProjectTakenDate())==true){// if employee joined team earlier than project taken date
                    return p.getProjectTakenDate().toString();
                }
                else{
                    return allTeamJoinedDates.get(2*i).toString();
                }
            }
        }
        return "";
    }

    public String projectEndDate(Project p, Team t){
        for(int i=0; i<allJoinedTeams.size(); i++){
            if(allJoinedTeams.get(i).getTeamName().equals(t.getTeamName())){
                if(allTeamJoinedDates.size()==2*i+1){
                    return p.getProjectFinishedDate().toString();
                }
                else if(allTeamJoinedDates.size()>2*i+1 && p.getProjectFinishedDate().isEarlier(allTeamJoinedDates.get(2*i+1))){
                    return p.getProjectFinishedDate().toString();
                }
                else{
                    return allTeamJoinedDates.get(2*i+1).toString();
                }
            }
        }
        return "";
    }

    public Day getProjectEndDay(Project p, Team t){
        for(int i=0; i<allJoinedTeams.size(); i++){
            if(allJoinedTeams.get(i).getTeamName().equals(t.getTeamName())){
                if(allTeamJoinedDates.size()==2*i+1){
                    return p.getProjectFinishedDate();
                }
                else if(allTeamJoinedDates.size()>2*i+1 && p.getProjectFinishedDate().isEarlier(allTeamJoinedDates.get(2*i+1))){
                    return p.getProjectFinishedDate();
                }
                else{
                    return allTeamJoinedDates.get(2*i+1);
                }
            }
        }
        return null;
    }
}
