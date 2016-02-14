package com.etherblood.cardsswingdisplay;

import java.awt.BorderLayout;

/**
 *
 * @author Philipp
 */
public class MainJFrame extends javax.swing.JFrame {

    private static final Long OWN_ID = 0L;
    private static final Long OPP_ID = 1L;
    public static final String CLIENT_TEMPLATES_PATH = "";//System.getProperty("user.home") + System.getProperty("file.separator") + "etherblood" + System.getProperty("file.separator");
    private final GameController controller = new GameController(new GamePanel());

    /**
     * Creates new form MainJFrame
     */
    public MainJFrame() {
        try {
            TemplatesReader.INSTANCE.read(CLIENT_TEMPLATES_PATH);
        } catch(Exception e) {
            e.printStackTrace(System.out);
        }

        initComponents();
        
        getContentPane().setLayout(new BorderLayout());
        GamePanel panel = controller.getGamePanel();
        add(panel, BorderLayout.CENTER);
        panel.setFocusable(true);
        panel.requestFocusInWindow();

        controller.createCard(OWN_ID, "player");
//        controller.setCardOwner(OWN_ID, OWN_ID);
//        controller.setCardZone(OWN_ID, CardZone.Board);

        controller.createCard(OPP_ID, "opponent");
//        controller.setCardOwner(OPP_ID, OPP_ID);
//        controller.setCardZone(OPP_ID, CardZone.Board);

//        long card3 = 3L;
//        controller.createCard(card3, "minion");
//        controller.setCardOwner(card3, OPP_ID);
//        controller.setCardZone(card3, CardZone.Board);
//        controller.setCardAttack(card3, 5);
//        controller.setCardHealth(card3, 7);
//        controller.setCardCost(card3, 6);
//        
//        long card4 = 4L;
//        controller.createCard(card4, "minion");
//        controller.setCardOwner(card4, OWN_ID);
//        controller.setCardZone(card4, CardZone.Hand);
//        controller.setCardAttack(card4, 5);
//        controller.setCardHealth(card4, 7);
//        controller.setCardCost(card4, 6);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Cards Display");
        setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1177, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 790, Short.MAX_VALUE)
        );

        pack();
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
            java.util.logging.Logger.getLogger(MainJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainJFrame().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

    public GameController getController() {
        return controller;
    }
}
