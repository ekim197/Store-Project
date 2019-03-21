package IO;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import ADT.*;
import Modules.Product;

public class ProductIO {
	
	private BST productlist; 
	private String filename; 
	private Scanner scanner; 

	
	public ProductIO(String fname)
	{
		filename = fname; 
		productlist = new BST();
		scanner = new Scanner(System.in);
	}

	public ProductIO(String fname, BST list)
	{
		filename = fname; 
		productlist = list;
		scanner = new Scanner(System.in);
	}

	/********
	 * 
	 * @return a completed hash. 
	 */
	public BST readfile()
	{
		boolean readable = false;
		boolean doneLoadingGraph = false;
		BufferedReader buff;
		FileReader filereader;

		try {
			filereader = new FileReader(filename);
			buff = new BufferedReader(filereader);
			String line;

			
			while (readable) {
				line = buff.readLine();
				if (line == null) // finished reading
				{
					readable = false;
					break;
				}
				
				
				String[] vertices = line.split(",");
				productlist.insert(new Product(Double.parseDouble(vertices[0]), vertices[1], 
						vertices[2], Double.parseDouble(vertices[3])));
			}
			buff.close();
		} catch (IOException e) {
			System.out.println("readfile(): Problem reading file. " + e.toString());
		}
		return productlist; 
	}
	
	/********
	* overwrite the entire file. 
	 * @throws IOException 
	*/
	public void rewritefile() throws IOException
	{
		boolean isinvalid = true;           
		FileWriter output = new FileWriter(filename);   
		PrintWriter filewriter = new PrintWriter(output); 
		
		filewriter.write(productlist.toString()); // @TODO change the format of tostring. 
			
		try {
			output.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
