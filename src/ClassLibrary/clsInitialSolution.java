/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ClassLibrary;
import java.util.*;
/**
 *
 * @author Kadri Sylejmani
 */
public class clsInitialSolution  {
    private clsSolution solution;

    clsSolution getInitialSolution(){
        boolean ThereIsASolution=false;
        while(!ThereIsASolution){
            create();
             ThereIsASolution=MakeSureThereIsASolution(); 
        }
        return solution;
    }
    
    boolean MakeSureThereIsASolution(){
        boolean rez=true;
        for(int i=0;i<solution.Itinerary.length;i++){
            if(solution.Itinerary[i][0]==null){
                rez=false;
            }
        }
        return rez;
    }
    
    final void create(){      
        solution=new clsSolution();
        addPOIsInOffList(); 
        boolean ThereIsPossibilityToInsertNewVisit=true;

        while(ThereIsPossibilityToInsertNewVisit){

           
            clsVisit visit=addPOIinTrip();
           if(visit.Reply==1)
           {
                solution.Itinerary[visit.constraints.TimeWindow.TourIndeks][visit.constraints.TimeWindow.VisitIndeks]=visit;
                solution.FlatItinerary[visit.constraints.TimeWindow.TourIndeks][visit.constraints.TimeWindow.VisitIndeks]=visit.POI_ID;
                float VisitDuration=clsData.getPOI()[visit.POI_ID-2][3];
                if(visit.constraints.TimeWindow.VisitIndeks==0){
                    solution.TourTimeSpent[visit.constraints.TimeWindow.TourIndeks]=
                            (visit.constraints.TimeWindow.UnusedTimeAtTheStartOfTheTour+
                            visit.constraints.TimeWindow.DistanceFromPrevioiusPoint+
                            VisitDuration +
                            visit.constraints.TimeWindow.DistanceToNextPoint);
                }
                else{
                    float DistaceFromPreviousLastPointToHome=clsGeneral.getDistanceBetweenPoints(
                            solution.FlatItinerary[visit.constraints.TimeWindow.TourIndeks][visit.constraints.TimeWindow.VisitIndeks-1],
                            1, 
                            clsData.getDistance());
                    solution.TourTimeSpent[visit.constraints.TimeWindow.TourIndeks]= 
                            solution.TourTimeSpent[visit.constraints.TimeWindow.TourIndeks]-
                            DistaceFromPreviousLastPointToHome+
                            visit.constraints.TimeWindow.DistanceFromPrevioiusPoint+
                            VisitDuration+
                            visit.constraints.TimeWindow.DistanceToNextPoint;
                    //Azhurnimi i kohese se udhetimit te pikese pararapke te pika e shtuar
                    solution.Itinerary[visit.constraints.TimeWindow.TourIndeks][visit.constraints.TimeWindow.VisitIndeks-1].
                            constraints.TimeWindow.DistanceToNextPoint=
                    solution.Itinerary[visit.constraints.TimeWindow.TourIndeks][visit.constraints.TimeWindow.VisitIndeks].
                            constraints.TimeWindow.DistanceFromPrevioiusPoint;
                }
                solution.BudgetCost+=visit.constraints.Cost;
                solution.TypeConstraintCounter=visit.constraints.POIType.POITypeCounter;
                clsGeneral.updateTourLastVisit(solution.TourLastVisit, visit.POI_ID, visit.constraints.TimeWindow.TourIndeks, 
                        visit.constraints.TimeWindow.VisitIndeks);
                solution.TripTimeCost=clsGeneral.getTripSpentTime(solution.TourTimeSpent);
                solution.POIsInItinerary.add(visit.POI_ID);
                solution.POIsOffItinerary.remove(visit.RemoveIndexInOffList);
                clsGeneral.UpdateMaxShift(visit.POI_ID, visit.constraints.TimeWindow, 
                        solution.MaxShift, clsData.getPOI(), clsData.getNumberOfPersons(),
                       solution.TourLastVisit);//Testimi 

                //System.out.println("Test");
               
           }
           else {
               ThereIsPossibilityToInsertNewVisit=false;
           }
        }
        solution.Evaluation=solution.getInitialEvaluation();
        solution.MaxShiftImpactInTrip=clsGeneral.getMaxShiftImpactInTrip(solution.TourLastVisit, solution.MaxShift);
        //System.out.println("Prova");   
    }
    
