package de.baleipzig.iris.model.neuralnet.layer;

import de.baleipzig.iris.common.Dimension;
import de.baleipzig.iris.model.neuralnet.node.INode;

import java.util.Vector;
import java.util.function.Consumer;

public class Layer implements ILayer {

    //region -- member --

    private Vector<Vector<INode>> layer;

    //endregion

    //region -- constructor --

    public Layer() {

        layer = new Vector<> ();
        layer.add(new Vector<>());
        layer.elementAt(0).add(null);
    }

    //endregion

    //region -- methods --

    public Dimension dim() {

        return layer.size() == 0 || (layer.size() == 1 && layer.elementAt(0).size() == 0) ?
                        new Dimension(0,0) :
                        new Dimension(layer.elementAt(0).size(), layer.size());
    }

    public void clear() {

        Dimension dim = this.dim();

        for(int i = 0; i < dim.getY(); i++){

            Vector<INode> row = new Vector<>();

            for(int j = 0; j < dim.getX(); j++)
                row.add(null);

            layer.add(row);
        }
    }

    public void resize(Dimension dim) {

        Vector<Vector<INode>> tmp = new Vector<>();
        Dimension crtDim = this.dim();

        for(int i = 0; i < dim.getY(); i++){

            Vector<INode> row = new Vector<>();

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

        Dimension dim = this.dim();
        for(int i = 0; i < dim.getY(); i++)
            for(int j = 0; j < dim.getX(); j++)
                if(layer.elementAt(i).elementAt(j) == null){
                    layer.elementAt(i).setElementAt(nodeCandidate, j);
                    return;
                }
    }

    public void removeNode(INode node) {

        Dimension dim = this.dim();
        for(int i = 0; i < dim.getY(); i++)
            for(int j = 0; j < dim.getX(); j++)
                if(layer.elementAt(i).elementAt(j) == node)
                    layer.elementAt(i).setElementAt(null, j);
    }

    public INode getNode(int x, int y) {
        return layer.elementAt(y).elementAt(x);
    }

    @Override
    public void applyToLayerNodes(Consumer<INode> func) {

        int layerSizeX = this.dim().getX();
        int layerSizeY = this.dim().getY();

        if(layerSizeX * layerSizeY == 0)
            return;

        int maxThreadNumber = Runtime.getRuntime().availableProcessors()*2;

        if( maxThreadNumber == 0 )
            maxThreadNumber = 1;

        if(maxThreadNumber > layerSizeX)
            maxThreadNumber = layerSizeX;

        class ArrayParallelizer extends Thread {

            private int threadId = 0;
            private int maxThreads = 1;

            private ArrayParallelizer(int id, int threads){
                threadId = id;
                maxThreads = threads;
            }

            @Override
            public void run(){
                if( threadId > maxThreads )
                    return;

                int start = threadId * layerSizeX / maxThreads;
                int end = (threadId+1) * layerSizeX / maxThreads;
                for( int i = start; i < end; ++i )
                    for( int j = 0; j < layerSizeY; ++j ) {
                        INode obj = layer.elementAt(j).elementAt(i);
                        if (obj != null)
                            func.accept(obj);
                    }
            }
        }

        ArrayParallelizer[] threads = new ArrayParallelizer[maxThreadNumber];

        for( int threadId = 0; threadId < maxThreadNumber; threadId++ ) {
            threads[threadId] = new ArrayParallelizer(threadId, maxThreadNumber);
            threads[threadId].start();
        }

        try {
            for (int threadId = 0; threadId < maxThreadNumber; threadId++)
                threads[threadId].join();
        }
        catch(InterruptedException ex){
            System.err.println("Thread aborted:\n" + ex.getMessage());
        }
    }

    //endregion
}