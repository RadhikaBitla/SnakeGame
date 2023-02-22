import com.mysql.jdbc.Driver;

import javax.swing.*;
import java.sql.*;
import java.sql.DriverManager;
public class Scores
{
    public void ConnectionAndInsertion(String name,int value)
    {
        PreparedStatement pre;
        Connection con;
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con= DriverManager.getConnection("jdbc:mysql://localhost/snakegame","root","Radhika@123");

            pre=con.prepareStatement("insert into PlayerScore(Name,Score) values(?,?)");
            pre.setString(1,name);
            pre.setString(2, String.valueOf(value));

            pre.executeUpdate();
            JOptionPane.showMessageDialog(null,"Thanks, Please play again... :)");
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public int HighestScore()
    {
        PreparedStatement pre;
        Connection con;
        ResultSet rs;
        int result=0;
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con= DriverManager.getConnection("jdbc:mysql://localhost/snakegame","root","Radhika@123");

            pre=con.prepareStatement("select MAX(Score) from PlayerScore");
            rs= pre.executeQuery();
            if(rs.next()!=false)
            {
                result=rs.getInt(1);
            }

        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }
}
