package onlinevotingsystem;

import javax.swing.*;

import java.awt.event.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class votingsystem extends Frame implements ActionListener {
	private static final long serialVersionUID = 1L;
	JFrame f; 
	JLabel l1,l2,l3; 
	static JTextField tf1;
	static JTextField tf2;
	static JTextField tf3; 
	JButton b;
	private static int bjpvotes=0;
	private static int aapvotes=0;
	private static int congressvotes=0;
	private static boolean hasvoted=false;
	private static String Party;
	
	votingsystem() {
		f=new JFrame("Voter Details");
		l1=new JLabel("Full Name");
		l1.setBounds(53, 35, 100, 30);
		tf1=new JTextField();
		tf1.setBounds(50, 55, 200, 30);
		l2=new JLabel("City");
		l2.setBounds(53, 85, 100, 30);
		tf2=new JTextField();
		tf2.setBounds(50, 105, 200, 30);
		l3=new JLabel("Age");
		l3.setBounds(53, 135, 100, 30);
		tf3=new JTextField();
		tf3.setBounds(50, 155, 50, 30);
		b=new JButton("Submit");
		b.setBounds(50, 200, 100, 50);
		b.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        String Name = tf1.getText();
		        String City = tf2.getText();
		        String Age = tf3.getText().trim();
		        if (Name.isEmpty() || City.isEmpty() || Age.isEmpty()) {
		            JOptionPane.showMessageDialog(b, "Please fill in all the details first.");
		            return;
		        }
		        int a = Integer.parseInt(Age);
		        if (a >= 18) {
		            JOptionPane.showMessageDialog(b, "Success");
		            
		            try {
						votingform();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
		        } else {
		            JOptionPane.showMessageDialog(b, "You are not eligible to vote");
		        }
		    }
		});

		f.add(l1);f.add(tf1);f.add(l2);f.add(tf2);f.add(l3);f.add(tf3);
		f.add(b);
		f.setSize(400,400);
		f.setLayout(null);
		f.setVisible(true);
		connectToDatabase();
	}

	public static void votingform() throws SQLException {
	
		JFrame voting=new JFrame("Vote for your Party");
		JLabel l4=new JLabel("Press the Party button you want to vote.");
		l4.setBounds(50, 50, 300, 50);
		JButton b1=new JButton("BJP");
		b1.setBounds(50, 100, 50, 50);
		JButton b2=new JButton("AAP");
		b2.setBounds(50, 150, 50, 50);
		JButton b3=new JButton("CONGRESS");
		b3.setBounds(50, 200, 100, 50);
		b1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!hasvoted) {
					bjpvotes++;
					System.out.println(bjpvotes);
					hasvoted=true;
					Party="BJP";
					JOptionPane.showMessageDialog(voting, "Thank you for voting! Happy Voting!");
                    voting.dispose();
                    Result();
				}
				else {
					JOptionPane.showMessageDialog(voting, "You have already voted!");
					}
			}
		});
		b2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!hasvoted) {
					aapvotes++;
					System.out.println(aapvotes);
					hasvoted=true;
					Party="AAP";
					JOptionPane.showMessageDialog(voting, "Thank you for voting! Happy Voting!");
                    voting.dispose();
                    Result();
				}
				else {
					JOptionPane.showMessageDialog(voting, "You have already voted!");
					}
			}
		});
		b3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!hasvoted) {
					congressvotes++;
					System.out.println(congressvotes);
					hasvoted=true;
					Party="CONGRESS";
					JOptionPane.showMessageDialog(voting, "Thank you for voting! Happy Voting!");
                    voting.dispose();
                    Result();
				}
				else {
					JOptionPane.showMessageDialog(voting, "You have already voted!");
					}
			}
		});
		voting.add(l4);voting.add(b1);voting.add(b2);voting.add(b3);
		voting.setSize(400,400);
		voting.setLayout(null);
		voting.setVisible(true);
		
	}
	public static void Result() {
		try {
			Connection connection=connectToDatabase();
			connection.setAutoCommit(false);
			   if (Party != null && !Party.isEmpty() ) { 
		            String query = "INSERT INTO voterdetails(Name, City, Age, Party) VALUES(?, ?, ?, ?)";
		            PreparedStatement statement = connection.prepareStatement(query);
		            statement.setString(1, tf1.getText());
		            statement.setString(2, tf2.getText());
		            statement.setString(3, tf3.getText());
		            statement.setString(4, Party);
		          

		            statement.executeUpdate();
		            

		            statement.close();
		            
		            
		        }
			   connection.commit();
			   connection.close();
		} catch(SQLException ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(tf1, "Failed to store voting details in the database");
		}
	}
	private static Connection connectToDatabase() {
		Connection conn=null;
		try {
			conn=(Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/voterdata", "root", "");
			if(conn!=null) {
				System.out.println("connection successful to database");
			}
		}
		catch(Exception e) {
			System.out.println("not connected to database");
		}
		return conn;
	}
	
	public static void main(String[] args) {
		new votingsystem();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}


