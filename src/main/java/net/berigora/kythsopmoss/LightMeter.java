package net.berigora.kythsopmoss;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class LightMeter extends Item {

	public LightMeter() {
		setRegistryName("lightmeter");
		setUnlocalizedName(getRegistryName().toString());
		setCreativeTab(CreativeTabs.FOOD);
		
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer playerIn, EnumHand handIn)
    {
		if(!world.isRemote) {
			BlockPos p = playerIn.getPosition();
			int skylightsubtracted = world.getSkylightSubtracted();
			int lightforblock = world.getLightFor(EnumSkyBlock.BLOCK, p);
			int lightforsky = world.getLightFor(EnumSkyBlock.SKY, p);
			int effectiveskylight = Math.max(lightforsky - skylightsubtracted, 0); 
			
			playerIn.sendMessage(new TextComponentString("Light Levels:"));
			playerIn.sendMessage(new TextComponentString("  Sky visibility: "+ lightforsky));
			playerIn.sendMessage(new TextComponentString("  Sky:   " + effectiveskylight ));
			playerIn.sendMessage(new TextComponentString("  Block: " + lightforblock));
			playerIn.sendMessage(new TextComponentString("  Eff:   " + Math.max(lightforblock,  effectiveskylight)));
			playerIn.sendMessage(new TextComponentString("  SLS:   " +  skylightsubtracted));
		}
			
        return new ActionResult<ItemStack>(EnumActionResult.PASS, playerIn.getHeldItem(handIn));
    }
}
