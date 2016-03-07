package com.etherblood.cardsmatch.cardgame;

import com.etherblood.cardsmatch.cardgame.components.battle.LegendaryComponent;
import com.etherblood.cardsmatch.cardgame.components.battle.buffs.ChargeComponent;
import com.etherblood.cardsmatch.cardgame.components.battle.buffs.DivineShieldComponent;
import com.etherblood.cardsmatch.cardgame.components.battle.MinionComponent;
import com.etherblood.cardsmatch.cardgame.components.battle.buffs.TauntComponent;
import com.etherblood.cardsmatch.cardgame.components.battle.hero.HeroComponent;
import com.etherblood.cardsmatch.cardgame.components.misc.NameComponent;
import com.etherblood.cardsmatch.cardgame.components.battle.stats.AttackComponent;
import com.etherblood.cardsmatch.cardgame.components.battle.stats.HealthComponent;
import com.etherblood.cardsmatch.cardgame.components.battle.stats.ManaCostComponent;
import com.etherblood.cardsmatch.cardgame.components.battle.races.BeastComponent;
import com.etherblood.cardsmatch.cardgame.components.battle.races.DemonComponent;
import com.etherblood.cardsmatch.cardgame.components.battle.races.DragonComponent;
import com.etherblood.cardsmatch.cardgame.components.battle.races.MechComponent;
import com.etherblood.cardsmatch.cardgame.components.battle.races.MurlocComponent;
import com.etherblood.cardsmatch.cardgame.components.battle.races.PirateComponent;
import com.etherblood.cardsmatch.cardgame.components.cards.CastTemplateComponent;
import com.etherblood.cardsmatch.cardgame.components.cards.DeleteOnTriggerRemovedFromHand;
import com.etherblood.cardsmatch.cardgame.components.cards.SpellComponent;
import com.etherblood.cardsmatch.cardgame.components.effects.effects.PlayerDefeatedEffectComponent;
import com.etherblood.cardsmatch.cardgame.components.effects.effects.AttachTemplateEffectComponent;
import com.etherblood.cardsmatch.cardgame.components.effects.effects.AttackEffectComponent;
import com.etherblood.cardsmatch.cardgame.components.effects.effects.cardzone.BoardAttachEffectComponent;
import com.etherblood.cardsmatch.cardgame.components.effects.targeting.CreateSingleTargetEntityEffectComponent;
import com.etherblood.cardsmatch.cardgame.components.effects.effects.DealDamageEffectComponent;
import com.etherblood.cardsmatch.cardgame.components.effects.effects.DealRandomDamageEffectComponent;
import com.etherblood.cardsmatch.cardgame.components.effects.effects.DrawEffectComponent;
import com.etherblood.cardsmatch.cardgame.components.effects.effects.MakeAllyEffectComponent;
import com.etherblood.cardsmatch.cardgame.components.effects.effects.SummonEffectComponent;
import com.etherblood.cardsmatch.cardgame.components.effects.conditions.CanAttackConditionComponent;
import com.etherblood.cardsmatch.cardgame.components.effects.conditions.ItsMyTurnConditionComponent;
import com.etherblood.cardsmatch.cardgame.components.effects.effects.EndTurnEffectComponent;
import com.etherblood.cardsmatch.cardgame.components.effects.effects.MakeEnemyEffectComponent;
import com.etherblood.cardsmatch.cardgame.components.effects.effects.mana.PayManaCostEffectComponent;
import com.etherblood.cardsmatch.cardgame.components.effects.targeting.EffectIsTargetedComponent;
import com.etherblood.cardsmatch.cardgame.components.effects.targeting.EffectMinimumTargetsRequiredComponent;
import com.etherblood.cardsmatch.cardgame.components.effects.targeting.EffectRequiresUserTargetsComponent;
import com.etherblood.cardsmatch.cardgame.components.effects.targeting.filters.TargetAlliesComponent;
import com.etherblood.cardsmatch.cardgame.components.effects.targeting.filters.TargetBoardComponent;
import com.etherblood.cardsmatch.cardgame.components.effects.targeting.filters.TargetEnemiesComponent;
import com.etherblood.cardsmatch.cardgame.components.effects.targeting.filters.TargetExcludeSelfComponent;
import com.etherblood.cardsmatch.cardgame.components.effects.targeting.filters.TargetHeroesComponent;
import com.etherblood.cardsmatch.cardgame.components.effects.targeting.filters.TargetMinionsComponent;
import com.etherblood.cardsmatch.cardgame.components.effects.targeting.filters.TargetOwnerComponent;
import com.etherblood.cardsmatch.cardgame.components.effects.targeting.filters.TargetPlayersComponent;
import com.etherblood.cardsmatch.cardgame.components.effects.targeting.filters.TargetSelfComponent;
import com.etherblood.cardsmatch.cardgame.components.effects.targeting.EffectTargetsSingleRandomComponent;
import com.etherblood.cardsmatch.cardgame.components.effects.triggers.BattlecryTriggerComponent;
import com.etherblood.cardsmatch.cardgame.components.effects.triggers.DeathrattleTriggerComponent;
import com.etherblood.cardsmatch.cardgame.components.effects.triggers.EndTurnTriggerComponent;
import com.etherblood.cardsmatch.cardgame.components.effects.triggers.PlayerActivationTriggerComponent;
import com.etherblood.entitysystem.data.EntityComponent;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

