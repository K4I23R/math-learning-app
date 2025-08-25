package pl.michalsnella.mathlearning.model.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserProfile {
    private String id;
    private String name;
    private Integer age;
    private Gender gender;
    private Avatar avatar;

    private int points;
    private UserStats stats = new UserStats();
    private Set<Achievement> achievements = new HashSet<>();

    private Instant createdAt;
    private Instant lastSeenAt;

    public UserProfile() {}

    public static UserProfile create(String name, Integer age, Avatar avatar) {
        UserProfile u = new UserProfile();
        u.id = UUID.randomUUID().toString();
        u.name = name;
        u.age = age;
        u.avatar = avatar != null ? avatar : Avatar.FOX;
        u.points = 0;
        u.createdAt = Instant.now();
        u.lastSeenAt = u.createdAt;
        return u;
    }


    public void addPoints(int delta) {
        this.points += Math.max(0, delta);
    }

    public boolean spendPoints(int cost) {
        if (points >= cost) {
            points -= cost;
            return true;
        }
        return false;
    }


    public boolean grant(Achievement a) {
        return achievements.add(a);
    }

    public boolean has(Achievement a) {
        return achievements.contains(a);
    }


    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    public Gender getGender() {
        return gender;
    }

    public Avatar getAvatar() {
        return avatar;
    }

    public int getPoints() {
        return points;
    }

    public UserStats getStats() {
        return stats;
    }

    public Set<Achievement> getAchievements() {
        return achievements;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getLastSeenAt() {
        return lastSeenAt;
    }


    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public void setAvatar(Avatar avatar) {
        this.avatar = avatar;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void setStats(UserStats stats) {
        this.stats = stats;
    }

    public void setAchievements(Set<Achievement> achievements) {
        this.achievements = achievements;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public void setLastSeenAt(Instant lastSeenAt) {
        this.lastSeenAt = lastSeenAt;
    }


    @JsonIgnore
    public String displayLabel() {
        return name + (age != null ? (" (" + age + ")") : "");
    }

}
