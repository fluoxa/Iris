package de.baleipzig.iris.common;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Pair<KeyType, ValueType> {

    private KeyType key;
    private ValueType value;
}