/**
 *
 * @author Philipp
 */
public class DefaultTemplateSetFactory {

    public static final String ACTIVATION_ATTACK = "Activation=>Attack";
    public static final String ACTIVATION_SUMMON = "Activation=>Summon";
    private final ArrayList<EntityTemplate> templates = new ArrayList<>();

    private final EntityComponent[] targetEnemyMinions = new EntityComponent[]{new TargetEnemiesComponent(), new TargetMinionsComponent(), new TargetBoardComponent()};
    private final EntityComponent[] targetAllMinions = new EntityComponent[]{new TargetMinionsComponent(), new TargetBoardComponent()};
    private final EntityComponent[] targetEnemyCharacters = new EntityComponent[]{new TargetEnemiesComponent(), new TargetBoardComponent()};
    private final EntityComponent[] targetOwner = new EntityComponent[]{new TargetOwnerComponent()};
    private final EntityComponent[] targetOpponent = new EntityComponent[]{new TargetEnemiesComponent(), new TargetPlayersComponent()};
    private final EntityComponent[] targetPlayers = new EntityComponent[]{new TargetPlayersComponent()};
    private final EntityComponent[] targetAllCharacters = new EntityComponent[]{new TargetBoardComponent()};
    private final EntityComponent[] targetSelf = new EntityComponent[]{new TargetSelfComponent()};
    private final EntityComponent[] targetEnemyHeroes = new EntityComponent[]{new TargetEnemiesComponent(), new TargetBoardComponent(), new TargetHeroesComponent()};
    private final BattlecryTriggerComponent battlecry = new BattlecryTriggerComponent();
    private final EffectMinimumTargetsRequiredComponent atLeastOneTargetRequired = new EffectMinimumTargetsRequiredComponent(1);

    public TemplateSet createEntityTemplates(String templatesPath) {

        for (EntityTemplate entityTemplate : new TemplateLoader().read(templatesPath)) {
            templates.add(entityTemplate);
        }

        createHeroes();
        createMinions();
        createSpells();

        EntityTemplate summonEffect = new EntityTemplate();
        summonEffect.setCollectible(false);
        summonEffect.add(new NameComponent(ACTIVATION_SUMMON));
        summonEffect.add(new PayManaCostEffectComponent());
        summonEffect.add(new SummonEffectComponent());
        summonEffect.add(new PlayerActivationTriggerComponent());
        summonEffect.add(new ItsMyTurnConditionComponent());
        summonEffect.add(new DeleteOnTriggerRemovedFromHand());
        templates.add(summonEffect);

        EntityTemplate attackEffect = new EntityTemplate();
        attackEffect.setCollectible(false);
        attackEffect.add(new NameComponent(ACTIVATION_ATTACK));
        attackEffect.add(new PlayerActivationTriggerComponent());
        attackEffect.add(new AttackEffectComponent());
        attackEffect.add(atLeastOneTargetRequired);
        attackEffect.add(new ItsMyTurnConditionComponent());
        attackEffect.add(new CanAttackConditionComponent());
        templates.add(attackEffect);

        return new TemplateSet(templateListToMap());
    }