    clsVisit addPOIinTrip(){
        clsVisit result=new clsVisit();
        boolean LoopIterationIsViable=true;
//        boolean SelectRandomPOIWithTimeWindow=true;// used to be false
        boolean ThereIsNoPOIOpenAtTheStartOfTheTour=false;
        int TypeConstraintViolationCounter=0;
        int CounterForPOIsOffItinerarySortedBasedOnTimeWindow=0;
        while(LoopIterationIsViable){ 
            int randomOffPOI_Indeks;
            int POI_ID;
//            if(SelectRandomPOIWithTimeWindow){
                ArrayList<Integer> IndeksInOffList=new ArrayList();
                ArrayList<Integer> POIsOpenInPeriod=clsGeneral.getPOIsWithTimeWindowsInPeriod(solution.POIsOffItinerary, 
                        IndeksInOffList,
                        solution.TourTimeSpent, clsData.getStartPoint()[6], 
                        solution.TourLastVisit, clsData.getPOI(), clsData.getDistance(), clsData.getNumberOfPersons());
                int randomPOIsOpenInPeriod_Indeks;
                if(POIsOpenInPeriod.size()>1){
                    randomPOIsOpenInPeriod_Indeks=clsGeneral.getRandomNumber(POIsOpenInPeriod.size()-1);
                    POI_ID=POIsOpenInPeriod.get(randomPOIsOpenInPeriod_Indeks);
                }
                else if(POIsOpenInPeriod.size()==1){
                     randomPOIsOpenInPeriod_Indeks=0;
                     POI_ID=POIsOpenInPeriod.get(randomPOIsOpenInPeriod_Indeks);
                }
                else
                {
                    POI_ID=this.getPOIsOffItinerarySortedBasedOnTimeWindow(solution.POIsOffItinerary).
                            get(CounterForPOIsOffItinerarySortedBasedOnTimeWindow);
                    randomPOIsOpenInPeriod_Indeks=solution.POIsOffItinerary.indexOf(POI_ID);
//                    randomPOIsOpenInPeriod_Indeks=clsGeneral.getRandomNumber(solution.POIsOffItinerary.size()-1); 
//                    POI_ID=solution.POIsOffItinerary.get(randomPOIsOpenInPeriod_Indeks);
                    ThereIsNoPOIOpenAtTheStartOfTheTour=true;
                    CounterForPOIsOffItinerarySortedBasedOnTimeWindow++;
                    
                    //Dy gabime: Dritaret Kohore dhe Mbetje ne Unaze
                }
                if(!IndeksInOffList.isEmpty())
                    randomOffPOI_Indeks=IndeksInOffList.get(randomPOIsOpenInPeriod_Indeks);
                else
                    randomOffPOI_Indeks=solution.POIsOffItinerary.indexOf(POI_ID);
//            }
//            else
//            {
//                randomOffPOI_Indeks=clsGeneral.getRandomNumber(solution.POIsOffItinerary.size()-1);
//                POI_ID=solution.POIsOffItinerary.get(randomOffPOI_Indeks);
//            }
            clsConstraint Constraints=ChechkPOIConstraints(POI_ID,ThereIsNoPOIOpenAtTheStartOfTheTour);
            if(Constraints.Reply==1){//There is space in tours
                result.Reply=1;
                result.POI_ID=POI_ID;
                result.RemoveIndexInOffList=randomOffPOI_Indeks;               
                result.constraints=Constraints;
                LoopIterationIsViable=false;
                TypeConstraintViolationCounter=0;
            }
            else if(Constraints.Reply==-3 && Constraints.TimeWindow.Replay==-3){//All tours have two POIs
                result.Reply=-1;
                LoopIterationIsViable=false;
            }
            else if(Constraints.Reply==-3 && Constraints.TimeWindow.Replay==-2){//There is no space in tours
                result.Reply=-1;
                LoopIterationIsViable=false;
            }
            else if(Constraints.Reply==-3 && Constraints.TimeWindow.Replay==-1){//Time Window of current POI is surpassed
                result.Reply=-5;
                LoopIterationIsViable=false;  
            }
            else if(Constraints.Reply==-2){ // TypeConstraint violation
                TypeConstraintViolationCounter++;
                if(TypeConstraintViolationCounter==5){
                     result.Reply=-3;
                     LoopIterationIsViable=false;
                     //System.out.println("Prova");
                }
              }
            else if(Constraints.Reply==-1){ // Budget violation
                     result.Reply=-4;
                     LoopIterationIsViable=false;
                     //System.out.println("Prova");
            }
            else {
                 LoopIterationIsViable=true;
//                SelectRandomPOIWithTimeWindow=true;
            }
        }
     return result;
    }
    
