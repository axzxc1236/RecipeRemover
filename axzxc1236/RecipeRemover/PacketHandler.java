package axzxc1236.RecipeRemover;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import axzxc1236.RecipeRemover.RecipeHandler;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;

public class PacketHandler implements IPacketHandler {

    @Override
    public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player) {
        if (!packet.channel.equals(RecipeRemover.CHANNEL)) return;
        if (packet.data == null) return;
        DataInputStream dis = new DataInputStream(new ByteArrayInputStream(packet.data));
        try {
            while (dis.available() != 0)
                RecipeHandler.addID(dis.readUTF().trim());
        } catch (Exception e) {
            throw new RuntimeException(e); // Catch a major error
        }
        RecipeHandler.removeRecipes();
    }

    public static Packet250CustomPayload buildPacket(String... data) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(baos);

        try {
            for (String i : data) {
                out.writeUTF(i);
            }
        } catch (IOException e) {
            e.printStackTrace(System.err);
        }
        return new Packet250CustomPayload(RecipeRemover.CHANNEL, baos.toByteArray());
    }
}