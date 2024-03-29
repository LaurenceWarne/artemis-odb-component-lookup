package laurencewarne.componentlookup;

import com.artemis.BaseSystem;
import com.badlogic.gdx.utils.ObjectIntMap;

import laurencewarne.componentlookup.annotations.FieldLookup;

public class SystemD extends BaseSystem {

    @FieldLookup(component=ComponentA.class, field="notAField")
    private ObjectIntMap<String> lookup;

    public ObjectIntMap<String> getLookup() {
	return lookup;
    }    

    @Override
    public void processSystem() {
	
    }
}
