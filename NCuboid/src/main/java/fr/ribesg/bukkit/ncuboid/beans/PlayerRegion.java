/***************************************************************************
 * Project file:    NPlugins - NCuboid - PlayerRegion.java                 *
 * Full Class name: fr.ribesg.bukkit.ncuboid.beans.PlayerRegion            *
 *                                                                         *
 *                Copyright (c) 2012-2014 Ribesg - www.ribesg.fr           *
 *   This file is under GPLv3 -> http://www.gnu.org/licenses/gpl-3.0.txt   *
 *    Please contact me at ribesg[at]yahoo.fr if you improve this file!    *
 ***************************************************************************/

package fr.ribesg.bukkit.ncuboid.beans;

import fr.ribesg.bukkit.ncore.common.ChunkCoord;
import fr.ribesg.bukkit.ncore.common.NLocation;
import fr.ribesg.bukkit.ncore.config.UuidDb;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public abstract class PlayerRegion extends GeneralRegion {

	public static enum RegionState {
		/**
		 * Normal Region
		 */
		NORMAL,

		/**
		 * First point selected
		 */
		TMPSTATE1,

		/**
		 * All points selected, waiting for "/cuboid create" command
		 */
		TMPSTATE2
	}

	private String          regionName;
	private UUID            ownerId;
	private RegionState     state;
	private long            totalSize;
	private Set<ChunkCoord> chunks;

	/**
	 * Create a new Region, when user select points etc
	 */
	public PlayerRegion(final String regionName, final UUID ownerId, final String worldName, final RegionType type) {
		super(worldName, type, 1);
		setRegionName(regionName);
		setOwnerId(ownerId);
		setState(RegionState.TMPSTATE1);
	}

	/**
	 * Create a Region from a save
	 */
	public PlayerRegion(final String regionName, final UUID ownerId, final String worldName, final RegionState state, final long totalSize, final RegionType type, final Rights rights, final int priority, final Flags flags, final Attributes flagAtts) {

		super(worldName, type, rights, priority, flags, flagAtts);
		setRegionName(regionName);
		setOwnerId(ownerId);
		setState(state);
		setTotalSize(totalSize);
	}

	/**
	 * Called on a Selection region to transform it into an actual Region
	 *
	 * @param regionName the name of the new Region
	 */
	public void create(final String regionName) {
		setRegionName(regionName);
		setState(RegionState.NORMAL);
	}

	// Location check
	@Override
	public boolean contains(final NLocation loc) {
		return loc.getWorldName().equals(getWorldName()) && contains(loc.getX(), loc.getY(), loc.getZ());
	}

	public abstract boolean contains(final double x, final double y, final double z);

	public abstract boolean overlaps(final PlayerRegion r);

	// Info
	public String getInfoLine() {
		return "- " + getRegionName() + " (" + UuidDb.getName(this.ownerId) + ") " + getSizeString();
	}

	public abstract String getSizeString();

	public boolean isOwner(final Player player) {
		return isOwnerId(player.getUniqueId());
	}

	public boolean isOwnerId(final UUID id) {
		return this.ownerId.equals(id);
	}

	public Set<ChunkCoord> getChunks() {
		return chunks;
	}

	protected void setChunks(final Set<ChunkCoord> chunks) {
		this.chunks = chunks;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(final String regionName) {
		this.regionName = regionName;
	}

	public UUID getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(final UUID ownerId) {
		this.ownerId = ownerId;
	}

	public RegionState getState() {
		return state;
	}

	protected void setState(final RegionState state) {
		this.state = state;
	}

	public abstract long getMaxLength();

	public long getTotalSize() {
		return totalSize;
	}

	protected void setTotalSize(final long totalSize) {
		this.totalSize = totalSize;
	}

	@Override
	public boolean isUser(final Player player) {
		return isOwnerId(player.getUniqueId()) || super.isUser(player);
	}

	@Override
	public boolean isUserId(final UUID id) {
		return isOwnerId(id) || super.isUserId(id);
	}

	@Override
	public boolean isAdmin(final Player player) {
		return isOwnerId(player.getUniqueId()) || super.isAdmin(player);
	}

	@Override
	public boolean isAdminId(final UUID id) {
		return isOwnerId(id) || super.isAdminId(id);
	}

	@Override
	public Set<UUID> getUsers() {
		final Set<UUID> users = super.getUsers();
		final Set<UUID> result = new HashSet<>();
		if (users != null) {
			result.addAll(users);
		}
		result.add(getOwnerId());
		return result;
	}

	@Override
	public Set<UUID> getAdmins() {
		final Set<UUID> admins = super.getAdmins();
		final Set<UUID> result = new HashSet<>();
		if (admins != null) {
			result.addAll(admins);
		}
		result.add(getOwnerId());
		return result;
	}
}
