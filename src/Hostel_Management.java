import net.proteanit.sql.DbUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Hostel_Management {
    private JPanel Main;
    private JTextField txtName;
    private JTextField txtFee;
    private JTextField txtMobile;
    private JButton saveButton;
    private JTable table1;
    private JButton updateButton;
    private JButton deleteButton;
    private JButton searchButton;
    private JScrollPane table_1;
    private JTextField txtid;

    public static void main(String[] args) {
        JFrame frame = new JFrame("management");
        frame.setContentPane(new Hostel_Management().Main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    Connection con;
    PreparedStatement pst;

    public void connect()
    {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/management", "root","Sanu@2002");
            System.out.println("Successes");
        }
        catch (ClassNotFoundException ex)
        {
            ex.printStackTrace();

        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
    }


    void table_load()
    {
        try
        {
            pst = con.prepareStatement("select * from register");
            ResultSet rs = pst.executeQuery();
            table1.setModel(DbUtils.resultSetToTableModel(rs));
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }



    public Hostel_Management() {
        connect();
        table_load();
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String studentName, studentFee, Mobile;
                studentName = txtName.getText();
                studentFee = txtFee.getText();
                Mobile = txtMobile.getText();
                try {
                    pst = con.prepareStatement("insert into register(studentName,studentFee,Mobile)values(?,?,?)");
                    pst.setString(1, studentName);
                    pst.setString(2, studentFee);
                    pst.setString(3, Mobile);
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Record Addedddd!!!!!");
                    table_load();
                    txtName.setText("");
                    txtFee.setText("");
                    txtMobile.setText("");
                    txtName.requestFocus();
                }

                catch (SQLException e1)
                {
                    e1.printStackTrace();
                }



            }
        });
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    String sid = txtid.getText();

                    pst = con.prepareStatement("select studentName,studentFee,Mobile from register where id = ?");
                    pst.setString(1, sid);
                    ResultSet rs = pst.executeQuery();

                    if(rs.next()==true)
                    {
                        String studentName = rs.getString(1);
                        String studentFee = rs.getString(2);
                        String Mobile = rs.getString(3);

                        txtName.setText(studentName);
                        txtFee.setText(studentFee);
                        txtMobile.setText(Mobile);

                    }
                    else
                    {
                        txtName.setText("");
                        txtFee.setText("");
                        txtMobile.setText("");
                        JOptionPane.showMessageDialog(null,"Invalid Student id");

                    }
                }
                catch (SQLException ex)
                {
                    ex.printStackTrace();
                }
            }

        });


        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String sid,studentName,studentFee,Mobile;
                studentName = txtName.getText();
                studentFee = txtFee.getText();
                Mobile = txtMobile.getText();
                sid = txtid.getText();

                try {
                    pst = con.prepareStatement("update register set studentName = ?,studentFee = ?,Mobile = ? where id = ?");
                    pst.setString(1, studentName);
                    pst.setString(2, studentFee);
                    pst.setString(3, Mobile);
                    pst.setString(4, sid);

                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Record Updateee!!!!!");
                    table_load();
                    txtName.setText("");
                    txtFee.setText("");
                    txtMobile.setText("");
                    txtName.requestFocus();
                }

                catch (SQLException e1)
                {
                    e1.printStackTrace();
                }
            }
        });


        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String sid;
                sid = txtid.getText();

                try {
                    pst = con.prepareStatement("delete from register where id = ?");

                    pst.setString(1, sid);

                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Record Deleteeeeee!!!!!");
                    table_load();
                    txtName.setText("");
                    txtFee.setText("");
                    txtMobile.setText("");
                    txtName.requestFocus();
                }

                catch (SQLException e1)
                {

                    e1.printStackTrace();
                }
            }
        });
    }
}
