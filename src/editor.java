package com.company;

import com.sun.javafx.tk.FontLoader;

import java.awt.*;
import javax.swing.*;
import java.io.*;
import java.awt.event.*;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.plaf.metal.*;
import javax.swing.undo.UndoManager;

class editor extends JFrame implements ActionListener {

    JTextArea t;
    JFrame f;
    JLabel jLabelH;
    JLabel jLabelV;
    UndoManager manager = new UndoManager();


    // Constructor
    editor() {
        f = new JFrame("word");
        // icon
        Image icon = Toolkit.getDefaultToolkit().getImage("D:\\proje2\\src\\com\\company\\word.png");
        f.setIconImage(icon);
        //

        f.setLocationRelativeTo(null);


        try {
            // Set metal look and feel
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");

            // Set theme to ocean
            MetalLookAndFeel.setCurrentTheme(new OceanTheme());
        } catch (Exception e) {
        }

        t = new JTextArea();
        t.setBackground(Color.black);
        t.setForeground(Color.white);

        //undo_redo
        t.getDocument().addUndoableEditListener(new UndoableEditListener() {
            public void undoableEditHappened(UndoableEditEvent e) {
                manager.addEdit(e.getEdit());
            }
        });
        //

        JMenuBar mb = new JMenuBar();

        JMenu m1 = new JMenu("File");

        JMenuItem mi1 = new JMenuItem("New");

        JMenuItem mi2 = new JMenuItem("Open");

        ////////////////////////////////////////
        Action saveAction2 = new AbstractAction("open") {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser j = new JFileChooser("f:");

                int r = j.showOpenDialog(null);

                if (r == JFileChooser.APPROVE_OPTION) {
                    File fi = new File(j.getSelectedFile().getAbsolutePath());

                    try {
                        String s1 = "", sl = "";

                        FileReader fr = new FileReader(fi);

                        BufferedReader br = new BufferedReader(fr);

                        sl = br.readLine();

                        while ((s1 = br.readLine()) != null) {
                            sl = sl + "\n" + s1;
                        }

                        t.setText(sl);
                    } catch (Exception evt) {
                        JOptionPane.showMessageDialog(f, evt.getMessage());
                    }
                } else
                    JOptionPane.showMessageDialog(f, "the user cancelled the operation");

            }
        };