     clsConstraint ChechkPOIConstraints(int POI_ID, boolean ThereIsNoPOIOpenAtTheStartOfTheTour){
         clsConstraint result=new clsConstraint();
         float Cost=clsGeneral.CheckBudget(clsData.getBudgetLimitation(), solution.BudgetCost, 
                 clsData.getPOI()[POI_ID-2][10+clsData.getNumberOfPersons()]);
         if(Cost!=-1){
             result.Cost=Cost;
             float POI []=clsData.getPOI()[POI_ID-2];
             clsTypeConstraint TypeConstraint=clsGeneral.CheckTypeConstraint(
                     solution.TypeConstraintCounter, clsData.getMaximumNumberOfVerticesOfTypeZ(),
                     POI, clsData.getNumberOfPersons());
             if(TypeConstraint.Reply==0||TypeConstraint.Reply==1){
                    result.POIType=TypeConstraint;
                     clsTimeWindowConstraint TimeWindowConstraint=clsGeneral.ChechkPOITimeWindow(
                         POI_ID, solution.TourTimeSpent,
                         clsData.getStartPoint()[6], POI[3],solution.TourLastVisit, 
                         clsData.getDistance(), 
                         POI[4+clsData.getNumberOfPersons()],
                         POI[8+clsData.getNumberOfPersons()],
                         ThereIsNoPOIOpenAtTheStartOfTheTour,
                         solution.TourLastVisit);
                 result.TimeWindow=TimeWindowConstraint;
                 if(TimeWindowConstraint.Replay==1){
                     result.Reply=1;
                 }
                 else
                     result.Reply=-3;
             }
            else
                 result.Reply=-2;
         }
         else
             result.Reply=-1;
         
         return result;
    }
    
    ArrayList<Integer> getPOIsOffItinerarySortedBasedOnTimeWindow(ArrayList<Integer> POIsOffItinerary){
        ArrayList<Integer> result=new ArrayList();
        result.addAll(POIsOffItinerary);
        sortListOfPOIs(result);
        return result;
    }
     
    final void addPOIsInOffList(){
        for(int i=0;i<clsData.getPOI().length;i++)
            solution.POIsOffItinerary.add((int)clsData.getPOI()[i][0]);
   }
    
    void sortListOfPOIs(ArrayList <Integer> ListOfPOIs){
            int X;
            for(int i=0;i<ListOfPOIs.size()-1;i++){
                for(int j=i+1;j<ListOfPOIs.size();j++){
                    boolean TimeWindwoStartsLater;
                            TimeWindwoStartsLater=clsData.getPOI()[ListOfPOIs.get(i) -2][4+clsData.getNumberOfPersons()] >
                                      clsData.getPOI()[ListOfPOIs.get(j)-2][4+clsData.getNumberOfPersons()];
                    if(TimeWindwoStartsLater){
                        X=ListOfPOIs.get(i);
                        ListOfPOIs.add(i, ListOfPOIs.get(j));
                        ListOfPOIs.remove(i+1);
                        ListOfPOIs.add(j, X);
                        ListOfPOIs.remove(j+1);
                    }
                }
            }
    } 
 }
