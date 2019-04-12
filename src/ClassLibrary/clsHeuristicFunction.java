/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ClassLibrary;
import java.util.ArrayList;
/**
 *
 * @author user
 */
public class clsHeuristicFunction { 
     static float wEntreeFee = 0.1f;
     static float wTravelingDistance = .0f;
     static float wVisitTime = .4f;
     static float wTimeWindow = .0f;
     static float wSatisfactionFactor = .0f;
     static float wTypeConstraint = 0.5f;
     static float wTWConflicts=0.0f;
    
     static void updateWeights2(int IterationCounter, int IterationsWithoutImprovement, int WeightStep){

         int WeightSelector=IterationsWithoutImprovement%(4*WeightStep);
         if(WeightSelector<WeightStep)
         {
             wEntreeFee=0.1f;
             wTravelingDistance=0.0f;
             wVisitTime = .4f;
             wTimeWindow = 0.f;
             wSatisfactionFactor = 0.0f;
             wTypeConstraint = 0.5f;
         }
         else if(WeightSelector>=WeightStep &&WeightSelector<2*WeightStep)
         {
             wEntreeFee=0.1f;
             wTravelingDistance=0.0f;
             wVisitTime = .5f;
             wTimeWindow = 0.f;
             wSatisfactionFactor = 0.0f;
             wTypeConstraint = 0.4f;
         }
         else if(WeightSelector>=2*WeightStep && WeightSelector<3*WeightStep)
         {
             wEntreeFee=0.1f;
             wTravelingDistance=0.0f;
             wVisitTime = .3f;
             wTimeWindow = 0.f;
             wSatisfactionFactor = 0.3f;
             wTypeConstraint = 0.3f;
         }
         else if(WeightSelector>=3*WeightStep && WeightSelector<4*WeightStep)
         {
             wEntreeFee=0.0f;
             wTravelingDistance=0.0f;
             wVisitTime = 0.0f;
             wTimeWindow = 0.0f;
             wSatisfactionFactor = 1.0f;
             wTypeConstraint = 0.0f;
         }
    }

     
     static void updateWeights(int IterationsWithoutImprovement, int WeightStep){
         
         int WeightSelector=IterationsWithoutImprovement%(4*WeightStep);
         if(WeightSelector<WeightStep)
         {
             wEntreeFee=0.0f;
             wTravelingDistance=0.0f;
             wVisitTime = 1.0f;
             wTimeWindow = 0.f;
             wSatisfactionFactor = 0.f;
             wTypeConstraint = 0.0f;
         }
         else if(WeightSelector>=WeightStep &&WeightSelector<2*WeightStep)
         {
             wEntreeFee=0.0f;
             wTravelingDistance=0.0f;
             wVisitTime = 0.0f;
             wTimeWindow = 0.0f;
             wSatisfactionFactor = 1.f;
             wTypeConstraint = 0.f;
         }
         else if(WeightSelector>=2*WeightStep && WeightSelector<3*WeightStep)
         {
             wEntreeFee=0.0f;
             wTravelingDistance=0.0f;
             wVisitTime = 0.0f;
             wTimeWindow = 0.0f;
             wSatisfactionFactor = 0.0f;
             wTypeConstraint = 1.0f;
         }
         else if(WeightSelector>=3*WeightStep && WeightSelector<4*WeightStep)
         {
             wEntreeFee=1.0f;
             wTravelingDistance=0.0f;
             wVisitTime = 0.0f;
             wTimeWindow = 0.0f;
             wSatisfactionFactor = 0.0f;
             wTypeConstraint = 0.0f;
         }
        else if(WeightSelector>=4*WeightStep && WeightSelector<5*WeightStep)
         {
             wEntreeFee=0.3f;
             wTravelingDistance=0.3f;
             wVisitTime = 0.1f;
             wTimeWindow = 0.0f;
             wSatisfactionFactor = 0.0f;
             wTypeConstraint = 0.1f;
         }
        else if(WeightSelector>=5*WeightStep && WeightSelector<6*WeightStep)
         {
             wEntreeFee=0.1f;
             wTravelingDistance=0.3f;
             wVisitTime = 0.3f;
             wTimeWindow = 0.1f;
             wSatisfactionFactor = 0.0f;
             wTypeConstraint = 0.0f;
         }
     }
    
//    static float getHeuristicEvaluation(int POI_ID,int LeftPoint, int RightPoint,int CurrentTypeCounter[],
//                                        ArrayList<Integer> Points, ArrayList<Float> AssignedTimes,float CurrentCost){       
//        float result=(float)Math.pow(clsData.getPOI()[POI_ID-2][4],2)/
//                (getVisitTime2(Points,AssignedTimes)+getEntreeFee2(CurrentCost)+getTypeConstraint2(POI_ID,CurrentTypeCounter));
////                +
////                wTWConflicts*(1-NumberOfConflicts/(float)NumberOfPOIsInItinerary);
//        return result;
//    }
    
