/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUIs;

import NeuralThings.PaulNetworkFactory;
import NeuralThings.PaulNetworkFactory.NNCodes;
import bytefigtestering.GAControllerObject;
import bytefigtestering.Seed;
import java.util.LinkedList;
import javax.swing.JOptionPane;

/**
 *
 * @author paul.kline
 */
public class SeedSettingGUI extends javax.swing.JFrame {

    //LinkedList<String> SeedsStrings= new LinkedList<>();
    LinkedList<String> Weights = new LinkedList<>();
    LinkedList<NNCodes> NNs = new LinkedList<>();
    LinkedList<Seed> SeedsObjs = new LinkedList<>();
    private GAControllerObject myGACO=null;

    /**
     * Creates new form SeedSettingGUI
     */
    public SeedSettingGUI() {
        man();
        initComponents();
        PopulateNNList();
        PopulateWeightList();
    }

    SeedSettingGUI(GAControllerObject mygaco) {
        this();
        myGACO= mygaco;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblNNs = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        lstNNs = new javax.swing.JList();
        lblWeights = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        lstWeights = new javax.swing.JList();
        jScrollPane3 = new javax.swing.JScrollPane();
        lstSeeds = new javax.swing.JList();
        lblSeeds = new javax.swing.JLabel();
        btnRemoveSelected = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        btnPair = new javax.swing.JButton();
        btnAddCustomWeights = new javax.swing.JButton();
        btnRemoveSelectedWeights = new javax.swing.JButton();

        setTitle("Genetics--Seed Selection");

        lblNNs.setText("Neural Nets");

        lstNNs.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        lstNNs.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(lstNNs);

        lblWeights.setText("Weights");

        lstWeights.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        lstWeights.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        lstWeights.setDragEnabled(true);
        jScrollPane2.setViewportView(lstWeights);

        jScrollPane3.setViewportView(lstSeeds);

        lblSeeds.setText("Seeds");

        btnRemoveSelected.setText("Remove Selected");
        btnRemoveSelected.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveSelectedActionPerformed(evt);
            }
        });

        btnSave.setText("Save");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        btnPair.setText("Pair");
        btnPair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPairActionPerformed(evt);
            }
        });

        btnAddCustomWeights.setText("Add Custom Weight");
        btnAddCustomWeights.setEnabled(false);

        btnRemoveSelectedWeights.setText("Remove Selected Weights");
        btnRemoveSelectedWeights.setEnabled(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addComponent(lblNNs))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(30, 30, 30))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnRemoveSelectedWeights)
                            .addComponent(btnAddCustomWeights))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblWeights)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnPair)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblSeeds)
                        .addGap(121, 121, 121))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(btnSave))
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 283, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnRemoveSelected))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNNs)
                    .addComponent(lblWeights)
                    .addComponent(lblSeeds))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 283, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnSave))
                    .addComponent(btnRemoveSelected)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnPair, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnAddCustomWeights)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnRemoveSelectedWeights)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(29, 29, 29))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(22, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnPairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPairActionPerformed
        String strNN = ((NNCodes) lstNNs.getSelectedValue()).toString();
        String strWeights = (String) lstWeights.getSelectedValue();
        //SeedsStrings.add(strNN+ "~~" + strWeights);        
        // lstSeeds.setListData(SeedsStrings.toArray());
        if (strNN.equals("NONE") && strWeights.equals("NONE")) {
            JOptionPane.showMessageDialog(this, "Error: Must have weights or NN or both.");
            return;
        }
        SeedsObjs.add(new Seed((NNCodes) lstNNs.getSelectedValue(), strWeights));
        lstSeeds.setListData(SeedsObjs.toArray());
        myGACO.setSeeds(SeedsObjs);
    }//GEN-LAST:event_btnPairActionPerformed

    private void btnRemoveSelectedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveSelectedActionPerformed
        int[] selectedIndicies = lstSeeds.getSelectedIndices();
        for (int i = selectedIndicies.length - 1; i >= 0; i--) {
            //SeedsStrings.remove(selectedIndicies[i]);            
            SeedsObjs.remove(selectedIndicies[i]);
        }
        //lstSeeds.setListData(SeedsStrings.toArray());
        lstSeeds.setListData(SeedsObjs.toArray());
        myGACO.setSeeds(SeedsObjs);
    }//GEN-LAST:event_btnRemoveSelectedActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        this.setVisible(false);
    }//GEN-LAST:event_btnSaveActionPerformed

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
            java.util.logging.Logger.getLogger(SeedSettingGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SeedSettingGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SeedSettingGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SeedSettingGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */

    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddCustomWeights;
    private javax.swing.JButton btnPair;
    private javax.swing.JButton btnRemoveSelected;
    private javax.swing.JButton btnRemoveSelectedWeights;
    private javax.swing.JButton btnSave;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblNNs;
    private javax.swing.JLabel lblSeeds;
    private javax.swing.JLabel lblWeights;
    private javax.swing.JList lstNNs;
    private javax.swing.JList lstSeeds;
    private javax.swing.JList lstWeights;
    // End of variables declaration//GEN-END:variables

    private void PopulateNNList() {
        for (NNCodes nncode : NNCodes.values()) {
            NNs.add(nncode);
        }
        lstNNs.setListData(NNs.toArray());
    }

    private void PopulateWeightList() {
        String[] myarray = new String[]{
            "RANDOM, ??, ??, ??, ??, ??, ??, ??, ??",
            "11065641, -6, -12, 51, -3, 10, 37, 50, 42",
            "9142091, -6, -9, 46, 1, 11, 35, 54, 50",
            "9083268, -2, -11, 48, 0, 14, 32, 50, 45",
            "7735195, -5, -6, 47, -4, 13, 32, 48, 46",
            "7453891, -5, -14, 45, 4, 10, 31, 55, 41",
            "7207240, -4, -15, 51, -3, 11, 35, 50, 46",
            "6830564, -6, -15, 48, -4, 12, 31, 47, 42",
            "6357973, -3, -9, 45, 3, 10, 30, 47, 45",
            "5854339, -3, -11, 48, -2, 10, 34, 50, 43",
            "5494136, -5, -10, 44, -3, 12, 37, 52, 46",
            "5307054, -2, -15, 45, 1, 10, 33, 47, 45",
            "5297383, -2, -11, 39, 4, 11, 31, 52, 41",
            "5291309, -4, -7, 48, 4, 10, 32, 45, 45",
            "5199118, -2, -14, 45, -1, 14, 33, 49, 44",
            "4825974, -4, -13, 51, -3, 10, 33, 46, 47",
            "4621648, -5, -7, 51, 0, 11, 35, 54, 45",
            "4620648, -1, -13, 48, -2, 16, 34, 49, 50",
            "4587570, -6, -13, 47, -3, 11, 32, 49, 48",
            "4391275, -3, -13, 48, -2, 12, 37, 53, 48",
            "4218350, -3, -8, 43, 4, 14, 31, 47, 45",
            "-1, -320, -778, 4403, 324, 1316, 3170, 4743, 4495",
            "-0, 0, 0, 0, 0, 0, 0, 0, 0",
            "NONE"};
        for (int i = 0; i < myarray.length; i++) {
            Weights.add(myarray[i]);
        }
        lstWeights.setListData(Weights.toArray());
    }
}
