package pluginworkshop;

import de.itc.onkostar.api.Disease;
import de.itc.onkostar.api.IOnkostarApi;
import de.itc.onkostar.api.Procedure;
import de.itc.onkostar.api.analysis.AnalyseTriggerEvent;
import de.itc.onkostar.api.analysis.AnalyzerRequirement;
import de.itc.onkostar.api.analysis.IProcedureAnalyzer;
import de.itc.onkostar.api.analysis.OnkostarPluginType;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

public class DemoProcedureAnalyzer implements IProcedureAnalyzer {

    @Autowired
    private IOnkostarApi onkostarApi;

    @Override
    public OnkostarPluginType getType() {
        return OnkostarPluginType.ANALYZER;
    }

    @Override
    public String getVersion() {
        return "0.1.0";
    }

    @Override
    public String getName() {
        return "DemoPlugin";
    }

    @Override
    public String getDescription() {
        return "Demo Plugin von heute";
    }

    /**
     * @deprecated
     */
    @Override
    public boolean isRelevantForDeletedProcedure() {
        return false;
    }

    @Override
    public boolean isRelevantForAnalyzer(Procedure procedure, Disease disease) {
        return null != procedure && disease != null && procedure.getFormName().equals("OS.Diagnose");
    }

    @Override
    public AnalyzerRequirement getRequirement() {
        return AnalyzerRequirement.PROCEDURE;
    }

    @Override
    public Set<AnalyseTriggerEvent> getTriggerEvents() {
        return Set.of(AnalyseTriggerEvent.CREATE, AnalyseTriggerEvent.CREATE_LOCK);
    }

    @Override
    public boolean isSynchronous() {
        return false;
    }

    @Override
    public void analyze(Procedure procedure, Disease disease) {
        var patientid = procedure.getPatient().getId();
        onkostarApi.setPersonPool(patientid, 1);
    }
}