    //untill 17.07.2012
        static float getHeuristicEvaluation(int POI_ID,int LeftPoint, int RightPoint,int CurrentTypeCounter[],
                                        ArrayList<Integer> Points, ArrayList<Float> AssignedTimes,float CurrentCost){       
        float result=wSatisfactionFactor*clsData.getNormalisedSF(POI_ID)+
                wTravelingDistance*getTimeConsumption(POI_ID, LeftPoint, RightPoint)+
                wVisitTime*getVisitTime(Points,AssignedTimes)+
                wTimeWindow*clsData.getNormalisedTimeWindow(POI_ID)+
                wEntreeFee*getEntreeFee(CurrentCost)+
                wTypeConstraint*getTypeConstraint(POI_ID,CurrentTypeCounter);
//                +
//                wTWConflicts*(1-NumberOfConflicts/(float)NumberOfPOIsInItinerary);
        return result;
    }
    
    
    static float getEntreeFee( float CurrentCost){
        float result=(1-CurrentCost/clsData.getBudgetLimitation());
        return result;
    }     
    static float getEntreeFee2( float CurrentCost){
        float result=(CurrentCost/clsData.getBudgetLimitation());
        return result;
    }
    static float getTypeConstraint(int Point, int CurrentTypeCounter[]){
        float result;
        if(CurrentTypeCounter==null)
            result=0;
        else
        {
            int diff=0;
            for(int i=0;i<10;i++){
                diff=diff+ 
                (clsData.getMaximumNumberOfVerticesOfTypeZ()[i]-
                CurrentTypeCounter[i]-(int)clsData.getPOI()[Point-2][11+
                clsData.getNumberOfPersons()]);
            }
            result=diff/clsData.getTypeConstraintMaxDiff();
            }
        return result;
    } 
    static float getTypeConstraint2(int Point, int CurrentTypeCounter[]){
        float result;
        if(CurrentTypeCounter==null)
            result=0;
        else
        {
            int diff=0;
            for(int i=0;i<10;i++){
                diff=diff+ 
                (clsData.getMaximumNumberOfVerticesOfTypeZ()[i]-
                CurrentTypeCounter[i]-(int)clsData.getPOI()[Point-2][11+
                clsData.getNumberOfPersons()]);
            }
            result=1-diff/clsData.getTypeConstraintMaxDiff();
            }
        return result;
    }
    
    
    static float getTimeConsumption(int Point, int LeftPOInt, int RightPoint){
        float result;
        result=1-(clsData.getNormalisedDistance(Point, LeftPOInt)+clsData.getNormalisedVistDuration(Point)+
                clsData.getNormalisedDistance(Point, RightPoint))/3;
        return result;
    }
    
//    static float getVisitTime(int POI_ID, float AssignedTime){
//        float result=1-Math.abs((AssignedTime-clsData.getPOIMiddleOfTimeWindow(POI_ID))/
//                clsData.getMaxDiscrepanceFromMidPoint(POI_ID));
//        return result;
//    }
    
    static float getVisitTime(ArrayList<Integer> Points, ArrayList<Float> AssignedTimes){
        float NormalisedSumOfDifference=0;
        for(int i=0;i<Points.size();i++)
            NormalisedSumOfDifference+=( Math.abs((AssignedTimes.get(i)-clsData.getPOIMiddleOfTimeWindow(Points.get(i))))/
                               clsData.getMaxDiscrepanceFromMidPoint(Points.get(i))
                             );
        float result=1-NormalisedSumOfDifference/Points.size();
        return result;
    }
    static float getVisitTime2(ArrayList<Integer> Points, ArrayList<Float> AssignedTimes){
        float NormalisedSumOfDifference=0;
        for(int i=0;i<Points.size();i++)
            NormalisedSumOfDifference+=( Math.abs((AssignedTimes.get(i)-clsData.getPOIMiddleOfTimeWindow(Points.get(i))))/
                               clsData.getMaxDiscrepanceFromMidPoint(Points.get(i))
                             );
        float result=NormalisedSumOfDifference/Points.size();
        return result;
    }
    static float getSwapOutEvaluation(int InsertedPOI_ID, int VisitIndex,clsVisit Itinerary[],
                                      int TourLastVisit, float IndicatedStartTime, 
                                      float IndicatedEndTime,int CurrentTypeCounter[],
                                      float ShiftingValue,float CurrentCost){
        float result;
        int LeftPoint,RightPoint;
        if(VisitIndex==0)
            LeftPoint=1;
        else
            LeftPoint=Itinerary[VisitIndex-1].POI_ID;
        if(VisitIndex==TourLastVisit)
            RightPoint=1;
        else
            RightPoint=Itinerary[VisitIndex+1].POI_ID;
        
        ArrayList<Integer> Points=new ArrayList();
        ArrayList<Float> AssignedTimes=new ArrayList();
        create_PointsAndAssignedTimes_Lists(InsertedPOI_ID,VisitIndex,Itinerary,IndicatedStartTime,IndicatedEndTime,
                                            ShiftingValue,Points,AssignedTimes,VisitIndex+1,TourLastVisit);

        result=getHeuristicEvaluation(InsertedPOI_ID, LeftPoint, RightPoint,
                                      CurrentTypeCounter,Points,AssignedTimes,CurrentCost);
        return result;
    }
    
