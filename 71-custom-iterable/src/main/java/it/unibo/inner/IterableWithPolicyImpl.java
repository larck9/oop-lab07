package it.unibo.inner;
import java.util.*;
import it.unibo.inner.api.IterableWithPolicy;
import it.unibo.inner.api.Predicate;

public class IterableWithPolicyImpl<T> implements IterableWithPolicy<T> {
    
    private T [] arrayofT;
    private Predicate<T> filter;

    IterableWithPolicyImpl(final T[] array){
        
        this(array,new AlwaysTruePredicate<>());
    }

    IterableWithPolicyImpl(final T[] array, Predicate<T> filter){
        this.arrayofT=array.clone();
        this.filter=filter;
    }

    @Override
    public void setIterationPolicy(Predicate<T> filter) {
        this.filter=filter;
    }
    
    
    public Iterator<T> iterator(){
        return new Inner();
    }
     

    public String toString() {
        List<T> elements = new ArrayList<>();
        Iterator<T> it = iterator();
        while (it.hasNext()) {
            elements.add(it.next()); // Add each element to the list
        }
        return elements.toString(); // Return the list as a string
    }

    private static class AlwaysTruePredicate<T> implements Predicate<T> {  //le classi statiche non usano nulla della outerclass sono delle specie di costanti
        
        @Override
        public boolean test(T elem) {
            return true;
        }
    }

    class Inner implements Iterator<T> {

        private int index;

        Inner(){
            this.index=0;
        }
        
            @Override
            public boolean hasNext() {
                //continua a scorrere indici finche il filtro non e corretto e finche la lunghezza lo permette
                while(index<arrayofT.length && !filter.test(arrayofT[index])){
                    index++;
                }

                return index<arrayofT.length;
            }
        
            @Override
            public T next(){
                if(hasNext()){
                    return arrayofT[index++];
                }
                else{
                    throw new NoSuchElementException();
                }
            }
            
            
        }

    }

