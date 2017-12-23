package net.berigora.kythsopmoss;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.FMLLog;

@Mod(modid = OpMossMod.MODID, version = OpMossMod.VERSION, useMetadata = true)
public class OpMossMod
{
	public final static String MODID = "kythsopmoss";
	public final static String VERSION = "0.1";
	
	public static final Logger log = LogManager.getLogger(MODID);
	
	public static final Item OpMoss = new OverpoweredMoss();
	public static final Item LightMeter = new LightMeter();
	public static final ModRegeneratingMoss RegenerationModifier = new ModRegeneratingMoss();
	
	@SidedProxy(clientSide = "net.berigora.kythsopmoss.ClientRegisterer", serverSide = "net.berigora.kythsopmoss.Registerer")
	public static Registerer registration;
	
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        log.info("Let's remove some foam from up in this thing");
    }
    
}
