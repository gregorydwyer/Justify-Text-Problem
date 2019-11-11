/*
This program takes a paragraph of input and outputs an array of left and right justified text.
*/
import java.util.Scanner;

public class JustifyText {

	public static void main(String[] args) {
		//check for command line arguments and take input if there are none
		if (args.length == 0) {
			Scanner input = new Scanner(System.in);
			String text;
			int width;
			System.out.print("Paragraph = ");
			text = input.nextLine();
			System.out.print("Page Width = ");
			width = input.nextInt();
			String[] array = alignText(text, width);
			
			//Output array
			for (int i = 0; i < array.length; i++) {
				System.out.println("Array [" + i+ "] = " + array[i]);
			}
		} 
		
		else if (args.length == 2) {
			String[] array = alignText(args[0], Integer.parseInt(args[1]));
			for (int i = 0; i < array.length; i++) {
				System.out.println("Array [" + i+ "] = " + array[i]);
			}
		} 
		
		//if too many/few arguments, let user know
		else {
			System.out.println("Incorrect formatting. Command line arguments should be in the following format: \"Input text paragraph\" \"width\"");
		}
	}

	public static String[] alignText(String text, int width) {
		//create string array without whitespace
		String[] textArray = text.split("\\s+");
		return alignText(textArray, width);
	}

	public static String[] alignText(String[] text, int width) {
		String[] alignedText = new String[text.length];
		int strlen = 0;
		int row = 0;
		int end = 0;
		for (int start = 0; start < text.length; row++) {
			do {
				if(text[end].length() > width){
					System.out.printf("\"%s\" longer than specified width. Terminating program.\n", text[end]);
					System.exit(0);
				}
				//add up the number of letters from each word
				strlen += text[end].length();
				end++;
				
				//while loop looks at (current string length + length of next word + 1 space for each word(except the last one))
				// if this is still less than or equal to the width, loop to add the next word
			} while (end < text.length  && ((strlen + text[end].length() + (end - start)) <= width));
			
			//when words + spaces get to the limit, add them to the array
			alignedText[row] = buildRow(text, start, end, width, strlen);
			
			//reset string length
			strlen = 0;
			//move starting word index
			start = end;
		}
		
		//create new array and only copy filled rows over
		String[] array = new String[row];
		System.arraycopy(alignedText, 0, array, 0, row);
		
		return array;
	}

	public static String buildRow(String[] text, int start, int end, int width, int strlen) {
		//create array for new row and populate first and last indicies with "
		char[] row = new char[width + 2];
		row[0] = '"';
		row[width + 1] = '"';
		
		//calculate number of spaces, words = the number of words that will have spaces after them
		//divide the spaces evenly and store remainder in remSpaces
		int spaces = width - strlen;
		int words = end - start - 1;
		if(words == 0){words = 1;}
		int remSpaces = spaces % (words);
		spaces = (int) spaces/words;
		
		int index = 1;
		while (index <= width) {
			char[] temp = text[start++].toCharArray();
			System.arraycopy(temp, 0, row, index, temp.length);
			index += temp.length;
			if (index < width) {
				
				//if there are remaing spaces, add one after the current word
				if(remSpaces > 0){
					row[index++] = ' ';
					remSpaces--;
				}
				
				//add in the calculated number of spaces 
				for (int i = 0; i < spaces; i++) {
					row[index++] = ' ';
				}
			}
		}
		
		return new String(row);
	}
}