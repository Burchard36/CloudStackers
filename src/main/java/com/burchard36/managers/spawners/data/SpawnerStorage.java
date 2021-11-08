package com.burchard36.managers.spawners.data;

import com.burchard36.json.JsonDataFile;
import com.burchard36.json.enums.FileFormat;
import com.burchard36.json.errors.InvalidClassAdapterException;
import com.squareup.moshi.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SpawnerStorage extends JsonDataFile {

    //public List<Object> spawners;
    private List<JsonSpawnerData> spawnerData;

    public SpawnerStorage(JavaPlugin plugin, String pathToFile, FileFormat format) {
        super(plugin, pathToFile, format);
        //this.spawners = new ArrayList<>();
        this.spawnerData = new ArrayList<>();
        this.spawnerData.add(new JsonSpawnerData());
    }

    @FromJson
    @Override
    public void fromJson(JsonReader reader, JsonAdapter<? extends JsonDataFile> classFileAdapter)
            throws IOException, InvalidClassAdapterException {
        final SpawnerStorage spawnerStorage = (SpawnerStorage) classFileAdapter.fromJson(reader);
        if (spawnerStorage == null) throw new InvalidClassAdapterException("Invalid class adapter when parsing SpawnerStorage class!");

        this.spawnerData = spawnerStorage.spawnerData;
    }

    @ToJson
    @Override
    public void toJson(JsonWriter writer) throws IOException {
        writer.beginObject()
                .name("spawners");
        this.writeSpawners(writer);
        writer.endObject();
    }

    private void writeSpawners(final JsonWriter writer) throws IOException{
        writer.beginArray();
        this.spawnerData.forEach((spawnerData) -> {
            try {
                writer.beginObject();
                writer.name("x").value(spawnerData.x);
                writer.name("y").value(spawnerData.y);
                writer.name("z").value(spawnerData.z);
                writer.name("world_name").value(spawnerData.worldName);
                writer.name("spawner_amount").value(spawnerData.spawnerAmount);
                writer.endObject();
            } catch (IOException e) {
                Bukkit.getLogger().info("SpawnerStorage#writerSpawners() ERROR!! Cannot write JSON object");
                e.printStackTrace();
            }
        });
        writer.endArray();
    }

    /**
     * Returns the list of SpawnerData
     * @return List of JsonSpawnerData objects
     */
    public final List<JsonSpawnerData> getSpawnerData() {
        return this.spawnerData;
    }
}
