package com.epiicthundercat.raft.registry.recipe;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.common.collect.Maps;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;


public class RecipeSeparator {

	private static final RecipeSeparator PULVERIZING_BASE = new RecipeSeparator();
    private final Map<ItemStack, ItemStack> pulvList = Maps.<ItemStack, ItemStack>newHashMap();
    private final Map<ItemStack, Float> experienceList = Maps.<ItemStack, Float>newHashMap();

    public static RecipeSeparator instance()
    {
        return PULVERIZING_BASE;
    }

    public RecipeSeparator()
    {
  /*  	addPulverizingRecipeForBlock(Blocks.COBBLESTONE, new ItemStack(Blocks.GRAVEL), 1.0F);
    	addPulverizingRecipeForBlock(Blocks.GRAVEL, new ItemStack(Blocks.SAND), 1.0F);
    	addPulverizingRecipeForBlock(Blocks.STONE, new ItemStack(Blocks.GRAVEL), 1.0F);
    	addPulverizingRecipeForBlock(Blocks.IRON_ORE, new ItemStack(RItems.IronDust, 2), 1.0F);
    	addPulverizingRecipeForBlock(Blocks.GOLD_ORE, new ItemStack(RItems.GoldDust, 2), 1.0F);
    	addPulverizingRecipeForBlock(Blocks.COAL_ORE, new ItemStack(RItems.CoalDust, 2), 1.0F);
    	addPulverizingRecipeForBlock(Blocks.DIAMOND_ORE, new ItemStack(RItems.DiamondDust, 2), 1.0F);
    	
    	addPulverizingRecipeForItem(Items.IRON_INGOT, new ItemStack(RItems.IronDust), 1.0F);
    	addPulverizingRecipeForItem(Items.COAL, new ItemStack(RItems.CoalDust), 1.0F);
    	addPulverizingRecipeForItem(Items.GOLD_INGOT, new ItemStack(RItems.GoldDust), 1.0F);
    	addPulverizingRecipeForItem(Items.DIAMOND, new ItemStack(RItems.DiamondDust), 1.0F);

        addPulverizingRecipeForBlock(Blocks.LAPIS_ORE, new ItemStack(Items.DYE, 12, 4), 1.0F);
        addPulverizingRecipeForBlock(Blocks.EMERALD_ORE, new ItemStack(Items.EMERALD, 2), 1.0F);
        addPulverizingRecipeForBlock(Blocks.QUARTZ_ORE, new ItemStack(Items.QUARTZ, 4), 1.0F);
        addPulverizingRecipeForBlock(Blocks.GLOWSTONE, new ItemStack(Items.GLOWSTONE_DUST, 4), 1.0F);
        addPulverizingRecipeForBlock(Blocks.WOOL, new ItemStack(Items.STRING, 4), 1.0F);
*/
        String[] oreDictionary = OreDictionary.getOreNames();

        for(String entry : oreDictionary) {
            if(entry.startsWith("dust")) {
                List<ItemStack> oreList = OreDictionary.getOres(entry.replaceFirst("dust", "ore"));
                if(!oreList.isEmpty()) {
                    List<ItemStack> dustList = OreDictionary.getOres(entry);
                    if(!dustList.isEmpty())
                        addPulverizingRecipe(getOreDict(entry.replaceFirst("dust", "ore")), new ItemStack(dustList.get(0).getItem(), 2, dustList.get(0).getItemDamage()), 1.0F);
                }
            } else if(entry.startsWith("ingot")) {
                List<ItemStack> dustList = OreDictionary.getOres(entry.replaceFirst("ingot", "dust"));
                if(!dustList.isEmpty()) {
                    addPulverizingRecipe(getOreDict(entry), dustList.get(0).copy(), 1.0F);
                }
            }
        }
    }

    public void addPulverizingRecipeForBlock(Block input, ItemStack output, float experience)
    {
        this.addPulverizingRecipeForItem(Item.getItemFromBlock(input), output, experience);
    }

    public void addPulverizingRecipeForItem(Item input, ItemStack output, float experience)
    {
        this.addPulverizingRecipe(new ItemStack(input, 1, 32767), output, experience);
    }

    public void addPulverizingRecipe(ItemStack input, ItemStack output, float experience)
    {
        if (getPulverizingResult(input) != null) { net.minecraftforge.fml.common.FMLLog.info("Ignored pulverizing recipe with conflicting input: " + input + " = " + output); return; }
        this.pulvList.put(input, output);
        this.experienceList.put(output, Float.valueOf(experience));
    }


    @Nullable
    public ItemStack getPulverizingResult(ItemStack stack)
    {
        for (Entry<ItemStack, ItemStack> entry : this.pulvList.entrySet())
        {
            if (this.compareItemStacks(stack, (ItemStack)entry.getKey()))
            {
                return (ItemStack)entry.getValue();
            }
        }

        return null;
    }

    private boolean compareItemStacks(ItemStack stack1, ItemStack stack2)
    {
        return stack2.getItem() == stack1.getItem() && (stack2.getMetadata() == 32767 || stack2.getMetadata() == stack1.getMetadata());
    }

    public Map<ItemStack, ItemStack> getPulverizingList()
    {
        return this.pulvList;
    }

    public float getPulverizingExperience(ItemStack stack)
    {
        float ret = stack.getItem().getSmeltingExperience(stack);
        if (ret != -1) return ret;

        for (Entry<ItemStack, Float> entry : this.experienceList.entrySet())
        {
            if (this.compareItemStacks(stack, (ItemStack)entry.getKey()))
            {
                return ((Float)entry.getValue()).floatValue();
            }
        }

        return 0.0F;
    }

    public static ItemStack getOreDict(String oreIdName) {
        List<ItemStack> ores = OreDictionary.getOres(oreIdName);
        if (ores != null && ores.size() > 0)
            return ores.get(0).copy();
        return null;
    }


}

