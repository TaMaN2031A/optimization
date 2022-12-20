package com.example.optimization;

import java.util.ArrayList;
import java.util.Arrays;

public class Implementation implements Iimplementation {
    ImplementationHelper implementationHelper = new ImplementationHelper();

    public ArrayList<ArrayList<Double>> firstMethod(ArrayList<ArrayList<Double>> eq, int pre)
    { // Gauss

        ArrayList<ArrayList<Double>> total_ans = new ArrayList<ArrayList<Double>>();
        int test;
        ArrayList<Double> ans_x=new ArrayList<>();

        ArrayList<Double> temp=new ArrayList<>();
        temp.add(1.0);

        for (int i=0;i< eq.get(0).size()-1;i++)
        {
            ans_x.add(0.0);
        }
        for (int i=0;i< eq.size();i++) {
            for (int j = 0; j < eq.get(0).size() ; j++) {

                eq.get(i).set(j, implementationHelper.set_precision(eq.get(i).get(j),pre));

            }

        }
        for (int i=0;i< eq.size();i++) {

            eq=implementationHelper.pivoting(eq,i);
            if(Math.abs(eq.get(i).get(i))<1e-10) {
                continue;
            }
            for (int j = i+1; j < eq.size(); j++) {

                //forward elem
                double factor = eq.get(j).get(i) / eq.get(i).get(i);

                for (int l = i;l< eq.get(0).size();l++){
                    if(Math.abs(eq.get(j).get(l)-factor*eq.get(i).get(l))<1e-12){
                        eq.get(j).set(l,0.0);

                    }
                    else{ eq.get(j).set(l,implementationHelper.set_precision(eq.get(j).get(l)-factor*eq.get(i).get(l),pre));}

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
        for (int i=0;i< eq.get(0).size()-1;i++)
        {
            ans_x.add(0.0);
        }
        for (int i=0;i< eq.size();i++) {
            for (int j = 0; j < eq.get(0).size() ; j++) {
                eq.get(i).set(j, implementationHelper.set_precision(eq.get(i).get(j),pre));
            }
        }
        for (int i=0;i< eq.size();i++) {
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
                    else{ eq.get(j).set(l,implementationHelper.set_precision(eq.get(j).get(l)-factor*eq.get(i).get(l),pre));}
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
            if(Math.abs(eq.get(i).get(i))<1e-12) {
                continue;
            }
            for (int j = i-1; j >=0; j--) {
                double factor=eq.get(j).get(i)/eq.get(i).get(i);
                for (int l=eq.get(0).size()-1;l>=i;l--){                                              //backward_sub
                    if(Math.abs(eq.get(j).get(l)-factor*eq.get(i).get(l))<1e-10){
                        eq.get(j).set(l,0.0);
                    }
                    else{ eq.get(j).set(l,implementationHelper.set_precision(eq.get(j).get(l)-factor*eq.get(i).get(l),pre));}


                }
            }
        }
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
    public ArrayList <Double> thirdMethod (ArrayList<ArrayList<Double>> in, String requiredForm, int precision, boolean scaling) {
        double[][] A = new double[in.size()][in.size()];
        double[] b = new double[in.size()];
        for(int i = 0; i < in.size(); i++)
        {
            for(int j = 0; j < in.size(); j++)
            {
                A[i][j] = in.get(i).get(j);
            }
        }
        for(int i = 0; i < in.size(); i++)
        {
            b[i] = in.get(i).get(in.get(0).size()-1);
        }
        ArrayList <Double> Solution = new ArrayList<>();
        System.out.println(Arrays.deepToString(A));
        System.out.println(Arrays.toString(b));

        boolean ok;
        int n = A.length, free = 0;
        int[] pos;
        boolean infinity = false;
        if(("Downlittle Form").equals(requiredForm)) {
            double[][] L = new double[n][n];
            implementationHelper.setDiagonal(L, n);
            double[] SF = new double[n];
            pos = new int[n];
            if(scaling) implementationHelper.getScalingFactors(A, pos, SF, n);
            else {
                implementationHelper.setArray(SF, n);
                implementationHelper.setPos(pos, n);
            }
            ok = implementationHelper.decompose_LU(A, L, n, pos, SF, precision);

            if (!ok) {
                Solution.add(1.0);
                return Solution;
            }
            ok = implementationHelper.forward_sub(L, b, pos, n, precision);
            if (!ok) {
                Solution.add(1.0);
                return Solution;
            }
            int s = implementationHelper.isSolvable(A, b, n);
            if(s != 0) {
                s--;
                if (s < n) {
                    infinity = true;
                    free = n - s;
                }
                for(int i = n-1; i > n-1-free; i--) {
                    b[pos[i]] = Math.round(Math.random())%23;
                }
                ok = implementationHelper.backward_sub(A, b, pos, n, precision, free);
                if (!ok) {
                    Solution.add(1.0);
                    return Solution;
                }
                Solution = implementationHelper.setSolution(b, pos);
            } else {
                return new ArrayList<>();
            }
        } else if (("Crout Form").equals(requiredForm)) {
            double[][] L = new double[n][n];
            double[][] U = new double[n][n];
            ok = implementationHelper.crout_decompose(A, L, U, n, precision);
            if (!ok) {
                Solution.add(1.0);
                return Solution;
            }
            ok = implementationHelper.forward_sub(L, b, n, precision);
            if (!ok) {
                Solution.add(1.0);
                return Solution;
            }
            ok = implementationHelper.backward_sub(U, b, n, precision);
            if (!ok) {
                Solution.add(1.0);
                return Solution;
            }
            Solution = implementationHelper.setSolution(b);
        } else if (("Cholesky Form").equals(requiredForm)) {
            if (implementationHelper.isSymmetric(A, n)) {
                double[][] L = new double[n][n];
                ok = implementationHelper.cholesky_decompose(A, L, n, precision);
                if (!ok) {
                    Solution.add(1.0);
                    return Solution;
                }
                implementationHelper.printArray(L);
                ok = implementationHelper.forward_sub(L, b, n, precision);
                if (!ok) {
                    Solution.add(1.0);
                    return Solution;
                }
                ok = implementationHelper.backward_sub(L, b, n, precision);
                if (!ok) {
                    Solution.add(1.0);
                    return Solution;
                }
                Solution = implementationHelper.setSolution(b);
            } else {
                Solution.add(1.0);
                return Solution;
            }
        } else {
            Solution.add(0.0);
            return Solution;
        }
        if(infinity) {
            Solution.add((double)free);
        }
        return Solution;
    }
    //  Giving : -- initial guess + number of iterations --
    public ArrayList<Double> JacobiI(double [][] aug ,  double [] oldX , int nIterations , int precision)
    {
        double A [][] = new double[aug.length][aug.length];
        double b[] = new double[aug.length];
        for(int i=0 ; i< aug.length ; i++)
        {
            for(int j=0 ; j< aug.length ; j++)
            {
                A[i][j] = implementationHelper.set_precision(aug[i][j] , precision);
            }
            b[i] = implementationHelper.set_precision(aug[i][aug.length] , precision);
        }
        // check if there are 0 in the main diagonal
        for(int i=0 ; i<b.length ; i++)
        {
            if (A[i][i] == 0)
            {
                System.out.println("The system can't be solved using this method");
                ArrayList <Double> out = new ArrayList<>();
                out.add(-1.0);
                return out;
            }
        }
        int rows = aug.length;
        double x [][] = new double[rows][2];
        x[0][1] = 0;                                                  // # of iterations taken
        double ea = 1;
        while(nIterations > 0)
        {
            System.out.println("iteration" + (x[0][1] + 1));
            if(x[0][1] > 0)
            {
                for(int i = 0;  i < rows ;  i++)
                {
                    oldX[i] = implementationHelper.set_precision(x[i][0] , precision);                  // make x = old one before going into the iterations "jacobi"
                }
            }

            //Each iteration takes O(n2) time
            for(int i = 0;  i < rows ;  i++)
            {
                double subValue = 0.0;
                for(int j = 0 ;  j < rows ;  j++)           // to get the subtracted value from b
                {
                    if(i != j)          // not a main diagonal element
                    {
                        subValue = implementationHelper.set_precision(subValue , precision)  + implementationHelper.set_precision(A[i][j] * oldX[j] , precision);
                    }
                }
                //System.out.println("subValue: " + subValue);
                x[i][0] = implementationHelper.set_precision((( b[i] - subValue ) / A[i][i]) , precision);            // calculate the Xs
                System.out.println("x"+(i+1)+":   " + x[i][0]);
            }
            nIterations--;
            if(x[0][1] > 0)
            {
                if(x[0][0] != 0)
                    ea = Math.abs((x[0][0]  - oldX[0])/x[0][0]);                       // getting the error
                for(int i = 1;  i < rows ;  i++)
                {
                    if(x[i][0] != 0)
                    {
                        if(ea < Math.abs(((x[i][0] - oldX[i])/x[i][0])))
                        {
                            ea = Math.abs(((x[i][0] - oldX[i])/x[i][0]));
                        }
                    }
                }
                System.out.println("the relative approximate error: " + ea);
            }
            x[0][1] ++;
            x[1][1] = ea;
        }
        ArrayList <Double> out = new ArrayList<>();
        for (int i=0 ; i<rows ; i++)
            out.add(x[i][0]);
        out.add(x[0][1]);
        out.add(x[1][1]);
        return  out;
    }


    //  Giving : -- initial guess + error tolerance --
    public ArrayList<Double> Jacobi(double [][] aug ,  double [] oldX  , double es , int precision)
    {
        int maxIterations = 100;
        double A [][] = new double[aug.length][aug.length];
        double b[] = new double[aug.length];
        for(int i=0 ; i< aug.length ; i++)
        {
            for(int j=0 ; j< aug.length ; j++)
            {
                A[i][j] = implementationHelper.set_precision(aug[i][j] , precision);
            }
            b[i] = implementationHelper.set_precision(aug[i][aug.length] , precision);
        }

        // check if there are 0 in the main diagonal
        for(int i=0 ; i<b.length ; i++)
        {
            if (A[i][i] == 0)
            {
                System.out.println("The system can't be solved using this method");
                ArrayList <Double> out = new ArrayList<>();
                out.add(-1.0);
                return out;
            }
        }

        int rows = aug.length;
        double x [][] = new double[rows][2];
        x[0][1] = 0;                                                  // # of iterations taken
        double ea = es+1;                                         // to make sure it is greater than es
        while((ea >= es ) &&  (maxIterations > 0))
        {
            System.out.println("iteration" + (x[0][1] + 1));
            if(x[0][1] > 0)
            {
                for(int i = 0;  i < rows ;  i++)
                {
                    oldX[i] = implementationHelper.set_precision(x[i][0] , precision);                   // make x = old one before going into the iterations "jacobi"
                }
            }

            //Each iteration takes O(n2) time
            for(int i = 0;  i < rows ;  i++)
            {
                double subValue = 0.0;
                for(int j = 0 ;  j < rows ;  j++)           // to get the subtracted value from b
                {
                    if(i != j)          // not a main diagonal element
                    {
                        subValue = implementationHelper.set_precision(subValue , precision)  + implementationHelper.set_precision(A[i][j] * oldX[j] , precision);
                    }
                }
                //System.out.println("subValue: " + subValue);
                x[i][0] = implementationHelper.set_precision((( b[i] - subValue ) / A[i][i]) , precision);            // calculate the Xs
                System.out.println("x"+(i+1)+":   " + x[i][0]);
            }
            maxIterations--;
            if(x[0][1] > 0)
            {
                if(x[0][0] != 0)
                    ea = Math.abs((x[0][0]  - oldX[0])/x[0][0]);                       // getting the error
                for(int i = 1;  i < rows ;  i++)
                {
                    if(x[i][0] != 0)
                    {
                        if(ea < Math.abs(((x[i][0] - oldX[i])/x[i][0])))
                        {
                            ea = Math.abs(((x[i][0] - oldX[i])/x[i][0]));
                        }
                    }
                }
                System.out.println("the relative approximate error: " + ea);
            }
            x[0][1] ++;
            x[1][1] = ea;
        }
        ArrayList <Double> out = new ArrayList<>();
        for (int i=0 ; i<rows ; i++)
            out.add(x[i][0]);
        out.add(x[0][1]);
        out.add(x[1][1]);
        return  out ;
    }
}