        static float getInsertEvaluation(int InsertedPOI_ID, int VisitIndex,clsVisit Itinerary[],
                                      int TourLastVisit, float IndicatedStartTime, 
                                      float IndicatedEndTime,int CurrentTypeCounter[],
                                      float ShiftingValue, float CurrentCost){
        float result;
        int LeftPoint,RightPoint;
        if(VisitIndex==0)
            LeftPoint=1;
        else if(VisitIndex==TourLastVisit+1)
            LeftPoint=Itinerary[VisitIndex-1].POI_ID;
        else
            LeftPoint=Itinerary[VisitIndex-1].POI_ID;
        if(VisitIndex==TourLastVisit+1)
            RightPoint=1;
        else
            RightPoint=Itinerary[VisitIndex].POI_ID;

        ArrayList<Integer> Points=new ArrayList();
        ArrayList<Float> AssignedTimes=new ArrayList();
        create_PointsAndAssignedTimes_Lists(InsertedPOI_ID,VisitIndex,Itinerary,IndicatedStartTime,IndicatedEndTime,
                                            ShiftingValue,Points,AssignedTimes,VisitIndex,TourLastVisit);

        result=getHeuristicEvaluation(InsertedPOI_ID, LeftPoint, RightPoint,
                                      CurrentTypeCounter,Points,AssignedTimes,CurrentCost);
        return result;
    }
    
    
    
    static void create_PointsAndAssignedTimes_Lists(int InsertedPOI_ID, int VisitIndex,clsVisit Itinerary[],
                                      float IndicatedStartTime, float IndicatedEndTime,
                                      float ShiftingValue,
                                      ArrayList<Integer> Points, ArrayList<Float> AssignedTimes,
                                      int StartIndex, int EndIndex){
        float InsertedPointTimeOfAssignment;
        InsertedPointTimeOfAssignment=IndicatedStartTime+ (IndicatedEndTime-IndicatedStartTime)/2;
        Points.add(InsertedPOI_ID);
        AssignedTimes.add(InsertedPointTimeOfAssignment);
        
        for(int i=StartIndex;i<=EndIndex;i++){
            Points.add( Itinerary[i].POI_ID);
            float UpdatedTimeOfAssignment=ShiftingValue+
                    Itinerary[i].constraints.TimeWindow.StartTime+
                    (Itinerary[i].constraints.TimeWindow.EndTime-
                    Itinerary[i].constraints.TimeWindow.StartTime)/2;
             AssignedTimes.add(UpdatedTimeOfAssignment);
        }   
    }

