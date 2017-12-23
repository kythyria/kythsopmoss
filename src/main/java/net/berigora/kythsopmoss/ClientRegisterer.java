package net.berigora.kythsopmoss;

import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ClientRegisterer extends Registerer {
	public ClientRegisterer() { }
	
	@SubscribeEvent
	public void registerItems(ModelRegistryEvent event) {
		Item item = OpMossMod.OpMoss;
		ModelResourceLocation mrl = new ModelResourceLocation(item.getRegistryName().toString(), "inventory");
		ModelBakery.registerItemVariants(item, mrl);
		ModelLoader.setCustomMeshDefinition(item, new ItemMeshDefinition() {
	        public ModelResourceLocation getModelLocation(ItemStack stack) {
	            return mrl;
	        }
	    });
	}
}
