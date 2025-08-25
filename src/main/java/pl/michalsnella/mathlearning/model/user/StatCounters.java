package pl.michalsnella.mathlearning.model.user;

public class StatCounters {
    private int tasksTotal;
    private int tasksCorrect;
    private int tasksPerfect;
    private long timeMs;

    public void addTasks(int total, int correct, int perfect) {
        this.tasksTotal   += total;
        this.tasksCorrect += correct;
        this.tasksPerfect += perfect;
    }

    public void addTime(long deltaMs) {
        this.timeMs += deltaMs;
    }

    public int getTasksTotal() {
        return tasksTotal;
    }

    public int getTasksCorrect() {
        return tasksCorrect;
    }

    public int getTasksPerfect() {
        return tasksPerfect;
    }

    public long getTimeMs() {
        return timeMs;
    }

    public void setTasksTotal(int v) {
        this.tasksTotal=v;
    }

    public void setTasksCorrect(int v) {
        this.tasksCorrect=v;
    }

    public void setTasksPerfect(int v) {
        this.tasksPerfect=v;
    }

    public void setTimeMs(long v) {
        this.timeMs=v;
    }

}