    static float getNeighboorSwapEvaluationInRemoveingTour(int InsertedPOI_ID, int VisitIndex,clsVisit Itinerary[],
                                      int VisitIndexInInsertingTour, float IndicatedStartTime, float IndicatedEndTime,int CurrentTypeCounter[],
                                      float RemoveingTourShiftingValue,boolean SwapBetweenNeighbourPOIs, float CurrentCost){
        float result;
        int LeftPoint,RightPoint;
        if(VisitIndex==0)
            LeftPoint=1;
        else
            LeftPoint=Itinerary[VisitIndex-1].POI_ID;
        RightPoint=Itinerary[VisitIndex].POI_ID;

        ArrayList<Integer> Points=new ArrayList();
        ArrayList<Float> AssignedTimes=new ArrayList();
        int StartIndex=VisitIndex+1,EndIndex;
        if(SwapBetweenNeighbourPOIs){
            EndIndex=VisitIndex;
        }
        else
        {
             EndIndex=VisitIndexInInsertingTour-1;
        }
        
        create_PointsAndAssignedTimes_Lists(InsertedPOI_ID,VisitIndex,Itinerary,IndicatedStartTime,IndicatedEndTime,
                                            RemoveingTourShiftingValue,Points,AssignedTimes,StartIndex,EndIndex);

        result=getHeuristicEvaluation(InsertedPOI_ID, LeftPoint, RightPoint,
                CurrentTypeCounter,Points,AssignedTimes,CurrentCost);
        return result;
    }
    static float getNeighboorSwapEvaluationInInsertingTour( int InsertedPOI_ID, int VisitIndex,clsVisit Itinerary[],
                                                            int TourLastVisit, float IndicatedStartTime, 
                                                            float IndicatedEndTime,int CurrentTypeCounter[],
                                                            float RemoveingTourShiftingValue, float InsertingTourShiftingValue,
                                                            float CurrentCost){
        float result;
        int LeftPoint,RightPoint;
        LeftPoint=Itinerary[VisitIndex].POI_ID;
        if(VisitIndex==TourLastVisit)
            RightPoint=1;
        else
            RightPoint=Itinerary[VisitIndex+1].POI_ID;
        
        ArrayList<Integer> Points=new ArrayList();
        ArrayList<Float> AssignedTimes=new ArrayList();
        create_PointsAndAssignedTimes_Lists(InsertedPOI_ID,VisitIndex,Itinerary,IndicatedStartTime,IndicatedEndTime,
                                            RemoveingTourShiftingValue+InsertingTourShiftingValue,Points,AssignedTimes,
                                            VisitIndex+1,TourLastVisit);
        result=getHeuristicEvaluation(InsertedPOI_ID, LeftPoint, RightPoint,CurrentTypeCounter,Points,AssignedTimes,CurrentCost);
        return result;
    }
    static float getSwapInEvaluation(int RemovedPOI_ID, int InsertedPOI_ID, 
                                     int VisitIndexInRemoveingTour,int VisitIndexInInsertingTour,
                                     clsVisit RemoveingTourItinerary[], clsVisit InsertingTourItinerary[],
                                     int LastVisitIndexInRemoveingTour, int LastVisitIndexInInsertingTour,
                                     float IndicatedStartTimeInRemoveingTour, float IndicatedEndTimeInRemoveingTour,
                                     float IndicatedStartTimeInInsertingTour, float IndicatedEndTimeInInsertingTour,
                                     boolean SwapInSameTour,
                                     boolean SwapBetweenNeighbourPOIs,
                                     float RemoveingTourShiftingValue,
                                     float InsertingTourShiftingValue,
                                     float CurrentCost){
        float result;
        int CurrentTypeCounter[]=null;
        float RemoveingTourEvaluation,InsertingTourEvaluation;
        if(SwapInSameTour){
                RemoveingTourEvaluation=getNeighboorSwapEvaluationInRemoveingTour(InsertedPOI_ID,VisitIndexInRemoveingTour,
                        RemoveingTourItinerary, VisitIndexInInsertingTour,IndicatedStartTimeInRemoveingTour,IndicatedEndTimeInRemoveingTour,
                        CurrentTypeCounter,RemoveingTourShiftingValue,SwapBetweenNeighbourPOIs, CurrentCost);
                InsertingTourEvaluation=getNeighboorSwapEvaluationInInsertingTour(RemovedPOI_ID,VisitIndexInInsertingTour,
                        InsertingTourItinerary,LastVisitIndexInInsertingTour,
                        IndicatedStartTimeInInsertingTour,IndicatedEndTimeInInsertingTour,CurrentTypeCounter,
                        RemoveingTourShiftingValue,InsertingTourShiftingValue,CurrentCost);  
        }
        else
        {
            RemoveingTourEvaluation=getSwapOutEvaluation(InsertedPOI_ID,VisitIndexInRemoveingTour,RemoveingTourItinerary,
                    LastVisitIndexInRemoveingTour,IndicatedStartTimeInRemoveingTour,IndicatedEndTimeInRemoveingTour,
                    CurrentTypeCounter,RemoveingTourShiftingValue,CurrentCost);
            InsertingTourEvaluation=getSwapOutEvaluation(RemovedPOI_ID,VisitIndexInInsertingTour,
                    InsertingTourItinerary,LastVisitIndexInInsertingTour,
                    IndicatedStartTimeInInsertingTour,IndicatedEndTimeInInsertingTour,
                    CurrentTypeCounter,InsertingTourShiftingValue,CurrentCost);
        }

        result=RemoveingTourEvaluation+InsertingTourEvaluation;
        return result;
    }
}
