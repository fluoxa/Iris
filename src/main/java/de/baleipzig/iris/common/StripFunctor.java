package de.baleipzig.iris.common;

import java.util.Vector;
import java.util.concurrent.Callable;
import java.util.function.BiConsumer;

public class StripFunctor<T> implements Callable<Void> {

    private int start = 0;
    private int end = 0;
    private Vector<Vector<T>> array = null;
    private Object[] params = null;
    private BiConsumer<T, Object[]> func = null;

    public StripFunctor(int start, int end, Vector<Vector<T>> array, BiConsumer<T, Object[]> func, Object[] params ){
        this.start = start;
        this.end = end;
        this.func = func;
        this.array = array;
        this.params = params;
    }

    @Override
    public Void call(){

        if(array == null || func == null || start > end)
            return null;

        if(!isRect(array))
            return null;

        int sizeY = array.size();
        int sizeX = array.elementAt(0).size();

        if(start > sizeX || end > sizeX)
            return null;

        for( int i = start; i < end; ++i ) {
            for( int j = 0; j < sizeY; ++j ) {
                T obj = array.elementAt(j).elementAt(i);
                if (obj != null)
                    func.accept(obj, params);
            }
        }

        return null;
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