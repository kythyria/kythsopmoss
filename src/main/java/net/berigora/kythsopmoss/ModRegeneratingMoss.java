package net.berigora.kythsopmoss;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import slimeknights.tconstruct.library.modifiers.ModifierNBT;
import slimeknights.tconstruct.library.modifiers.ModifierTrait;
import slimeknights.tconstruct.library.utils.ModifierTagHolder;
import slimeknights.tconstruct.library.utils.ToolHelper;

public class ModRegeneratingMoss extends ModifierTrait {
	
	// factor for how long it takes to transition out of broken state.
	public static final int BASE_HEAL_TIMEOUT = 4 * 20; // four seconds
	public static final int HEAL_TIMEOUT_WEAKNESS_PENALTY = 1000;
	public static final float HEAL_TIMEOUT_BROKEN_PENALTY_FACTOR = 3;
	public static final int AVERAGE_FULL_RECOVERY_TIME = 20 * 60 * 8;
	public static final int HEAL_PULSE_TIME = 20; // do healing once a second;
	
	public static final String TAG_LAST_DAMAGE_TIMESTAMP = "last_damage_timestamp";
	
	public ModRegeneratingMoss() {
		super("kyths_regenerating_moss", 0x5ad8e4, 3, 0);
		
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	@Override
	public void onUpdate(ItemStack tool, World world, Entity ent, int itemSlot, boolean isSelected) {
		
		if(world.isRemote || !(ent instanceof EntityLivingBase)) { return; }
		
		EntityLivingBase player = (EntityLivingBase)ent;
		
		// only in the hotbar of a player
		// must be in hotbar or offhand for players
		if(player instanceof EntityPlayer
		&& !InventoryPlayer.isHotbar(itemSlot)
		&& ((EntityPlayer) player).getHeldItemOffhand() != tool) {
			return;
		}
		
		// Don't heal in use.
		if(((EntityPlayer) player).getHeldItemMainhand() == tool) {
			return;
		}
		
		
		// Mending Moss heals 3 every 7.5s at most.
		// Ecological heals 1 every 40s on average.
		// Maximum durability is something like 1636, so MM takes 12270s even if you had enough xp.
		// Ecological is lolno. Three and a half hours is bad enough.
		// We should heal *massively* faster when the tool has a lot of durability, but drop down
		// to a minimum when the light level is low (and about 2/3 rate in torchlight
		// The idea is, it takes REPAIR_TIME ticks in skylight >= 11 or so, longer in dimmer light.
		// The more durable your tool is, the quicker it transitions out of broken.
		
		
		// These are *solar-powered* nanites, so how fast we heal depends on the light level
		/*
		int blocklight = world.getLightFor(EnumSkyBlock.BLOCK, pos);
		int skylight = Math.max(world.getLightFor(EnumSkyBlock.SKY, pos) - world.getSkylightSubtracted(), 0);*/ 
		
		//------------
		// If the tool is damaged it takes a while to start repairing. How long depends on durability and if broken.
		// 
		
		int currentDurability = ToolHelper.getCurrentDurability(tool);
		int maxDurability = ToolHelper.getMaxDurability(tool);
		
		// If tool is unbroken, don't need to do anything.
		if(currentDurability >= maxDurability) { return; }
		
		int delay = BASE_HEAL_TIMEOUT;
		
		delay += Math.max(HEAL_TIMEOUT_WEAKNESS_PENALTY - maxDurability, 0);
		
		if(ToolHelper.isBroken(tool)) {
			delay = (int)Math.floor(delay * HEAL_TIMEOUT_BROKEN_PENALTY_FACTOR);
		}
		
		ModifierTagHolder modtag = ModifierTagHolder.getModifier(tool, getModifierIdentifier());
		Data data = modtag.getTagData(Data.class);
		
		if((world.getTotalWorldTime() - data.lastDamageTimestamp) < delay ) {
			return;
		}
		
		double healspertick = (double)maxDurability / (double)AVERAGE_FULL_RECOVERY_TIME;
		
		// Light is a factor: full skylight heals at max rate. Full blocklight heals at 0.75x.
		BlockPos pos = player.getPosition();
		double skylightfactor = Math.max(world.getLightFor(EnumSkyBlock.SKY, pos) - world.getSkylightSubtracted(), 1) / 15.0;
		double blocklightfactor = world.getLightFor(EnumSkyBlock.BLOCK, pos) / 15.0;
		double lightfactor = Math.max(skylightfactor, blocklightfactor * 0.75);
		
		healspertick *= lightfactor;
		
		if (healspertick >= 1) {
			ToolHelper.repairTool(tool, (int)Math.round(healspertick), player);
		}
		else {
			long ticksperheal = (long)Math.ceil(1/healspertick);
			if(world.getTotalWorldTime() % ticksperheal == 0) {
				ToolHelper.repairTool(tool, 1, player);
			}
		}
	}
	
	public int onToolDamage(ItemStack tool, int damage, int newDamage, EntityLivingBase entity) {
		ModifierTagHolder modtag = ModifierTagHolder.getModifier(tool, getModifierIdentifier());
		Data data = modtag.getTagData(Data.class);
		
		data.lastDamageTimestamp = entity.world.getTotalWorldTime();
		
		modtag.save();
		return newDamage;
	}
	
	public static class Data extends ModifierNBT {
		public long lastDamageTimestamp;
		
		@Override
	    public void read(NBTTagCompound tag) {
			super.read(tag);
			lastDamageTimestamp = tag.getLong(TAG_LAST_DAMAGE_TIMESTAMP); 
		}
		
		@Override
	    public void write(NBTTagCompound tag) {
			super.write(tag);
			tag.setLong(TAG_LAST_DAMAGE_TIMESTAMP, lastDamageTimestamp);
		}
	}
}
