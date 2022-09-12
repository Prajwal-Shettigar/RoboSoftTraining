package com.prajwal.onlyXML;

import java.util.List;
import java.util.Map;

public class BasicClass {

    List<String> listOfStrings;

    Map<Integer,String> mapOfValues;

    PrintsString printsString;


    public BasicClass(PrintsString printsString) {
        this.printsString = printsString;
    }

    public void setListOfStrings(List<String> listOfStrings) {
        this.listOfStrings = listOfStrings;
    }

    public void setMapOfValues(Map<Integer, String> mapOfValues) {
        this.mapOfValues = mapOfValues;
    }

    @Override
    public String toString() {
        return "BasicClass{" +
                "listOfStrings=" + listOfStrings +
                ", mapOfValues=" + mapOfValues +
                ", printsString=" + printsString.printMessage() +
                '}';
    }
}
