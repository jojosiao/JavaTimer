package javatimer;

import javatimer.JavaTimer;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;


public class JavaTimerGUI extends JFrame {
	JLabel text1 = new JLabel("Date/Time Today:");
	Date currentDate = new Date();
	SimpleDateFormat currentDateFormat = new SimpleDateFormat("MM-dd-yyyy H:mm:ss a");
	String currentDateString = currentDateFormat.format(currentDate);
	JLabel text2 = new JLabel(currentDateString);
	JButton button1 = new JButton("Punch-In Time");
	FlowLayout timerGUILayout = new FlowLayout();
	
	public JavaTimerGUI(String name){
		super(name);
	}
	
	public void setupTimerGUI(final Container pane)
	{
		final JPanel timerGUIPanel = new JPanel();
		timerGUIPanel.setLayout(timerGUILayout);
        timerGUILayout.setAlignment(FlowLayout.TRAILING);

		
		timerGUIPanel.add(text1);
		timerGUIPanel.add(text2);
		timerGUIPanel.add(button1);
		
		timerGUIPanel.setComponentOrientation(
                ComponentOrientation.LEFT_TO_RIGHT);
		
		pane.add(timerGUIPanel,BorderLayout.CENTER);

		button1.addActionListener( new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				JavaTimer jTimer = new JavaTimer();	
				String filelogpath = "C:\\vagrant\\JavaClasses\\FileLog";
				jTimer.CheckLastFileTimeLog(filelogpath);
				JOptionPane.showMessageDialog(pane,"New Punch-In time done!");
			}

		});

		ActionListener DateTimeListener = new ActionListener()
 		{
 			public void actionPerformed(ActionEvent ae ) 
 			{
 				Date currentDate = new Date();
				SimpleDateFormat currentDateFormat = new SimpleDateFormat("MM-dd-yyyy H:mm:ss a");
				String currentDateString = currentDateFormat.format(currentDate);
				text2.setText(currentDateString);	
 			}
 			
 		};
 		Timer DateTimeTimer = new Timer(1000,DateTimeListener );
 		DateTimeTimer.start();
	}


	
	public static void main(String[] args)
	{
		
		JavaTimerGUI jtGUI = new JavaTimerGUI("JavaTimerGUI");
		jtGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Set up the content pane.
        jtGUI.setupTimerGUI(jtGUI.getContentPane());
        //Display the window.
        jtGUI.pack();
        jtGUI.setVisible(true);

	}
	
	
}