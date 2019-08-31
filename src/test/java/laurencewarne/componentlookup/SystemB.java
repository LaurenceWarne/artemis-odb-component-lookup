package laurencewarne.componentlookup;

import com.artemis.BaseSystem;
import com.badlogic.gdx.utils.ObjectIntMap;

import laurencewarne.componentlookup.annotations.FieldLookup;

public class SystemB extends BaseSystem {

    @FieldLookup(component=ComponentA.class, field="birthday")
    private ObjectIntMap<String> lookup;

    public ObjectIntMap<String> getLookup() {
	return lookup;
    }

    @Override
    public void processSystem() {
	
    }
}
