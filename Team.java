import java.util.ArrayList;
import java.util.Collections;

public class Team implements Comparable<Team> {
    private String teamName;
    private Employee head;
    private Day dateSetup; //the date this team was setup
    private Project takenProject;
    private ArrayList<Employee> teamMembers = new ArrayList<>();
    private ArrayList<Day> busyDays = new ArrayList<>(); 
    private ArrayList<Project> allTakenProjects = new ArrayList<>();

    public Team(String n, Employee hd) {
        //Set teamName, head, setup date
        teamName=n;
        head=hd;
        teamMembers.add(head);
        head.addTeam(this);
        dateSetup=SystemDate.getInstance().clone();
        }

        public static void list(ArrayList<Team> list) {
            //Learn: "-" means left-aligned
            System.out.printf("%-15s%-10s%-14s%-20s\n", "Team Name", "Leader", "Setup Date", "Members");
            for (Team t : list)
                System.out.printf("%-15s%-10s%-14s%-20s\n", t.teamName, t.head.getName(), t.dateSetup, t.listTeamMembers()); //display t.teamName, etc..
            }

        @Override
        public int compareTo(Team another) {
            return this.teamName.compareTo(another.teamName);
        }

        public String getTeamName(){return teamName;}

        public static Team searchTeam(ArrayList<Team> list, String tnameToSearch) {
            for(int i=0; i<list.size(); i++){
                if(list.get(i).getTeamName().equals(tnameToSearch)){
                    return list.get(i);
                }
            }
            return null;
        }

        public String listTeamMembers(){
            if(teamMembers.size()==1){
                return "(no member)";
            }
            else{
                teamMembers.remove(head);
                Collections.sort(teamMembers);

                String allTeamMembers="";
                for(int i =0; i<teamMembers.size()-1; i++){
                    allTeamMembers+= teamMembers.get(i).getName() + " ";
                }
                allTeamMembers+=teamMembers.get(teamMembers.size()-1).getName();

                teamMembers.add(head);
                return allTeamMembers;
            }
        } 
        
        public ArrayList<Employee> getTeamMembers(){
            return teamMembers;
        }
        
        public String getLeaderName(){
            return head.getName();
        }

        public void addMember(Employee e){
            teamMembers.add(e);
        }

        public void eraseMember(Employee e){
            teamMembers.remove(e);
        }

        public void fireTeamMember(Employee e){
            teamMembers.remove(e);
        }

        public void addProject(Project p){
            takenProject = p;
            allTakenProjects.add(p);
        }

        public void removeProject(Project p){
            takenProject=null;
            allTakenProjects.remove(p);

        }

        public void bookBusyDays(Day startDay, Day endDay){
            Day dtmp = new Day(startDay.toString());
            while(!dtmp.toString().equals(endDay.next().toString())){
                busyDays.add((new Day(dtmp.toString())));
                dtmp = dtmp.next();   
            }
        }

        public void unbookBusyDays(Day startDay, Day endDay){
            Day dtmp = new Day(startDay.toString());
            while(!dtmp.toString().equals(endDay.next().toString())){
                Day d = new Day(dtmp.toString());
                busyDays.remove(d);
                for(int i=0; i<busyDays.size(); i++){
                    if(busyDays.get(i).toString().equals(d.toString())){
                        busyDays.remove(busyDays.get(i));
                        break;
                    }
                }
                dtmp.set(dtmp.next().toString());   
            }
        }

		public boolean busyDate(Day startDay, Day endDay) {
            Day d = new Day(startDay.toString());
            while(!d.toString().equals(endDay.next().toString())){
                for(int i=0; i<busyDays.size(); i++){
                    if(busyDays.get(i).toString().equals(d.toString())){
                        return true;
                    }
                }
                d.set(d.next().toString());
            }
            return false;
        }
        
        // public Integer estimateFinishDays(Integer manpower){
        //     Integer daysToFinish =  (int) Math.ceil((double) manpower / (double) this.teamMembers.size());
        //     Day startDay;
        //     Day endDay = null;
        //     Day now = SystemDate.getInstance();
        //     Integer howLong = 1;
        //     Day tmp;
        //     boolean finished = false;

