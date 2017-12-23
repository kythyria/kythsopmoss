package net.berigora.kythsopmoss;

import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Registerer {
	public Registerer() {
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	@SubscribeEvent
    public void registerItems(RegistryEvent.Register<Item> event) {
    	event.getRegistry().register(OpMossMod.OpMoss);
    	//event.getRegistry().register(OpMossMod.LightMeter);
    	
    	OpMossMod.RegenerationModifier.addItem(OpMossMod.OpMoss, 1, 1);
    	OpMossMod.log.info("Registered the moss");
    }
}
