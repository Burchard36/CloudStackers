package com.burchard36.managers.spawners.config;

import com.burchard36.CloudStacker;
import com.burchard36.json.JsonDataFile;
import com.burchard36.json.enums.FileFormat;
import com.burchard36.json.errors.InvalidClassAdapterException;
import com.burchard36.managers.spawners.config.formats.spawner.SpawnerSettings;
import com.burchard36.managers.spawners.config.formats.world.WorldSettings;
import com.squareup.moshi.*;
import okio.BufferedSource;

import java.io.IOException;

public class SpawnerConfig extends JsonDataFile {

    @Json(name = "world_settings")
    public WorldSettings worldSettings;
    @Json(name = "spawner_settings")
    public SpawnerSettings spawnerSettings;

    public SpawnerConfig(final CloudStacker plugin,
                         final String pathToFile,
                         final FileFormat format) {
        super(plugin, pathToFile, format);
        this.worldSettings = new WorldSettings();
        this.spawnerSettings = new SpawnerSettings();
    }

    @FromJson
    @Override
    public void fromJson(JsonReader reader, JsonAdapter<? extends JsonDataFile> classFileAdapter) throws
            IOException, InvalidClassAdapterException {

        final SpawnerConfig spawnerConfig = (SpawnerConfig) classFileAdapter.fromJson(reader);
        if (spawnerConfig == null) throw new InvalidClassAdapterException("Invalid class file adapter for Moshi when reading from SpawnerConfig class!");

        this.worldSettings = spawnerConfig.worldSettings;
    }

    @ToJson
    @Override
    public void toJson(JsonWriter writer) throws IOException {
        /* World Settings object */
        writer.beginObject();
        this.writeWorldSettings(writer);
        this.writeSpawnerSettings(writer);
        writer.endObject();
    }

    private void writeWorldSettings(final JsonWriter writer) throws IOException {
        writer.name("world_settings")
                .beginObject()
                .name("enabled_worlds")
                .jsonValue(this.worldSettings.enabledWorlds)
                .endObject();
    }

    private void writeSpawnerSettings(final JsonWriter writer) throws IOException {
        writer.name("spawner_settings")
                .beginObject()
                .name("spawner_mode")
                .value(this.spawnerSettings.getSpawnMode().name())
                .endObject();
    }
}
