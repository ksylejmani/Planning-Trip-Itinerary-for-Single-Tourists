package ClassLibrary;
import java.io.*;

public class clsStart {


    
    private static clsData data;
    public static void main(String[] args) throws IOException{
         clsExperiment  experiment=new clsExperiment();
         experiment.CreateFile("Experiment MAX_ITERATIONS");
        
        int NumberOfParameterValues=6; 
        int NumberOfInstances=148;
        int NumberOfExecutions=10;
        for(int i=1;i<=NumberOfParameterValues;i++){
            int  MAX_ITERATIONS=40000+2000*(i-1);
            //Write titles
            String SheetName="MI "+ MAX_ITERATIONS;
            experiment.CreateSheet(SheetName,i-1);
            experiment.WriteColumnTitle("Instance", 0,0);
            for(int j=1;j<=10;j++){
                experiment.WriteColumnTitle("Value"+j, j,0);
            }
            for(int j=1;j<=10;j++){
                experiment.WriteColumnTitle("Time"+j, j+10,0);
            }
            experiment.WriteColumnTitle("Max value", 21,0);
            experiment.WriteColumnTitle("Average value", 22,0);
            experiment.WriteColumnTitle("Min value", 23,0);
            experiment.WriteColumnTitle("Max time", 24,0);
            experiment.WriteColumnTitle("Average time", 25,0);
            experiment.WriteColumnTitle("Min time", 26,0);
            experiment.WriteColumnTitle("Sum(S)", 27,0);
            experiment.WriteColumnTitle("Benchmark", 28,0);
            
            for(int j=1;j<=NumberOfInstances;j++){
                
                String Instance[]=getFileName(j);
                data=new clsData(Instance[1]);  
                clsTabooSearch TabooSearch=new clsTabooSearch();
                clsSolution solution;
                experiment.WriteRowData(i-1,0,j, Instance[0]);
                
                for(int k=1;k<=NumberOfExecutions;k++){
                    
                    long startTime=System.currentTimeMillis();
                    solution=TabooSearch.findSolution(MAX_ITERATIONS); 
                    long endTime=System.currentTimeMillis();
                    long ExecutionTime=endTime-startTime;
                    experiment.WriteRowData(i-1,k, j, solution.Evaluation);
                    experiment.WriteRowData(i-1,k+10, j, ExecutionTime);
                }
            }
        }
        experiment.closeExcelFile();
        //clsTest.PrintTrip(solution);
    }
    
    
    static String []getFileName(int InstanceCounter){
        String result[]=new String[2];
        int NumberOfTours;
        String InstanceGroup;
        String InstanceName;
        int InstanceIndex;
        if(InstanceCounter<=37)
        {
            NumberOfTours=1;
            if(InstanceCounter<=29)
            {
                InstanceGroup="Solomon";
                if(InstanceCounter<=9)
                {
                    InstanceName="c";
                    InstanceIndex=100+InstanceCounter;
                }
                else if(InstanceCounter<=21)
                {
                    InstanceName="r";
                    InstanceIndex=100+InstanceCounter-9;
                }
                else
                {
                    InstanceName="rc";
                    InstanceIndex=100+InstanceCounter-21;
                }
            }
            else
            {
                InstanceGroup="Cordeau";
                InstanceName="pr0";
                InstanceIndex=InstanceCounter-29;
                if(InstanceIndex>5)
                    InstanceIndex++;
            }
        }
        else if(InstanceCounter<=74)
        {
            NumberOfTours=2;
            if(InstanceCounter<=66)
            {
                InstanceGroup="Solomon";
                if(InstanceCounter<=46)
                {
                    InstanceName="c";
                    InstanceIndex=100+InstanceCounter-37;
                }
                else if(InstanceCounter<=58)
                {
                    InstanceName="r";
                    InstanceIndex=100+InstanceCounter-46;
                }
                else
                {
                    InstanceName="rc";
                    InstanceIndex=100+InstanceCounter-58;
                }
            }
            else
            {
                InstanceGroup="Cordeau";
                InstanceName="pr0";
                InstanceIndex=InstanceCounter-66;
                if(InstanceIndex>5)
                    InstanceIndex++;
            }
        }
        else if(InstanceCounter<=111)
        {
            NumberOfTours=3;
            if(InstanceCounter<=103)
            {
                InstanceGroup="Solomon";
                if(InstanceCounter<=83)
                {
                    InstanceName="c";
                    InstanceIndex=100+InstanceCounter-74;
                }
                else if(InstanceCounter<=95)
                {
                    InstanceName="r";
                    InstanceIndex=100+InstanceCounter-83;
                }
                else
                {
                    InstanceName="rc";
                    InstanceIndex=100+InstanceCounter-95;
                }
            }
            else
            {
                InstanceGroup="Cordeau";
                InstanceName="pr0";
                InstanceIndex=InstanceCounter-103;
                if(InstanceIndex>5)
                    InstanceIndex++;
            }
        }
        else
        {
            NumberOfTours=4;
            if(InstanceCounter<=140)
            {
                InstanceGroup="Solomon";
                if(InstanceCounter<=120)
                {
                    InstanceName="c";
                    InstanceIndex=100+InstanceCounter-111;
                }
                else if(InstanceCounter<=132)
                {
                    InstanceName="r";
                    InstanceIndex=100+InstanceCounter-120;
                }
                else
                {
                    InstanceName="rc";
                    InstanceIndex=100+InstanceCounter-132;
                }
            }
            else
            {
                InstanceGroup="Cordeau";
                InstanceName="pr0";
                InstanceIndex=InstanceCounter-140;
                if(InstanceIndex>5)
                    InstanceIndex++;
            }
        }
        String FileName="MCTOPMTW-"+NumberOfTours+"-"+InstanceName+InstanceIndex+".txt";
        result[0]= FileName;
        result[1] ="Instances"+File.separator+"MCTOPMTW"+ File.separator+"MCTOPMTW-"+InstanceGroup+File.separator+FileName;
        return result;
    }
}
