package com.gladurbad.medusa.packet.processor;

import com.gladurbad.medusa.data.PlayerData;
import io.github.retrooper.packetevents.packetwrappers.out.entityteleport.WrappedPacketOutEntityTeleport;
import io.github.retrooper.packetevents.packetwrappers.out.entityvelocity.WrappedPacketOutEntityVelocity;
import com.gladurbad.medusa.packet.Packet;
import io.github.retrooper.packetevents.packetwrappers.out.explosion.WrappedPacketOutExplosion;
import io.github.retrooper.packetevents.packetwrappers.out.position.WrappedPacketOutPosition;
import io.github.retrooper.packetevents.packetwrappers.out.transaction.WrappedPacketOutTransaction;
import org.bukkit.Bukkit;

public class SendingPacketProcessor  {

    public void handle(final PlayerData data, final Packet packet) {
        if (packet.isVelocity()) {
            final WrappedPacketOutEntityVelocity wrapper = new WrappedPacketOutEntityVelocity(packet.getRawPacket());
            if (wrapper.getEntity() == data.getPlayer()) {
                /*Bukkit.broadcastMessage(data.getPlayer().getName() + " took velocity.");*/
                data.getVelocityProcessor().handle(wrapper.getVelocityX(), wrapper.getVelocityY(), wrapper.getVelocityZ());
            }
        }
        if (packet.isExplosion()) {
            final WrappedPacketOutExplosion wrapper = new WrappedPacketOutExplosion(packet.getRawPacket());
            data.getVelocityProcessor().handle(wrapper.getPlayerMotionX(), wrapper.getPlayerMotionY(), wrapper.getPlayerMotionZ());
        }
        if (packet.isTeleport()) {
            data.getPositionProcessor().handleTeleport();
        }
        data.getChecks().forEach(check -> check.handle(packet));
    }
}
