
package ventanas;

import Main.DB;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class servidores extends javax.swing.JFrame {
DefaultTableModel modeloLista = new DefaultTableModel();

    public void llenarTabla() {

        ArrayList<Object> columna = new ArrayList<Object>();
        columna.add("ID_SERVIDOR");
        columna.add("NOMBRE");
        columna.add("IP");
        columna.add("ESTADO");

        for (Object col : columna) {
            modeloLista.addColumn(col);
        }

        this.jTable1.setModel(modeloLista);
        ArrayList<Object[]> servers = new ArrayList<Object[]>();

        DB myDb_1 = new DB("servidorcorreo.db");
        try {
            try {
                if (!myDb_1.connect()) {
                    System.out.println("SERVER : ERROR DE CONEXION CON LA BASE DE DATOS.");
                } else {
                    String query_string_2 = "select * from servidor;";
                    myDb_1.executeQuery(query_string_2, "rsl_existe_3");
                    while (myDb_1.next("rsl_existe_3")) {
                        String id_servidor = myDb_1.getString("idservidor", "rsl_existe_3") + "";
                        String nombre_server = myDb_1.getString("nombre", "rsl_existe_3") + "";
                        String ip_server = myDb_1.getString("ip", "rsl_existe_3") + "";
                        String estado_server = myDb_1.getString("estado", "rsl_existe_3") + "";
                        Object[] persona = new Object[]{id_servidor, nombre_server, ip_server, estado_server };
                        servers.add(persona);
                    }

                    for (Object[] element : servers) {
                        modeloLista.addRow(element);
                    }

                    this.jTable1.setModel(modeloLista);
                }
            } catch (Exception e) {
                System.out.println("SERVER : Ocurrió un error al traer la información de servidores.");
                JOptionPane.showMessageDialog(null, "Ocurrió un error al traer la información de servidores.", "Error", JOptionPane.ERROR_MESSAGE);
            } finally {
                myDb_1.close();
            }
        } catch (Exception e) {

            System.out.println("SERVER : Ocurrió un error al traer la información de servidores.");
            JOptionPane.showMessageDialog(null, "Ocurrió un error al traer la información de servidores.", "Error", JOptionPane.ERROR_MESSAGE);
        }

    }

    public servidores() {
        initComponents();
        llenarTabla();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel5.setFont(new java.awt.Font("Arial", 0, 28)); // NOI18N
        jLabel5.setForeground(java.awt.Color.white);
        jLabel5.setText("Listado de servidores:");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        jTable1 = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex){
                return false;
            }
        };
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jTable1.getTableHeader().setResizingAllowed(false);
        jTable1.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(jTable1);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, 920, -1));

        jButton1.setBackground(new java.awt.Color(0, 0, 0));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/undo.png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 10, 60, 50));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/background.png"))); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        Interfaz menu = new Interfaz();
        menu.setVisible(true);
        menu.setResizable(false);
        this.setVisible(false);
    }//GEN-LAST:event_jButton1ActionPerformed


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
            java.util.logging.Logger.getLogger(servidores.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(servidores.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(servidores.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(servidores.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new servidores().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
