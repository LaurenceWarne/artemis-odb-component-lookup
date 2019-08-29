package laurencewarne.componentlookup;

import com.artemis.BaseSystem;
import com.badlogic.gdx.utils.IntMap;

import laurencewarne.componentlookup.annotations.FieldLookup;

public class SystemA extends BaseSystem {

    @FieldLookup(targetClass=ComponentA.class, componentField="name")
    private IntMap<String> lookup;

    @Override
    public void processSystem() {
	
    }
}