        // open shortcut
        saveAction2.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK));
        mi2.setAction(saveAction2);
        ////////////


        //////////////////////////////////////////////////////
        JMenuItem mi3 = new JMenuItem("Save");
        Action saveAction = new AbstractAction("save") {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser j = new JFileChooser("f:");

                int r = j.showSaveDialog(null);

                if (r == JFileChooser.APPROVE_OPTION) {

                    File fi = new File(j.getSelectedFile().getAbsolutePath());

                    try {
                        FileWriter wr = new FileWriter(fi, false);

                        BufferedWriter w = new BufferedWriter(wr);

                        w.write(t.getText());

                        w.flush();
                        w.close();
                    } catch (Exception evt) {
                        JOptionPane.showMessageDialog(f, evt.getMessage());
                    }
                } else
                    JOptionPane.showMessageDialog(f, "the user cancelled the operation");
            }
        };

        // save shortcut
        saveAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK));
        mi3.setAction(saveAction);
        ///////////////


        JMenuItem mi9 = new JMenuItem("Print");

        mi1.addActionListener(this);
        mi2.addActionListener(this);
        mi3.addActionListener(this);
        mi9.addActionListener(this);

        m1.add(mi1);
        m1.add(mi2);
        m1.add(mi3);
        m1.add(mi9);

        /////////////////////////////
        JMenu m2 = new JMenu("Edit");

        JMenuItem mi4 = new JMenuItem("cut");
        JMenuItem mi5 = new JMenuItem("copy");
        JMenuItem mi6 = new JMenuItem("paste");

        mi4.addActionListener(this);
        mi5.addActionListener(this);
        mi6.addActionListener(this);

        m2.add(mi4);
        m2.add(mi5);
        m2.add(mi6);

        /////////////////////////////////
        JMenu m3 = new JMenu("Font");

        JMenuItem mi7 = new JMenuItem("small");
        JMenuItem mi8 = new JMenuItem("normal");
        JMenuItem mi10 = new JMenuItem("large");
        JMenuItem mi13 = new JMenuItem("bold");
        JMenuItem mi14 = new JMenuItem("italic");
        JMenuItem mi15 = new JMenuItem("NFont");


        mi7.addActionListener(this);
        mi8.addActionListener(this);
        mi10.addActionListener(this);
        mi13.addActionListener(this);
        mi14.addActionListener(this);
        mi15.addActionListener(this);


        m3.add(mi7);
        m3.add(mi8);
        m3.add(mi10);
        m3.add(mi13);
        m3.add(mi14);
        m3.add(mi15);


        ////////////////////
        JMenu m4 = new JMenu("U_R");

        JMenuItem mi11 = new JMenuItem("undo");
        JMenuItem mi12 = new JMenuItem("redo");

        mi11.addActionListener(this);
        mi12.addActionListener(this);

        m4.add(mi11);
        m4.add(mi12);

        /////////////////////////
        JMenuItem m5 = new JMenuItem("NFrame");

        m5.addActionListener(this);

        ///////////////////////
        JMenuItem m6 = new JMenuItem("close");

        m6.addActionListener(this);

        mb.add(m1);
        mb.add(m2);
        mb.add(m3);
        mb.add(m4);
        mb.add(m5);
        mb.add(m6);

        //////////////////////////

        f.setJMenuBar(mb);
        f.setSize(500, 500);
        f.show();
        f.getContentPane().add(t);


        ///////////////// add scroll
        JScrollPane scrollBar = new JScrollPane(t);
        scrollBar.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollBar.setHorizontalScrollBarPolicy(
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        f.getContentPane().add(scrollBar);
        scrollBar.setBounds(13, 39, 413, 214);

        ///////////

    }

    public void actionPerformed(ActionEvent e) {
        String s = e.getActionCommand();

        if (s.equals("cut")) {
            t.cut();
        } else if (s.equals("copy")) {
            t.copy();
        } else if (s.equals("paste")) {
            t.paste();
        } else if (s.equals("Save")) {
            // Create an object of JFileChooser class
            JFileChooser j = new JFileChooser("f:");

            // Invoke the showsSaveDialog function to show the save dialog
            int r = j.showSaveDialog(null);

            if (r == JFileChooser.APPROVE_OPTION) {

                // Set the label to the path of the selected directory
                File fi = new File(j.getSelectedFile().getAbsolutePath());

                try {
                    FileWriter wr = new FileWriter(fi, false);

                    BufferedWriter w = new BufferedWriter(wr);

                    w.write(t.getText());

                    w.flush();
                    w.close();
                } catch (Exception evt) {
                    JOptionPane.showMessageDialog(f, evt.getMessage());
                }
            }
            // If the user cancelled the operation
            else
                JOptionPane.showMessageDialog(f, "the user cancelled the operation");
        } else if (s.equals("Print")) {
            try {
                // print the file
                t.print();
            } catch (Exception evt) {
                JOptionPane.showMessageDialog(f, evt.getMessage());
            }
        } else if (s.equals("Open")) {
            // Create an object of JFileChooser class
            JFileChooser j = new JFileChooser("f:");

            // Invoke the showsOpenDialog function to show the save dialog
            int r = j.showOpenDialog(null);

            // If the user selects a file
            if (r == JFileChooser.APPROVE_OPTION) {
                // Set the label to the path of the selected directory
                File fi = new File(j.getSelectedFile().getAbsolutePath());

                try {
                    String s1 = "", sl = "";

                    FileReader fr = new FileReader(fi);

                    BufferedReader br = new BufferedReader(fr);

                    // Initialize sl
                    sl = br.readLine();

                    // Take the input from the file
                    while ((s1 = br.readLine()) != null) {
                        sl = sl + "\n" + s1;
                    }

                    // Set the text
                    t.setText(sl);
                } catch (Exception evt) {
                    JOptionPane.showMessageDialog(f, evt.getMessage());
                }
            }
            // If the user cancelled the operation
            else
                JOptionPane.showMessageDialog(f, "the user cancelled the operation");

        } else if (s.equals("New")) {
            t.setText("");

        } else if (s.equals("close")) {
            f.setVisible(false);

        } else if (s.equals("small")) {
            t.setFont(new Font("Monospaced", Font.PLAIN, 20));

        } else if (s.equals("normal")) {
            t.setFont(new Font("Monospaced", Font.PLAIN, 40));

        } else if (s.equals("large")) {
            t.setFont(new Font("Monospaced", Font.PLAIN, 60));

        } else if (s.equals("undo")) {
            try {
                manager.undo();
            } catch (Exception ex) {
            }

        } else if (s.equals("redo")) {
            try {
                manager.redo();
            } catch (Exception ex) {
            }

        } else if (s.equals("NFrame")) {
            editor q = new editor();

        } else if (s.equals("bold")) {
            t.setFont(t.getFont().deriveFont(Font.BOLD, 40f));

        } else if (s.equals("italic")) {
            t.setFont(t.getFont().deriveFont(Font.ITALIC, 40f));

        } else if (s.equals("NFont")) {
            t.setFont(new Font("Monotype Corsiva", Font.PLAIN, 40));

        }


    }


}