    private void createHeroes() {

        EntityTemplate template = new EntityTemplate();
        template.add(new NameComponent("Hero"));
        template.add(new HealthComponent(20));
        template.add(new HeroComponent());
//        template.add(new RegenerationComponent(1));

        EntityTemplate endTurnEffect = new EntityTemplate();
        endTurnEffect.setCollectible(false);
        endTurnEffect.add(new NameComponent("Activation=>EndTurn"));
        endTurnEffect.add(new PlayerActivationTriggerComponent());
        endTurnEffect.add(new EffectIsTargetedComponent());
        endTurnEffect.add(new TargetOwnerComponent());
        endTurnEffect.add(new ItsMyTurnConditionComponent());
        endTurnEffect.add(new EndTurnEffectComponent());
        templates.add(endTurnEffect);

        EntityTemplate deathrattleDefeat = new EntityTemplate();
        deathrattleDefeat.setCollectible(false);
        deathrattleDefeat.add(new NameComponent("Deathrattle=>Defeat"));
        deathrattleDefeat.add(new DeathrattleTriggerComponent());
        deathrattleDefeat.add(new PlayerDefeatedEffectComponent());
        templates.add(deathrattleDefeat);
        template.addChild(deathrattleDefeat.getName());
        template.addChild(endTurnEffect.getName());
        template.setCollectible(false);
        templates.add(template);
    }

