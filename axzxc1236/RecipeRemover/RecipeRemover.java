package axzxc1236.RecipeRemover;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.Mod.ServerStarting;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;

@Mod(modid = RecipeRemover.MOD_ID, name = RecipeRemover.MOD_NAME, version = "0.4", useMetadata = true)
@NetworkMod(clientSideRequired = true, serverSideRequired = false, channels = {RecipeRemover.CHANNEL}, packetHandler = PacketHandler.class)
public class RecipeRemover {

    public static final String MOD_ID = "RecipeRemover";
    public static final String MOD_NAME = "Recipe Remover";
    public static final String CHANNEL = "RecipeRemover";

    @Instance(RecipeRemover.MOD_ID)
    public static RecipeRemover instance;

    private ConfigurationHandler config;

    @PreInit
    public void preInit(FMLPreInitializationEvent event) {
        config = new ConfigurationHandler(event.getSuggestedConfigurationFile());
        config.readConfig();
    }

    @Init
    public void init(FMLInitializationEvent event) {
        NetworkRegistry.instance().registerConnectionHandler(new PlayerHandler());
    }

    @ServerStarting
    public void serverStarting(FMLServerStartingEvent e) {
        if (FMLCommonHandler.instance().getEffectiveSide().isServer())
            RecipeHandler.removeRecipes();
    }

}