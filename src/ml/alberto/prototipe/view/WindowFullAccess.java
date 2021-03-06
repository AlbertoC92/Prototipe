/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ml.alberto.prototipe.view;

//import pruebaorden.UserDetails;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import ml.alberto.prototipe.control.DataBaseControl;
import ml.alberto.prototipe.control.DataBaseOutUsersControl;
import ml.alberto.prototipe.control.TableModel;
import ml.alberto.prototipe.model.User;

/**
 *
 * @author alber
 */
public class WindowFullAccess extends javax.swing.JFrame implements ActionListener {

    private ArrayList<User> users;
    private UserDetails details;
    private User userDetails;
    private DataBaseControl db; 
    private RegisterPanel reg;

    /**
     * Creates new form WindowFullAccess
     */
    public WindowFullAccess() {
        initComponents();
        initButons();
        addMouseListener();
        users = new ArrayList<User>();
        details = new UserDetails();
        userDetails = new User();
        db = new DataBaseControl();   
        chargeUsers();
       
       
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnAdd = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnDBOut = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableUsers = new javax.swing.JTable();
        btnDetails = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        LogeOut = new javax.swing.JMenu();
        btnLogout = new javax.swing.JCheckBoxMenuItem();
        jMenu2 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        btnAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ml/alberto/prototipe/resources/alta.png"))); // NOI18N

        btnDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ml/alberto/prototipe/resources/baja.png"))); // NOI18N
        btnDelete.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnDelete.setIconTextGap(0);

        btnDBOut.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ml/alberto/prototipe/resources/consulta.png"))); // NOI18N

        tableUsers.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tableUsers);

        btnDetails.setText("Details");

        jLabel1.setText("People on the company:");

        LogeOut.setText("File");

        btnLogout.setSelected(true);
        btnLogout.setText("Loge out");
        LogeOut.add(btnLogout);

        jMenuBar1.add(LogeOut);

        jMenu2.setText("Options");
        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(144, 144, 144)
                .addComponent(btnAdd)
                .addGap(226, 226, 226)
                .addComponent(btnDelete)
                .addGap(310, 310, 310)
                .addComponent(btnDetails)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnDBOut)
                .addGap(258, 258, 258))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(1362, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(btnAdd)
                        .addComponent(btnDelete)
                        .addComponent(btnDBOut))
                    .addComponent(btnDetails))
                .addGap(35, 35, 35)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 628, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(WindowFullAccess.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(WindowFullAccess.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(WindowFullAccess.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(WindowFullAccess.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new WindowFullAccess().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu LogeOut;
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnDBOut;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnDetails;
    private javax.swing.JCheckBoxMenuItem btnLogout;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tableUsers;
    // End of variables declaration//GEN-END:variables

    public void addMouseListener() {
        tableUsers.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                try {

                    tableUsers = (JTable) e.getSource();
                    Point point = e.getPoint();
                    int row = tableUsers.rowAtPoint(point);
                    if (e.getClickCount() == 1) {
                        setUserDetails(new User((String) tableUsers.getValueAt(tableUsers.getSelectedRow(), 0), (String) tableUsers.getValueAt(tableUsers.getSelectedRow(), 1), (String) tableUsers.getValueAt(tableUsers.getSelectedRow(), 2),
                                (String) tableUsers.getValueAt(tableUsers.getSelectedRow(), 3), (String) tableUsers.getValueAt(tableUsers.getSelectedRow(), 4), (String) tableUsers.getValueAt(tableUsers.getSelectedRow(), 5),
                                (String) tableUsers.getValueAt(tableUsers.getSelectedRow(), 6), (String) tableUsers.getValueAt(tableUsers.getSelectedRow(), 7), (String) tableUsers.getValueAt(tableUsers.getSelectedRow(), 8)));

                    }
                } catch (ArrayIndexOutOfBoundsException aio) {
                    System.out.println("EL ARRAY ES MAYOR QUE LA PARTE SELECCIONADA");
                }
            }
        });
        
       
    }

    private void chargeUsers() {
        try {
            String[] headers = {"ID", "NAME", "LASTNAME", "EMAILML", "MLPASS", "PCPASS", "SKYPEUSER", "BITRIXUSER", "TELEPHONE"};
            String[][] cells;
           
            users = db.checkDB();
            cells = new String[users.size()][9];
            for (int i = 0; i < users.size(); i++) {
                cells[i][0] = users.get(i).getId();
                cells[i][1] = users.get(i).getName();
                cells[i][2] = users.get(i).getLastaName();
                cells[i][3] = users.get(i).getEmailML();
                cells[i][4] = "********";
                cells[i][5] = "********";
                cells[i][6] = users.get(i).getSkypeUser();
                cells[i][7] = users.get(i).getBitrixUser();
                cells[i][8] = users.get(i).getTelephone();
            }

            TableModel model = new TableModel(cells, headers);
            tableUsers.setModel(model);

        } catch (NullPointerException npe) {

        }
    }

    public void initButons() {
        btnDetails.addActionListener(this);
        btnDetails.setActionCommand("DETAILS");
        btnLogout.addActionListener(this);
        btnLogout.setActionCommand("LOG");
        btnDelete.addActionListener(this);
        btnDelete.setActionCommand("DELETE");
        btnAdd.addActionListener(this);
        btnAdd.setActionCommand("ADD");
        btnDBOut.addActionListener(this);
        btnDBOut.setActionCommand("OUT");
       

    }

    public void details() {
        details.getDetails(getUserDetails());
    }
    
   
    
   
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        switch (command) {
            case "DETAILS":
                details();
                details.setVisible(true);
                dispose();

                break;
            case "LOG":
                new RegisterPanel().setVisible(true);
                dispose();
                break;
            case "DELETE":
                db.delete(getUserDetails().getEmailML());
                JOptionPane.showMessageDialog(this,"User removed");
                new WindowFullAccess().setVisible(true);
                dispose();

                break;
            case "ADD":
                details.setVisible(true);

                break;
            case "OUT":
               
                break;
        }
    }

    public User getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(User userDetails) {
        this.userDetails = userDetails;
    }

    private static class MouseAdapterImpl extends MouseAdapter {

        public MouseAdapterImpl() {
        }
    }

}
