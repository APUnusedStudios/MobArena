package net.apstudios.mobarena.events;

import net.apstudios.mobarena.MobArena;
import net.apstudios.mobarena.obj.Arena;
import net.apstudios.mobarena.obj.Drop;
import net.apstudios.mobarena.obj.Mob;
import net.apstudios.mobarena.obj.Wave;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class EntityDeath implements Listener {

    private MobArena plugin;

    public EntityDeath(MobArena plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        if(plugin.getUtil().doesMobExist(event.getEntity().getEntityId())) {
            List<Object> mobInfo = plugin.getUtil().getInfoFromMob(event.getEntity().getEntityId());
            Mob mob = (Mob) mobInfo.get(0);
            Wave wave = (Wave) mobInfo.get(1);
            Arena arena = (Arena) mobInfo.get(2);

            event.getDrops().clear();
            for(Drop drop : mob.getDrops()) {
                event.getDrops().add(drop.getDropItemStack());
            }

            event.setDroppedExp(0);
            if(mob.doesMobDropExp()) {
                event.setDroppedExp(mob.getExp());
            }

            wave.removeEntityFromAliveMobsList(event.getEntity().getEntityId());
            if(wave.getAliveMobs().size() == 0) {
                wave.endWave();
            }
        }
    }

}
