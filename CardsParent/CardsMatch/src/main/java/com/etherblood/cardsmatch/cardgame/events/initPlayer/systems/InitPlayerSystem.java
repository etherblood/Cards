///*
// * To change this template, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.etherblood.cardsmatch.cardgame.events.initPlayer.systems;
//
//import com.etherblood.cardsmatch.cardgame.MatchAbstractSystem;
//import com.etherblood.cardsmatch.cardgame.components.effects.GameOverEffectComponent;
//import com.etherblood.cardsmatch.cardgame.components.effects.triggers.DeathrattleTriggerComponent;
//import com.etherblood.cardsmatch.cardgame.components.misc.NameComponent;
//import com.etherblood.cardsmatch.cardgame.components.player.PlayerComponent;
//import com.etherblood.cardsmatch.cardgame.events.instantiateTemplate.InstantiateTemplateEvent;
//import com.etherblood.cardsmatch.cardgame.events.initPlayer.InitPlayerEvent;
//import com.etherblood.cardsmatch.cardgame.events.cardZones.BoardAttachEvent;
//import com.etherblood.cardsmatch.cardgame.events.setOwner.SetOwnerEvent;
//import com.etherblood.cardsmatch.cardgame.events.cardZones.LibraryAttachEvent;
//import com.etherblood.entitysystem.data.EntityId;
//
///**
// *
// * @author Philipp
// */
//public class InitPlayerSystem extends MatchAbstractSystem<InitPlayerEvent> {
//
//    @Override
//    public InitPlayerEvent handle(InitPlayerEvent event) {
//        EntityId player = event.playerId;
//        data.setComponent(player, new NameComponent(event.name));
//        data.setComponent(player, new PlayerComponent());
//
//        EntityId hero = createEntity();
//        fireEvent(new InstantiateTemplateEvent(hero, event.heroTemplate));
//        fireEvent(new SetOwnerEvent(hero, player));
//        fireEvent(new BoardAttachEvent(hero));
//        EntityId gameOverDeathrattle = createEntity();
//        data.setComponent(gameOverDeathrattle, new DeathrattleTriggerComponent(hero));
//        data.setComponent(gameOverDeathrattle, new GameOverEffectComponent());
//
//        for (String template : event.libraryCardTemplateNames) {
//            EntityId cardId = createEntity();
//            fireEvent(new InstantiateTemplateEvent(cardId, template));
//            fireEvent(new SetOwnerEvent(cardId, player));
//            fireEvent(new LibraryAttachEvent(cardId));
//        }
//        return event;
//    }
//}
