package com.dalplex.gui;

import com.dalplex.data.Member;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;

/**
 * @author Ben Pace
 */
@Deprecated
public class QuickViewMember extends JPanel {
    private JPanel content, title;
    private JScrollPane scrollPane;
    private final String[] headerText = {"Name", "Postcode", "Membership", "Edit", "Delete"};
    private JLabel headers[], titleText;

    private Connection conn;

    private ArrayList<Member> members;

    public QuickViewMember(int width, int height, Connection conn){
        this.conn = conn;

        setSize(width, height);
        setLayout(new BorderLayout(0,10));

        title = new JPanel();
        titleText = new JLabel("Members");
        title.add(titleText);

        content = new JPanel(new GridLayout(0,5, 10,5));
        //content.setPreferredSize(new Dimension(400,400));
        //content.setMinimumSize(new Dimension(width, height));

        headers = new JLabel[5];
        for(int i = 0;i < headerText.length;i++){
            headers[i] = new JLabel(headerText[i]);
            headers[i].setSize(getWidth() / 5, headers[i].getHeight());
            headers[i].setHorizontalAlignment(SwingConstants.CENTER);
            headers[i].setBorder(BorderFactory.createMatteBorder(0,0,1,0, Color.BLACK));
            content.add(headers[i]);
        }



        scrollPane = new JScrollPane(content);


        add(title, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);


    }

    public void getMembers() throws SQLException{
        Statement stmnt = conn.createStatement();
        String sql = "FROM " + Member.TABLE + " SELECT id";
        stmnt.execute(sql);
        ResultSet rs = stmnt.getResultSet();
    }

}
