package pl.michalsnella.mathlearning.service.user;

import pl.michalsnella.mathlearning.model.user.*;

import java.io.IOException;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

public class UserService {
    //private static final String DEFAULT_STORE_DIR = Paths.get(System.getProperty("user.home"), ".mathlearning", "users").toString();
    private static final String DEFAULT_STORE_DIR = "D:\\Programowanie - projekty\\LearningApp\\math-learning\\users";
    private final UserRepository repo;
    private UserProfile active;

    public UserService() {
        this.repo = new UserRepository(Paths.get(DEFAULT_STORE_DIR));
    }


    public UserService(String storeDir) {
        this.repo = new UserRepository(Paths.get(storeDir));
    }


    public UserProfile createUser(String name, Integer age, Avatar avatar) throws IOException {
        UserProfile u = UserProfile.create(name, age, avatar);
        repo.save(u);
        return u;
    }

    public List<UserProfile> listUsers() throws IOException {
        return repo.loadAll();
    }


    public Optional<UserProfile> getActive() {
        return Optional.ofNullable(active);
    }


    public void setActive(UserProfile u) throws IOException {
        this.active = u;
        u.setLastSeenAt(Instant.now());
        repo.save(u);
    }


    public void setActiveById(String userId) throws IOException {
        this.active = repo.load(userId);
        this.active.setLastSeenAt(Instant.now());
        repo.save(this.active);
    }


    public void deleteUser(String userId) throws IOException {
        if (active != null && userId.equals(active.getId())) active = null;
        repo.delete(userId);
    }


    public void addPoints(int delta) throws IOException {
        ensureActive();
        active.addPoints(delta);
        repo.save(active);
    }
    public boolean spendPoints(int cost) throws IOException {
        ensureActive();
        boolean ok = active.spendPoints(cost);
        if (ok) repo.save(active);
        return ok;
    }


    public boolean grantAchievement(Achievement a) throws IOException {
        ensureActive();
        boolean fresh = active.grant(a);
        if (fresh) repo.save(active);
        return fresh;
    }


    public boolean hasAchievement(Achievement a) throws IOException {
        ensureActive();
        return active.has(a);
    }


    public void addSessionStats(OperationType op, int total, int correct, int perfect, long timeMs) throws IOException {
        ensureActive();
        active.getStats().add(op, total, correct, perfect, timeMs);
        repo.save(active);
    }


    private void ensureActive() {
        if (active == null) throw new IllegalStateException("No active user selected.");
    }
}

