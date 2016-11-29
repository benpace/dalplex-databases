package com.dalplex.gui.fullviews;

import com.dalplex.data.Member;
import com.dalplex.data.Program;
import com.dalplex.gui.FullView;

import javax.swing.*;
import java.awt.*;


public class EventView extends FullView{
    private Program event;
    private final String[] fieldNames = {
            "Description", "Date", "Room"
    };
    private JLabel[] fieldLabels, data;
    public EventView(int width, int height, Program e) {
        super(width, height, "Program");
        this.event = e;

        fieldLabels = new JLabel[fieldNames.length];
        data = new JLabel[fieldNames.length];

        //data = new JLabel[fieldNames.length];
        data[0] = new JLabel(event.getDescription());
        data[1] = new JLabel(event.getDate()+"");
        data[2] = new JLabel(event.getRoom().getDescription());



        for (int i = 0; i < fieldNames.length; i++) {
            fieldLabels[i] = new JLabel(fieldNames[i]);
            fieldLabels[i].setBorder(BorderFactory.createMatteBorder(0, 0, 0, 2, Color.BLACK));
            fieldLabels[i].setHorizontalAlignment(SwingConstants.CENTER);
            data[i].setHorizontalAlignment(SwingConstants.CENTER);
            getFields().add(fieldLabels[i]);
            getFields().add(data[i]);
        }
        JLabel memTitle = new JLabel("Members");
        memTitle.setFont(new Font(memTitle.getFont().getName(), Font.BOLD, memTitle.getFont().getSize()));
        getFields().add(memTitle);
        getFields().add(new JSeparator());

        for(Member member: event.getMembers()){
            getFields().add(new JLabel(member.getFname()));
            getFields().add(new JLabel(""+member.getLname()));
        }
    }
}
