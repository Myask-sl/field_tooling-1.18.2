package foo.myask.field_tooling.items;

import foo.myask.field_tooling.FieldTooling;
import foo.myask.field_tooling.tags.ModTags;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.*;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.common.Tags;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashMap;
import java.util.function.Consumer;


public class FieldToolParts extends RecipeProvider {
    public static final DeferredRegister<Item> ITEMS
            = DeferredRegister.create(ForgeRegistries.ITEMS, FieldTooling.MOD_ID);
    public static final CreativeModeTab TAB_HEADS = new CreativeModeTab("tool_heads") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(heads.get("iron")[2].get());
        }
    };
    public static HashMap<String, RegistryObject<Item>[]> heads = new HashMap<>();

    public FieldToolParts(DataGenerator p_125973_) {
        super(p_125973_);
    }
    public static ResourceLocation rLFrom(String word){
        return new ResourceLocation(FieldTooling.MOD_ID,word);
    }
    public static void registerHeads(String mat) {
        RegistryObject<Item>[] newheads = new RegistryObject[4];
        newheads[0] = ITEMS.register(mat + "_sword_blade", () -> new Item(new Item.Properties().tab(TAB_HEADS)));
        newheads[1] = ITEMS.register(mat + "_pickaxe_head", () -> new Item(new Item.Properties().tab(TAB_HEADS)));
        newheads[2] = ITEMS.register(mat + "_axe_head", () -> new Item(new Item.Properties().tab(TAB_HEADS)));
        newheads[3] = ITEMS.register(mat + "_hoe_blade", () -> new Item(new Item.Properties().tab(TAB_HEADS)));
        heads.put(mat,newheads);
    }
    public static void registerHeadRecipes(String mat, Item swd, Item pick, Item axe, Item hoe, Item shovel, TagKey<Item> ingredient, Consumer<FinishedRecipe> recipeConsumer, boolean sword){
        RegistryObject<Item>[] newheads = heads.get(mat);
        if (sword) swordBlade(recipeConsumer,swd,newheads[0].get(),ingredient,mat);
        pickaxeHead(recipeConsumer,pick,newheads[1].get(),ingredient,mat,newheads[0].get());
        axeHead(recipeConsumer,axe,newheads[2].get(),ingredient,mat);
        hoeBlade(recipeConsumer,hoe,newheads[3].get(),ingredient,mat,newheads[0].get());
        shovelBlade(recipeConsumer,shovel,ingredient,mat);
    }
    public static void registerHeadRecipes(String mat, Item swd, Item pick, Item axe, Item hoe, Item shovel, TagKey<Item> material, Consumer<FinishedRecipe> recipeConsumer){
        registerHeadRecipes(mat,swd,pick,axe,hoe,shovel,material,recipeConsumer,true);
    }
    public static void swordBlade(Consumer<FinishedRecipe> recipeConsumer, Item tool, Item head, TagKey<Item> mat, String word){
        ShapedRecipeBuilder.shaped(head)
                .pattern("A ")
                .pattern(" A")
                .define('A', mat)
                .unlockedBy("has_"+word,has(mat))
                .save(recipeConsumer,rLFrom(word+"_sword_blade"));
        ShapedRecipeBuilder.shaped(tool)
                .pattern("H")
                .pattern("R")
                .define('H', head).define('R', Tags.Items.RODS)
                .unlockedBy("has_"+word+"_sword_blade",has(head))
                .save(recipeConsumer, rLFrom(word+"_sword_from_head_and_rod"));
    }
    public static void pickaxeHead(Consumer<FinishedRecipe> recipeConsumer, Item tool, Item head, TagKey<Item> mat, String word, Item swdbld){
        ShapedRecipeBuilder.shaped(head)
                .pattern("H ")
                .pattern(" A")
                .define('H', swdbld).define('A', mat)
                .unlockedBy("has_"+word+"_sword_blade",has(swdbld))
                .save(recipeConsumer,rLFrom(word+"_pickaxe_head_from_sword_blade"));
        ShapedRecipeBuilder.shaped(tool)
                .pattern("AA")
                .pattern("RA")
                .define('A', mat).define('R', ModTags.Items.HAFTS)
                .unlockedBy("has_"+word,has(mat))
                .save(recipeConsumer, rLFrom(word+"_pickaxe_from_mats_and_haft"));
        ShapedRecipeBuilder.shaped(tool)
                .pattern("HA")
                .pattern("R ")
                .define('H', swdbld).define('A', mat).define('R', ModTags.Items.HAFTS)
                .unlockedBy("has_"+word+"_sword_blade",has(swdbld))
                .save(recipeConsumer,rLFrom( word+"_pickaxe_from_swordblade_mat_and_haft"));
        ShapedRecipeBuilder.shaped(tool)
                .pattern("H")
                .pattern("R")
                .define('H', head).define('R', ModTags.Items.HAFTS)
                .unlockedBy("has_"+word+"_pickaxe_head",has(head))
                .save(recipeConsumer,rLFrom( word+"_pickaxe_from_head_and_haft"));
    }
    public static void axeHead(Consumer<FinishedRecipe> recipeConsumer, Item tool, Item head, TagKey<Item> mat, String word){
        ShapedRecipeBuilder.shaped(head)
                .pattern("AA")
                .pattern("A ")
                .define('A', mat)
                .unlockedBy("has_"+word,has(mat))
                .save(recipeConsumer, rLFrom(word+"_axe_head_from_mats"));
        ShapedRecipeBuilder.shaped(tool)
                .pattern("AA")
                .pattern("AR")
                .define('A', mat).define('R', ModTags.Items.HAFTS)
                .unlockedBy("has_"+word,has(mat))
                .save(recipeConsumer, rLFrom(word+"_axe_from_mats_and_haft"));
        ShapedRecipeBuilder.shaped(tool)
                .pattern("H")
                .pattern("R")
                .define('H', head).define('R', ModTags.Items.HAFTS)
                .unlockedBy("has_"+word+"_axe_head",has(head))
                .save(recipeConsumer, rLFrom(word+"_axe_from_head_and_haft"));
    }
    public static void hoeBlade(Consumer<FinishedRecipe> recipeConsumer, Item tool, Item head, TagKey<Item> mat, String word, Item swdbld){
        ShapelessRecipeBuilder.shapeless(head)
                .requires(swdbld)
                .unlockedBy("has_"+word+"_sword_blade",has(swdbld))
                .save(recipeConsumer,rLFrom(word+"_hoe_blade_spin"));
        ShapedRecipeBuilder.shaped(tool)
                .pattern("AA")
                .pattern(" R")
                .define('A', mat).define('R', ModTags.Items.HAFTS)
                .unlockedBy("has_"+word,has(mat))
                .save(recipeConsumer, rLFrom(word+"_hoe_from_mats_and_haft"));
        ShapedRecipeBuilder.shaped(tool)
                .pattern("H")
                .pattern("R")
                .define('H', head).define('R', ModTags.Items.HAFTS)
                .unlockedBy("has_"+word+"_hoe_blade",has(head))
                .save(recipeConsumer, rLFrom(word+"_hoe_from_head_and_haft"));
    }
    public static void shovelBlade(Consumer<FinishedRecipe> recipeConsumer, Item tool, TagKey<Item> mat, String word) { //is one mat for head, no head item.
        ShapedRecipeBuilder.shaped(tool)
                .pattern("A")
                .pattern("R")
                .define('A', mat)
                .define('R', ModTags.Items.HAFTS)
                .unlockedBy("has_"+word,has(mat))
                .save(recipeConsumer, rLFrom(word+"_shovel_from_mats_and_haft"));
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> recipeConsumer) {
        registerHeadRecipes("wooden",  Items.WOODEN_SWORD,Items.WOODEN_PICKAXE,Items.WOODEN_AXE,Items.WOODEN_HOE,Items.WOODEN_SHOVEL, ItemTags.PLANKS,recipeConsumer);
        registerHeadRecipes("stone",  Items.STONE_SWORD,Items.STONE_PICKAXE,Items.STONE_AXE,Items.STONE_HOE,Items.STONE_SHOVEL, ItemTags.STONE_TOOL_MATERIALS,recipeConsumer);
        registerHeadRecipes("iron",  Items.IRON_SWORD,Items.IRON_PICKAXE,Items.IRON_AXE,Items.IRON_HOE,Items.IRON_SHOVEL, Tags.Items.INGOTS_IRON,recipeConsumer);
        registerHeadRecipes("diamond",  Items.DIAMOND_SWORD,Items.DIAMOND_PICKAXE,Items.DIAMOND_AXE,Items.DIAMOND_HOE,Items.DIAMOND_SHOVEL, Tags.Items.GEMS_DIAMOND,recipeConsumer);
        registerHeadRecipes("golden",  Items.GOLDEN_SWORD,Items.GOLDEN_PICKAXE,Items.GOLDEN_AXE,Items.GOLDEN_HOE,Items.GOLDEN_SHOVEL, Tags.Items.INGOTS_GOLD,recipeConsumer);
    }
}