    private void createMinions() {
        ChargeComponent charge = new ChargeComponent();
        DivineShieldComponent divine = new DivineShieldComponent();
        TauntComponent taunt = new TauntComponent();
        DeathrattleTriggerComponent deathrattle = new DeathrattleTriggerComponent();
        EndTurnTriggerComponent endTurn = new EndTurnTriggerComponent();

        ItsMyTurnConditionComponent myTurn = new ItsMyTurnConditionComponent();

        LegendaryComponent legendary = new LegendaryComponent();

        BeastComponent beast = new BeastComponent();
        MechComponent mech = new MechComponent();
        MurlocComponent murloc = new MurlocComponent();
        DragonComponent dragon = new DragonComponent();
        DemonComponent demon = new DemonComponent();
        PirateComponent pirate = new PirateComponent();

//        createMinion("Wisp", 0, 1, 1);
        createMinion("Target Dummy", 0, 0, 2, taunt, mech);
        createMinion("Murloc Raider", 1, 2, 1, murloc);
        createMinion("Stonetusk Boar", 1, 1, 1, charge, beast);
        createMinion("Argent Squire", 1, 1, 1, divine);
        createMinion("Voidwalker", 1, 1, 3, taunt, demon);
        createMinion("Tournament Attendee", 1, 2, 1, taunt);
        createMinion("Shieldbearer", 1, 0, 4, taunt);
        createMinion("Goldshire Footman", 1, 1, 2, taunt);
        createMinion("Bloodfen Raptor", 2, 3, 2, beast);
        createMinion("Puddlestomper", 2, 3, 2, murloc);
//        createMinion("River Crocolisk", 2, 2, 3, beast);
        createMinion("Bluegill Warrior", 2, 2, 1, charge, murloc);
        createMinion("Shielded Minibot", 2, 2, 2, divine, mech);
        createMinion("Annoy-o-Tron", 2, 1, 2, taunt, divine, mech);
        createMinion("Frostwolf Grunt", 2, 2, 2, taunt);
        createMinion("Scarlet Crusader", 3, 3, 1, divine);
        createMinion("Ice Rager", 3, 5, 2);
        createMinion("Magma Rager", 3, 5, 1);
        createMinion("Spider Tank", 3, 3, 4, mech);
        createMinion("Wolfrider", 3, 3, 1, charge);
        createMinion("Argent Horserider", 3, 2, 1, charge, divine);
        createMinion("Gnomeregan Infantry", 3, 1, 4, taunt, charge);
        createMinion("Silverback Patriarch", 3, 1, 4, taunt, beast);
        createMinion("Ironfur Grizzly", 3, 3, 3, taunt, beast);
        createMinion("Chillwind Yeti", 4, 4, 5);
        createMinion("Kor'kron Elite", 4, 4, 3, charge);
        createMinion("Lost Tallstrider", 4, 5, 4, beast);
        createMinion("Silvermoon Guardian", 4, 3, 3, divine);
        createMinion("Oasis Snapjaw", 4, 2, 7, beast);
        createMinion("Stormwind Knight", 4, 2, 5, charge);
        createMinion("Sen'jin Shieldmasta", 4, 3, 5, taunt);
        createMinion("Mogu'shan Warden", 4, 1, 7, taunt);
        createMinion("Evil Heckler", 4, 5, 4, taunt);
        createMinion("Pit Fighter", 5, 5, 6);
        createMinion("Salty Dog", 5, 7, 4, pirate);
        createMinion("Booty Bay Bodyguard", 5, 5, 4, taunt);
        createMinion("Fen Creeper", 5, 3, 6, taunt);
        createMinion("Argent Commander", 6, 4, 2, charge, divine);
        createMinion("Boulderfist Ogre", 6, 6, 7);
        createMinion("Reckless Rocketeer", 6, 5, 2, charge);
        createMinion("Lord of the Arena", 6, 6, 5, taunt);
        createMinion("Sunwalker", 6, 4, 5, taunt, divine);
        createMinion("Captured Jormungar", 7, 5, 9, beast);
        createMinion("Fearsome Doomguard", 7, 6, 8, demon);
        createMinion("Wargolem", 7, 7, 7);
        createMinion("Core Hound", 7, 9, 5, beast);
        createMinion("Force-Tank MAX", 8, 7, 7, divine, mech);
        createMinion("Ironbark Protector", 8, 8, 8, taunt);
        createMinion("King Krush", 9, 8, 8, charge, beast, legendary);
        createMinion("Nozdormu", 9, 8, 8, dragon);
        createMinion("Thaddius", 10, 11, 11, legendary);

        EntityTemplate highmane = createMinion("Savannah Highmane", 6, 6, 5, beast);
        String hyenaDeathrattle = createAllySpawn(createToken("Hyena", 2, 2, 2, beast).getName(), deathrattle);
        highmane.addChild(hyenaDeathrattle);
        highmane.addChild(hyenaDeathrattle);

        EntityTemplate dreadSteed = createMinion("Dreadsteed", 4, 1, 1, demon);
        dreadSteed.addChild(createAllySpawn("Dreadsteed", deathrattle));

        EntityTemplate cairne = createMinion("Cairne Bloodhoof", 6, 4, 5, legendary);
        cairne.addChild(createAllySpawn(createToken("Baine Bloodhoof", 4, 4, 5, legendary).getName(), deathrattle));

        EntityTemplate hauntedCreeper = createMinion("Haunted Creeper", 2, 1, 2, beast);
        String spectralSpiderDeathrattle = createAllySpawn(createToken("Spectral Spider", 1, 1, 1).getName(), deathrattle);
        hauntedCreeper.addChild(spectralSpiderDeathrattle);
        hauntedCreeper.addChild(spectralSpiderDeathrattle);

        EntityTemplate nerubianEgg = createMinion("Nerubian Egg", 2, 0, 2);
        nerubianEgg.addChild(createAllySpawn(createToken("Nerubian", 3, 4, 4).getName(), deathrattle));

        EntityTemplate harvestGolem = createMinion("Harvest Golem", 3, 2, 3, mech);
        harvestGolem.addChild(createAllySpawn(createToken("Damaged Golem", 1, 2, 1, mech).getName(), deathrattle));

        EntityTemplate belcher = createMinion("Sludge Belcher", 5, 3, 5, taunt);
        belcher.addChild(createAllySpawn(createToken("Slime", 1, 1, 2, taunt).getName(), deathrattle));

        EntityTemplate lootHoarder = createMinion("Loot Hoarder", 2, 2, 1);
        lootHoarder.addChild(createDrawEffect(1, targetOwner, deathrattle));

        EntityTemplate dancingSwords = createMinion("Dancing Swords", 3, 4, 4);
        dancingSwords.addChild(createDrawEffect(1, targetOpponent, deathrattle));

        EntityTemplate coldlight = createMinion("Coldlight Oracle", 3, 2, 2);
        coldlight.addChild(createDrawEffect(2, targetPlayers, battlecry));

        String draw1Battlecry = createDrawEffect(1, targetOwner, battlecry);

        EntityTemplate gnomishInventor = createMinion("Gnomish Inventor", 4, 2, 4);
        gnomishInventor.addChild(draw1Battlecry);

        EntityTemplate noviceEngineer = createMinion("Novice Engineer", 2, 1, 1);
        noviceEngineer.addChild(draw1Battlecry);

        EntityTemplate bladeMaster = createMinion("Injured Blademaster", 3, 4, 7);
        bladeMaster.addChild(createFilteredDamage(4, targetSelf, battlecry));

        EntityTemplate kvaldir = createMinion("Injured Kvaldir", 1, 2, 4);
        kvaldir.addChild(createFilteredDamage(3, targetSelf, battlecry));

        EntityTemplate leperGnome = createMinion("Leper Gnome", 1, 2, 1);
        leperGnome.addChild(createFilteredDamage(2, targetEnemyHeroes, deathrattle));

        EntityTemplate nightBlade = createMinion("Nightblade", 5, 4, 4);
        nightBlade.addChild(createFilteredDamage(4, targetEnemyHeroes, battlecry));

        EntityTemplate exploSheep = createMinion("Explosive Sheep", 2, 1, 1, mech);
        exploSheep.addChild(createFilteredDamage(2, targetAllMinions, deathrattle));

        EntityTemplate unstableGhoul = createMinion("Unstable Ghoul", 2, 1, 3, taunt);
        unstableGhoul.addChild(createFilteredDamage(1, targetAllMinions, deathrattle));

        EntityTemplate abomination = createMinion("Abomination", 5, 4, 4, taunt);
        abomination.addChild(createFilteredDamage(2, targetAllCharacters, deathrattle));

        EntityTemplate dreadInfernal = createMinion("Dread Infernal", 6, 6, 6, demon);
        dreadInfernal.addChild(createFilteredDamage(1, targetAllCharacters, battlecry, new TargetExcludeSelfComponent()));

        EntityTemplate baronGeddon = createMinion("Baron Geddon", 7, 7, 5, legendary);
        baronGeddon.addChild(createFilteredDamage(2, targetAllCharacters, endTurn, myTurn, new TargetExcludeSelfComponent()));

        EntityTemplate dreadScale = createMinion("Dreadscale", 3, 4, 2, legendary, beast);
        dreadScale.addChild(createFilteredDamage(1, targetAllMinions, endTurn, myTurn, new TargetExcludeSelfComponent()));

        EntityTemplate hogger = createMinion("Hogger", 6, 4, 4, legendary);
        hogger.addChild(createAllySpawn(createToken("Gnoll", 2, 2, 2, taunt).getName(), endTurn, myTurn));

        EntityTemplate obsiDestroyer = createMinion("Obsidian Destroyer", 7, 7, 7);
        obsiDestroyer.addChild(createAllySpawn(createToken("Scarab", 1, 1, 1, taunt).getName(), endTurn, myTurn));

        EntityTemplate impMaster = createMinion("Imp Master", 3, 1, 5);
        impMaster.addChild(createAllySpawn(createToken("Imp", 1, 1, 1, demon).getName(), endTurn, myTurn));
        impMaster.addChild(createFilteredDamage(1, targetSelf, endTurn, myTurn));

        EntityTemplate drBoom = createMinion("Dr. Boom", 7, 7, 7, legendary);
        EntityTemplate boomBot = createToken("Boom Bot", 1, 1, 1, mech);
        boomBot.addChild(createFilteredRandomDamage(1, 4, targetEnemyCharacters, deathrattle, new EffectTargetsSingleRandomComponent()));
        String spawnBoomBot = createAllySpawn(boomBot.getName(), battlecry);
        drBoom.addChild(spawnBoomBot);
        drBoom.addChild(spawnBoomBot);

        EntityTemplate bombLobber = createMinion("Bomb Lobber", 5, 3, 3);
        bombLobber.addChild(createFilteredDamage(4, targetEnemyMinions, battlecry, new EffectTargetsSingleRandomComponent()));

        EntityTemplate tidehunter = createMinion("Murloc Tidehunter", 2, 2, 1, murloc);
        tidehunter.addChild(createAllySpawn(createToken("Murloc Scout", 1, 1, 1, murloc).getName(), battlecry));

        EntityTemplate razorfen = createMinion("Razorfen Hunter", 3, 2, 3);
        razorfen.addChild(createAllySpawn(createToken("Boar", 1, 1, 1, beast).getName(), battlecry));

        EntityTemplate dragonMechanic = createMinion("Dragonling Mechanic", 4, 2, 4);
        dragonMechanic.addChild(createAllySpawn(createToken("Mechanical Dragonling", 1, 2, 1, mech).getName(), battlecry));

        EntityTemplate silverHandKnight = createMinion("Silver Hand Knight", 5, 4, 4);
        silverHandKnight.addChild(createAllySpawn(createToken("Squire", 1, 2, 2).getName(), battlecry));

//        EntityTemplate leeroy = createMinion("Leeroy Jenkins", 5, 6, 2, charge);
        String spawnWhelp = createEnemySpawn(/*createToken(*/"Whelp"/*, 1, 1, 1, dragon).getName()*/, battlecry);
        //        leeroy.addChild(spawnWhelp);
        //        leeroy.addChild(spawnWhelp);
    }

