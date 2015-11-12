package javatimer;

import java.io.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.Writer;
import java.io.RandomAccessFile;

import java.util.*;
import java.util.Date;

import java.text.SimpleDateFormat;
import java.text.ParseException;

public class JavaTimer 
{

	
	public Boolean GetUserChoice()
	{
		System.out.println("Punch in a Time Log? [Y/N] ");
		Scanner sc = new Scanner(System.in);
		String input = sc.next();
		if(input.equals("Y"))
			return true;
		else 
			return false;
	}


	public ArrayList<String> ReadFileLog(String filename)
	{
		ArrayList<String> fLines = new ArrayList<String>() ;
		try{
			BufferedReader fread = new BufferedReader(new FileReader(filename));
			String fLine ;
			while(( fLine = fread.readLine()) != null ) {
				fLines.add(fLine);
			}
			fread.close();
		}catch(IOException e) {
			e.printStackTrace();
		}
		return fLines;
	}


	public String[] ParseFileTimeLog(String TimeLog)
	{
		String timelog[] = new String[3];
		if(TimeLog != null && TimeLog.length()>0)
		{
			timelog = TimeLog.split("\t");
		}
		return timelog;
	}


	public void UpdateLastFileTimeLog(String completelog, String filename)
	{
		// let's open the file, read it, replace the last line, close the file.
		ArrayList<String> fLines = new ArrayList<String>() ;
		try{
			BufferedReader fread = new BufferedReader(new FileReader(filename));
			String fLine ;
			while(( fLine = fread.readLine()) != null ) 
			{
				fLines.add(fLine);
			}
			fread.close();
		}catch(IOException e) {
			e.printStackTrace();
		}
		fLines.set(fLines.size()-1,completelog);

		File fileLog = new File(filename);
		try
		{
			FileWriter fwriter = new FileWriter(fileLog, false);
			for(int i =0; i< fLines.size();i++) 
			{
				fwriter.write(fLines.get(i));

				//to avoid double CR LF
				if(i<(fLines.size()-1))
					fwriter.write("\n");

			}
			fwriter.close();	
		}catch(IOException e) {
			e.printStackTrace();
		}
		

	}

	public void NewFileTimeLog(String completelog, String filename)
	{
		File fileLog = new File(filename);
		try
		{	
			FileWriter fwriter = new FileWriter(fileLog,true);
			fwriter.write(completelog);
			fwriter.close();
		}catch(IOException e)
		{
			e.printStackTrace();
		}
	}

	public void CheckLastFileTimeLog(String filelogpath)
	{
		
	 	// let's read the file log.
		
		ArrayList<String> fileLog = new JavaTimer().ReadFileLog(filelogpath);


		SimpleDateFormat datetimelogformat = new SimpleDateFormat("MM-dd-yyyy");
		SimpleDateFormat timeformat = new SimpleDateFormat("HH:mm:ss a");
	
		Date CurrentDate = new Date();
		String CurrentDateString = datetimelogformat.format(CurrentDate);
		String CurrentTimeString = timeformat.format(CurrentDate);

		String[] NewTimeLog = new String[3];

		if(fileLog.size()==0)
		{
			//String currentdate = timeformat.format(CurrentDate);
			NewTimeLog[0] = datetimelogformat.format(CurrentDate);
			NewTimeLog[1] = CurrentTimeString;
			NewTimeLog[2] = "";
			String completelog = String.join("\t",NewTimeLog) ;

			new JavaTimer().NewFileTimeLog(completelog,filelogpath);
		}
		else if(fileLog.size()>0)
		{
			String[] LastFileTimeLog = new JavaTimer().ParseFileTimeLog(fileLog.get(fileLog.size()-1));

			if(LastFileTimeLog.length==3)
			{
				
				NewTimeLog[0] = datetimelogformat.format(CurrentDate);
				NewTimeLog[1] = CurrentTimeString;
				NewTimeLog[2] = "";
				String completelog = "\n" + String.join("\t",NewTimeLog) ;

				new JavaTimer().NewFileTimeLog(completelog,filelogpath);
			} else {
				
				try{

					Date currDate = datetimelogformat.parse(CurrentDateString);
					Date dateFromFile = datetimelogformat.parse(LastFileTimeLog[0]);

					if(dateFromFile != null) {
						if(dateFromFile.equals(currDate))
						{
							NewTimeLog[0] = LastFileTimeLog[0];
							NewTimeLog[1] = LastFileTimeLog[1];
							NewTimeLog[2] = CurrentTimeString;
							String completelog =  String.join("\t",NewTimeLog) ;
							new JavaTimer().UpdateLastFileTimeLog(completelog,filelogpath);
						}else if(dateFromFile.before(currDate))
						{
							NewTimeLog[0] = datetimelogformat.format(CurrentDate);
							NewTimeLog[1] = CurrentTimeString;
							NewTimeLog[2] = "";
							String completelog = "\n" + String.join("\t",NewTimeLog);
							new JavaTimer().NewFileTimeLog(completelog,filelogpath);
						}
						
					}
				
				}catch(ParseException e)
				{
					e.printStackTrace();
				}				
			}	

		}		


	}

	public static void main(String[] args)
	{
		Boolean choice = new JavaTimer().GetUserChoice();
		if(choice.equals(true))
		{
			// let's make a time log.
			String filelogpath = "C:\\vagrant\\Java\\com\\JojoSiao\\JavaTimer\\FileLog";
			new JavaTimer().CheckLastFileTimeLog(filelogpath);
			System.out.println("New Time Punched today.");

		}else 
		{
			// let's exit and say Goodbye!.
			System.out.println("Since you pressed N, you mean you don't want a time log.");
			System.out.println("GoodBye!");
		}


	}

}