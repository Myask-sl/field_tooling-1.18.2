package foo.myask.field_tooling.tags;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class ModTags {
    private static TagKey<Item> tag (String name){
        return ItemTags.create(new ResourceLocation("field_tooling",name));
    }
    public static class Items{
        public static final TagKey<Item> HAFTS = tag("hafts");
    }
}
