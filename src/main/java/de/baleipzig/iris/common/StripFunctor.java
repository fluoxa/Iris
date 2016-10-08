package de.baleipzig.iris.common;

import java.util.Vector;
import java.util.function.Consumer;

public class StripFunctor<T> extends Thread {

    private int start = 0;
    private int end = 0;
    private Vector<Vector<T>> array = null;
    private Consumer<T> func = null;

    public StripFunctor(int start, int end, Vector<Vector<T>> array, Consumer<T> func ){
        this.start = start;
        this.end = end;
        this.func = func;
        this.array = array;
    }

    @Override
    public void run(){

        if(array == null || func == null || start > end)
            return;

        if(!isRect(array))
            return;

        int sizeY = array.size();
        int sizeX = array.elementAt(0).size();

        if(start > sizeX || end > sizeX)
            return;

        for( int i = start; i < end; ++i )
            for( int j = 0; j < sizeY; ++j ) {
                T obj = array.elementAt(j).elementAt(i);
                if (obj != null)
                    func.accept(obj);
            }
    }

    private boolean isRect(Vector<Vector<T>> array) {

        if (array.size() == 0)
            return false;

        int width = array.elementAt(0).size();

        for (Vector<T> row : array)
            if (row.size() != width)
                return false;

        return true;
    }
}