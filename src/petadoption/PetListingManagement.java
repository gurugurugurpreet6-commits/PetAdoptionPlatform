package petadoption;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class PetListingManagement extends JFrame {
    private JTable petTable;
    private DefaultTableModel model;

    public PetListingManagement() {
        Theme.apply();
        setTitle("Pet Listing Management - Pet Adoption Platform");
        setSize(1150,720); setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); setLocationRelativeTo(null); setResizable(false);
        JPanel root=new JPanel(new BorderLayout()); root.setBackground(Theme.BG);
        JPanel side=Theme.sidebar("Pet Listings", ()->{dispose();new AdminDashboard();}, ()->new UserManagement(), null, ()->new SystemSettings(), ()->new Analytics());
        JPanel content=new JPanel(new BorderLayout()); content.setBackground(Theme.BG);
        content.add(Theme.pageHeader("Pet Listing Management","Review, approve and manage shelter pet listings","PET LISTINGS"),BorderLayout.NORTH);

        JPanel body=new JPanel(new BorderLayout(0,16)); body.setBackground(Theme.BG); body.setBorder(BorderFactory.createEmptyBorder(20,32,25,32));
        JPanel top=new JPanel(new BorderLayout()); top.setOpaque(false);
        JLabel title=new JLabel("All Pet Listings"); title.setFont(new Font("Arial",Font.BOLD,20)); title.setForeground(Theme.TEXT);
        JLabel hint=new JLabel("Select a listing to approve or reject it"); hint.setFont(new Font("Arial",Font.PLAIN,12)); hint.setForeground(Theme.MUTED);
        top.add(title,BorderLayout.WEST); top.add(hint,BorderLayout.EAST); body.add(top,BorderLayout.NORTH);

        model=new DefaultTableModel(new String[]{"ID","Pet Name","Type","Breed","Age","Gender","Status","Shelter ID"},0){public boolean isCellEditable(int r,int c){return false;}};
        petTable=new JTable(model); Theme.styleTable(petTable);
        petTable.getColumnModel().getColumn(0).setPreferredWidth(50); petTable.getColumnModel().getColumn(1).setPreferredWidth(140); petTable.getColumnModel().getColumn(6).setPreferredWidth(110);
        JScrollPane scroll=new JScrollPane(petTable); scroll.setBorder(BorderFactory.createEmptyBorder());
        JPanel tableCard=new JPanel(new BorderLayout()); tableCard.setBackground(Color.WHITE); tableCard.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(225,229,235)),BorderFactory.createEmptyBorder(10,10,10,10)));
        tableCard.add(scroll,BorderLayout.CENTER); body.add(tableCard,BorderLayout.CENTER);

        JPanel actions=new JPanel(new FlowLayout(FlowLayout.RIGHT,12,0)); actions.setOpaque(false);
        JButton approve=Theme.button("✓  Approve",Theme.GREEN,135), reject=Theme.button("✕  Reject",Theme.RED,135), refresh=Theme.button("↻  Refresh",Theme.ORANGE,135);
        actions.add(approve);actions.add(reject);actions.add(refresh); body.add(actions,BorderLayout.SOUTH);
        content.add(body,BorderLayout.CENTER); root.add(side,BorderLayout.WEST); root.add(content,BorderLayout.CENTER); add(root);
        approve.addActionListener(e->updateStatus("Approved")); reject.addActionListener(e->updateStatus("Rejected")); refresh.addActionListener(e->loadPets());
        loadPets(); setVisible(true);
    }

    private void loadPets(){ model.setRowCount(0); try(Connection con=DatabaseConnection.getConnection(); PreparedStatement pst=con.prepareStatement("SELECT pet_id, pet_name, type, breed, age, gender, status, shelter_id FROM pets")){ ResultSet rs=pst.executeQuery(); while(rs.next()) model.addRow(new Object[]{rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getInt(5),rs.getString(6),rs.getString(7),rs.getInt(8)}); }catch(Exception e){JOptionPane.showMessageDialog(this,"Unable to load pet listings.\n\n"+e.getMessage(),"Database Error",JOptionPane.ERROR_MESSAGE);}}

    private void updateStatus(String newStatus){ int row=petTable.getSelectedRow(); if(row==-1){JOptionPane.showMessageDialog(this,"Please select a pet listing first.","Select Listing",JOptionPane.WARNING_MESSAGE);return;} int id=(int)model.getValueAt(row,0); String name=model.getValueAt(row,1).toString(); int choice=JOptionPane.showConfirmDialog(this,"Set \""+name+"\" to "+newStatus+"?","Confirm Action",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE); if(choice!=JOptionPane.YES_OPTION)return; try(Connection con=DatabaseConnection.getConnection();PreparedStatement pst=con.prepareStatement("UPDATE pets SET status=? WHERE pet_id=?")){pst.setString(1,newStatus);pst.setInt(2,id);pst.executeUpdate();loadPets();JOptionPane.showMessageDialog(this,"Pet listing updated successfully.","Success",JOptionPane.INFORMATION_MESSAGE);}catch(Exception e){JOptionPane.showMessageDialog(this,"Operation failed.\n\n"+e.getMessage(),"Database Error",JOptionPane.ERROR_MESSAGE);}}
    public static void main(String[] args){new PetListingManagement();}
}
