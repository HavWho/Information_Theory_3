package com.company;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    //функция быстрого возведения в степень
    //Пусть m=(mkmk−1...m1m0)2 — двоичное представление степени n
    //Тогда n=mk⋅2k+mk−1⋅2k−1+...+m1⋅2+m0, где mk=1,mi∈{0,1} и xn=x^((...((mk⋅2+mk−1)⋅2+mk−2)⋅2+...)⋅2+m1)⋅2+m0
    public static int fastPower(int val1, int val2, int n) {

        BigInteger bigVal1 = BigInteger.valueOf(val1);
        BigInteger bigVal2 = BigInteger.valueOf(val2);
        BigInteger toReturn = BigInteger.ONE;

        while (!bigVal2.equals(BigInteger.ZERO)){
            while (bigVal2.mod(BigInteger.valueOf(2)).equals(BigInteger.ZERO)){
                bigVal2 = bigVal2.divide(BigInteger.TWO);
                bigVal1 = bigVal1.multiply(bigVal1).mod(BigInteger.valueOf(n));
            }
            bigVal2 = bigVal2.add(BigInteger.valueOf(-1));
            toReturn = toReturn.multiply(bigVal1).mod(BigInteger.valueOf(n));
        }

        String resultStr = toReturn.toString();

        return Integer.parseInt(resultStr);
    }

    //расширенный алгоритм Евклида для вычисления НОД
    //умножение чисел всегда быстрее, чем разложение результата на простые множители
    public static int[] GCDex(int a, int b) {
        int aTemp = a;
        int bTemp = b;
        int s1 = 1, s2 = 0;
        int t1 = 0, t2 = 1;
        int[] toReturn = new int[3];
        int quotient;

        while (bTemp != 0){
            quotient = aTemp / bTemp;
            int temp = aTemp % bTemp;
            aTemp = bTemp;
            bTemp = temp;
            int tempS = s1 - s2 * quotient;
            s1 = s2;
            s2 = tempS;
            int tempR = t1 - t2 * quotient;
            t1 = t2;
            t2 = tempR;
        }

        toReturn[0] = aTemp;
        toReturn[1] = s1;
        toReturn[2] = t1;

        return toReturn;
    }


    public static int fi(int p, int q) {
        return (p - 1) * (q - 1);
    }

    public static int getHashCode(String text, int n){
        int h = 100;

        for (int i = 0; i < text.length(); i++)
            h = (h + (int)text.charAt(i)) * (h + (int)text.charAt(i)) % n;

        return h;
    }

    public static void main(String[] args) {
        Scanner scannerString = new Scanner(System.in);
        Scanner scannerInt = new Scanner(System.in);

        System.out.print("Input you string to encrypt: ");

        String toEncrypt = scannerString.nextLine();

        System.out.print("Input p: ");
        int p = scannerInt.nextInt();

        System.out.print("Input q: ");
        int q = scannerInt.nextInt();

        System.out.print("Input e: ");
        int e = scannerInt.nextInt();

        int firstHash = getHashCode(toEncrypt, p * q);

        int fiN = fi(p, q);

        //contains d-exponent value
        int[] secretKey = GCDex(fiN, e);
        int d = secretKey[2];
        if (d < 0){
            d += fiN;
        }

        int signature = fastPower(firstHash, d, p * q);
        System.out.println("Hash: " + firstHash + "\n" + "Signature: " + signature);

        System.out.print("Input your new text: ");
        String newText = scannerString.nextLine();

        System.out.print("Input new signature: ");
        int newSignature = scannerInt.nextInt();

        int newHash = getHashCode(newText, p * q);
        if (newHash == fastPower(newSignature, e, p * q))
            System.out.println("Success!");
        else
            System.out.println("Incorrect input!");

    }
}
