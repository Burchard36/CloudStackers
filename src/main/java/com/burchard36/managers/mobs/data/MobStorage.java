package com.burchard36.managers.mobs.data;

import com.burchard36.json.JsonDataFile;
import com.burchard36.json.enums.FileFormat;
import com.burchard36.json.errors.InvalidClassAdapterException;
import com.squareup.moshi.*;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.List;

public class MobStorage extends JsonDataFile {

    @Json(name = "mobs")
    private List<JsonMobData> mobList;

    public MobStorage(JavaPlugin plugin, String pathToFile, FileFormat format) {
        super(plugin, pathToFile, format);
    }

    @FromJson
    @Override
    public void fromJson(JsonReader reader, JsonAdapter<? extends JsonDataFile> classFileAdapter)
            throws IOException, InvalidClassAdapterException {
            final MobStorage mobStorage = (MobStorage) classFileAdapter.fromJson(reader);
            if (mobStorage == null) throw new InvalidClassAdapterException("Invalid class adapter when parsing MobStorage class!");

            this.mobList = mobStorage.mobList;
    }

    @Override
    public void toJson(JsonWriter writer) throws IOException {
        writer.beginObject();
        this.writeMobList(writer);
        writer.endObject();
    }

    private void writeMobList(final JsonWriter writer) throws IOException{
        writer.name("mobs")
                .beginArray();
        this.mobList.forEach((jsonMobData) -> {
            try {
                writer.beginObject();
                writer.name("mob_type").value(jsonMobData.mobType)
                        .name("x").value(jsonMobData.x)
                        .name("y").value(jsonMobData.y)
                        .name("z").value(jsonMobData.z)
                        .name("world_name").value(jsonMobData.worldName)
                        .name("mob_amount").value(jsonMobData.amount);
                writer.endObject();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public final List<JsonMobData> getMobDataList() {
        return this.mobList;
    }
}
