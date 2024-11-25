package net.jasonyess.mendingvillagermod.villager;

import com.google.common.collect.ImmutableSet;
import net.jasonyess.mendingvillagermod.MendingVillagerMod;
import net.jasonyess.mendingvillagermod.block.ModBlocks;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.lang.reflect.InvocationTargetException;

public class ModVillagers {
    public static final DeferredRegister<PoiType> POI_TYPES =
            DeferredRegister.create(ForgeRegistries.POI_TYPES, MendingVillagerMod.MOD_ID);
    public static final DeferredRegister<VillagerProfession> VILLAGER_PROFESSIONS =
            DeferredRegister.create(ForgeRegistries.VILLAGER_PROFESSIONS, MendingVillagerMod.MOD_ID);

    public static final RegistryObject<PoiType> REFINED_BOOKSHELF_POI = POI_TYPES.register("refined_bookshelf_poi",
            () -> new PoiType(ImmutableSet.copyOf(ModBlocks.REFINED_BOOKSHELF_BLOCK.get().getStateDefinition().getPossibleStates()),
                    1, 1));

    public static final RegistryObject<VillagerProfession> MENDER = VILLAGER_PROFESSIONS.register("mender",
            () -> new VillagerProfession("mender", x -> x.get() == REFINED_BOOKSHELF_POI.get(),
                    x -> x.get() == REFINED_BOOKSHELF_POI.get(), ImmutableSet.of(), ImmutableSet.of(),
                    SoundEvents.VILLAGER_WORK_LIBRARIAN));

    public static void registerPOIs() {
        try {
            ObfuscationReflectionHelper.findMethod(PoiType.class,
                    "registerBlockStates", PoiType.class).invoke(null, REFINED_BOOKSHELF_POI.get());
        } catch (InvocationTargetException | IllegalAccessException exception) {
            exception.printStackTrace();
        }
    }

    public static void register(IEventBus eventBus) {
        POI_TYPES.register(eventBus);
        VILLAGER_PROFESSIONS.register(eventBus);
    }
}
