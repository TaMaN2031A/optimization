package com.example.optimization;

public class AbstractFactory {
    public Iimplementation get(String phase)
    {
        if(phase.equals("phase one"))
            return new Implementation(); // phase one
        /*
        else
            return new Implemenation2(); phase 2
         */
        return null;
    }
}