    private void createSpells() {
        createSpell("Flamestrike", 7, createFilteredDamage(4, targetEnemyMinions, battlecry));
        createSpell("Consecration", 4, createFilteredDamage(2, targetEnemyCharacters, battlecry));
        createSpell("Arcane Explosion", 2, createFilteredDamage(1, targetEnemyMinions, battlecry));
        createSpell("Whirlwind", 1, createFilteredDamage(1, targetAllMinions, battlecry));
        createSpell("Hellfire", 4, createFilteredDamage(3, targetAllCharacters, battlecry));
        createSpell("Flamecannon", 2, createFilteredDamage(4, targetEnemyMinions, atLeastOneTargetRequired, battlecry, new EffectTargetsSingleRandomComponent()));
        createSpell("Sinister Strike", 1, createFilteredDamage(3, targetEnemyHeroes, battlecry));

        createSpell("Starfire", 6, createDrawEffect(1, targetOwner, battlecry), createUserTargetedDamage(5, targetAllCharacters, atLeastOneTargetRequired));
        createSpell("Hammer of Wrath", 4, createDrawEffect(1, targetOwner, battlecry), createUserTargetedDamage(3, targetAllCharacters, atLeastOneTargetRequired));
        createSpell("Shiv", 2, createDrawEffect(1, targetOwner, battlecry), createUserTargetedDamage(1, targetAllCharacters, atLeastOneTargetRequired));
        createSpell("Pyroblast", 10, createUserTargetedDamage(10, targetAllCharacters, atLeastOneTargetRequired));
        createSpell("Fireball", 4, createUserTargetedDamage(6, targetAllCharacters, atLeastOneTargetRequired));
        createSpell("Darkbomb", 2, createUserTargetedDamage(3, targetAllCharacters, atLeastOneTargetRequired));
        createSpell("Arcane Shot", 1, createUserTargetedDamage(2, targetAllCharacters, atLeastOneTargetRequired));
        createSpell("Holy Smite", 1, createUserTargetedDamage(2, targetAllCharacters, atLeastOneTargetRequired));
        createSpell("Flame Lance", 5, createUserTargetedDamage(8, targetAllMinions, atLeastOneTargetRequired));

        createSpell("Arcane Intellect", 3, createDrawEffect(2, targetOwner, battlecry));
        createSpell("Sprint", 7, createDrawEffect(4, targetOwner, battlecry));
        createSpell("Fan of Knives", 3, createFilteredDamage(1, targetEnemyMinions, battlecry), createDrawEffect(1, targetOwner, battlecry));
        String randomDamage = createFilteredDamage(1, targetEnemyCharacters, battlecry, new EffectTargetsSingleRandomComponent());
        createSpell("Arcane Missles", 1, randomDamage, randomDamage, randomDamage);
    }

