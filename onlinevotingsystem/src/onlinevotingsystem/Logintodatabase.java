package onlinevotingsystem;
import javax.swing.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
public class Logintodatabase extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	JFrame f; JLabel l1,l2; final JPasswordField value; JButton b;
	 //final JLabel l;
	final JTextField text;
	String username="voter";
	String password="123456";
	Logintodatabase(){
		f=new JFrame("Login to Voting Results");
		//l=new JLabel();
		value = new JPasswordField();
		//l.setBounds(20,150, 200,50); 
		value.setBounds(100,75,100,30); 
		l1=new JLabel("Username:");
		l2=new JLabel("Password:");
		l1.setBounds(20,20, 80,30); 
		l2.setBounds(20,75, 80,30);
		b=new JButton("Login");
		b.setBounds(100,120, 80,30); 
		text = new JTextField(); 
		text.setBounds(100,20, 100,30);
		b.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String u= text.getText();
				String p=new String(value.getPassword());
				if(u.equals(username) && p.equals(password)) {
					JOptionPane.showMessageDialog(null,"Login Successful");
					displayfromdatabase();
				}
				else {
					JOptionPane.showMessageDialog(null, "Login Failed");
				}
				
			}
			
		});
        f.add(value); f.add(l1);  f.add(l2); f.add(b); f.add(text); 
        //f.add(l);
        f.setSize(300,300);    
        f.setLayout(null);    
        f.setVisible(true);
        connecttodatabase();
	}
	public void displayfromdatabase() {
		JFrame F = new JFrame("Data from Database");
        JTextArea dataTextArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(dataTextArea);
        scrollPane.setBounds(20, 20, 260, 220);
       
        Connection connection = connecttodatabase();
        if (connection != null) {
            try {
                PreparedStatement statement = connection.prepareStatement("SELECT * FROM voterdetails");
                ResultSet resultSet = statement.executeQuery();
                dataTextArea.append("Name\tCity 2\tAge 3\tParty 4\n");
                
                while (resultSet.next()) {
                    String column1 = resultSet.getString("Name");
                    String column2 = resultSet.getString("City");
                    String column3 = resultSet.getString("Age");
                    String column4 = resultSet.getString("Party");
                    
                    dataTextArea.append(column1 + "\t" + column2 + "\t" + column3 + "\t" + column4 + "\n");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Failed to connect to the database");
        }
        F.add(scrollPane);
        F.setSize(300, 300);
        F.setLayout(null);
        F.setVisible(true);
    }
	private static Connection connecttodatabase() {
		Connection conn=null;
		try {
			conn=(Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/voterdata", "root", "");
			if(conn!=null) {
				System.out.println("connection successful to databse");
			}
		} catch(Exception e) {
			System.out.println("not connected to database");
		}
		return conn;
	}
	public static void main(String[] args) {
		new Logintodatabase();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
	
