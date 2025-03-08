package it.unibo.nestedenum;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Implementation of {@link MonthSorter}.
 */
public final class MonthSorterNested implements MonthSorter {

    enum Month{
        JANUARY(31,1),
        FEBRUARY(28,2),
        MARCH(31,3),
        APRIL(30,4),
        MAY(31,5),
        JUNE(30,6),
        JULY(31,7),
        AUGUST(31,8),
        SEPTEMBER(30,9),
        OCTOBER(31,10),
        NOVEMBER(30,11),
        DECEMBER(31,12);
        
        private final int days;
        private final int priority;
        private Month(final int days,final int priority){
            this.days=days;
            this.priority=priority;
        }

        public int getDays(){
            return this.days;
        }

        public int getPrio(){
            return this.priority;
        }

        static public  Month fromString(String text){
            String upText=text.toUpperCase();
            ArrayList<String> exception=new ArrayList<>();
            ArrayList<String> allMonth =new ArrayList<>(listOfMonthToString());
            // for every enum, search if it match with some, if not we iterate each character until it match with something
            
                for(String tomatch: allMonth){             
                    if(upText.equals(tomatch)){
                        return Month.valueOf(tomatch);
                    }
                    else if(tomatch.length()> upText.length() && tomatch.substring(0,upText.length()).equals(upText) ){ //HA SENSO SE LA STRINGA BASE E' PIU GRANDE DI QUELLA INSERITA  e //sottostringa di tomatch che va dall inizio allo stesso indice di quella inserita deve essere uguale a quella inserita
                        exception.add(tomatch);
                    }
                }

                if(exception.size() == 1){
                    return Month.valueOf(exception.get(0));
                }
                else if(exception.size() >1){
                    throw new IllegalArgumentException("Ambiguous input: " + text + " matches " + exception);
                }
    
                throw new IllegalArgumentException("ERROR no month with such name" + exception);
                
        }

    }

        
    
    
    //convert the ENUMS into a list of string
        private static ArrayList<String> listOfMonthToString(){
            ArrayList<String> allMonth =new ArrayList<>();
            for(final Month m: Month.values()){    
                allMonth.add(m.toString());
            }
            return new ArrayList<>(allMonth);
        }
    
    
     @Override
     public Comparator<String> sortByDays() {
        return new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return Integer.compare(
                Month.fromString(o1).getDays(), 
                Month.fromString(o2).getDays());  
            }
        };
    }

     @Override
     public Comparator<String> sortByOrder() {
        
        return new Comparator<String>() {
            
            @Override
            public int compare(String o1, String o2) {
                return Integer.compare(
                Month.fromString(o1).getPrio(),
                Month.fromString(o2).getPrio());
            }
        };
    }
}
