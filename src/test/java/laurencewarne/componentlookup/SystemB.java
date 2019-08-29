package laurencewarne.componentlookup;

import com.artemis.BaseSystem;
import com.badlogic.gdx.utils.ObjectIntMap;

import laurencewarne.componentlookup.annotations.FieldLookup;

public class SystemB extends BaseSystem {

    @FieldLookup(targetClass=ComponentA.class, componentField="Age")
    private ObjectIntMap<Integer> lookup;

    public ObjectIntMap<Integer> getLookup() {
	return lookup;
    }

    @Override
    public void processSystem() {
	
    }
}


