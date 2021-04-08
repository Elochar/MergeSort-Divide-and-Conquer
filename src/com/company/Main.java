//This Code is written my Cole Hartnett on 4/7/2021
//For a CS 224 coding assignment number 8 with Professor Byung Lee
//The purpose of this program is to do a mergesort recursively via divide and conquer
//There will be separate sort and merge functions that will be return the number of inversions
// (when the number of the left is larger than the number on the right for any number n) and will
// be called in one main, asking the user for input of a specific file to get the input from
package com.company;
import java.util.*;
import java.io.*;
import javax.swing.*;

public class Main {
	//Sort and count will take an array of integers and the size and return the number of inversions
	public static int sort_and_count(int[] arr, int arrSize){
		//create variables for calculations
		int inv_Count=0;
		//mid will store the middle of the array
		int mid = arrSize/2;
		//for right and leftmost numbers
		int[] right = new int[arrSize - mid];
		int[] left = new int[mid];
		//escape case for recursion
		if (arrSize < 2){
			return inv_Count;
		}
		//fill in the left and right arrays
		for(int i=0; i < mid; i++){
			left[i] = arr[i];
		}
		for(int i=mid; i<arrSize; i++){
			right[i-mid] = arr[i];
		}
		//call sort recursively to fully sort
		inv_Count += sort_and_count(left, mid);
		inv_Count += sort_and_count(right, arrSize-mid);
		//call merge to "undivide" after conquered and make a temporary array for printing
		int[] temp = new int[arrSize];
		inv_Count += merge_and_count(temp, left, right, mid, arrSize - mid);
		//return the number of inversions
		//System.out.println(inv_Count);
		return inv_Count;

	}

	//merge_and_count will print one full sorted array (arr) from two (left and right) and the number of inversions
	public static int merge_and_count(int[] arr, int[] left, int[] right, int l, int r){
		//add the inversion count to return
		int inv_Count = 0;
		//make i, j, and k be references for left, right, and middle during array indexing
		int i = 0, j = 0, k = 0;
		while(i < l && j < r){
			if(left[i] <= right[j]){
				arr[k++] = left[i++];
			}
			else{
				arr[k++] = right[j++];
				//update the inversion count
				inv_Count += ((arr.length/2)-i);
			}
		}
		while(i < l){
			arr[k++] = left[i++];
		}
		while (j < r){
			arr[k++] = right[j++];
		}

		//System.out.println("flag");
		return inv_Count;
	}
	/*
	The following two functions are undocumented because they are almost identical to the other ones
	Their purpose is to return a sorted array instead of returning the number of inversions
	Trying to print the array within the two recursive functions that return the inversion count was proven
	to be too messy so I made these separate function to also solve the problem recursively and return the just one int array
	 */
	public static int[] sortArrGive(int[] arr, int arrSize){
		int[] finalArr = new int[arrSize];
		int mid = arrSize/2;
		int[] right = new int[arrSize - mid];
		int[] left = new int[mid];
		if (arrSize < 2){
			return finalArr;
		}
		for(int i=0; i < mid; i++){
			left[i] = arr[i];
		}
		for(int i=mid; i<arrSize; i++){
			right[i-mid] = arr[i];
		}
		sortArrGive(left, mid);
		sortArrGive(right, arrSize-mid);
		mergeArrGive(finalArr, left, right, mid, arrSize - mid);
		//return the fully merged array
		return finalArr;
	}
	public static int[] mergeArrGive(int[] arr, int[] left, int[] right, int l, int r){
		int i = 0, j = 0, k = 0;
		while(i < l && j < r){
			if(left[i] <= right[j]){ arr[k++] = left[i++]; }
			else{ arr[k++] = right[j++]; }
		}
		while(i < l){ arr[k++] = left[i++]; }
		while (j < r){ arr[k++] = right[j++]; }
		//return the original array now that it has been merged
		return arr;
	}

    public static void main(String[] args) {
	    System.out.println("Please enter the name of the file to read the arrays from: ");
	    Scanner in = new Scanner(System.in);
	    String filename = in.nextLine();
	    BufferedReader reader;
	    try {
			reader = new BufferedReader(new FileReader(filename));
			String line = reader.readLine();
			while (line != null){
				//create the array for calculations
				int[] temp;
				//turn the line into an array of string
				String[] strArr = line.split(" ");
				temp= new int[strArr.length];
				for(int i=0; i < strArr.length; i++){
					temp[i] = Integer.parseInt(strArr[i]);
				}
				//print the inversions and sorted array
				System.out.print("Number of Inversions are: " + sort_and_count(temp, temp.length));
				System.out.print("  The sorted array is: ");
				int[] temp2 = new int[temp.length];
				temp2 = sortArrGive(temp, temp.length);
				for(int e: temp2) {
					System.out.print(e);
					System.out.print(" ");
				}
				System.out.println();
				//read next line
				line = reader.readLine();
			}
		}catch (IOException e){
			System.out.println("ERROR file not found! Printing the stack trace for more info: ");
			e.printStackTrace();
		}
    }
}
