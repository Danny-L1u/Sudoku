package Grade_11;

import java.io.*;
import java.util.Scanner;

/**
 * This program takes in an empty 4 by 4 sudoku board and fills it in. It does this by going translating the inputed board
 * into a 2D array, then it goes through every empty space and checks what numbers the empty space cannot be. If there are 3 numbers 
 * the space cannot be (or one possibility is can be) then that empty space is filled and the program continues this process until 
 * the entire board is filled.
 * 
 * @author dliu
 */
public class Sudoku_DannyLiu {

	public static void main(String[] args) throws IOException {
		Scanner in = new Scanner(System.in);

		//Input File name for input and file name for output
		System.out.println("Welcome to Danny's Sudoku Solver!");
		System.out.print("Please enter the name of the input file: ");
		String file = in.next();

		Scanner inFile = new Scanner(new File(file));
		System.out.print("Please enter the name of the file you would like your ouput to go in: ");
		file = in.next();

		//Set up the print stream between the program and the file
		FileWriter out = new FileWriter (file);
		BufferedWriter bw  = new BufferedWriter(out);
		PrintWriter reportFile = new PrintWriter(bw);
		reportFile.println("Here are the completed sudoku puzzles\n");


		//Loops through each line in the input file
		while(inFile.hasNextLine()){
			String [][] board = new String[4][4];

			//File Input
			for (int i = 0; i < 4; i++) {
				//Copy each element from one sudoku board from the input file to a 2D array
				String line[] = inFile.nextLine().split(""); 
				for (int j = 0; j < 4; j++) {
					board[i][j] = line[j];
				}
			}

			//Fill out the spaces that only have one possibility while they are available
			do {

				//While the board has empty spaces
				while (isComplete(board) == false) {
					boolean oneOption;

					//Loop through the entire array until there are no empty spaces that have a guaranteed number
					do {

						//Keeps track if there is is a space that only has a certain option
						oneOption = false;

						//Loop through the entire 2D array
						for (int i = 0; i < board.length; i++) {
							for (int j = 0; j < board.length; j++) {
								//If the selected space is empty (filled by a dash)
								if (board[i][j].equals("-")) {

									//Check and store what values the empty space cannot be
									String already[] = new String [4];
									checkRow(board, i, already);
									checkCol(board, j, already);
									int possible = 0;

									//Check the number of possibilities the empty space can be
									for (int k = 0; k < already.length; k++) {
										if (already[k] == null) {
											//Add one for every possible number the empty space can be
											possible++;
										}
									}

									//If there is only one possibility the empty space can be, assign that possibility to the space
									if (possible == 1){
										//Check what the possible number is
										for (int k = 0; k < already.length; k++) {
											if (already[k] == null) {
												//Assign the possibility to the empty space
												board[i][j] = Integer.toString(k + 1);
												break;
											}
										}

										//Make oneOption true so that loop continues
										oneOption = true;
									}
								}
							}
						}

					} while (oneOption == true);
				}

			} while (isComplete(board) == false);


			//Output the completed sudoku board in the "report.txt" file
			for (int i = 0; i < board.length; i ++) {
				for (int j = 0; j < board[i].length; j++) {
					reportFile.print(board[i][j] + " ");
				}
				reportFile.print("\n");
			}
			reportFile.print("\n");
		}

		System.out.print("Check " + file + " for your output");
		reportFile.close();
	}


	/**
	 * This method checks the row of the empty space inputed and returns what the number cannot be based on the 
	 * other numbers in the row.
	 * @param board
	 * @param x
	 * @param already
	 * @return already
	 */
	public static void checkRow (String board[][], int x, String already[]) {
		//Loop through the row
		for (int i = 0; i < board.length; i++) {
			//If the current space is occupied by a number, the empty space cannot be that number
			if (board[x][i].equals("-") == false) {
				//Add that number to the already array
				already[Integer.parseInt(board[x][i]) - 1] = board[x][i];
			}
		}
	}


	/**
	 * This method checks the column of the empty space inputed and returns what the number cannot be based on the 
	 * other numbers in the column.
	 * @param board
	 * @param y
	 * @param already
	 * @return already
	 */
	public static void checkCol (String board[][], int y, String already[]) {
		//Loop through the column
		for (int i = 0; i < board.length; i++) {
			//If the current space is occupied by a number, the empty space cannot be that number
			if (board[i][y].equals("-") == false) {
				//Add that number to the already array
				already[Integer.parseInt(board[i][y]) - 1] = board[i][y];
			}
		}
	}


	/**
	 * This method checks if the sudoku board has any empty spaces left, if it does then the board is not complete and return false,
	 * if it does not then the board is completed and return true.
	 * @param board
	 * @return true || false
	 */
	public static Boolean isComplete (String board[][]) {
		//Loop through the 2D array
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				//If there is a dash, then there is an empty space so return false.
				if (board[i][j].equals("-")){
					return false;
				}
			}
		}
		return true;
	}

}


