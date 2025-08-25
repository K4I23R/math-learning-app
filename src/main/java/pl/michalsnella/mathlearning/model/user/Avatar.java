package pl.michalsnella.mathlearning.model.user;

public enum Avatar {
    BOY_1, BOY_2, BOY_3, GIRL_1, GIRL_2, GIRL_3, FOX, CAT, DOG, PANDA, KOALA, KNIGHT, ROBOT;

    /** Ścieżka do assetu, np. /img/avatars/FOX.png */
    public String resourcePath() {
        return "/img/avatars/" + name().toLowerCase() + ".png";
    }
}