    private void createSpell(String name, int cost, String... effects) {
        EntityTemplate spell = createBasicSpell(name, cost);
        for (String effect : effects) {
            spell.addChild(effect);
        }
    }

    private EntityTemplate createBasicSpell(String name, int cost) {
        EntityTemplate template = new EntityTemplate();
        template.add(new NameComponent(name));
        template.add(new ManaCostComponent(cost));
        template.add(new SpellComponent());
        template.add(new CastTemplateComponent(ACTIVATION_SUMMON));
        templates.add(template);
        return template;
    }

    private String createFilteredDamage(int damage, EntityComponent[] filter, EntityComponent triggerComponent, EntityComponent... conditions) {
        EntityTemplate aoe = new EntityTemplate();
        aoe.setCollectible(false);
        aoe.add(new EffectIsTargetedComponent());
        aoe.addAll(filter);
        aoe.add(new DealDamageEffectComponent(damage));
        aoe.add(triggerComponent);
        String name = triggerName(triggerComponent) + "=>Damage" + damage;
        for (EntityComponent condition : conditions) {
            aoe.add(condition);
            name += " {" + conditionName(condition) + "}";
        }
        name += UUID.randomUUID();
        aoe.add(new NameComponent(name));
        templates.add(aoe);
        return name;
    }

