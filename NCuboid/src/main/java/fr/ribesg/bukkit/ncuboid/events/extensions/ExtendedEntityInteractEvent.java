package fr.ribesg.bukkit.ncuboid.events.extensions;

import fr.ribesg.bukkit.ncuboid.beans.CuboidDb;
import fr.ribesg.bukkit.ncuboid.beans.GeneralCuboid;
import fr.ribesg.bukkit.ncuboid.events.AbstractExtendedEvent;
import org.bukkit.event.entity.EntityInteractEvent;

import java.util.Set;

public class ExtendedEntityInteractEvent extends AbstractExtendedEvent {

	private final GeneralCuboid      cuboid;
	private final Set<GeneralCuboid> cuboids;

	public ExtendedEntityInteractEvent(final CuboidDb db, final EntityInteractEvent event) {
		super(event);
		cuboids = db.getAllByLocation(event.getBlock().getLocation());
		cuboid = db.getPrior(cuboids);
	}

	public GeneralCuboid getCuboid() {
		return cuboid;
	}

	public Set<GeneralCuboid> getCuboids() {
		return cuboids;
	}
}