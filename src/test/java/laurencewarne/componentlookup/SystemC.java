package laurencewarne.componentlookup;

import com.artemis.BaseSystem;
import com.badlogic.gdx.utils.ObjectIntMap;

import laurencewarne.componentlookup.annotations.FieldLookup;

public class SystemC extends BaseSystem {

    @FieldLookup(component=ComponentA.class, field="birthday")
    private ObjectIntMap<String> bdayLookup;
    @FieldLookup(component=ComponentA.class, field="age")
    private ObjectIntMap<Integer> ageLookup;

    public ObjectIntMap<String> getBdayLookup() {
	return bdayLookup;
    }

    public ObjectIntMap<Integer> getAgeLookup() {
	return ageLookup;
    }    

    @Override
    public void processSystem() {
	
    }
}
