package com.example.optimization;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;
import java.util.Vector;
import java.util.function.UnaryOperator;
public class Solver {
    public ArrayList<ArrayList<Double>> gaussElimination(ArrayList<ArrayList<Double>> eq, int pre) {

        ArrayList<ArrayList<Double>> total_ans = new ArrayList<ArrayList<Double>>();
        int test;
        ArrayList<Double> ans_x=new ArrayList<>();

        ArrayList<Double> temp=new ArrayList<>();
        temp.add(1.0);

        for (int i=0;i< eq.get(0).size()-1;i++) {

            ans_x.add(0.0);
        }


        for (int i=0;i< eq.get(0).size();i++) {


            eq=pivoting(eq,i);
            if(Math.abs(eq.get(i).get(i))<1e-10) {
                continue;
            }
            for (int j = i+1; j < eq.size(); j++) {

                //forward elem
                double factor = eq.get(j).get(i) / eq.get(i).get(i);

                for (int l=i;l< eq.get(0).size();l++){
                    if(Math.abs(eq.get(j).get(l)-factor*eq.get(i).get(l))<1e-12){
                        eq.get(j).set(l,0.0);

                    }
                    else{ eq.get(j).set(l,eq.get(j).get(l)-factor*eq.get(i).get(l));}

                }
            }
        }




        test=test_solution(eq);

        if(test==-3){
            temp.clear();
            temp.add(3.0);
            total_ans.add(temp);
            return total_ans;
        }
        else if(test!=-1){
            temp.clear();
            temp.add(2.0);
            temp.add(test+0.0);
        }

        double ans;
        int k=1;
        for (int i=eq.get(0).size()-2;i>=0;i=i-1) {
            double total=0;
            for (int j = i+1; j <eq.get(0).size()-1 ; j++) {
                total+=eq.get(i).get(j)*ans_x.get(j);
            }
            if(Math.abs(eq.get(i).get(i))<1e-12){
                ans=0.0;
            }
            else {
                ans = (eq.get(i).get(i + k) - total) / eq.get(i).get(i);
                ans = set_precision(ans, pre);
            }
            if(Math.abs(ans)<1e-12){
                ans=0.0;
            }

            ans_x.set(i,ans);
            k++;


        }
        total_ans.add(temp);
        total_ans.add(ans_x);

        return total_ans;
    }
    public ArrayList<ArrayList<Double>> gaussJordanelimination(ArrayList<ArrayList<Double>>eq ,int pre) {

        ArrayList<ArrayList<Double>> total_ans = new ArrayList<ArrayList<Double>>();
        ArrayList<Double> ans_x=new ArrayList<>();
        ArrayList<Double> temp=new ArrayList<>();
        int test;
        temp.add(1.0);
        for (int i=0;i< eq.get(0).size()-1;i++) {

            ans_x.add(0.0);
        }

        for (int i=0;i< eq.get(0).size();i++) {


            eq=pivoting(eq,i);
            if(Math.abs(eq.get(i).get(i))<1e-12) {
                continue;
            }
            for (int j = i+1; j < eq.size(); j++) {


                double factor=eq.get(j).get(i)/eq.get(i).get(i);

                for (int l=i;l< eq.get(0).size();l++){                                              //forward_sub
                    if(Math.abs(eq.get(j).get(l)-factor*eq.get(i).get(l))<1e-12){
                        eq.get(j).set(l,0.0);

                    }
                    else{ eq.get(j).set(l,eq.get(j).get(l)-factor*eq.get(i).get(l));}

                }
            }
        }

        test=test_solution(eq);
        if(test==-3){
            temp.clear();
            temp.add(3.0);
            total_ans.add(temp);
            return total_ans;
        }
        else if(test!=-1){
            temp.clear();
            temp.add(2.0);
            temp.add(test+0.0);

        }
        for (int i= eq.get(0).size()-2;i>=0;i--) {


            for (int j = i-1; j >=0; j--) {

                double factor=eq.get(j).get(i)/eq.get(i).get(i);

                for (int l=eq.get(0).size()-1;l>=i;l--){                                              //backward_sub
                    if(Math.abs(eq.get(j).get(l)-factor*eq.get(i).get(l))<1e-10){
                        eq.get(j).set(l,0.0);

                    }
                    else {
                        eq.get(j).set(l,eq.get(j).get(l)-factor*eq.get(i).get(l));
                    }

                }
            }
        }
        System.out.println(eq);

        for (int j = eq.get(0).size()-2; j>=0 ; j--) {
            double ans;
            if(Math.abs(eq.get(j).get(j))<1e-12){
                ans=0.0;
            }
            else{ ans = eq.get(j).get(eq.get(0).size()-1) / eq.get(j).get(j);}

            ans=set_precision(ans,pre);
            if (Math.abs(ans) < 1e-12) {
                ans = 0.0;
            }
            ans_x.set(j, ans);
        }

        // System.out.println(ans_x);


        total_ans.add(temp);
        total_ans.add(ans_x);

        return total_ans;
    }

    private int test_solution(ArrayList<ArrayList<Double>> eq ){
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

    private double round(double x, int p) {
        return Math.round(x*pow(10, p))/pow(10, p);
    }

    private double set_precision(double x, int pre) {
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
    private ArrayList<ArrayList<Double>> pivoting(ArrayList<ArrayList<Double>> eq ,int column){

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
