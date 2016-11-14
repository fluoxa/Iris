package de.baleipzig.iris.logic.worker;

public interface ICrudWorker<T, ID> {

    void save(T t);

    T load(ID id);

    T create();

    void delete(ID id);
}
