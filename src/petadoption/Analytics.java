package petadoption;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class Analytics extends JFrame {
    private JLabel usersLabel,petsLabel,applicationsLabel,adoptedLabel;
    public Analytics(){
        Theme.apply(); setTitle("Platform Analytics - Pet Adoption Platform"); setSize(1150,720); setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); setLocationRelativeTo(null); setResizable(false);
        JPanel root=new JPanel(new BorderLayout()); root.setBackground(Theme.BG);
        JPanel side=Theme.sidebar("Analytics",()->{dispose();new AdminDashboard();},()->new UserManagement(),()->new PetListingManagement(),()->new SystemSettings(),null);
        JPanel content=new JPanel(new BorderLayout()); content.setBackground(Theme.BG); content.add(Theme.pageHeader("Platform Analytics","Monitor users, pets and adoption activity","LIVE DATA"),BorderLayout.NORTH);
        JPanel body=new JPanel(new BorderLayout(0,18));body.setBackground(Theme.BG);body.setBorder(BorderFactory.createEmptyBorder(20,32,25,32));
        JPanel cards=new JPanel(new GridLayout(1,4,15,0));cards.setOpaque(false);
        usersLabel=createCard(cards,"TOTAL USERS","0","👥",Theme.BLUE); petsLabel=createCard(cards,"TOTAL PETS","0","🐾",Theme.GREEN); applicationsLabel=createCard(cards,"APPLICATIONS","0","📋",Theme.PURPLE); adoptedLabel=createCard(cards,"ADOPTED PETS","0","❤",Theme.ORANGE);
        body.add(cards,BorderLayout.NORTH);
        JPanel lower=new JPanel(new BorderLayout());lower.setBackground(Color.WHITE);lower.setBorder(BorderFactory.createLineBorder(new Color(225,229,235)));
        JPanel lh=new JPanel(new BorderLayout());lh.setOpaque(false);lh.setBorder(BorderFactory.createEmptyBorder(18,20,12,20)); JLabel lt=new JLabel("Platform Overview");lt.setFont(new Font("Arial",Font.BOLD,20));lt.setForeground(Theme.TEXT);JLabel ls=new JLabel("Current database statistics");ls.setForeground(Theme.MUTED);ls.setFont(new Font("Arial",Font.PLAIN,12));lh.add(lt,BorderLayout.WEST);lh.add(ls,BorderLayout.EAST);lower.add(lh,BorderLayout.NORTH);
        JPanel info=new JPanel(new GridLayout(2,2,15,15));info.setBackground(Color.WHITE);info.setBorder(BorderFactory.createEmptyBorder(10,20,20,20));
        info.add(infoBox("Users registered on platform","Manage accounts from User Management",Theme.BLUE)); info.add(infoBox("Pets listed by shelters","Review listings from Pet Listings",Theme.GREEN)); info.add(infoBox("Adoption requests received","Track application activity",Theme.PURPLE)); info.add(infoBox("Successful adoptions","Pets marked as Adopted",Theme.ORANGE)); lower.add(info,BorderLayout.CENTER); body.add(lower,BorderLayout.CENTER);
        JPanel bottom=new JPanel(new FlowLayout(FlowLayout.RIGHT));bottom.setOpaque(false);JButton refresh=Theme.button("↻  Refresh Analytics",Theme.BLUE,165);bottom.add(refresh);body.add(bottom,BorderLayout.SOUTH);refresh.addActionListener(e->loadAnalytics());
        content.add(body,BorderLayout.CENTER);root.add(side,BorderLayout.WEST);root.add(content,BorderLayout.CENTER);add(root);loadAnalytics();setVisible(true);
    }
    private JLabel createCard(JPanel panel,String title,String value,String icon,Color accent){JPanel c=new JPanel(new BorderLayout(12,0));c.setBackground(Color.WHITE);c.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(0,4,0,0,accent),BorderFactory.createEmptyBorder(15,16,15,16)));JLabel i=new JLabel(icon);i.setFont(new Font("Segoe UI Emoji",Font.PLAIN,28));c.add(i,BorderLayout.WEST);JPanel t=new JPanel();t.setOpaque(false);t.setLayout(new BoxLayout(t,BoxLayout.Y_AXIS));JLabel v=new JLabel(value);v.setFont(new Font("Arial",Font.BOLD,28));v.setForeground(accent);JLabel l=new JLabel(title);l.setFont(new Font("Arial",Font.BOLD,10));l.setForeground(Theme.MUTED);t.add(v);t.add(l);c.add(t,BorderLayout.CENTER);panel.add(c);return v;}
    private JPanel infoBox(String a,String b,Color c){JPanel p=new JPanel(new BorderLayout());p.setBackground(new Color(248,249,251));p.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(0,3,0,0,c),BorderFactory.createEmptyBorder(14,15,14,15)));JLabel x=new JLabel(a);x.setFont(new Font("Arial",Font.BOLD,14));x.setForeground(Theme.TEXT);JLabel y=new JLabel(b);y.setFont(new Font("Arial",Font.PLAIN,12));y.setForeground(Theme.MUTED);p.add(x,BorderLayout.NORTH);p.add(y,BorderLayout.CENTER);return p;}
    private void loadAnalytics(){try(Connection con=DatabaseConnection.getConnection();Statement st=con.createStatement()){usersLabel.setText(String.valueOf(count(st,"SELECT COUNT(*) FROM users")));petsLabel.setText(String.valueOf(count(st,"SELECT COUNT(*) FROM pets")));applicationsLabel.setText(String.valueOf(count(st,"SELECT COUNT(*) FROM adoption_requests")));adoptedLabel.setText(String.valueOf(count(st,"SELECT COUNT(*) FROM pets WHERE status='Adopted'")));}catch(Exception e){JOptionPane.showMessageDialog(this,"Unable to load analytics.\n\n"+e.getMessage(),"Database Error",JOptionPane.ERROR_MESSAGE);}}
    private int count(Statement st,String sql)throws Exception{try(ResultSet rs=st.executeQuery(sql)){return rs.next()?rs.getInt(1):0;}}
    public static void main(String[] args){new Analytics();}
}
