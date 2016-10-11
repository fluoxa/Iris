package de.baleipzig.iris.common;

import lombok.Data;

@Data
public class Dimension {

    //region -- member --

    private int x;
    private int y;

    //endregion

    //region -- constructor --

    public Dimension() {
        x = 0;
        y = 0;
    }

    public Dimension(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Dimension(Dimension dim) {
        this.x = dim.getX();
        this.y = dim.getY();
    }

    //endregion

    //region -- methods --

    public int getDegreesOfFreedom() {
        return x*y;
    }

    //endregion
}