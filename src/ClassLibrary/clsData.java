package ClassLibrary;
import java.io.*;
import java.util.*;
public class clsData {
    
    private static int NumberOfTours,
            NumberOfVertices,
            NumberOfPersons,// Change for MCTOPMTW
            MinimumNumberOfPersonsInSubgroup,// Change for MCTOPMTW
            BudgetLimitation,
            NumberOfVerticesOfTypeZ=10,
            MaxNumberOfPOIsPossiblePerTour;
    private static int MaximumNumberOfVerticesOfTypeZ[] =new int[NumberOfVerticesOfTypeZ];
    
    private static float StartPoint []=new float [7];// Seven details for the start point
    private static float POI[][];//details for each point of interest
    
    static private float Distance[][]=new float[NumberOfVertices][NumberOfVertices];
    static private float POIMiddleOfTimeWindow[]=new float[NumberOfVertices];
  
    static private float NormalisedSF[];
    static private float NormalisedDistance[][];
    static private float NormalisedVistDuration[];
    static private float MaxDiscrepanceFromMidPoint[];
    static private float NormalisedTimeWindow[];
    static private float NormalisedEntreeFee[];
    static private float NormalisedTypeConstraint[];
    static boolean ExtendTimeWindows;
    static int TypeConstraintMaxDiff;
    clsData(String filename)throws IOException{
       readFromFile(filename);
       Distance=calculateDistance();
       regulateTimeWindows();
       POIMiddleOfTimeWindow=this.calculatePOIMiddleOfTimeWindow();      
       MaxNumberOfPOIsPossiblePerTour =(int)Math.ceil(getStartPoint()[6]/
                (getMinimumDistance()+getAverageDuration()));
       NormalisedSF=this.calculateNormalisedSF();
       NormalisedDistance=this.calculateNormalisedDistance();
       NormalisedVistDuration=this.calculateNormalilsedVisitDuration();
       MaxDiscrepanceFromMidPoint=this.calculateMaxDiscrepanceFromMidPoint();
       NormalisedTimeWindow=this.calculateNormalisedTimeWindow();
       NormalisedEntreeFee=this.calculateNormalisedEntreeFee();
       NormalisedTypeConstraint=this.calculateNormalisedTypeConstraint();
       ExtendTimeWindows=true;
       //changeTimeWindows();
       extendTimeWindows();
       TypeConstraintMaxDiff=this.calculateTypeConstraintMaxDiff();
      }
    clsData(clsData AnotherData){
        this.NumberOfTours=AnotherData.NumberOfTours;
        this.NumberOfPersons=AnotherData.NumberOfPersons;
        this.NumberOfVertices=AnotherData.NumberOfVertices;
        this.MinimumNumberOfPersonsInSubgroup=AnotherData.MinimumNumberOfPersonsInSubgroup;
        this.BudgetLimitation=AnotherData.BudgetLimitation;
        this.NumberOfVerticesOfTypeZ=AnotherData.NumberOfVerticesOfTypeZ;
        this.MaxNumberOfPOIsPossiblePerTour=AnotherData.MaxNumberOfPOIsPossiblePerTour;
        System.arraycopy(AnotherData.MaximumNumberOfVerticesOfTypeZ, 0, 
                this.MaximumNumberOfVerticesOfTypeZ, 0, this.MaximumNumberOfVerticesOfTypeZ.length);
        System.arraycopy(AnotherData.StartPoint, 0, 
                this.StartPoint, 0, this.StartPoint.length);       
        System.arraycopy(AnotherData.POI, 0, 
                this.POI, 0, this.POI.length);
        System.arraycopy(AnotherData.Distance, 0, 
                this.Distance, 0, this.Distance.length);
        System.arraycopy(AnotherData.POIMiddleOfTimeWindow, 0, 
                this.POIMiddleOfTimeWindow, 0, this.POIMiddleOfTimeWindow.length);
    }
    
