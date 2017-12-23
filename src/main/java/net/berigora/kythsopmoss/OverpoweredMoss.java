package net.berigora.kythsopmoss;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.library.TinkerRegistry;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;

public class OverpoweredMoss extends Item {
	public OverpoweredMoss() {
		setRegistryName("overpowered_moss");
		setUnlocalizedName(getRegistryName().toString());
		setCreativeTab(TinkerRegistry.tabGeneral);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	@SuppressWarnings("deprecation")
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		tooltip.add(TextFormatting.ITALIC + I18n.translateToLocalFormatted("item.kythsopmoss:overpowered_moss.tooltip"));
		tooltip.add(" ");
		tooltip.add(I18n.translateToLocalFormatted("modifier.kyths_regenerating_moss.name"));
	}
}
