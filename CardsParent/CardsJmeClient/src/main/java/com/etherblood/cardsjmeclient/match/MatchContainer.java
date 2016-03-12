package com.etherblood.cardsjmeclient.match;

import com.etherblood.cardsjmeclient.match.cards.Card;
import com.simsilica.lemur.Axis;
import com.simsilica.lemur.Container;
import com.simsilica.lemur.FillMode;
import com.simsilica.lemur.Label;
import com.simsilica.lemur.component.BorderLayout;
import com.simsilica.lemur.component.BoxLayout;
import com.simsilica.lemur.component.SpringGridLayout;
import java.util.List;

/**
 *
 * @author Philipp
 */
public class MatchContainer extends Container {

    private final Container oppBoard = new Container();
    private final Container ownBoard = new Container();
    private final Container ownHand = new Container();
    private final Container oppHand = new Container();
//    private final Container infoPanel = new Container();
    private final InfoContainer infos = new InfoContainer();
//    private final Label actionLabel = new Label("");
    
    
    public MatchContainer() {
        super(new SpringGridLayout(Axis.X, Axis.Y));
//        addChild(infoPanel, BorderLayout.Position.West);
//        infoPanel.addChild(actionLabel);
        Container tmp = new Container();
        addChild(tmp);
        addChild(infos);
        tmp.addChild(oppHand);
        tmp.addChild(oppBoard);
        tmp.addChild(ownBoard);
        tmp.addChild(ownHand);
        oppHand.setLayout(new BoxLayout(Axis.X, FillMode.None));
        oppBoard.setLayout(new BoxLayout(Axis.X, FillMode.None));
        ownBoard.setLayout(new BoxLayout(Axis.X, FillMode.None));
        ownHand.setLayout(new BoxLayout(Axis.X, FillMode.None));
    }

//    @Override
//    public void paint(Graphics g) {
//        super.paint(g);
//        if(drag != null && drop != null) {
//            g.setColor(Color.red);
//            g.drawLine(drag.x, drag.y, drop.x, drop.y);
//        }
//    }

    public Container getOppHand() {
        return oppHand;
    }
    
    public Container getOppBoard() {
        return oppBoard;
    }

    public Container getOwnBoard() {
        return ownBoard;
    }

    public Container getOwnHand() {
        return ownHand;
    }

    public void select(Card card) {
        if(card == null) {
            infos.deselect();
        } else {
            infos.select(card);
        }
    }
    
//    public void setAction(Ability ability, List<Card> targets) {
//        String text = "";
//        if (!targets.isEmpty()) {
//            for (Card cardPanel : targets) {
//                text += ", " + cardPanel.getCardName();
//            }
//            text = " {" + text.replaceFirst(", ", "") + "}";
//        }
//        if (ability != null) {
//            text = ability.getName() + text;
//        }
//        actionLabel.setText(text);
//    }
}