    static public int getNumberOfTours(){
        return NumberOfTours;
    }
    static public int getNumberOfVertices(){
        return NumberOfVertices;
    }
    public static int getNumberOfPersons(){
        return NumberOfPersons;
    }
    static public int getMinimumNumberOfPersonsInSubgroup(){
        return MinimumNumberOfPersonsInSubgroup;
    }
    static public int getBudgetLimitation(){
        return BudgetLimitation;
    }
    static public int getNumberOfVerticesOfTypeZ(){
        return NumberOfVerticesOfTypeZ;
    }
    static public int getMaxNumberOfPOIsPossiblePerTour(){
        return MaxNumberOfPOIsPossiblePerTour;
    }
    static public float [] getStartPoint(){
        return StartPoint;
    }
   public static float [][] getPOI(){
       return POI;
   }
   public static int [] getMaximumNumberOfVerticesOfTypeZ(){
       return MaximumNumberOfVerticesOfTypeZ;
   }
   static public float [][] getDistance(){
       return Distance;
   }
   
   static public float getPOIMiddleOfTimeWindow(int POI_ID){
       return POIMiddleOfTimeWindow[POI_ID-2];
   }
   static public float getNormalisedSF(int POI_ID){
       return NormalisedSF [POI_ID-2];
   }

     
   static public float getNormalisedVistDuration(int POI_ID){
       return NormalisedVistDuration[POI_ID-2];
   }
   static public float getNormalisedTimeWindow(int POI_ID){
       return NormalisedTimeWindow[POI_ID-2];
   }
   static public float getNormalisedEntreeFee(int POI_ID){
       return NormalisedEntreeFee[POI_ID-2];
   }
   static public float getNormalisedTypeConstraint(int POI_ID){
       return NormalisedTypeConstraint[POI_ID-2];
   }
   static public float getNormalisedDistance(int Point1,int Point2){
       float result=-1;
       if(Point1>Point2)
           result=NormalisedDistance[Point2-1][Point1-2];
       else
           result=NormalisedDistance[Point1-1][Point2-2];
       return result;
   }
   static public float getMaxDiscrepanceFromMidPoint(int POI_ID){
       return MaxDiscrepanceFromMidPoint[POI_ID-2];
   }
   static public float getTypeConstraintMaxDiff(){
       return TypeConstraintMaxDiff;
   }
   
   
   private float [][] calculateDistance(){
        float[][] result=new float[NumberOfVertices][NumberOfVertices];
        
        for(int i=0;i<result.length;i++)
            for(int j=i;j<result[0].length;j++)
            {
                if(i==0)
                {
                    result[i][j]=(float)(
                                        Math.sqrt(
                                                    Math.pow((StartPoint[1]-POI[j][1]),2)+
                                                    Math.pow((StartPoint[2]-POI[j][2]),2)
                                                )
                                        );
                    //System.out.println("Distance:"+result[i][j]);
                }
                else
                {
                    result[i][j]=(float)(
                                        Math.sqrt(
                                                    Math.pow((POI[i-1][1]-POI[j][1]),2)+
                                                    Math.pow((POI[i-1][2]-POI[j][2]),2)
                                                )
                                        );
                }
            }
       
        return result;
    }
   private float [] calculatePOIMiddleOfTimeWindow(){
       float[] result=new float[NumberOfVertices];
       for(int i=0;i<result.length;i++)
       {
           result[i]=POI[i][4+NumberOfPersons]+(POI[i][8+NumberOfPersons]-POI[i][4+NumberOfPersons])/2;
       }
       return result;
   } 

  private float [] calculateMaxDiscrepanceFromMidPoint(){
       float[] result=new float[NumberOfVertices];
       for(int i=0;i<result.length;i++)
       {
           result[i]=POIMiddleOfTimeWindow[i] -(POI[i][4+NumberOfPersons]+POI[i][3]/2);
       }
       return result;
   } 
   
   
   
  public static float getAverageDistance(){
      float result=0;
      for (int i=0;i<Distance.length;i++)
          for(int j=i;j<Distance[0].length;j++)
              result+=Distance[i][j];
      
      result=result/(Distance.length/2*(Distance.length+1));
      return result;
  }
  public float getAverageDuration(){
      float result=0;
      for(int i=0;i<POI.length;i++)
          result+=POI[i][3]; //Data about duration is in index 4;
      return result/POI.length;
  }
  