        //     while(finished==false){
        //         for(int i=0; i<busyDays.size(); i++){
        //             if(busyDays.get(i).toString().equals(now.toString())){
        //                 now = now.next();
        //             }
        //             else{
        //                 startDay=now;
        //                 for(int j=0; j<daysToFinish; j++){
        //                     endDay = startDay.next();
        //                 }
        //                 finished=true;
        //                 break;
        //             }
        //         }
                
        //     }

        //     tmp = SystemDate.getInstance();
        //     while (!tmp.toString().equals(endDay.toString())){
        //         howLong++;
        //         tmp = tmp.next();
        //     }
        //     return howLong;
        // }

        public Day getStartDay(Integer manpower){
            Integer daysToFinish = (int) Math.ceil((double) manpower/ (double) teamMembers.size());
            Day startDay = null;
            Day endDay = null;
            Day now = SystemDate.getInstance();
            boolean finished = false;


            while(finished==false){
                for(int i=0; i<busyDays.size(); i++){
                    if(busyDays.get(i).toString().equals(now.toString())){
                        now = now.next();
                    }
                    else{
                        startDay=now;
                        for(int j=0; j<daysToFinish; j++){
                            endDay = startDay.next();
                        }
                        finished=true;
                        break;
                    }
                }
            }
            return startDay;
        }

        public Day getEndDay(Integer manpower){
            Integer daysToFinish = (int) Math.ceil((double) manpower/ (double) teamMembers.size());
            Day startDay;
            Day endDay = null;
            Day now = SystemDate.getInstance();
            boolean finished = false;


            while(finished==false){
                for(int i=0; i<busyDays.size(); i++){
                    if(busyDays.get(i).toString().equals(now.toString())){
                        now = now.next();
                    }
                    else{
                        startDay=now;
                        for(int j=0; j<daysToFinish; j++){
                            endDay = startDay.next();
                        }
                        finished=true;
                        return endDay;
                        
                        
                    }
                }
            }

            return endDay;
        }
        
        public Project getCurrentProject(){
            return takenProject;
        }

        public Day estimateFinishDay(Integer manpower){
            Double workDays =  (double) manpower / (double) teamMembers.size();
            Day startDay = new Day(SystemDate.getInstance().next().toString());
            Day tmp;
            Day finishDay = null;
            boolean datesValid = false;
            while(datesValid==false){
                if(dayIsBusy(startDay)){
                    startDay = startDay.next();
                }
                else{
                    tmp = startDay;
                    boolean isbusy = false;
                    for(int i=0; i<workDays-1; i++){
                        if(dayIsBusy(tmp)){
                            startDay = startDay.next();
                            isbusy = true;
                            break;
                        }
                        else{
                            tmp = tmp.next();
                        }
                    }
                    if(dayIsBusy(tmp)){
                        startDay = startDay.next();
                        isbusy = true;
                    }
                    if(isbusy==false){
                        finishDay = new Day(tmp.toString());
                        datesValid=true;
                    }
                    
                }
            }

            return finishDay; 

        }

        public boolean dayIsBusy(Day d){
            for(int i=0; i<busyDays.size(); i++){
                if(busyDays.get(i).toString().equals(d.toString())){
                    return true;
                }
            }
            return false;
        }

        public Day estimateStartDay(Integer manpower){
            Double workDays =  (double) manpower / (double) teamMembers.size();
            Day startDay = new Day(SystemDate.getInstance().next().toString());
            Day tmp;
            Day finishDay = null;
            boolean datesValid = false;
            while(datesValid==false){
                if(dayIsBusy(startDay)){
                    startDay = startDay.next();
                }
                else{
                    tmp = startDay;
                    boolean isbusy = false;
                    for(int i=0; i<workDays-1; i++){
                        if(dayIsBusy(tmp)){
                            startDay = startDay.next();
                            isbusy = true;
                            break;
                        }
                        else{
                            tmp = tmp.next();
                        }
                    }
                    if(dayIsBusy(tmp)){
                        startDay = startDay.next();
                        isbusy = true;
                    }
                    if(isbusy==false){
                        finishDay = new Day(tmp.toString());
                        datesValid=true;
                    }
                    
                }
            }

            return startDay;
        }

}
