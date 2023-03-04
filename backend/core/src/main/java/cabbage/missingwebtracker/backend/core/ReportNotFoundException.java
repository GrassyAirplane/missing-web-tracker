package cabbage.missingwebtracker.backend.core;

public class ReportNotFoundException extends IllegalArgumentException {

    public final String serializedParameters;

    public ReportNotFoundException(String parameters) {
        this.serializedParameters = parameters;
    }


}
