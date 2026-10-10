package petadoption;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class SystemSettings extends JFrame {
    private JTable settingsTable; private DefaultTableModel model;
    public SystemSettings(){
        Theme.apply();setTitle("System Settings - Pet Adoption Platform");setSize(1150,720);setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);setLocationRelativeTo(null);setResizable(false);
        JPanel root=new JPanel(new BorderLayout());root.setBackground(Theme.BG);JPanel side=Theme.sidebar("System Settings",()->{dispose();new AdminDashboard();},()->new UserManagement(),()->new PetListingManagement(),null,()->new Analytics());
        JPanel content=new JPanel(new BorderLayout());content.setBackground(Theme.BG);content.add(Theme.pageHeader("System Settings","Control platform configuration and registration options","CONFIGURATION"),BorderLayout.NORTH);
        JPanel body=new JPanel(new BorderLayout(0,16));body.setBackground(Theme.BG);body.setBorder(BorderFactory.createEmptyBorder(20,32,25,32));
        JPanel card=new JPanel(new BorderLayout());card.setBackground(Color.WHITE);card.setBorder(BorderFactory.createLineBorder(new Color(225,229,235)));
        JPanel head=new JPanel(new BorderLayout());head.setOpaque(false);head.setBorder(BorderFactory.createEmptyBorder(18,20,12,20));JLabel t=new JLabel("Platform Configuration");t.setFont(new Font("Arial",Font.BOLD,20));t.setForeground(Theme.TEXT);JLabel s=new JLabel("Select a setting and update its value");s.setFont(new Font("Arial",Font.PLAIN,12));s.setForeground(Theme.MUTED);head.add(t,BorderLayout.WEST);head.add(s,BorderLayout.EAST);card.add(head,BorderLayout.NORTH);
        model=new DefaultTableModel(new String[]{"ID","Setting Name","Setting Value"},0){public boolean isCellEditable(int r,int c){return false;}};settingsTable=new JTable(model);Theme.styleTable(settingsTable);JScrollPane scroll=new JScrollPane(settingsTable);scroll.setBorder(BorderFactory.createEmptyBorder(0,10,10,10));card.add(scroll,BorderLayout.CENTER);
        JPanel actions=new JPanel(new FlowLayout(FlowLayout.RIGHT,12,12));actions.setBackground(Color.WHITE);JButton update=Theme.button("✎  Update Setting",Theme.PURPLE,155),refresh=Theme.button("↻  Refresh",Theme.ORANGE,130);actions.add(update);actions.add(refresh);card.add(actions,BorderLayout.SOUTH);body.add(card,BorderLayout.CENTER);content.add(body,BorderLayout.CENTER);root.add(side,BorderLayout.WEST);root.add(content,BorderLayout.CENTER);add(root);
        update.addActionListener(e->updateSetting());refresh.addActionListener(e->loadSettings());loadSettings();setVisible(true);
    }
    private void loadSettings(){model.setRowCount(0);try(Connection con=DatabaseConnection.getConnection();PreparedStatement pst=con.prepareStatement("SELECT setting_id, setting_name, setting_value FROM system_settings")){ResultSet rs=pst.executeQuery();while(rs.next())model.addRow(new Object[]{rs.getInt(1),rs.getString(2),rs.getString(3)});}catch(Exception e){JOptionPane.showMessageDialog(this,"Unable to load settings.\n\n"+e.getMessage(),"Database Error",JOptionPane.ERROR_MESSAGE);}}
    private void updateSetting(){int row=settingsTable.getSelectedRow();if(row==-1){JOptionPane.showMessageDialog(this,"Please select a setting first.","Select Setting",JOptionPane.WARNING_MESSAGE);return;}int id=(int)model.getValueAt(row,0);String name=model.getValueAt(row,1).toString();String current=model.getValueAt(row,2).toString();JTextField field=new JTextField(current);field.setFont(new Font("Arial",Font.PLAIN,14));field.setPreferredSize(new Dimension(360,40));int choice=JOptionPane.showConfirmDialog(this,new Object[]{"New value for  \""+name+"\":",Box.createVerticalStrut(5),field},"Update Setting",JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE);if(choice!=JOptionPane.OK_OPTION||field.getText().trim().isEmpty())return;try(Connection con=DatabaseConnection.getConnection();PreparedStatement pst=con.prepareStatement("UPDATE system_settings SET setting_value=? WHERE setting_id=?")){pst.setString(1,field.getText().trim());pst.setInt(2,id);pst.executeUpdate();loadSettings();JOptionPane.showMessageDialog(this,"Setting updated successfully.","Success",JOptionPane.INFORMATION_MESSAGE);}catch(Exception e){JOptionPane.showMessageDialog(this,"Operation failed.\n\n"+e.getMessage(),"Database Error",JOptionPane.ERROR_MESSAGE);}}
    public static void main(String[] args){new SystemSettings();}
}
