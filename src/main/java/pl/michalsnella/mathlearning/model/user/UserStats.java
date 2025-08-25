package pl.michalsnella.mathlearning.model.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.EnumMap;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserStats {
    private StatCounters global = new StatCounters();
    private Map<OperationType, StatCounters> byOperation = new EnumMap<>(OperationType.class);

    public UserStats() {
        for (OperationType op : OperationType.values()) {
            byOperation.put(op, new StatCounters());
        }
    }

    public StatCounters getGlobal() { return global; }
    public void setGlobal(StatCounters global) { this.global = global; }

    public Map<OperationType, StatCounters> getByOperation() { return byOperation; }
    public void setByOperation(Map<OperationType, StatCounters> byOperation) { this.byOperation = byOperation; }

    public void add(OperationType op, int total, int correct, int perfect, long timeMs) {
        global.addTasks(total, correct, perfect);
        global.addTime(timeMs);
        byOperation.get(op).addTasks(total, correct, perfect);
        byOperation.get(op).addTime(timeMs);
    }
}