    private String createUserTargetedDamage(int damage, EntityComponent[] filter, EntityComponent... conditions) {
        EntityTemplate effect = new EntityTemplate();
        effect.setCollectible(false);
        effect.add(new EffectIsTargetedComponent());
        effect.add(new EffectRequiresUserTargetsComponent());
        effect.addAll(filter);
        effect.add(new DealDamageEffectComponent(damage));
        effect.add(battlecry);
        String name = triggerName(battlecry) + "=>Damage" + damage;
        for (EntityComponent condition : conditions) {
            effect.add(condition);
            name += " {" + conditionName(condition) + "}";
        }
        name += UUID.randomUUID();
        effect.add(new NameComponent(name));
        templates.add(effect);
        return name;
    }

    private String createFilteredRandomDamage(int offset, int rngRange, EntityComponent[] filter, EntityComponent triggerComponent, EntityComponent... components) {
        EntityTemplate aoe = new EntityTemplate();
        aoe.setCollectible(false);
        aoe.add(new EffectIsTargetedComponent());
        aoe.addAll(filter);
        aoe.addAll(components);
        aoe.add(new DealRandomDamageEffectComponent(offset, rngRange));
        aoe.add(triggerComponent);
        String name = triggerName(triggerComponent) + "=>Damage" + offset + "-" + (offset + rngRange - 1);
        name += UUID.randomUUID();
        aoe.add(new NameComponent(name));
        templates.add(aoe);
        return name;
    }

    private String triggerName(EntityComponent triggerComponent) {
        return triggerComponent.getClass().getSimpleName().split("Trigger")[0];
    }

    private String conditionName(EntityComponent condition) {
        return condition.getClass().getSimpleName().split("Condition")[0];
    }

    private EntityTemplate createToken(String name, int cost, int attack, int health, EntityComponent... components) {
        EntityTemplate template = createMinion(name, cost, attack, health, components);
        template.setCollectible(false);
        return template;
    }

