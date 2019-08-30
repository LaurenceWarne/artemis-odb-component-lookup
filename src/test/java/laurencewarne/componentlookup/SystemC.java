package laurencewarne.componentlookup;

import com.artemis.BaseSystem;
import com.badlogic.gdx.utils.ObjectIntMap;

import laurencewarne.componentlookup.annotations.FieldLookup;

public class SystemC extends BaseSystem {

    @FieldLookup(targetClass=ComponentA.class, componentField="birthday")
    private ObjectIntMap<String> bdayLookup;
    @FieldLookup(targetClass=ComponentA.class, componentField="age")
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
