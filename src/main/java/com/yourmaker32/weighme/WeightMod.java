package com.yourmaker32.weighme;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

@Mod(WeightMod.MODID)
public class WeightMod {
    public static final String MODID = "weigh_me";

    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MODID);
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MODID);
    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

    private static final String[][] WEIGHTS = {
            {"0_25", "0.25"}, {"0_5", "0.5"}, {"1", "1"}, {"2", "2"}, {"4", "4"},
            {"8", "8"}, {"16", "16"}, {"32", "32"}, {"64", "64"}, {"128", "128"}, {"256", "256"}
    };

    public static final List<DeferredBlock<Block>> WEIGHT_BLOCKS = new ArrayList<>();
    public static final List<DeferredItem<WeightBlockItem>> WEIGHT_ITEMS = new ArrayList<>();

    static {
        for (String[] w : WEIGHTS) {
            String id = "weight_block_" + w[0];
            String label = w[1];
            DeferredBlock<Block> block = BLOCKS.registerSimpleBlock(id,
                    BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).sound(SoundType.NETHERITE_BLOCK));
            DeferredItem<WeightBlockItem> item = ITEMS.register(id,
                    () -> new WeightBlockItem(block.get(), new Item.Properties(), label));
            WEIGHT_BLOCKS.add(block);
            WEIGHT_ITEMS.add(item);
        }
    }

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> WEIGHT_TAB =
            CREATIVE_TABS.register("weights", () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup." + MODID))
                    .icon(() -> new ItemStack(WEIGHT_ITEMS.get(0).get()))
                    .displayItems((params, output) -> WEIGHT_ITEMS.forEach(i -> output.accept(i.get())))
                    .build());

    public WeightMod(IEventBus modEventBus, ModContainer modContainer) {
        BLOCKS.register(modEventBus);
        ITEMS.register(modEventBus);
        CREATIVE_TABS.register(modEventBus);
    }
}
