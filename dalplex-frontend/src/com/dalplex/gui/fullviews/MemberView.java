package com.dalplex.gui.fullviews;

import com.dalplex.data.Member;
import com.dalplex.gui.FullView;

import javax.swing.*;
import java.awt.*;


public class MemberView extends FullView {
    private Member member;
    private final String[] fieldNames = {
            "First Name", "Last Name", "Phone Number",
            "Birthday", "Address", "City", "Postcode", "Membership Type"
    };
    private JLabel[] fieldLabels, data;
    public MemberView(int width, int height, Member e) {
        super(width, height, "Member: " + e.getFname());
        this.member = e;

        fieldLabels = new JLabel[fieldNames.length];
        data = new JLabel[fieldNames.length];

        //data = new JLabel[fieldNames.length];

        data[0] = new JLabel(member.getFname());
        data[1] = new JLabel(member.getLname());
        data[2] = new JLabel(member.getPhone());
        data[3] = new JLabel("" + member.getBirthday());
        data[4] = new JLabel(member.getAddress());
        data[5] = new JLabel(member.getCity());
        data[6] = new JLabel(member.getPostcode());
        data[7] = new JLabel(member.getMemType().getDescription());


        for (int i = 0; i < fieldNames.length; i++) {
            fieldLabels[i] = new JLabel(fieldNames[i]);
            fieldLabels[i].setBorder(BorderFactory.createMatteBorder(0, 0, 0, 2, Color.BLACK));
            fieldLabels[i].setHorizontalAlignment(SwingConstants.CENTER);
            data[i].setHorizontalAlignment(SwingConstants.CENTER);
            getFields().add(fieldLabels[i]);
            getFields().add(data[i]);
        }
    }
}
