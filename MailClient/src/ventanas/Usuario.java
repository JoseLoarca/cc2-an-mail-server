/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ventanas;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Harim
 */
public class Usuario extends javax.swing.JFrame {

    public DataInputStream flujoDatosEntrada;
    public DataOutputStream flujoDatosSalida;
    public String userId;
    public String reqStr;
    
    public void setInfo(DataInputStream input, DataOutputStream output, String id) {
        flujoDatosEntrada = input;
        flujoDatosSalida = output;
        userId = id;
    }
    
    /**
     * Creates new form Usuario
     */
    public Usuario() {
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

        ServerInput = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        AddContactBtn = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        UserInput = new javax.swing.JTextField();
        fondo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        ServerInput.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        ServerInput.setText("yimail");
        getContentPane().add(ServerInput, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 220, 430, 40));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/man (2).png"))); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 60, -1, -1));

        jLabel4.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel4.setForeground(java.awt.Color.white);
        jLabel4.setText("Agregar Nuevo Contacto");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, -1, -1));

        AddContactBtn.setBackground(java.awt.SystemColor.textHighlight);
        AddContactBtn.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        AddContactBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/people.png"))); // NOI18N
        AddContactBtn.setText("Agregar Contacto");
        AddContactBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddContactBtnActionPerformed(evt);
            }
        });
        getContentPane().add(AddContactBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 330, 180, 40));

        jLabel5.setBackground(new java.awt.Color(255, 255, 51));
        jLabel5.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel5.setForeground(java.awt.Color.white);
        jLabel5.setText("Ingrese usuario:");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 100, -1, -1));

        jLabel6.setBackground(new java.awt.Color(255, 255, 51));
        jLabel6.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel6.setForeground(java.awt.Color.white);
        jLabel6.setText("Ingrese servidor:");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 190, -1, -1));

        UserInput.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        UserInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UserInputActionPerformed(evt);
            }
        });
        getContentPane().add(UserInput, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 130, 430, 40));

        fondo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/background.png"))); // NOI18N
        getContentPane().add(fondo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 780, 410));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void UserInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UserInputActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_UserInputActionPerformed

    private boolean validFields() {
        if (UserInput.getText().isEmpty() || ServerInput.getText().isEmpty() ||
                UserInput.getText().trim().equals("") || ServerInput.getText().trim().equals("")) {
            return false;
        }
        
        return true;
    }
    
    private void AddContactBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddContactBtnActionPerformed
        // TODO add your handling code here:
        String contact = UserInput.getText();
        String server = ServerInput.getText();
        
        if ( ! validFields()) {
            JOptionPane.showMessageDialog(null, "Por favor complete ambos campos.", "Error", JOptionPane.ERROR_MESSAGE);
        } else {      
            String contactStr = contact + "@" + server;
            reqStr = ("NEWCONT " + contactStr);

            try {
                flujoDatosSalida.writeUTF(reqStr);

                String response = flujoDatosEntrada.readUTF();

                System.out.println("respuesta: " + response);

                String[] splitResponse = response.split(" ");

                switch(splitResponse[2]) {
                    case "OK":
                        Contactos contacts = new Contactos();
                        contacts.setInfo(flujoDatosEntrada, flujoDatosSalida, "4");
                        contacts.setVisible(true);
                        this.setVisible(false);
                        break;
                    case "NEWCONT":
                        switch(splitResponse[4]) {
                            case "109":
                                JOptionPane.showMessageDialog(null, "No se ha encontrado el contacto.", "Error", JOptionPane.ERROR_MESSAGE);
                                break;
                            case "110":
                                JOptionPane.showMessageDialog(null, "No se ha encontrado el servidor.", "Error", JOptionPane.ERROR_MESSAGE);
                                break;
                            default:
                                JOptionPane.showMessageDialog(null, "Ha ocurrido un error desconocido, intente nuevamente.", "Error", JOptionPane.ERROR_MESSAGE);
                                break;
                        }
                        break;
                    default:
                        JOptionPane.showMessageDialog(null, "Ha ocurrido un error desconocido, intente nuevamente.", "Error", JOptionPane.ERROR_MESSAGE);
                        break;
                }
            } catch (IOException ex) {
                System.out.println("No se puedo crear la conexion");
            }
        }  
    }//GEN-LAST:event_AddContactBtnActionPerformed

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
            java.util.logging.Logger.getLogger(Usuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Usuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Usuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Usuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Usuario().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AddContactBtn;
    private javax.swing.JTextField ServerInput;
    private javax.swing.JTextField UserInput;
    private javax.swing.JLabel fondo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    // End of variables declaration//GEN-END:variables
}
