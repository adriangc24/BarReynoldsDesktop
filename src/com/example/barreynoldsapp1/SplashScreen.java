package com.example.barreynoldsapp1;

import javax.swing.*;
import javax.swing.plaf.ProgressBarUI;

import java.awt.*;
import javax.swing.GroupLayout.Alignment;

public class SplashScreen {
    private final JFrame window;
    private long startTime;
    private int minimumMilliseconds;
    static JProgressBar progressBar;


    public SplashScreen() {
        window = new JFrame("Group Layout");
        window.setUndecorated(true);
        window.getContentPane().setBackground(Color.BLACK);
        ImageIcon image = new ImageIcon("logobox.png");
        JLabel label = new JLabel("", image, SwingConstants.CENTER);
        
        progressBar = new JProgressBar(0,100);
        progressBar.setStringPainted(true);
        GroupLayout groupLayout = new GroupLayout(window.getContentPane());
        groupLayout.setHorizontalGroup(
        	groupLayout.createParallelGroup(Alignment.LEADING)
        		.addGroup(groupLayout.createSequentialGroup()
        			.addContainerGap()
        			.addComponent(label, GroupLayout.DEFAULT_SIZE, 700, Short.MAX_VALUE)
        			.addContainerGap())
        		.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
        			.addGap(142)
        			.addComponent(progressBar, GroupLayout.DEFAULT_SIZE, 446, Short.MAX_VALUE)
        			.addGap(132))
        );
        groupLayout.setVerticalGroup(
        	groupLayout.createParallelGroup(Alignment.LEADING)
        		.addGroup(groupLayout.createSequentialGroup()
        			.addComponent(label, GroupLayout.PREFERRED_SIZE, 350, GroupLayout.PREFERRED_SIZE)
        			.addGap(18)
        			.addComponent(progressBar, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)
        			.addContainerGap(27, Short.MAX_VALUE))
        );
        window.getContentPane().setLayout(groupLayout);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        window.setBounds((int) ((screenSize.getWidth() - image.getIconWidth()) / 2),
                (int) ((screenSize.getHeight() - image.getIconHeight()) / 2),
                720, 422);
    }

    public void show(int minimumMilliseconds) {
        this.minimumMilliseconds = minimumMilliseconds;
        window.setVisible(true);
        startTime = System.currentTimeMillis();
    }

    public void hide() {
        long elapsedTime = System.currentTimeMillis() - startTime;
        try {
            Thread.sleep(Math.max(minimumMilliseconds - elapsedTime, 0));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        window.setVisible(false);
    }
}
