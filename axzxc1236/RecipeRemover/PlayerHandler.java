package axzxc1236.RecipeRemover;

import net.minecraft.network.INetworkManager;
import net.minecraft.network.NetLoginHandler;
import net.minecraft.network.packet.NetHandler;
import net.minecraft.network.packet.Packet1Login;
import net.minecraft.server.MinecraftServer;
import cpw.mods.fml.common.network.IConnectionHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;

public class PlayerHandler implements IConnectionHandler {


    @Override
    public void playerLoggedIn(Player player, NetHandler netHandler, INetworkManager manager) {
        System.out.println("Synchronizing client <-> server removed recipes");
        PacketDispatcher.sendPacketToPlayer(PacketHandler.buildPacket(ConfigurationHandler.getLines().toArray(new String[ConfigurationHandler.getLines().size()])), player);
    }

    @Override
    public String connectionReceived(NetLoginHandler netHandler, INetworkManager manager) {
        return null;
    }

    @Override
    public void connectionOpened(NetHandler netClientHandler, String server, int port, INetworkManager manager) {

    }

    @Override
    public void connectionOpened(NetHandler netClientHandler, MinecraftServer server, INetworkManager manager) {

    }

    @Override
    public void connectionClosed(INetworkManager manager) {

    }

    @Override
    public void clientLoggedIn(NetHandler clientHandler, INetworkManager manager, Packet1Login login) {

    }
}