    private EntityTemplate createMinion(String name, int cost, int attack, int health, EntityComponent... components) {
        EntityTemplate template = createVanillaMinion(name, cost, attack, health);
        for (EntityComponent component : components) {
            template.add(component);
        }
        return template;
    }

    private EntityTemplate createVanillaMinion(String name, int cost, int attack, int health) {
        EntityTemplate template = new EntityTemplate();
        template.add(new NameComponent(name));
        template.add(new ManaCostComponent(cost));
        template.add(new AttackComponent(attack));
        template.add(new HealthComponent(health));
        template.add(new MinionComponent());
        template.add(new CastTemplateComponent(ACTIVATION_SUMMON));
        templates.add(template);
        return template;
    }

    private String createAllySpawn(String templateName, EntityComponent triggerComponent, EntityComponent... conditions) {
        EntityTemplate template = createSpawn(templateName, triggerComponent, concatenate(conditions, new MakeAllyEffectComponent()));
        return template.getName();
    }

    private String createEnemySpawn(String templateName, EntityComponent triggerComponent, EntityComponent... conditions) {
        EntityTemplate template = createSpawn(templateName, triggerComponent, concatenate(conditions, new MakeEnemyEffectComponent()));
        return template.getName();
    }

    private EntityTemplate createSpawn(String templateName, EntityComponent triggerComponent, EntityComponent... conditions) {
        EntityTemplate template = new EntityTemplate();
        template.setCollectible(false);
        template.add(new CreateSingleTargetEntityEffectComponent());
        template.add(new AttachTemplateEffectComponent(templateName));
        template.add(new BoardAttachEffectComponent());
        template.add(triggerComponent);
        String name = triggerName(triggerComponent) + "=>" + templateName;
        for (EntityComponent condition : conditions) {
            template.add(condition);
            name += " {" + conditionName(condition) + "}";
        }
        template.add(new NameComponent(name));
        templates.add(template);
        return template;
    }

    private String createDrawEffect(int numCards, EntityComponent[] filter, EntityComponent triggerComponent) {
        EntityTemplate draw = new EntityTemplate();
        draw.setCollectible(false);
        draw.add(triggerComponent);
        draw.add(new EffectIsTargetedComponent());
        draw.addAll(filter);
        draw.add(new DrawEffectComponent(numCards));
        String name = triggerName(triggerComponent) + "=>Draw" + numCards;
        name += UUID.randomUUID();
        draw.add(new NameComponent(name));
        templates.add(draw);
        return draw.getName();
    }

//    private SelectEffectTargetsComponent createRandomFilter(SelectEffectTargetsComponent filter) {
//        return new SelectEffectTargetsComponent(filter.selection.and(TargetFilters.RANDOM));
//    }
//    
//    private SelectEffectTargetsComponent createExcludeSelfFilter(SelectEffectTargetsComponent filter) {
//        return new SelectEffectTargetsComponent(filter.selection.and(TargetFilters.EXCLUDE_SELF));
//    }
    private HashMap<String, EntityTemplate> templateListToMap() {
        HashMap<String, EntityTemplate> result = new HashMap<>();
        for (EntityTemplate template : templates) {
            result.put(template.getName(), template);
        }
//        for (String template : result.keySet()) {
//            System.out.println("registered template: " + template);
//        }
        System.out.println(result.size() + " templates");
        int collectibles = 0;
        for (EntityTemplate entityTemplate : result.values()) {
            if (entityTemplate.isCollectible()) {
                collectibles++;
            }
        }
        System.out.println(collectibles + " collectibles");
        return result;
    }

    private <T> T[] concatenate(T[] a, T... b) {
        int aLen = a.length;
        int bLen = b.length;
        @SuppressWarnings("unchecked")
        T[] c = (T[]) Array.newInstance(a.getClass().getComponentType(), aLen + bLen);
        System.arraycopy(a, 0, c, 0, aLen);
        System.arraycopy(b, 0, c, aLen, bLen);
        return c;
    }
}