  public float getMinimumDistance(){
      float result=Distance[0][0];
      for (int i=0;i<Distance.length;i++)
          for(int j=i;j<Distance[0].length;j++)
              if(Distance[i][j]<result)
                result=Distance[i][j];
      return result;
  }
  public float getMaximumDistance(){
      float result=Distance[0][0];
      for (int i=0;i<Distance.length;i++)
          for(int j=i;j<Distance[0].length;j++)
              if(Distance[i][j]>result)
                result=Distance[i][j];
      return result;
  }
  private float getMaximumVisitDuration(){
      float Max=POI[0][3];
      for(int i=1;i<POI.length;i++)
          if(POI[i][3]>Max)
              Max=POI[i][3];
      return Max;
  }
  
  private float getMaximumTimeWindow(){
      float Max=POI[0][8+NumberOfPersons]-POI[0][4+NumberOfPersons];
      for(int i=1;i<POI.length;i++)
          if(POI[i][8+NumberOfPersons]-POI[i][4+NumberOfPersons]>Max)
              Max=POI[i][8+NumberOfPersons]-POI[i][4+NumberOfPersons];
      return Max;
  }          
  private float getMaximumEntreeFee(){
      float Max=POI[0][10+NumberOfPersons];
      for(int i=1;i<POI.length;i++)
          if(POI[i][10+NumberOfPersons]>Max)
              Max=POI[i][10+NumberOfPersons];
      return Max;
  }
  private float [] calculateNormalisedEntreeFee(){
      float MaximumEntreeFee=this.getMaximumEntreeFee();
      float [] result =new float[NumberOfVertices];
      for(int i =0;i<result.length;i++){
          result[i]=(POI[i][10+NumberOfPersons])/MaximumEntreeFee;
      }
      return result;
  }          
  private float [] calculateNormalisedTypeConstraint(){
      float [] result =new float[NumberOfVertices];
      for(int i =0;i<result.length;i++){
          int NumberOfZeros=0;
          for(int j=11+NumberOfPersons;j<21+NumberOfPersons;j++)
              if(POI[i][j]==0)
                  NumberOfZeros++;
          result[i]=NumberOfZeros/10.0f;
      }
      return result;
  }       
  private float [] calculateNormalisedTimeWindow(){
      float MaximumTimeWindow=this.getMaximumTimeWindow();
      float [] result =new float[NumberOfVertices];
      for(int i =0;i<result.length;i++){
          result[i]=(POI[i][8+NumberOfPersons]-POI[i][4+NumberOfPersons])/MaximumTimeWindow;
      }
      return result;
  }          
          
  private float [] calculateNormalilsedVisitDuration(){
      float MaximumVisitDuration=this.getMaximumVisitDuration();
      float [] result =new float[NumberOfVertices];
      for(int i =0;i<result.length;i++){
          result[i]=POI[i][3]/MaximumVisitDuration;
      }
      return result;
  }
 public float [][] calculateNormalisedDistance(){
     float result[][]=new float[NumberOfVertices][NumberOfVertices];
     float MaximumDistance=this.getMaximumDistance(); 
     for (int i=0;i<result.length;i++)
          for(int j=i;j<result[0].length;j++)
              result[i][j]=Distance[i][j]/MaximumDistance;
     return result;
  }
  private float calculateMaximalSatisfactionFactor(){
      float Max=POI[0][4];
      for(int i=1;i<POI.length;i++)
          if(POI[i][4]>Max)
              Max=POI[i][4];
      return Max;
  }
  
  private float [] calculateNormalisedSF(){
      float MaximalSatisfactionFactor=this.calculateMaximalSatisfactionFactor();
      float [] result =new float[NumberOfVertices];
      for(int i =0;i<result.length;i++){
          result[i]=POI[i][4]/MaximalSatisfactionFactor;
        }
      return result;
  }
  
  final int calculateTypeConstraintMaxDiff(){// Sum of each type constraint limit
      int result=0;
      for(int i=0;i<MaximumNumberOfVerticesOfTypeZ.length;i++){
          result+=MaximumNumberOfVerticesOfTypeZ[i];
      }
      return result;
  }
  
