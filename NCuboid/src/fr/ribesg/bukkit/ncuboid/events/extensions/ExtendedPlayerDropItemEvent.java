package fr.ribesg.bukkit.ncuboid.events.extensions;

import lombok.Getter;

import org.bukkit.event.player.PlayerDropItemEvent;

import fr.ribesg.bukkit.ncuboid.beans.CuboidDB;
import fr.ribesg.bukkit.ncuboid.beans.PlayerCuboid;
import fr.ribesg.bukkit.ncuboid.events.AbstractExtendedEvent;

public class ExtendedPlayerDropItemEvent extends AbstractExtendedEvent {

    @Getter PlayerCuboid playerCuboid;

    public ExtendedPlayerDropItemEvent(final CuboidDB db, final PlayerDropItemEvent event) {
        super(event);
        playerCuboid = db.getPriorByLoc(event.getPlayer().getLocation());
    }

}
