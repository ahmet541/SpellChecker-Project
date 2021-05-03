package View;

import java.io.IOException;

import javax.swing.JFrame;

public class MyListGameGui {

    // Variables declaration - do not modify  
    private String chosenWord;   
    private JFrame frame;                
    private javax.swing.JTextField answerText;
    private javax.swing.JButton backButton;
    private javax.swing.JPanel bottom;
    private javax.swing.JButton checkButton;
    private javax.swing.JLabel inst1;
    private javax.swing.JLabel inst2;
    private javax.swing.JPanel textArea;
    private javax.swing.JPanel left;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JPanel right;
    private javax.swing.JPanel top;
    private String answer;
    private String whichGame;
    /**
     * Creates new form MyListGameGUI
     */
    public MyListGameGui(String chosenWord, JFrame frame, String whichGame) {
        this.frame = frame;
        frame.getContentPane().setVisible(false);
        frame.getContentPane().removeAll();
        initComponents();
        this.chosenWord = chosenWord;
        this.whichGame = whichGame;
        frame.getContentPane().setVisible(true);
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        backButton = new javax.swing.JButton();
        textArea = new javax.swing.JPanel();
        inst2 = new javax.swing.JLabel();
        inst1 = new javax.swing.JLabel();
        answerText = new javax.swing.JTextField();
        checkButton = new javax.swing.JButton();
        top = new javax.swing.JPanel();
        bottom = new javax.swing.JPanel();
        right = new javax.swing.JPanel();
        left = new javax.swing.JPanel();


        mainPanel.setBackground(new java.awt.Color(204, 204, 255));

        backButton.setText("Back");
        backButton.setAlignmentY(0.0F);
        backButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {
					backButtonActionPerformed(evt);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        });
        
        answerText.setAlignmentX(0.0F);
        answerText.setAlignmentY(0.0F);

        checkButton.setText("CHECK!");
        checkButton.setAlignmentY(0.0F);
        checkButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkButtonActionPerformed(evt);
            }
        });
        
        inst2.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        inst2.setText("try to wirte the word you choose correctly!");

        inst1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        inst1.setText("Take a deep breath,think about the clouds and");

        javax.swing.GroupLayout textAreaLayout = new javax.swing.GroupLayout(textArea);
        textArea.setLayout(textAreaLayout);
        textAreaLayout.setHorizontalGroup(
            textAreaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(textAreaLayout.createSequentialGroup()
                .addGroup(textAreaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(textAreaLayout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addComponent(inst1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(textAreaLayout.createSequentialGroup()
                        .addGap(52, 52, 52)
                        .addComponent(inst2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(12, 12, 12)))
                .addGap(49, 49, 49))
        );
        textAreaLayout.setVerticalGroup(
            textAreaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(textAreaLayout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(inst1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(inst2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(38, 38, 38))
        );

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addGap(167, 167, 167)
                        .addComponent(checkButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(59, 59, 59))
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addComponent(backButton)
                        .addGap(59, 59, 59)
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(textArea, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(answerText))))
                .addGap(112, 112, 112))
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addComponent(backButton)
                .addGap(34, 34, 34)
                .addComponent(textArea, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(31, 31, 31)
                .addComponent(answerText, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(45, 45, 45)
                .addComponent(checkButton, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(95, 95, 95))
        );

        frame.getContentPane().add(mainPanel, java.awt.BorderLayout.CENTER);

        top.setBackground(new java.awt.Color(255, 255, 255));
        top.setPreferredSize(new java.awt.Dimension(20, 20));

        javax.swing.GroupLayout topLayout = new javax.swing.GroupLayout(top);
        top.setLayout(topLayout);
        topLayout.setHorizontalGroup(
            topLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 675, Short.MAX_VALUE)
        );
        topLayout.setVerticalGroup(
            topLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );

        frame.getContentPane().add(top, java.awt.BorderLayout.PAGE_START);

        bottom.setBackground(new java.awt.Color(255, 255, 255));
        bottom.setPreferredSize(new java.awt.Dimension(566, 20));

        javax.swing.GroupLayout bottomLayout = new javax.swing.GroupLayout(bottom);
        bottom.setLayout(bottomLayout);
        bottomLayout.setHorizontalGroup(
            bottomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 675, Short.MAX_VALUE)
        );
        bottomLayout.setVerticalGroup(
            bottomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );

        frame.getContentPane().add(bottom, java.awt.BorderLayout.PAGE_END);

        right.setBackground(new java.awt.Color(255, 255, 255));
        right.setPreferredSize(new java.awt.Dimension(20, 316));

        javax.swing.GroupLayout rightLayout = new javax.swing.GroupLayout(right);
        right.setLayout(rightLayout);
        rightLayout.setHorizontalGroup(
            rightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        rightLayout.setVerticalGroup(
            rightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 402, Short.MAX_VALUE)
        );

        frame.getContentPane().add(right, java.awt.BorderLayout.LINE_END);

        left.setBackground(new java.awt.Color(255, 255, 255));
        left.setForeground(new java.awt.Color(51, 255, 255));
        left.setPreferredSize(new java.awt.Dimension(20, 316));

        javax.swing.GroupLayout leftLayout = new javax.swing.GroupLayout(left);
        left.setLayout(leftLayout);
        leftLayout.setHorizontalGroup(
            leftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        leftLayout.setVerticalGroup(
            leftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 402, Short.MAX_VALUE)
        );

        frame.getContentPane().add(left, java.awt.BorderLayout.LINE_START);
    }// </editor-fold>                        

    /**
     * It chechks whether the game is won by the user.
     * @return true if the user win, otherwise false
     */
    public boolean isRoundWon(){
        answer = answerText.getText().toLowerCase();;
        if( answer.equals( this.chosenWord ) ) {
            return true;
        }
        
        return false;
    }
    
    private void checkButtonActionPerformed(java.awt.event.ActionEvent evt) {                                            
        if(isRoundWon()){
            inst1.setText("                        CONGRATILATIONS!");
            inst2.setText("                Your answer is correct!");
            mainPanel.setBackground(new java.awt.Color(153, 255, 153));
        }
        else{
            inst1.setText("                          WRONG ANSWER!");
            inst2.setText("                           Try Again!");
            mainPanel.setBackground(new java.awt.Color(255, 102, 102));
        }
    }       
    
    private void backButtonActionPerformed(java.awt.event.ActionEvent evt) throws IOException {                                           
        // TODO add your handling code here:
        if ( whichGame.equals("first")) {
            new MyListGui(frame);
        }
        else {
            new ChooseWordGui(whichGame, frame);
        }   
    }                                          

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
            java.util.logging.Logger.getLogger(MyListGameGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MyListGameGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MyListGameGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MyListGameGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

    }


    // End of variables declaration                   
}