   static int findPOIWithEarliestTimeWindow(){
      float MinOpenTime=(int)POI[0][4+NumberOfPersons];
      int MinPOIID=(int)POI[0][0];
      for(int i=1;i<POI.length;i++)
          if(POI[i][4+NumberOfPersons]<MinOpenTime){
              MinOpenTime=POI[i][4+NumberOfPersons];
              MinPOIID=(int)POI[i][0];
          }
      return MinPOIID;
  }
  
  void readFromFile(String filename) throws IOException {
       Reader r = new BufferedReader(new FileReader(filename));
        StreamTokenizer stok = new StreamTokenizer(r);
        stok.parseNumbers();
        stok.nextToken();
        while (stok.ttype != StreamTokenizer.TT_EOF) {

            if (stok.ttype == StreamTokenizer.TT_NUMBER)
            {      
                this.NumberOfTours=(int)stok.nval;
            }
            else
                System.out.println("Nonnumber: " + stok.sval);
            stok.nextToken();
            if (stok.ttype == StreamTokenizer.TT_NUMBER)
            {      
                this.NumberOfVertices=(int)stok.nval;
            }
            else
                System.out.println("Nonnumber: " + stok.sval);
            stok.nextToken();
            
            // Change for MCTOPMTW
             this.NumberOfPersons=1;
//            if (stok.ttype == StreamTokenizer.TT_NUMBER)
//            {      
//                this.NumberOfPersons=(int)stok.nval;
//            }
//            else
//                System.out.println("Nonnumber: " + stok.sval);
//            stok.nextToken();
//            
//            // Change for MCTOPMTW
//            if (stok.ttype == StreamTokenizer.TT_NUMBER)
//            {      
//                this.MinimumNumberOfPersonsInSubgroup=(int)stok.nval;
//            }
//            else
//                System.out.println("Nonnumber: " + stok.sval);
//            stok.nextToken();
            if (stok.ttype == StreamTokenizer.TT_NUMBER)
            {      
                this.BudgetLimitation=(int)stok.nval;
//                this.BudgetLimitation=10000;//E nxjerre jasht funksionimit bugjetin
            }
            else
                System.out.println("Nonnumber: " + stok.sval);
            stok.nextToken();
            this.NumberOfVerticesOfTypeZ=10;//Number of types of POIs
            for(int i=0;i<MaximumNumberOfVerticesOfTypeZ.length;i++){
                    if (stok.ttype == StreamTokenizer.TT_NUMBER)
                    {      
                        MaximumNumberOfVerticesOfTypeZ[i]=(int)stok.nval;
//                        MaximumNumberOfVerticesOfTypeZ[i]=20;
                    }
                    else
                        System.out.println("Nonnumber: " + stok.sval);
                    stok.nextToken();   
            }
            for(int i=0;i<StartPoint.length;i++){
                    if (stok.ttype == StreamTokenizer.TT_NUMBER)
                    {      
                         StartPoint[i]=(float)stok.nval;
                    }
                    else
                        System.out.println("Nonnumber: " + stok.sval);
                    stok.nextToken();   
            }
            
            POI=new float [NumberOfVertices][21+NumberOfPersons];
            for(int i=0;i<POI.length;i++){
                for(int j=0;j<POI[0].length;j++){
                    if (stok.ttype == StreamTokenizer.TT_NUMBER)
                    {      
                         POI[i][j]=(float)stok.nval;
                    }
                    else
                        System.out.println("Nonnumber: " + stok.sval);
                    stok.nextToken();   
                }
            }

        }
        //System.out.println("Test");
   }
  
  static void regulateTimeWindows(){
     for(int i=0;i<POI.length;i++){
         POI[i][NumberOfPersons+8]+=POI[i][3];
     }
 }
  static void extendTimeWindows(){
     for(int i=0;i<POI.length;i++){
//         POI[i][NumberOfPersons+8]+=4*POI[i][3];
     }
 }
  static void shrinkTimeWindows(){
     for(int i=0;i<POI.length;i++){
//         POI[i][NumberOfPersons+8]-=4*POI[i][3];
     }
 }
  
  static void changeTimeWindows(){
      if(ExtendTimeWindows){
          extendTimeWindows();
          ExtendTimeWindows=false;
      }
      else
      {
          shrinkTimeWindows();
          ExtendTimeWindows=true;
      }
          
  }
}