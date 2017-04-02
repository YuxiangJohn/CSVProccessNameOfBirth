import org.apache.commons.csv.*;
import java.io.*;
import edu.duke.*;
/**
 * Write a description of BabyBirths here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class BabyBirths {
    public void printNames(){
        FileResource fr = new FileResource();
        for(CSVRecord rec:fr.getCSVParser(false)){
            int numBorn = Integer.parseInt(rec.get(2));
            if(numBorn<=100){
                System.out.println("Name "+rec.get(0)+" Gender "+rec.get(1)+" Num Born "+rec.get(2));
            }
        
        }
    }
    
    public void totalBirths(FileResource fr){
        int totalBirths = 0;
        int totalBoys=0;
        int totalGirls=0;
        
        int numBoyName=0;
        int numGirlName=0;
        int totalName=0;
        for(CSVRecord rec:fr.getCSVParser(false)){
            int numBorn = Integer.parseInt(rec.get(2));
            totalBirths += numBorn;
            totalName++;
            if(rec.get(1).equals("M")){
                totalBoys +=numBorn;
                numBoyName++;
            }else {
                totalGirls +=numBorn;
                numGirlName++;
            }           
        }
            System.out.println("total births= "+totalBirths);
            System.out.println("total girls= "+totalGirls);
            System.out.println("total boys= "+totalBoys);
            
            System.out.println("total num of names= "+totalName);
            System.out.println("total num of girls' name= "+numGirlName);
            System.out.println("total num of boys' name= "+numBoyName);
    }
    
    public void testTotalBirths(){
        FileResource fr = new FileResource("us_babynames_by_year/yob1905.csv");
        totalBirths(fr);
    }
    
    public int getRank(int year, String name, String gender){
        int rank=0;
        String fileYear=year+"";
        FileResource fr = new FileResource("us_babynames_by_year/yob"+fileYear+".csv");
        for(CSVRecord rec:fr.getCSVParser(false)){
            if(rec.get(1).equals(gender)){
            rank++;
            if(rec.get(0).equals(name)){
            return rank;
            }
            }
        }
        return -1;
    }
    
    public void testGetRank(){
      int rank= getRank(1971,"Frank","M");
      System.out.println("the rank is: "+rank);
    }
    
    public String getName(int year, int rank, String gender){
        String name="";
        String fileYear=year+"";
        int count=0;
        FileResource fr = new FileResource("us_babynames_by_year/yob"+fileYear+".csv");
        for(CSVRecord rec:fr.getCSVParser(false)){
            if(rec.get(1).equals(gender)){
                count++;
                if(count==rank){
                    return rec.get(0);
                }
            }
        }
        return "NO NAME";
    }
    public void testGetName(){
        String name= getName(1982,450,"M");
      System.out.println("the rank is: "+name);
    }
    
    public void whatIsNameInYear(String name,int year,int newYear,String gender){      
      int rank=getRank(year,name,gender);
      String newName=getName(newYear,rank,gender);
      String printGender="";
      if(gender.equals("M")){
        printGender="he";
        }else {
        printGender="she";
        }
        
      System.out.println(name+" born in "+year+" would be "+newName+" if "+printGender+" was born in "+newYear);
      
    }
    public void testWhatIsNameInYear(){
        whatIsNameInYear("Susan",1972,2014,"F");
    }
    
    public int yearOfHighestRank(String name, String gender){
        DirectoryResource dr=new DirectoryResource();
        int currRank=-1;
        int currYear=-1;
        for(File f: dr.selectedFiles()){
        FileResource fr=new FileResource(f);
        String filename= f.getName();
        int year= Integer.parseInt(filename.substring(3,7));
        int rank=getRank(year,name,gender);
        if(currRank==-1){
            currRank=rank;
            currYear= year;
        }
        if(currRank>rank && rank!=-1){
          currRank=rank;
          currYear=year;
        }
        }
        if(currRank==-1){
            return -1 ;
        }else {
            return currYear;
        }
    }
    public void testYearOfHighestRank(){
        int year=  yearOfHighestRank("Mich","M");
        System.out.println("The year of highest rank is: "+year);
    }
    
    public double getAverageRank(String name,String gender){
        DirectoryResource dr=new DirectoryResource();
        int total=0;
        int count=0;
        for(File f: dr.selectedFiles()){
           FileResource fr=new FileResource(f);
           String filename= f.getName();
           int year= Integer.parseInt(filename.substring(3,7));
           int rank=getRank(year,name,gender);
           if(rank!=-1){
               total+=rank;
               count++;
            }
        }
        return (double)total/count;
    }
    public void testGetAverageRank(){
        double average=getAverageRank("Robert","M");
        System.out.println("the average rank is: "+average);
    }
    public int getTotalBirthsRankedHigher(int year,String name,String gender){
        String fileYear=year+"";
       FileResource fr = new FileResource("us_babynames_by_year/yob"+fileYear+".csv");
       int total=0;
       int count=0;
       //int rank=getRank(year,name,gender);
       for(CSVRecord rec:fr.getCSVParser(false)){
           if(rec.get(1).equals(gender)){
           if(rec.get(0).equals(name)){
            return total;
            }else{
                total+=Integer.parseInt(rec.get(2));
            }
        }
        }
       return total;
    }
    public void testGetTotalHigher(){
        int total=getTotalBirthsRankedHigher(1990,"Drew","M");
        System.out.println(total);
    }
}
