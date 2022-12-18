package com.example.optimization;

import java.util.ArrayList;

public class Implementation implements Iimplementation {
        ImplementationHelper implementationHelper = new ImplementationHelper();

        public ArrayList<ArrayList<Double>> firstMethod(ArrayList<ArrayList<Double>> eq, int pre) { // Gauss

            ArrayList<ArrayList<Double>> total_ans = new ArrayList<ArrayList<Double>>();
            int test;
            ArrayList<Double> ans_x=new ArrayList<>();

            ArrayList<Double> temp=new ArrayList<>();
            temp.add(1.0);

            for (int i=0;i< eq.get(0).size()-1;i++) {

                ans_x.add(0.0);
            }


            for (int i=0;i< eq.get(0).size();i++) {


                eq=implementationHelper.pivoting(eq,i);
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
            test=implementationHelper.test_solution(eq);
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
                    ans = implementationHelper.set_precision(ans, pre);
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

        public ArrayList<ArrayList<Double>> secondMethod(ArrayList<ArrayList<Double>>eq ,int pre) { // GaussJordan
            ArrayList<ArrayList<Double>> total_ans = new ArrayList<ArrayList<Double>>();
            ArrayList<Double> ans_x=new ArrayList<>();
            ArrayList<Double> temp=new ArrayList<>();
            int test;
            temp.add(1.0);
            for (int i=0;i< eq.get(0).size()-1;i++) {

                ans_x.add(0.0);
            }
            for (int i=0;i< eq.get(0).size();i++) {
                eq=implementationHelper.pivoting(eq,i);
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
            test=implementationHelper.test_solution(eq);
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

                ans=implementationHelper.set_precision(ans,pre);
                if (Math.abs(ans) < 1e-12) {
                    ans = 0.0;
                }
                ans_x.set(j, ans);
            }
            total_ans.add(temp);
            total_ans.add(ans_x);
            return total_ans;
        }
}
