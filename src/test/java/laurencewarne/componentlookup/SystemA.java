package laurencewarne.componentlookup;

import com.artemis.BaseSystem;
import com.badlogic.gdx.utils.ObjectIntMap;

import laurencewarne.componentlookup.annotations.FieldLookup;

public class SystemA extends BaseSystem {

    @FieldLookup(component=ComponentA.class, field="name")
    public ObjectIntMap<String> lookup;

    @Override
    public void processSystem() {
	
    }
}
