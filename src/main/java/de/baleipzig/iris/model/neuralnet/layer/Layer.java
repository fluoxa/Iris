package de.baleipzig.iris.model.neuralnet.layer;

import de.baleipzig.iris.common.Dimension;
import de.baleipzig.iris.common.StripFunctor;
import de.baleipzig.iris.model.neuralnet.node.INode;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class Layer implements ILayer {

    //region -- member --

    private static ExecutorService executorService;
    private static int numberOfThreads = 0;
    private Vector<Vector<INode>> layer;

    //endregion

    //region -- constructor --

    static {

        numberOfThreads = 2*Runtime.getRuntime().availableProcessors();
        executorService = Executors.newFixedThreadPool(numberOfThreads);
    }

    public Layer() {

        layer = new Vector<> ();
        layer.add(new Vector<>());
        layer.elementAt(0).add(null);
    }

    //endregion

    //region -- methods --

    public Dimension getDim() {

        return layer.size() == 0 || (layer.size() == 1 && layer.elementAt(0).size() == 0) ?
                        new Dimension(0,0) :
                        new Dimension(layer.elementAt(0).size(), layer.size());
    }

    public void clear() {

        Dimension dim = this.getDim();

        layer.clear();

        for(int i = 0; i < dim.getY(); i++){

            Vector<INode> row = new Vector<>(dim.getX());

            for(int j = 0; j < dim.getX(); j++)
                row.add(null);

            layer.add(row);
        }
    }

    public void resize(Dimension dim) {

        Vector<Vector<INode>> tmp = new Vector<>(dim.getY());
        Dimension crtDim = this.getDim();

        for(int i = 0; i < dim.getY(); i++){

            Vector<INode> row = new Vector<>(dim.getX());

            for(int j = 0; j < dim.getX(); j++){

                if(i < crtDim.getY() && j < crtDim.getX())
                    row.add(layer.elementAt(i).elementAt(j));
                else
                    row.add(null);
            }

            tmp.add(row);
        }

        layer = tmp;
    }

    public void addNode(INode nodeCandidate) {

        Dimension dim = this.getDim();
        for(int i = 0; i < dim.getY(); i++)
            for(int j = 0; j < dim.getX(); j++)
                if(layer.elementAt(i).elementAt(j) == null){
                    layer.elementAt(i).setElementAt(nodeCandidate, j);
                    return;
                }
    }

    public void removeNode(INode node) {

        Dimension dim = this.getDim();
        for(int i = 0; i < dim.getY(); i++)
            for(int j = 0; j < dim.getX(); j++)
                if(layer.elementAt(i).elementAt(j) == node)
                    layer.elementAt(i).setElementAt(null, j);
    }

    public INode getNode(int x, int y) {
        return layer.elementAt(y).elementAt(x);
    }

    public void applyToLayerNodes(BiConsumer<INode, Object[]> func, Object[] params) {

        int layerSizeX = this.getDim().getX();
        int layerSizeY = this.getDim().getY();

        if(layerSizeX * layerSizeY == 0)
            return;

        int maxThreadNumber = numberOfThreads;

        if( maxThreadNumber == 0 )
            maxThreadNumber = 1;

        if(maxThreadNumber > layerSizeX)
            maxThreadNumber = layerSizeX;

        Collection<StripFunctor<INode>> callables = new ArrayList<>(maxThreadNumber);

        int stripSize = layerSizeX / maxThreadNumber;

        for(int threadId = 0; threadId < maxThreadNumber; threadId++) {
            int start = threadId * stripSize;
            int end = threadId == maxThreadNumber-1 ? layerSizeX : (threadId + 1) * stripSize;
            callables.add(new StripFunctor<>(start, end, layer, func, params));
        }

        try {
             executorService.invokeAll(callables);
        }
        catch(InterruptedException ex){
            System.err.println("Thread aborted:\n" + ex.getMessage());
        }
    }

    public void applyToLayerNodes(Consumer<INode> func) {

        BiConsumer<INode, Object[]> biFunction = (x, y) -> func.accept(x);
        this.applyToLayerNodes(biFunction, null);
    }

    //endregion
}