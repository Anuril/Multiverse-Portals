package com.onarandombox.MultiversePortals;

import org.bukkit.Location;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerMoveEvent;

import com.onarandombox.MultiverseCore.MVPlayerSession;
import com.onarandombox.MultiverseCore.MVWorld;
import com.onarandombox.utils.Destination;
import com.onarandombox.utils.DestinationType;

public class MVPPlayerListener extends PlayerListener {
    private MultiversePortals plugin;
    public MVPPlayerListener(MultiversePortals plugin) {
        this.plugin = plugin;
    }
    
    @Override
    public void onPlayerMove(PlayerMoveEvent event) {
        MVPlayerSession ps = this.plugin.core.getPlayerSession(event.getPlayer());
        
        // If the location is stale, ie: the player isn't actually moving xyz coords, they're looking around
        if(ps != null && ps.isStaleLocation()) {
            return;
        }
        
        // Otherwise, they actually moved. Check to see if their loc is inside a portal!
        MVPortal portal = this.plugin.getPortalUtils().isPortal(event.getPlayer(), event.getTo());
        if(portal != null) {
            System.out.print("I FOUND A PORTAL: " + portal);
            //TODO: Money
            Destination d = portal.getDestination();
            Location l = null;
            if(d.getType() == DestinationType.World) {
                
                if(this.plugin.core.isMVWorld(d.getName())) {
                    MVWorld w = this.plugin.core.getMVWorld(d.getName());
                    l = w.getCBWorld().getSpawnLocation();
                } else if(this.plugin.getServer().getWorld(d.getName()) != null) {
                    l = this.plugin.getServer().getWorld(d.getName()).getSpawnLocation();
                }
            } else if(d.getType() == DestinationType.Portal) {
                
            }
            
            if(l == null) {
                return;
            }
            event.getPlayer().teleport(l);
        }
    }

}
