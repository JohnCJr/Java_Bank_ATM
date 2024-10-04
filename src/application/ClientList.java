package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;

public class ClientList {
	private ArrayList<Customer> clients = new ArrayList<Customer>();	// stores result text file information for each client
	private static int index;	// value of the index of the current Customer found in the ArrayList above
	SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");	// formats Date() that will be read
	
	// default constructor that reads a text file with client information
	// and assigns the proper values to each Customer in the clients ArrayList
	ClientList(){
		FileReader clientList = null;
		try {
			clientList = new FileReader("Clients_data.txt");
			BufferedReader bufferReader = new BufferedReader(clientList);
			int customerID = 0;	// needs to be a 5-digit ID
			int customerPIN = 0;	// needs to be a 4-digit PIN
			String customerName = "";
			String customerEmail = "";
			String customerPhone = "";
			String customerAddress = "";
			Number checkingAccountNumber = 0;
			Number checkingAccountType = 0;
			Date checkingAccountOpenDate = new Date();
			float checkingAccountBalance = 0;
			Number savingAccountNumber = 0;
			Number savingAccountType = 0;
			Date savingAccountOpenDate = new Date();
			float savingAccountBalance = 0;
			String line = "";
			StringTokenizer token;
			bufferReader.readLine();	// reads the first line that is simply the name of each piece of client data
										// makes sure that the first line that is read contains the actual client data
			line = bufferReader.readLine();
			
			
			while (line != null)
			{
				token = new StringTokenizer(line, ",");
				while (token.hasMoreTokens())
				{
					customerID = Integer.parseInt(token.nextToken());//Integer.parseInt(fileScnr.next());//fileScnr.nextLine();
					customerPIN = Integer.parseInt(token.nextToken());//Integer.valueOf(fileScnr.next());//
					customerName = token.nextToken();
					customerEmail = token.nextToken();
					customerPhone = token.nextToken();
					customerAddress = token.nextToken();
					checkingAccountNumber = Integer.parseInt(token.nextToken());
					checkingAccountType = Integer.parseInt(token.nextToken());
				    checkingAccountOpenDate = formatter.parse(token.nextToken());
					checkingAccountBalance = Float.parseFloat(token.nextToken());
					savingAccountNumber = Integer.parseInt(token.nextToken());
					savingAccountType = Integer.parseInt(token.nextToken());
					savingAccountOpenDate = formatter.parse(token.nextToken());
					savingAccountBalance = Float.parseFloat(token.nextToken());
					clients.add(new Customer(customerID, customerPIN, customerName, customerEmail, customerPhone, customerAddress, checkingAccountNumber, checkingAccountType,
							checkingAccountOpenDate, checkingAccountBalance, savingAccountNumber,
							savingAccountType, savingAccountOpenDate, savingAccountBalance));
					line = bufferReader.readLine();
				}
			}
			
			bufferReader.close();
		
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
		
	}
	
	// updates the ArrayList with the new values of a clients accounts
	public void updateClientList(ArrayList<Customer> list) throws IOException {
		FileWriter writer = new FileWriter("Clients_data.txt");
		BufferedWriter buffer = new BufferedWriter(writer);
		int length = list.size();
		String heading = "CustomerID,Customer PIN,Customer Name,Customer Email,Customer Phone,Customer Address,Checking Account "
				+ "number,Type,Open Date,Balance,Savings Account number,Type,Open Date,Balance\n";
		buffer.write(heading);
		
		for (int i = 0; i < length; i++) {
			String customerID = Integer.toString(list.get(i).getCustomerID());
			String customerPIN = Integer.toString(list.get(i).getCustomerPIN());
			String customerName = list.get(i).getCustomerName();
			String customerEmail = list.get(i).getCustomerEmail();
			String customerPhone = list.get(i).getCustomerPhone();
			String customerAddress = list.get(i).getCustomerAddress();
			String checkingAccountNumber = list.get(i).checkingAccount.getAccountNumber().toString();
			String checkingAccountType = list.get(i).checkingAccount.getAccountType().toString();
			String checkingAccountOpenDate = formatter.format(list.get(i).checkingAccount.getOpenDate());
			String checkingAccountBalance = Float.toString(list.get(i).checkingAccount.getBalance());
			String savingAccountNumber = list.get(i).savingsAccount.getAccountNumber().toString();
			String savingAccountType = list.get(i).savingsAccount.getAccountType().toString();
			String savingAccountOpenDate = formatter.format(list.get(i).savingsAccount.getOpenDate());
			String savingAccountBalance = (Float.toString(list.get(i).savingsAccount.getBalance()));
			String line = customerID + "," + customerPIN + "," + customerName + "," + customerEmail + "," + customerPhone + "," + customerAddress + "," 
			+ checkingAccountNumber + "," + checkingAccountType + "," + checkingAccountOpenDate + "," + checkingAccountBalance + "," + savingAccountNumber + "," + savingAccountType 
			+ "," + savingAccountOpenDate + "," + savingAccountBalance + "\n"; 
			buffer.write(line);
		}
		buffer.close();
		
		System.out.println("...Client list has been updated");
	}
	
	// returns the ArrayList containing the Customer objects
	public ArrayList<Customer> getClientList() {
		return clients;
	}
	
	public void setIndex(int index) {ClientList.index = index;}
	public int getIndex() {return ClientList.index;}
	
}
