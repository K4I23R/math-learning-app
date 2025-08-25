package pl.michalsnella.mathlearning.service.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import pl.michalsnella.mathlearning.model.user.UserProfile;

import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    private final Path baseDir;
    private final ObjectMapper mapper;

    public UserRepository(Path baseDir) {
        this.baseDir = baseDir;
        this.mapper = new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .enable(SerializationFeature.INDENT_OUTPUT)
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        System.out.println("[UserRepository] Base dir: " + this.baseDir);
    }

    private Path fileFor(String userId) { return baseDir.resolve(userId + ".json"); }

    public void init() throws IOException {
        if (!Files.exists(baseDir)) Files.createDirectories(baseDir);
    }

    public void save(UserProfile profile) throws IOException {
        init();
        mapper.writeValue(fileFor(profile.getId()).toFile(), profile);
    }

    public UserProfile load(String userId) throws IOException {
        Path p = fileFor(userId);
        if (!Files.exists(p)) throw new NoSuchFileException("User not found: " + userId);
        return mapper.readValue(p.toFile(), UserProfile.class);
    }

    public List<UserProfile> loadAll() throws IOException {
        init();
        List<UserProfile> out = new ArrayList<>();
        try (DirectoryStream<Path> ds = Files.newDirectoryStream(baseDir, "*.json")) {
            for (Path p : ds) out.add(mapper.readValue(p.toFile(), UserProfile.class));
        }
        return out;
    }

    public void delete(String userId) throws IOException {
        Files.deleteIfExists(fileFor(userId));
    }
}

