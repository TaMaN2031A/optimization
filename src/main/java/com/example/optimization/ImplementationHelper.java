package com.example.optimization;

import java.util.ArrayList;

public class ImplementationHelper {
    public int test_solution(ArrayList<ArrayList<Double>> eq ){
        int rank_Agu= eq.size(), rank= eq.size(),test=1;
        for (int i=0;i< eq.size();i++) {
            test=1;
            for (int j = 0; j < eq.get(0).size()-1; j++) {
                if(Math.abs(eq.get(i).get(j))>1e-10){
                    test=0;
                }


            }
            if (test==1){
                if(rank==1){
                    rank=1;
                }
                else {
                    rank--;
                }
            }
        }
        for (int i=0;i< eq.size();i++) {
            test=1;
            for (int j = 0; j < eq.get(0).size(); j++) {
                if(Math.abs(eq.get(i).get(j))>1e-10){
                    test=0;
                }


            }
            if (test==1){
                if(rank_Agu==1){
                    rank_Agu=1;
                }
                else {
                    rank_Agu--;
                }
            }
        }


        if(rank<rank_Agu){
            //System.out.println("inconsistent solution");
            return -3;
        }
        if(rank==rank_Agu&&rank_Agu!=(eq.get(0).size()-1)){
            System.out.println("infinite solution:there are "+Math.abs((eq.get(0).size()-1)-rank)+" all free variable\none solution when free variable =0 is:");
            //to know number of free variable
            return Math.abs((eq.get(0).size()-1)-rank);
        }
        return -1;
    }
    private double pow(double x, int p) {
        if(p == 0) {
            return 1;
        } else if (p%2 == 1) {
            return x*pow(x, p-1);
        } else {
            double t = pow(x, p/2);
            return t*t;
        }
    }

    public double round(double x, int p) {
        return Math.round(x*pow(10, p))/pow(10, p);
    }

    public double set_precision(double x, int pre) {
        boolean neg = false;
        if(x < 0) {
            x *= -1;
            neg = true;
        }
        double t = 1;
        int digits = 0;
        while (t <= x) {
            digits++;
            t *= 10;
        }
        if(digits <= pre) {
            int dif = pre - digits;
            x = round(x, dif);
        } else {
            int dif = digits - pre;
            x = Math.round(x/pow(10, dif));
        }
        if(neg) x *= -1;
        return x;
    }
    public ArrayList<ArrayList<Double>> pivoting(ArrayList<ArrayList<Double>> eq ,int column){

        ArrayList<Double> max=new ArrayList<>();
        ArrayList<Double> temp=new ArrayList<>();
        ArrayList<ArrayList<Double>>new_eq = new ArrayList<ArrayList<Double>>();
        double max_number=eq.get(column).get(column);
        max=eq.get(column);
        int j=column;

        for (int i=column+1; i<eq.size();i++){
            if(Math.abs(max_number)<Math.abs(eq.get(i).get(column))){
                max_number=eq.get(i).get(column);
                max=eq.get(i);
                j=i;
            }
        }

        temp= (ArrayList<Double>) eq.get(column).clone();
        for (int i=0; i<eq.size();i++){
            if(i==column){new_eq.add(max);}
            else if(j==i){new_eq.add(temp);}
            else{
                new_eq.add(eq.get(i));
            }
        }
        return new_eq;
    }

}
