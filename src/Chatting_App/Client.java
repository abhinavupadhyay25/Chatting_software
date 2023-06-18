package Chatting_App;

import java.awt.*;
import java.awt.event.*;
import java.util.Calendar;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.border.EmptyBorder;
import java.util.*;
import java.text.*;
import java.io.*;
import java.net.*;

public class Client implements ActionListener {
    JPanel p1;
    static JPanel p2;
    JLabel l1,l2,l3,l4,l5,l6,l7;
    JTextField t1;
    JButton b1;
    static Box vertical=Box.createVerticalBox();
    static DataOutputStream dout;
    static JFrame f=new JFrame();

    Client(){
        f.setSize(450,700);
       f.setLocation(800,50);
       f.setUndecorated(true);

        f.getContentPane().setBackground(Color.WHITE);
        f.setLayout(null);

        p1=new JPanel();
        p1.setBackground(new Color(7,94,84));
        p1.setBounds(0,0,450,70);
        p1.setLayout(null);
        f.add(p1);

        ImageIcon img=new ImageIcon(ClassLoader.getSystemResource("Chatting_app/icons/3.png"));
        Image i1=img.getImage().getScaledInstance(25,25,Image.SCALE_DEFAULT);
        ImageIcon img1=new ImageIcon(i1);

        l1=new JLabel(img1);
        l1.setBounds(5,20,25,25);
        p1.add(l1);

        l1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.exit(0);
            }
        });

        ImageIcon img2=new ImageIcon(ClassLoader.getSystemResource("Chatting_App/icons/2.png"));
        Image i2=img2.getImage().getScaledInstance(50,50,Image.SCALE_DEFAULT);
        ImageIcon img3=new ImageIcon(i2);

        l2=new JLabel(img3);
        l2.setBounds(40,10,50,50);
        p1.add(l2);

        ImageIcon img4=new ImageIcon(ClassLoader.getSystemResource("Chatting_App/icons/video.png"));
        Image i3=img4.getImage().getScaledInstance(30,30,Image.SCALE_DEFAULT);
        ImageIcon img5=new ImageIcon(i3);

        l3=new JLabel(img5);
        l3.setBounds(300,20,30,30);
        p1.add(l3);

        ImageIcon img6=new ImageIcon(ClassLoader.getSystemResource("Chatting_App/icons/phone.png"));
        Image i4=img6.getImage().getScaledInstance(35,50,Image.SCALE_DEFAULT);
        ImageIcon img7=new ImageIcon(i4);

        l4=new JLabel(img7);
        l4.setBounds(360,20,35,30);
        p1.add(l4);

        ImageIcon img8=new ImageIcon(ClassLoader.getSystemResource("Chatting_App/icons/3icon.png"));
        Image i5=img8.getImage().getScaledInstance(10,25,Image.SCALE_DEFAULT);
        ImageIcon img9=new ImageIcon(i5);

        l5=new JLabel(img9);
        l5.setBounds(420,20,10,25);
        p1.add(l5);

        l6=new JLabel("Bunty");
        l6.setBounds(110,15,100,18);
        l6.setForeground(Color.WHITE);
        l6.setFont(new Font("SAN_SERIF",Font.BOLD,18));
        p1.add(l6);

        l7=new JLabel("Active Now");
        l7.setBounds(110,35,100,18);
        l7.setForeground(Color.WHITE);
        l7.setFont(new Font("SAN_SERIF",Font.BOLD,14));
        p1.add(l7);

        p2=new JPanel();
        p2.setBounds(5,75,440,570);
        f.add(p2);

        t1=new JTextField();
        t1.setBounds(5,655,310,40);
        t1.setFont(new Font("SAN_SERIF",Font.BOLD,16));
        f.add(t1);

        b1=new JButton("Send");
        b1.setBounds(320,655,123,40);
        b1.setBackground(new Color(7,94,84));
        b1.setFont(new Font("SAN_SERIF",Font.BOLD,16));
        b1.setForeground(Color.WHITE);
        f.add(b1);

        b1.addActionListener(this);

        f.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            String chat=t1.getText();
            JPanel chat1=formatLabel(chat);

            p2.setLayout(new BorderLayout());

            JPanel right=new JPanel(new BorderLayout());
            right.add(chat1,BorderLayout.LINE_END);
            vertical.add(right);
            vertical.add(Box.createVerticalStrut(15));

            p2.add(vertical,BorderLayout.PAGE_START);

            dout.writeUTF(chat);

            t1.setText("");

            f.repaint();
            f.invalidate();
            f.validate();
        }
        catch(Exception e2){
            e2.printStackTrace();
        }
    }

    public static JPanel formatLabel(String chat){
        JPanel panel=new JPanel();
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
        JLabel chat2=new JLabel("<html><p style=\"width: 150px\">" + chat + "</p></html>");
        chat2.setFont(new Font("Tahoma",Font.BOLD,16));
        chat2.setBackground(new Color(37,211,102));
        chat2.setOpaque(true);
        chat2.setBorder(new EmptyBorder(15,15,15,50));

        panel.add(chat2);

        Calendar cal= Calendar.getInstance();
        SimpleDateFormat sdf=new SimpleDateFormat("HH:MM");

        JLabel time=new JLabel();
        time.setText(sdf.format(cal.getTime()));

        panel.add(time);

        return  panel;
    }

    public static void main(String[] args) {
        new Client();

        try {
            Socket socket=new Socket("localhost",60001);
            DataInputStream din=new DataInputStream(socket.getInputStream());
            dout=new DataOutputStream(socket.getOutputStream());

            while(true){
                p2.setLayout(new BorderLayout());
                String msg=(String) din.readUTF();
                JPanel panel=formatLabel(msg);

                JPanel left=new JPanel(new BorderLayout());
                left.add(panel,BorderLayout.LINE_START);
                vertical.add(left);

                vertical.add(Box.createVerticalStrut(15));
                p2.add(vertical,BorderLayout.PAGE_START);
                f.validate();
            }
        }
        catch (Exception e1){
            e1.printStackTrace();
        }
    }
}
