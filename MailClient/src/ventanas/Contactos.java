/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ventanas;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Harim
 */
public class Contactos extends javax.swing.JFrame {
    
    public DataInputStream flujoDatosEntrada;
    public DataOutputStream flujoDatosSalida;
    public String userId;
    public String reqStr;

    /**
     * Persiste el id del usuario y los metodos para enviar y recibir datos por medio de una conexion previamente realizada
     *
     * @param DataInputStream input
     * @param DataOutputStream output
     * @param String id
     *
     * @return void
     */
    public void setInfo(DataInputStream input, DataOutputStream output, String id) {
        flujoDatosEntrada = input;
        flujoDatosSalida = output;
        userId = id;
        Boolean aux = false;
        String recibo, contact, server, ultimo;
        
        try {
            String reqStr = ("CLIST " + userId);
            flujoDatosSalida.writeUTF(reqStr);
            modelo = new DefaultTableModel();
            modelo.addColumn("Contacto");
            modelo.addColumn("Servidor");
            ContactsTable.setModel(modelo);
             while (aux == false){
                recibo = flujoDatosEntrada.readUTF();     
                
                String[] parts = recibo.split(" ");
               
                switch (parts[2]) {
                    case "OK":
                        String respContact = parts[4];
                        String[] partsResp = respContact.split("@");
                        contact = partsResp[0];
                        server = partsResp[1];
                        modelo.addRow(new Object[]{contact, server});
                        int posicion = recibo.length() - 1;
                        ultimo = recibo.substring(posicion);
                        if(ultimo.equals("*")){
                            aux = true;
                        }
                        break;
                    case "CLIST":
                        modelo.addRow(new Object[]{"Sin datos.", " "});
                        aux = true;
                        break;
                    default:
                        modelo.addRow(new Object[]{"Sin datos.", " "});
                        aux = true;
                        break;
                }
            }
            
        } catch (Exception e) {
            System.out.println("Error ocurrido: " + e);
        }
    }

    /**
     * Creates new form Contactos
     */
    public Contactos() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        ContactsTable = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        fondo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        ContactsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Contacto", "Servidor"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(ContactsTable);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, 730, 430));

        jLabel2.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel2.setForeground(java.awt.Color.white);
        jLabel2.setText("Listado de Contactos:");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, -1, -1));

        jButton1.setBackground(javax.swing.UIManager.getDefaults().getColor("textHighlight"));
        jButton1.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jButton1.setText("Agregar nuevo contacto");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 380, 170, 30));

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/new.png"))); // NOI18N
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 250, -1, -1));

        fondo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/background.png"))); // NOI18N
        fondo.setToolTipText("");
        getContentPane().add(fondo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Abre menu nuevo contacto
     *
     * @param evt
     */
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        Usuario menu = new Usuario();
        menu.setInfo(flujoDatosEntrada, flujoDatosSalida, userId);
        menu.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private static DefaultTableModel modelo;
    
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
            java.util.logging.Logger.getLogger(Contactos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Contactos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Contactos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Contactos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Contactos().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable ContactsTable;
    private javax.swing.JLabel fondo;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
