/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tetrispaul4;

import java.awt.HeadlessException;
import javax.swing.JOptionPane;

/**
 *
 * @author paul.kline
 */
public class SpecificAskNNConditions extends javax.swing.JFrame {

    private ControllerObject myGACO = null;

    /**
     * Creates new form SpecificAskNNConditions
     */
    public SpecificAskNNConditions() {
        man();
        initComponents();

    }

    public SpecificAskNNConditions(ControllerObject mygaco) {
        this();
        myGACO = mygaco;
        //default the initial conditions.
      //  chkbEmptySpace.setSelected(true);
     //   chkbfrequency.setSelected(true);
        myGACO.setCareIfHoleIsMadeByPlacement(chkbEmptySpace.isSelected());
        myGACO.setCareIfTopIsnotFrequent(chkbfrequency.isSelected());

        myGACO.setTakeFirstPositive(chkbTakeFirstPos.isSelected());

        PercentHigherActionPerformedHelper();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblPrompt = new javax.swing.JLabel();
        chkbfrequency = new javax.swing.JCheckBox();
        chkbEmptySpace = new javax.swing.JCheckBox();
        chkbAlwaysAsk = new javax.swing.JCheckBox();
        btnSave = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        chkbTakeFirstPos = new javax.swing.JCheckBox();
        chkbPercentHigher = new javax.swing.JCheckBox();
        txtPercentHigher = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();

        lblPrompt.setText("Ask the NN for help if...");

        chkbfrequency.setSelected(true);
        chkbfrequency.setText("Top choice of GA is NOT (or ties for) the most prevelant option in the top 5");
        chkbfrequency.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkbfrequencyActionPerformed(evt);
            }
        });

        chkbEmptySpace.setText("An ugly enpty space results from the GA's placement");
        chkbEmptySpace.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkbEmptySpaceActionPerformed(evt);
            }
        });

        chkbAlwaysAsk.setText("ALWAYS ASK");
        chkbAlwaysAsk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkbAlwaysAskActionPerformed(evt);
            }
        });

        btnSave.setText("Save");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        chkbTakeFirstPos.setSelected(true);
        chkbTakeFirstPos.setText("Take First Positive");
        chkbTakeFirstPos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkbTakeFirstPosActionPerformed(evt);
            }
        });

        chkbPercentHigher.setSelected(true);
        chkbPercentHigher.setText("Ignore NN IF top choice is this % higher");
        chkbPercentHigher.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkbPercentHigherActionPerformed(evt);
            }
        });

        txtPercentHigher.setText("2");

        jLabel1.setText("than the next choice.");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblPrompt)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(56, 56, 56)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(chkbPercentHigher)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtPercentHigher, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel1))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(chkbTakeFirstPos)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnSave))
                                .addComponent(chkbEmptySpace, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(chkbfrequency, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(chkbAlwaysAsk, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 383, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblPrompt)
                .addGap(18, 18, 18)
                .addComponent(chkbfrequency)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(chkbEmptySpace)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(chkbAlwaysAsk)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnSave)
                        .addContainerGap(171, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(chkbPercentHigher)
                            .addComponent(txtPercentHigher, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))
                        .addGap(18, 18, 18)
                        .addComponent(chkbTakeFirstPos))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void chkbAlwaysAskActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkbAlwaysAskActionPerformed
        boolean bool = chkbAlwaysAsk.isSelected();
        chkbEmptySpace.setEnabled(!bool);
        chkbfrequency.setEnabled(!bool);

        if (bool) {
            myGACO.setCareIfTopIsnotFrequent(true);
            myGACO.setCareIfHoleIsMadeByPlacement(true);
        } else {
            myGACO.setCareIfTopIsnotFrequent(chkbfrequency.isSelected());
            myGACO.setCareIfHoleIsMadeByPlacement(chkbEmptySpace.isSelected());
        }
    }//GEN-LAST:event_chkbAlwaysAskActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        //dummy save, already saved it. :D just hide the form.

        this.setVisible(false);
    }//GEN-LAST:event_btnSaveActionPerformed

    private void chkbfrequencyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkbfrequencyActionPerformed
        myGACO.setCareIfTopIsnotFrequent(chkbfrequency.isSelected());
    }//GEN-LAST:event_chkbfrequencyActionPerformed

    private void chkbEmptySpaceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkbEmptySpaceActionPerformed
        myGACO.setCareIfHoleIsMadeByPlacement(chkbEmptySpace.isSelected());
    }//GEN-LAST:event_chkbEmptySpaceActionPerformed

    private void chkbTakeFirstPosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkbTakeFirstPosActionPerformed
        myGACO.setTakeFirstPositive(chkbTakeFirstPos.isSelected());
    }//GEN-LAST:event_chkbTakeFirstPosActionPerformed

    private void chkbPercentHigherActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkbPercentHigherActionPerformed

        PercentHigherActionPerformedHelper();
    }//GEN-LAST:event_chkbPercentHigherActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void man() {
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
            java.util.logging.Logger.getLogger(SpecificAskNNConditions.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SpecificAskNNConditions.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SpecificAskNNConditions.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SpecificAskNNConditions.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSave;
    private javax.swing.JCheckBox chkbAlwaysAsk;
    private javax.swing.JCheckBox chkbEmptySpace;
    private javax.swing.JCheckBox chkbPercentHigher;
    private javax.swing.JCheckBox chkbTakeFirstPos;
    private javax.swing.JCheckBox chkbfrequency;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblPrompt;
    private javax.swing.JTextField txtPercentHigher;
    // End of variables declaration//GEN-END:variables

    public void PercentHigherActionPerformedHelper() throws HeadlessException {
        float input;
        try {
           input = Float.parseFloat(txtPercentHigher.getText());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: Improper Percentage entered.");
            input= Float.NaN;
        }


        myGACO.setPercentHigherRequirement(input);
    }
}