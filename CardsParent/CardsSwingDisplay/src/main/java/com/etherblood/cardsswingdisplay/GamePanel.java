package com.etherblood.cardsswingdisplay;

import java.awt.BorderLayout;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Philipp
 */
public class GamePanel extends JPanel {

    private final ZonePanel oppBoard = new ZonePanel();
    private final ZonePanel ownBoard = new ZonePanel();
    private final ZonePanel ownHand = new ZonePanel();
    private final ZonePanel oppHand = new ZonePanel();
    private final JPanel infoPanel = new JPanel();
    private final JLabel actionLabel = new JLabel();
//    private Point drag, drop;

    public GamePanel() {
        super(new BorderLayout());

        add(infoPanel, BorderLayout.WEST);
        infoPanel.add(actionLabel, BorderLayout.NORTH);

        JPanel tmp = new JPanel(new BorderLayout());
        tmp.add(oppHand, BorderLayout.NORTH);
        tmp.add(oppBoard, BorderLayout.SOUTH);
        add(tmp, BorderLayout.NORTH);
        add(ownBoard, BorderLayout.CENTER);
        add(ownHand, BorderLayout.SOUTH);
    }

//    @Override
//    public void paint(Graphics g) {
//        super.paint(g);
//        if(drag != null && drop != null) {
//            g.setColor(Color.red);
//            g.drawLine(drag.x, drag.y, drop.x, drop.y);
//        }
//    }

    public ZonePanel getOppHand() {
        return oppHand;
    }
    
    public ZonePanel getOppBoard() {
        return oppBoard;
    }

    public ZonePanel getOwnBoard() {
        return ownBoard;
    }

    public ZonePanel getOwnHand() {
        return ownHand;
    }

    public void setAction(Ability ability, List<CardPanel> targets) {
        String text = "";
        if (!targets.isEmpty()) {
            for (CardPanel cardPanel : targets) {
                text += ", " + cardPanel.getCardName();
            }
            text = " {" + text.replaceFirst(", ", "") + "}";
        }
        if (ability != null) {
            text = ability.getName() + text;
        }
        actionLabel.setText(text);
    }
//    public void setDrag(Point drag) {
//        this.drag = drag;
//    }
//
//    public void setDrop(Point drop) {
//        this.drop = drop;
//    }
}
